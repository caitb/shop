package com.masiis.shop.web.common.utils.notice;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.common.utils.wx.WxPFNoticeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * @Date 2016/8/19
 * @Author lzh
 */
public class SysNoticeUtils {
    private static Logger log = Logger.getLogger(SysNoticeUtils.class);
    private String noticeConfig;
    {
        try {
            noticeConfig = PropertiesUtils.getStringValue("sys_notice_config");
        } catch (Exception e){
            log.error("获取消息发送配置失败,默认不发消息");
        }
        if(StringUtils.isBlank(noticeConfig)){
            noticeConfig = "0";
        }
    }

    private static class Holder {
        private static final SysNoticeUtils INSTANCE = new SysNoticeUtils();
    }
    private SysNoticeUtils() {
    }
    // 单例懒加载
    public static final SysNoticeUtils getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 合伙人申请成功提示
     *
     * @param user
     * @param params 参数数组,(第一个,支付金额;第二个,支付方式;第三个,支付详情;第四个,支付时间)
     * @return  返回是否成功调用
     */
    public Boolean partnerApplySuccessNotice(ComUser user, String[] params) {
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().partnerApplySuccessNotice(user, params);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().partnerApplySuccessNotice(user, params);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().partnerJoinNotice(pUser, user, joinTime);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().partnerJoinNotice(pUser, user, joinTime);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().partnerJoinByUpgradeNotice(pUser, user, joinTime, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().partnerJoinByUpgradeNotice(pUser, user, joinTime, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().partnerJoinByRecommendNotice(pUser, user, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().partnerJoinByRecommendNotice(pUser, user, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().partnerRealNameAuthNotice(user, isSuccess, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().partnerRealNameAuthNotice(user, isSuccess, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }

    /**
     * 实名认证申请提交通知
     *
     * @param user
     * @param params    (第一个,手机号; 第二个,日期)
     * @return  返回是否成功调用
     */
    public Boolean partnerRealNameSubmit(ComUser user, String[] params) {
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().partnerRealNameSubmit(user, params);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().partnerRealNameSubmit(user, params);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().orderShippedNotice(user, params, orderUrl);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().orderShippedNotice(user, params, orderUrl);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }

    /**
     * 代理订单发货通知
     *
     * @param user     通知对象
     * @param params   (1,商品名称;2,订单编号(不是id);3,快递公司;4,快递单号)
     * @return  返回是否成功调用
     */
    public Boolean trailOrderShippedNotice(ComUser user, String[] params) {
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().trailOrderShippedNotice(user, params);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().trailOrderShippedNotice(user, params);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().newOrderNotice(user, params, orderUrl, hasInventory);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().newOrderNotice(user, params, orderUrl, hasInventory);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().newSupplementOrderNotice(user, params, orderUrl, hasInventory);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().newSupplementOrderNotice(user, params, orderUrl, hasInventory);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().orderConfirmNotice(user, params);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().orderConfirmNotice(user, params);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().orderAutoConfirmNotice(user, params);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().orderAutoConfirmNotice(user, params);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().pfExtractApply(user, params, isSuccess);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().pfExtractApply(user, params, isSuccess);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }

    /**
     * 提现申请结果通知
     *
     * @param user
     * @param params (1,提现商户(代理商名称);2,提现金额;3,提现账户;4,处理时间)
     * @return  返回是否成功调用
     */
    public Boolean pfExtractApplySuccess(ComUser user, String[] params) {
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().pfExtractApplySuccess(user, params);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().pfExtractApplySuccess(user, params);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }

    /**
     * 补货成功提醒-平台代发
     *
     * @param user
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单状态)
     * @return  返回是否成功调用
     */
    public Boolean replenishmentByPlatForm(ComUser user, String[] params) {
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().replenishmentByPlatForm(user, params);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().replenishmentByPlatForm(user, params);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }

    /**
     * 补货成功提醒-自己发货
     *
     * @param user
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单状态)
     * @return  返回是否成功调用
     */
    public Boolean replenishmentBySelf(ComUser user, String[] params) {
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().replenishmentBySelf(user, params);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().replenishmentBySelf(user, params);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }

    /**
     * 订单进入排单提醒
     *
     * @param user
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单类型;5,订单状态)
     * @return  返回是否成功调用
     */
    public Boolean orderInQueue(ComUser user, String[] params) {
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().orderInQueue(user, params);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().orderInQueue(user, params);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }

    /**
     * 处理排单提醒-平台代发
     *
     * @param user
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单类型;5,订单状态)
     * @return  返回是否成功调用
     */
    public Boolean dealWithOrderInQueueByPlatForm(ComUser user, String[] params) {
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().dealWithOrderInQueueByPlatForm(user, params);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().dealWithOrderInQueueByPlatForm(user, params);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }

    /**
     * 处理排单提醒-自己发货
     *
     * @param user
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单类型;5,订单状态)
     * @return  返回是否成功调用
     */
    public Boolean dealWithOrderInQueueBySelf(ComUser user, String[] params) {
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().dealWithOrderInQueueBySelf(user, params);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().dealWithOrderInQueueBySelf(user, params);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }

    /**
     * 处理排单提醒-提醒上级下级进入排单,需要补货
     *
     * @param user
     * @param params (1,订单名称(可传商品名称);2,订单价格;3,订单数量;4,订单类型;5,订单状态)
     * @return  返回是否成功调用
     */
    public Boolean dealWithOrderInQueueByUp(ComUser user, String[] params, String url) {
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().dealWithOrderInQueueByUp(user, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().dealWithOrderInQueueByUp(user, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }


    /**
     * 下级补货成功通知上级
     *
     * @param user  上级代理商用户对象
     * @param params (1,订单名称(可传商品名称);2,订单价格;3,订单数量;4,订单类型;5,订单状态; 6,下级名称)
     * @Param url   查看补货订单url
     * @return  返回是否成功调用
     */
    public Boolean supplementSuccessToUp(ComUser user, String[] params, String url) {
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().supplementSuccessToUp(user, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().supplementSuccessToUp(user, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().inventoryShortageNotice(user, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().inventoryShortageNotice(user, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().newShopOrderNotice(pUser, params, orderUrl);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().newShopOrderNotice(pUser, params, orderUrl);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().newShopOrderNoticeNoStock(pUser, params, orderUrl);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().newShopOrderNoticeNoStock(pUser, params, orderUrl);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }

    /**
     * 线下支付提醒
     *
     * @param user  目标人
     * @param params    (第一个,订单号; 第二个,商品明细(如:抗引力boss级合伙人订单))
     * @param orderUrl  通知的订单详情页
     * @return  返回是否成功调用
     */
    public Boolean offLinePayNotice(ComUser user, String[] params, Date creatTime, String orderUrl){
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().offLinePayNotice(user, params, creatTime, orderUrl);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().offLinePayNotice(user, params, creatTime, orderUrl);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }

    /**
     * 升级订单支付成功提示
     *
     * @param user
     * @param params 参数数组,(第一个,支付金额;第二个,支付方式;第三个,支付详情;第四个,支付时间)
     * @return  返回是否成功调用
     */
    public Boolean upgradePaySuccessNotice(ComUser user, String[] params) {
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().upgradePaySuccessNotice(user, params);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().upgradePaySuccessNotice(user, params);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().subLineUpgradeApplyNotice(pUser, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().subLineUpgradeApplyNotice(pUser, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().upgradeApplySubmitNotice(user, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().upgradeApplySubmitNotice(user, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().upgradeApplyAuditPassNotice(user, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().upgradeApplyAuditPassNotice(user, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().subUpgradeApplyCancelNotice(user, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().subUpgradeApplyCancelNotice(user, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().subLineUpgradeApplyCancelNotice(pUser, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().subLineUpgradeApplyCancelNotice(pUser, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().upgradeApplyNotHandleNotice(pUser, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().upgradeApplyNotHandleNotice(pUser, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().upgradeApplyTwoDayNotPayNotice(user, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().upgradeApplyTwoDayNotPayNotice(user, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().upgradeApplySevenDayNotPayNotice(user, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().upgradeApplySevenDayNotPayNotice(user, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().upgradeApplyResultNotice(pUser, params, url, isApply);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().upgradeApplyResultNotice(pUser, params, url, isApply);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().upgradeResultNoticeUpLine(pUser, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().upgradeResultNoticeUpLine(pUser, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }

    /**
     * 推荐成功通知
     *
     * @param pUser 推荐人用户对象
     * @param params    (第一个,被推荐人)
     * @return  是否调用成功
     */
    public Boolean recommendSuccessNotice(ComUser pUser, String[] params){
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().recommendSuccessNotice(pUser, params);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().recommendSuccessNotice(pUser, params);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().recommendProfitNotice(user, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().recommendProfitNotice(user, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().recommendProfitOutNotice(user, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().recommendProfitOutNotice(user, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().orderUnpayTwoDayCancelNotice(user, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().orderUnpayTwoDayCancelNotice(user, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().orderUnpayThreeDayCancelNotice(user, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().orderUnpayThreeDayCancelNotice(user, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
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
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().orderUnpaySevenDayCancelNotice(user, params, url);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().orderUnpaySevenDayCancelNotice(user, params, url);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }

    /**
     * 新成员加入
     *
     * @param user  发送消息对象
     * @param params    (第一个,合伙产品名称; 第二个合伙等级名称; 第三个,新加入合伙人姓名; 第四个,时间)
     * @return
     */
    public Boolean newMemberJoinNotice(ComUser user, String[] params){
        boolean result = false;
        if("1".equals(noticeConfig)) {
            // 发送微信消息
            result = WxPFNoticeUtils.getInstance().newMemberJoinNotice(user, params);
        } else if("2".equals(noticeConfig)) {
            // 发送app消息
            result = AppPfNoticeUtils.getInstance().newMemberJoinNotice(user, params);
        } else {
            log.error("发送消息配置类型错误!");
        }
        return result;
    }

}
