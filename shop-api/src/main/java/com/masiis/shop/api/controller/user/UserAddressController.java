package com.masiis.shop.api.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.api.bean.order.OManagementIndexReq;
import com.masiis.shop.api.bean.user.ComUserAddressReq;
import com.masiis.shop.api.bean.user.ComUserAddressRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysConstants;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.user.UserAddressService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAddress;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
        return "mall/user/xinjiandizhi";
    }

    /**
     * 新增地址
     *
     * @author hanzengzhi
     * @date 2016/3/7 23:27
     */
    @RequestMapping("/addOrUpdateAddress.do")
    @ResponseBody
    @SignValid(paramType = ComUserAddressRes.class)
    public ComUserAddressRes addOrUpdateAddress(HttpServletRequest request,
                                     ComUserAddressReq addressReq,
                                     ComUser comUser ) {
        ComUserAddressRes addressRes = new ComUserAddressRes();
        try{
            ComUserAddress address = new ComUserAddress();
            String s = null;
            if (comUser != null) {
                if (addressReq!=null&&isValidateParam(addressReq,address)){
                    addressReq.setUserId(comUser.getId());
                    addressReq.setCreateTime(new Date());
                    s = userAddressService.addOrUpdateAddress(request,addressReq.getId(),addressReq.getIsDefault(),address,addressReq.getOperateType(),10000);
                }else{
                    addressRes.setResCode(SysResCodeCons.RES_CODE_ADDRESS_NULL);
                    addressRes.setResMsg(SysResCodeCons.RES_CODE_ADDRESS_NULL_MSG);
                }
            } else {
                addressRes.setResCode(SysResCodeCons.RES_CODE_ADDRESS_NULL);
                addressRes.setResMsg(SysResCodeCons.RES_CODE_ADDRESS_NULL_MSG);
            }
            if (s!=null&&!s.equals("false")){
                addressRes.setResCode(SysResCodeCons.RES_CODE_ADDRESS_ADD_SUCCESS);
                addressRes.setResMsg(SysResCodeCons.RES_CODE_ADDRESS_ADD_SUCCESS_MSG);
            }
        }catch (Exception ex){
            addressRes.setResCode(SysResCodeCons.RES_CODE_ADDRESS_ADD_FAIL);
            addressRes.setResMsg(ex.getMessage());
        }
        return addressRes;
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
        request.getSession().removeAttribute(SysConstants.SESSION_MALL_CONFIRM_ORDER_SHOP_ID);
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
        return "mall/user/editAddress";
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
        String basePath = null;
        switch (manageAddressJumpType){
            case managePageToChooseAddressPageTag: //返回到选择地址界面
                returnPage = "mall/user/xuanze";
                Long selectedAddressId = (Long) request.getSession().getAttribute(SysConstants.SESSION_ORDER_SELECTED_ADDRESS);
                model.addAttribute("addressId", selectedAddressId);
                break;
            case managePageToPersonalInfoPageTag:  //返回到到个人中心
                basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort();
                returnPage = "redirect:" + basePath +"/sfOrderManagerController/borderManagement.html";
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
                                      @RequestParam(value = "shopId", required = false) Long shopId,
                                      Model model)throws Exception {
        request.getSession().setAttribute(SysConstants.SESSION_ORDER_SELECTED_ADDRESS, selectedAddressId);
        request.getSession().setAttribute(SysConstants.SESSION_ORDER_TYPE, pageType);
        request.getSession().setAttribute(SysConstants.SESSION_ORDER_Id, orderId);
        request.getSession().setAttribute(SysConstants.SESSION_ORDER_SKU_ID, skuId);
        request.getSession().setAttribute(SysConstants.SESSION_PF_USER_SKU_STOCK_ID, pfUserSkuStockId);
        request.getSession().setAttribute(SysConstants.SESSION_MALL_CONFIRM_ORDER_SHOP_ID, shopId);
        model.addAttribute("addressId", selectedAddressId);
        return "mall/user/xuanze";
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
        return "mall/user/guanli";
    }

    /**
     * 获得用户地址
     *
     * @author hanzengzhi
     * @date 2016/3/7 23:26
     */
    @RequestMapping("/getUserAddressByUserId.do")
    @ResponseBody
    @SignValid(paramType = ComUserAddressRes.class)
    public ComUserAddressRes getUserAddressByUserId(HttpServletRequest request,
                                         ComUserAddressReq addressReq,
                                         ComUser comUser) {
        ComUserAddressRes addressRes = new ComUserAddressRes();
        try{
            if (comUser != null) {
                addressReq.setUserId(comUser.getId());
            } else {
                addressRes.setResCode(SysResCodeCons.RES_CODE_ADDRESS_NULL);
                addressRes.setResMsg(SysResCodeCons.RES_CODE_ADDRESS_NULL_MSG);
                return addressRes;
            }
            ComUserAddress comUserAddress = new ComUserAddress();
            cloneAddressReqToAddress(addressReq,comUserAddress);
            List<ComUserAddress> comUserAddressList = userAddressService.queryComUserAddressesByParam(comUserAddress);
            addressRes.setResCode(SysResCodeCons.RES_CODE_ADDRESS_QUERY_SUCCESS);
            addressRes.setResMsg(SysResCodeCons.RES_CODE_ADDRESS_QUERY_SUCCESS_MEG);
            addressRes.setAddresses(comUserAddressList);
        }catch (Exception ex){
            addressRes.setResCode(SysResCodeCons.RES_CODE_ADDRESS_QUERY_FAIL);
            addressRes.setResMsg(ex.getMessage());
        }
        return addressRes;
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
            ComUser comUser = null;
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
            ComUser comUser = null;
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
    /**
     * 判断参数是否合法
     * @author hanzengzhi
     * @date 2016/5/19 17:57
     */
    private Boolean isValidateParam(ComUserAddressReq addressReq,ComUserAddress address){
        if (addressReq.getUserId()==null){
            return false;
        }
        if (StringUtils.isEmpty(addressReq.getName())){
            return false;
        }
        if (StringUtils.isEmpty(addressReq.getZip())){
            return false;
        }
        if (addressReq.getProvinceId()==null){
            return false;
        }
        if (StringUtils.isEmpty(addressReq.getProvinceName())){
            return false;
        }
        if (addressReq.getCityId()==null){
            return false;
        }
        if (StringUtils.isEmpty(addressReq.getCityName())){
            return false;
        }
        if (addressReq.getRegionId()==null){
            return false;
        }
        if (StringUtils.isEmpty(addressReq.getRegionName())){
            return false;
        }
        if (StringUtils.isEmpty(addressReq.getAddress())){
            return false;
        }
        if (StringUtils.isEmpty(addressReq.getMobile())){
            return false;
        }
        cloneAddressReqToAddress(addressReq,address);
        return true;
    }
    /**
     * address接口参数转化为address实体类
     * @author hanzengzhi
     * @date 2016/5/19 17:57
     */
    private ComUserAddress cloneAddressReqToAddress(ComUserAddressReq addressReq,ComUserAddress address){
        address.setUserId(addressReq.getUserId());
        address.setName(addressReq.getName());
        address.setZip(addressReq.getZip());
        address.setProvinceId(addressReq.getProvinceId());
        address.setProvinceName(addressReq.getProvinceName());
        address.setCityId(addressReq.getCityId());
        address.setCityName(addressReq.getCityName());
        address.setRegionId(addressReq.getRegionId());
        address.setRegionName(addressReq.getRegionName());
        address.setAddress(addressReq.getAddress());
        address.setMobile(addressReq.getMobile());
        return address;
    }
}
