package com.masiis.shop.common.util;

import com.masiis.shop.common.constant.SMSConstants;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * MobileMessageUtil
 *
 * @author wbj
 * @date 2016/3/9
 */
public class MobileMessageUtil {

    private Logger log = Logger.getLogger(this.getClass());

    /**
     * 短信結束語
     */
    private static String EVENING_MESSAGE = "";
    /**
    * 系统标识
    */
    private static String fromIdentify = "";

    private String[] content;

    private static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);

    /**
     * 判断是B端还是C端 短信
     * @param fromIdentify  B/C/""
     */
    private MobileMessageUtil(String fromIdentify){
        this.fromIdentify = fromIdentify;
        System.out.println("发送短信请求来源：" + this.fromIdentify + "端");
//        if ("B".equals(this.fromIdentify)){
//            EVENING_MESSAGE = "关注麦链合伙人公众账号，查看最新状态";
//        }
//        if ("C".equals(this.fromIdentify)){
//            EVENING_MESSAGE = "关注麦链商城众账号，查看最新状态";
//        }
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
        if ("B".equals(fromIdentify)){
            return sendMethod(phone, SMSConstants.VERIFICATION_CODE_B, content);
        }else {
            return sendMethod(phone, SMSConstants.VERIFICATION_CODE_C, content);
        }
    }

