package com.masiis.shop.web.platform.service.product;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.AgentSku;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.beans.product.ProductSimple;
import com.masiis.shop.dao.platform.product.*;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.platform.service.user.PfUserBrandService;
import com.masiis.shop.web.platform.service.user.PfUserRecommendRelationService;
import com.masiis.shop.web.platform.service.user.PfUserSkuService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
@Transactional
public class ProductService {


    private final static Logger log = Logger.getLogger(ProductService.class);

    @Resource
    private ProductMapper productMapper;
    @Resource
    private ComSpuMapper comSpuMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComSkuImageMapper comSkuImageMapper;
    @Resource
    private ProductSimpleMapper productSimpleMapper;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private PfSkuStockService pfSkuStockService;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private ComBrandMapper comBrandMapper;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private SkuService skuService;
    @Resource
    private PfUserBrandService pfUserBrandService;
    @Resource
    private PfUserRecommendRelationService pfUserRecommendRelationService;

    private static Integer pageSize = 10;
    /**
     * @Author 贾晶豪
     * @Date 2016/3/5 0005 下午 2:30
     * 根据商品ID展示商品属性详情
     */
    public Product getSkuDetails(Integer skuId) throws Exception {
        Product product = productMapper.getSkuDetailsBySkuId(skuId);
        Integer currentStock = product.getStock() - product.getFrozenStock();
        product.setStock(currentStock < 0 ? 0 : currentStock);
        ComSpu comSpu = comSpuMapper.selectById(product.getSpuId());
        ComBrand comBrand = comBrandMapper.selectById(comSpu.getBrandId());
        product.setLogoUrl(comBrand.getLogoUrl());
        product.setBrand(comBrand.getContent());
        product.setPolicy(comSpu.getPolicy());
        List<ComSkuImage> skuImgList = productMapper.getSkuImgById(skuId);
        String productImgValue = PropertiesUtils.getStringValue("index_product_prototype_url");
        if (skuImgList != null && skuImgList.size() > 0) {
            for (ComSkuImage comSkuImage : skuImgList) {
                comSkuImage.setFullImgUrl(productImgValue + comSkuImage.getImgUrl());
            }
            product.setComSkuImages(skuImgList);
        }
        return product;
    }

    /**
     * @Author Jing Hao
     * @Date 2016/3/5 0005 下午 2:30
     * 代理商利润
     */
    public Integer getMaxDiscount(Integer skuId) throws Exception {
        int bb = (int) ((1 - productMapper.maxDiscount(skuId)) * 100);
        return bb;
    }

