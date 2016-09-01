package com.masiis.shop.api.controller.family;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.api.bean.family.MerchantsReq;
import com.masiis.shop.api.bean.family.MerchantsRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.api.service.MerchantsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 招商展示页面
 * Created by cai_tb on 16/8/10.
 */
@Controller
@RequestMapping("/merchants")
public class MerchantsController {

    private final static Log log = LogFactory.getLog(MerchantsController.class);

    @Resource
    private MerchantsService merchantsService;

    @RequestMapping("/show")
    @ResponseBody
    @SignValid(paramType = MerchantsReq.class)
    public MerchantsRes show(HttpServletRequest request, MerchantsReq merchantsReq, ComUser comUser) {
        MerchantsRes merchantsRes = new MerchantsRes();

        try {
            Map<String, Object> dataMap = merchantsService.show(merchantsReq.getBrandId(), merchantsReq.getUserPid(), comUser.getId(), merchantsReq.getSourceType());
            dataMap.put("sourceType", merchantsReq.getSourceType());
            merchantsRes.setDataMap(dataMap);
            merchantsRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            merchantsRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);

            log.info("家族招商-------------sourceType: " + merchantsReq.getSourceType());
        } catch (Exception e) {
            merchantsRes.setResCode(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
            merchantsRes.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR_MSG);

            log.error("获取招商展示数据失败![merchantsReq=" + merchantsReq + "]" + e);
            e.printStackTrace();
        }

        return merchantsRes;
    }
}
