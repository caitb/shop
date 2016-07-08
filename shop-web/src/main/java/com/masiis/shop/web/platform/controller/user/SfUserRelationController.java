package com.masiis.shop.web.platform.controller.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.user.SfSpokesAndFansInfo;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.platform.controller.base.BaseController;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * b端小铺代言人Controller
 * Created by wangbingjian on 2016/7/7.
 */
@Controller
@RequestMapping(value = "/distribution")
public class SfUserRelationController extends BaseController{
    private static final Logger logger = Logger.getLogger(SfUserRelationController.class);
    /**
     * 每页展示条数
     */
    private static final Integer pageSize = 10;

    @Autowired
    private SfShopService sfShopService;
    @Autowired
    private SfUserRelationService sfUserRelationService;

    /**
     * B端小铺代言人查询首页
     * @param request request
     * @return mv
     * @throws Exception
     */
    @RequestMapping(value = "/spokesManHome.shtml")
    public ModelAndView spokesManHome(HttpServletRequest request) throws Exception{
        logger.info("B端小铺代言人首页");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户未登陆");
        }
        Long userId = comUser.getId();
        logger.info("userId = " + userId);
        ModelAndView mv = new ModelAndView();
        SfShop sfShop = sfShopService.getSfShopById(userId);
        if (sfShop == null){
            logger.info("该用户："+userId+"，没有对应的小铺信息");
            mv.addObject("totalPage", 0);
            mv.addObject("currentPage", 0);
            mv.addObject("totalCount", 0);
        }else {
            Long shopId = sfShop.getId();
            logger.info("shopId = " + shopId);
            List<SfSpokesAndFansInfo> infos = sfUserRelationService.getAllSfSpokesManInfos(true, 1, pageSize, shopId);
            Integer totalCount = sfUserRelationService.getAllSfSpokesManNum(shopId);
            if (totalCount == 0 || infos == null || infos.size() == 0){
                logger.info("没有对应的代言人数据");
                mv.addObject("totalPage", 0);
                mv.addObject("currentPage", 0);
                mv.addObject("totalCount", 0);
            }else {
                Integer pageNums = totalCount%pageSize == 0 ? totalCount/pageSize : totalCount/pageSize + 1;
                mv.addObject("infos",infos);
                mv.addObject("totalPage", pageNums);
                mv.addObject("currentPage", 1);
                mv.addObject("totalCount", totalCount);
            }
        }
        mv.setViewName("platform/user/spokesManList");
        return mv;
    }

    /**
     * 通过代言人ID查询代言人信息
     * @param request   request
     * @param currentPage 查询第几页
     * @param queryType 查询方式   0：第一页 1：下一页 2：上一页
     * @param pageNums  总页数
     * @param ID    ID
     * @return
     */
    @RequestMapping(value = "/findByID.do")
    @ResponseBody
    public String findSpokesManByID(HttpServletRequest request,
                                    @RequestParam(value = "currentPage",required = true) Integer currentPage,
                                    @RequestParam(value = "queryType",required = true) Integer queryType,
                                    @RequestParam(value = "pageNums",required = true) Integer pageNums,
                                    @RequestParam(value = "ID") String ID)throws Exception{
        logger.info("B端小铺代言人首页");
        ComUser comUser = getComUser(request);
        JSONObject jsonObject = new JSONObject();
        if (comUser == null){
            jsonObject.put("isTrue",false);
            jsonObject.put("msg","用户未登陆");
            logger.info("result:"+jsonObject.toString());
            return jsonObject.toString();
        }
        Long userId = comUser.getId();
        logger.info("userId = " + userId);
        SfShop sfShop = sfShopService.getSfShopById(userId);
        List<SfSpokesAndFansInfo> infos = null;
        if (sfShop == null){
            jsonObject.put("isTrue",false);
            jsonObject.put("msg","没有对应的小铺信息");
            logger.info("result:"+jsonObject.toString());
            return jsonObject.toString();
        }
        Long shopId = sfShop.getId();
        logger.info("shopId = " + shopId);
        switch (queryType.intValue()){
            //查询第一页
            case 0 : {
                logger.info("查询第一页信息");
                if (StringUtils.isEmpty(ID) || ID.equals("NaN")){ID = null;}
                logger.info("ID = " + ID);
                Integer totalCount = sfUserRelationService.getSpokesManNumByID(shopId, ID);
                logger.info("totalCount = " + totalCount);
                if (totalCount == 0){
                    logger.info("没有对应的代言人信息");
                    jsonObject.put("isTrue",false);
                    jsonObject.put("msg","没有对应的代言人信息");
                    logger.info("result:"+jsonObject.toString());
                    return jsonObject.toString();
                }
                pageNums = totalCount%pageSize == 0 ? totalCount/pageSize : totalCount/pageSize + 1;
                infos = sfUserRelationService.getShopSpokesManByID(true, 1, pageSize, shopId, ID);
                jsonObject.put("totalPage", pageNums);
                jsonObject.put("currentPage", 1);
                jsonObject.put("totalCount", totalCount);
                jsonObject.put("infos",infos);
                break;
            }
            //查询下一页
            case 1 : {
                logger.info("查询下一页信息");
                if (StringUtils.isEmpty(ID) || ID.equals("NaN")){ID = null;}
                logger.info("ID = " + ID);
                logger.info("currentPage = " + currentPage);
                logger.info("pageNums = " + pageNums);
                if (currentPage + 1 > pageNums){
                    jsonObject.put("isTrue",false);
                    jsonObject.put("msg","已经是最后一页");
                    logger.info("result:"+jsonObject.toString());
                    return jsonObject.toString();
                }
                infos = sfUserRelationService.getShopSpokesManByID(true, currentPage + 1, pageSize, shopId, ID);
                jsonObject.put("totalPage", pageNums);
                jsonObject.put("currentPage", currentPage + 1);
                jsonObject.put("infos",infos);
                break;
            }
            //查询上一页
            case 2 : {
                logger.info("查询上一页信息");
                if (StringUtils.isEmpty(ID) || ID.equals("NaN")){ID = null;}
                logger.info("ID = " + ID);
                logger.info("currentPage = " + currentPage);
                logger.info("pageNums = " + pageNums);
                if (currentPage - 1 < 1){
                    jsonObject.put("isTrue",false);
                    jsonObject.put("msg","已经是第一页");
                    logger.info("result:"+jsonObject.toString());
                    return jsonObject.toString();
                }
                infos = sfUserRelationService.getShopSpokesManByID(true, currentPage - 1, pageSize, shopId, ID);
                jsonObject.put("totalPage", pageNums);
                jsonObject.put("currentPage", currentPage - 1);
                jsonObject.put("infos",infos);
                break;
            }
        }
        jsonObject.put("isTrue",true);
        logger.info("result:"+jsonObject.toString());
        return jsonObject.toString();
    }
}
