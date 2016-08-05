package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.platform.UpGradeStatus;
import com.masiis.shop.common.enums.platform.UpGradeUpStatus;
import com.masiis.shop.common.enums.promotion.SfTurnTableRuleTypeEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.beans.user.upgrade.UpGradeInfoPo;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.ComAgentLevelService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.product.PfSkuStockService;
import com.masiis.shop.web.platform.service.product.PfUserSkuStockService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.mall.service.shop.SfShopSkuService;
import com.masiis.shop.web.platform.service.user.*;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserTurnTableService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 升级支付
 */
@Service
@Transactional
public class BUpgradePayService {

    private final static Logger log = Logger.getLogger(BUpgradePayService.class);

    @Resource
    private PfBorderPaymentService borderPaymentService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private BOrderOperationLogService bOrderOperationLogService;
    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private PfUserCertificateService pfUserCertificateService;
    @Resource
    private BOrderPayService bOrderPayService;
    @Resource
    private UserService comUserService;
    @Resource
    private ComAgentLevelService comAgentLevelService;
    @Resource
    private PfSkuStockService pfSkuStockService;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private PfUserUpgradeNoticeService userUpgradeNoticeService;
    @Resource
    private PfUserRebateService pfUserRebateService;
    @Resource
    private SfShopSkuService sfShopSkuService;
    @Resource
    private BOrderStatisticsService orderStatisticsService;
    @Resource
    private BOrderBillAmountService billAmountService;
    @Resource
    private PfUserSkuHistoryService pfUserSkuHistoryService;
    @Resource
    private PfUserCertificateHistoryService pfUserCertificateHistoryService;
    @Resource
    private UpgradeNoticeService upgradeNoticeService;
    @Resource
    private UpgradeWechatNewsService upgradeWechatNewsService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private SfUserTurnTableService userTurnTableService;

    public void paySuccessCallBack(PfBorderPayment pfBorderPayment, String outOrderId, String rootPath) {
        //修改订单支付
        log.info("更新订单支付表------start");
        updatePfBorderPayment(pfBorderPayment, outOrderId);
        log.info("更新订单支付表------end");
        //修改订单
        log.info("修改订单------start");
        PfBorder pfBorder = updatePfBorder(pfBorderPayment.getPfBorderId(), pfBorderPayment);
        List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(pfBorder.getId());
        PfUserUpgradeNotice pfUserUpgradeNotice = userUpgradeNoticeService.selectByPfBorderId(pfBorder.getId());
        log.info("修改订单------end");
        //添加订单操作日志
        log.info("插入订单操作日志------start");
        insertOrderOperationLog(pfBorder);
        log.info("插入订单操作日志------end");
        //修改证书和插入证书历史表
        log.info("修改证书插入历史-----start");
        inserHistoryAndUpdatePfUserCertificate(pfBorder.getUserId(), pfBorderItems, null, rootPath);
        log.info("修改证书插入历史-----end");
        //修改上下级绑定关系和插入历史表
        log.info("修改上下级关系插入历史-------start");
        inserHistoryAndUpdatePfUserSku(pfBorder.getUserId(), pfBorder.getUserPid(), pfBorder.getId(), pfBorderItems);
        log.info("修改上下级关系插入历史-------end");
        //修改冻结库存
        log.info("修改冻结库存----start");
        updateFrozenStock(pfBorder, pfBorderItems);
        log.info("修改冻结库存----end");
        //修改用户统计中奖励金额
        log.info("修改用户统计中奖励金额----start");
        orderStatisticsService.statisticsOrder(pfBorder.getId());
        log.info("修改用户统计中奖励金额----end");
        //插入一次性奖励
        log.info("插入一次性奖励----start");
        insertUserRebate(pfBorder, pfUserUpgradeNotice);
        log.info("插入一次性奖励----end");
        //修改小铺商品信息
        log.info("修改小铺商品信息----start");
        updateSfShopSku(pfBorder.getUserId(), pfBorderItems);
        log.info("修改小铺商品信息----end");
        //修改用户账户
        log.info("修改用户账户----start");
        billAmountService.orderBillAmount(pfBorder.getId());
        log.info("修改用户账户----end");
        //修改通知单状态
        log.info("修改通知单的状态----start");
        updateUpgradeNotice(pfBorder.getId());
        log.info("修改通知单的状态----end");
        //增加抽奖的次数
        log.info("增加抽奖的次数----start");
        userTurnTableService.addTimes(null,pfBorder.getUserId(),SfTurnTableRuleTypeEnum.B.getCode(), SysConstants.PLATFORM_TURN_TABLE_RULE_TIMES);
        log.info("增加抽奖的次数----end");
        if (pfBorder.getSendType() == 1 && pfBorder.getOrderStatus() == BOrderStatus.WaitShip.getCode()) {
            //处理平台发货类型订单
            log.info("------处理平台发货类型订单----start");
            saveBOrderSendType(pfBorder);
            log.info("------处理平台发货类型订单----end");
        } else {
            log.info("没有处理平台发货类型订单-----pfBorder.getSendType()======" + pfBorder.getSendType() + "------pfBorder.getOrderStatus()====" + pfBorder.getOrderStatus());
        }


    }

