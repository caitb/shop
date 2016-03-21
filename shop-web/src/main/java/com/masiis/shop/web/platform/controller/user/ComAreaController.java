package com.masiis.shop.web.platform.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.dao.po.ComArea;
import com.masiis.shop.web.platform.service.user.ComAreaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by hzzh on 2016/3/20.
 */
@Controller
@RequestMapping(value = "comArea")
public class ComAreaController {

    @Resource
    private ComAreaService comAreaService;

    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "queryAllProvince.do")
    @ResponseBody
    public String  queryAllProvince(HttpServletRequest request, HttpServletResponse response)  {

        try {
            List<ComArea> comAreas = comAreaService.queryAllProvince();
            String returnJson = objectMapper.writeValueAsString(comAreas);;
            return returnJson;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "queryCityByProviceId.do")
    @ResponseBody
    public String queryCityByProviceId(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "proviceId")Integer proviceId){

        try {
            List<ComArea> comAreas = comAreaService.queryCityByProviceId(proviceId);
            String returnJson = objectMapper.writeValueAsString(comAreas);;
            return returnJson;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "queryCountyByCityId.do")
    @ResponseBody
    public String queryCountyByCityId(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "cityId")Integer cityId){

        try {
            List<ComArea> comAreas = comAreaService.queryCountyByCityId(cityId);
            String returnJson = objectMapper.writeValueAsString(comAreas);;
            return returnJson;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
