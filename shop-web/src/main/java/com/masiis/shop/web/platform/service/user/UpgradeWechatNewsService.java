package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BOrderUpgradeDetail;
import com.masiis.shop.dao.beans.user.upgrade.UpGradeInfoPo;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderPayment;
import com.masiis.shop.dao.po.PfBorderRecommenReward;
import com.masiis.shop.web.platform.service.order.PfBorderRecommenRewardService;
import com.masiis.shop.web.platform.utils.wx.WxPFNoticeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private PfBorderRecommenRewardService pfBorderRecommenRewardService;
    

    /**
     * 升级订单支付成功后发送微信
     * @param pfBorder
     * @param pfBorderPayment
     * @return
     */
    public Boolean upgradeOrderPaySuccessSendWXNotice(PfBorder pfBorder, PfBorderPayment pfBorderPayment,BOrderUpgradeDetail upgradeDetail){
        String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/deliveryBorderDetils.html?upgradeId=" + upgradeDetail.getUpgradeNoticeId();
        //给升级人发微信
        ComUser comUser = comUserService.getUserById(pfBorder.getUserId());
        ComUser newComUser = comUserService.getUserById(pfBorder.getUserPid());
        ComUser oldUser = comUserService.getUserById(upgradeDetail.getOldPUserId());
        String[] param = new String[4];
        param[0] = pfBorderPayment.getAmount().intValue()+"";
        logger.info("支付金额---------"+pfBorderPayment.getAmount().intValue());
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
        }else{
            //上级变化
            logger.info("发送微信通知-----------上级变化");
            //给原上级发微信
            String[] _param = new String[1];
            _param[0] = comUser.getRealName();
            WxPFNoticeUtils.getInstance().upgradeApplyResultNotice(oldUser,_param,url,true);
            //给新的上级发
            WxPFNoticeUtils.getInstance().partnerJoinByUpgradeNotice(newComUser,comUser,DateUtil.Date2String(new Date(),DateUtil.CHINESEALL_DATE_FMT),url);
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
        return WxPFNoticeUtils.getInstance().upgradeApplySubmitNotice(comUser,param,PropertiesUtils.getStringValue("web.domain.name.address") + url);
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
        calendar.add(Calendar.DAY_OF_YEAR,2);//日期加10天
        param[4] = DateUtil.Date2String(calendar.getTime(),DateUtil.CHINESEALL_DATE_FMT);
        logger.info("跳转url============"+PropertiesUtils.getStringValue("web.domain.name.address") + url);
        return WxPFNoticeUtils.getInstance().subLineUpgradeApplyNotice(comUser,param,PropertiesUtils.getStringValue("web.domain.name.address") + url);
    }
}
