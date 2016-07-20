package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.beans.product.ProductSimple;
import com.masiis.shop.dao.platform.product.*;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.common.constant.platform.SysConstants;
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
    /**
     * @Author 贾晶豪
     * @Date 2016/3/5 0005 下午 2:30
     * 根据商品ID展示商品属性详情
     */
    public Product getSkuDetails(Integer skuId) throws Exception {
        Product product = productMapper.getSkuDetailsBySkuId(skuId);
        product.setStock(product.getStock() - product.getFrozenStock());
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
            ComSku comSku = comSkuMapper.selectById(skuId);
            if (comSku != null) {
                product.setName(comSku.getName());
                //获取运费
                ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
                if (comSpu != null) {
                    //获取默认图片
                    ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(skuId);
                    List<ComSkuImage> comSkuImages = new ArrayList<ComSkuImage>();
                    comSkuImages.add(comSkuImage);
                    product.setShipAmount(comSpu.getShipAmount());
                    product.setComSkuImages(comSkuImages);
                }
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

    /**
     * @Author 贾晶豪
     * @Date 2016/3/16 0016 下午 7:38
     * 更新库存
     */
    public void updateStock(Integer selfStock, Integer skuId,Long userId) throws Exception {
        PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(userId,skuId);
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
}