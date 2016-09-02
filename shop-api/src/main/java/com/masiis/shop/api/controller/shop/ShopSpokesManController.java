package com.masiis.shop.api.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.shop.SpokesMapRei;
import com.masiis.shop.api.bean.shop.SpokesMapReq;
import com.masiis.shop.api.bean.shop.SpokesMapRes;
import com.masiis.shop.api.bean.shop.SpokesMapRef;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.user.BfSpokesManDetailPo;
import com.masiis.shop.dao.beans.user.SfSpokesAndFansInfo;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hw on 2016/7/29.
 */
@Controller
@RequestMapping(value = "/shop/spokesman")
public class ShopSpokesManController extends BaseController {

    private final static Log log = LogFactory.getLog(ShopSpokesManController.class);

    /**
     * 每页展示条数
     */
    private static final Integer pageSize = 10;

    @Autowired
    private SfShopService sfShopService;
    @Autowired
    private SfUserRelationService sfUserRelationService;

    @RequestMapping("/spokesManHome.shtml")
    @ResponseBody
    @SignValid(paramType=SpokesMapReq.class)
    public SpokesMapRes spokesManHome(HttpServletRequest request, SpokesMapReq req, ComUser comUser){
        Long userId = comUser.getId();
        log.info("B端小铺代言人首页userId = " + userId);
        Integer cur = req.getPageNum();
        if(cur == null || cur < 0){
            throw new BusinessException("页码不正确");
        }
        SpokesMapRes res = new SpokesMapRes();
        try {
            SfShop sfShop = sfShopService.getSfShopByUserId(userId);
            if (sfShop == null){
                log.info("该用户："+ userId +"，没有对应的小铺信息");
                res.setHasQueryData(0);
            }else{
                Long shopId = sfShop.getId();
                //获取代言人数量
                Integer spokesManNum = sfUserRelationService.getFansOrSpokesMansNum(shopId, true, userId);
                //获取粉丝数量
                Integer fansNum = sfUserRelationService.getFansOrSpokesMansNum(shopId, false, userId);
                if(fansNum == 0){
                    res.setHasQueryData(0);
                }else{
                    res.setHasQueryData(1);
                    Integer pageNums = fansNum%pageSize == 0 ? fansNum/pageSize : fansNum/pageSize + 1;
                //  cur+1修改为cur最后一页不显示
                    if (cur > pageNums) {
                        throw new BusinessException("暂无更多");
                    }
                    res.setTotalPage(pageNums);
                    res.setTotalFansNum(fansNum);
                    res.setTotalSpokesManNum(spokesManNum);
                    List<SfSpokesAndFansInfo> infos = sfUserRelationService.getShopSpokesManByID(true, cur, pageSize, shopId, req.getID(), userId);
                    SfSpokesAndFansInfo info;
                    for (int i = 0; i < infos.size(); i++){
                        info = infos.get(i);
                        info.setFansNum(sfUserRelationService.getFansNumByUserId(info.getUserId(), shopId));
                        info.setSpokesManNum(sfUserRelationService.getSpokesManNumByUserId(info.getUserId(), shopId));
                    }
                    res.setInfos(infos);
                }
            }
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            log.error("加载B端小铺代言人首页数据失败![comUser=" + comUser+  "]");
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(e.getMessage());
        }
        log.info(JSONObject.toJSON(res));
        return res;
    }
    /**
     * Created by wl 2016/8/8
     * 通过代言人ID查询代言人信息af
     * @param request   request
     * @return
     */
    @RequestMapping(value = "/findByID.do")
    @ResponseBody
    @SignValid(paramType=SpokesMapRef.class)
    public SpokesMapRes findSpokesManByID(HttpServletRequest request, SpokesMapRef ref, ComUser comUser){
        log.info("B端小铺代言人首页");
        if (comUser == null){
            throw new BusinessException("用户未登陆");
        }
        Long userId = comUser.getId();
        log.info("userId = " + userId);
        SpokesMapRes res = new SpokesMapRes();
        try {
        SfShop sfShop = sfShopService.getSfShopByUserId(userId);
        List<SfSpokesAndFansInfo> infos = null;
        if (sfShop == null){
            log.info("该用户："+ userId +"，没有对应的小铺信息");
            res.setHasQueryData(0);
        }else{
        Long shopId = sfShop.getId();
        log.info("shopId = " + shopId);
        String ID = ref.getID();
        if (StringUtils.isEmpty(ID) || ID.equals("NaN")){ID = null;}
        log.info("ID = " + ID);
        // queryType 查询方式   0：第一页 1：下一页 2：上一页
            Integer queryType = ref.getQueryType();
            log.info("queryType = " + queryType);
            int cur = queryType.intValue();
        switch (cur ){
            //查询第一页
            case 0 : {
                log.info("查询第一页信息");
                log.info("ID = " + ID);
                Integer totalCount = sfUserRelationService.getSpokesManNumByID(shopId, ID, false, userId);
                log.info("totalCount = " + totalCount);
                if (totalCount == 0){
                    log.info("没有对应的代言人信息");
                    res.setHasQueryData(0);
                }else {
                    res.setHasQueryData(1);
                    Integer pageNums = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
                    infos = sfUserRelationService.getShopSpokesManByID(true, 1, pageSize, shopId, ID, userId);
                    res.setTotalPage(pageNums);
                    res.setPageSize(1);
                    res.setInfos(infos);
                }
                break;
            }
            //查询下一页
            case 1 : {
                log.info("查询下一页信息");
                Integer totalCount = sfUserRelationService.getSpokesManNumByID(shopId, ID, false, userId);
                Integer pageNums = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
                if (cur  + 1 > pageNums.intValue()){
                    res.setHasQueryData(0);
                }else {
                    res.setHasQueryData(1);
                infos = sfUserRelationService.getShopSpokesManByID(true, cur  + 1, pageSize, shopId, ID, userId);
                res.setTotalPage(pageNums);
                res.setInfos(infos);
                }
                break;
            }
            //查询上一页
            case 2 : {
                log.info("查询上一页信息");
                if (cur   - 1 < 1){
                    res.setHasQueryData(0);
                }
                infos = sfUserRelationService.getShopSpokesManByID(true, cur  - 1, pageSize, shopId, ID, userId);
                res.setInfos(infos);
                break;
            }
        }
        SfSpokesAndFansInfo info;
        for (int i = 0; i < infos.size(); i++){
            info = infos.get(i);
            info.setFansNum(sfUserRelationService.getFansNumByUserId(info.getUserId(), shopId));
            info.setSpokesManNum(sfUserRelationService.getSpokesManNumByUserId(info.getUserId(), shopId));
            infos.set(i,info);
        }
            res.setInfos(infos);
        }
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            log.error("加载B端小铺代言人首页数据失败![comUser=" + comUser+  "]");
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(e.getMessage());
        }
        return res;
    }

