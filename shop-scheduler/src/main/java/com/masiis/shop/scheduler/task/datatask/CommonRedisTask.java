package com.masiis.shop.scheduler.task.datatask;

import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.constant.wx.WxConsSF;
import com.masiis.shop.scheduler.utils.wx.WxCredentialUtils;
import org.springframework.stereotype.Component;

/**
 * @Date 2016/5/9
 * @Auther lzh
 */
@Component
public class CommonRedisTask {

    public void refreshPFAccessToken(){
        WxCredentialUtils.getInstance()
                .refreshCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET);
    }

    public void refreshSFAccessToken(){
        WxCredentialUtils.getInstance()
                .refreshCredentialAccessToken(WxConsSF.APPID, WxConsSF.APPSECRET);
    }
}
