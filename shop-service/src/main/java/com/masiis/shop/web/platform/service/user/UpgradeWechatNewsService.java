package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.platform.BOrderType;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BOrderUpgradeDetail;
import com.masiis.shop.dao.beans.user.upgrade.UpGradeInfoPo;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.order.PfUserUpgradeNoticeService;
import com.masiis.shop.web.common.utils.wx.WxPFNoticeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * 代理升级微信消息通知Service
 * Created by wangbingjian on 2016/6/18.
 */
@Service
public class UpgradeWechatNewsService {
    private Logger logger = Logger.getLogger(UpgradeWechatNewsService.class);
    @Resource
    private UserService comUserService;
    @Resource
    private PfUserUpgradeNoticeService userUpgradeNoticeService;

    /**
     * 升级订单支付成功后，进入排单发送微信
     * @param pfBorder
     * @param pfBorderPayment
     * @param upgradeDetail
     * @return
     */
    public Boolean upgradeOrderPaySuccssEntryWaiting(PfBorder pfBorder,PfBorderPayment pfBorderPayment,BOrderUpgradeDetail upgradeDetail){
        //1.发送升级成功提醒
        upgradeOrderPaySuccessSendWXNotice(pfBorder,pfBorderPayment,upgradeDetail);
        //2发送排单提醒
        //2.1给自己发
        logger.info("升级订单给自己发--------userId----"+pfBorder.getUserId());
        ComUser comUser = comUserService.getUserById(pfBorder.getUserId());
        ComUser pComUser = comUserService.getUserById(pfBorder.getUserPid());
        String[] _param = new String[5];
        _param[0] = upgradeDetail.getSkuName();
        _param[1] = "￥"+pfBorder.getPayAmount().toString();
        _param[2] = upgradeDetail.getQuantity()+"";
        _param[3] = BOrderType.UPGRADE.getDesc();
        _param[4] = BOrderStatus.MPS.getDesc();
        WxPFNoticeUtils.getInstance().orderInQueue(comUser,_param);
        //2.2给上级发
        logger.info("升级订单给上级发--------pUserId----"+pfBorder.getUserPid());
        String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/product/user/" + pfBorder.getUserPid();
        String[] param = new String[5];
        param[0] = upgradeDetail.getSkuName();
        param[1] = "￥"+pfBorder.getPayAmount().toString();
        param[2] = upgradeDetail.getQuantity()+"";
        param[3] = BOrderType.UPGRADE.getDesc();
        param[4] = BOrderStatus.MPS.getDesc();
        WxPFNoticeUtils.getInstance().dealWithOrderInQueueByUp(pComUser,param,url);
        //3给下级申请的发推送

        return true;
    }

    /**
     * 升级订单支付成功后，没有进入排单发送微信
     * @param pfBorder
     * @param pfBorderPayment
     * @return
     */
    public Boolean upgradeOrderPaySuccessSendWXNotice(PfBorder pfBorder, PfBorderPayment pfBorderPayment,BOrderUpgradeDetail upgradeDetail){
        logger.info("------订单id-------"+pfBorder.getId());
        logger.info("------通知单id-------"+upgradeDetail.getUpgradeNoticeId());
        String newPuserUrl = PropertiesUtils.getStringValue("web.domain.name.address") + "/upgrade/upgradeInfoNewUp.shtml?upgradeId=" + upgradeDetail.getUpgradeNoticeId();
        String oldPuserUrl = PropertiesUtils.getStringValue("web.domain.name.address") + "/upgrade/upgradeInfo.shtml?upgradeId=" + upgradeDetail.getUpgradeNoticeId();
        //给升级人发微信
        ComUser comUser = comUserService.getUserById(pfBorder.getUserId());
        ComUser newComUser = comUserService.getUserById(pfBorder.getUserPid());
        ComUser oldUser = comUserService.getUserById(upgradeDetail.getOldPUserId());
        String[] param = new String[4];
        param[0] = "￥"+pfBorderPayment.getAmount().toString();
        param[1] = pfBorderPayment.getPayTypeName();
        param[2] = "升级"+upgradeDetail.getApplyAgentLevelName();
        param[3] = DateUtil.Date2String(new Date(),DateUtil.CHINESEALL_DATE_FMT);
        WxPFNoticeUtils.getInstance().upgradePaySuccessNotice(comUser,param);
        //给上级发送微信
        logger.info("发送微信通知---原上级-------"+upgradeDetail.getOldPUserId());
        logger.info("发送微信通知---新上级-------"+pfBorder.getUserPid());
        if (pfBorder.getUserPid().equals(upgradeDetail.getOldPUserId())){
            //上级没变化
            logger.info("发送微信通知-----------上级没变化");
            String[] _param = new String[3];
            _param[0] = comUser.getRealName();
            _param[1] = upgradeDetail.getApplyAgentLevelName()+"";
            _param[2] = DateUtil.Date2String(new Date(),DateUtil.CHINESEALL_DATE_FMT);
            WxPFNoticeUtils.getInstance().upgradeResultNoticeUpLine(newComUser,_param,oldPuserUrl);
        }else{
            //上级变化
            logger.info("发送微信通知-----------上级变化");
            //给原上级发微信
            String[] _param = new String[1];
            _param[0] = comUser.getRealName();
            WxPFNoticeUtils.getInstance().upgradeApplyResultNotice(oldUser,_param,newPuserUrl,true);
            //给新的上级发
            WxPFNoticeUtils.getInstance().partnerJoinByUpgradeNotice(newComUser,comUser,DateUtil.Date2String(new Date(),DateUtil.CHINESEALL_DATE_FMT),newPuserUrl);
        }
        return true;
    }

