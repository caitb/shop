package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.shop.SfShopSkuMapper;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.product.PfSkuStatisticMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.user.*;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.platform.service.product.PfUserSkuStockService;
import com.masiis.shop.web.platform.utils.AsyncUploadCertUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * BOrderPayAgentService
 *
 * @author ZhaoLiang
 * @date 2016/8/10
 */
@Service
@Transactional
public class DirectAgentService {

    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private PfSkuStatisticMapper pfSkuStatisticMapper;
    @Resource
    private PfUserCertificateService pfUserCertificateService;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;
    @Resource
    private SfShopMapper sfShopMapper;
    @Resource
    private SfShopSkuMapper sfShopSkuMapper;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private SkuService skuService;
    @Resource
    private PfUserRecommendRelationService pfUserRecommendRelationService;
    @Resource
    private PfUserBrandService pfUserBrandService;
    @Resource
    private ComSpuMapper comSpuMapper;
    @Resource
    private PfUserStatisticsService userStatisticsService;

    public void directAgent(ComUser comUser, Integer skuId) throws Exception {
        PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(comUser.getId(), skuId);
        if (pfUserSku == null) {
            //获取商品对象
            ComSku comSku = skuService.getSkuById(skuId);
            //获取SPU对象
            ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
            List<ComSku> comSkuList = pfUserBrandService.getPrimarySkuByBrandId(comSpu.getBrandId());
            Integer mainSkuId = 0;
            Integer mainAgentLevelId = 0;// 获取主打商品的合伙等级
            Long mainUserPid = 0L;
            for (ComSku cs : comSkuList) {
                PfUserSku main_pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(comUser.getId(), cs.getId());
                if (main_pfUserSku != null) {
                    mainSkuId = main_pfUserSku.getSkuId();
                    mainAgentLevelId = main_pfUserSku.getAgentLevelId();
                    mainUserPid = main_pfUserSku.getUserPid();
                    break;
                }
            }
            if (mainAgentLevelId.equals(0)) {
                throw new BusinessException("还未代理主打商品");
            }

            logger.info("<1>为小铺生成商品");
            addShopSku(comUser, comSku, mainAgentLevelId);
            logger.info("<2>处理合伙推荐关系");
            addUserRecommenRelation(comUser, comSku, mainSkuId);
            logger.info("<3>处理用户合伙关系");
            addUserSku(comUser, comSku, mainUserPid, mainAgentLevelId);
            logger.info("<4>初始化品牌合伙关系");
            addUserBrand(comUser, comSpu);
            logger.info("<5>修改代理人数(如果是代理类型的订单增加修改sku代理人数)");
            pfSkuStatisticMapper.updateAgentNumBySkuId(skuId);
            logger.info("<6>初始化用户商品库存");
            addUserSkuStock(comUser, comSku, comSpu);
            logger.info("<7>初始化用户统计表信息");
            insertStatisticsUserInfo(comUser, comSku);
            //添加合伙证书 回写证书编号
            try {
                AsyncUploadCertUtil.getInstance().getUploadOSSQueue().put(comUser);
            } catch (InterruptedException e) {
                logger.error("阻塞住了");
            }
        }
    }