    /**
     * 查看代言人详情
     * @param request   request
     * @return  rea
     * @throws Exception
     */
    @RequestMapping(value = "/spokesManDetail.shtml")
    @ResponseBody
    @SignValid(paramType=SpokesMapRei.class)
    public SpokesMapRes querySpokesManDetail(HttpServletRequest request, SpokesMapRei rei, ComUser comUser){

        log.info("查看代言人详情");
        if (comUser == null){
            throw new BusinessException("用户未登陆");
        }
        Long userId = comUser.getId();
        log.info("B端小铺代言人首页userId = " + userId);
        SpokesMapRes res = new SpokesMapRes();
        SfShop sfShop = sfShopService.getSfShopByUserId(userId);
        log.info("shopId = " + sfShop.getId());
        if (sfShop == null){
            log.info("该用户："+ userId +"，没有对应的小铺信息");
            res.setHasQueryData(0);
        }else {
            res.setHasQueryData(1);
            BfSpokesManDetailPo detailPo = null;
            try {
                Long showUserId = rei.getShowUserId();
                //showUserId   查看人Id
                log.info("showUserId = " + showUserId);
                detailPo =  sfUserRelationService.getSpokesManDetail(showUserId, sfShop.getId());
                res.setDetailPo(detailPo);
                res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            } catch (Exception e) {
                e.printStackTrace();
                log.info(e.getMessage());
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(e.getMessage());
            }
        }
        return res;
    }
}
