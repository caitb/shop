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
import org.apache.commons.lang.StringUtils;
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
     * @param noPay notice对象
     * @return  返回是否成功调用
     */
    public Boolean wxNotice(String accessToken, WxNoticeReq noPay) {
        try {
            if(StringUtils.isBlank(noPay.getTouser())){
                throw new BusinessException("查不到wxUser");
            }
            String url = WxConsPF.URL_WX_NOTICE + "?access_token=" + accessToken;
            String result = HttpClientUtils.httpPost(url, JSONObject.toJSONString(noPay));
            log.info("调用模板返回:" + result);
            WxNoticeRes res = JSONObject.parseObject(result, WxNoticeRes.class);
            if ("0".equals(res.getErrcode())) {
                return true;
            }
        } catch (Exception e) {
            log.error(e);
        }
        log.error("发送模板消息失败");
        return false;
    }

    /**
     * 合伙人申请成功提示
     *
     * @param user
     * @param params 参数数组,(第一个,支付金额;第二个,支付方式;第三个,支付详情;第四个,支付时间)
     * @return  返回是否成功调用
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
     * @return  返回是否成功调用
     */
    public Boolean partnerJoinNotice(ComUser pUser, ComUser user, String joinTime) {
        WxPFPartnerJoin join = new WxPFPartnerJoin();
        WxNoticeReq<WxPFPartnerJoin> req = new WxNoticeReq<>(join);

        join.setFirst(new WxNoticeDataItem("下级代理加入通知", null));
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
     * 下线加入通知(升级方式加入)
     *
     * @param pUser 上级代理用户对象
     * @param user  下级代理用户对象
     * @param joinTime 下级代理加入时间
     * @Param url   查看详情
     * @return  返回是否成功调用
     */
    public Boolean partnerJoinByUpgradeNotice(ComUser pUser, ComUser user, String joinTime, String url) {
        WxPFPartnerJoin join = new WxPFPartnerJoin();
        WxNoticeReq<WxPFPartnerJoin> req = new WxNoticeReq<>(join);

        join.setFirst(new WxNoticeDataItem("您有一个新的下级", null));
        join.setRemark(new WxNoticeDataItem("此人是通过升级方式成为您的下级，您需要给他的原上级一次性奖励，具体金额请线下沟通。点击查看详情", null));
        join.setKeyword1(new WxNoticeDataItem(user.getMobile(), null));
        join.setKeyword2(new WxNoticeDataItem(joinTime, null));
        join.setKeyword3(new WxNoticeDataItem(user.getWxNkName(), null));

        req.setTouser(getOpenIdByComUser(pUser));
        // 调用下线加入模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_PTNER_JOIN_NOTICE);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 下级加入通知(推荐方式加入)
     *
     * @param pUser 上级代理用户对象
     * @param user  下级代理用户对象
     * @param params    (第一个,推荐人; 第二个,加入时间)
     * @Param url   查看详情
     * @return  返回是否成功调用
     */
    public Boolean partnerJoinByRecommendNotice(ComUser pUser, ComUser user, String[] params, String url) {
        WxPFPartnerJoin join = new WxPFPartnerJoin();
        WxNoticeReq<WxPFPartnerJoin> req = new WxNoticeReq<>(join);

        join.setFirst(new WxNoticeDataItem("新下级加入通知", null));
        join.setRemark(new WxNoticeDataItem("您有一个新的下级成功加入，ta的推荐人是" + params[0], null));
        join.setKeyword1(new WxNoticeDataItem(user.getMobile(), null));
        join.setKeyword2(new WxNoticeDataItem(params[1], null));
        join.setKeyword3(new WxNoticeDataItem(user.getWxNkName(), null));

        req.setTouser(getOpenIdByComUser(pUser));
        // 调用下线加入模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_PTNER_JOIN_NOTICE);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 实名认证通知
     *
     * @param user
     * @param isSuccess 是否认证成功
     * @param url       成功,继续申请合伙人链接;失败,重新实名认证链接
     * @return  返回是否成功调用
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
     * @return  返回是否成功调用
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
     * @return  返回是否成功调用
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
     * 代理订单发货通知
     *
     * @param user     通知对象
     * @param params   (1,商品名称;2,订单编号(不是id);3,快递公司;4,快递单号)
     * @return  返回是否成功调用
     */
    public Boolean trailOrderShippedNotice(ComUser user, String[] params) {
        WxPFOrderShipped shipped = new WxPFOrderShipped();
        WxNoticeReq<WxPFOrderShipped> req = new WxNoticeReq<>(shipped);

        shipped.setFirst(new WxNoticeDataItem("您好，您的" + params[0] + "试用订单已发货", null));
        shipped.setKeyword1(new WxNoticeDataItem(params[1], null));
        shipped.setKeyword2(new WxNoticeDataItem(params[2], null));
        shipped.setKeyword3(new WxNoticeDataItem(params[3], null));
        shipped.setRemark(new WxNoticeDataItem("请注意查收。", null));

        req.setTouser(getOpenIdByComUser(user));
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
     * @param hasInventory  库存是否充足
     * @return  返回是否成功调用
     */
    public Boolean newOrderNotice(ComUser user, String[] params, String orderUrl, boolean hasInventory) {
        WxPFNewOrder newOrder = new WxPFNewOrder();
        WxNoticeReq<WxPFNewOrder> req = new WxNoticeReq<>(newOrder);

        newOrder.setFirst(new WxNoticeDataItem("您有新的合伙人订单,请到订单管理中查看", null));
        newOrder.setKeyword1(new WxNoticeDataItem(params[0], null));
        newOrder.setKeyword2(new WxNoticeDataItem(params[1], null));
        newOrder.setRemark(new WxNoticeDataItem("目前您的库存不足，为了不影响下级销售，请及时补货。", null));

        req.setTouser(getOpenIdByComUser(user));
        if(hasInventory) {
            newOrder.setRemark(new WxNoticeDataItem("点击查看详情", null));
            req.setUrl(orderUrl);
        }
        // 调用新订单提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_NEW_PF_ORDER);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 有新的下级补货订单
     *
     * @param user
     * @param params   (1,订单编号(不是id);2,时间)
     * @param orderUrl 查看新订单url
     * @param hasInventory  库存是否充足
     * @return  返回是否成功调用
     */
    public Boolean newSupplementOrderNotice(ComUser user, String[] params, String orderUrl, boolean hasInventory) {
        WxPFNewOrder newOrder = new WxPFNewOrder();
        WxNoticeReq<WxPFNewOrder> req = new WxNoticeReq<>(newOrder);

        newOrder.setFirst(new WxNoticeDataItem("您有新的补货订单,请到订单管理中查看", null));
        newOrder.setKeyword1(new WxNoticeDataItem(params[0], null));
        newOrder.setKeyword2(new WxNoticeDataItem(params[1], null));
        newOrder.setRemark(new WxNoticeDataItem("目前您的库存不足，为了不影响下级销售，请及时补货。", null));

        req.setTouser(getOpenIdByComUser(user));
        if(hasInventory) {
            newOrder.setRemark(new WxNoticeDataItem("点击查看详情", null));
            req.setUrl(orderUrl);
        }
        // 调用新订单提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_NEW_PF_ORDER);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 订单确认收货
     *
     * @param user
     * @param params (第一个,订单号(ordercode); 第二个,商品名称; 第三个,下单时间;
     *               第四个,发货时间; 第五个,确认收货时间)
     * @return  返回是否成功调用
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
     * 订单7天自动确认收货
     *
     * @param user
     * @param params (第一个,订单号(ordercode); 第二个,商品名称; 第三个,下单时间;
     *               第四个,发货时间; 第五个,确认收货时间)
     * @return  返回是否成功调用
     */
    public Boolean orderAutoConfirmNotice(ComUser user, String[] params){
        WxPFOrderConfirmNotice confirm = new WxPFOrderConfirmNotice();
        WxNoticeReq<WxPFOrderConfirmNotice> req = new WxNoticeReq<>(confirm);

        confirm.setFirst(new WxNoticeDataItem("亲，您在我们商城买的宝贝7天自动确认收货。", null));
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
     * @param params    (1,提现金额;2,提现时间;3,提现状态; 4,拒绝原因)
     * @param isSuccess
     * @return  返回是否成功调用
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
            apply.setRemark(new WxNoticeDataItem("拒绝原因：" + params[3] + "，如有问题请联系客服!", null));
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
     * @return  返回是否成功调用
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
     * @return  返回是否成功调用
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
     * @return  返回是否成功调用
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
        order.setRemark(new WxNoticeDataItem("您的补货订单支付成功，我们将尽快发货请耐心等待收货。", null));

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
     * @return  返回是否成功调用
     */
    public Boolean orderInQueue(ComUser user, String[] params) {
        WxPFNewOrderDetail order = new WxPFNewOrderDetail();
        WxNoticeReq<WxPFNewOrderDetail> req = new WxNoticeReq<>(order);

        order.setFirst(new WxNoticeDataItem("恭喜您支付成功", null));
        order.setKeyword1(new WxNoticeDataItem(params[0], null));
        order.setKeyword2(new WxNoticeDataItem(params[1], null));
        order.setKeyword3(new WxNoticeDataItem(params[2], null));
        order.setKeyword4(new WxNoticeDataItem(params[3], null));
        order.setKeyword5(new WxNoticeDataItem(params[4], null));
        order.setRemark(new WxNoticeDataItem("由于库存不足，您的订单已进入排单，请耐心等待。", null));

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
     * @return  返回是否成功调用
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
     * @return  返回是否成功调用
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
        order.setRemark(new WxNoticeDataItem("您的排单订单已发货，请耐心等待收货。", null));

        req.setTouser(getOpenIdByComUser(user));
        // 调用新订单提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_NEW_ORDER_DETAIL);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 处理排单提醒-提醒上级下级进入排单,需要补货
     *
     * @param user
     * @param params (1,订单名称(可传商品名称);2,订单价格;3,订单数量;4,订单类型;5,订单状态)
     * @return  返回是否成功调用
     */
    public Boolean dealWithOrderInQueueByUp(ComUser user, String[] params, String url) {
        WxPFNewOrderDetail order = new WxPFNewOrderDetail();
        WxNoticeReq<WxPFNewOrderDetail> req = new WxNoticeReq<>(order);

        order.setFirst(new WxNoticeDataItem("您有新的订单。", null));
        order.setKeyword1(new WxNoticeDataItem(params[0], null));
        order.setKeyword2(new WxNoticeDataItem(params[1], null));
        order.setKeyword3(new WxNoticeDataItem(params[2], null));
        order.setKeyword4(new WxNoticeDataItem(params[3], null));
        order.setKeyword5(new WxNoticeDataItem(params[4], null));
        order.setRemark(new WxNoticeDataItem("您的库存不足，请及时补货，以免影响您的下级销售，点击补货。", null));

        req.setTouser(getOpenIdByComUser(user));
        // 调用新订单提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_NEW_ORDER_DETAIL);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 库存不足提醒
     *
     * @param user
     * @param params (1,商品编码;2,商品名称;3,库存数量)
     * @param url    查看库存url
     * @return  返回是否成功调用
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
     * @return  返回是否成功调用
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
     * 店铺新订单通知,库存不足情况
     *
     * @param pUser    店铺店主user
     * @param params   (1,收件人;2,联系电话;3,收货地址;4,购物清单;5,备注)
     * @param orderUrl
     * @return  返回是否成功调用
     */
    public Boolean newShopOrderNoticeNoStock(ComUser pUser, String[] params, String orderUrl) {
        WxPFNewShopOrder order = new WxPFNewShopOrder();
        WxNoticeReq<WxPFNewShopOrder> req = new WxNoticeReq<>(order);

        order.setFirst(new WxNoticeDataItem("你有新的店铺订单啦", null));
        order.setKeyword1(new WxNoticeDataItem(params[0], null));
        order.setKeyword2(new WxNoticeDataItem(params[1], null));
        order.setKeyword3(new WxNoticeDataItem(params[2], null));
        order.setKeyword4(new WxNoticeDataItem(params[3], null));
        order.setKeyword5(new WxNoticeDataItem(params[4], null));
        order.setRemark(new WxNoticeDataItem("您的库存不足，无法发货，请及时补货。点击补货！", null));

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
     * @param params    (第一个,订单号; 第二个,商品明细(如:抗引力boss级合伙人订单))
     * @param orderUrl  通知的订单详情页
     * @return  返回是否成功调用
     */
    public Boolean offLinePayNotice(ComUser user, String[] params,Date creatTime, String orderUrl){
        WxPFOffLinePayNotice offLinePayNotice = new WxPFOffLinePayNotice();
        WxNoticeReq<WxPFOffLinePayNotice> req = new WxNoticeReq<>(offLinePayNotice);

        offLinePayNotice.setFirst(new WxNoticeDataItem("订单未付款提醒", null));
        offLinePayNotice.setKeyword1(new WxNoticeDataItem(params[0], null));
        offLinePayNotice.setKeyword2(new WxNoticeDataItem(DateUtil.Date2String(creatTime,DateUtil.SQL_TIME_FMT), null));
        offLinePayNotice.setKeyword3(new WxNoticeDataItem(params[1], null));
/*        Date date = DateUtil.String2Date(params[1], "yyyy-MM-dd HH:mm:ss");
        date = DateUtil.getDateNextdays(date, 7);*/
        offLinePayNotice.setRemark(new WxNoticeDataItem("您选择的是线下支付，请您在"
                + DateUtil.insertDay(creatTime) + "前付款以免过期。点击查看详情。", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setUrl(orderUrl);
        // 调用库存不足提醒模板id
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_OFFLINE_PAY);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 升级订单支付成功提示
     *
     * @param user
     * @param params 参数数组,(第一个,支付金额;第二个,支付方式;第三个,支付详情;第四个,支付时间)
     * @return  返回是否成功调用
     */
    public Boolean upgradePaySuccessNotice(ComUser user, String[] params) {
        WxPFPartnerApplyOK applyOK = new WxPFPartnerApplyOK();
        WxNoticeReq<WxPFPartnerApplyOK> req = new WxNoticeReq<>(applyOK);

        applyOK.setFirst(new WxNoticeDataItem("您好，您的升级订单支付成功。", null));
        applyOK.setRemark(new WxNoticeDataItem("恭喜您已升级成功，感谢您的使用。", null));
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
     * 下级代理申请升级通知
     *
     * @param pUser 上级用户对象
     * @param params    (第一个,下级代理名称; 第二个,下级代理现等级; 第三个,下级代理申请等级;
     *                  第四个,申请时间; 第五个,申请过期时间)
     * @param url   处理下级代理升级申请单url
     * @return  返回是否成功调用
     */
    public Boolean subLineUpgradeApplyNotice(ComUser pUser, String[] params, String url){
        WxPFUpgradeApplyNotice notice = new WxPFUpgradeApplyNotice();
        WxNoticeReq<WxPFUpgradeApplyNotice> req = new WxNoticeReq<>(notice);

        notice.setFirst(new WxNoticeDataItem("您有一个代理申请升级", null));
        notice.setKeyword1(new WxNoticeDataItem(params[0], null));
        notice.setKeyword2(new WxNoticeDataItem(params[1], null));
        notice.setKeyword3(new WxNoticeDataItem(params[2], null));
        notice.setKeyword4(new WxNoticeDataItem(params[3], null));
        notice.setRemark(new WxNoticeDataItem("为了保证您的利益，请在" + params[4]
                + "之前及时处理，逾期默认不升级，点击处理。", null));

        req.setTouser(getOpenIdByComUser(pUser));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_UP_APPLY_NOTICE);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 升级通知单提交通知
     *
     * @param user 用户对象
     * @param params    (第一个,代理名称; 第二个,代理现等级; 第三个,代理申请等级;
     *                  第四个,申请时间)
     * @param url   查看代理升级申请单url
     * @return  返回是否成功调用
     */
    public Boolean upgradeApplySubmitNotice(ComUser user, String[] params, String url){
        WxPFUpgradeApplyNotice notice = new WxPFUpgradeApplyNotice();
        WxNoticeReq<WxPFUpgradeApplyNotice> req = new WxNoticeReq<>(notice);

        notice.setFirst(new WxNoticeDataItem("您的升级申请已提交，请耐心等待审核。", null));
        notice.setKeyword1(new WxNoticeDataItem(params[0], null));
        notice.setKeyword2(new WxNoticeDataItem(params[1], null));
        notice.setKeyword3(new WxNoticeDataItem(params[2], null));
        notice.setKeyword4(new WxNoticeDataItem(params[3], null));
        notice.setRemark(new WxNoticeDataItem("审核通过后需要您继续支付，点击查看升级单，了解最新状态。", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_UP_APPLY_NOTICE);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 申请升级通知审核通过
     *
     * @param user 用户对象
     * @param params    (第一个,代理名称; 第二个,代理现等级; 第三个,代理申请等级;
     *                  第四个,申请时间; 第五个,申请过期时间)
     * @param url   处理下级代理升级申请单url
     * @return  返回是否成功调用
     */
    public Boolean upgradeApplyAuditPassNotice(ComUser user, String[] params, String url){
        WxPFUpgradeApplyNotice notice = new WxPFUpgradeApplyNotice();
        WxNoticeReq<WxPFUpgradeApplyNotice> req = new WxNoticeReq<>(notice);

        notice.setFirst(new WxNoticeDataItem("您的升级申请已审核通过。", null));
        notice.setKeyword1(new WxNoticeDataItem(params[0], null));
        notice.setKeyword2(new WxNoticeDataItem(params[1], null));
        notice.setKeyword3(new WxNoticeDataItem(params[2], null));
        notice.setKeyword4(new WxNoticeDataItem(params[3], null));
        notice.setRemark(new WxNoticeDataItem("您的升级申请已审核通过，请在" + params[4]
                + "前支付完成，逾期将取消订单。点击继续支付。", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_UP_APPLY_NOTICE);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 升级通知单取消通知
     *
     * @param user 用户对象
     * @param params    (第一个,代理名称; 第二个,代理现等级; 第三个,代理申请等级;
     *                  第四个,申请时间)
     * @param url   查看代理升级申请单url
     * @return  返回是否成功调用
     */
    public Boolean subUpgradeApplyCancelNotice(ComUser user, String[] params, String url){
        WxPFUpgradeApplyNotice notice = new WxPFUpgradeApplyNotice();
        WxNoticeReq<WxPFUpgradeApplyNotice> req = new WxNoticeReq<>(notice);

        notice.setFirst(new WxNoticeDataItem("您的升级申请已取消。", null));
        notice.setKeyword1(new WxNoticeDataItem(params[0], null));
        notice.setKeyword2(new WxNoticeDataItem(params[1], null));
        notice.setKeyword3(new WxNoticeDataItem(params[2], null));
        notice.setKeyword4(new WxNoticeDataItem(params[3], null));
        notice.setRemark(new WxNoticeDataItem("您的升级申请已取消，升级未成功。", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_UP_APPLY_NOTICE);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 下级代理申请升级取消通知
     *
     * @param pUser 上级用户对象
     * @param params    (第一个,下级代理名称; 第二个,下级代理现等级; 第三个,下级代理申请等级;
     *                  第四个,申请时间)
     * @param url   查看下级代理升级申请单url
     * @return  返回是否成功调用
     */
    public Boolean subLineUpgradeApplyCancelNotice(ComUser pUser, String[] params, String url){
        WxPFUpgradeApplyNotice notice = new WxPFUpgradeApplyNotice();
        WxNoticeReq<WxPFUpgradeApplyNotice> req = new WxNoticeReq<>(notice);

        notice.setFirst(new WxNoticeDataItem("您的下级升级申请已取消。", null));
        notice.setKeyword1(new WxNoticeDataItem(params[0], null));
        notice.setKeyword2(new WxNoticeDataItem(params[1], null));
        notice.setKeyword3(new WxNoticeDataItem(params[2], null));
        notice.setKeyword4(new WxNoticeDataItem(params[3], null));
        notice.setRemark(new WxNoticeDataItem("您的下级升级申请已取消，升级未成功。", null));

        req.setTouser(getOpenIdByComUser(pUser));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_UP_APPLY_NOTICE);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 下级升级申请单2天未处理,默认不升级
     *
     * @param pUser 上级用户对象
     * @param params    (第一个,取消时间)
     * @param url   查看升级申请单
     * @return  返回是否成功调用
     */
    public Boolean upgradeApplyNotHandleNotice(ComUser pUser, String[] params, String url){
        WxPFUpServiceCancelNotice notice = new WxPFUpServiceCancelNotice();
        WxNoticeReq<WxPFUpServiceCancelNotice> req = new WxNoticeReq<>(notice);

        notice.setFirst(new WxNoticeDataItem("您有一单下级升级申请超过2天未处理，系统默认处理为不升级。", null));
        notice.setKeyword1(new WxNoticeDataItem("升级申请", null));
        notice.setKeyword2(new WxNoticeDataItem(params[0], null));
        notice.setKeyword3(new WxNoticeDataItem("超过2天未处理，系统默认不升级。", null));
        notice.setRemark(new WxNoticeDataItem("您的下级将会收到支付提醒，支付成功后ta将不是您的下级，您可以获得一次性奖励。点击查看详情。", null));

        req.setTouser(getOpenIdByComUser(pUser));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_UP_CANCEL_NOTICE);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 升级申请单2天未支付,自动取消
     *
     * @param user
     * @param params    (第一个,取消时间)
     * @param url   查看升级申请单url
     * @return  返回是否成功调用
     */
    public Boolean upgradeApplyTwoDayNotPayNotice(ComUser user, String[] params, String url){
        return upgradeApplyNotPayNotice(user, params, url, "2");
    }

    /**
     * 升级申请单7天未支付,自动取消
     *
     * @param user
     * @param params    (第一个,取消时间)
     * @param url   查看升级申请单url
     * @return
     */
    public Boolean upgradeApplySevenDayNotPayNotice(ComUser user, String[] params, String url){
        return upgradeApplyNotPayNotice(user, params, url, "7");
    }

    /**
     * 升级申请单N天未支付,自动取消
     *
     * @param user
     * @param params    (第一个,取消时间)
     * @param url   查看升级申请单url
     * @param days  N天
     * @return  返回是否成功调用
     */
    private Boolean upgradeApplyNotPayNotice(ComUser user, String[] params, String url, String days){
        WxPFUpServiceCancelNotice notice = new WxPFUpServiceCancelNotice();
        WxNoticeReq<WxPFUpServiceCancelNotice> req = new WxNoticeReq<>(notice);

        notice.setFirst(new WxNoticeDataItem("您有一笔升级申请单超过" + days + "天未支付，系统已经为您取消升级申请单。", null));
        notice.setKeyword1(new WxNoticeDataItem("升级申请", null));
        notice.setKeyword2(new WxNoticeDataItem(params[0], null));
        notice.setKeyword3(new WxNoticeDataItem("超过" + days + "天未支付，系统取消升级申请单。", null));
        notice.setRemark(new WxNoticeDataItem("您的升级申请单已取消，点击查看详情。", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_UP_CANCEL_NOTICE);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 升级申请结果通知(下级脱离上级)
     *
     * 第一个示例:
     * 您好，在您的下级 王平 的升级申请单中，您已选择不升级。
     * 申请内容：王平的升级申请
     * 申请结果：不升级
     * 您的下级将会收到支付提醒，支付成功后ta将不是您的下级，您可以获得一次性奖励。点击查看详情。
     *
     * 第二个示例:
     * 您好，您的下级 王平 已成功升级。
     * 申请内容：王平的升级申请
     * 申请结果：下级升级成功
     * 您的下级已升级成功，ta已不是您的下级，您可以获得一次性奖励。点击查看详情。
     *
     * @param pUser 上级用户对象
     * @param params    (第一个,下级名称)
     * @param url   查看升级申请单url
     * @param isApply 下级是否申请成功
     * @return
     */
    public Boolean upgradeApplyResultNotice(ComUser pUser, String[] params, String url, boolean isApply){
        WxPFUpgradeResultNotice notice = new WxPFUpgradeResultNotice();
        WxNoticeReq<WxPFUpgradeResultNotice> req = new WxNoticeReq<>(notice);

        notice.setKeyword1(new WxNoticeDataItem(params[0] + "的升级申请", null));
        if(isApply) {
            notice.setFirst(new WxNoticeDataItem("您好，您的下级 " + params[0] + " 已成功升级。", null));
            notice.setKeyword2(new WxNoticeDataItem("下级升级成功", null));
            notice.setRemark(new WxNoticeDataItem("您的下级已升级成功，ta已不是您的下级，您可以获得一次性奖励。点击查看详情。", null));
        } else {
            notice.setFirst(new WxNoticeDataItem("您好，在您的下级 " + params[0] + " 的升级申请单中，您已选择不升级。", null));
            notice.setKeyword2(new WxNoticeDataItem("不升级", null));
            notice.setRemark(new WxNoticeDataItem("您的下级将会收到支付提醒，支付成功后ta将不是您的下级，您可以获得一次性奖励。点击查看详情。", null));
        }

        req.setTouser(getOpenIdByComUser(pUser));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_UP_RESULT_NOTICE);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 下级升级成功,仍是原上级的下级
     *
     * @param pUser 上级用户对象
     * @param params    (第一个,下级用户昵称; 第二个,下级新代理等级; 第三个,生效时间)
     * @param url   查看url
     * @return
     */
    public Boolean upgradeResultNoticeUpLine(ComUser pUser, String[] params, String url){
        WxPFUpgradeResultUPNotice notice = new WxPFUpgradeResultUPNotice();
        WxNoticeReq<WxPFUpgradeResultUPNotice> req = new WxNoticeReq<>(notice);

        notice.setFirst(new WxNoticeDataItem("您下级升级成功。", null));
        notice.setKeyword1(new WxNoticeDataItem(params[0], null));
        notice.setKeyword2(new WxNoticeDataItem(params[1], null));
        notice.setKeyword3(new WxNoticeDataItem(params[2], null));
        notice.setRemark(new WxNoticeDataItem("升级成功后，您仍是ta的上级，点击查看。", null));

        req.setTouser(getOpenIdByComUser(pUser));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_UP_SUCCESS_NOTICE);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 推荐成功通知
     *
     * @param pUser 推荐人用户对象
     * @param params    (第一个,被推荐人)
     * @return  是否调用成功
     */
    public Boolean recommendSuccessNotice(ComUser pUser, String[] params){
        WxPFRecommendSuccess success = new WxPFRecommendSuccess();
        WxNoticeReq<WxPFRecommendSuccess> req = new WxNoticeReq<>(success);

        success.setFirst(new WxNoticeDataItem("恭喜您，推荐成功。", null));
        success.setKeyword1(new WxNoticeDataItem(pUser.getWxNkName(), null));
        success.setKeyword2(new WxNoticeDataItem(params[0], null));
        success.setRemark(new WxNoticeDataItem("您已成功成为ta的推荐人，可以获得推荐奖励。", null));

        req.setTouser(getOpenIdByComUser(pUser));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_RECOMMEND_SUCCESS_NOTICE);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 推荐佣金通知
     *
     * @param user 获得推荐奖励的用户对象
     * @param params    (第一个,佣金金额; 第二个,时间)
     * @param url   点击去获得奖励订单页面
     * @return  是否调用成功
     */
    public Boolean recommendProfitNotice(ComUser user, String[] params, String url){
        WxPFRecommendProfit profit = new WxPFRecommendProfit();
        WxNoticeReq<WxPFRecommendProfit> req = new WxNoticeReq<>(profit);

        profit.setFirst(new WxNoticeDataItem("恭喜您，获得了一笔新的推荐佣金。", null));
        profit.setKeyword1(new WxNoticeDataItem(params[0], null));
        profit.setKeyword2(new WxNoticeDataItem(params[1], null));
        profit.setRemark(new WxNoticeDataItem("您可以在\"获取奖励订单\"中查看，点击查看。", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_RECOMMEND_PROFIT_IN);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 推荐佣金通知
     *
     * @param user 发出推荐奖励的用户对象
     * @param params    (第一个,佣金金额; 第二个,时间; 第三个,被推荐人名; 第四个,获得推荐奖励人名)
     * @param url   点击去获得奖励订单页面
     * @return  是否调用成功
     */
    public Boolean recommendProfitOutNotice(ComUser user, String[] params, String url){
        WxPFRecommendProfit profit = new WxPFRecommendProfit();
        WxNoticeReq<WxPFRecommendProfit> req = new WxNoticeReq<>(profit);

        profit.setFirst(new WxNoticeDataItem("您有一笔推荐佣金支出。", null));
        profit.setKeyword1(new WxNoticeDataItem(params[0], null));
        profit.setKeyword2(new WxNoticeDataItem(params[1], null));
        profit.setRemark(new WxNoticeDataItem("您需要给 " + params[2] + " 的推荐人 " + params[3]
                + " 支付一笔推荐佣金，系统已经扣除，详情请查看发出奖励订单。", null));

        req.setTouser(getOpenIdByComUser(user));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_RECOMMEND_PROFIT_IN);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    private Boolean orderUnpayCancelNoitce(ComUser user, String[] params, String url, String remark){
        WxPFOrderCancelNotice notice = new WxPFOrderCancelNotice();
        WxNoticeReq<WxPFOrderCancelNotice> req = new WxNoticeReq<WxPFOrderCancelNotice>(notice);

        notice.setFirst(new WxNoticeDataItem("您的订单已取消。", null));
        notice.setKeyword1(new WxNoticeDataItem(params[0], null));
        notice.setKeyword2(new WxNoticeDataItem(params[1], null));
        notice.setKeyword3(new WxNoticeDataItem(params[2], null));
        notice.setKeyword4(new WxNoticeDataItem(params[3], null));
        notice.setRemark(new WxNoticeDataItem(remark, null));

        req.setTouser(getOpenIdByComUser(user));
        req.setTemplate_id(WxConsPF.WX_PF_TM_ID_ORDER_CANCEL_NOTICE);
        req.setUrl(url);
        return wxNotice(WxCredentialUtils.getInstance()
                .getCredentialAccessToken(WxConsPF.APPID, WxConsPF.APPSECRET), req);
    }

    /**
     * 订单超过2天未支付
     *
     * @param user
     * @param params    (第一个,订单编号; 第二个,商品名称; 第三个,商品数量; 第四个,订单金额)
     * @param url
     * @return
     */
    public Boolean orderUnpayTwoDayCancelNotice(ComUser user, String[] params, String url){
        return orderUnpayCancelNoitce(user, params, url, "您的订单超过2天未支付，系统已经帮您取消。");
    }

    /**
     * 订单超过3天未支付
     *
     * @param user
     * @param params    (第一个,订单编号; 第二个,商品名称; 第三个,商品数量; 第四个,订单金额)
     * @param url
     * @return
     */
    public Boolean orderUnpayThreeDayCancelNotice(ComUser user, String[] params, String url){
        return orderUnpayCancelNoitce(user, params, url, "您的订单超过3天未支付，系统已经帮您取消。");
    }

    /**
     * 线下支付订单超过7天未支付
     *
     * @param user
     * @param params    (第一个,订单编号; 第二个,商品名称; 第三个,商品数量; 第四个,订单金额)
     * @param url
     * @return
     */
    public Boolean orderUnpaySevenDayCancelNotice(ComUser user, String[] params, String url){
        return orderUnpayCancelNoitce(user, params, url, "您的线下支付订单超过7天未支付，系统已经帮您取消。");
    }

    private String getOpenIdByComUser(ComUser user) {
        ComWxUser wxUser = null;
        try {
            if (user == null) {
                throw new BusinessException("user为空");
            }
            wxUser = wxUserService.getUserByUnionidAndAppid(user.getWxUnionid(), WxConsPF.APPID);
            if (wxUser == null) {
                throw new BusinessException("wxUser为空");
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
        return wxUser.getOpenid();
    }

}