    /**
     * 修改订单支付
     *
     * @param pfBorderPayment
     * @param outOrderId
     */
    private void updatePfBorderPayment(PfBorderPayment pfBorderPayment, String outOrderId) {
        pfBorderPayment.setOutOrderId(outOrderId);
        pfBorderPayment.setIsEnabled(1);
        int i = borderPaymentService.update(pfBorderPayment);
        if (i != 1) {
            throw new BusinessException("更新订单支付表失败----id----" + pfBorderPayment.getId());
        }
    }

    /**
     * 修改订单信息
     *
     * @param orderId
     * @param pfBorderPayment
     */
    private PfBorder updatePfBorder(Long orderId, PfBorderPayment pfBorderPayment) {
        PfBorder pfBorder = bOrderService.getPfBorderById(orderId);
        BigDecimal payAmount = pfBorderPayment.getAmount();
        if (pfBorder != null) {
            log.info("订单之前的状态-----" + pfBorder.getOrderStatus());
            pfBorder.setOrderStatus(BOrderStatus.WaitShip.getCode());
            log.info("订单之后的状态-----" + pfBorder.getOrderStatus());
            pfBorder.setPayTime(new Date());
            pfBorder.setModifyTime(new Date());
            pfBorder.setPayStatus(1);
            pfBorder.setReceivableAmount(pfBorder.getReceivableAmount().subtract(payAmount));
            pfBorder.setPayAmount(pfBorderPayment.getAmount());
            int i = bOrderService.updatePfBorder(pfBorder);
            if (i != 1) {
                log.info("支付成功查询订单失败----orderId---" + orderId);
                throw new BusinessException("支付成功查询订单失败----orderId---" + orderId);
            }
        } else {
            log.info("支付成功查询订单失败----orderId---" + orderId);
            throw new BusinessException("支付成功查询订单失败----orderId---" + orderId);
        }
        return pfBorder;
    }

