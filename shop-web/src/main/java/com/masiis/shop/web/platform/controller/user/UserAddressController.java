package com.masiis.shop.web.platform.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by hanzengzhi on 2016/3/7.
 * 用户地址
 */
@Controller
@RequestMapping("/userAddress")
public class UserAddressController extends BaseController {

    @Resource
    private UserAddressService userAddressService;

    //管理地址跳转到选择地址界面
    private static  final  int managePageToChooseAddressPageTag = 0;
    //管理地址跳转到个人中心界面
    private static  final  int managePageToPersonalInfoPageTag = 1;
    //新增地址增加完跳转到订单界面
    public static final int addAddressPageToOrderPage = 0;
    //新增地址增加完跳转到管理地址界面
    public static final int getAddAddressPageToPersonalInfoPage =1;

    /**
     * 跳转到新增地址界面
     *
     * @author hanzengzhi
     * @date 2016/3/7 23:27
     */
    @RequestMapping("/toAddAddressPage.html")
    public String toAddAddressPage(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(value = "addAddressJumpType",required = false,defaultValue = "0") int addAddressJumpType,
                                   Model model) throws Exception {
        model.addAttribute("addAddressJumpType",addAddressJumpType);
        return "platform/order/xinjiandizhi";
    }

    /**
     * 新增地址
     *
     * @author hanzengzhi
     * @date 2016/3/7 23:27
     */
    @RequestMapping("/addOrUpdateAddress.do")
    @ResponseBody
    public String addOrUpdateAddress(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value = "id", required = false) Long id,
                                     @RequestParam(value = "name", required = true) String name,
                                     @RequestParam(value = "phone", required = true) String phone,
                                     @RequestParam(value = "postcode", required = true) String postcode,
                                     @RequestParam(value = "provinceId", required = true) Integer provinceId,
                                     @RequestParam(value = "provinceName", required = true) String provinceName,
                                     @RequestParam(value = "cityId", required = true) Integer cityId,
                                     @RequestParam(value = "cityName", required = true) String cityName,
                                     @RequestParam(value = "countyId", required = true) Integer countyId,
                                     @RequestParam(value = "countyName", required = true) String countyName,
                                     @RequestParam(value = "detailAddress", required = true) String detailAddress,
                                     @RequestParam(value = "isDefault", required = false) Integer isDefault,
                                     @RequestParam(value = "operateType", required = true) String operateType,
                                     @RequestParam(value = "addressId", required = false) Integer selectedAddressId,
                                     @RequestParam(value = "pfCorderId", required = false) Integer pfCorderId,
                                     @RequestParam(value = "addAddressJumpType",required = false,defaultValue = "0")int addAddressJumpType,
                                     Model model) throws JsonProcessingException {
        try{
            ComUser comUser = getComUser(request);
            ComUserAddress comUserAddress = new ComUserAddress();
            if (comUser != null) {
                comUserAddress.setUserId(comUser.getId());
            } else {
                throw new BusinessException("请重新登录");
            }
            comUserAddress.setName(name);
            comUserAddress.setMobile(phone);
            comUserAddress.setZip(postcode);
            comUserAddress.setProvinceId(provinceId);
            comUserAddress.setProvinceName(provinceName);
            comUserAddress.setCityId(cityId);
            comUserAddress.setCityName(cityName);
            comUserAddress.setRegionId(countyId);
            comUserAddress.setRegionName(countyName);
            comUserAddress.setAddress(detailAddress);
            comUserAddress.setCreateTime(new Date());

            model.addAttribute("addressId", selectedAddressId);
            model.addAttribute("pfCorderId", pfCorderId);
            String s = userAddressService.addOrUpdateAddress(request,id,isDefault,comUserAddress,operateType,addAddressJumpType);
            return s;
        }catch (Exception ex){
            if (org.apache.commons.lang.StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
}

    /**
     * 选择地址点击某个地址或者返回
     *
     * @author hanzengzhi
     * @date 2016/3/12 11:56
     */
    @RequestMapping("/clickAddressOrReturnToPage.do")
    public String clickAddressOrReturnToPage(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestParam(value = "selectedAddressId", required = false) Long selectedAddressId)throws Exception {
        String redirectHead = "redirect:";
        String redirectBody = userAddressService.getOrderPagePath(request,selectedAddressId);
        request.getSession().removeAttribute(SysConstants.SESSION_ORDER_SELECTED_ADDRESS);
        request.getSession().removeAttribute(SysConstants.SESSION_ORDER_TYPE);
        request.getSession().removeAttribute(SysConstants.SESSION_ORDER_Id);
        request.getSession().removeAttribute(SysConstants.SESSION_ORDER_SKU_ID);
        request.getSession().removeAttribute(SysConstants.SESSION_PF_USER_SKU_STOCK_ID);
        return redirectHead+redirectBody;
    }

    /**
     * 跳转到编辑地址界面
     *
     * @author hanzengzhi
     * @date 2016/3/9 18:14
     */
    @RequestMapping("/toEditAddress.html")
    public String toEditAddress(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam(value = "id", required = true) Long id,
                                @RequestParam(value = "addAddressJumpType",required = false,defaultValue = "0")int addAddressJumpType,
                                @RequestParam(value = "manageAddressJumpType",required = false,defaultValue = "0")int manageAddressJumpType,
                                Model model) throws Exception {
        ComUserAddress comUserAddress = userAddressService.getUserAddressById(id);
        if (comUserAddress != null) {
            model.addAttribute("provinceId", comUserAddress.getProvinceId());
            model.addAttribute("cityId", comUserAddress.getCityId());
            model.addAttribute("countyId", comUserAddress.getRegionId());
            model.addAttribute("comUserAddress", comUserAddress);
            model.addAttribute("addAddressJumpType",addAddressJumpType);
            model.addAttribute("manageAddressJumpType",manageAddressJumpType);
        }
        return "platform/order/editAddress";
    }

    /**
     * 管理地址界面返回按钮，返回到具体界面
     *
     * @author hanzengzhi
     * @date 2016/3/14 14:25
     */
    @RequestMapping("/manageAddressPageToChooseAddressPage.html")
    public String manageAddressPageToChooseAddressPage(HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       @RequestParam(value = "manageAddressJumpType",required = true,defaultValue = "0")int manageAddressJumpType,
                                                       Model model)throws Exception {
        String returnPage = null;
        switch (manageAddressJumpType){
            case managePageToChooseAddressPageTag: //返回到选择地址界面
                returnPage = "platform/order/xuanze";
                Long selectedAddressId = (Long) request.getSession().getAttribute(SysConstants.SESSION_ORDER_SELECTED_ADDRESS);
                model.addAttribute("addressId", selectedAddressId);
                break;
            case managePageToPersonalInfoPageTag:  //返回到到个人中心
                String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort();
                returnPage = "redirect:" + basePath +"/personalInfo/personalHomePageInfo.html";
                break;
            default://返回到选择地址界面
                break;
        }
        return returnPage;
    }

    /**
     * 跳转到选择地址界面
     *
     * @author hanzengzhi
     * @date 2016/3/9 15:14
     */
    @RequestMapping("/toChooseAddressPage.html")
    public String toChooseAddressPage(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "pageType", required = true) String pageType,
                                      @RequestParam(value = "orderId", required = false) Long orderId,
                                      @RequestParam(value = "skuId", required = false) Integer skuId,
                                      @RequestParam(value = "selectedAddressId", required = true) Long selectedAddressId,
                                      @RequestParam(value = "pfUserSkuStockId", required = false) Long pfUserSkuStockId,
                                      Model model)throws Exception {
        request.getSession().setAttribute(SysConstants.SESSION_ORDER_SELECTED_ADDRESS, selectedAddressId);
        request.getSession().setAttribute(SysConstants.SESSION_ORDER_TYPE, pageType);
        request.getSession().setAttribute(SysConstants.SESSION_ORDER_Id, orderId);
        request.getSession().setAttribute(SysConstants.SESSION_ORDER_SKU_ID, skuId);
        request.getSession().setAttribute(SysConstants.SESSION_PF_USER_SKU_STOCK_ID, pfUserSkuStockId);
        model.addAttribute("addressId", selectedAddressId);
        return "platform/order/xuanze";
    }



    /**
     * 跳转到管理地址界面
     *
     * @author hanzengzhi
     * @date 2016/3/7 22:59
     */
    @RequestMapping("/toManageAddressPage.html")
    public String toManageAddressPage(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "addAddressJumpType",required = false,defaultValue = "0")int addAddressJumpType,
                                      @RequestParam(value = "manageAddressJumpType",required = false,defaultValue = "0")int manageAddressJumpType,
                                      Model model)throws Exception {
        model.addAttribute("addAddressJumpType",addAddressJumpType);
        model.addAttribute("manageAddressJumpType",manageAddressJumpType);
        return "platform/order/guanli";
    }

    /**
     * 获得用户地址
     *
     * @author hanzengzhi
     * @date 2016/3/7 23:26
     */
    @RequestMapping("/getUserAddressByUserId.do")
    @ResponseBody
    public String getUserAddressByUserId(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Model model) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ComUser comUser = getComUser(request);
            ComUserAddress comUserAddress = new ComUserAddress();
            if (comUser != null) {
                comUserAddress.setUserId(comUser.getId());
            } else {
                comUserAddress.setUserId(1L);
            }
            List<ComUserAddress> comUserAddressList = userAddressService.queryComUserAddressesByParam(comUserAddress);
            String returnJson = objectMapper.writeValueAsString(comUserAddressList);
            return returnJson;
        }catch (Exception ex){
            if (org.apache.commons.lang.StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("获得地址失败", ex);
            }
        }
    }

    /**
     * 删除地址
     *
     * @author hanzengzhi
     * @date 2016/3/9 15:21
     */
    @RequestMapping("/deleteUserAddressById.do")
    @ResponseBody
    public Long deleteUserAddressById(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "id", required = true) Long id,
                                      @RequestParam(value = "defaultAddressId", required = false) Long defaultAddressId)  {
        try{
            if (StringUtils.isEmpty(id)) {
                throw new BusinessException("删除地址失败");
            }
            ComUser comUser = getComUser(request);
            Long userId = null;
            if (comUser != null) {
                userId = comUser.getId();
            } else {
                throw new BusinessException("请重新登录");
            }
            Long i = userAddressService.deleteUserAddressById(id, userId, defaultAddressId);
            if (i == 0) {
                return 0L;
            } else {
                Long selectedAddressId = (Long) request.getSession().getAttribute(SysConstants.SESSION_ORDER_SELECTED_ADDRESS);
                if (id.equals(selectedAddressId)) {
                    request.getSession().removeAttribute(SysConstants.SESSION_ORDER_SELECTED_ADDRESS);
                }
                return i;
            }
        }catch (Exception ex){
            if (org.apache.commons.lang.StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
    }

    /**
     * 设置地址为默认地址
     *
     * @author hanzengzhi
     * @date 2016/3/9 16:26
     */
    @RequestMapping("/settingDefaultAddress.do")
    @ResponseBody
    public Boolean settingDefaultAddress(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @RequestParam(value = "id", required = true) Long id) {
        try{
            ComUser comUser = getComUser(request);
            Long userId = null;
            if (comUser != null) {
                userId = comUser.getId();
            } else {
                throw new BusinessException("请重新登录");
            }
            Boolean bl = userAddressService.settingDefaultAddress(id, userId);
            return bl;
        }catch (Exception ex){
            if (org.apache.commons.lang.StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("设置默认地址失败", ex);
            }
        }
    }
}
