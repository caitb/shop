package com.masiis.shop.web.mall.controller.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.user.SfSpokenAndFansPageViewPo;
import com.masiis.shop.dao.beans.user.SfSpokesAndFansInfo;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 小铺分销关系Controller
 * Created by wangbingjian on 2016/7/5.
 */
@Controller
@RequestMapping(value = "/distribution")
public class SfUserRelationController extends BaseController {
    private static final Logger logger = Logger.getLogger(SfUserRelationController.class);

    @Autowired
    private SfUserRelationService sfUserRelationService;
    @Autowired
    private SfShopService sfShopService;

    /**
     * 每页显示条数
     */
    private static final Integer pageSize = 10;

    /**
     * 粉丝查询首页
     * @param request   request
     * @return  mv
     */
    @RequestMapping(value = "/fansHome.shtml")
    public ModelAndView fansHome(HttpServletRequest request) throws Exception{
        logger.info("粉丝查询首页");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户未登陆");
        }
        ModelAndView mv = new ModelAndView();
        //获取分享过的小铺列表
        List<SfShop> shops = sfShopService.getSharedShops(comUser.getId());
        mv.addObject("shops",shops);
        SfSpokenAndFansPageViewPo pageViewPo = sfUserRelationService.dealWithFansPageView(comUser.getId(), null, null, true, 1, pageSize);
        //用户分页使用，三级粉丝总数量
        Integer threeSum = pageViewPo.getFirstCount() + pageViewPo.getSecondCount() + pageViewPo.getThirdCount();
        logger.info("三级粉丝总数量：" + threeSum);
        //获取总页数
        Integer pageNums = threeSum%pageSize == 0 ? threeSum/pageSize : threeSum/pageSize + 1;
        logger.info("总页数：" + pageNums);
        mv.addObject("currentPage",1);
        mv.addObject("threeSum",threeSum);
        mv.addObject("pageNums",pageNums);
        mv.addObject("pageViewPo",pageViewPo);
        mv.setViewName("mall/user/sf_fans");
        return mv;
    }

    /**
     * ajax请求查询粉丝列表
     * @param request   request
     * @param shopId    小铺id
     * @param fansLevel 粉丝级别
     * @return  String
     */
    @RequestMapping(value = "/fans.do")
    @ResponseBody
    public String queryFansAjax(HttpServletRequest request,
                                @RequestParam(value = "shopId", required = true) Long shopId,
                                @RequestParam(value = "fansLevel", required = true) Integer fansLevel){
        logger.info("ajax请求查询粉丝列表");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户未登陆");
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            logger.info("shopId:"+shopId);
            logger.info("fansLevel:"+fansLevel);
            if (shopId.longValue() == 0){
                shopId = null;
            }
            if (fansLevel.intValue() == 0){
                fansLevel = null;
            }
            SfSpokenAndFansPageViewPo pageViewPo = sfUserRelationService.dealWithFansPageView(comUser.getId(), fansLevel, shopId, true, 1, pageSize);
            //用户分页使用，三级粉丝总数量
            Integer threeSum = 0;
            if (fansLevel == null){
                threeSum = pageViewPo.getFirstCount() + pageViewPo.getSecondCount() + pageViewPo.getThirdCount();
            }else {
                switch (fansLevel.intValue()){
                    case 1 : {
                        threeSum = pageViewPo.getFirstCount();
                        break;
                    }
                    case 2 : {
                        threeSum = pageViewPo.getSecondCount();
                        break;
                    }
                    case 3 : {
                        threeSum = pageViewPo.getThirdCount();
                        break;
                    }
                }
            }
            //获取总页数
            Integer pageNums = threeSum%pageSize == 0 ? threeSum/pageSize : threeSum/pageSize + 1;
            jsonObject.put("firstCount", pageViewPo.getFirstCount());
            jsonObject.put("secondCount", pageViewPo.getSecondCount());
            jsonObject.put("thirdCount", pageViewPo.getThirdCount());
            jsonObject.put("totalCount", pageViewPo.getTotalCount());
            jsonObject.put("threeSum", threeSum);
            logger.info("总页数：" + pageNums);
            jsonObject.put("totalPage", pageNums);
            jsonObject.put("currentPage", 1);
            jsonObject.put("infos", pageViewPo.getSfSpokesAndFansInfos());
            jsonArray.put(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("ajax查询粉丝列表异常");
            throw new BusinessException("系统异常");
        }
        logger.info(jsonArray.toString());
        return jsonArray.toString();
    }

    /**
     * ajax查询跟多粉丝列表
     * @param request       request
     * @param shopId        小铺id
     * @param fansLevel     粉丝级别
     * @param currentPage   当前页
     * @return
     */
    @RequestMapping(value = "/moreFans.do")
    @ResponseBody
    public String showMoreFans(HttpServletRequest request,
                               @RequestParam(value = "shopId", required = true) Long shopId,
                               @RequestParam(value = "fansLevel", required = true) Integer fansLevel,
                               @RequestParam(value = "currentPage", required = true) Integer currentPage){
        logger.info("ajax请求查询粉丝列表");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户未登陆");
        }
        JSONArray jsonArray = new JSONArray();
        try {
            logger.info("shopId = " + shopId);
            logger.info("fansLevel = " + fansLevel);
            logger.info("currentPage = " + currentPage);
            List<SfSpokesAndFansInfo> infos = sfUserRelationService.getSfFansInfos(true, currentPage.intValue() + 1, pageSize, comUser.getId(), fansLevel == 0?null:fansLevel, shopId == 0?null:shopId);
            JSONObject jsonObject = new JSONObject();
            if (infos == null || infos.size() == 0){
                jsonObject.put("currentPage", currentPage);
            }else {
                jsonObject.put("currentPage", currentPage.intValue() + 1);
            }
            jsonObject.put("infos",infos);
            jsonArray.put(jsonObject);
            logger.info(jsonArray.toString());
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("请求异常");
        }
        return jsonArray.toString();
    }

    /**
     * 小铺代言人展示列表首页
     * @param request
     * @return  mv
     */
    @RequestMapping(value = "/spokesManHome.shtml")
    public ModelAndView spokesManHome(HttpServletRequest request){
        logger.info("小铺代言人展示列表首页");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户未登陆");
        }
        Long userId = comUser.getId();
        logger.info("userId = " + userId);
        ModelAndView mv = new ModelAndView();
        //获取分享过的小铺列表
        List<SfShop> shops = sfShopService.getSharedShops(userId);
        mv.addObject("shops",shops);
        SfSpokenAndFansPageViewPo pageViewPo = sfUserRelationService.dealWithSpokesManPageView(comUser.getId(), null, null, true, 1, pageSize, 1);
        //用户分页使用，三级粉丝总数量
        Integer twoSum = pageViewPo.getFirstCount() + pageViewPo.getSecondCount();
        logger.info("两级代言人总数量：" + twoSum);
        //获取总页数
        Integer pageNums = twoSum%pageSize == 0 ? twoSum/pageSize : twoSum/pageSize + 1;
        logger.info("总页数：" + pageNums);
        mv.addObject("currentPage",1);
        mv.addObject("twoSum",twoSum);
        mv.addObject("pageNums",pageNums);
        mv.addObject("pageViewPo",pageViewPo);
        mv.setViewName("mall/user/sf_spokesman");
        return mv;
    }

    /**
     * ajax请求查询代言人列表
     * @param request   request
     * @param shopId    小铺id
     * @param spokeLevel 粉丝级别
     * @return  String
     */
    @RequestMapping(value = "/spokesMan.do")
    @ResponseBody
    public String querySpokesManAjax(HttpServletRequest request,
                                @RequestParam(value = "shopId", required = true) Long shopId,
                                @RequestParam(value = "spokeLevel", required = true) Integer spokeLevel){
        logger.info("ajax请求查询代言人列表");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户未登陆");
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            logger.info("shopId = "+shopId);
            logger.info("spokeLevel = "+spokeLevel);
            if (shopId.longValue() == 0){
                shopId = null;
            }
            if (spokeLevel.intValue() == 0){
                spokeLevel = null;
            }
            SfSpokenAndFansPageViewPo pageViewPo = sfUserRelationService.dealWithSpokesManPageView(comUser.getId(), spokeLevel, shopId, true, 1, pageSize, 1);
            //用户分页使用，两级代言人总数量
            Integer threeSum = 0;
            if (spokeLevel == null){
//                threeSum = pageViewPo.getFirstCount() + pageViewPo.getSecondCount() + pageViewPo.getThirdCount();
                threeSum = pageViewPo.getFirstCount() + pageViewPo.getSecondCount();
            }else {
                switch (spokeLevel.intValue()){
                    case 1 : {
                        threeSum = pageViewPo.getFirstCount();
                        break;
                    }
                    case 2 : {
                        threeSum = pageViewPo.getSecondCount();
                        break;
                    }
                    case 3 : {
                        threeSum = pageViewPo.getThirdCount();
                        break;
                    }
                }
            }
            //获取总页数
            Integer pageNums = threeSum%pageSize == 0 ? threeSum/pageSize : threeSum/pageSize + 1;
            jsonObject.put("firstCount", pageViewPo.getFirstCount());
            jsonObject.put("secondCount", pageViewPo.getSecondCount());
            jsonObject.put("thirdCount", pageViewPo.getThirdCount());
            jsonObject.put("totalCount", pageViewPo.getTotalCount());
            jsonObject.put("threeSum", threeSum);
            logger.info("总页数：" + pageNums);
            jsonObject.put("totalPage", pageNums);
            jsonObject.put("currentPage", 1);
            jsonObject.put("infos", pageViewPo.getSfSpokesAndFansInfos());
            jsonArray.put(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("ajax查询代言人列表异常");
            throw new BusinessException("系统异常");
        }
        logger.info(jsonArray.toString());
        return jsonArray.toString();
    }

    /**
     * ajax查询跟多粉丝列表
     * @param request       request
     * @param shopId        小铺id
     * @param fansLevel     粉丝级别
     * @param currentPage   当前页
     * @return
     */
    @RequestMapping(value = "/moreSpokesMan.do")
    @ResponseBody
    public String showMoreSpokesMan(HttpServletRequest request,
                               @RequestParam(value = "shopId", required = true) Long shopId,
                               @RequestParam(value = "fansLevel", required = true) Integer fansLevel,
                               @RequestParam(value = "currentPage", required = true) Integer currentPage){
        logger.info("查询更多代言人列表");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户未登陆");
        }
        logger.info("shopId = " + shopId);
        logger.info("fansLevel = " + fansLevel);
        JSONArray jsonArray = new JSONArray();
        if (shopId.longValue() == 0){
            shopId = null;
        }
        if (fansLevel.longValue() == 0){
            fansLevel = null;
        }
        try {
            List<SfSpokesAndFansInfo> infos = sfUserRelationService.getSfSpokesManInfos(true, currentPage.intValue() + 1, pageSize, comUser.getId(), fansLevel, shopId, 1);
            JSONObject jsonObject = new JSONObject();
            if (infos == null || infos.size() == 0){
                jsonObject.put("currentPage", currentPage);
            }else {
                jsonObject.put("currentPage", currentPage.intValue() + 1);
            }
            jsonObject.put("infos",infos);
            jsonArray.put(jsonObject);
            logger.info(jsonArray.toString());
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("请求异常");
        }
        return jsonArray.toString();
    }
}
