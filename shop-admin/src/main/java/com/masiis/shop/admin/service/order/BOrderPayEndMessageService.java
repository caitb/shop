package com.masiis.shop.admin.service.order;

import com.masiis.shop.admin.utils.WxPFNoticeUtils;
import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.enums.BOrder.BOrderType;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PropertiesUtils;
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

    /**
     * 支付完成推送消息
     */
    public void payEndPushMessage(PfBorderPayment pfBorderPayment) {
        //金额格式化
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
        //时间统一格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        //订单数据
        PfBorder pfBorder = bOrderService.findById(pfBorderPayment.getPfBorderId());
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
            pushMessageMPS(comUser, pComUser, pfBorder, pfBorderItems, simpleDateFormat, numberFormat);
        } else if (pfBorder.getPayStatus().intValue() == 1) {
            //订单类型(0代理1补货2拿货)
            if (pfBorder.getOrderType().equals(BOrderType.agent.getCode())) {
                pushMessageAgent(comUser, pComUser, pfBorder, pfBorderPayment, pfBorderItems, comAgentLevel, simpleDateFormat, numberFormat);
            } else if (pfBorder.getOrderType().equals(BOrderType.Supplement.getCode())) {
                //拿货方式(0未选择1平台代发2自己发货)
                if (pfBorder.getSendType().intValue() == 1) {
                    pushMessageSupplementAndSendTypeI(comUser, pfBorder, pfBorderItems, numberFormat);
                } else if (pfBorder.getSendType().intValue() == 2) {
                    pushMessageSupplementAndSendTypeII(comUser, pComUser, pfBorder, pfBorderItems, simpleDateFormat, numberFormat);
                }
            }
        }
        if (pfBorder.getRecommenAmount().compareTo(BigDecimal.ZERO) > 0) {
            PfBorderRecommenReward pfBorderRecommenReward = pfBorderRecommenRewardService.getByPfBorderItemId(pfBorderItems.get(0).getId());
            ComUser recommenUser = comUserMapper.selectByPrimaryKey(pfBorderRecommenReward.getRecommenUserId());
            if (pfBorder.getOrderType().equals(BOrderType.agent.getCode())) {
                pushMessageRecommen(comUser, recommenUser);
            }
            pushMessageRecommenRebate(recommenUser, pfBorderRecommenReward, simpleDateFormat, numberFormat);
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
            WxPFNoticeUtils.getInstance().newOrderNotice(pComUser, paramIn, url, false);
            MobileMessageUtil.getInitialization("B").haveNewLowerOrder(pComUser.getMobile(), pfBorder.getOrderStatus());
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
            if (pfBorder.getRecommenAmount().compareTo(BigDecimal.ZERO) > 0) {
                PfBorderRecommenReward pfBorderRecommenReward = pfBorderRecommenRewardService.getByPfBorderItemId(pfBorderItems.get(0).getId());
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
                                                   PfBorder pfBorder,
                                                   List<PfBorderItem> pfBorderItems,
                                                   NumberFormat numberFormat) {
        String[] param = new String[4];
        param[0] = pfBorderItems.get(0).getSkuName();
        param[1] = numberFormat.format(pfBorder.getOrderAmount());
        param[2] = pfBorderItems.get(0).getQuantity().toString();
        param[3] = BOrderStatus.getByCode(pfBorder.getOrderType()).getDesc();
        WxPFNoticeUtils.getInstance().replenishmentByPlatForm(comUser, param);
        MobileMessageUtil.getInitialization("B").addStockSuccess(comUser.getMobile(), pfBorder.getSendType(), pfBorderItems.get(0).getQuantity().toString());
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
        param[3] = BOrderStatus.getByCode(pfBorder.getOrderType()).getDesc();
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
    private void pushMessageRecommenRebate(ComUser recommenUser,
                                           PfBorderRecommenReward pfBorderRecommenReward,
                                           SimpleDateFormat simpleDateFormat,
                                           NumberFormat numberFormat) {
        String[] param = new String[2];
        param[0] = numberFormat.format(pfBorderRecommenReward.getRewardTotalPrice());
        param[1] = simpleDateFormat.format(pfBorderRecommenReward.getCreateTime());
        String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/myRecommend/getRewardBorder";
        WxPFNoticeUtils.getInstance().recommendProfitNotice(recommenUser, param, url);
    }
}
