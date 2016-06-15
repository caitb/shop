package com.masiis.shop.admin.controller.recommend;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.recommend.RecommendStatisticsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 推荐统计
 * Created by cai_tb on 16/6/15.
 */
@Controller
@RequestMapping("/recommendStatistics")
public class RecommendStatisticsController {

    private final static Log log = LogFactory.getLog(RecommendStatisticsController.class);

    @Resource
    private RecommendStatisticsService recommendStatisticsService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "recommend/recommend_statistics_list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(
                       HttpServletRequest request,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String sortOrder,
                       String code,
                       String realName
                       ){

        Map<String, Object> conditionMap = new HashMap<>();
        Map<String, Object> pageMap = null;
        try {
            if(StringUtils.isNotBlank(code)) conditionMap.put("code", code);
            if(StringUtils.isNotBlank(realName)) conditionMap.put("realName", URLDecoder.decode(realName, "UTF-8"));

            pageMap = recommendStatisticsService.list(pageNumber, pageSize, sortName, sortOrder, conditionMap);
        } catch (Exception e) {
            log.error("获取推荐统计列表失败![conditionMap="+conditionMap+"]"+e);
            e.printStackTrace();
        }

        return pageMap;
    }
}
