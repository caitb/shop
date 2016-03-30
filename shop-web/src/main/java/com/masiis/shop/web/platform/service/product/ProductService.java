package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.beans.product.ProductSimple;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.product.ProductMapper;
import com.masiis.shop.dao.platform.product.ProductSimpleMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.ComSkuImage;
import com.masiis.shop.dao.po.ComSpu;
import com.masiis.shop.dao.po.PfUserSkuStock;
import com.masiis.shop.web.platform.constants.SysConstants;
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
    private ComSkuImageMapper comSkuImageMapper;
    @Resource
    private ProductSimpleMapper productSimpleMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    /**
     * @Author 贾晶豪
     * @Date 2016/3/5 0005 下午 2:30
     * 根据商品ID展示商品属性详情
     */
    public Product getSkuDetails(String skuId) throws Exception {
        Product product = productMapper.getSkuDetailsBySkuId(skuId);
        if (product != null && product.getName().length() > 40) {
            product.setName(product.getName().substring(0, 41) + "......");
        }
        if (product != null && product.getSlogan() != null && product.getSlogan().length() > 50) {

            product.setSlogan(product.getSlogan().substring(0, 51) + "......");
        }
        List<ComSkuImage> skuImgList = productMapper.getSkuImgById(skuId);
        String productImgValue = PropertiesUtils.getStringValue("index_product_308_308_url");
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
    public Integer getMaxDiscount() throws Exception {
        Integer MaxDiscount = productMapper.maxDiscount() * 100;
        return MaxDiscount;
    }
    /**
     * 跳转到试用申请页
     *
     * @author hanzengzhi
     * @date 2016/3/5 16:19
     */
    public Product applyTrialToPageService(Integer skuId) {
        Product product = null;
        try {
            product = getSkuDetails(skuId.toString());
            if (product != null) {
                //获取运费
                ComSpu comSpu = comSpuMapper.selectById(product.getSpuId());
                if (comSpu != null) {
                    //获取默认图片
                    ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(skuId);
                    List<ComSkuImage> comSkuImages = new ArrayList<ComSkuImage>();
                    comSkuImages.add(comSkuImage);
                    product.setShipAmount(comSpu.getShipAmount());
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
                ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(product.getId());
                product.setComSkuImage(comSkuImage);
                product.getComSkuImage().setFullImgUrl(productImgValue + comSkuImage.getImgUrl());
            }
        }
        return userProducts;
    }

    /**
     * @Author 贾晶豪
     * @Date 2016/3/16 0016 下午 7:38
     * 更新库存
     */
    public void updateStock(Integer stock, Integer id) throws Exception {
        Map<String, Object> param = new HashMap<>();
        Product product = productMapper.getProductStock(id);
        if (product != null && (product.getStock() - stock >= 0)) {
            param.put("stock", product.getStock() - stock);
            param.put("id", id);
            productMapper.updateStock(param);
        }
    }
    /**
      * @Author 贾晶豪
      * @Date 2016/3/21 0021 上午 10:13
      * 查看库存
      */
    public PfUserSkuStock getStockByUser(Long id) throws Exception {
        return pfUserSkuStockMapper.selectByPrimaryKey(id);
    }
}
