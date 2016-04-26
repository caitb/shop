package com.masiis.shop.common.util;

import com.masiis.shop.common.constant.SMSConstants;

/**
 * MobileMessageUtil
 *
 * @author ZhaoLiang
 * @date 2016/3/9
 */
public class MobileMessageUtil {

    private static String sign = "_SMS";

    /**
     * 发送验证码
     * @param phone
     * @param code      验证码
     * @param minute    有效分钟数
     * @return
     */
    public static boolean VerificationCode(String phone, String code, String minute){
        String[] content = new String[]{code,minute};
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.VERIFICATION_CODE, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

//    /**
//     * 支付短信
//     * @param phone             手机号码
//     * @param skuName           商品名称
//     * @param agentLevelName    代理等级名称
//     * @return
//     */
//    public static boolean sendMessageForToPay(String phone, String skuName, String agentLevelName) {
//        String[] content = new String[]{skuName,agentLevelName};
//        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.TOPAY_TEMPLETE_ID, content);
//        if (!"000000".equals(smsRes[0])) {
//            return false;
//        }
//        return true;
//    }

    /**
     * 合伙人申请成功提示
     * {1}：商品名称；{2}：合伙人等级
     * @param phone
     * @param skuName           商品名称
     * @param agentLevelName    合伙人等级
     * @return
     */
    public static boolean partnerApplicationSuccess(String phone, String skuName, String agentLevelName){
        String[] content = new String[]{skuName,agentLevelName};
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
    public static boolean verifiedSubmitRemind(String phone, String days){
        String[] content = new String[]{days};
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.VERIFIED_SUBMIT_REMIND, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 实名认证审核结果
     * @param phone
     * @param approved  true:通过审核  false:未通过审核
     * @return
     */
    public static boolean verifiedComplete(String phone, boolean approved){
        String[] content = new String[]{approved==true?"通过审核":"未通过审核", approved==true?"继续申请合伙人":"重新提交实名认证"};
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
    public static boolean goodsOrderShipRemind(String phone, String orderCode, String shipName, String shipCode){
        String[] content = new String[]{orderCode, shipName, shipCode};
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
    public static boolean haveNewLowerOrder(String phone){
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.NEW_LOWER_ORDER, null);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 发货订单
     * @param phone
     * @return
     */
    public static boolean shipOrder(String phone){
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.SHIP_ORDER, null);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 提现申请审核
     * @param phone
     * @param days     工作日天数
     * @return
     */
    public static boolean withdrawRequestVerify(String phone, String days){
        String[] content = new String[]{days};
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.WITHDRAW_REQUEST_VERIFY, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 提现审核通过
     * @param phone
     * @param days      工作日天数
     * @param theWay    打款方式{1:微信 2:支付宝 3:银行卡}
     * @return
     */
    public static boolean withdrawVerifyApprove(String phone, String days, Integer theWay){
        String[] content = new String[]{days,theWay==1?"微信":theWay==2?"支付宝":theWay==3?"银行卡":""};
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.WITHDRAW_VERIFY_APPROVE, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 提现申请审核拒绝
     * @param phone
     * @param reason    拒绝原因
     * @return
     */
    public static boolean withdrawVerifyRefuse(String phone, String reason){
        String[] content = new String[]{reason};
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.WITHDRAW_VERIFY_REFUSE, content);
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
    public static boolean withdrawVerifyRefuse_C(String phone, String reason){
        String[] content = new String[]{reason};
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.WITHDRAW_VERIFY_REFUSE_C, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 补货-平台代发
     * @param phone
     * @param quantity   补货数量
     * @return
     */
    public static boolean addStockByPlatform(String phone, String quantity){
        String[] content = new String[]{quantity};
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.ADD_STOCK_PLATFORM, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 补货-自己发货
     * @param phone
     * @return
     */
    public static boolean addStockByUserself(String phone){
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.ADD_STOCK_SELF, null);
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
    public static boolean joinQueueOrder(String phone, String orderCode){
        String[] content = new String[]{orderCode};
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.JOIN_QUEUE_ORDER, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 处理排单提醒-平台代发
     * @param phone
     * @param orderCode     订单编号
     * @return
     */
    public static boolean dealQueueOrderPlatform(String phone, String orderCode){
        String[] content = new String[]{orderCode};
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.DEAL_QUEUE_ORDER_PLATFORM, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 处理排单提醒-自己发货
     * @param phone
     * @param orderCode     订单编号
     * @param shipName      快递公司名称
     * @param shipCode      快递单号
     * @return
     */
    public static boolean dealQueueOrderSelf(String phone, String orderCode, String shipName, String shipCode){
        String[] content = new String[]{orderCode, shipName, shipCode};
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.DEAL_QUEUE_ORDER_SELF, content);
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
    public static boolean stockNotEnoughWarning(String phone, String skuName){
        String[] content = new String[]{skuName};
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
    public static boolean newMallOrderRemind(String phone){
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.NEW_MALL_ORDER_REMIND, null);
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
    public static boolean consumerOrderRemind(String phone, String skuName){
        String[] content = new String[]{skuName};
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
    public static boolean consumerShipRemind(String phone, String orderCode){
        String[] content = new String[]{orderCode};
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
    public static boolean consumerConsumeSuccessRemind(String phone, String orderCode){
        String[] content = new String[]{orderCode};
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, SMSConstants.CONSUMER_CONSUME_SUCCESS_REMIND, content);
        if (!"000000".equals(smsRes[0])) {
            return false;
        }
        return true;
    }
}
