package com.masiis.shop.web.mall.utils.wx;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.beans.wx.notice.*;
import com.masiis.shop.common.constant.wx.WxConsSF;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComWxUser;
import com.masiis.shop.web.mall.service.user.WxUserService;
import com.masiis.shop.web.mall.utils.ApplicationContextUtil;
import org.apache.log4j.Logger;

/**
 * @Date 2016/4/27
 * @Auther lzh
 */
public class WxSFNoticeUtils {
    private static Logger log = Logger.getLogger(WxSFNoticeUtils.class);

    private WxUserService wxUserService = (WxUserService) ApplicationContextUtil.getBean("wxUserService");

    private WxSFNoticeUtils() {}
    private static class Holder {
        private static final WxSFNoticeUtils INSTANCE = new WxSFNoticeUtils();
    }

    // 单例懒加载
    public static WxSFNoticeUtils getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 调用模板发消息
     *
     * @param noPay
     * @return
     */
    private Boolean wxNotice(String accessToken, WxNoticeReq noPay) {
        String url = WxConsSF.URL_WX_NOTICE + "?access_token=" + accessToken;
        String result = HttpClientUtils.httpPost(url, JSONObject.toJSONString(noPay));
        WxNoticeRes res = JSONObject.parseObject(result, WxNoticeRes.class);
        if ("0".equals(res.getErrcode())) {
            return true;
        }
        log.error("发送模板消息失败");
        return false;
    }

    /**
     * 小铺订单下单成功
     *
     * @param user  通知的用户
     * @param params    (第一个,订单号(ordercode); 第二个,支付金额; 第三个,支付方式)
     * @return
     */
    public Boolean orderCreateNotice(ComUser user, String[] params){
        WxSFOrderCreateNotice orderCreate = new WxSFOrderCreateNotice();
        WxNoticeReq<WxSFOrderCreateNotice> req = new WxNoticeReq<>(orderCreate);

        orderCreate.setFirst(new WxNoticeDataItem("您好，您的订单支付成功", null));
        orderCreate.setKeyword1(new WxNoticeDataItem(params[0], null));
        orderCreate.setKeyword2(new WxNoticeDataItem(params[1], null));
        orderCreate.setKeyword3(new WxNoticeDataItem(params[2], null));
        orderCreate.setRemark(new WxNoticeDataItem("您的订单支付成功，我们将会尽快安排发货。", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setTemplate_id(WxConsSF.WX_SF_TM_ID_ORDER_CREATE);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsSF.APPID, WxConsSF.APPSECRET), req);
    }

    /**
     * 小铺订单发货提醒
     *
     * @param user  通知的用户
     * @param params    (第一个,订单号(ordercode); 第二个,快递公司; 第三个,快递单号)
     * @param orderUrl  详情url
     * @return
     */
    public Boolean orderShipNotice(ComUser user, String[] params, String orderUrl){
        WxSFOrderShipNotice orderSHip = new WxSFOrderShipNotice();
        WxNoticeReq<WxSFOrderShipNotice> req = new WxNoticeReq<>(orderSHip);

        orderSHip.setFirst(new WxNoticeDataItem("您好，您的订单已发货", null));
        orderSHip.setKeyword1(new WxNoticeDataItem(params[0], null));
        orderSHip.setKeyword2(new WxNoticeDataItem(params[1], null));
        orderSHip.setKeyword3(new WxNoticeDataItem(params[2], null));
        orderSHip.setRemark(new WxNoticeDataItem("点击查看订单详情。", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setUrl(orderUrl);
        req.setTemplate_id(WxConsSF.WX_SF_TM_ID_ORDER_SHIP);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsSF.APPID, WxConsSF.APPSECRET), req);
    }

    /**
     * 小铺订单确认收货
     *
     * @param user
     * @param params (第一个,订单号(ordercode); 第二个,商品名称; 第三个,下单时间;
     *               第四个,发货时间; 第五个,确认收货时间)
     * @return
     */
    public Boolean orderConfirmNotice(ComUser user, String[] params){
        WxSFOrderConfirmNotice confirm = new WxSFOrderConfirmNotice();
        WxNoticeReq<WxSFOrderConfirmNotice> req = new WxNoticeReq<>(confirm);

        confirm.setFirst(new WxNoticeDataItem("亲，您在我们商城买的宝贝已经确认收货。", null));
        confirm.setKeyword1(new WxNoticeDataItem(params[0], null));
        confirm.setKeyword2(new WxNoticeDataItem(params[1], null));
        confirm.setKeyword3(new WxNoticeDataItem(params[2], null));
        confirm.setKeyword4(new WxNoticeDataItem(params[3], null));
        confirm.setKeyword5(new WxNoticeDataItem(params[4], null));
        confirm.setRemark(new WxNoticeDataItem("感谢您的支持与厚爱。", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setTemplate_id(WxConsSF.WX_SF_TM_ID_ORDER_CONFIRM);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsSF.APPID, WxConsSF.APPSECRET), req);
    }

