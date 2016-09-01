package com.masiis.shop.admin.controller.product;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.product.BannerService;
import com.masiis.shop.dao.po.PbBanner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/8/13.
 */
@RequestMapping("/banner")
@Controller
public class BannerController {

    private final static Log log = LogFactory.getLog(BannerController.class);

    @Resource
    private BannerService bannerService;

    @RequestMapping("/bannerList.shtml")
    public String bannerList(){
        return "product/bannerList";
    }

    /**
     * banner查询
     * @param request
     * @param response
     * @param pageNumber
     * @param pageSize
     * @param sortName
     * @param sortOrder
     * @return
     */
    @RequestMapping("/bannerList.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String sortOrder) {
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> pageMap = null;
        try {
            pageMap = bannerService.bannerList(pageNumber, pageSize, sortName, sortOrder, map);
        } catch (Exception e) {
            log.error("获取banner列表失败![conditionMap="+map+"]"+e);
            e.printStackTrace();
        }
        return pageMap;

    }

    @RequestMapping("/addBanner.shtml")
    public String addBanner(){
        return "product/addBanner";
    }

    /**
     * 添加banner
     */
    @RequestMapping("/addBanner.do")
    @ResponseBody
    public Object add(PbBanner pbBanner){
     bannerService.addBanner(pbBanner);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", "success");
        resultMap.put("msg", "banner添加成功");
        return resultMap;
    }

    /**
     * 根据id删除一条数据
     * @param id
     */
    @RequestMapping("/deleteBanner.do")
    @ResponseBody
    public void deleteBanner(Integer id){
        bannerService.deleteBanner(id);
    }

    @RequestMapping("/editBanner.shtml")
    public String editBannerById(Integer id, Model model){
        model.addAttribute("banner", bannerService.selectById(id));
        return "product/editBanner";
    }

    /**
     *根据id 编辑banner
     */
    @RequestMapping("/editBanner.do")
    @ResponseBody
    public Object editBanner(PbBanner pbBanner){
        bannerService.editBanner(pbBanner);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", "success");
        resultMap.put("msg", "banner编辑成功");
        return resultMap;
    }
}
