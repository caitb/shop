package com.masiis.shop.admin.service.order;

import com.masiis.shop.admin.service.user.UpgradeNoticeService;
import com.masiis.shop.admin.service.user.UpgradeWechatNewsService;
import com.masiis.shop.admin.utils.WxPFNoticeUtils;
import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.platform.BOrderType;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BOrderUpgradeDetail;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * BOrderPayEndMessageService
 *
 * @author ZhaoLiang
 * @date 2016/6/19
 */
@Service
public class BOrderPayEndMessageService {

    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private BOrderService bOrderService;
    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;
    @Resource
    PfBorderRecommenRewardService pfBorderRecommenRewardService;
    @Resource
    private UpgradeNoticeService upgradeNoticeService;
    @Resource
    private PfUserUpgradeNoticeService userUpgradeNoticeService;
    @Resource
    private UpgradeWechatNewsService upgradeWechatNewsService;

    /**
     * 支付完成推送消息
     */
    public void payEndPushMessage(PfBorderPayment pfBorderPayment) {
        //金额格式化
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
        //时间统一格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        //订单数据
        PfBorder pfBorder = bOrderService.getPfBorderById(pfBorderPayment.getPfBorderId());
        //下单人数据
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        //上级用户数据
        ComUser pComUser = null;
        if (pfBorder.getUserPid() > 0) {
            pComUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserPid());
        }
        //订单商品数据
        List<PfBorderItem> pfBorderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
        //代理等级数据
        ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(pfBorderItems.get(0).getAgentLevelId());
        logger.info("****************************处理推送通知***********************************************");
        if (pfBorder.getOrderStatus().equals(BOrderStatus.MPS.getCode())) {
            if (pfBorder.getOrderType().equals(BOrderType.UPGRADE.getCode())) {
                logger.info("------升级订单进入排单发送微信------");
                //发送微信通知
                PfUserUpgradeNotice pfUserUpgradeNotice = userUpgradeNoticeService.selectByPfBorderId(pfBorder.getId());
                BOrderUpgradeDetail upgradeDetail = upgradeNoticeService.getUpgradeNoticeInfo(pfUserUpgradeNotice.getId());
                Boolean bl = upgradeWechatNewsService.upgradeOrderPaySuccssEntryWaiting(pfBorder, pfBorderPayment, upgradeDetail);
            } else {
                pushMessageMPS(comUser, pComUser, pfBorder, pfBorderItems, simpleDateFormat, numberFormat);
            }
        } else if (pfBorder.getPayStatus().intValue() == 1) {
            //订单类型(0代理1补货2拿货)
            if (pfBorder.getOrderType().equals(BOrderType.agent.getCode())) {
                pushMessageAgent(comUser, pComUser, pfBorder, pfBorderPayment, pfBorderItems, comAgentLevel, simpleDateFormat, numberFormat);
            } else if (pfBorder.getOrderType().equals(BOrderType.Supplement.getCode())) {
                //拿货方式(0未选择1平台代发2自己发货)
                if (pfBorder.getSendType().intValue() == 1) {
                    pushMessageSupplementAndSendTypeI(comUser, pComUser, pfBorder, pfBorderItems, numberFormat);
                } else if (pfBorder.getSendType().intValue() == 2) {
                    pushMessageSupplementAndSendTypeII(comUser, pComUser, pfBorder, pfBorderItems, simpleDateFormat, numberFormat);
                }
            } else if (pfBorder.getOrderType().equals(BOrderType.UPGRADE.getCode())) {
                //支付完成推送消息(发送失败不回滚事务)
                logger.info("未进入排单----升级订单发送短信-------");
                try {
                    //发送微信通知
                    PfUserUpgradeNotice pfUserUpgradeNotice = userUpgradeNoticeService.selectByPfBorderId(pfBorder.getId());
                    BOrderUpgradeDetail upgradeDetail = upgradeNoticeService.getUpgradeNoticeInfo(pfUserUpgradeNotice.getId());
                    Boolean bl = upgradeWechatNewsService.upgradeOrderPaySuccessSendWXNotice(pfBorder, pfBorderPayment, upgradeDetail);
                    if (!bl) {
                        logger.info("升级支付完发送消息失败");
                        throw new Exception("升级支付完发送消息失败");
                    }
                } catch (Exception ex) {
                    logger.info("升级支付完发送消息失败");
                }
            }
        }
        PfBorderRecommenReward pfBorderRecommenReward = pfBorderRecommenRewardService.getByPfBorderItemId(pfBorderItems.get(0).getId());
        if (pfBorderRecommenReward != null) {
            ComUser recommenUser = comUserMapper.selectByPrimaryKey(pfBorderRecommenReward.getRecommenUserId());
            if (pfBorder.getOrderType().equals(BOrderType.agent.getCode())) {
                pushMessageRecommen(comUser, recommenUser);
            }
            if (pfBorderRecommenReward.getRewardTotalPrice().compareTo(BigDecimal.ZERO) > 0) {
                pushMessageRecommenRebate(pfBorder, comUser, recommenUser, pfBorderRecommenReward, simpleDateFormat, numberFormat);
            }
        }
    }

    /**
     * 排单发送消息
     *
     * @param comUser
     * @param pComUser
     * @param pfBorder
     * @param pfBorderItems
     * @param simpleDateFormat
     */
    private void pushMessageMPS(ComUser comUser,
                                ComUser pComUser,
                                PfBorder pfBorder,
                                List<PfBorderItem> pfBorderItems,
                                SimpleDateFormat simpleDateFormat,
                                NumberFormat numberFormat) {
        String[] param = new String[5];
        param[0] = pfBorderItems.get(0).getSkuName();
        param[1] = numberFormat.format(pfBorder.getOrderAmount());
        param[2] = pfBorderItems.get(0).getQuantity().toString();
        param[3] = BOrderType.getByCode(pfBorder.getOrderType()).getDesc();
        param[4] = BOrderStatus.getByCode(pfBorder.getOrderStatus()).getDesc();
        WxPFNoticeUtils.getInstance().orderInQueue(comUser, param);
        MobileMessageUtil.getInitialization("B").joinQueueOrder(comUser.getMobile(), pfBorder.getOrderCode());
        if (pComUser != null) {
            String[] paramIn = new String[2];
            paramIn[0] = pfBorder.getOrderCode();
            paramIn[1] = simpleDateFormat.format(pfBorder.getCreateTime());
            String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/borderDetils.html?id=" + pfBorder.getId();
            MobileMessageUtil.getInitialization("B").haveNewLowerOrder(pComUser.getMobile(), pfBorder.getOrderStatus());
            if (pfBorder.getOrderType().intValue() == BOrderType.Supplement.getCode().intValue()) {
                WxPFNoticeUtils.getInstance().newSupplementOrderNotice(pComUser, paramIn, url, false);
            } else {
                WxPFNoticeUtils.getInstance().newOrderNotice(pComUser, paramIn, url, false);
            }
        }
    }

    /**
     * 代理发送消息
     *
     * @param comUser
     * @param pComUser
     * @param pfBorder
     * @param pfBorderPayment
     * @param pfBorderItems
     * @param comAgentLevel
     * @param simpleDateFormat
     */
    private void pushMessageAgent(ComUser comUser,
                                  ComUser pComUser,
                                  PfBorder pfBorder,
                                  PfBorderPayment pfBorderPayment,
                                  List<PfBorderItem> pfBorderItems,
                                  ComAgentLevel comAgentLevel,
                                  SimpleDateFormat simpleDateFormat,
                                  NumberFormat numberFormat) {
        String[] params = new String[4];
        params[0] = numberFormat.format(pfBorder.getPayAmount());
        params[1] = pfBorderPayment.getPayTypeName();
        params[2] = pfBorderItems.get(0).getSkuName() + "-" + comAgentLevel.getName();
        params[3] = simpleDateFormat.format(pfBorder.getPayTime());
        WxPFNoticeUtils.getInstance().partnerApplySuccessNotice(comUser, params);
        MobileMessageUtil.getInitialization("B").partnerApplicationSuccess(comUser.getMobile(), pfBorderItems.get(0).getSkuName(), comAgentLevel.getName());
        //给上级推送
        if (pComUser != null) {
            PfBorderRecommenReward pfBorderRecommenReward = pfBorderRecommenRewardService.getByPfBorderItemId(pfBorderItems.get(0).getId());
            if (pfBorderRecommenReward != null) {
                ComUser recommenUser = comUserMapper.selectByPrimaryKey(pfBorderRecommenReward.getRecommenUserId());
                String[] paramsI = new String[2];
                paramsI[0] = recommenUser.getWxNkName();
                paramsI[1] = simpleDateFormat.format(pfBorder.getCreateTime());
                WxPFNoticeUtils.getInstance().partnerJoinByRecommendNotice(pComUser, comUser, paramsI, "");
            } else {
                WxPFNoticeUtils.getInstance().partnerJoinNotice(pComUser, comUser, simpleDateFormat.format(pfBorder.getCreateTime()));
            }
            MobileMessageUtil.getInitialization("B").haveNewLowerOrder(pComUser.getMobile(), pfBorder.getOrderStatus());
        }
    }

    /**
     * 补货平台代发发送消息
     *
     * @param comUser
     * @param pfBorder
     * @param pfBorderItems
     * @param numberFormat
     */
    private void pushMessageSupplementAndSendTypeI(ComUser comUser,
                                                   ComUser pComUser,
                                                   PfBorder pfBorder,
                                                   List<PfBorderItem> pfBorderItems,
                                                   NumberFormat numberFormat) {
        String[] param = new String[4];
        param[0] = pfBorderItems.get(0).getSkuName();
        param[1] = numberFormat.format(pfBorder.getOrderAmount());
        param[2] = pfBorderItems.get(0).getQuantity().toString();
        param[3] = BOrderStatus.getByCode(pfBorder.getOrderStatus()).getDesc();
        WxPFNoticeUtils.getInstance().replenishmentByPlatForm(comUser, param);
        MobileMessageUtil.getInitialization("B").addStockSuccess(comUser.getMobile(), pfBorder.getSendType(), pfBorderItems.get(0).getQuantity().toString());
        if (pfBorder.getUserPid() != 0) {
            String[] paramI = new String[6];
            paramI[0] = pfBorderItems.get(0).getSkuName();
            paramI[1] = numberFormat.format(pfBorder.getOrderAmount());
            paramI[2] = pfBorderItems.get(0).getQuantity().toString();
            paramI[3] = BOrderType.getByCode(pfBorder.getOrderType()).getDesc();
            paramI[4] = BOrderStatus.getByCode(pfBorder.getOrderStatus()).getDesc();
            paramI[5] = comUser.getRealName();
            String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/borderDetils.html?id=" + pfBorder.getId();
            WxPFNoticeUtils.getInstance().supplementSuccessToUp(pComUser, paramI, url);
        }
    }

    /**
     * 补货自己发货发送消息
     *
     * @param comUser
     * @param pComUser
     * @param pfBorder
     * @param pfBorderItems
     * @param simpleDateFormat
     */
    private void pushMessageSupplementAndSendTypeII(ComUser comUser,
                                                    ComUser pComUser,
                                                    PfBorder pfBorder,
                                                    List<PfBorderItem> pfBorderItems,
                                                    SimpleDateFormat simpleDateFormat,
                                                    NumberFormat numberFormat) {
        String[] param = new String[4];
        param[0] = pfBorderItems.get(0).getSkuName();
        param[1] = numberFormat.format(pfBorder.getOrderAmount());
        param[2] = pfBorderItems.get(0).getQuantity().toString();
        param[3] = BOrderStatus.getByCode(pfBorder.getOrderStatus()).getDesc();
        WxPFNoticeUtils.getInstance().replenishmentBySelf(comUser, param);
        MobileMessageUtil.getInitialization("B").addStockSuccess(comUser.getMobile(), pfBorder.getSendType(), pfBorderItems.get(0).getQuantity().toString());
        if (pfBorder.getUserPid() != 0) {
            String[] paramIn = new String[2];
            paramIn[0] = pfBorder.getOrderCode();
            paramIn[1] = simpleDateFormat.format(pfBorder.getCreateTime());
            String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/borderDetils.html?id=" + pfBorder.getId();
            WxPFNoticeUtils.getInstance().newOrderNotice(pComUser, paramIn, url, true);
            MobileMessageUtil.getInitialization("B").haveNewLowerOrder(pComUser.getMobile(), pfBorder.getOrderStatus());
        }
    }

    /**
     * 推荐成功发送消息
     *
     * @param comUser      被推荐人
     * @param recommenUser 推荐人
     */
    private void pushMessageRecommen(ComUser comUser,
                                     ComUser recommenUser) {
        String[] param = new String[1];
        param[0] = comUser.getWxNkName();
        WxPFNoticeUtils.getInstance().recommendSuccessNotice(recommenUser, param);
    }

    /**
     * 推荐成功奖励发送消息
     *
     * @param recommenUser
     * @param pfBorderRecommenReward
     * @param simpleDateFormat
     * @param numberFormat
     */
    private void pushMessageRecommenRebate(PfBorder pfBorder,
                                           ComUser comUser,
                                           ComUser recommenUser,
                                           PfBorderRecommenReward pfBorderRecommenReward,
                                           SimpleDateFormat simpleDateFormat,
                                           NumberFormat numberFormat) {
        // 给获得奖励的人发
        String[] param = new String[2];
        param[0] = numberFormat.format(pfBorderRecommenReward.getRewardTotalPrice());
        param[1] = simpleDateFormat.format(pfBorderRecommenReward.getCreateTime());
        String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/myRecommend/getRewardBorder";
        WxPFNoticeUtils.getInstance().recommendProfitNotice(recommenUser, param, url);

        // 给发出推荐奖励的人发
        String[] pParam = {
                numberFormat.format(pfBorder.getRecommenAmount()),
                simpleDateFormat.format(pfBorderRecommenReward.getCreateTime()),
                comUser.getRealName(),
                recommenUser.getRealName()
        };
        ComUser pUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserPid());
        String pUrl = PropertiesUtils.getStringValue("web.domain.name.address") + "/myRecommend/sendRewardBorder";
        WxPFNoticeUtils.getInstance().recommendProfitOutNotice(pUser, pParam, pUrl);
    }
}
