package com.masiis.shop.admin.controller.poster;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.poster.PosterService;
import com.masiis.shop.common.util.PropertiesUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/9/6.
 */

@Controller
@RequestMapping("/poster")
public class PosterController {

    private final static Log log = LogFactory.getLog(PosterController.class);
    @Resource
    private PosterService posterService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "poster/list";
    }

    /**
     * 获取海报列表
     * @param request
     * @param response
     * @param pageNumber
     * @param pageSize
     * @param sortName
     * @param sortOrder
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String sortOrder,
                       String realName) {

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> pageMap = null;
        try {
            if(realName != null){
                map.put("realName",realName);
            }
            pageMap = posterService.posterList(pageNumber, pageSize, sortName, sortOrder, map);
        } catch (Exception e) {
            log.error("获取海报列表失败![conditionMap="+map+"]"+e);
            e.printStackTrace();
        }
        return pageMap;
    }

    /**
     * 根据id删除海报信息
     * @param id
     * @return
     */
    @RequestMapping("/delete.do")
    @ResponseBody
    public void delete(Integer id){
         posterService.delete(id);
    }

}