    /**
     * 添加订单操作日志
     *
     * @param pfBorder
     */
    private void insertOrderOperationLog(PfBorder pfBorder) {
        log.info("----添加订单操作日志-----");
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "升级支付成功");
    }

    /**
     * 修改商品的代理关系
     *
     * @param userId   用户id
     * @param userPid  用户新上级id
     * @param borderId 订单id
     */
    private void inserHistoryAndUpdatePfUserSku(Long userId, Long userPid, Long borderId, List<PfBorderItem> pfBorderItems) {
        for (PfBorderItem orderItem : pfBorderItems) {
            log.info("修改商品的的代理关系-----userId---" + userId + "----skuId----" + orderItem.getSkuId());
            PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userId, orderItem.getSkuId());
            int i = insertUserSkuHistory(pfUserSku);
            if (i == 1) {
                log.info("插入商品历史表成功----修改商品关系---start");
                int _i = updatePfUserSku(userPid, borderId, orderItem, pfUserSku);
                if (_i == 1) {
                    log.info("插入商品历史表成功----修改商品关系---end");
                } else {
                    log.info("插入商品历史表成功----修改商品关系---失败");
                }
            } else {
                log.info("插入商品历史表成功----失败");
                throw new BusinessException("");
            }
        }
    }

    /**
     * 修改商品的代理关系
     *
     * @param userPid
     * @param borderId
     * @param orderItem
     * @param pfUserSku
     * @return
     */
    private int updatePfUserSku(Long userPid, Long borderId, PfBorderItem orderItem, PfUserSku pfUserSku) {
        log.info("---修改商品的代理关系-----订单id-----" + borderId);
        Integer skuId = orderItem.getSkuId();
        Integer agentLevelId = orderItem.getAgentLevelId();
        int i = 0;
        if (pfUserSku != null) {
            log.info("---修改商品的代理关系-----用户新上级-----userId----" + userPid + "-----skuId----" + skuId);
            PfUserSku parentPfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userPid, pfUserSku.getSkuId());
            log.info("期望等级----" + agentLevelId + "-----原上级等级------" + parentPfUserSku.getAgentLevelId());
            if (!agentLevelId.equals(parentPfUserSku.getAgentLevelId())) {
                Integer parent_id = 0;
                Long parent_userPid = 0l;
                String parent_treeCode = pfUserSku.getSkuId() + ",";
                Integer parent_treeLevel = 0;
                if (null != parentPfUserSku) {
                    parent_id = parentPfUserSku.getId();
                    parent_userPid = parentPfUserSku.getUserId();
                    parent_treeCode = parentPfUserSku.getTreeCode();
                    log.info("父级的treeCode-----" + parent_treeCode);
                    parent_treeLevel = parentPfUserSku.getTreeLevel();
                }
                PfUserCertificate certificateInfo = pfUserCertificateService.selectByUserSkuId(pfUserSku.getId());
                if (certificateInfo != null) {
                    log.info("证书编码code--------" + certificateInfo.getCode());
                    pfUserSku.setCode(certificateInfo.getCode());
                } else {
                    log.info("根据pfuserskuId-----" + pfUserSku.getId() + "-----查询证书失败");
                }
                pfUserSku.setCreateTime(new Date());
                pfUserSku.setPid(parent_id);
                pfUserSku.setUserPid(parent_userPid);
                pfUserSku.setAgentLevelId(agentLevelId);
                pfUserSku.setIsPay(1);
                pfUserSku.setIsCertificate(1);
                pfUserSku.setPfBorderId(borderId);
                log.info("skuId-----" + skuId + "-----期望等级agentLevelId-------" + agentLevelId);
                PfSkuAgent newPfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, agentLevelId);
                if (newPfSkuAgent != null) {
                    pfUserSku.setBail(newPfSkuAgent.getBail());
                    log.info("新等级的保证金-----" + pfUserSku.getBail());
                } else {
                    throw new BusinessException("获取新的等级的保证金，代理商品为null");
                }
                i = pfUserSkuService.update(pfUserSku);
                if (i <= 0) {
                    throw new BusinessException("分销关系树结构修改失败");
                }
                Integer id = pfUserSku.getId();
                String treeCode = pfUserSku.getTreeCode();
                String parentTreeCode = parent_treeCode;
                Integer id_index = treeCode.indexOf(String.valueOf(id)) + 1;
                Integer treeLevel = pfUserSku.getTreeLevel() - parent_treeLevel - 1;
                if (treeLevel < 0) {
                    log.info("树结构更换只能挂在高于自己的树枝");
                    throw new BusinessException("树结构更换只能挂在高于自己的树枝");
                }
                log.info("之前的pfUserSku----的---treeCode-----" + treeCode);
                log.info("要更变后的treeCode------parentTreeCode-----" + parentTreeCode);
                log.info("id_index-----" + id_index);
                log.info("treeLevel-----" + treeLevel);
                i = pfUserSkuService.updateTreeCodes(treeCode, parentTreeCode, id_index, treeLevel);
                if (i <= 0) {
                    log.info("分销关系树结构修改失败");
                    throw new BusinessException("分销关系树结构修改失败");
                }
            } else {
                log.info("同等级不能合伙");
                throw new BusinessException("同等级不能合伙");
            }
        } else {
            log.info("pfUserSku不能为空");
            throw new BusinessException("pfUserSku不能为空");
        }
        return i;
    }

    /**
     * 插入pfuserHistory历史表
     *
     * @param pfUserSku
     */
    private int insertUserSkuHistory(PfUserSku pfUserSku) {
        log.info("----插入pfuserHistory表-----");
        PfUserSkuHistory userSkuHistory = new PfUserSkuHistory();
        userSkuHistory.setPfUserSkuId(pfUserSku.getId());
        userSkuHistory.setAddTime(new Date());
        userSkuHistory.setCreateTime(pfUserSku.getCreateTime());
        userSkuHistory.setCode(pfUserSku.getCode());
        userSkuHistory.setPid(pfUserSku.getPid());
        userSkuHistory.setUserId(pfUserSku.getUserId());
        userSkuHistory.setUserPid(pfUserSku.getUserPid());
        userSkuHistory.setSkuId(pfUserSku.getSkuId());
        userSkuHistory.setAgentLevelId(pfUserSku.getAgentLevelId());
        userSkuHistory.setIsPay(pfUserSku.getIsPay());
        userSkuHistory.setIsCertificate(pfUserSku.getIsCertificate());
        userSkuHistory.setPfBorderId(pfUserSku.getPfBorderId());
        userSkuHistory.setBail(pfUserSku.getBail());
        userSkuHistory.setAgentNum(pfUserSku.getAgentNum());
        userSkuHistory.setRemark("升级修改sku关系增加历史");
        userSkuHistory.setTreeCode(pfUserSku.getTreeCode());
        userSkuHistory.setTreeLevel(pfUserSku.getTreeLevel());
        userSkuHistory.setRewardUnitPrice(pfUserSku.getRewardUnitPrice());
        int i = pfUserSkuHistoryService.insert(userSkuHistory);
        if (i != 1) {
            log.info("升级修改sku关系增加历史");
            throw new BusinessException("升级修改sku关系增加历史");
        }
        return i;
    }


    /**
     * 修改证书
     *
     * @param userId
     * @param orderItems
     * @param spuId
     */
    private void inserHistoryAndUpdatePfUserCertificate(Long userId, List<PfBorderItem> orderItems, Integer spuId, String rootPath) {
        for (PfBorderItem orderItem : orderItems) {
            log.info("---修改证书----userId----" + userId + "----skuId---" + orderItem.getSkuId() + "----spuId----" + spuId);
            PfUserCertificate pfUserCertificate = pfUserCertificateService.selectByUserIdAndSkuId(userId, orderItem.getSkuId());
            if (pfUserCertificate != null) {
                int i = insertCertificateHistory(pfUserCertificate);
                if (i == 1) {
                    ComUser comUser = comUserService.getUserById(userId);
                    Calendar calendar = Calendar.getInstance();
                    pfUserCertificate.setBeginTime(calendar.getTime());
                    calendar.set(Calendar.MONTH, 11);
                    calendar.set(Calendar.DAY_OF_MONTH, 31);
                    pfUserCertificate.setEndTime(calendar.getTime());
                    pfUserCertificate.setAgentLevelId(orderItem.getAgentLevelId());
                    pfUserCertificate.setStatus(1);
                    pfUserCertificate.setCode(pfUserCertificateService.getCertificateCode(pfUserCertificate));
                    ComAgentLevel comAgentLevel = comAgentLevelService.selectByPrimaryKey(pfUserCertificate.getAgentLevelId());
                    String newIdCard = comUser.getIdCard().substring(0, 4) + "**********" + comUser.getIdCard().substring(comUser.getIdCard().length() - 4, comUser.getIdCard().length());
                    String picName = bOrderPayService.uploadFile(rootPath + "/static/images/certificate/" + comAgentLevel.getImgUrl(),//filePath - 原图的物理路径
                            rootPath + "/static/font/",//字体路径
                            pfUserCertificate.getCode(),//certificateCode - 证书编号
                            comUser.getRealName(),//userName - 用户名称
                            comAgentLevel.getName(),//levelName - 代理等级名称
                            orderItem.getSkuName(),//skuName - 商品名称
                            orderItem.getSkuName(),
                            newIdCard,//idCard - 身份证号
                            comUser.getMobile(),//mobile - 手机号
                            pfUserCertificate.getWxId(),//wxId - 微信号
                            DateUtil.Date2String(pfUserCertificate.getBeginTime(), "yyyy-MM-dd", null),//beginDate - 开始日期
                            DateUtil.Date2String(pfUserCertificate.getEndTime(), "yyyy-MM-dd", null));//endDate - 结束日期
                    pfUserCertificate.setImgUrl(picName + ".jpg");
                    pfUserCertificate.setRemark("升级支付成功修改证书");
                    pfUserCertificateService.update(pfUserCertificate);
                } else {
                    log.info("修改证书插入历史失败-----");
                    throw new BusinessException("修改证书插入历史失败-----");
                }
            } else {
                log.info("修改证书失败，之前的证书为null");
                throw new BusinessException("修改证书失败，之前的证书为null");
            }
        }
    }

    private int insertCertificateHistory(PfUserCertificate userCertificate) {
        log.info("----插入证书历史表-------");
        PfUserCertificateHistory history = new PfUserCertificateHistory();
        history.setAddTime(new Date());
        history.setPfUserCertificateId(userCertificate.getId());
        history.setCreateTime(new Date());
        history.setCode(userCertificate.getCode());
        history.setPfUserSkuId(userCertificate.getPfUserSkuId());
        history.setUserId(userCertificate.getUserId());
        history.setSpuId(userCertificate.getSpuId());
        history.setSkuId(userCertificate.getSkuId());
        history.setIdCard(userCertificate.getIdCard());
        history.setMobile(userCertificate.getMobile());
        history.setWxId(userCertificate.getWxId());
        history.setBeginTime(userCertificate.getBeginTime());
        history.setEndTime(userCertificate.getEndTime());
        history.setAgentLevelId(userCertificate.getAgentLevelId());
        history.setImgUrl(userCertificate.getImgUrl());
        history.setStatus(userCertificate.getStatus());
        history.setReason(userCertificate.getReason());
        history.setPoster(userCertificate.getPoster());
        history.setRemark("修改证书插入历史表");
        int i = pfUserCertificateHistoryService.insert(history);
        if (i != 1) {
            log.info("插入证书历史表失败----证书id---" + userCertificate.getId());
            throw new BusinessException("插入证书历史表失败");
        }
        return i;
    }


    /**
     * 冻结库存
     *
     * @param pfBorder
     */
    private void updateFrozenStock(PfBorder pfBorder, List<PfBorderItem> pfBorderItems) {
        for (PfBorderItem pfBorderItem : pfBorderItems) {
            if (pfBorderItem.getQuantity() > 0) {
                if (pfBorder.getUserPid() == 0) {
                    log.info("-----上级为平台-----");
                    PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(pfBorderItem.getSkuId());
                    //如果可售库存不足或者排单开关打开的情况下 订单进入排单
                    if (pfSkuStock.getIsQueue() == 1 || pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                        //平台库存不足，排单处理
                        pfBorder.setOrderStatus(BOrderStatus.MPS.getCode());//排队订单
                        log.info("平台库存不足进入排单");
                        bOrderService.updatePfBorder(pfBorder);
                    }
                    //增加平台冻结库存
                    pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                    if (pfSkuStockService.updateByIdAndVersions(pfSkuStock) != 1) {
                        throw new BusinessException("(平台发货)排队订单增加冻结量失败");
                    }
                } else {
                    PfUserSkuStock parentSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                    //上级合伙人库存不足，排单处理
                    if (pfBorder.getSendType() == 1 && (parentSkuStock.getStock() - parentSkuStock.getFrozenStock() < pfBorderItem.getQuantity())) {
                        pfBorder.setOrderStatus(BOrderStatus.MPS.getCode());//排队订单
                        log.info("上级合伙人库存不足，排单处理");
                        bOrderService.updatePfBorder(pfBorder);
                    }
                    //增加平台冻结库存
                    parentSkuStock.setFrozenStock(parentSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                    if (pfUserSkuStockService.updateByIdAndVersions(parentSkuStock) != 1) {
                        throw new BusinessException("(代理发货)排队订单增加冻结量失败");
                    }
                }
            }
        }
    }

    /**
     * 处理平台拿货类型订单
     * <1>减少发货方库存 如果用户id是0操作平台库存
     * <2>增加收货方库存
     * <3>订单完成处理
     *
     * @param pfBorder
     */
    public void saveBOrderSendType(PfBorder pfBorder) {
//        bOrderPayService.saveBOrderSendType(pfBorder);
    }


    /**
     * 插入一次性奖励
     *
     * @param pfBorder
     */
    private void insertUserRebate(PfBorder pfBorder, PfUserUpgradeNotice pfUserUpgradeNotice) {
        log.info("插入一次性奖励-----orderId-----" + pfBorder.getId());
        log.info("获得奖励人-------" + pfUserUpgradeNotice.getUserPid());
        log.info("支付奖励用户-------" + pfBorder.getUserPid());
        if (!pfBorder.getUserPid().equals(pfUserUpgradeNotice.getUserPid())) {
            //原上级和新上级不是同一个人。新上级给原上级发奖励
            PfUserRebate pfUserRebate = new PfUserRebate();
            pfUserRebate.setCreateTime(new Date());
            pfUserRebate.setCreateTime(new Date());
            if (pfUserUpgradeNotice != null) {
                pfUserRebate.setUserUpgradeNoticeId(pfUserUpgradeNotice.getId());
            }
            pfUserRebate.setUserUpgradeNoticeId(pfUserUpgradeNotice.getId());
            log.info("通知单id--------" + pfUserUpgradeNotice.getId());
            pfUserRebate.setUserId(pfUserUpgradeNotice.getUserPid());//获得奖励用户id
            pfUserRebate.setUserPid(pfBorder.getUserPid());//支付奖励用户id
            pfUserRebate.setPfBorderId(pfBorder.getId());
            int i = pfUserRebateService.insert(pfUserRebate);
            if (i != 1) {
                log.info("升级支付成功插入一次性奖励失败");
                throw new BusinessException("升级支付成功插入一次性奖励失败");
            }
        } else {
            log.info("原上级和新上级是同一个人不需要发送奖励");
        }
    }

    /**
     * 修改小铺商品信息
     *
     * @param shopUserId
     * @param orderItems
     */
    private void updateSfShopSku(Long shopUserId, List<PfBorderItem> orderItems) {
        for (PfBorderItem orderItem : orderItems) {
            log.info("修改小铺商品的sku等级和保证金-----小铺userId---" + shopUserId + "----skuId----" + orderItem.getSkuId());
            List<SfShopSku> sfShopSkus = sfShopSkuService.getSfShopSkuByUserIdAndSkuId(shopUserId, orderItem.getSkuId());
            for (SfShopSku sfShopSku:sfShopSkus){
                if (sfShopSku != null) {
                    log.info("修改前的小铺商品的代理等级---之前--" + orderItem.getAgentLevelId());
                    sfShopSku.setAgentLevelId(orderItem.getAgentLevelId());
                    log.info("修改前的小铺商品的代理等级---之后--" + orderItem.getAgentLevelId());
                    log.info("修改前的小铺保证金-----" + sfShopSku.getBail());
                    sfShopSku.setBail(orderItem.getBailAmount());
                    log.info("修改后的小铺保证金-----" + orderItem.getBailAmount());
                    int i = sfShopSkuService.update(sfShopSku);
                    if (i != 1) {
                        log.info("修改小铺商品的sku等级和保证金失败");
                        throw new BusinessException("修改小铺商品的sku等级和保证金失败");
                    }
                }
            }
        }
    }

    /**
     * 修改通知单状态
     *
     * @param pfBorderId
     */
    private void updateUpgradeNotice(Long pfBorderId) {
        log.info("修改通知单状态--------" + pfBorderId);
        //修改当前申请升级的通知单状态
        log.info("修改当前申请升级的通知单状态----start");
        Long userId = updateCurrentNotice(pfBorderId);
        log.info("修改当前申请升级的通知单状态----end---当前用户id---" + userId);
        //判断当前升级是否有下级，有下级则修改下级的状态
        if (userId != null) {
            log.info("当前升级是否有下级，有下级则修改下级的状态并且给处理中的下级发送微信通知--start");
            updateAllLowerNoticeAndSendLowerNotice(userId);
            log.info("当前升级是否有下级，有下级则修改下级的状态并且给处理中的下级发送微信通知--end");
        } else {
            log.info("修改当前申请升级的通知单状态状态失败");
        }
    }
    /**
     * 修改当前申请升级的通知单状态
     *
     * @param pfBorderId
     * @return
     */
    private Long updateCurrentNotice(Long pfBorderId) {
        log.info("修改当前升级的通知单状态-----订单id---" + pfBorderId);
        PfUserUpgradeNotice pfUserUpgradeNotice = userUpgradeNoticeService.selectByPfBorderId(pfBorderId);
        if (pfUserUpgradeNotice != null) {
            pfUserUpgradeNotice.setStatus(UpGradeStatus.STATUS_Complete.getCode());
            userUpgradeNoticeService.update(pfUserUpgradeNotice);
            return pfUserUpgradeNotice.getUserId();
        }
        return null;
    }

    /**
     * 判断当前升级是否有下级，有下级则修改下级的状态,并且给处理中的下级发送微信通知
     *
     * @param userId
     */
    private void updateAllLowerNoticeAndSendLowerNotice(Long userId) {
        log.info("修改所有下级为处理中的状态-----父id----" + userId);
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(UpGradeStatus.STATUS_Untreated.getCode());
        statusList.add(UpGradeStatus.STATUS_Processing.getCode());
        List<PfUserUpgradeNotice> notices = userUpgradeNoticeService.selectByUserPidAndInStatus(userId, statusList);
        if (notices != null) {
            log.info("下级的人数--------" + notices.size());
        } else {
            log.info("下级人数为null");
        }
        for (PfUserUpgradeNotice notice : notices) {
            UpGradeInfoPo upGradeInfoPo = null;
            ComUser comUser = null;
            log.info("通知单id------" + notice.getId() + "------通知单状态------" + notice.getStatus());
            if (notice.getStatus() == UpGradeStatus.STATUS_Processing.getCode()) {
                log.info("上级升级成功给下级处理中的发送微信通知，获得下级信息----start");
                comUser = comUserService.getUserById(notice.getUserId());
                upGradeInfoPo = upgradeNoticeService.getUpGradeInfo(notice.getId());
                log.info("上级升级成功给下级处理中的发送微信通知，获得下级信息----end");
            }
            log.info("下级id-------" + notice.getUserId() + "-----下级的状态status----" + notice.getStatus());
            notice.setStatus(UpGradeStatus.STATUS_NoPayment.getCode());
            notice.setUpStatus(UpGradeUpStatus.UP_STATUS_Complete.getCode());
            userUpgradeNoticeService.update(notice);
            if (upGradeInfoPo != null && comUser != null) {
                Boolean bl = upgradeWechatNewsService.upgradeApplyAuditPassNotice(comUser, upGradeInfoPo, "/upgrade/myApplyUpgrade.shtml?upgradeId=" + notice.getId());
                if (bl) {
                    log.info("上级升级成功给下级处理中的发送微信通知---success");
                } else {
                    log.info("上级升级成功给下级处理中的发送微信通知---fail");
                }
            }
        }
    }

}
