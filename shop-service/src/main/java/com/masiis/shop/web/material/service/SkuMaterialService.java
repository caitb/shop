package com.masiis.shop.web.material.service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.github.pagehelper.PageHelper;
import com.masiis.shop.dao.beans.material.SkuMaterial;
import com.masiis.shop.dao.platform.material.ComSkuMaterialMapper;
import com.masiis.shop.dao.po.ComSkuMaterial;
import com.masiis.shop.dao.po.ComSkuMaterialItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiajinghao on 2016/7/7.
 * 素材service
 */
@Service
@Transactional
public class SkuMaterialService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private ComSkuMaterialMapper comSkuMaterialMapper;


    /**
     * jjh
     * 分页展示素材图片
     * @param mgId
     * @param currentPage
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<SkuMaterial> skuMaterial(Integer mgId,Integer currentPage,Integer pageSize) throws Exception{
        PageHelper.startPage(currentPage, pageSize);
        List<ComSkuMaterial> comSkuMaterials = comSkuMaterialMapper.selectByMglId(mgId);
        List<SkuMaterial> materialList = new ArrayList<>();
        for (ComSkuMaterial comSkuMaterial :comSkuMaterials){
            SkuMaterial skuMaterial = new SkuMaterial();
            List<ComSkuMaterialItem> materialItemList = comSkuMaterialMapper.selectMaterialItemByMtId(comSkuMaterial.getId());
            skuMaterial.setComSkuMaterialItems(materialItemList);
            skuMaterial.setCreateTime(comSkuMaterial.getCreateTime());
            skuMaterial.setContent(comSkuMaterial.getContent());
            skuMaterial.setTitle(comSkuMaterial.getTitle());
            skuMaterial.setSort(comSkuMaterial.getSort());
            skuMaterial.setId(comSkuMaterial.getId());
            materialList.add(skuMaterial);
        }
        return materialList;
    }
}
