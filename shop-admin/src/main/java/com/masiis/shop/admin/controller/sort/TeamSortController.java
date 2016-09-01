package com.masiis.shop.admin.controller.sort;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.sort.BrandSortService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/16.
 */
@Controller
@RequestMapping("/teamsort")
public class TeamSortController {
    private final static Log log = LogFactory.getLog(BrandSortController.class);

    @Resource
    private BrandSortService brandSortService;

    @RequestMapping("/list.shtml")
    public String list() {
        return "teamsort/list";
    }

    @RequestMapping("/teamSortList.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String sortOrder) {
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> pageMap = null;
        try {
            pageMap = brandSortService.teamSortList(pageNumber, pageSize, sortName, sortOrder, map);
        } catch (Exception e) {
            log.error("获取teamSort列表失败![conditionMap="+map+"]"+e);
            e.printStackTrace();
        }
        return pageMap;
    }
}
