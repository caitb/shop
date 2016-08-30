package com.masiis.shop.admin.controller.statistic;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.statistic.StatisticUserService;
import com.masiis.shop.common.util.WxPropertiesUtils;
import com.masiis.shop.dao.beans.statistic.StatisticOrder;
import com.masiis.shop.dao.beans.statistic.StatisticUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cai_tb on 16/8/29.
 */

@Controller
@RequestMapping("/statistic")
public class PlatformController {

    private final static Log log = LogFactory.getLog(PlatformController.class);

    @Resource
    private StatisticUserService statisticUserService;

    @RequestMapping("list.shtml")
    public String list(){
        return "statistic/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, String beginTime){
      try {
          if(beginTime == null){
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
              beginTime = sdf.format(new Date())+" 00:00:00";
          }
          //注册用户量
          List<StatisticUser> userList = statisticUserService.userList(beginTime, WxPropertiesUtils.getStringValue("wx.conf.sf.APPID"));
          //新增用户量
          List<StatisticUser> newUserList = statisticUserService.newUserList(beginTime, WxPropertiesUtils.getStringValue("wx.conf.sf.APPID"));
          //平台使用用户量（人）（麦链合伙人：合伙人数；）
          List<StatisticUser> useList = statisticUserService.useList(beginTime, WxPropertiesUtils.getStringValue("wx.conf.sf.APPID"));
          //平台使用用户量（人）（麦链合伙人：新增合伙人数；）
          List<StatisticUser> newUseList = statisticUserService.newUseList(beginTime, WxPropertiesUtils.getStringValue("wx.conf.sf.APPID"));
          //麦链商城购买人数
          List<StatisticUser> cityNum = statisticUserService.cityNum(beginTime, WxPropertiesUtils.getStringValue("wx.conf.sf.APPID"));
          //麦链商城新增购买人数
          List<StatisticUser> newCityNum = statisticUserService.newCityNum(beginTime, WxPropertiesUtils.getStringValue("wx.conf.sf.APPID"));
          //麦链合伙人成交订单量,成交金额
          List<StatisticOrder> selectOrder = statisticUserService.selectOrder(beginTime, WxPropertiesUtils.getStringValue("wx.conf.sf.APPID"));
          //麦链合伙人新增成交订单量,新增成交金额
          List<StatisticOrder> newSelectOrder = statisticUserService.newSelectOrder(beginTime, WxPropertiesUtils.getStringValue("wx.conf.sf.APPID"));
          //麦链商城<StatisticOrder>成交订单量,成交金额
          List<StatisticOrder> cityOrder = statisticUserService.cityOrder(beginTime, WxPropertiesUtils.getStringValue("wx.conf.sf.APPID"));
          //麦链商城新增成交订单量,新增成交金额
          List<StatisticOrder> newCityOrder = statisticUserService.newCityOrder(beginTime, WxPropertiesUtils.getStringValue("wx.conf.sf.APPID"));


          List<Map<String, Object>> list = new ArrayList();

          Map<String, Object> user = new HashMap<>();
         user.put("registerCountUser", userList.get(0).getRegisterCountUser());
         user.put("registerCount", userList.get(0).getTotal());
          if(newUserList != null && newUserList.size() > 0){
              user.put("newRegisterCount", newUserList.get(0).getTotal());
          }else{
              user.put("newRegisterCount", 0);
          }
          if(useList != null && useList.size() > 0){
              user.put("useCount",useList.get(0).getTotal());
          }else{
              user.put("useCount",0);
          }
          if(useList != null && useList.size() > 0){
              user.put("newUseCount",newUseList.get(0).getTotal());
          }else{
              user.put("newUseCount",0);
          }

          if(selectOrder != null && selectOrder.size() > 0){
              user.put("orderSale",selectOrder.get(0).getSale());
          }else{
              user.put("orderSale",0.00);
          }

          if(newSelectOrder != null && newSelectOrder.size() > 0){
              user.put("newOrderSale",newSelectOrder.get(0).getNewSale());
          }else{
              user.put("newOrderSale",0.00);
          }

          if(selectOrder != null && selectOrder.size() > 0){
              user.put("orderTotal",selectOrder.get(0).getOrderTotal());
          }else{
              user.put("orderTotal",0);
          }

          if(newSelectOrder != null && newSelectOrder.size() > 0){
              user.put("newOrderTotal",newSelectOrder.get(0).getNewOrderTotal());
          }else{
              user.put("newOrderTotal",0);
          }





          Map<String, Object> city = new HashMap<>();
          city.put("registerCountUser", userList.get(1).getRegisterCountUser());
          city.put("registerCount", userList.get(1).getTotal());
          if(newUserList != null && newUserList.size() > 1){
              city.put("newRegisterCount", newUserList.get(1).getTotal());
          }else{
              city.put("newRegisterCount", 0);
          }
          if(cityNum != null && cityNum.size() > 0){
              city.put("useCount",cityNum.get(0).getTotal());
          }else{
              city.put("useCount",0);
          }

          if(newCityNum != null && newCityNum.size() > 0){
              city.put("newUseCount",newCityNum.get(0).getTotal());
          }else{
              city.put("newUseCount",0);
          }
          if(cityOrder != null && cityOrder.size() > 0){
              city.put("orderSale",cityOrder.get(0).getSale());
          }else{
              city.put("orderSale",0.00);
          }
          if(newCityOrder != null && newCityOrder.size() > 0){
              city.put("newOrderSale",newCityOrder.get(0).getNewSale());
          }else{
              city.put("newOrderSale",0.00);
          }
          if(cityOrder != null && cityOrder.size() > 0){
              city.put("orderTotal",cityOrder.get(0).getOrderTotal());
          }else{
              city.put("orderTotal",0);
          }
          if(newCityOrder != null && newCityOrder.size() > 0){
              city.put("newOrderTotal",newCityOrder.get(0).getNewOrderTotal());
          }else{
              city.put("newOrderTotal",0);
          }




          list.add(user);
          list.add(city);


          Map<String, Object> pageMap = new HashMap<>();
          pageMap.put("rows", list);
          pageMap.put("total", 2);
          return pageMap;

      } catch (Exception e){
          log.error("获取数据失败!"+e);
          e.printStackTrace();
      }
        return "error";
    }

}
