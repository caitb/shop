package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.beans.order.BOrderUpgradeDetail;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.product.PfSkuStockService;
import com.masiis.shop.web.platform.service.product.PfUserSkuStockService;
import com.masiis.shop.web.platform.service.shop.SfShopSkuService;
import com.masiis.shop.web.platform.service.user.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
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
    private PfUserRecommendRelationService pfUserRecommendRelationService;
    @Resource
    private UpgradeWechatNewsService upgradeWechatNewsService;
    @Resource
    private UpgradeNoticeService upgradeNoticeService;

    public void paySuccessCallBack(PfBorderPayment pfBorderPayment, String outOrderId, String rootPath) {
        //修改订单支付
        updatePfBorderPayment(pfBorderPayment, outOrderId);
        //修改订单
        PfBorder pfBorder = updatePfBorder(pfBorderPayment.getPfBorderId(), pfBorderPayment);
        List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(pfBorder.getId());
        PfUserUpgradeNotice pfUserUpgradeNotice = userUpgradeNoticeService.selectByPfBorderId(pfBorder.getId());
        //添加订单操作日志
        insertOrderOperationLog(pfBorder);
        //修改上下级绑定关系和插入历史表
        inserHistoryAndUpdatePfUserSku(pfBorder.getUserId(), pfBorder.getUserPid(), pfBorder.getId(), pfBorderItems);
        //修改证书和插入证书历史表
        inserHistoryAndUpdatePfUserCertificate(pfBorder.getUserId(), pfBorderItems, null, rootPath);
        //修改冻结库存
        updateFrozenStock(pfBorder, pfBorderItems);
        //修改用户统计中奖励金额
        orderStatisticsService.statisticsOrder(pfBorder.getId());
        //插入一次性奖励
        insertUserRebate(pfBorder, pfBorderItems, pfUserUpgradeNotice);
        //修改小铺商品信息
        updateSfShopSku(pfBorder.getUserId(), pfBorderItems);
        //修改用户账户
        billAmountService.orderBillAmount(pfBorder.getId());
        //修改通知单状态
        updateUpgradeNotice(pfBorder.getId());
        if (pfBorder.getSendType() == 1 && pfBorder.getOrderStatus() == BOrderStatus.accountPaid.getCode()) {
            //处理平台发货类型订单
            saveBOrderSendType(pfBorder);
        }
        //支付完成推送消息(发送失败不回滚事务)
        try {
            //发送微信通知
            BOrderUpgradeDetail upgradeDetail = upgradeNoticeService.getUpgradeNoticeInfo(pfUserUpgradeNotice.getId());
            upgradeWechatNewsService.upgradeOrderPaySuccessSendWXNotice(pfBorder, pfBorderPayment, upgradeDetail);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

    }

    /**
     * 修改订单支付
     *
     * @param pfBorderPayment
     * @param outOrderId
     */
    private void updatePfBorderPayment(PfBorderPayment pfBorderPayment, String outOrderId) {
        log.info("------更新订单支付表------");
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
        log.info("----更新订单-----");
        PfBorder pfBorder = bOrderService.getPfBorderById(orderId);
        BigDecimal payAmount = pfBorderPayment.getAmount();
        if (pfBorder != null) {
            pfBorder.setOrderStatus(BOrderStatus.accountPaid.getCode());
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
            PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userId, orderItem.getSkuId());
            int i = insertUserSkuHistory(pfUserSku);
            if (i == 1) {
                updatePfUserSku(userPid, borderId, orderItem, pfUserSku);
            } else {
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
        BigDecimal bailAmount = orderItem.getBailAmount();
        Integer skuId = orderItem.getSkuId();
        Integer agentLevelId = orderItem.getAgentLevelId();
        int i = 0;
        if (pfUserSku != null) {
            PfUserSku parentPfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userPid, pfUserSku.getSkuId());
            if (!agentLevelId.equals(parentPfUserSku.getAgentLevelId())) {
                Integer parent_id = 0;
                Long parent_userPid = 0l;
                String parent_treeCode = pfUserSku.getSkuId() + ",";
                Integer parent_treeLevel = 0;
                if (null != parentPfUserSku) {
                    parent_id = parentPfUserSku.getId();
                    parent_userPid = parentPfUserSku.getUserId();
                    parent_treeCode = parentPfUserSku.getTreeCode();
                    parent_treeLevel = parentPfUserSku.getTreeLevel();
                }
                pfUserSku.setPid(parent_id);
                pfUserSku.setUserPid(parent_userPid);
                pfUserSku.setAgentLevelId(agentLevelId);
                pfUserSku.setIsPay(1);
                pfUserSku.setIsCertificate(1);
                pfUserSku.setPfBorderId(borderId);
                pfUserSku.setBail(bailAmount);
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
                    throw new BusinessException("树结构更换只能挂在高于自己的树枝");
                }
                i = pfUserSkuService.updateTreeCodes(treeCode, parentTreeCode, id_index, treeLevel);
                if (i <= 0) {
                    throw new BusinessException("分销关系树结构修改失败");
                }
            } else {
                throw new BusinessException("同等级不能合伙");
            }
        } else {
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
                    String picName = bOrderPayService.uploadFile(rootPath + "/static/images/certificate/" + comAgentLevel.getImgUrl(),//filePath - 原图的物理路径
                            rootPath + "/static/font/",//字体路径
                            pfUserCertificate.getCode(),//certificateCode - 证书编号
                            comUser.getRealName(),//userName - 用户名称
                            comAgentLevel.getName(),//levelName - 代理等级名称
                            orderItem.getSkuName(),//skuName - 商品名称
                            orderItem.getSkuName(),
                            comUser.getIdCard(),//idCard - 身份证号
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
        log.info("-----处理发货库存----");
        for (PfBorderItem pfBorderItem : pfBorderItems) {
            if (pfBorder.getUserPid() == 0) {
                PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(pfBorderItem.getSkuId());
                //如果可售库存不足或者排单开关打开的情况下 订单进入排单
                if (pfSkuStock.getIsQueue() == 1 || pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                    //平台库存不足，排单处理
                    pfBorder.setOrderStatus(BOrderStatus.MPS.getCode());//排队订单
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

    /**
     * 处理平台拿货类型订单
     * <1>减少发货方库存 如果用户id是0操作平台库存
     * <2>增加收货方库存
     * <3>订单完成处理
     *
     * @param pfBorder
     */
    public void saveBOrderSendType(PfBorder pfBorder) {
        bOrderPayService.saveBOrderSendType(pfBorder);
    }


    /**
     * 插入一次性奖励
     *
     * @param pfBorder
     */
    private void insertUserRebate(PfBorder pfBorder, List<PfBorderItem> orderItems, PfUserUpgradeNotice pfUserUpgradeNotice) {
        for (PfBorderItem pfBorderItem : orderItems) {
            PfUserRecommenRelation userRecommenRelation = pfUserRecommendRelationService.selectRecommenRelationByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
            if (!userRecommenRelation.getUserPid().equals(pfBorder.getUserPid())){
                PfUserRebate pfUserRebate = new PfUserRebate();
                pfUserRebate.setCreateTime(new Date());
                pfUserRebate.setCreateTime(new Date());
                if (pfUserUpgradeNotice != null) {
                    pfUserRebate.setUserUpgradeNoticeId(pfUserUpgradeNotice.getId());
                }
                pfUserRebate.setUserUpgradeNoticeId(pfUserUpgradeNotice.getId());
                log.info("通知单id--------"+pfUserUpgradeNotice.getId());
                pfUserRebate.setUserId(userRecommenRelation.getUserPid());//获得奖励用户id
                log.info("获得奖励人-------"+userRecommenRelation.getUserPid());
                pfUserRebate.setUserPid(pfBorder.getUserPid());//支付奖励用户id
                log.info("支付奖励用户-------"+pfBorder.getUserPid());
                pfUserRebate.setPfBorderId(pfBorder.getId());
                log.info("订单id------"+pfBorder.getId());
                int i = pfUserRebateService.insert(pfUserRebate);
                if (i != 1) {
                    log.info("升级支付成功插入一次性奖励失败");
                    throw new BusinessException("升级支付成功插入一次性奖励失败");
                }
            }else{
                log.info("获得奖励和支付奖励的人是同一个人");
            }
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
            SfShopSku sfShopSku = sfShopSkuService.getSfShopSkuByUserIdAndSkuId(shopUserId, orderItem.getSkuId());
            if (sfShopSku != null) {
                sfShopSku.setAgentLevelId(orderItem.getAgentLevelId());
                sfShopSku.setBail(orderItem.getBailAmount());
                int i = sfShopSkuService.update(sfShopSku);
                if (i != 1) {
                    throw new BusinessException("修改小铺商品的sku等级和保证金失败");
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
        //修改当前申请升级的通知单状态
        Long userId = updateCurrentNotice(pfBorderId);
        //判断当前升级是否有下级，有下级则修改下级的状态
        if (userId != null) {
            updateAllLowerNotice(userId);
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
            pfUserUpgradeNotice.setStatus(3);
            userUpgradeNoticeService.update(pfUserUpgradeNotice);
            return pfUserUpgradeNotice.getUserId();
        }
        return null;
    }

    /**
     * 判断当前升级是否有下级，有下级则修改下级的状态
     *
     * @param userPid
     */
    private void updateAllLowerNotice(Long userPid) {
        log.info("修改所有下级为处理中的状态-----父id----" + userPid);
        List<PfUserUpgradeNotice> notices = userUpgradeNoticeService.selectByUserPidAndStatus(userPid, 1);
        for (PfUserUpgradeNotice notice : notices) {
            log.info("下级id-------" + notice.getUserId());
            notice.setStatus(3);
            userUpgradeNoticeService.update(notice);
        }
    }

}