    /**
     * 跳转到试用申请页
     *
     * @author hanzengzhi
     * @date 2016/3/5 16:19
     */
    public Product applyTrialToPageService(Integer skuId) {
        Product product = new Product();
        product.setId(skuId);
        try {
            log.info("获取商品信息------------"+skuId);
            ComSku comSku = comSkuMapper.selectById(skuId);
            if (comSku != null) {
                product.setName(comSku.getName());
                //获取运费
                ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
                if (comSpu != null) {
                    log.info("------获取图片------");
                    //获取默认图片
                    ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(skuId);
                    List<ComSkuImage> comSkuImages = new ArrayList<ComSkuImage>();
                    comSkuImages.add(comSkuImage);
                    log.info("运费--------------"+comSpu.getShipAmount().toString());
                    product.setShipAmount(comSpu.getShipAmount());
                    product.setComSkuImages(comSkuImages);
                }else{
                    log.info("---------不能获取图片----------");
                }
            }else{
                log.info("获取商品信息为null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    /**
     * 获取SKU简单数据
     *
     * @param skuId
     * @return
     * @throws Exception
     */
    public ProductSimple getSkuSimple(Integer skuId) throws Exception {
        return productSimpleMapper.selectBySkuId(skuId);
    }

    /**
     * @Author 贾晶豪
     * @Date 2016/3/16 0016 上午 10:31
     * 个人中心商品列表
     */
    public List<Product> productListByUser(Long userId) throws Exception {
        List<Product> userProducts = productMapper.getProductsByUser(userId);
        String productImgValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        if (userProducts != null) {
            for (Product product : userProducts) {
                int currentStock = product.getStock() - product.getFrozenStock();
                if (currentStock >= 0) {
                    product.setStock(currentStock);
                    product.setNeedStockNum(0);
                } else {
                    product.setNeedStockNum(product.getFrozenStock() - product.getStock());
                    product.setStock(0);
                }
                ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(product.getId());
                product.setComSkuImage(comSkuImage);
                product.getComSkuImage().setFullImgUrl(productImgValue + comSkuImage.getImgUrl());
                PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(product.getId());
                product.setIsQueue(pfSkuStock.getIsQueue());
                PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(userId, product.getId());
                product.setUserPid(pfUserSku.getUserPid());
            }
        }
        return userProducts;
    }


    public List<Product> productListByUserPage(Long userId,Integer pageNum) throws Exception {
        PageHelper.startPage(pageNum, pageSize, false);
        List<Product> userProducts = productMapper.getProductsByUser(userId);
        String productImgValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        if (userProducts != null) {
            for (Product product : userProducts) {
                int currentStock = product.getStock() - product.getFrozenStock();
                if (currentStock >= 0) {
                    product.setStock(currentStock);
                    product.setNeedStockNum(0);
                } else {
                    product.setNeedStockNum(product.getFrozenStock() - product.getStock());
                    product.setStock(0);
                }
                ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(product.getId());
                product.setComSkuImage(comSkuImage);
                product.getComSkuImage().setFullImgUrl(productImgValue + comSkuImage.getImgUrl());
                PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(product.getId());
                product.setIsQueue(pfSkuStock.getIsQueue());
                PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(userId, product.getId());
                product.setUserPid(pfUserSku.getUserPid());
            }
        }
        return userProducts;
    }
    /**
     * jjh
     * 个人中心商品列表--仓库中
     */
    public List<AgentSku> productListByUserNotAgent(Long userId,Integer pageNum) throws Exception {
        PageHelper.startPage(pageNum, pageSize, false);
        List<ComSku> comSkus = comSkuMapper.getProductsByUserNotAgent(userId);//展示自己没代理过的所有的商品
        List<AgentSku> agentSkus = new ArrayList<>();
        String productImgValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        if (comSkus != null) {
            for (ComSku comSku : comSkus) {
                AgentSku agentSku = new AgentSku();
                ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(comSku.getId());
                Map<String,Object> mainSkuMap = getPrimarySkuAgendlevel(userId, comSku.getId());
                Integer primarySkuId = Integer.parseInt(mainSkuMap.get("mainSkuId").toString());
                //获取推荐关系
                PfUserRecommenRelation pfUserRecommenRelation = pfUserRecommendRelationService.selectRecommenRelationByUserIdAndSkuId(userId,primarySkuId);
                if(pfUserRecommenRelation!=null && pfUserRecommenRelation.getUserPid()!=0){ //有推荐关系
                    PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(pfUserRecommenRelation.getUserPid(),comSku.getId());//推荐人对该商品的代理状态
                    if (pfUserSku==null){
                        agentSku.setUpgradeIsAgent(0);
                    }else {
                        agentSku.setUpgradeIsAgent(1);
                    }
                }else { //没有推荐关系--查找上级的代理关系
                    PfUserSku primaryPfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(userId,primarySkuId);//我对主商品的代理状态
                    if(primaryPfUserSku!=null && primaryPfUserSku.getUserPid()==0){//boss
                        agentSku.setIsBoss(1);
                    }else {
                        agentSku.setIsBoss(0);
                        PfUserSku upperPfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(primaryPfUserSku.getUserPid(),comSku.getId());//我的上级对该商品的代理状态
                        if (upperPfUserSku==null){
                            agentSku.setUpperIsAgent(0);
                        }else {
                            agentSku.setUpperIsAgent(1);
                        }
                    }
                }
                agentSku.setSkuImgURl(productImgValue + comSkuImage.getImgUrl());
                agentSku.setBrandName(comSku.getcName());
                agentSku.setPrice(pfSkuAgentMapper.selectBySkuIdAndLevelId(comSku.getId(),Integer.parseInt(mainSkuMap.get("mainAgentLevelId").toString())).getUnitPrice());
                agentSku.setSkuName(comSku.getName());
                agentSku.setSkuId(comSku.getId());
                agentSkus.add(agentSku);
            }
        }
        return agentSkus;
    }
    /**
     * @Author 贾晶豪
     * @Date 2016/3/16 0016 下午 7:38
     * 更新库存
     */
    public void updateStock(Integer selfStock, Integer skuId,Long userId) throws Exception {
        PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(userId, skuId);
        Map<String, Object> param = new HashMap<>();
        if (pfUserSkuStock != null) {
            param.put("selfStock", selfStock + pfUserSkuStock.getFrozenCustomStock());
            param.put("id", pfUserSkuStock.getId());
            param.put("version", pfUserSkuStock.getVersion());
            productMapper.updateStock(param);
        }
    }

    /**
     * @Author 贾晶豪
     * @Date 2016/3/21 0021 上午 10:13
     * 查看当前库存
     */
    public PfUserSkuStock getStockByUser(Long id) throws Exception {
        return pfUserSkuStockService.selectByPrimaryKey(id);
    }

    /**
     * 上级/平台库存
     */
    public Integer getUpperStock(Long UserId, Integer skuId) {
        Integer upperStock = 0;
        PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(UserId, skuId);//当前代理关系
        if (pfUserSku != null && pfUserSku.getPid() == 0) {
            PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(skuId);
            upperStock = pfSkuStock.getStock() - pfSkuStock.getFrozenStock();
        } else {
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfUserSku.getUserPid(), skuId);
            upperStock = pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock();
        }
        if (upperStock < 0) {
            upperStock = 0;
        }
        return upperStock;
    }

    /**
     * @Author Jing Hao
     * @Date 2016/3/31 0031 上午 11:07
     * 发展直属下级的人数
     */
    public Map<String, Object> getLowerCount(Integer skuId, Integer stock, Integer level) {
        Map<String, Object> param = new HashMap();
        Integer countLevel = 0;
        int endLevel = comAgentLevelMapper.getMaxAgentLevel();
        PfSkuAgent pfSkuAgent = pfSkuAgentMapper.selectBySkuIdAndLevelId(skuId, level);
        if (level == endLevel || stock == 0) {
            param.put("countLevel", countLevel);
            param.put("priceDiscount", pfSkuAgent.getUnitPrice());
            param.put("levelStock", pfSkuAgent.getQuantity());
            return param;
        } else {
            countLevel = stock / pfSkuAgent.getQuantity();
            param.put("countLevel", countLevel);
            param.put("levelStock", pfSkuAgent.getQuantity());
        }
        param.put("priceDiscount", pfSkuAgent.getUnitPrice());
        return param;
    }

    /**
     * jjh
     * API 业务数据
     * @param skuId
     * @return
     */
    public Product getSkuHtml(Integer skuId){
        Product product = productMapper.getSkuDetailsBySkuId(skuId);
        Integer currentStock = product.getStock() - product.getFrozenStock();
        product.setStock(currentStock < 0 ? 0 : currentStock);
       return product;
    }
    /**
     * jjh
     * 获取主打商品的合伙等级
     */
    public Map<String,Object> getPrimarySkuAgendlevel(Long userId,Integer skuId){
        Map<String,Object> map = new HashMap<>();
        Integer mainAgentLevelId = 0;// 获取主打商品的合伙等级
        Integer mainSkuId = 0;
        PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userId, skuId);
        if (pfUserSku == null) {
            //获取商品对象
            ComSku comSku = skuService.getSkuById(skuId);
            //获取SPU对象
            ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
            List<ComSku> comSkuList = pfUserBrandService.getPrimarySkuByBrandId(comSpu.getBrandId());
            for (ComSku cs : comSkuList) {
                PfUserSku main_pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userId, cs.getId());
                if (main_pfUserSku != null) {
                    mainAgentLevelId = main_pfUserSku.getAgentLevelId();
                    mainSkuId = main_pfUserSku.getSkuId();
                    break;
                }
            }
            if (mainAgentLevelId.equals(0)) {
                throw new BusinessException("还未代理主打商品");
            }
        }
        map.put("mainAgentLevelId",mainAgentLevelId);
        map.put("mainSkuId",mainSkuId);
        return map;
    }
}