    /**
     * 初始化用户商品库存
     *
     * @param comUser 用户对象
     * @param comSku  sku对象
     * @param comSpu  spu对象
     */
    private void addUserSkuStock(ComUser comUser, ComSku comSku, ComSpu comSpu) {
        PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(comUser.getId(), comSku.getId());
        if (pfUserSkuStock == null) {
            pfUserSkuStock = new PfUserSkuStock();
            pfUserSkuStock.setCreateTime(new Date());
            pfUserSkuStock.setUserId(comUser.getId());
            pfUserSkuStock.setSpuId(comSpu.getId());
            pfUserSkuStock.setSkuId(comSku.getId());
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
     * @param rootPath
     */

    /**
     * 处理用户合伙关系和证书数据
     *
     * @param comUser          用户对象
     * @param comSku           SKU对象
     * @param mainUserPid      主商品上级用户id
     * @param mainAgentLevelId 主商品代理等级
     */
    private void addUserSku(ComUser comUser, ComSku comSku, Long mainUserPid, Integer mainAgentLevelId) {
        PfUserSku thisUS = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(comUser.getId(), comSku.getId());
        if (thisUS == null) {
            thisUS = new PfUserSku();
            thisUS.setCreateTime(new Date());
            thisUS.setCreateTime(new Date());
            PfUserSku parentUS = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(mainUserPid, comSku.getId());
            if (parentUS == null) {
                if (mainUserPid == 0) {
                    thisUS.setPid(0);
                    thisUS.setUserPid(0L);
                    thisUS.setTreeLevel(1);
                } else {
                    throw new BusinessException("您的上级还没有代理此商品！");
                }
            } else {
                thisUS.setPid(parentUS.getId());
                thisUS.setUserPid(parentUS.getUserId());
                thisUS.setTreeLevel(parentUS.getTreeLevel() + 1);
            }
            thisUS.setAgentNum(0l);
            thisUS.setUserId(comUser.getId());
            thisUS.setSkuId(comSku.getId());
            thisUS.setAgentLevelId(mainAgentLevelId);
            thisUS.setIsPay(1);
            thisUS.setIsCertificate(1);
            thisUS.setPfBorderId(0L);
            thisUS.setBail(BigDecimal.ZERO);
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
        }
    }


    /**
     * 为小铺生成商品
     *
     * @param comUser          用户
     * @param comSku           sku
     * @param mainAgentLevelId 合伙等级
     */
    private void addShopSku(ComUser comUser, ComSku comSku, Integer mainAgentLevelId) {
        SfShop sfShop = sfShopMapper.selectByUserId(comUser.getId());
        if (sfShop != null) {
            SfShopSku sfShopSku = sfShopSkuMapper.selectByShopIdAndSkuId(sfShop.getId(), comSku.getId(), 0);
            if (sfShopSku == null) {
                sfShopSku = new SfShopSku();
                sfShopSku.setCreateTime(new Date());
                sfShopSku.setShopId(sfShop.getId());
                sfShopSku.setShopUserId(comUser.getId());
                sfShopSku.setSpuId(comSku.getSpuId());
                sfShopSku.setSkuId(comSku.getId());
                sfShopSku.setIsOwnShip(0);
                sfShopSku.setIsSale(1);
                sfShopSku.setAgentLevelId(mainAgentLevelId);
                sfShopSku.setBail(BigDecimal.ZERO);
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

    /**
     * 处理合伙推荐关系
     *
     * @param comUser  用户对象
     * @param comSku   sku
     * @param maiSkuId 主打skuId
     */
    private void addUserRecommenRelation(ComUser comUser, ComSku comSku, Integer maiSkuId) {
        //主打商品推荐关系
        PfUserRecommenRelation main_pfUserRecommenRelation = pfUserRecommendRelationService.selectRecommenRelationByUserIdAndSkuId(comUser.getId(), maiSkuId);
        if (main_pfUserRecommenRelation != null && main_pfUserRecommenRelation.getPid() > 0) {
            //找到副商品的推荐人数据
            PfUserRecommenRelation p_pfUserRecommenRelation = pfUserRecommendRelationService.selectRecommenRelationByUserIdAndSkuId(main_pfUserRecommenRelation.getUserPid(), comSku.getId());
            PfUserRecommenRelation pfUserRecommenRelation = new PfUserRecommenRelation();
            pfUserRecommenRelation.setCreateTime(new Date());
            pfUserRecommenRelation.setPid(p_pfUserRecommenRelation.getId());
            pfUserRecommenRelation.setUserId(comUser.getId());
            pfUserRecommenRelation.setUserPid(p_pfUserRecommenRelation.getUserId());
            pfUserRecommenRelation.setSkuId(comSku.getId());
            pfUserRecommenRelation.setPfBorderId(0L);
            pfUserRecommenRelation.setTreeCode("");
            pfUserRecommenRelation.setTreeLevel(p_pfUserRecommenRelation.getTreeLevel() + 1);
            pfUserRecommenRelation.setRemark("绑定合伙人推荐关系");
            pfUserRecommendRelationService.insert(pfUserRecommenRelation);
            String treeCode = p_pfUserRecommenRelation.getTreeCode() + pfUserRecommenRelation.getId() + ",";
            if (pfUserRecommendRelationService.updateTreeCodeById(pfUserRecommenRelation.getId(), treeCode) != 1) {
                throw new BusinessException("treeCode修改失败");
            }
        } else {
            PfUserRecommenRelation pfUserRecommenRelation = new PfUserRecommenRelation();
            pfUserRecommenRelation.setCreateTime(new Date());
            pfUserRecommenRelation.setPid(0);
            pfUserRecommenRelation.setUserId(comUser.getId());
            pfUserRecommenRelation.setUserPid(0l);
            pfUserRecommenRelation.setSkuId(comSku.getId());
            pfUserRecommenRelation.setPfBorderId(0L);
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


    /**
     * 初始化用户统计表信息
     *
     * @param comUser
     * @param comSku
     */
    private void insertStatisticsUserInfo(ComUser comUser, ComSku comSku) {
        PfUserStatistics pfUserStatistics = new PfUserStatistics();
        pfUserStatistics.setCreateTime(new Date());
        pfUserStatistics.setUserId(comUser.getId());
        pfUserStatistics.setSkuId(comSku.getId());
        pfUserStatistics.setIncomeFee(new BigDecimal(0));
        pfUserStatistics.setProfitFee(new BigDecimal(0));
        pfUserStatistics.setCostFee(BigDecimal.ZERO);
        pfUserStatistics.setUpOrderCount(0);
        pfUserStatistics.setUpProductCount(0);
        pfUserStatistics.setDownOrderCount(0);
        pfUserStatistics.setDownProductCount(0);
        pfUserStatistics.setTakeOrderCount(0);
        pfUserStatistics.setTakeProductCount(0);
        pfUserStatistics.setTakeFee(new BigDecimal(0));
        pfUserStatistics.setVersion(0L);
        pfUserStatistics.setRecommenGetFee(BigDecimal.ZERO);
        pfUserStatistics.setRecommenSendFee(BigDecimal.ZERO);
        userStatisticsService.insert(pfUserStatistics);
    }
}
