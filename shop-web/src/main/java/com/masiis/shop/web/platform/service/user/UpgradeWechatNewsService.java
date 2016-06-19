package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BOrderUpgradeDetail;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderPayment;
import com.masiis.shop.web.platform.utils.wx.WxPFNoticeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 代理升级微信消息通知Service
 * Created by wangbingjian on 2016/6/18.
 */
@Service
public class UpgradeWechatNewsService {

    @Resource
    private UserService comUserService;

    /**
     * 插入订单border成功后发送微信
     * @param oldPUserId
     * @param newPUserId
     * @param userId
     * @param upgradeDetail
     * @return
     */
    public Boolean insertUpgradeOrderSendWXNotice(Long oldPUserId,Long newPUserId,Long userId,BOrderUpgradeDetail upgradeDetail){
        ComUser oldComUser = comUserService.getUserById(oldPUserId);
        ComUser newComUser = comUserService.getUserById(newPUserId);
        ComUser comUser = comUserService.getUserById(userId);
        String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/deliveryBorderDetils.html?upgradeId=" + upgradeDetail.getUpgradeNoticeId();
        if (upgradeDetail.getUpStatus()==1){
            //原上级暂时不升级，给原上级发微信
            String[] param = new String[1];
            param[0] = comUser.getRealName();
            WxPFNoticeUtils.getInstance().upgradeApplyResultNotice(oldComUser,param,url,true);
        }
        return false;
    }

    /**
     * 升级订单支付成功后发送微信
     * @param pfBorder
     * @param pfBorderPayment
     * @param userId
     * @return
     */
    public Boolean upgradeOrderPaySuccessSendWXNotice(PfBorder pfBorder, PfBorderPayment pfBorderPayment, Long userId,BOrderUpgradeDetail upgradeDetail){
        String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/deliveryBorderDetils.html?upgradeId=" + upgradeDetail.getUpgradeNoticeId();
        //给升级人发微信
        ComUser comUser = comUserService.getUserById(userId);
        ComUser newComUser = comUserService.getUserById(pfBorder.getUserPid());
        ComUser oldUser = comUserService.getUserById(upgradeDetail.getOldPUserId());
        String[] param = new String[4];
        param[0] = pfBorder.getPayAmount().intValue()+"";
        param[1] = pfBorderPayment.getPayTypeName();
        param[2] = "升级"+upgradeDetail.getApplyAgentLevelName();
        param[3] = DateUtil.Date2String(new Date(),DateUtil.CHINESEALL_DATE_FMT);
        WxPFNoticeUtils.getInstance().upgradePaySuccessNotice(comUser,param);
        //给上级发送微信
        if (pfBorder.getUserPid().equals(upgradeDetail.getOldPUserId())){
            //上级没变化
            WxPFNoticeUtils.getInstance().upgradeApplyResultNotice(oldUser,param,url,true);
        }else{
            //上级变化
            //给原上级发微信
            String[] _param = new String[4];
            _param[0] = comUser.getRealName();
            WxPFNoticeUtils.getInstance().upgradeApplyResultNotice(oldUser,param,url,true);
            //给新的上级发
            WxPFNoticeUtils.getInstance().partnerJoinByUpgradeNotice(newComUser,comUser,DateUtil.Date2String(new Date(),DateUtil.CHINESEALL_DATE_FMT),url);
        }
        return true;
    }

    /**
     * 升级通知单提交通知
     * @param comUser
     * @param upgradeDetail
     * @return
     */
    public Boolean upgradeApplySubmitNotice(ComUser comUser,BOrderUpgradeDetail upgradeDetail, String url){
        String[] param = new String[4];
        param[0]=upgradeDetail.getApplyAgentLevelName();
        param[1]=upgradeDetail.getCurrentAgentLevel()+"";
        param[2]=upgradeDetail.getApplyAgentLevel()+"";
        param[3]= DateUtil.Date2String(new Date(),DateUtil.CHINESEALL_DATE_FMT);
        return WxPFNoticeUtils.getInstance().upgradeApplySubmitNotice(comUser,param,PropertiesUtils.getStringValue("web.domain.name.address") + url);
    }

    /**
     * 下级代理申请升级通知
     * @param comUser
     * @param upgradeDetail
     * @param url
     * @return
     */
    public boolean subLineUpgradeApplyNotice(ComUser comUser,BOrderUpgradeDetail upgradeDetail, String url){
        String[] param = new String[4];
        param[0]=upgradeDetail.getApplyAgentLevelName();
        param[1]=upgradeDetail.getCurrentAgentLevel()+"";
        param[2]=upgradeDetail.getApplyAgentLevel()+"";
        param[3]= DateUtil.Date2String(new Date(),DateUtil.CHINESEALL_DATE_FMT);
        return WxPFNoticeUtils.getInstance().subLineUpgradeApplyNotice(comUser,param,PropertiesUtils.getStringValue("web.domain.name.address") + url);
    }
}
