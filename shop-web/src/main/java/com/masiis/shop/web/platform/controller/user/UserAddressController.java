package com.masiis.shop.web.platform.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.dao.beans.system.System;
import com.masiis.shop.dao.po.ComArea;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.web.platform.service.user.ComAreaService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import com.sun.org.apache.xpath.internal.operations.Mod;
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
     * @author  hanzengzhi
     * @date  2016/3/7 23:27
     */
    @RequestMapping("/toAddAddressPage.html")
    public String toAddAddressPage(HttpServletRequest request,
                                   HttpServletResponse response,Model model)throws JsonProcessingException{
        List<ComArea> comAreas = comAreaService.queryComAreasByParams(new ComArea());
        ObjectMapper objectMapper = new ObjectMapper();
        model.addAttribute("comAreas", objectMapper.writeValueAsString(comAreas));
        return "platform/order/xinjiandizhi";
    }
    /**
     * 新增地址
     * @author  hanzengzhi
     * @date  2016/3/7 23:27
     */
    @RequestMapping("/addOrUpdateAddress.do")
    @ResponseBody
    public String addOrUpdateAddress(HttpServletRequest request,
                             HttpServletResponse response,
                                     @RequestParam(value = "id", required = false) Integer id,
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
                                     @RequestParam(value = "operateType", required = true) String operateType)throws JsonProcessingException {
        ComUser comUser = (ComUser)request.getSession().getAttribute("comUser");
        ComUserAddress comUserAddress = new ComUserAddress();
        if (comUser!=null){
            comUserAddress.setUserId(comUser.getId());
        }else{
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
        if (operateType.equals("save")){
            i = userAddressService.addComUserAddress(comUserAddress);
        }else{
            comUserAddress.setIsDefault(isDefault);
            comUserAddress.setId(id);
            i = userAddressService.updateComUserAddress(comUserAddress);
        }
        if (i==1){
            return "success";
        }else{
            return "false";
        }
    }

    /**
     * 跳转到编辑地址界面
     * @author  hanzengzhi
     * @date  2016/3/9 18:14
     */
    @RequestMapping("/toEditAddress.html")
    public String toEditAddress(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam(value = "id", required = true)Integer id,
                                Model model)throws Exception{
        //获得用户地址
        if (StringUtils.isEmpty(id)){
            id = 1;
        }
        ComUserAddress comUserAddress = userAddressService.getUserAddressById(id);
        if (comUserAddress == null){
        }else{
            model.addAttribute("comUserAddress", comUserAddress);
        }
        //获得省市区
        List<ComArea> comAreas = comAreaService.queryComAreasByParams(new ComArea());
        ObjectMapper objectMapper = new ObjectMapper();
        model.addAttribute("comAreas", objectMapper.writeValueAsString(comAreas));
        return "platform/order/editAddress";
    }
    /**
     * 跳转到选择地址界面
     * @author  hanzengzhi
     * @date  2016/3/9 15:14
     */
    @RequestMapping("/toChooseAddressPage.html")
    public String toChooseAddressPage(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "addressId", required = false)Integer id,
                                      @RequestParam(value = "pfCorderId", required = true)Integer pfCorderId,
                                      Model model){
        model.addAttribute("addressId",id);
        model.addAttribute("pfCorderId",pfCorderId);
        return "platform/order/xuanze";
    }
    /**
     * 跳转到管理地址界面
     * @author  hanzengzhi
     * @date  2016/3/7 22:59
     */
    @RequestMapping("/toManageAddressPage.html")
    public String toManageAddressPage(HttpServletRequest request,
                                HttpServletResponse response,
                                      @RequestParam(value = "addressId", required = false)Integer id,
                                      @RequestParam(value = "pfCorderId", required = true)Integer pfCorderId,
                                      Model model){
        model.addAttribute("selectedAddressId",id);
        model.addAttribute("pfCorderId",pfCorderId);
        return "platform/order/guanli";
    }
    /**
     * 获得用户地址
     * @author  hanzengzhi
     * @date  2016/3/7 23:26
     */
    @RequestMapping("/getUserAddressByUserId.do")
    @ResponseBody
    public String getUserAddressByUserId(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Model model)throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        ComUser comUser = (ComUser)request.getSession().getAttribute("comUser");
        ComUserAddress comUserAddress = new ComUserAddress();
        if (comUser!=null){
            comUserAddress.setUserId(comUser.getId());
        }else{
            comUserAddress.setUserId(1L);
        }
        List<ComUserAddress> comUserAddressList = userAddressService.queryComUserAddressesByParam(comUserAddress);
        String returnJson = objectMapper.writeValueAsString(comUserAddressList);
        return returnJson;
    }
    /**
     * 删除地址
     * @author  hanzengzhi
     * @date  2016/3/9 15:21 
     */
    @RequestMapping("/deleteUserAddressById.do")
    @ResponseBody
    public Integer deleteUserAddressById(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "id", required = true)Integer id,
                                         @RequestParam(value = "defaultAddressId", required = false)Integer defaultAddressId)throws Exception{
        if (StringUtils.isEmpty(id)){
            id = 1;
        }else{

        }
        ComUser comUser = (ComUser)request.getSession().getAttribute("comUser");
        Long userId = null;
        if (comUser!=null){
            userId = comUser.getId();
        }else{
            userId = 1L;
        }
       int i = userAddressService.deleteUserAddressById(id,userId,defaultAddressId);
        if (i==0){
            return 0;
        }else{
            return i;
        }
    }
    /**
     * 设置地址为默认地址
     * @author  hanzengzhi
     * @date  2016/3/9 16:26
     */
    @RequestMapping("/settingDefaultAddress.do")
    @ResponseBody
    public Boolean settingDefaultAddress(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "id", required = true)Integer id){
        if (StringUtils.isEmpty(id)){
            id = 1;
        }else{
        }
        ComUser comUser = (ComUser)request.getSession().getAttribute("comUser");
        Long userId = null;
        if (comUser!=null){
            userId = comUser.getId();
        }else{
            userId = 1L;
        }
        Boolean bl = userAddressService.settingDefaultAddress(id,userId);
        return bl;
    }
}
