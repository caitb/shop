package com.masiis.shop.admin.controller.recommend;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.recommend.RecommendService;
import com.masiis.shop.dao.po.PfUserRecommenRelation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 推荐管理
 * Created by cai_tb on 16/6/15.
 */
@RequestMapping("/recommend")
@Controller
public class RecommendController {

    private final static Log log = LogFactory.getLog(RecommendController.class);

    @Resource
    private RecommendService recommendService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "recommend/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String sortOrder,
                       PfUserRecommenRelation pfUserRecommenRelation
                       ){

        Map<String, Object> pageMap = null;
        try {
            pageMap = recommendService.list(pageNumber, pageSize, sortName, sortOrder, pfUserRecommenRelation);
        } catch (Exception e) {
            log.error("获取推荐关系列表失败![pageNumber="+pageNumber+"][pageSize="+pageSize+"][sortName="+sortName+"][sortOrder="+sortOrder+"][pfUserRecommenRelation="+pfUserRecommenRelation+"]"+e);
            e.printStackTrace();
        }

        return pageMap;
    }
}
