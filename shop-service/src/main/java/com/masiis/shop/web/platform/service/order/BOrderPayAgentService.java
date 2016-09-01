package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.platform.BOrderUserSource;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.shop.SfShopSkuMapper;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.order.PfBorderPaymentMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.product.PfSkuStatisticMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.user.*;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.mall.service.shop.SfShopStatisticsService;
import com.masiis.shop.web.platform.service.product.PfSkuStockService;
import com.masiis.shop.web.platform.service.product.PfUserSkuStockService;
import com.masiis.shop.web.platform.utils.AsyncUploadCertUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * BOrderPayAgentService
 *
 * @author ZhaoLiang
 * @date 2016/8/10
 */
@Service
@Transactional
public class BOrderPayAgentService {

    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private PfBorderMapper pfBorderMapper;
    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private PfBorderPaymentMapper pfBorderPaymentMapper;
    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private PfSkuStatisticMapper pfSkuStatisticMapper;
    @Resource
    private PfUserCertificateService pfUserCertificateService;
    @Resource
    private SfShopMapper sfShopMapper;
    @Resource
    private SfShopSkuMapper sfShopSkuMapper;
    @Resource
    private BOrderOperationLogService bOrderOperationLogService;
    @Resource
    private PfSkuStockService pfSkuStockService;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private SkuService skuService;
    @Resource
    private SfShopStatisticsService shopStatisticsService;
    @Resource
    private BOrderStatisticsService orderStatisticsService;
    @Resource
    private BOrderBillAmountService billAmountService;
    @Resource
    private PfUserRecommendRelationService pfUserRecommendRelationService;
    @Resource
    private PfBorderRecommenRewardService pfBorderRecommenRewardService;
    @Resource
    private BOrderShipService bOrderShipService;
    @Resource
    private PfUserBrandService pfUserBrandService;
    @Resource
    private ComSpuMapper comSpuMapper;
    @Resource
    private PfUserOrganizationService pfUserOrganizationService;

