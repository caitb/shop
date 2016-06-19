package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BOrderUpgradeDetail;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.utils.wx.WxPFNoticeUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 代理升级微信消息通知Service
 * Created by wangbingjian on 2016/6/18.
 */
@Service
public class UpgradeWechatNewsService {

    /**
     * 升级通知单提交通知
     * @param comUser
     * @param upgradeDetail
     * @return
     */
    public Boolean upgradeApplySubmitNotice(ComUser comUser,BOrderUpgradeDetail upgradeDetail){
        String[] param = new String[4];
        param[0]=upgradeDetail.getApplyAgentLevelName();
        param[1]=upgradeDetail.getCurrentAgentLevel()+"";
        param[2]=upgradeDetail.getApplyAgentLevel()+"";
        param[3]= DateUtil.Date2String(new Date(),DateUtil.CHINESEALL_DATE_FMT);
        String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/deliveryBorderDetils.html?upgradeId=" + upgradeDetail.getUpgradeNoticeId();
        //发送
       return WxPFNoticeUtils.getInstance().upgradeApplySubmitNotice(comUser,param,url);
    }
}
