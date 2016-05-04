package com.masiis.shop.web.mall.utils.wx;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.beans.wx.notice.*;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComWxUser;
import com.masiis.shop.web.mall.service.user.WxUserService;
import com.masiis.shop.web.mall.utils.ApplicationContextUtil;
import org.apache.log4j.Logger;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Date:2016/4/21
 * @auth:lzh
 */
public class WxPFNoticeUtils {
    private static Logger log = Logger.getLogger(WxPFNoticeUtils.class);
    private NumberFormat rmbFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
    private SimpleDateFormat timeFormart = new SimpleDateFormat("yyyy年MM月dd日 H:m:s");
    private WxUserService wxUserService = (WxUserService) ApplicationContextUtil.getBean("wxUserService");


    private static class Holder {
        private static final WxPFNoticeUtils INSTANCE = new WxPFNoticeUtils();
    }

    private WxPFNoticeUtils() {
    }

    // 单例懒加载
    public static final WxPFNoticeUtils getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 调用模板发消息
     *
     * @param noPay
     * @return
     */
    public Boolean wxNotice(String accessToken, WxNoticeReq noPay) {
        String url = WxConsPF.URL_WX_NOTICE + "?access_token=" + accessToken;
        String result = HttpClientUtils.httpPost(url, JSONObject.toJSONString(noPay));
        WxNoticeRes res = JSONObject.parseObject(result, WxNoticeRes.class);
        if ("0".equals(res.getErrcode())) {
            return true;
        }
        log.error("发送模板消息失败");
        return false;
    }

