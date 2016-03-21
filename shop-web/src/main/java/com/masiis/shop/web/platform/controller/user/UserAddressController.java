package com.masiis.shop.web.platform.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.dao.po.ComArea;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.service.user.ComAreaService;
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
public class UserAddressController {

    @Resource
    private UserAddressService userAddressService;
    @Resource
    private ComAreaService comAreaService;

    /**
     * 跳转到新增地址界面
     *
     * @author hanzengzhi
     * @date 2016/3/7 23:27
     */
    @RequestMapping("/toAddAddressPage.html")
    public String toAddAddressPage(HttpServletRequest request,
                                   HttpServletResponse response, Model model) throws JsonProcessingException {
/*        List<ComArea> comAreas = comAreaService.queryComAreasByParams(new ComArea());
        ObjectMapper objectMapper = new ObjectMapper();
        model.addAttribute("comAreas", objectMapper.writeValueAsString(comAreas));*/
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
                                     Model model) throws JsonProcessingException {
        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
        ComUserAddress comUserAddress = new ComUserAddress();
        if (comUser != null) {
            comUserAddress.setUserId(comUser.getId());
        } else {
            comUserAddress.setUserId(1L);
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
        int i = 0;
        if (operateType.equals("save")) {
            i = userAddressService.addComUserAddress(comUserAddress);
        } else {
            comUserAddress.setIsDefault(isDefault);
            comUserAddress.setId(id);
            i = userAddressService.updateComUserAddress(comUserAddress);
        }
        model.addAttribute("addressId", selectedAddressId);
        model.addAttribute("pfCorderId", pfCorderId);
        if (i == 1) {
            return "success";
        } else {
            return "false";
        }
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
                                Model model) throws Exception {
        //获得用户地址
        if (StringUtils.isEmpty(id)) {
            id = 1L;
        }
        ComUserAddress comUserAddress = userAddressService.getUserAddressById(id);
        if (comUserAddress == null) {
        } else {
            model.addAttribute("provinceId", comUserAddress.getProvinceId());
            model.addAttribute("cityId", comUserAddress.getCityId());
            model.addAttribute("countyId", comUserAddress.getRegionId());
            model.addAttribute("comUserAddress", comUserAddress);
        }
        //获得省市区
/*        List<ComArea> comAreas = comAreaService.queryComAreasByParams(new ComArea());
        ObjectMapper objectMapper = new ObjectMapper();
        model.addAttribute("comAreas", objectMapper.writeValueAsString(comAreas));*/
        return "platform/order/editAddress";
    }

    /**
     * 管理地址界面返回到选择地址界面
     *
     * @author hanzengzhi
     * @date 2016/3/14 14:25
     */
    @RequestMapping("/manageAddressPageToChooseAddressPage.html")
    public String manageAddressPageToChooseAddressPage(HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       Model model) {

        Long selectedAddressId = (Long) request.getSession().getAttribute(SysConstants.SESSION_ORDER_SELECTED_ADDRESS);
        model.addAttribute("addressId", selectedAddressId);
        return "platform/order/xuanze";
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
                                      Model model) {
        request.getSession().setAttribute(SysConstants.SESSION_ORDER_SELECTED_ADDRESS, selectedAddressId);
        request.getSession().setAttribute(SysConstants.SESSION_ORDER_TYPE, pageType);
        request.getSession().setAttribute(SysConstants.SESSION_ORDER_Id, orderId);
        request.getSession().setAttribute(SysConstants.SESSION_ORDER_SKU_ID, skuId);
        model.addAttribute("addressId", selectedAddressId);
        return "platform/order/xuanze";
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
                                             @RequestParam(value = "selectedAddressId", required = false) Long selectedAddressId) {

        String orderType = (String) request.getSession().getAttribute(SysConstants.SESSION_ORDER_TYPE);
        Long orderId = (Long) request.getSession().getAttribute(SysConstants.SESSION_ORDER_Id);
        Integer skuId = (Integer) request.getSession().getAttribute(SysConstants.SESSION_ORDER_SKU_ID);
        StringBuffer sb = new StringBuffer();
        if (orderType.equals(SysConstants.SESSION_TRIAL_ORDER_TYPE_VALUE)) {
            //跳转到支付使用界面
            sb.append("redirect:/corder/confirmOrder.do?");
            if (!StringUtils.isEmpty(skuId)) {
                sb.append("skuId=").append(skuId).append("&");
            }
            if (!StringUtils.isEmpty(selectedAddressId)) {
                sb.append("&selectedAddressId=").append(selectedAddressId);
            }
        } else if (orderType.equals(SysConstants.SESSION_PAY_ORDER_TYPE_VALUE)) {
            //跳转到支付界面
            sb.append("redirect:/border/payBOrder.shtml?");
            if (!StringUtils.isEmpty(orderId)) {
                sb.append("bOrderId=").append(orderId).append("&");
            }
            if (!StringUtils.isEmpty(selectedAddressId)) {
                sb.append("userAddressId=").append(selectedAddressId);
            }
        }
        request.getSession().removeAttribute(SysConstants.SESSION_ORDER_SELECTED_ADDRESS);
        request.getSession().removeAttribute(SysConstants.SESSION_ORDER_TYPE);
        request.getSession().removeAttribute(SysConstants.SESSION_ORDER_Id);
        request.getSession().removeAttribute(SysConstants.SESSION_ORDER_SKU_ID);
        return sb.toString();
    }

    /**
     * 跳转到管理地址界面
     *
     * @author hanzengzhi
     * @date 2016/3/7 22:59
     */
    @RequestMapping("/toManageAddressPage.html")
    public String toManageAddressPage(HttpServletRequest request,
                                      HttpServletResponse response) {
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
                                         Model model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
        ComUserAddress comUserAddress = new ComUserAddress();
        if (comUser != null) {
            comUserAddress.setUserId(comUser.getId());
        } else {
            comUserAddress.setUserId(1L);
        }
        List<ComUserAddress> comUserAddressList = userAddressService.queryComUserAddressesByParam(comUserAddress);
        String returnJson = objectMapper.writeValueAsString(comUserAddressList);
        return returnJson;
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
                                      @RequestParam(value = "defaultAddressId", required = false) Long defaultAddressId) throws Exception {
        if (StringUtils.isEmpty(id)) {
            id = 1L;
        } else {

        }
        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
        Long userId = null;
        if (comUser != null) {
            userId = comUser.getId();
        } else {
            userId = 1L;
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
        if (StringUtils.isEmpty(id)) {
            id = 1L;
        } else {
        }
        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
        Long userId = null;
        if (comUser != null) {
            userId = comUser.getId();
        } else {
            userId = 1L;
        }
        Boolean bl = userAddressService.settingDefaultAddress(id, userId);
        return bl;
    }
}
