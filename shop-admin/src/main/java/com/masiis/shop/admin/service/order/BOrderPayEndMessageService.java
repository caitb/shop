package com.masiis.shop.admin.service.order;

import com.masiis.shop.admin.service.product.PfUserSkuStockService;
import com.masiis.shop.admin.service.user.PfUserSkuService;
import com.masiis.shop.admin.service.user.UpgradeMobileMessageService;
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
    @Resource
    private UpgradeMobileMessageService upgradeSuccssSendMoblieMessage;
    @Resource
    private PfUserSkuStockService userSkuStockService;
    @Resource
    private PfBorderRecommenRewardService recommenRewardService;
    @Resource
    private PfUserSkuService pfUserSkuService;

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
                PfUserUpgradeNotice pfUserUpgradeNotice = userUpgradeNoticeService.selectByPfBorderId(pfBorder.getId());
                BOrderUpgradeDetail upgradeDetail = upgradeNoticeService.getUpgradeNoticeInfo(pfUserUpgradeNotice.getId());
                //发送微信通知
                Boolean bl = upgradeWechatNewsService.upgradeOrderPaySuccssEntryWaiting(pfBorder, pfBorderPayment, upgradeDetail);
                //发送短信
                upgradeSuccssSendMoblieMessage.upgradeSuccssSendMoblieMessage(comUser,pfBorder,pfBorderItems,upgradeDetail,true);
            } else {
                pushMessageMPS(comUser, pComUser, pfBorder, pfBorderItems, simpleDateFormat, numberFormat,comAgentLevel);
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
                //补货发送短信
                pushMoblieMessageSupplement(comUser,pComUser,pfBorder,pfBorderItems,comAgentLevel,false);
            } else if (pfBorder.getOrderType().equals(BOrderType.UPGRADE.getCode())) {
                //支付完成推送消息(发送失败不回滚事务)
                logger.info("未进入排单----升级订单发送短信-------");
                PfUserUpgradeNotice pfUserUpgradeNotice = userUpgradeNoticeService.selectByPfBorderId(pfBorder.getId());
                BOrderUpgradeDetail upgradeDetail = upgradeNoticeService.getUpgradeNoticeInfo(pfUserUpgradeNotice.getId());
                try {
                    //发送微信通知
                    Boolean bl = upgradeWechatNewsService.upgradeOrderPaySuccessSendWXNotice(pfBorder, pfBorderPayment, upgradeDetail);
                    if (!bl) {
                        logger.info("升级支付完发送消息失败");
                        throw new Exception("升级支付完发送消息失败");
                    }
                    //发送短信
                    upgradeSuccssSendMoblieMessage.upgradeSuccssSendMoblieMessage(comUser,pfBorder,pfBorderItems,upgradeDetail,false);
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
                                NumberFormat numberFormat,
                                ComAgentLevel comAgentLevel) {
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
            //发送短信
            if (pfBorder.getOrderType().equals(BOrderType.agent.getCode())) {
                //代理发送短信并给这个人的团队所有成员发微信
                pushMobileMessageAgent(comUser,pComUser,pfBorder,pfBorderItems,comAgentLevel,simpleDateFormat,true);
            }else if (pfBorder.getOrderType().equals(BOrderType.Supplement.getCode())) {
                //补货发送短信
                pushMoblieMessageSupplement(comUser,pComUser,pfBorder,pfBorderItems,comAgentLevel,true);
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
            // MobileMessageUtil.getInitialization("B").haveNewLowerOrder(pComUser.getMobile(), pfBorder.getOrderStatus());
            //发送短信并给这个人的团队所有成员发微信
            pushMobileMessageAgent(comUser,pComUser,pfBorder,pfBorderItems,comAgentLevel,simpleDateFormat,false);
        }
    }
    /**
     * 合伙人订单发送短信
     * @param comUser
     * @param pComUser
     * @param pfBorder
     * @param pfBorderItems
     * @param comAgentLevel
     */
    private void pushMobileMessageAgent(ComUser comUser,
                                        ComUser pComUser,
                                        PfBorder pfBorder,
                                        List<PfBorderItem> pfBorderItems,
                                        ComAgentLevel comAgentLevel,
                                        SimpleDateFormat simpleDateFormat,
                                        Boolean isWaitngOrder){
        logger.info("合伙人订单发送短信-----start");
        PfBorderRecommenReward pfBorderRecommenReward = recommenRewardService.getByPfBorderItemId(pfBorderItems.get(0).getId());
        BigDecimal incomeAmout = pfBorder.getOrderAmount();
        logger.info("合伙人上级获得收入----------"+incomeAmout.toString()+"----------订单id----------"+pfBorder.getId());
        logger.info("是否处于排单-------"+isWaitngOrder);
        if (pfBorderRecommenReward!=null){
            logger.info("推荐人id-----------------"+pfBorderRecommenReward.getRecommenUserId());
            ComUser recommenRewardUser =  comUserMapper.selectByPrimaryKey(pfBorderRecommenReward.getRecommenUserId());
            String recommenRewardName = "";
            if (recommenRewardUser!=null) {
                //有推荐人的情况
                logger.info("-----------有推荐人的情况-----------");
                //给合伙人发
                logger.info("有推荐人情况下给合伙人发短信----start");
                if (pComUser!=null){
                    recommenRewardName = recommenRewardUser.getRealName();
                    logger.info("商品名称-------"+ pfBorderItems.get(0).getSkuName());
                    logger.info("等级名称-------"+ comAgentLevel.getName());
                    logger.info("收入-------"+ incomeAmout.toString());
                    logger.info("推荐人昵称-------"+ recommenRewardName);
                    Boolean bl = MobileMessageUtil.getInitialization("B").refereeLowerJoinUpNotice(pComUser.getMobile(),
                            pfBorderItems.get(0).getSkuName(),
                            comAgentLevel.getName(),
                            incomeAmout,
                            recommenRewardName,
                            //userSkuStockService.isEnoughStock(pComUser.getId(), pfBorderItems.get(0).getSkuId())
                            !isWaitngOrder);
                    if (bl) {
                        logger.info("合伙人订单有推荐人给合伙人发送短信成功");
                    }else{
                        logger.info("合伙人订单有推荐人给合伙人发送短信失败");
                    }
                    logger.info("有推荐人情况下给合伙人发短信----end");
                }else{
                    logger.info("有推荐人情况下给合伙人发短信-------合伙人不存在不发送短信--------------end");
                }
                //给推荐人发
                logger.info("给推荐人发短信------start");
                logger.info("推荐人电话号码-------"+ recommenRewardUser.getMobile());
                logger.info("用户昵称-------"+ comUser.getRealName());
                logger.info("商品名称-------"+ pfBorderItems.get(0).getSkuName());
                logger.info("等级名称-------"+ comAgentLevel.getName());
                logger.info("奖励-------"+ pfBorderRecommenReward.getRewardTotalPrice().toString());
                Boolean _bl = MobileMessageUtil.getInitialization("B").recommendCommissionRemind(
                        recommenRewardUser.getMobile(),
                        comUser.getRealName(),
                        pfBorderItems.get(0).getSkuName(),
                        comAgentLevel.getName(),
                        pfBorderRecommenReward.getRewardTotalPrice().toString()
                );
                if (_bl){
                    logger.info("合伙人订单有推荐人发送佣金成功");
                }else{
                    logger.info("合伙人订单有推荐人发送佣金失败");
                }
                logger.info("给推荐人发短信------end");
            }else{
                logger.info("合伙人查询是否有推荐人获得comUser为null");
            }
        } else{
            //没有推荐人的情况
            logger.info("--------------没有推荐人的情况------------");
            logger.info("上级号码---------"+pComUser.getMobile());
            logger.info("商品名称---------"+pfBorderItems.get(0).getSkuName());
            logger.info("等级名称---------"+comAgentLevel.getName());
            logger.info("收入---------"+incomeAmout);
            Boolean bl = MobileMessageUtil.getInitialization("B").lowerJoinRemind(pComUser.getMobile(),
                    pfBorderItems.get(0).getSkuName(),
                    comAgentLevel.getName(),
                    incomeAmout,
                    //userSkuStockService.isEnoughStock(pComUser.getId(),pfBorderItems.get(0).getSkuId()
                    !isWaitngOrder
            );
            if (bl){
                logger.info("合伙人订单没有推荐人发送短信成功");
            }else{
                logger.info("合伙人订单没有推荐人发送短信失败");
            }
        }
        //给这个人的团队所有成员发短信微信
        logger.info("给这个人的团队所有成员发短信微信----start");
        for (PfBorderItem orderItem : pfBorderItems){
            PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(comUser.getId(),orderItem.getSkuId());
            if (pfUserSku!=null){
                List<PfUserSku> userSkus = pfUserSkuService.getBossTeamInfoByTreeCode(pfUserSku.getTreeCode());
                for (PfUserSku teamUserSku:userSkus) {
                    ComUser teamComUser = comUserMapper.selectByPrimaryKey(teamUserSku.getUserId());
                    if (teamComUser!=null){
                        //发短信
                        logger.info("商品名称---------"+orderItem.getSkuName()+"-----orderItem的id----"+orderItem.getId());
                        if (MobileMessageUtil.getInitialization("B").newPartnerJoin(teamComUser.getMobile(),orderItem.getSkuName())){
                            logger.info("新合伙人加入----发短信通知----"+teamComUser.getWxNkName()+"------success");
                        }else {
                            logger.info("新合伙人加入----发短信通知----"+teamComUser.getWxNkName()+"------fail");
                        }
                        //发微信
                        String[] bossTeamParam = new String[4];
                        bossTeamParam[0]=pfBorderItems.get(0).getSkuName();
                        bossTeamParam[1]=comAgentLevel.getName();
                        bossTeamParam[2]=comUser.getWxNkName();
                        if (pfBorder.getPayTime()!=null){
                            bossTeamParam[3]=simpleDateFormat.format(pfBorder.getPayTime());
                        }else{
                            bossTeamParam[3]=simpleDateFormat.format(pfBorder.getCreateTime());
                        }
                        if(WxPFNoticeUtils.getInstance().newMemberJoinNotice(teamComUser,bossTeamParam)){
                            logger.info("新合伙人加入----发微信通知----"+teamComUser.getRealName()+"------success");
                        }else{
                            logger.info("新合伙人加入----发微信通知----"+teamComUser.getRealName()+"------fail");
                        }
                    }else{
                        logger.info("查询boss团队里的这个人查询不到-----userId---"+teamUserSku.getUserId());
                    }
                }
            }else{
                logger.info("查询这个人代理的商品不存在");
            }
        }
        logger.info("给这个人的团队所有成员发短信微信-------end");
        logger.info("合伙人订单发送短信-----end");
    }
    /**
     * 补货发送短信
     * @param comUser
     * @param pComUser
     * @param pfBorderItems
     * @param comAgentLevel
     *
     * 合伙人收入 = 订单金额  - 保证金 -  推荐奖 - 运费
     */
    private void pushMoblieMessageSupplement(ComUser comUser,
                                             ComUser pComUser,
                                             PfBorder pfBorder,
                                             List<PfBorderItem> pfBorderItems,
                                             ComAgentLevel comAgentLevel,
                                             Boolean isWaitingOrder){
        logger.info("补货发送短信-------start");
        //给合伙人发送短信
        logger.info("给合伙人发送短信-----start");
        if (pComUser!=null){
            BigDecimal incomeAmout = pfBorder.getOrderAmount();
            logger.info("合伙人上级获得收入----------"+incomeAmout.toString()+"----------订单id----------"+pfBorder.getId());
            logger.info("是否处于排单-------"+isWaitingOrder);
            logger.info("上级号码--------"+pComUser.getMobile());
            logger.info("商品名称--------"+pfBorderItems.get(0).getSkuName());
            logger.info("等级名称--------"+comAgentLevel.getName());
            logger.info("用户昵称--------"+comUser.getRealName());
            logger.info("数量--------"+pfBorderItems.get(0).getQuantity());
            logger.info("收入--------"+incomeAmout);
            Boolean bl = MobileMessageUtil.getInitialization("B").refereeAddstockUpRemind(pComUser.getMobile(),
                    pfBorderItems.get(0).getSkuName(),
                    comAgentLevel.getName(),
                    comUser.getRealName(),
                    pfBorderItems.get(0).getQuantity(),
                    incomeAmout,
                    //userSkuStockService.isEnoughStock(pComUser.getId(),pfBorderItems.get(0).getSkuId()
                    !isWaitingOrder
            );
            if (bl){
                logger.info("补货给合伙人发送短信成功");
            }else{
                logger.info("补货给合伙人发送短信失败");
            }
            logger.info("给合伙人发送短信-----end");
        }else{
            logger.info("上级合伙人不存在不给合伙人发短信------------end");
        }

        //给推荐人发短信
        logger.info("给推荐人发短信-----start");
        logger.info("补货给推荐人发送短信-----pfborderItem的id-----"+pfBorderItems.get(0).getId());
        PfBorderRecommenReward pfBorderRecommenReward = recommenRewardService.getByPfBorderItemId(pfBorderItems.get(0).getId());
        if (pfBorderRecommenReward!=null){
            ComUser recommenRewardUser =  comUserMapper.selectByPrimaryKey(pfBorderRecommenReward.getRecommenUserId());
            logger.info("推荐人id-----------------"+pfBorderRecommenReward.getRecommenUserId());
            logger.info("推荐人的电话-------"+recommenRewardUser.getMobile());
            logger.info("商品名称-------"+pfBorderItems.get(0).getSkuName());
            logger.info("等级名称-------"+comAgentLevel.getName());
            logger.info("数量-------"+comUser.getRealName());
            logger.info("推荐人的奖励-------"+pfBorderRecommenReward.getRewardTotalPrice().toString());
            if (recommenRewardUser!=null){
                Boolean _bl = MobileMessageUtil.getInitialization("B").refereeAddstockRecomendRemind(recommenRewardUser.getMobile(),
                        pfBorderItems.get(0).getSkuName(),
                        comAgentLevel.getName(),
                        comUser.getRealName(),
                        pfBorderItems.get(0).getQuantity(),
                        pfBorderRecommenReward.getRewardTotalPrice().toString()
                );
                if (_bl){
                    logger.info("补货给推荐人发送短信成功");
                }else{
                    logger.info("补货给推荐人发送短信失败");
                }
            }else{
                logger.info("补货发送短信没有推荐人不发送短信");
            }
        }else{
            logger.info("补货发送短信没有推荐人不发送短信");
        }
        logger.info("给推荐人发短信-----end");
        logger.info("补货发送短信-------end");
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
