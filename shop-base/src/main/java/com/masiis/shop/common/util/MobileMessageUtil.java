package com.masiis.shop.common.util;

import com.masiis.shop.common.constant.SMSConstants;

/**
 * MobileMessageUtil
 *
 * @author wbj
 * @date 2016/3/9
 */
public class MobileMessageUtil {

    /**
     * 短信結束語
     */
    private static String EVENING_MESSAGE = "";
    /**
     * 系统标识
     */
    private static String fromIdentify = "";

    private String[] content;

    /**
     * 判断是B端还是C端 短信
     * @param fromIdentify  B/C/""
     */
    private MobileMessageUtil(String fromIdentify){
        this.fromIdentify = fromIdentify;
        if ("B".equals(this.fromIdentify)){
            EVENING_MESSAGE = "关注麦链合伙人公众账号，查看最新状态";
        }
        if ("C".equals(this.fromIdentify)){
            EVENING_MESSAGE = "关注麦链商城众账号，查看最新状态";
        }
    }

    /**
     * 单例模式初始化  MobileMessageUtil
     * @param fromIdentify
     * @return
     */
    public static final MobileMessageUtil getInitialization(String fromIdentify) {
        MobileMessageUtil mobileMessageUtil = new MobileMessageUtil(fromIdentify);
        return mobileMessageUtil;
    }

