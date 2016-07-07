package com.masiis.shop.web.material.service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.material.MaterialLibrary;
import com.masiis.shop.dao.platform.material.ComSkuMaterialGroupMapper;
import com.masiis.shop.dao.platform.material.ComSkuMaterialLibraryMapper;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSkuImage;
import com.masiis.shop.dao.po.ComSkuMaterialLibrary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiajinghao on 2016/7/6.
 * 素材库列表service
 */

@Service
@Transactional
public class MaterialLibraryService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private ComSkuMaterialLibraryMapper comSkuMaterialLibraryMapper;

    @Resource
    private ComSkuMaterialGroupMapper comSkuMaterialGroupMapper;

    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComSkuImageMapper comSkuImageMapper;
    /**
     * 获取商品素材库
     * jjh
     * @return
     */
    public List<MaterialLibrary> SkuMaterialLibraryList(int currentPage,int pageSize) throws Exception{
        List<MaterialLibrary> materialLibraryList = new ArrayList<>();
        PageHelper.startPage(currentPage, pageSize);
        List<ComSkuMaterialLibrary> comSkuMaterialLibraryList = comSkuMaterialLibraryMapper.selectAll();
        String Value = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        for (ComSkuMaterialLibrary comSkuMaterialLibrary :comSkuMaterialLibraryList){
            MaterialLibrary materialLibrary = new MaterialLibrary();
            ComSku comSku =comSkuMapper.selectByPrimaryKey(comSkuMaterialLibrary.getSkuId());
            ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(comSkuMaterialLibrary.getSkuId());
            materialLibrary.setComSku(comSku);
            materialLibrary.setId(comSkuMaterialLibrary.getId());
            materialLibrary.setCreateTime(comSkuMaterialLibrary.getCreateTime());
            materialLibrary.setRemark(Value+comSkuImage.getImgUrl());
            materialLibrary.setName(comSkuMaterialLibrary.getName());
            materialLibraryList.add(materialLibrary);
        }
        return materialLibraryList;
    }

}
