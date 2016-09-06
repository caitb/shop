package com.masiis.shop.web.common.utils.notice;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.beans.wx.notice.*;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComWxUser;
import com.masiis.shop.dao.po.PfSysMessage;
import com.masiis.shop.web.platform.service.message.PfSysMessageService;
import com.masiis.shop.web.common.service.WxUserService;
import com.masiis.shop.web.common.utils.ApplicationContextUtil;
import com.masiis.shop.web.common.utils.wx.WxCredentialUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Date 2016/8/19
 * @Author lzh
 */
public class AppPfNoticeUtils {
    private static Logger log = Logger.getLogger(AppPfNoticeUtils.class);
    private PfSysMessageService pfSysMessageService = (PfSysMessageService) ApplicationContextUtil.getBean("pfSysMessageService");

    private static class Holder {
        private static final AppPfNoticeUtils INSTANCE = new AppPfNoticeUtils();
    }
    private AppPfNoticeUtils() {
    }
    // 单例懒加载
    public static final AppPfNoticeUtils getInstance() {
        return Holder.INSTANCE;
    }

    private LinkedBlockingQueue<String> pfSysMessageQueue = null;
    private Thread queueThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String param = null;
                    try {
                        param = pfSysMessageQueue.take();
                        pfSysMessageService.handleMessageSend(param);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        try {
                            pfSysMessageQueue.put(param);
                        } catch (InterruptedException e1) {
                            log.error(e1.getMessage(), e1);
                        }
                    }
                }
            }
        });
    {
        pfSysMessageQueue = new LinkedBlockingQueue<>();
        queueThread.start();
    }

    public void checkQueueThread(){
        queueThread.isAlive();
    }
    public LinkedBlockingQueue<String> getMessageQueue(){
        return pfSysMessageQueue;
    }

    public Boolean unifyNotice(PfSysMessage message){
        Boolean result = true;
        try{
            if(message.getUserId() == null || message.getUserId().longValue() <= 0l){
                throw new BusinessException("查不到用户");
            }
            pfSysMessageService.handleMessageAdd(message);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = false;
        }
        log.error("发送app消息失败");
        return result;
    }

    /**
     * 合伙人申请成功提示
     *
     * @param user
     * @param params 参数数组,(第一个,支付金额;第二个,支付方式;第三个,支付详情;第四个,支付时间)
     * @return  返回是否成功调用
     */
    public Boolean partnerApplySuccessNotice(ComUser user, String[] params) {
        String messageContent = "恭喜您，合伙人申请支付成功\n"
                + "支付金额:" + params[0] + "\n"
                + "支付方式:" + params[1] + "\n"
                + "支付详情:" + params[2] + "\n"
                + "支付时间:" + params[3] + "\n"
                + "恭喜您已成为合伙人。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "下级代理加入通知\n"
                + "手机号:" + user.getMobile() + "\n"
                + "加入时间:" + joinTime + "\n"
                + "下线代理昵称:" + user.getWxNkName() + "\n"
                + "麦链合伙人，感谢有您!";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(pUser.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "您有一个新的下级\n"
                + "手机号:" + user.getMobile() + "\n"
                + "加入时间:" + joinTime + "\n"
                + "下线代理昵称:" + user.getWxNkName() + "\n"
                + "此人是通过升级方式成为您的下级，您需要给他的原上级一次性奖励，具体金额请线下沟通。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(pUser.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "新下级加入通知\n"
                + "手机号:" + user.getMobile() + "\n"
                + "加入时间:" + params[1] + "\n"
                + "下线代理昵称:" + user.getWxNkName() + "\n"
                + "您有一个新的下级成功加入，ta的推荐人是" + params[0] + "。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "实名认证\n";

        PfSysMessage message = pfSysMessageService.initBean();
        if (isSuccess) {
            messageContent += "认证详情:恭喜您认证通过\n"
                            + "认证结果:认证通过\n"
                            + "您的实名认证已经通过，请继续申请合伙人!";
        } else {
            messageContent += "认证详情:非常抱歉，由于您提供的身份信息有误，实名认证失败，请重新认证。\n"
                            + "认证结果:认证失败\n"
                            + "认证失败，请重新认证。";
        }

        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
    }

    /**
     * 实名认证申请提交通知
     *
     * @param user
     * @param params    (第一个,手机号; 第二个,日期)
     * @return  返回是否成功调用
     */
    public Boolean partnerRealNameSubmit(ComUser user, String[] params) {
        String messageContent = "实名认证已经提交\n"
                + "用户名:" + user.getWxNkName() + "\n"
                + "手机号:" + params[0] + "\n"
                + "日期:" + params[1] + "\n"
                + "您的实名信息已经提交，审核将会在1个工作日完成。请耐心等待审核结果!";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "您好，您的" + params[0] + params[1] + "订单已发货\n"
                    + "订单编号:" + params[2] + "\n"
                    + "快递公司:" + params[3] + "\n"
                    + "快递单号:" + params[4] + "\n"
                    + "请注意查收。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
    }

    /**
     * 代理订单发货通知
     *
     * @param user     通知对象
     * @param params   (1,商品名称;2,订单编号(不是id);3,快递公司;4,快递单号)
     * @return  返回是否成功调用
     */
    public Boolean trailOrderShippedNotice(ComUser user, String[] params) {
        String messageContent = "您好，您的" + params[0] + "试用订单已发货\n"
                + "订单编号:" + params[1] + "\n"
                + "快递公司:" + params[2] + "\n"
                + "快递单号:" + params[3] + "\n"
                + "请注意查收。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "您有新的合伙人订单,请到订单管理中查看\n"
                + "订单编号:" + params[0] + "\n"
                + "时间:" + params[1] + "\n";
        if(!hasInventory) {
            messageContent += "目前您的库存不足，为了不影响下级销售，请及时补货。";
        }

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "您有新的补货订单,请到订单管理中查看\n"
                + "订单编号:" + params[0] + "\n"
                + "时间:" + params[1] + "\n";
        if(!hasInventory) {
            messageContent += "目前您的库存不足，为了不影响下级销售，请及时补货。";
        }

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "亲，您在我们商城买的宝贝已经确认收货。\n"
                + "订单号:" + params[0] + "\n"
                + "商品名称:" + params[1] + "\n"
                + "下单时间:" + params[2] + "\n"
                + "发货时间:" + params[3] + "\n"
                + "确认收货时间:" + params[4] + "\n"
                + "感谢您的支持与厚爱。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "亲，您在我们商城买的宝贝7天自动确认收货。\n"
                + "订单号:" + params[0] + "\n"
                + "商品名称:" + params[1] + "\n"
                + "下单时间:" + params[2] + "\n"
                + "发货时间:" + params[3] + "\n"
                + "确认收货时间:" + params[4] + "\n"
                + "感谢您的支持与厚爱。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = null;
        if (isSuccess) {
            messageContent = "您好，您的提现申请已经提交\n"
                    + "提现金额:" + params[0] + "\n"
                    + "提现时间:" + params[1] + "\n"
                    + "提现状态:" + params[2] + "\n"
                    + "审核结果会在2个工作日内完成，请耐心等待!";
        } else {
            messageContent = "您好，您的提现申请被拒绝了\n"
                    + "提现金额:" + params[0] + "\n"
                    + "提现时间:" + params[1] + "\n"
                    + "提现状态:" + params[2] + "\n"
                    + "拒绝原因：" + params[3] + "，如有问题请联系客服!";
        }

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
    }

    /**
     * 提现申请结果通知
     *
     * @param user
     * @param params (1,提现商户(代理商名称);2,提现金额;3,提现账户;4,处理时间)
     * @return  返回是否成功调用
     */
    public Boolean pfExtractApplySuccess(ComUser user, String[] params) {
        String messageContent = "提现结果通知\n"
                + "提现商户:" + params[0] + "\n"
                + "提现金额:" + params[1] + "\n"
                + "提现账户:" + params[2] + "\n"
                + "处理时间:" + params[3] + "\n"
                + "您好，您的提现申请已经通过审核，汇款将会在1个工作日内完成，请注意查收";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
    }

    /**
     * 补货成功提醒-平台代发
     *
     * @param user
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单状态)
     * @return  返回是否成功调用
     */
    public Boolean replenishmentByPlatForm(ComUser user, String[] params) {
        String messageContent = "补货成功\n"
                + "订单名称:" + params[0] + "\n"
                + "订单价格:" + params[1] + "\n"
                + "订单数量:" + params[2] + "\n"
                + "订单类型:补货\n"
                + "订单状态:" + params[3] + "\n"
                + "您的在线库存已更新。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
    }

    /**
     * 补货成功提醒-自己发货
     *
     * @param user
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单状态)
     * @return  返回是否成功调用
     */
    public Boolean replenishmentBySelf(ComUser user, String[] params) {
        String messageContent = "您的补货订单支付成功\n"
                + "订单名称:" + params[0] + "\n"
                + "订单价格:" + params[1] + "\n"
                + "订单数量:" + params[2] + "\n"
                + "订单类型:补货\n"
                + "订单状态:" + params[3] + "\n"
                + "您的补货订单支付成功，我们将尽快发货请耐心等待收货。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
    }

    /**
     * 订单进入排单提醒
     *
     * @param user  发送用户对象
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单类型;5,订单状态)
     * @return  返回是否成功调用
     */
    public Boolean orderInQueue(ComUser user, String[] params) {
        String messageContent = "恭喜您支付成功\n"
                + "订单名称:" + params[0] + "\n"
                + "订单价格:" + params[1] + "\n"
                + "订单数量:" + params[2] + "\n"
                + "订单类型:补货\n"
                + "订单状态:" + params[3] + "\n"
                + "由于库存不足，您的订单已进入排单，请耐心等待。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
    }

    /**
     * 处理排单提醒-平台代发
     *
     * @param user  发送用户对象
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单类型;5,订单状态)
     * @return  返回是否成功调用
     */
    public Boolean dealWithOrderInQueueByPlatForm(ComUser user, String[] params) {
        String messageContent = "恭喜您支付成功\n"
                + "订单名称:" + params[0] + "\n"
                + "订单价格:" + params[1] + "\n"
                + "订单数量:" + params[2] + "\n"
                + "订单类型:" + params[3] + "\n"
                + "订单状态:" + params[4] + "\n"
                + "您的排单订单已处理，在线库存已更新。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
    }

    /**
     * 处理排单提醒-自己发货
     *
     * @param user
     * @param params (1,订单名称;2,订单价格;3,订单数量;4,订单类型;5,订单状态)
     * @return  返回是否成功调用
     */
    public Boolean dealWithOrderInQueueBySelf(ComUser user, String[] params) {
        String messageContent = "您的排单订单已发货\n"
                + "订单名称:" + params[0] + "\n"
                + "订单价格:" + params[1] + "\n"
                + "订单数量:" + params[2] + "\n"
                + "订单类型:" + params[3] + "\n"
                + "订单状态:" + params[4] + "\n"
                + "您的排单订单已发货，请耐心等待收货。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
    }

    /**
     * 处理排单提醒-提醒上级下级进入排单,需要补货
     *
     * @param user
     * @param params (1,订单名称(可传商品名称);2,订单价格;3,订单数量;4,订单类型;5,订单状态)
     * @return  返回是否成功调用
     */
    public Boolean dealWithOrderInQueueByUp(ComUser user, String[] params, String url) {
        String messageContent = "您有新的订单。\n"
                + "订单名称:" + params[0] + "\n"
                + "订单价格:" + params[1] + "\n"
                + "订单数量:" + params[2] + "\n"
                + "订单类型:" + params[3] + "\n"
                + "订单状态:" + params[4] + "\n"
                + "您的库存不足，请及时补货，以免影响您的下级销售。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "您有下级补货订单。\n"
                + "订单名称:" + params[0] + "\n"
                + "订单价格:" + params[1] + "\n"
                + "订单数量:" + params[2] + "\n"
                + "订单类型:" + params[3] + "\n"
                + "订单状态:" + params[4] + "\n"
                + "您的下级 " + params[5] + " 已经补货成功。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = user.getRealName() + "你好，您有商品的库存数量低于10件，请及时补充库存。\n"
                + "商品编码:" + params[0] + "\n"
                + "商品名称:" + params[1] + "\n"
                + "库存数量:" + params[2] + "\n";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "你有新的店铺订单啦\n"
                + "收件人:" + params[0] + "\n"
                + "联系电话:" + params[1] + "\n"
                + "收货地址:" + params[3] + "\n"
                + "购物清单:" + params[4] + "\n"
                + "备注:" + params[4] + "\n";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(pUser.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "你有新的店铺订单啦\n"
                + "收件人:" + params[0] + "\n"
                + "联系电话:" + params[1] + "\n"
                + "收货地址:" + params[3] + "\n"
                + "购物清单:" + params[4] + "\n"
                + "备注:" + params[4] + "\n"
                + "您的库存不足，无法发货，请及时补货。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(pUser.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "订单未付款提醒\n"
                + "订单号:" + params[0] + "\n"
                + "创建时间:" + DateUtil.Date2String(creatTime,DateUtil.SQL_TIME_FMT) + "\n"
                + "商品明细:" + params[1] + "\n"
                + "您选择的是线下支付，请您在" + DateUtil.insertDay(creatTime) + "前付款以免过期。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
    }

    /**
     * 升级订单支付成功提示
     *
     * @param user
     * @param params 参数数组,(第一个,支付金额;第二个,支付方式;第三个,支付详情;第四个,支付时间)
     * @return  返回是否成功调用
     */
    public Boolean upgradePaySuccessNotice(ComUser user, String[] params) {
        String messageContent = "您好，您的升级订单支付成功。\n"
                + "支付金额:" + params[0] + "\n"
                + "支付方式:" + params[1] + "\n"
                + "支付详情:" + params[2] + "\n"
                + "支付时间:" + params[3] + "\n"
                + "恭喜您已升级成功，感谢您的使用。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "您有一个代理申请升级\n"
                + "姓名:" + params[0] + "\n"
                + "现在等级:" + params[1] + "\n"
                + "申请等级:" + params[2] + "\n"
                + "申请时间:" + params[3] + "\n"
                + "为了保证您的利益，请在" + params[4] + "之前及时处理，逾期默认不升级。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(pUser.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "您的升级申请已提交，请耐心等待审核。\n"
                + "姓名:" + params[0] + "\n"
                + "现在等级:" + params[1] + "\n"
                + "申请等级:" + params[2] + "\n"
                + "申请时间:" + params[3] + "\n"
                + "审核通过后需要您继续支付，点击查看升级单，了解最新状态。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "您的升级申请已审核通过。\n"
                + "姓名:" + params[0] + "\n"
                + "现在等级:" + params[1] + "\n"
                + "申请等级:" + params[2] + "\n"
                + "申请时间:" + params[3] + "\n"
                + "您的升级申请已审核通过，请在" + params[4] + "前支付完成，逾期将取消订单。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "您的升级申请已取消。\n"
                + "姓名:" + params[0] + "\n"
                + "现在等级:" + params[1] + "\n"
                + "申请等级:" + params[2] + "\n"
                + "申请时间:" + params[3] + "\n"
                + "您的升级申请已取消，升级未成功。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "您的下级升级申请已取消\n"
                + "姓名:" + params[0] + "\n"
                + "现在等级:" + params[1] + "\n"
                + "申请等级:" + params[2] + "\n"
                + "申请时间:" + params[3] + "\n"
                + "您的下级升级申请已取消，升级未成功。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(pUser.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "您有一单下级升级申请超过2天未处理，系统默认处理为不升级。\n"
                + "服务项目:升级申请\n"
                + "取消时间:" + params[0] + "\n"
                + "处理方式:超过2天未处理，系统默认不升级。\n"
                + "您的下级将会收到支付提醒，支付成功后ta将不是您的下级，您可以获得一次性奖励，请进入系统查看。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(pUser.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "您有一笔升级申请单超过 " + days + " 天未支付，系统已经为您取消升级申请单。\n"
                + "服务项目:升级申请\n"
                + "取消时间:" + params[0] + "\n"
                + "处理方式:超过" + days + "天未支付，系统取消升级申请单。\n"
                + "您的升级申请单已取消。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "";

        if(isApply) {
            messageContent += "您好，您的下级 " + params[0] + " 已成功升级。\n"
                    + "申请内容:" + params[0] + "的升级申请\n"
                    + "申请结果:下级升级成功\n"
                    + "您的下级已升级成功，ta已不是您的下级，您可以获得一次性奖励。";
        } else {
            messageContent += "您好，您的下级 " + params[0] + " 的升级申请单中，您已选择不升级。\n"
                    + "申请内容:" + params[0] + "的升级申请\n"
                    + "申请结果:不升级\n"
                    + "您的下级将会收到支付提醒，支付成功后ta将不是您的下级，您可以获得一次性奖励。";
        }

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(pUser.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "您下级升级成功。\n"
                + "用户昵称:" + params[0] + "\n"
                + "最新等级:" + params[1] + "\n"
                + "生效时间:" + params[2] + "\n"
                + "升级成功后，您仍是ta的上级。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(pUser.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
    }

    /**
     * 推荐成功通知
     *
     * @param pUser 推荐人用户对象
     * @param params    (第一个,被推荐人)
     * @return  是否调用成功
     */
    public Boolean recommendSuccessNotice(ComUser pUser, String[] params){
        String messageContent = "恭喜您，推荐成功。\n"
                + "推荐人:" + pUser.getWxNkName() + "\n"
                + "被推荐人:" + params[0] + "\n"
                + "您已成功成为ta的推荐人，可以获得推荐奖励。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(pUser.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "恭喜您，获得了一笔新的推荐佣金。\n"
                + "佣金金额:" + params[0] + "\n"
                + "时间:" + params[1] + "\n"
                + "您可以在\"获取奖励订单\"中查看。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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
        String messageContent = "您有一笔推荐佣金支出。\n"
                + "佣金金额:" + params[0] + "\n"
                + "时间:" + params[1] + "\n"
                + "您需要给 " + params[2] + " 的推荐人 " + params[3] + " 支付一笔推荐佣金，系统已经扣除。";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
    }

    private Boolean orderUnpayCancelNoitce(ComUser user, String[] params, String url, String remark){
        String messageContent = "您的订单已取消。\n"
                + "订单编号:" + params[0] + "\n"
                + "商品名称:" + params[1] + "\n"
                + "商品数量:" + params[2] + "\n"
                + "订单金额:" + params[3] + "\n"
                + remark;

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
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

    /**
     * 新成员加入
     *
     * @param user  发送消息对象
     * @param params    (第一个,合伙产品名称; 第二个合伙等级名称; 第三个,新加入合伙人姓名; 第四个,时间)
     * @return
     */
    public Boolean newMemberJoinNotice(ComUser user, String[] params){
        String messageContent = "老大，麦链来新人啦！\n"
                + "姓名:" + params[0] + "-" + params[1] + "-" + params[2] + "\n"
                + "时间:" + params[3] + "\n"
                + "我们的团队又加入1位合伙人！恭喜恭喜！";

        PfSysMessage message = pfSysMessageService.initBean();
        message.setUserId(user.getId());
        message.setContent(messageContent);
        return unifyNotice(message);
    }

}