    /**
     * 短信验证码
     * @param phone
     * @param code      验证码
     * @param minute    有效分钟数
     * @return
     */
    public boolean verificationCode(String phone, String code, String minute){
        content = new String[3];
        content[0] = code;
        content[1] = minute;
        content[2] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.VERIFICATION_CODE, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 合伙人申请成功提示
     * {1}：商品名称；{2}：合伙人等级
     * @param phone
     * @param skuName           商品名称
     * @param agentLevelName    合伙人等级
     * @return
     */
    public boolean partnerApplicationSuccess(String phone, String skuName, String agentLevelName){
        content = new String[3];
        content[0] = skuName;
        content[1] = agentLevelName;
        content[2] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.PARTNER_APPLICATION_SUCCESS, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 实名认证提交提醒
     * 【麦链商城】您的身份证资料提交成功，我们将在{1}个工作日内完成审核，请耐心等待
     * @param phone
     * @param days      审核工作日
     * @return
     */
    public boolean verifiedSubmitRemind(String phone, String days){
        content = new String[2];
        content[0] = days;
        content[1] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.VERIFIED_SUBMIT_REMIND, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 实名认证审核结果
     * @param phone
     * @param flag  是否通过
     * @return
     */
    public boolean certificationVerifyResult(String phone, boolean flag){
        content = new String[3];
        if (flag){
            content[0] = "通过审核";
            content[1] = "继续申请合伙人";
        }else {
            content[0] = "未通过审核";
            content[1] = "重新提交实名认证";
        }
        content[2] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.VERIFIED_COMPLETE, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 订单发货提醒
     * @param phone
     * @param orderCode     订单编号
     * @param shipName      快递公司名称
     * @param shipCode      快递单号
     * @return
     */
    public boolean goodsOrderShipRemind(String phone, String orderCode, String shipName, String shipCode){
        content = new String[4];
        content[0] = orderCode;
        content[1] = shipName;
        content[2] = shipCode;
        content[3] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.ORDER_SHIP_REMIND, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 有新的下级订单
     * @param phone
     * @return
     */
    public  boolean haveNewLowerOrder(String phone){
        content = new String[1];
        content[0] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.NEW_LOWER_ORDER, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 代理提现申请审核
     * @param phone
     * @param days     工作日天数
     * @return
     */
    public boolean withdrawRequestVerifyAgent(String phone, String days){
        content = new String[2];
        content[0] = days;
        content[1] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.WITHDRAW_REQUEST_VERIFY_AGENT, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 代理提现审核通过
     * @param phone
     * @param days      工作日天数
     * @param theWay    打款方式{1:微信 2:支付宝 3:银行卡}
     * @return
     */
    public boolean withdrawVerifyApproveAgent(String phone, String days, Integer theWay){
        content = new String[3];
        content[0] = days;
        content[1] = theWay==1?"微信":theWay==2?"支付宝":theWay==3?"银行卡":"";
        content[2] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.WITHDRAW_VERIFY_APPROVE_AGENT, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 提现申请审核拒绝-B端
     * @param phone
     * @param reason    拒绝原因
     * @return
     */
    public boolean withdrawVerifyRefuseAgent(String phone, String reason){
        content = new String[2];
        content[0] = reason;
        content[1] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.WITHDRAW_VERIFY_REFUSE_AGENT, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 补货成功提醒
     * @param phone
     * @param sendType  拿货类型
     * @param quantity  库存数量
     * @return
     */
    public boolean addStockSuccess(String phone, Integer sendType, String quantity){
        content = new String[3];
        if (1 == sendType){
            content[0] = "平台代发";
            content[1] = "在线库存已更新;" + quantity;
        }
        if (2 == sendType){
            content[0] = "自己发货";
            content[1] = "我们将尽快发货;" + quantity;
        }
        content[2] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.ADD_STOCK_SUCCESS, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 进入排单提醒
     * @param phone
     * @param orderCode   订单编号
     * @return
     */
    public boolean joinQueueOrder(String phone, String orderCode){
        content = new String[2];
        content[0] = orderCode;
        content[1] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.JOIN_QUEUE_ORDER, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 处理排单提醒
     * @param phone
     * @param orderCode     订单编号
     * @return
     */
    public boolean dealQueueOrderRemind(String phone, String orderCode, Integer sendType){
        content = new String[3];
        content[0] = orderCode;
        content[1] = sendType==1?"查看在线库存":sendType==2?"耐心等待发货":"";
        content[2] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.DEAL_QUEUE_ORDER_REMIND, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 库存不足预警
     * @param phone
     * @param skuName
     * @return
     */
    public boolean stockNotEnoughWarning(String phone, String skuName){
        content = new String[2];
        content[0] = skuName;
        content[1] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.STOCK_NOT_ENOUGH_WARNINT, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 新店铺订单提醒
     * @param phone
     * @return
     */
    public boolean newMallOrderRemind(String phone){
        content = new String[1];
        content[0] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.NEW_MALL_ORDER_REMIND, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 消费者下单提醒
     * @param phone
     * @param skuName   商品名称
     * @return
     */
    public boolean consumerOrderRemind(String phone, String skuName){
        content = new String[2];
        content[0] = skuName;
        content[1] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.CONSUMER_ORDER_REMIND, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 消费者发货提醒
     * @param phone
     * @param orderCode    订单编码
     * @return
     */
    public boolean consumerShipRemind(String phone, String orderCode){
        content = new String[2];
        content[0] = orderCode;
        content[1] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.CONSUMER_SHIP_REMIND, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 消费者交易成功提醒
     * @param phone
     * @param orderCode
     * @return
     */
    public boolean consumerConsumeSuccessRemind(String phone, String orderCode){
        content = new String[2];
        content[0] = orderCode;
        content[1] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.CONSUMER_CONSUME_SUCCESS_REMIND, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 现申请审核-C端
     * @param phone
     * @param days     工作日天数
     * @return
     */
    public boolean withdrawRequestVerifyCustomer(String phone, String days){
        content = new String[2];
        content[0] = days;
        content[1] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.WITHDRAW_REQUEST_VERIFY_CUSTOMER, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 消费者提现审核通过
     * @param phone
     * @param days      工作日天数
     * @param theWay    打款方式{1:微信 2:支付宝 3:银行卡}
     * @return
     */
    public boolean withdrawVerifyApproveCustomer(String phone, String days, Integer theWay){
        content = new String[3];
        content[0] = days;
        content[1] = theWay==1?"微信":theWay==2?"支付宝":theWay==3?"银行卡":"";
        content[2] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.WITHDRAW_VERIFY_APPROVE_CUSTOMER, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 提现申请审核拒绝-c
     * @param phone
     * @param reason    拒绝原因
     * @return
     */
    public boolean withdrawVerifyRefuseCustomer(String phone, String reason){
        content = new String[2];
        content[0] = reason;
        content[1] = EVENING_MESSAGE;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.WITHDRAW_VERIFY_REFUSE_CUSTOMER, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 线下支付提醒
     * @param phone
     * @param orderCode
     * @param amount
     * @param mes
     * @return
     */
    public static boolean offlinePaymentsRemind(String phone, String orderCode, String amount, String mes){
        String[] content = new String[3];
        content[0] = orderCode;
        content[1] = amount;
        content[2] = mes;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.OFFLINE_PAYMENTS_REMIND, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;

    }

    public static void main(String[] args){
        MobileMessageUtil.getInitialization("B").consumerConsumeSuccessRemind("18800175968","111111");
        MobileMessageUtil.getInitialization("B").certificationVerifyResult("18800175968",true);
    }
}