    /**
     * 小铺佣金提醒
     *
     * @param user
     * @param params
     * @return
     */
    public Boolean profitInNotice(ComUser user, String[] params){
        WxSFProfitInNotice profit = new WxSFProfitInNotice();
        WxNoticeReq<WxSFProfitInNotice> req = new WxNoticeReq<>(profit);

        profit.setFirst(new WxNoticeDataItem("恭喜您，获得了一笔新的佣金，您可以在“个人中心-我的佣金”中查看详细佣金信息。", null));
        profit.setKeyword1(new WxNoticeDataItem(params[0], null));
        profit.setKeyword2(new WxNoticeDataItem(params[1], null));
        profit.setRemark(new WxNoticeDataItem("感谢您的使用。", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setTemplate_id(WxConsSF.WX_SF_TM_ID_PROFIT_IN);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsSF.APPID, WxConsSF.APPSECRET), req);
    }

    /**
     * 提现申请通知
     *
     * @param user
     * @param params    (第一个,提现金额; 第二个,提现时间; 第三个,提现状态; 第四个,拒绝原因(如果申请成功,则不填))
     * @param isSuccess 提现申请是否成功
     * @return
     */
    public Boolean extractApplyNotice(ComUser user, String[] params, boolean isSuccess){
        WxSFExtractApplyNotice apply = new WxSFExtractApplyNotice();
        WxNoticeReq<WxSFExtractApplyNotice> req = new WxNoticeReq<>(apply);

        if(isSuccess){
            apply.setFirst(new WxNoticeDataItem("您好，您的提现申请已经提交", null));
            apply.setKeyword1(new WxNoticeDataItem(params[0], null));
            apply.setKeyword2(new WxNoticeDataItem(params[1], null));
            apply.setKeyword3(new WxNoticeDataItem(params[2], null));
            apply.setRemark(new WxNoticeDataItem("审核结果会在2个工作日内完成，请耐心等待", null));
        } else {
            apply.setFirst(new WxNoticeDataItem("您好，您的提现申请被拒绝了", null));
            apply.setKeyword1(new WxNoticeDataItem(params[0], null));
            apply.setKeyword2(new WxNoticeDataItem(params[1], null));
            apply.setKeyword3(new WxNoticeDataItem(params[2], null));
            apply.setRemark(new WxNoticeDataItem("拒绝原因，" + params[3] + "，如有问题请联系客服", null));
        }

        req.setTouser(getOpenIdByComUser(user));
        req.setTemplate_id(WxConsSF.WX_SF_TM_ID_EXTRACT_APPLY);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsSF.APPID, WxConsSF.APPSECRET), req);
    }

    /**
     * 提现结果通知
     *
     * @param user
     * @param params    (第一个,提现用户;
     *                  第二个,提现金额;
     *                  第三个,提现账户(建设银行：6222*****6768或者微信钱包);
     *                  第四个,处理时间)
     * @return
     */
    public Boolean extractResultNotice(ComUser user, String[] params){
        WxSFExtractResultNotice result = new WxSFExtractResultNotice();
        WxNoticeReq<WxSFExtractResultNotice> req = new WxNoticeReq<>(result);

        result.setFirst(new WxNoticeDataItem("提现结果通知", null));
        result.setKeyword1(new WxNoticeDataItem(params[0], null));
        result.setKeyword2(new WxNoticeDataItem(params[1], null));
        result.setKeyword3(new WxNoticeDataItem(params[2], null));
        result.setKeyword4(new WxNoticeDataItem(params[3], null));
        result.setRemark(new WxNoticeDataItem("您好，您的提现申请已经通过审核，汇款将会在1个工作日内完成，请注意查收", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setTemplate_id(WxConsSF.WX_SF_TM_ID_EXTRACT_APPLY);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsSF.APPID, WxConsSF.APPSECRET), req);
    }

    private String getOpenIdByComUser(ComUser user) {
        if (user == null) {
            log.info("user为空");
            throw new BusinessException("user为空");
        }
        ComWxUser wxUser = wxUserService.getUserByUnionidAndAppid(user.getWxUnionid(), WxConsSF.APPID);
        if (wxUser == null) {
            log.info("wxUser为空");
            throw new BusinessException("wxUser为空");
        }
        return wxUser.getOpenid();
    }
}
