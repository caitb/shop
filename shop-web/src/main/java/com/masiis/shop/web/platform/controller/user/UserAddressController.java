package com.masiis.shop.web.platform.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.dao.beans.system.System;
import com.masiis.shop.dao.po.ComArea;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.web.platform.service.user.ComAreaService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by hzzh on 2016/3/7.
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
    @RequestMapping("/addAddress.json")
    public String addAddress(HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam(value = "name", required = true) String name,
                             @RequestParam(value = "phone", required = true) String phone,
                             @RequestParam(value = "postcode", required = true) String postcode,
                             @RequestParam(value = "provinceId", required = true) Integer provinceId,
                             @RequestParam(value = "provinceName", required = true) String provinceName,
                             @RequestParam(value = "cityId", required = true) Integer cityId,
                             @RequestParam(value = "cityName", required = true) String cityName,
                             @RequestParam(value = "countyId", required = true) Integer countyId,
                             @RequestParam(value = "countyName", required = true) String countyName,
                             @RequestParam(value = "street", required = true) String street,
                             @RequestParam(value = "detailAddress", required = true) String detailAddress,
                             Model model)throws JsonProcessingException {
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
        comUserAddress.setIsDefault(1);
        int i = userAddressService.addComUserAddress(comUserAddress);
        if (i==1){
            java.lang.System.out.println();
        }
        return null;
    }
    /**
     * 跳转到管理地址界面
     * @author  hanzengzhi
     * @date  2016/3/7 22:59
     */
    @RequestMapping("/toManageAddressPage.html")
    public String toManageAddressPage(HttpServletRequest request,
                                HttpServletResponse response){
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
                                         HttpServletResponse response)throws JsonProcessingException{
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
}
