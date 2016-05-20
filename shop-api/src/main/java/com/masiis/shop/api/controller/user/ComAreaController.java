package com.masiis.shop.api.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.api.bean.user.ComAreaReq;
import com.masiis.shop.api.bean.user.ComAreaRes;
import com.masiis.shop.api.bean.user.ComUserAddressReq;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.service.user.ComAreaService;
import com.masiis.shop.dao.po.ComArea;
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
    @SignValid(paramType = ComAreaReq.class)
    public ComAreaRes  queryAllProvince(HttpServletRequest request, HttpServletResponse response)  {

        ComAreaRes comAreaRes = new ComAreaRes();
        try {
            List<ComArea> comAreas = comAreaService.queryAllProvince();
            comAreaRes.setComAreas(comAreas);
        }catch (Exception e){
            e.printStackTrace();
        }
        return comAreaRes;
    }

    @RequestMapping(value = "queryCityByProviceId.do")
    @ResponseBody
    @SignValid(paramType = ComAreaReq.class)
    public ComAreaRes queryCityByProviceId(HttpServletRequest request, ComAreaReq comAreaReq){
        ComAreaRes comAreaRes = new ComAreaRes();
        try {
            List<ComArea> comAreas = comAreaService.queryCityByProviceId(comAreaReq.getPid());
            comAreaRes.setComAreas(comAreas);
        }catch (Exception e){
            e.printStackTrace();
        }
        return comAreaRes;
    }

    @RequestMapping(value = "queryCountyByCityId.do")
    @ResponseBody
    @SignValid(paramType = ComAreaReq.class)
    public ComAreaRes queryCountyByCityId(HttpServletRequest request,
                                      ComAreaReq comAreaReq){
        ComAreaRes comAreaRes = new ComAreaRes();
        try {
            List<ComArea> comAreas = comAreaService.queryCountyByCityId(comAreaReq.getPid());
            comAreaRes.setComAreas(comAreas);
        }catch (Exception e){
            e.printStackTrace();
        }
        return comAreaRes;
    }
}
