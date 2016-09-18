package com.masiis.shop.admin.service.product;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.product.ProductInfo;
import com.masiis.shop.dao.platform.product.*;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by cai_tb on 16/3/7.
 */
@Service
@Transactional
public class ProductService {

    private final static Log log = LogFactory.getLog(ProductService.class);

    @Resource
    private ComSpuMapper comSpuMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private SfSkuDistributionMapper sfSkuDistributionMapper;
    @Resource
    private ComSkuImageMapper comSkuImageMapper;
    @Resource
    private PfSkuStatisticMapper pfSkuStatisticMapper;
    @Resource
    private PfSkuStockService pfSkuStockService;
    @Resource
    private ComSkuExtensionMapper comSkuExtensionMapper;

    /**
     * 添加商品
     *
     * @param comSpu
     * @param comSku
     */
    public void save(ComSpu comSpu, ComSku comSku, ComSkuExtension comSkuExtension, List<ComSkuImage> comSkuImages) {

        log.info("保存商品start..........................");
        //保存spu
        if(comSpu.getId() == null){
            log.info("保存[comSpu="+comSpu+"]..........................");
            comSpuMapper.insert(comSpu);
        }else{
            log.info("更改[comSpu="+comSpu+"]..........................");
            comSpuMapper.updateById(comSpu);
        }

        //保存sku
        if(comSku.getId() == null){
            comSku.setSpuId(comSpu.getId());

            log.info("保存[comSku="+comSku+"]..........................");
            comSkuMapper.insert(comSku);


            //sku统计表
            PfSkuStatistic pfSkuStatistic = new PfSkuStatistic();
            pfSkuStatistic.setSkuId(comSku.getId());
            pfSkuStatistic.setAgentNum(0);
            pfSkuStatistic.setTrialNum(0);

            log.info("保存[pfSkuStatistic="+pfSkuStatistic+"]..........................");
            pfSkuStatisticMapper.insert(pfSkuStatistic);

            //库存量
            PfSkuStock pfSkuStock = new PfSkuStock();
            pfSkuStock.setCreateTime(new Date());
            pfSkuStock.setSkuId(comSku.getId());
            pfSkuStock.setSpuId(comSpu.getId());
            pfSkuStock.setStock(10);
            pfSkuStock.setFrozenStock(0);
            pfSkuStock.setVersion(0);

            log.info("保存[pfSkuStock="+pfSkuStock+"]..........................");
            pfSkuStockService.insert(pfSkuStock);
        }else{
            log.info("更改[comSku="+comSku+"]..........................");
            comSkuMapper.updateById(comSku);
        }


        //保存sku扩展表
        if(comSkuExtension.getId() == null){
            comSkuExtension.setSkuId(comSku.getId());

            log.info("保存[comSkuExtension="+comSkuExtension+"]..........................");
            comSkuExtensionMapper.insert(comSkuExtension);
        }else{
            log.info("更改[comSkuExtension="+comSkuExtension+"]..........................");
            comSkuExtensionMapper.updateById(comSkuExtension);
        }

        //保存sku图片
        if (comSkuImages != null && comSkuImages.size() > 0) {
            //删除原有的sku图片
            comSkuImageMapper.deleteBySkuId(comSku.getId());
            for (ComSkuImage comSkuImage : comSkuImages) {
                comSkuImage.setSpuId(comSpu.getId());
                comSkuImage.setSkuId(comSku.getId());

                log.info("保存[comSkuImage="+comSkuImage+"]..........................");
                comSkuImageMapper.insert(comSkuImage);
            }
        }
        log.info("保存商品end..........................");

    }