    /**
     * 合伙人申请成功提示
     *
     * @param user
     * @param params 参数数组,(第一个,支付金额;第二个,支付方式;第三个,支付详情;第四个,支付时间)
     * @return
     */
    public Boolean partnerApplySuccessNotice(ComUser user, String[] params) {
        WxPFPartnerApplyOK applyOK = new WxPFPartnerApplyOK();
        WxNoticeReq<WxPFPartnerApplyOK> req = new WxNoticeReq<>(applyOK);

        applyOK.setFirst(new WxNoticeDataItem("恭喜您，合伙人申请支付成功", null));
        applyOK.setRemark(new WxNoticeDataItem("恭喜您已成为合伙人。", null));
        applyOK.setKeyword1(new WxNoticeDataItem(params[0], null));
        applyOK.setKeyword2(new WxNoticeDataItem(params[1], null));
        applyOK.setKeyword3(new WxNoticeDataItem(params[2], null));
        applyOK.setKeyword4(new WxNoticeDataItem(params[3], null));

        req.setTouser(getOpenIdByComUser(user));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_PTNER_APPLY_OK);

        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 下线加入通知
     *
     * @param pUser
     * @param user
     * @param joinTime 下级代理加入时间
     * @return
     */
    public Boolean partnerJoinNotice(ComUser pUser, ComUser user, String joinTime) {
        WxPFPartnerJoin join = new WxPFPartnerJoin();
        WxNoticeReq<WxPFPartnerJoin> req = new WxNoticeReq<>(join);

        join.setFirst(new WxNoticeDataItem("下线代理加入通知", null));
        join.setRemark(new WxNoticeDataItem("麦链合伙人，感谢有您!", null));
        join.setKeyword1(new WxNoticeDataItem(user.getMobile(), null));
        join.setKeyword2(new WxNoticeDataItem(joinTime, null));
        join.setKeyword3(new WxNoticeDataItem(user.getWxNkName(), null));

        req.setTouser(getOpenIdByComUser(pUser));
        // 调用下线加入模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_PTNER_JOIN_NOTICE);

        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 实名认证通知
     *
     * @param user
     * @param isSuccess 是否认证成功
     * @param url       成功,继续申请合伙人链接;失败,重新实名认证链接
     * @return
     */
    public Boolean partnerRealNameAuthNotice(ComUser user, boolean isSuccess, String url) {
        WxPFPartnerRealNameAuth auth = new WxPFPartnerRealNameAuth();
        WxNoticeReq<WxPFPartnerRealNameAuth> req = new WxNoticeReq<>(auth);

        auth.setFirst(new WxNoticeDataItem("实名认证", null));
        if (isSuccess) {
            auth.setRemark(new WxNoticeDataItem("您的实名认证已经通过，点击继续申请合伙人!", null));
            auth.setKeyword1(new WxNoticeDataItem("恭喜您认证通过", null));
            auth.setKeyword2(new WxNoticeDataItem("认证通过", null));
        } else {
            auth.setRemark(new WxNoticeDataItem("认证失败，点击重新认证。", null));
            auth.setKeyword1(new WxNoticeDataItem("非常抱歉，由于您提供的身份信息有误，实名认证失败，请重新认证。", null));
            auth.setKeyword2(new WxNoticeDataItem("认证失败", null));
        }

        req.setTouser(getOpenIdByComUser(user));
        req.setUrl(url);
        // 调用下实名认证结果通知模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_AUTH_NOTICE);

        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 实名认证申请提交通知
     *
     * @param user
     * @param params    (第一个,手机号; 第二个,日期)
     * @return
     */
    public Boolean partnerRealNameSubmit(ComUser user, String[] params) {
        WxPFPartnerRealNameSubmit sub = new WxPFPartnerRealNameSubmit();
        WxNoticeReq<WxPFPartnerRealNameSubmit> req = new WxNoticeReq<>(sub);

        sub.setFirst(new WxNoticeDataItem("实名认证已经提交", null));
        sub.setRemark(new WxNoticeDataItem("您的实名信息已经提交，审核将会在1个工作日完成。请耐心等待审核结果!", null));
        sub.setKeyword1(new WxNoticeDataItem(user.getWxNkName(), null));
        sub.setKeyword2(new WxNoticeDataItem(params[0], null));
        sub.setKeyword3(new WxNoticeDataItem(params[1], null));

        req.setTouser(getOpenIdByComUser(user));
        // 调用实名认证申请提交模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_RM_SUBMIT);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 代理订单发货通知
     *
     * @param user     通知对象
     * @param params   (1,商品名称;2,代理等级;3,订单编号(不是id);4,快递公司;5,快递单号)
     * @param orderUrl 订单详情url
     * @return
     */
    public Boolean orderShippedNotice(ComUser user, String[] params, String orderUrl) {
        WxPFOrderShipped shipped = new WxPFOrderShipped();
        WxNoticeReq<WxPFOrderShipped> req = new WxNoticeReq<>(shipped);

        shipped.setFirst(new WxNoticeDataItem("您好，您的" + params[0] + params[1] + "订单已发货", null));
        shipped.setKeyword1(new WxNoticeDataItem(params[2], null));
        shipped.setKeyword2(new WxNoticeDataItem(params[3], null));
        shipped.setKeyword3(new WxNoticeDataItem(params[4], null));
        shipped.setRemark(new WxNoticeDataItem("点击查看订单详情。", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setUrl(orderUrl);
        // 调用发货模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_ORDER_SHIPPED);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 有新的下级订单
     *
     * @param user
     * @param params   (1,订单编号(不是id);2,时间)
     * @param orderUrl 查看新订单url
     * @return
     */
    public Boolean newOrderNotice(ComUser user, String[] params, String orderUrl) {
        WxPFNewOrder newOrder = new WxPFNewOrder();
        WxNoticeReq<WxPFNewOrder> req = new WxNoticeReq<>(newOrder);

        newOrder.setFirst(new WxNoticeDataItem("您有新的合伙人订单,请到店铺查看", null));
        newOrder.setKeyword1(new WxNoticeDataItem(params[0], null));
        newOrder.setKeyword2(new WxNoticeDataItem(params[1], null));
        newOrder.setRemark(new WxNoticeDataItem("请及时发货，点击查看详情", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setUrl(orderUrl);
        // 调用新订单提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_NEW_PF_ORDER);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
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
        WxPFOrderConfirmNotice confirm = new WxPFOrderConfirmNotice();
        WxNoticeReq<WxPFOrderConfirmNotice> req = new WxNoticeReq<>(confirm);

        confirm.setFirst(new WxNoticeDataItem("亲，您在我们商城买的宝贝已经确认收货。", null));
        confirm.setKeyword1(new WxNoticeDataItem(params[0], null));
        confirm.setKeyword2(new WxNoticeDataItem(params[1], null));
        confirm.setKeyword3(new WxNoticeDataItem(params[2], null));
        confirm.setKeyword4(new WxNoticeDataItem(params[3], null));
        confirm.setKeyword5(new WxNoticeDataItem(params[4], null));
        confirm.setRemark(new WxNoticeDataItem("感谢您的支持与厚爱。", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_ORDER_CONFIRM);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 提现申请通知
     *
     * @param user
     * @param params    (1,提现金额;2,提现时间;3,提现状态)
     * @param isSuccess
     * @return
     */
    public Boolean pfExtractApply(ComUser user, String[] params, boolean isSuccess) {
        WxPFExtractApply apply = new WxPFExtractApply();
        WxNoticeReq<WxPFExtractApply> req = new WxNoticeReq<>(apply);

        if (isSuccess) {
            apply.setFirst(new WxNoticeDataItem("您好，您的提现申请已经提交", null));
            apply.setKeyword1(new WxNoticeDataItem(params[0], null));
            apply.setKeyword2(new WxNoticeDataItem(params[1], null));
            apply.setKeyword3(new WxNoticeDataItem(params[2], null));
            apply.setRemark(new WxNoticeDataItem("审核结果会在2个工作日内完成，请耐心等待!", null));
        } else {
            apply.setFirst(new WxNoticeDataItem("您好，您的提现申请被拒绝了", null));
            apply.setKeyword1(new WxNoticeDataItem(params[0], null));
            apply.setKeyword2(new WxNoticeDataItem(params[1], null));
            apply.setKeyword3(new WxNoticeDataItem(params[2], null));
            apply.setRemark(new WxNoticeDataItem("拒绝原因，您没有这么多余额，如有问题请联系客服!", null));
        }

        req.setTouser(getOpenIdByComUser(user));
        // 调用新订单提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_EXTRACT_APPLY);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 提现申请结果通知
     *
     * @param user
     * @param params (1,提现商户(代理商名称);2,提现金额;3,提现账户;4,处理时间)
     * @return
     */
    public Boolean pfExtractApplySuccess(ComUser user, String[] params) {
        WxPFExtractApplySuccess eas = new WxPFExtractApplySuccess();
        WxNoticeReq<WxPFExtractApplySuccess> req = new WxNoticeReq<>(eas);

        eas.setFirst(new WxNoticeDataItem("提现结果通知", null));
        eas.setKeyword1(new WxNoticeDataItem(params[0], null));
        eas.setKeyword2(new WxNoticeDataItem(params[1], null));
        eas.setKeyword3(new WxNoticeDataItem(params[2], null));
        eas.setKeyword4(new WxNoticeDataItem(params[3], null));
        eas.setRemark(new WxNoticeDataItem("您好，您的提现申请已经通过审核，汇款将会在1个工作日内完成，请注意查收", null));

        req.setTouser(getOpenIdByComUser(user));
        // 调用提现申请成功通知模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_EXTRACT_APPLY_SUCCESS);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 补货成功提醒-平台代发
     *
     * @param user
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单状态)
     * @return
     */
    public Boolean replenishmentByPlatForm(ComUser user, String[] params) {
        WxPFNewOrderDetail order = new WxPFNewOrderDetail();
        WxNoticeReq<WxPFNewOrderDetail> req = new WxNoticeReq<>(order);

        order.setFirst(new WxNoticeDataItem("补货成功", null));
        order.setKeyword1(new WxNoticeDataItem(params[0], null));
        order.setKeyword2(new WxNoticeDataItem(params[1], null));
        order.setKeyword3(new WxNoticeDataItem(params[2], null));
        order.setKeyword4(new WxNoticeDataItem("补货", null));
        order.setKeyword5(new WxNoticeDataItem(params[3], null));
        order.setRemark(new WxNoticeDataItem("您的在线库存已更新。", null));

        req.setTouser(getOpenIdByComUser(user));
        // 调用新订单提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_NEW_ORDER_DETAIL);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 补货成功提醒-自己发货
     *
     * @param user
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单状态)
     * @return
     */
    public Boolean replenishmentBySelf(ComUser user, String[] params) {
        WxPFNewOrderDetail order = new WxPFNewOrderDetail();
        WxNoticeReq<WxPFNewOrderDetail> req = new WxNoticeReq<>(order);

        order.setFirst(new WxNoticeDataItem("您的补货订单支付成功", null));
        order.setKeyword1(new WxNoticeDataItem(params[0], null));
        order.setKeyword2(new WxNoticeDataItem(params[1], null));
        order.setKeyword3(new WxNoticeDataItem(params[2], null));
        order.setKeyword4(new WxNoticeDataItem("补货", null));
        order.setKeyword5(new WxNoticeDataItem(params[3], null));
        order.setRemark(new WxNoticeDataItem("您的补货订单支付成功，我们将尽快发货请耐心等待收获。", null));

        req.setTouser(getOpenIdByComUser(user));
        // 调用新订单提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_NEW_ORDER_DETAIL);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 订单进入排单提醒
     *
     * @param user
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单类型;5,订单状态)
     * @return
     */
    public Boolean orderInQueue(ComUser user, String[] params) {
        WxPFNewOrderDetail order = new WxPFNewOrderDetail();
        WxNoticeReq<WxPFNewOrderDetail> req = new WxNoticeReq<>(order);

        order.setFirst(new WxNoticeDataItem("您的订单进入排单", null));
        order.setKeyword1(new WxNoticeDataItem(params[0], null));
        order.setKeyword2(new WxNoticeDataItem(params[1], null));
        order.setKeyword3(new WxNoticeDataItem(params[2], null));
        order.setKeyword4(new WxNoticeDataItem(params[3], null));
        order.setKeyword5(new WxNoticeDataItem(params[4], null));
        order.setRemark(new WxNoticeDataItem("由于库存不足，您的订单已进入排单，我们会加快生产，请耐心等待。", null));

        req.setTouser(getOpenIdByComUser(user));
        // 调用新订单提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_NEW_ORDER_DETAIL);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 处理排单提醒-平台代发
     *
     * @param user
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单类型;5,订单状态)
     * @return
     */
    public Boolean dealWithOrderInQueueByPlatForm(ComUser user, String[] params) {
        WxPFNewOrderDetail order = new WxPFNewOrderDetail();
        WxNoticeReq<WxPFNewOrderDetail> req = new WxNoticeReq<>(order);

        order.setFirst(new WxNoticeDataItem("您的排单订单已发货", null));
        order.setKeyword1(new WxNoticeDataItem(params[0], null));
        order.setKeyword2(new WxNoticeDataItem(params[1], null));
        order.setKeyword3(new WxNoticeDataItem(params[2], null));
        order.setKeyword4(new WxNoticeDataItem(params[3], null));
        order.setKeyword5(new WxNoticeDataItem(params[4], null));
        order.setRemark(new WxNoticeDataItem("您的排单订单已处理，在线库存已更新。", null));

        req.setTouser(getOpenIdByComUser(user));
        // 调用新订单提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_NEW_ORDER_DETAIL);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 处理排单提醒-自己发货
     *
     * @param user
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单类型;5,订单状态)
     * @return
     */
    public Boolean dealWithOrderInQueueBySelf(ComUser user, String[] params) {
        WxPFNewOrderDetail order = new WxPFNewOrderDetail();
        WxNoticeReq<WxPFNewOrderDetail> req = new WxNoticeReq<>(order);

        order.setFirst(new WxNoticeDataItem("您的排单订单已发货", null));
        order.setKeyword1(new WxNoticeDataItem(params[0], null));
        order.setKeyword2(new WxNoticeDataItem(params[1], null));
        order.setKeyword3(new WxNoticeDataItem(params[2], null));
        order.setKeyword4(new WxNoticeDataItem(params[3], null));
        order.setKeyword5(new WxNoticeDataItem(params[4], null));
        order.setRemark(new WxNoticeDataItem("您的排单订单已发货，请耐心等待收获。", null));

        req.setTouser(getOpenIdByComUser(user));
        // 调用新订单提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_NEW_ORDER_DETAIL);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 库存不足提醒
     *
     * @param user
     * @param params (1,商品编码;2,商品名称;3,库存数量)
     * @param url    查看库存url
     * @return
     */
    public Boolean inventoryShortageNotice(ComUser user, String[] params, String url) {
        WxPFInventoryShortage shortage = new WxPFInventoryShortage();
        WxNoticeReq<WxPFInventoryShortage> req = new WxNoticeReq<>(shortage);

        shortage.setFirst(new WxNoticeDataItem(user.getRealName() + "你好，您有商品的库存数量低于10件，请及时补充库存。", null));
        shortage.setKeyword1(new WxNoticeDataItem(params[0], null));
        shortage.setKeyword2(new WxNoticeDataItem(params[1], null));
        shortage.setKeyword3(new WxNoticeDataItem(params[2], null));
        shortage.setRemark(new WxNoticeDataItem("点击查看详情", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setUrl(url);
        // 调用库存不足提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_INVENTORY_SHORTAGE);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 店铺新订单通知
     *
     * @param pUser    店铺店主user
     * @param params   (1,收件人;2,联系电话;3,收货地址;4,购物清单;5,备注)
     * @param orderUrl
     * @return
     */
    public Boolean newShopOrderNotice(ComUser pUser, String[] params, String orderUrl) {
        WxPFNewShopOrder order = new WxPFNewShopOrder();
        WxNoticeReq<WxPFNewShopOrder> req = new WxNoticeReq<>(order);

        order.setFirst(new WxNoticeDataItem("你有新的店铺订单啦", null));
        order.setKeyword1(new WxNoticeDataItem(params[0], null));
        order.setKeyword2(new WxNoticeDataItem(params[1], null));
        order.setKeyword3(new WxNoticeDataItem(params[2], null));
        order.setKeyword4(new WxNoticeDataItem(params[3], null));
        order.setKeyword5(new WxNoticeDataItem(params[4], null));
        order.setRemark(new WxNoticeDataItem("点击查看详情！", null));

        req.setTouser(getOpenIdByComUser(pUser));
        req.setUrl(orderUrl);
        // 调用库存不足提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_NEW_SHOP_ORDER);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 线下支付提醒
     *
     * @param user  目标人
     * @param params    (第一个,订单号; 第二个,订单创建时间; 第三个,商品明细(如:抗引力boss级合伙人订单))
     * @param orderUrl  通知的订单详情页
     * @return
     */
    public Boolean offLinePayNotice(ComUser user, String[] params, String orderUrl){
        WxPFOffLinePayNotice offLinePayNotice = new WxPFOffLinePayNotice();
        WxNoticeReq<WxPFOffLinePayNotice> req = new WxNoticeReq<>(offLinePayNotice);

        offLinePayNotice.setFirst(new WxNoticeDataItem("订单未付款提醒", null));
        offLinePayNotice.setKeyword1(new WxNoticeDataItem(params[0], null));
        offLinePayNotice.setKeyword2(new WxNoticeDataItem(params[1], null));
        offLinePayNotice.setKeyword3(new WxNoticeDataItem(params[2], null));
        Date date = DateUtil.String2Date(params[1], "yyyy-MM-dd HH:mm:ss");
        date = DateUtil.getDateNextdays(date, 7);
        offLinePayNotice.setRemark(new WxNoticeDataItem("您选择的是线下支付，请您在"
                + DateUtil.Date2String(date, "yyyy-MM-dd HH:mm:ss") + "前付款以免过期。点击查看详情。", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setUrl(orderUrl);
        // 调用库存不足提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_OFFLINE_PAY);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    private String getOpenIdByComUser(ComUser user) {
        if (user == null) {
            throw new BusinessException("user为空");
        }
        ComWxUser wxUser = wxUserService.getUserByUnionidAndAppid(user.getWxUnionid(), WxConsPF.APPID);
        if (wxUser == null) {
            throw new BusinessException("wxUser为空");
        }
        return wxUser.getOpenid();
    }

}