    /**
     * 升级通知单提交通知
     * @param comUser
     * @param upGradeInfoPo
     * @return
     */
    public Boolean upgradeApplySubmitNotice(ComUser comUser, UpGradeInfoPo upGradeInfoPo, String url){
        logger.info("--------------------------您的升级申请已提交，请耐心等待审核。--------------------------");
        String[] param = new String[4];
        param[0]=upGradeInfoPo.getApplyName();
        param[1]=upGradeInfoPo.getOrgAgentName();
        param[2]=upGradeInfoPo.getWishAgentName();
        param[3]= DateUtil.Date2String(new Date(),DateUtil.CHINESEALL_DATE_FMT);
        logger.info("跳转url============"+PropertiesUtils.getStringValue("web.domain.name.address") + url);
        try {
            return WxPFNoticeUtils.getInstance().upgradeApplySubmitNotice(comUser,param,PropertiesUtils.getStringValue("web.domain.name.address") + url);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 下级代理申请升级通知
     * @param comUser
     * @param upGradeInfoPo
     * @param url
     * @return
     */
    public boolean subLineUpgradeApplyNotice(ComUser comUser,UpGradeInfoPo upGradeInfoPo, String url){
        logger.info("------------------------------您有一个代理申请升级----------------------------------");
        String[] param = new String[5];
        param[0] = upGradeInfoPo.getApplyName();
        param[1] = upGradeInfoPo.getOrgAgentName();
        param[2] = upGradeInfoPo.getWishAgentName();
        param[3] = DateUtil.Date2String(new Date(),DateUtil.CHINESEALL_DATE_FMT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR,2);//日期加2天
        param[4] = DateUtil.Date2String(calendar.getTime(),DateUtil.CHINESEALL_DATE_FMT);
        logger.info("跳转url============"+PropertiesUtils.getStringValue("web.domain.name.address") + url);
        try {
            return WxPFNoticeUtils.getInstance().subLineUpgradeApplyNotice(comUser,param,PropertiesUtils.getStringValue("web.domain.name.address") + url);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 代理暂不升级 微信消息通知
     * @param comUser           暂不升级user
     * @param upGradeInfoPo     po
     * @param url               url
     * @return
     */
    public boolean upgradeApplyResultNotice(ComUser comUser, UpGradeInfoPo upGradeInfoPo, String url){
        logger.info("--------------------------暂不升级消息提醒-----------------------------");
        String [] param = new String[1];
        param[0] = upGradeInfoPo.getApplyName();
        logger.info("跳转url============"+PropertiesUtils.getStringValue("web.domain.name.address") + url);
        try {
            return WxPFNoticeUtils.getInstance().upgradeApplyResultNotice(comUser, param, PropertiesUtils.getStringValue("web.domain.name.address") + url, false);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 升级审核结果通知
     * @param comUser       user
     * @param upGradeInfoPo po
     * @param url           url
     * @return
     */
    public boolean upgradeApplyAuditPassNotice(ComUser comUser, UpGradeInfoPo upGradeInfoPo, String url){
        logger.info("------------------------代理升级申请审核结果通知---------------------------");
        String [] param = new String[5];
        param[0] = upGradeInfoPo.getApplyName();
        param[1] = upGradeInfoPo.getOrgAgentName();
        param[2] = upGradeInfoPo.getWishAgentName();
        param[3] = DateUtil.Date2String(new Date(),DateUtil.CHINESEALL_DATE_FMT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR,2);//日期加2天
        param[4] = DateUtil.Date2String(calendar.getTime(),DateUtil.CHINESEALL_DATE_FMT);
        logger.info("跳转url============"+PropertiesUtils.getStringValue("web.domain.name.address") + url);
        try {
            return WxPFNoticeUtils.getInstance().upgradeApplyAuditPassNotice(comUser, param, PropertiesUtils.getStringValue("web.domain.name.address") + url);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