    /**
     * 修改商品
     *
     * @param comSpu
     * @param comSku
     * @param pfSkuAgents
     * @param sfSkuDistributions
     */
    public void update(ComSpu comSpu, ComSku comSku, ComSkuExtension comSkuExtension, List<ComSkuImage> comSkuImages, List<PfSkuAgent> pfSkuAgents, List<SfSkuDistribution> sfSkuDistributions) {

        if (comSpu.getId() != null && comSku.getId() != null) {
            //保存spu
            comSpuMapper.updateById(comSpu);

            //保存sku
            comSkuMapper.updateById(comSku);

            if(comSkuExtension != null){
                comSkuExtensionMapper.updateById(comSkuExtension);
            }

            //保存sku图片
            if (comSkuImages != null && comSkuImages.size() > 0) {
                //删除原有的sku图片
                comSkuImageMapper.deleteBySkuId(comSku.getId());
                for (ComSkuImage comSkuImage : comSkuImages) {
                    comSkuImageMapper.insert(comSkuImage);
                }
            }

            //保存代理分润
            if (pfSkuAgents != null) {
                for (PfSkuAgent pfSkuAgent : pfSkuAgents) {
                    pfSkuAgentMapper.updateById(pfSkuAgent);
                }
            }

            //保存分销分润
            if (sfSkuDistributions != null) {
                for (SfSkuDistribution sfSkuDistribution : sfSkuDistributions) {
                    sfSkuDistributionMapper.updateById(sfSkuDistribution);
                }
            }
        }

    }

    /**
     * 商品上下架
     *
     * @param comSpu
     */
    public void putaway(ComSpu comSpu) {
        if (comSpu.getIsSale() == 1) {
            comSpu.setUpTime(new Date());
        } else if (comSpu.getIsSale() == 0) {
            comSpu.setDownTime(new Date());
        }
        comSpuMapper.updateById(comSpu);
    }

    /**
     * 商品列表
     *
     * @param comSku
     * @return
     */
    public Map<String, Object> list(Integer pageNo, Integer pageSize, ComSku comSku) {
        List<ProductInfo> productInfos = new ArrayList<>();

        PageHelper.startPage(pageNo, pageSize, "create_time desc");
        List<ComSku> comSkus = comSkuMapper.selectByCondition(comSku);

        for (ComSku cs : comSkus) {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setComSku(cs);
            productInfo.setComSpu(comSpuMapper.selectById(cs.getSpuId()));
            productInfo.setPfSkuStock(pfSkuStockService.selectBySkuId(cs.getId()));


            productInfos.add(productInfo);
        }

        PageInfo<ComSku> pageInfo = new PageInfo<>(comSkus);

        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", productInfos);

        return pageMap;
    }

    /**
     * 获取sku
     *
     * @param skuId
     * @return
     */
    public ProductInfo findSku(Integer skuId) {
        ProductInfo productInfo = new ProductInfo();

        ComSku comSku = comSkuMapper.selectById(skuId);
        ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
        ComSkuExtension comSkuExtension = comSkuExtensionMapper.selectBySkuId(comSku.getId());
        List<ComSkuImage> comSkuImages = comSkuImageMapper.selectBySkuId(comSku.getId());
        List<PfSkuAgent> pfSkuAgents = pfSkuAgentMapper.selectBySkuId(comSku.getId());
        List<SfSkuDistribution> sfSkuDistributions = sfSkuDistributionMapper.selectBySkuId(comSku.getId());

        productInfo.setComSku(comSku);
        productInfo.setComSpu(comSpu);
        productInfo.setComSkuExtension(comSkuExtension);
        productInfo.setComSkuImages(comSkuImages);
        productInfo.setPfSkuAgents(pfSkuAgents);
        productInfo.setSfSkuDistributions(sfSkuDistributions);

        return productInfo;
    }


    public Map<String,Object> mainSkuList(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> map) {
        String sort = "brand.cname desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<Map<String, Object>> skuMain = comSpuMapper.selectAll();
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(skuMain);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows",  skuMain);
        return pageMap;
    }

    public List<Map<String, Object>> brandList() {
        return  comSpuMapper.brandList();
    }

    public List<Map<String, Object>> spuList() {
        return comSpuMapper.spuList();
    }


    public boolean updateSpuMain(Integer brandId, Integer spuId, Integer type) {
        return comSpuMapper.updateSpuMaim(brandId, spuId, type);
    }

    public List<Map<String,Object>> selectByBrandId(Integer brandId) {
        return comSpuMapper.selectByBrandId(brandId);
    }

    public void deleteMain(Integer spuId) {
        comSpuMapper.deleteMain(spuId);
    }
}