//    /**
//     * 支付成功提示
//     * @param phone
//     * @param skuName   商品名称
//     * @param levelName 合伙人等级名称
//     * @return
//     */
//    public boolean topaySuccess(String phone, String skuName, String levelName){
//        content = new String[3];
//        content[0] = skuName;
//        content[1] = levelName;
//        content[2] = EVENING_MESSAGE;
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
    public boolean partnerApplicationSuccess(String phone, String skuName, String agentLevelName){
        content = new String[3];
        content[0] = skuName;
        content[1] = agentLevelName;
        content[2] = EVENING_MESSAGE;
        return sendMethod(phone, SMSConstants.PARTNER_APPLICATION_SUCCESS, content);
    }

    /**
     * 实名认证提交提醒
     * 【麦链商城】您的身份证资料提交成功，我们将在{1}个工作日内完成审核，请耐心等待
     * @param phone
     * @param days      审核工作日
     * @return
     */
    public boolean verifiedSubmitRemind(String phone, String days){
//        content = new String[2];
//        content[0] = days;
//        content[1] = EVENING_MESSAGE;
//        return sendMethod(phone, SMSConstants.VERIFIED_SUBMIT_REMIND, content);
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
        return sendMethod(phone, SMSConstants.VERIFIED_COMPLETE, content);
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
        return sendMethod(phone, SMSConstants.ORDER_SHIP_REMIND, content);
    }

    /**
     * 有新的下级订单
     * @param phone
     * @param status 订单状态
     * @return
     */
    public boolean haveNewLowerOrder(String phone, Integer status){



//        content = new String[2];
//        if (status == BOrderStatus.MPS.getCode()){
//            content[0] = "目前您的库存不足，请及时补货。";
//        }else {
//            content[0] = "";
//        }
//        content[1] = EVENING_MESSAGE;
//        return sendMethod(phone, SMSConstants.NEW_LOWER_ORDER, content);
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
        return sendMethod(phone, SMSConstants.WITHDRAW_REQUEST_VERIFY_AGENT, content);
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
        return sendMethod(phone, SMSConstants.WITHDRAW_VERIFY_APPROVE_AGENT, content);
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
        return sendMethod(phone, SMSConstants.WITHDRAW_VERIFY_REFUSE_AGENT, content);
    }

    /**
     * 补货成功提醒
     * @param phone
     * @param sendType  拿货类型
     * @param quantity  库存数量
     * @return
     */
    public boolean addStockSuccess(String phone, Integer sendType, String quantity){
//        content = new String[3];
//        if (1 == sendType){
//            content[0] = "平台代发";
//            content[1] = "在线库存已更新";
//        }
//        if (2 == sendType){
//            content[0] = "自己发货";
//            content[1] = "我们将尽快发货";
//        }
//        content[2] = EVENING_MESSAGE;
//        return sendMethod(phone, SMSConstants.ADD_STOCK_SUCCESS, content);
        return true;
    }

    /**
     * 进入排单提醒
     * @param phone
     * @param orderCode   订单编号
     * @return
     */
    public boolean joinQueueOrder(String phone, String orderCode){
//        content = new String[2];
//        content[0] = orderCode;
//        content[1] = EVENING_MESSAGE;
//        return sendMethod(phone, SMSConstants.JOIN_QUEUE_ORDER, content);
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
        return sendMethod(phone, SMSConstants.DEAL_QUEUE_ORDER_REMIND, content);
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
        return sendMethod(phone, SMSConstants.STOCK_NOT_ENOUGH_WARNINT, content);
    }

    /**
     * 新店铺订单提醒
     * @param phone
     * @return
     */
    public boolean newMallOrderRemind(String phone){
        content = new String[1];
        content[0] = EVENING_MESSAGE;
        return sendMethod(phone, SMSConstants.NEW_MALL_ORDER_REMIND, content);
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
        return sendMethod(phone, SMSConstants.CONSUMER_ORDER_REMIND, content);
    }

    /**
     * 消费者发货提醒
     * @param phone
     * @param orderCode 订单编号
     * @param shipName  物流公司名称
     * @param shipCode  运单号
     * @return
     */
    public boolean consumerShipRemind(String phone, String orderCode, String shipName, String shipCode){
        content = new String[4];
        content[0] = orderCode;
        content[1] = shipName;
        content[2] = shipCode;
        content[3] = EVENING_MESSAGE;
        return sendMethod(phone, SMSConstants.CONSUMER_SHIP_REMIND, content);
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
        return sendMethod(phone, SMSConstants.CONSUMER_CONSUME_SUCCESS_REMIND, content);
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
        return sendMethod(phone, SMSConstants.WITHDRAW_REQUEST_VERIFY_CUSTOMER, content);
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
        return sendMethod(phone, SMSConstants.WITHDRAW_VERIFY_APPROVE_CUSTOMER, content);
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
        return sendMethod(phone, SMSConstants.WITHDRAW_VERIFY_REFUSE_CUSTOMER, content);
    }

    /**
     * 线下支付提醒
     * @param phone
     * @param orderCode  订单编码
     * @param amount     支付金额
     * @param mes        收款信息
     * @return
     */
    public boolean offlinePaymentsRemind(String phone, String orderCode, BigDecimal amount, String date, String mes){

        log.info("phone-----"+phone);
        log.info("orderCode-----"+orderCode);
        log.info("amount-----"+amount);
        log.info("date-----"+date);
        log.info("mes-----"+mes);

        content = new String[5];
        content[0] = orderCode;
        String a = numberFormat.format(amount);
        content[1] = a;
        content[2] = date;
        content[3] = mes;
        content[4] = SMSConstants.consumerHotline;
        return sendMethod(phone, SMSConstants.OFFLINE_PAYMENTS_REMIND, content);
    }

    /**
     * 试用发货提醒
     * @param phone
     * @param skuName   商品名称
     * @param shipName  物流公司
     * @param shipCode  运单号
     * @return
     */
    public boolean trialShipmentsRemind(String phone, String skuName, String shipName, String shipCode){
        content = new String[5];
        content[0] = skuName;
        content[1] = shipName + "：";
        content[2] = shipCode;
        content[3] = EVENING_MESSAGE;
        return sendMethod(phone, SMSConstants.TRIAL_SHIPMENTS_REMIND, content);
    }

    /**
     * 下级补货提醒（普通—补货）
     * @param phone     手机号码
     * @param skuName   商品名称
     * @param levelName 等级名称
     * @param nickName  用户昵称
     * @param count     数量
     * @param income    收入
     * @param haveStock 库存是否充足  true：充足，false：不足
     * @return
     */
    public boolean lowerReplenishmentRemind(String phone, String skuName, String levelName, String nickName, Integer count, BigDecimal income, boolean haveStock){
        content = new String[5];
        content[0] = skuName;
        content[1] = levelName;
        content[2] = nickName;
        content[3] = String.valueOf(count);
        content[4] = numberFormat.format(income);
        content[5] = "";
        if (!haveStock){
            content[5] = "您的库存不足，请及时补货。";
        }
        return sendMethod(phone, SMSConstants.LOWER_PEPLENISHMENT_REMIND, content);
    }

    /**
     * 下级升级提醒（普通—升级）
     * @param phone         手机号码
     * @param skuName       商品名称
     * @param orginalLevel  原等级
     * @param nickName      昵称
     * @param upGradeLevel  升级后等级
     * @param income        收入
     * @param haveStock     库存是否充足  true：充足，false：不足
     * @return
     */
    public boolean lowerGroupUpRemind(String phone, String skuName, String orginalLevel, String nickName, String upGradeLevel, BigDecimal income, boolean haveStock){
        content = new String[6];
        content[0] = skuName;
        content[1] = orginalLevel;
        content[2] = nickName;
        content[3] = upGradeLevel;
        content[4] = numberFormat.format(income);
        content[5] = "";
        if (!haveStock){
            content[5] = "您的库存不足，请及时补货。";
        }
        return sendMethod(phone, SMSConstants.LOWER_UPGROUP_REMIND, content);
    }

    /**
     * 被推荐人升级，上级提醒（推荐关系—升级）
     * @param phone         手机号码
     * @param skuName       商品名称
     * @param orginalLevel  原等级
     * @param nickName      昵称
     * @param upGradeLevel  升级后等级
     * @param income        收入
     * @param haveStock     库存是否充足  true：充足，false：不足
     * @return
     */
    public boolean refereeUpgradeUpRemind(String phone, String skuName, String orginalLevel, String nickName, String upGradeLevel, BigDecimal income, boolean haveStock){
        content = new String[6];
        content[0] = skuName;
        content[1] = orginalLevel;
        content[2] = nickName;
        content[3] = upGradeLevel;
        content[4] = numberFormat.format(income);
        content[5] = "";
        if (!haveStock){
            content[5] = "您的库存不足，请及时补货。";
        }
        return sendMethod(phone, SMSConstants.REFEREE_UPGRADE_UP_REMIND, content);
    }

    /**
     * 被推荐人升级，推荐人佣金提醒（推荐关系—升级）
     * @param phone         手机号码
     * @param skuName       商品名称
     * @param orginalLevel  原等级
     * @param nickName      用户昵称
     * @param upGradeLevel  升级后等级
     * @param reward        奖励
     * @return
     */
    public boolean refereeUpgradeRecommendRemind(String phone, String skuName, String orginalLevel, String nickName, String upGradeLevel, String reward){
        content = new String[5];
        content[0] = skuName;
        content[1] = orginalLevel;
        content[2] = nickName;
        content[3] = upGradeLevel;
        content[4] = reward;
        return sendMethod(phone, SMSConstants.REFEREE_UPGRADE_RECOMMEND_COMMISSION_REMIND, content);
    }

    /**
     * 被推荐人补货，上级提醒（推荐关系—补货）
     * @param phone     手机号码
     * @param skuName   商品名称
     * @param levelName 等级名称
     * @param nickName  用户昵称
     * @param count     数量
     * @param income    收入
     * @param haveStock 库存是否充足  true：充足，false：不足
     * @return
     */
    public boolean refereeAddstockUpRemind(String phone, String skuName, String levelName, String nickName, Integer count, BigDecimal income, boolean haveStock){
        content = new String[6];
        content[0] = skuName;
        content[1] = levelName;
        content[2] = nickName;
        content[3] = String.valueOf(count);
        content[4] = numberFormat.format(income);
        content[5] = "";
        if (!haveStock){
            content[5] = "您的库存不足，请及时补货。";
        }
        return sendMethod(phone, SMSConstants.REFEREE_ADDSTOCK_UP_REMIND, content);
    }

    /**
     * 被推荐人补货，推荐人佣金提醒（推荐关系—补货）
     * @param phone     手机号
     * @param skuName   商品名称
     * @param levelName 等级名称
     * @param nickName  用户昵称
     * @param count    数量
     * @param reward    奖励
     * @return
     */
    public boolean refereeAddstockRecomendRemind(String phone, String skuName, String levelName, String nickName,Integer count, String reward){
        content = new String[5];
        content[0] = skuName;
        content[1] = levelName;
        content[2] = nickName;
        content[3] = String.valueOf(count);
        content[4] = reward;
        return sendMethod(phone, SMSConstants.REFEREE_ADDSTOCK_RECOMEND_COMMISSION_REMIND, content);
    }

    /**
     * 上级收到被推荐人的下级加入通知（推荐关系-合伙订单）
     * @param phone             手机号码
     * @param skuName           商品名称
     * @param levelName         等级名称
     * @param income            收入
     * @param recommenNickName  推荐人昵称
     * @param haveStock         库存是否充足  true：充足，false：不足
     * @return
     */
    public boolean refereeLowerJoinUpNotice(String phone, String skuName, String levelName, BigDecimal income, String recommenNickName, boolean haveStock){
        content = new String[5];
        content[0] = skuName;
        content[1] = levelName;
        content[2] = numberFormat.format(income);
        content[3] = recommenNickName;
        content[4] = "";
        if (!haveStock){
            content[4] = "您的库存不足，请及时补货。";
        }
        return sendMethod(phone, SMSConstants.REFEREE_LOWER_JOIN_UP_NOTICE, content);
    }

    /**
     * 推荐佣金提醒（推荐关系-合伙订单）
     * @param phone     手机号码
     * @param nickName  用户昵称
     * @param skuName   商品名称
     * @param levelName 等级名称
     * @param reward    奖励
     * @return
     */
    public boolean recommendCommissionRemind(String phone, String nickName, String skuName, String levelName, String reward){
        content = new String[4];
        content[0] = nickName;
        content[1] = skuName;
        content[2] = levelName;
        content[3] = reward;
        return sendMethod(phone, SMSConstants.RECOMMEND_COMMISSION_REMIND, content);
    }

    /**
     * 下单后店主提醒（新增）
     * @param phone     手机号
     * @param skuName   商品名称
     * @param count     数量
     * @param income    收入
     * @return
     */
    public boolean orderShopRemind(String phone, String skuName, Integer count, BigDecimal income){
        content = new String[3];
        content[0] = skuName;
        content[1] = String.valueOf(count);
        content[2] = numberFormat.format(income);
        return sendMethod(phone, SMSConstants.ORDER_SHOP_REMIND, content);
    }

    /**
     * 下级合伙人加入提醒（普通-合伙）
     * @param phone     手机号码
     * @param skuName   商品名称
     * @param levelName 等级名称
     * @param income    收入
     * @param hasStock  库存是否充足 true：充足， false：不足
     * @return
     */
    public boolean lowerJoinRemind(String phone, String skuName, String levelName, BigDecimal income, boolean hasStock){
        content = new String[4];
        content[0] = skuName;
        content[1] = levelName;
        content[2] = numberFormat.format(income);
        content[3] = "";
        if (!hasStock){
            content[3] = "您的库存不足，请及时补货。";
        }
        return sendMethod(phone, SMSConstants.LOWER_JOIN_REMIND, content);
    }

    /**
     * 新合伙人加入
     * @param phone
     * @param skuName   商品名称
     * @return
     */
    public boolean newPartnerJoin(String phone, String skuName){
        content = new String[1];
        content[0] = skuName;
        return sendMethod(phone, SMSConstants.NEW_PARTNER_JOIN, content);
    }

    /**
     * 发送短息方法
     * @param phone     手机号码
     * @param code      模板ID
     * @param content   内容
     * @return
     */
    public boolean sendMethod(String phone, String code, String[] content){
        try{
            String[] smsRes = CCPRestSmsSDK.sendSMSWithResultMasiisShop(phone, code, content);
            if (!"000000".equals(smsRes[0])) {
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args){
        MobileMessageUtil.getInitialization("B").newPartnerJoin("18602496813","抗引力");
    }
}