    /**
     * 合伙订单支付回调
     *
     * @param pfBorderPayment 订单支付对象
     * @param outOrderId      第三方支付订单id
     */
    public void payBOrderAgent(PfBorderPayment pfBorderPayment, String outOrderId) {
        Long bOrderId = pfBorderPayment.getPfBorderId();
        //获取订单对象
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(bOrderId);
        //获取用户对象
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        logger.info("<1>修改订单支付信息");
        pfBorderPayment.setOutOrderId(outOrderId);
        pfBorderPayment.setIsEnabled(1);//设置为有效
        pfBorderPaymentMapper.updateById(pfBorderPayment);
        logger.info("<2>修改订单数据");
        modifyBOrderAndAddLog(pfBorder, pfBorderPayment);
        logger.info("<3>修改用户合伙状态和拿货类型");
        if (comUser.getIsAgent() == 0) {
            comUser.setIsAgent(1);
        }
        if (comUser.getSendType() == 0) {
            comUser.setSendType(pfBorder.getSendType());
        }
        comUserMapper.updateByPrimaryKey(comUser);
        logger.info("<4>为用户生成小铺并初始化小铺统计表");
        addShopAndShopStatistics(comUser);
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(bOrderId)) {
            //获取商品对象
            ComSku comSku = skuService.getSkuById(pfBorderItem.getSkuId());
            //获取SPU对象
            ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
            logger.info("<5>为小铺生成商品");
            addShopSku(comUser, pfBorderItem);
            logger.info("<6>处理合伙推荐关系");
            addUserRecommenRelation(comUser, pfBorder, pfBorderItem);
            logger.info("<7>处理用户合伙关系和证书数据");
            addUserSkuAndUserCertificate(comUser, comSku, pfBorder, pfBorderItem);
            logger.info("<8>初始化品牌合伙关系");
            addUserBrand(comUser, comSpu);
            logger.info("<9>修改代理人数(如果是代理类型的订单增加修改sku代理人数)");
            if (pfBorder.getOrderType() == 0) {
                pfSkuStatisticMapper.updateAgentNumBySkuId(pfBorderItem.getSkuId());
            }
            logger.info("<10>初始化用户商品库存");
            addUserSkuStock(pfBorder, pfBorderItem);
            logger.info("<11>增加冻结库存");
            if (pfBorderItem.getQuantity() > 0) {
                modifySkuFrozenStock(pfBorder, pfBorderItem);
            }
            logger.info("<12>修改上级小白合伙人数");
            modifyUserOrganization(pfBorder, comSpu);
        }
        logger.info("<13>实时统计数据显示");
        orderStatisticsService.statisticsOrder(pfBorder.getId());
        logger.info("<14>修改结算中数据");
        billAmountService.orderBillAmount(pfBorder.getId());
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getSendType() == 1 && pfBorder.getOrderStatus().equals(BOrderStatus.WaitShip.getCode())) {
            //处理平台发货类型订单
            bOrderShipService.shipAndReceiptBOrder(pfBorder);
        }
    }

    /**
     * 修改上级小白合伙人数
     *
     * @param pfBorder
     * @param comSpu
     */
    private void modifyUserOrganization(PfBorder pfBorder, ComSpu comSpu) {
        if (pfBorder.getUserSource().equals(BOrderUserSource.free.getCode()) && pfBorder.getUserPid() != 0) {
            PfUserOrganization pfUserOrganization = pfUserOrganizationService.getByUserIdAndBrandId(pfBorder.getUserPid(), comSpu.getBrandId());
            if (pfUserOrganization != null) {
                pfUserOrganization.setFreemanNum(pfUserOrganization.getFreemanNum() + 1);
                if (pfUserOrganizationService.update(pfUserOrganization) != 1) {
                    throw new BusinessException("PfUserOrganization修改小白上限人数失败");
                }
            }
        }
    }

    /**
     * 增加冻结库存
     *
     * @param pfBorder     订单对象
     * @param pfBorderItem 订单商品表对象
     */
    private void modifySkuFrozenStock(PfBorder pfBorder, PfBorderItem pfBorderItem) {
        if (pfBorder.getUserPid() == 0) {
            PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(pfBorderItem.getSkuId());
            //如果可售库存不足或者排单开关打开的情况下 订单进入排单
            if (pfSkuStock.getIsQueue() == 1 || pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                //平台库存不足，排单处理
                pfBorder.setOrderStatus(BOrderStatus.MPS.getCode());//排队订单
                if (pfBorderMapper.updateById(pfBorder) != 1) {
                    throw new BusinessException("修改订单状态为排队订单失败");
                }
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
                if (pfBorderMapper.updateById(pfBorder) != 1) {
                    throw new BusinessException("修改订单状态为排队订单失败");
                }
            }
            //增加平台冻结库存
            parentSkuStock.setFrozenStock(parentSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
            if (pfUserSkuStockService.updateByIdAndVersions(parentSkuStock) != 1) {
                throw new BusinessException("(代理发货)排队订单增加冻结量失败");
            }
        }
    }

    /**
     * 初始化用户商品库存
     *
     * @param pfBorder     订单对象
     * @param pfBorderItem 订单商品表对象
     */
    private void addUserSkuStock(PfBorder pfBorder, PfBorderItem pfBorderItem) {
        PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
        if (pfUserSkuStock == null) {
            pfUserSkuStock = new PfUserSkuStock();
            pfUserSkuStock.setCreateTime(new Date());
            pfUserSkuStock.setUserId(pfBorder.getUserId());
            pfUserSkuStock.setSpuId(pfBorderItem.getSpuId());
            pfUserSkuStock.setSkuId(pfBorderItem.getSkuId());
            pfUserSkuStock.setStock(0);
            pfUserSkuStock.setFrozenStock(0);
            pfUserSkuStock.setFrozenCustomStock(0);
            pfUserSkuStock.setCustomStock(0);
            pfUserSkuStock.setVersion(0);
            pfUserSkuStockService.insert(pfUserSkuStock);
        }
    }

    /**
     * 初始化品牌代理关系
     *
     * @param comUser 用户对象
     * @param comSpu  SPU对象
     */
    private void addUserBrand(ComUser comUser, ComSpu comSpu) {
        PfUserBrand pfUserBrand = pfUserBrandService.findByUserIdAndBrandId(comUser.getId(), comSpu.getBrandId());
        if (pfUserBrand == null) {
            pfUserBrand = new PfUserBrand();
            pfUserBrand.setCreateTime(new Date());
            pfUserBrand.setUserId(comUser.getId());
            pfUserBrand.setBrandId(comSpu.getBrandId());
            pfUserBrand.setRemark("初始化品牌代理关系");
            if (pfUserBrandService.insert(pfUserBrand) != 1) {
                throw new BusinessException("pfUserBrand插入失败");
            }
        }
    }

    /**
     * 处理用户合伙关系和证书数据
     *
     * @param comUser      用户对象
     * @param comSku       SKU对象
     * @param pfBorder     订单对象
     * @param pfBorderItem 订单商品表对象
     */
    private void addUserSkuAndUserCertificate(ComUser comUser, ComSku comSku, PfBorder pfBorder, PfBorderItem pfBorderItem) {
        PfUserSku thisUS = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(comUser.getId(), pfBorderItem.getSkuId());
        if (thisUS == null) {
            thisUS = new PfUserSku();
            thisUS.setCreateTime(new Date());
            thisUS.setCreateTime(new Date());
            PfUserSku parentUS = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
            if (parentUS == null) {
                thisUS.setPid(0);
                thisUS.setUserPid(0L);
                thisUS.setTreeLevel(1);
            } else {
                thisUS.setPid(parentUS.getId());
                thisUS.setUserPid(parentUS.getUserId());
                thisUS.setTreeLevel(pfBorderItem.getAgentLevelId());
            }
            thisUS.setAgentNum(0l);
            thisUS.setUserId(pfBorder.getUserId());
            thisUS.setSkuId(pfBorderItem.getSkuId());
            thisUS.setAgentLevelId(pfBorderItem.getAgentLevelId());
            thisUS.setIsPay(1);
            thisUS.setIsCertificate(1);
            thisUS.setPfBorderId(pfBorder.getId());
            thisUS.setBail(pfBorder.getBailAmount());
            thisUS.setRemark("");
            thisUS.setCode("");
//            logger.info(thisUS.toString());
            pfUserSkuService.insert(thisUS);
            String treeCode = "";
            if (parentUS == null) {
                treeCode = thisUS.getSkuId() + "," + thisUS.getId() + ",";
            } else {
                treeCode = parentUS.getTreeCode() + thisUS.getId() + ",";
            }
            if (pfUserSkuService.updateTreeCodeById(thisUS.getId(), treeCode) != 1) {
                throw new BusinessException("treeCode修改失败");
            }
            //添加合伙证书 回写证书编号
            try {
                AsyncUploadCertUtil.getInstance().getUploadOSSQueue().put(comUser.getId());
            } catch (InterruptedException e) {
                logger.error("阻塞住了");
            }
        }
    }

    /**
     * 为小铺生成商品
     *
     * @param pfBorderItem 订单商品表对象
     */
    private void addShopSku(ComUser comUser, PfBorderItem pfBorderItem) {
        SfShop sfShop = sfShopMapper.selectByUserId(comUser.getId());
        if (sfShop != null) {
            SfShopSku sfShopSku = sfShopSkuMapper.selectByShopIdAndSkuId(sfShop.getId(), pfBorderItem.getSkuId(), 0);
            if (sfShopSku == null) {
                sfShopSku = new SfShopSku();
                sfShopSku.setCreateTime(new Date());
                sfShopSku.setShopId(sfShop.getId());
                sfShopSku.setShopUserId(comUser.getId());
                sfShopSku.setSpuId(pfBorderItem.getSpuId());
                sfShopSku.setSkuId(pfBorderItem.getSkuId());
                sfShopSku.setIsOwnShip(0);
                sfShopSku.setIsSale(1);
                sfShopSku.setAgentLevelId(pfBorderItem.getAgentLevelId());
                sfShopSku.setBail(pfBorderItem.getBailAmount());
                sfShopSku.setSaleNum(0l);
                sfShopSku.setShareNum(0l);
                sfShopSku.setQrCode("");
                sfShopSku.setRemark("");
                sfShop.setOwnShipAmount(BigDecimal.ZERO);
                sfShopSkuMapper.insert(sfShopSku);
            }
        }
    }

    /**
     * 处理合伙推荐关系
     *
     * @param comUser      用户对象
     * @param pfBorder     订单对象
     * @param pfBorderItem 订单商品表对象
     */
    private void addUserRecommenRelation(ComUser comUser, PfBorder pfBorder, PfBorderItem pfBorderItem) {
        PfUserRecommenRelation pfUserRecommenRelation = pfUserRecommendRelationService.selectRecommenRelationByUserIdAndSkuId(comUser.getId(), pfBorderItem.getSkuId());
        if (pfUserRecommenRelation == null) {
            PfBorderRecommenReward pfBorderRecommenReward = pfBorderRecommenRewardService.getByPfBorderItemId(pfBorderItem.getId());
            if (pfBorder.getUserPid() != 0 && pfBorderRecommenReward != null) {
                PfUserRecommenRelation parentPfUserRecommenRelation = pfUserRecommendRelationService.selectRecommenRelationByUserIdAndSkuId(pfBorderRecommenReward.getRecommenUserId(), pfBorderItem.getSkuId());
                pfUserRecommenRelation = new PfUserRecommenRelation();
                pfUserRecommenRelation.setCreateTime(new Date());
                pfUserRecommenRelation.setPid(parentPfUserRecommenRelation.getId());
                pfUserRecommenRelation.setUserId(comUser.getId());
                pfUserRecommenRelation.setUserPid(parentPfUserRecommenRelation.getUserId());
                pfUserRecommenRelation.setSkuId(pfBorderItem.getSkuId());
                pfUserRecommenRelation.setPfBorderId(pfBorder.getId());
                pfUserRecommenRelation.setTreeCode("");
                pfUserRecommenRelation.setTreeLevel(parentPfUserRecommenRelation.getTreeLevel() + 1);
                pfUserRecommenRelation.setRemark("绑定合伙人推荐关系");
                pfUserRecommendRelationService.insert(pfUserRecommenRelation);
                String treeCode = parentPfUserRecommenRelation.getTreeCode() + pfUserRecommenRelation.getId() + ",";
                if (pfUserRecommendRelationService.updateTreeCodeById(pfUserRecommenRelation.getId(), treeCode) != 1) {
                    throw new BusinessException("treeCode修改失败");
                }
            } else {
                pfUserRecommenRelation = new PfUserRecommenRelation();
                pfUserRecommenRelation.setCreateTime(new Date());
                pfUserRecommenRelation.setPid(0);
                pfUserRecommenRelation.setUserId(comUser.getId());
                pfUserRecommenRelation.setUserPid(0l);
                pfUserRecommenRelation.setSkuId(pfBorderItem.getSkuId());
                pfUserRecommenRelation.setPfBorderId(pfBorder.getId());
                pfUserRecommenRelation.setTreeCode("");
                pfUserRecommenRelation.setTreeLevel(1);
                pfUserRecommenRelation.setRemark("初始化合伙人推荐关系");
                pfUserRecommendRelationService.insert(pfUserRecommenRelation);
                String treeCode = pfUserRecommenRelation.getId() + ",";
                if (pfUserRecommendRelationService.updateTreeCodeById(pfUserRecommenRelation.getId(), treeCode) != 1) {
                    throw new BusinessException("treeCode修改失败");
                }
            }
        }
    }

    /**
     * 为用户生成小铺并初始化小铺统计表
     *
     * @param comUser 用户对象
     */
    private void addShopAndShopStatistics(ComUser comUser) {
        SfShop sfShop = sfShopMapper.selectByUserId(comUser.getId());
        if (sfShop == null) {
            sfShop = new SfShop();
            sfShop.setCreateTime(new Date());
            sfShop.setUserId(comUser.getId());
            sfShop.setStatus(1);
            sfShop.setExplanation("主营各类化妆品、保健品");
            sfShop.setLogo(comUser.getWxHeadImg());
            sfShop.setName(comUser.getRealName() + "的小店");
            sfShop.setPageviews(0l);
            sfShop.setQrCode("");
            sfShop.setSaleAmount(BigDecimal.ZERO);
            sfShop.setShipType(1);//运费类型0：消费者出运费1：代理商出运费
            sfShop.setShipAmount(BigDecimal.ZERO);
            sfShop.setAgentShipAmount(new BigDecimal(8));
            sfShop.setShoutNum(0l);
            sfShop.setVersion(0l);
            sfShop.setRemark("");
            sfShop.setOwnShipAmount(BigDecimal.ZERO);
            sfShopMapper.insert(sfShop);
        }
        SfShopStatistics shopStatistics = shopStatisticsService.selectByShopUserId(comUser.getId());
        if (shopStatistics == null) {
            shopStatistics = new SfShopStatistics();
            shopStatistics.setCreateTime(new Date());
            shopStatistics.setShopId(sfShop.getId());
            shopStatistics.setUserId(comUser.getId());
            shopStatistics.setIncomeFee(new BigDecimal(0));
            shopStatistics.setProfitFee(new BigDecimal(0));
            shopStatistics.setOrderCount(0);
            shopStatistics.setProductCount(0);
            shopStatistics.setPageviewsCount(0);
            shopStatistics.setShareCount(0);
            shopStatistics.setReturnOrderCount(0);
            shopStatistics.setVersion(0);
            shopStatistics.setRemark("");
            shopStatisticsService.insert(shopStatistics);
        }
    }

    /**
     * 修改订单数据并添加订单日志
     *
     * @param pfBorder 订单对象
     */
    private void modifyBOrderAndAddLog(PfBorder pfBorder, PfBorderPayment pfBorderPayment) {
        BigDecimal payAmount = pfBorderPayment.getAmount();
        if (pfBorder.getPayStatus() != BOrderStatus.NotPaid.getCode() && pfBorder.getPayStatus() != BOrderStatus.offLineNoPay.getCode()) {
            throw new BusinessException("订单号:" + pfBorder.getId() + ",已经支付成功.");
        }
        pfBorder.setReceivableAmount(pfBorder.getReceivableAmount().subtract(payAmount));
        pfBorder.setPayAmount(pfBorder.getPayAmount().add(payAmount));
        pfBorder.setPayTime(new Date());
        pfBorder.setPayStatus(1);
        pfBorder.setOrderStatus(BOrderStatus.WaitShip.getCode());//待发货
        pfBorderMapper.updateById(pfBorder);
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单支付成功");
    }

}
