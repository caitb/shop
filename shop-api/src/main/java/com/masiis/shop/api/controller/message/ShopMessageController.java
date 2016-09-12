package com.masiis.shop.api.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.message.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.EmojiUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.message.PfMessageCenterDetail;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.service.message.SfMessageContentService;
import com.masiis.shop.web.mall.service.message.SfMessageSrRelationService;
import com.masiis.shop.web.mall.service.message.SfSysMessageService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.shop.SfShopSkuService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 店铺消息controller
 *
 * @Date 2016/8/1
 * @Author lzh
 */
@Controller
@RequestMapping("/shopmessage")
public class ShopMessageController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfMessageContentService sfMessageContentService;
    @Resource
    private SfMessageSrRelationService sfMessageSrRelationService;
    @Resource
    private SfShopService sfShopService;
    @Resource
    private SfUserRelationService sfUserRelationService;
    @Resource
    private SfShopSkuService sfShopSkuService;
    @Resource
    private SfSysMessageService sfSysMessageService;

    @RequestMapping("/centerlist")
    @ResponseBody
    @SignValid(paramType = ShopMessageCenterListReq.class)
    public ShopMessageCenterListRes centerList(HttpServletRequest request, ShopMessageCenterListReq req, ComUser user){
        ShopMessageCenterListRes res = new ShopMessageCenterListRes();
        List<PfMessageCenterDetail> resData = new ArrayList<>();
        try {
            // 查询系统消息
            PfMessageCenterDetail detailSys = sfSysMessageService.querySysNotSeeGeneral(user.getId());
            if(StringUtils.isNotBlank(detailSys.getLatestMessage())){
                detailSys.setFromUserId(0 + "");
                detailSys.setFromUserName("系统消息");
                detailSys.setHeadUrl(PropertiesUtils.getStringValue("api.domain.name.address") + "/static/images/sys_notice_img.jpg");
                resData.add(detailSys);
            };

            List<SfMessageContent> contents = sfMessageContentService.queryContentFromUser(user, 0, 1);
            if(contents != null && contents.size() > 0){
                PfMessageCenterDetail detail = new PfMessageCenterDetail();
                detail.setNotSeeNum(0);
                detail.setFromUserId(user.getId() + "");
                detail.setFromUserName("我");
                detail.setLatestMessage(contents.get(0).getContent());
                detail.setHeadUrl(user.getWxHeadImg());
                resData.add(detail);
            }
            res.setIsAgent(user.getIsAgent());
            res.setmList(resData);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }

        return res;
    }

    /**
     * 分页查询我发出的群发消息
     *
     * @param request
     * @return
     */
    @RequestMapping("/detaillist")
    @ResponseBody
    @SignValid(paramType = ShopMessageDetailReq.class)
    public ShopMessageDetailRes messageList(HttpServletRequest request, ShopMessageDetailReq req, ComUser user){
        int pageSize = 10;
        ShopMessageDetailRes res = new ShopMessageDetailRes();
        List<SfMessageContent> resData = null;
        Integer cur = req.getPageNum();
        Long fUserId = req.getfUserId();
        try {
            if (cur == null || cur < 0) {
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR_MSG);
                throw new BusinessException("当前页码不正确");
            }
            int totalNums = 0;
            if(fUserId == null ||
                    (fUserId.longValue() != 0l && fUserId.longValue() != user.getId().longValue())){
                // 来源用户不正确
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_FROM_USER_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_FROM_USER_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_MESSAGE_FROM_USER_ERROR_MSG);
            }else if(fUserId.longValue() == 0l){
                // 系统消息
                totalNums = sfSysMessageService.queryNumsByUserIdAndType(user.getId(), 1);
            } else {
                totalNums = sfMessageContentService.queryNumsFromUser(user);
            }
            if(totalNums == 0){
                res.setHasQueryData(0);
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_HAS_NO_DATA);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_HAS_NO_DATA_MSG);
                return res;
            }
            // 第一页页码是0
            int pageNums = totalNums/pageSize + (totalNums%pageSize == 0 ? 0 : 1);
            // 检测页码是否超出总页数
            if(cur + 1 > pageNums){
                throw new BusinessException("下一页码不正确");
            }
            // 检测是否是最后一页
            if(cur + 1 == pageNums) {
                res.setIsLast(1);
            } else {
                res.setIsLast(0);
            }

            int start = cur * pageSize;
            // 查询要展现的消息数据
            if(fUserId.longValue() == 0l){
                // 系统消息
                resData = sfSysMessageService.queryContentByUserIdAndTypeWithPaging(user.getId(), 1, start, pageSize);;
            } else {
                resData = sfMessageContentService.queryContentFromUser(user, start, pageSize);
            }
            res.setmList(resData);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            res.setCurrentPage(cur);
            res.setHasQueryData(1);
            res.setPageSize(pageSize);
            res.setTotalPage(pageNums);
            res.setTotalCount(totalNums);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }
        return res;
    }

    @RequestMapping("/tonew")
    @ResponseBody
    @SignValid(paramType = ShopMessageToNewReq.class)
    public ShopMessageToNewRes toNewMessageData(HttpServletRequest request, ShopMessageToNewReq req, ComUser user){
        ShopMessageToNewRes res = new ShopMessageToNewRes();
        try {
            // 查询是否是代理商
            SfShop shop = sfShopService.getSfShopByUserId(user.getId());
            if(shop == null){
                // 还不是代理商
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_NOT_AGENT);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_NOT_AGENT_MSG);
                return res;
            }
            // 店铺粉丝数量
            Integer fansNum = sfUserRelationService.getFansNumsByShopId(shop.getId(), user.getId());
            // 店铺代言人数量
            Integer spokeManNum = sfUserRelationService.getSfSpokesManNumByShopId(shop.getId(), user.getId());
            // 查询代理商品skuId
            List<SfShopSku> shopSkus = sfShopSkuService.findSfShopOnSaleSkuByShopId(shop.getId());

            res.setFansNum(fansNum);
            res.setSpokeManNum(spokeManNum);
            res.setSkus(shopSkus);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }
        return res;
    }

    @RequestMapping("/newmessage")
    @ResponseBody
    @SignValid(paramType = ShopMessageCreateReq.class)
    public ShopMessageCreateRes newMessage(HttpServletRequest request, ShopMessageCreateReq req, ComUser user){
        ShopMessageCreateRes res = new ShopMessageCreateRes();
        String message = req.getMessage();
        Integer sendType = req.getSendType();
        Integer urlType = req.getUrlAppType();
        try{
            if(StringUtils.isBlank(message)){
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR_MSG);
                throw new BusinessException("消息内容为空");
            }
            if(sendType.intValue() != 1 && sendType.intValue() != 2){
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR_MSG);
                throw new BusinessException("发送类型不正确");
            }

            SfShopSku sku = null;
            String url = null;
            SfShop shop = sfShopService.getSfShopByUserId(user.getId());

            Map<String, Object> param = null;
            if(urlType.intValue() == -1){
                // 未选择

            } else if (urlType.intValue() == 1) {
                // 店铺首页
            } else if (urlType.intValue() == 2) {
                // 商品详情页
            } else {
                // 类型错误
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR_MSG);
                throw new BusinessException("跳转类型错误");
            }

            List<SfUserRelation> relations = null;
            Integer messageType = 0;
            String remark = "";
            if(sendType.intValue() == 1){
                messageType = 3;
                remark = "店铺群发粉丝";
                relations = sfUserRelationService.findAllFansRelationByShopId(shop.getId(), user.getId());
            } else if(sendType.intValue() == 2){
                messageType = 4;
                remark = "店铺群发代言人";
                relations = sfUserRelationService.findAllSpokeManRelationByShopId(shop.getId(), user.getId());
            }

            SfMessageContent content = sfMessageContentService.createMessageByShopAndAppType(shop,
                    EmojiUtils.encodeEmojiStr(message), messageType, remark, url, urlType,
                    req.getUrlAppParam());
            sfMessageContentService.insert(content);

            for(SfUserRelation relation:relations){
                SfMessageSrRelation sr = new SfMessageSrRelation();
                sr.setIsSee(0);
                sr.setCreateTime(new Date());
                sr.setRemark(remark);
                sr.setFromUser(user.getId());
                sr.setToUser(relation.getUserId());
                sr.setSfMessageContentId(content.getId());
                sr.setStatus(1);
                sfMessageSrRelationService.insert(sr);
            }

            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }

        return res;
    }

    public static void main(String... args){
        Map<String, Object> param = JSONObject.parseObject("{}", Map.class);
    }
}

