package com.masiis.shop.api.controller.message;

import com.masiis.shop.api.bean.message.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.EmojiUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.message.PfMessageCenterDetail;
import com.masiis.shop.dao.beans.message.PfMessageToNewBean;
import com.masiis.shop.dao.beans.user.upgrade.UserSkuAgent;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.message.PfMessageContentService;
import com.masiis.shop.web.platform.service.message.PfMessageSrRelationService;
import com.masiis.shop.web.platform.service.message.PfSysMessageService;
import com.masiis.shop.web.platform.service.user.PfUserOrganizationService;
import com.masiis.shop.web.platform.service.user.PfUserSkuService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理消息controller
 *
 * @Date 2016/8/1
 * @Author lzh
 */
@Controller
@RequestMapping("/message")
public class PfMessageController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfMessageContentService pfMessageContentService;
    @Resource
    private PfMessageSrRelationService pfMessageSrRelationService;
    @Resource
    private UserService userService;
    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private SkuService skuService;
    @Resource
    private PfSysMessageService pfSysMessageService;
    @Resource
    private PfUserOrganizationService pfUserOrganizationService;

    @RequestMapping("/centerlist")
    @ResponseBody
    @SignValid(paramType = PfCenterListReq.class)
    public PfCenterListRes centerList(HttpServletRequest request, PfCenterListReq req, ComUser user){
        PfCenterListRes res = new PfCenterListRes();
        List<PfMessageCenterDetail> resData = new ArrayList<>();
        int pageSize = 10;
        Integer cur = req.getPageNum();
        try{
            if(user == null){
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR_MSG);
                throw new BusinessException("怎么能没用户呢！");
            }
            if(cur == null || cur < 0){
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR_MSG);
                throw new BusinessException("当前页码不正确");
            }

            Integer num = pfUserSkuService.getGTLastLevelNumByUserId(user.getId());
            // 是否是代理商
            res.setIsAgent(user.getIsAgent());
            res.setIsLaseLevel(num.intValue() > 0 ? 0 : 1);

            Integer tcount = 0;
            if(cur.intValue() == 0) {
                // 查询系统消息
                PfMessageCenterDetail detailSys = pfSysMessageService.querySysNotSeeGeneral(user.getId());
                if(StringUtils.isNotBlank(detailSys.getLatestMessage())){
                    detailSys.setFromUserId(0 + "");
                    detailSys.setFromUserName("系统消息");
                    detailSys.setHeadUrl(PropertiesUtils.getStringValue("api.domain.name.address") + "/static/images/sys_notice_img.jpg");
                    resData.add(detailSys);
                    tcount++;
                }
                // 查询我的消息
                PfMessageContent content = pfMessageContentService.queryContentLatestByUserId(user.getId(), 2);
                PfMessageCenterDetail detailOfMy = new PfMessageCenterDetail();
                if (content != null && StringUtils.isNotBlank(content.getContent())) {
                    detailOfMy.setNotSeeNum(0);
                    detailOfMy.setFromUserId(user.getId() + "");
                    detailOfMy.setFromUserName("我");
                    detailOfMy.setLatestMessage(content.getContent());
                    detailOfMy.setHeadUrl(user.getWxHeadImg());
                    resData.add(detailOfMy);
                    tcount++;
                }
                res.setmList(resData);
            }

            int totalNums = pfMessageSrRelationService.queryNumsToUser(user, 2);
            if(totalNums == 0){
                res.setCurrentPage(cur);
                res.setPageSize(pageSize);

                if(cur.intValue() == 0){
                    res.setHasQueryData(0);
                    res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                    res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
                    if(tcount.intValue() > 0){
                        res.setHasQueryData(1);
                        res.setTotalCount(tcount);
                        res.setTotalPage(1);
                        res.setIsLast(1);
                    }
                    return res;
                } else {
                    res.setHasQueryData(0);
                    res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                    res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
                    return res;
                }
            }
            // 第一页页码是0
            int pageNums = totalNums/pageSize + (totalNums%pageSize == 0 ? 0 : 1);
            // 检测页码是否超出总页数
            if(cur + 1 > pageNums){
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR_MSG);
                throw new BusinessException("下一页码不正确");
            }
            // 检测是否是最后一页
            if(cur + 1 == pageNums){
                // 最后一页
                res.setIsLast(1);
            }else{
                // 非最后一页
                res.setIsLast(0);
            }
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            res.setCurrentPage(cur);
            res.setHasQueryData(1);
            res.setPageSize(pageSize);
            res.setTotalPage(pageNums);
            res.setTotalCount(totalNums);
            int start = cur * pageSize;
            // 查询要展现的消息数据
            List<PfMessageCenterDetail> am = pfMessageSrRelationService.queryFromUsersByToUserWithPaging(user, 2, start, pageSize);
            resData.addAll(am);

            res.setmList(resData);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            String resCode = res.getResCode();
            if(StringUtils.isBlank(resCode)){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }

        return res;
    }

    @RequestMapping("/detaillist")
    @ResponseBody
    @SignValid(paramType = PfMessageDetailListReq.class)
    public PfMessageDetailListRes detailList(HttpServletRequest request, PfMessageDetailListReq req, ComUser user){
        PfMessageDetailListRes res = new PfMessageDetailListRes();
        List<PfMessageContent> resData = null;
        int pageSize = 10;
        String fromUserName = "";
        Long tUserId = null;
        Long fUserId = null;
        Long uid = req.getUid();
        Integer cur = req.getPageNum();
        try {
            if (user == null) {
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR_MSG);
                throw new BusinessException("怎么能没用户呢！");
            }
            if (cur == null || cur < 0) {
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR_MSG);
                throw new BusinessException("当前页码不正确");
            }
            if (uid == null) {
                log.error("消息来源用户为空");
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR_MSG);
                throw new BusinessException("消息来源用户为空");
            }
            ComUser fUser = null;
            if(uid.intValue() > 0) {
                fUser = userService.getUserById(uid);
                if (fUser == null) {
                    log.error("消息来源用户找不到");
                    res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR);
                    res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR_MSG);
                    throw new BusinessException("消息来源用户找不到");
                }
            }
            fUserId = uid;
            if(uid.longValue() == 0l){
                // 查看系统消息
                fromUserName = "系统消息";
                tUserId = 0l;
            } else if(uid.longValue() == user.getId().longValue()){
                // 查看自己发出的消息
                fromUserName = "我";
                tUserId = null;
            } else {
                fromUserName = fUser.getRealName();
                tUserId = user.getId();
            }
            int totalNums = 0;
            if(tUserId == null){
                // 自己发出的消息
                totalNums = pfMessageContentService.queryNumsByUserIdAndType(fUserId, 2);
            } else if(tUserId.longValue() == 0l) {
                // 系统消息
                totalNums = pfSysMessageService.queryNumsByUserIdAndType(user.getId(), 1);
            } else {
                totalNums = pfMessageSrRelationService.queryNumsFromUserAndToUser(tUserId, fUserId, 2);
            }
            if(totalNums == 0){
                res.setHasQueryData(0);
                res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
                return res;
            }
            // 第一页页码是0
            int pageNums = totalNums/pageSize + (totalNums%pageSize == 0 ? 0 : 1);
            // 检测页码是否超出总页数
            if(cur + 1 > pageNums){
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR_MSG);
                throw new BusinessException("下一页码不正确");
            }
            // 检测是否是最后一页
            if(cur + 1 == pageNums){
                // 最后一页
                res.setIsLast(1);
            }else{
                // 非最后一页
                res.setIsLast(0);
            }
            int start = cur * pageSize;
            // 查询要展现的消息数据
            if(tUserId == null){
                resData = pfMessageContentService.queryContentByUserIdAndTypeWithPaging(fUserId, 2, start, pageSize);
            } else if(tUserId.longValue() == 0l) {
                // 查询系统消息
                resData = pfSysMessageService.queryContentByUserIdAndTypeWithPaging(user.getId(), 1, start, pageSize);
            } else {
                resData = pfMessageSrRelationService.queryDetailByFromUserAndToUserWithPaging(tUserId, fUserId, 2, start, pageSize);
            }

            res.setCurrentPage(cur);
            res.setHasQueryData(1);
            res.setPageSize(pageSize);
            res.setTotalCount(pageNums);
            res.setFromUserName(fromUserName);
            res.setTotalCount(totalNums);
            res.setmList(resData);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            String resCode = res.getResCode();
            if(StringUtils.isBlank(resCode)){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }

        return res;
    }


    @RequestMapping("/tonew")
    @ResponseBody
    @SignValid(paramType = PfMessageToNewReq.class)
    public PfMessageToNewRes toNewPageData(HttpServletRequest request, PfMessageToNewReq req, ComUser user){
        PfMessageToNewRes res = new PfMessageToNewRes();
        try{
            if(user == null){
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR_MSG);
                throw new BusinessException("怎么能没用户呢");
            }
            if(user.getIsAgent().intValue() == 0){
                // 还不是代理商
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_NOT_AGENT);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_NOT_AGENT_MSG);
                return res;
            }

            Integer childAgentNum = pfUserSkuService.getNumsByUserPid(user.getId());
            List<UserSkuAgent> skus = pfUserSkuService.getCurrentAgentLevel(user.getId());
            String baseName = "全部";

            // 按家族查询直接下级人数
            List<PfMessageToNewBean> infos = pfUserOrganizationService.getNumsGroupByOrganizationAndUserId(user.getId());
            for(PfMessageToNewBean info:infos){
                info.setoUrl(PropertiesUtils.getStringValue("organization_img_url") + info.getoUrl());
            }

            res.setTos(infos);
            res.setBaseName(baseName);
            res.setChildAgentNum(childAgentNum);
            res.setSkus(skus);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }

        return res;
    }


    @RequestMapping("/newmessage")
    @ResponseBody
    @SignValid(paramType = PfMessageCreateReq.class)
    public PfMessageCreateRes newMessage(HttpServletRequest request, PfMessageCreateReq req,
                             ComUser user){
        PfMessageCreateRes res = new PfMessageCreateRes();
        try{
            if(StringUtils.isBlank(req.getMessage())){
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_NOTENOUGH);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_NOTENOUGH_MSG);
                throw new BusinessException("消息内容为空");
            }

            String url = null;
            if(req.getUrlAppType() == null || req.getUrlAppType() < 0){
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_PARAM_ERROR_MSG);
                throw new BusinessException("请求跳转类型不正确");
            }

            Integer sendType = req.getSendType();
            if(sendType == null || sendType.intValue() < 0){
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_TO_USER_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_TO_USER_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_MESSAGE_TO_USER_ERROR_MSG);
            }

            List<Long> relations = null;
            Integer messageType = 2;
            String remark = "";
            if(sendType.intValue() == 0){
                relations = pfUserSkuService.getChildUserIdByUserPid(user.getId());
                remark = "直接下级群发";
            } else {
                PfUserOrganization pfUserOrganization = pfUserOrganizationService.getById(sendType);
                if(pfUserOrganization == null){
                    res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_TO_USER_ERROR);
                    res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_TO_USER_ERROR_MSG);
                    throw new BusinessException(SysResCodeCons.RES_CODE_MESSAGE_TO_USER_ERROR_MSG);
                } else {
                    relations = pfUserSkuService
                            .getChildsByUserIdAndBrandId(user.getId(), pfUserOrganization.getBrandId());
                }
                remark = "按家族直接下级群发";
            }

            if(relations == null || relations.size() <= 0){
                res.setResCode(SysResCodeCons.RES_CODE_MESSAGE_HAS_NO_CHILD);
                res.setResMsg(SysResCodeCons.RES_CODE_MESSAGE_HAS_NO_CHILD_MSG);
                throw new BusinessException("暂无直接下级");
            }

            PfMessageContent content = pfMessageContentService.createMessageByAppType(user.getId(),
                    EmojiUtils.encodeEmojiStr(req.getMessage()), messageType, remark, url,
                    req.getUrlAppType(), req.getUrlAppParam());
            pfMessageContentService.insert(content);

            for(Long toUser:relations){
                PfMessageSrRelation srRelation = pfMessageSrRelationService.createRelationByContent(content, toUser);
                pfMessageSrRelationService.insert(srRelation);
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

}
