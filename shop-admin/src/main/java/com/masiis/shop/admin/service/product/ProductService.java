package com.masiis.shop.admin.service.product;

import com.masiis.shop.dao.platform.product.*;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/3/7.
 */
@Service
public class ProductService {

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

    /**
     * 添加商品
     * @param comSpu
     * @param comSku
     * @param pfSkuAgents
     * @param sfSkuDistributions
     */
    public void save(ComSpu comSpu, ComSku comSku, List<ComSkuImage> comSkuImages, List<PfSkuAgent> pfSkuAgents, List<SfSkuDistribution> sfSkuDistributions){

        //保存spu
        comSpuMapper.insert(comSpu);

        //保存sku
        comSku.setSpuId(comSpu.getId());
        comSkuMapper.insert(comSku);

        //保存sku图片
        int i = 0;
        for(ComSkuImage comSkuImage : comSkuImages){
            comSkuImage.setSpuId(comSpu.getId());
            comSkuImage.setSkuId(comSku.getId());
            comSkuImage.setSort(i++);

            comSkuImageMapper.insert(comSkuImage);
        }

        //保存代理分润
        for(PfSkuAgent pfSkuAgent : pfSkuAgents){
            pfSkuAgent.setSkuId(comSku.getId());
            pfSkuAgentMapper.insert(pfSkuAgent);
        }

        //保存分销分润
        for(SfSkuDistribution sfSkuDistribution : sfSkuDistributions){
            sfSkuDistribution.setSkuId(comSku.getId());
            sfSkuDistributionMapper.insert(sfSkuDistribution);
        }
    }
}
