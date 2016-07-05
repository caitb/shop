package com.masiis.shop.admin.service.material;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.material.MaterialLibrary;
import com.masiis.shop.dao.platform.material.ComSkuMaterialGroupMapper;
import com.masiis.shop.dao.platform.material.ComSkuMaterialLibraryMapper;
import com.masiis.shop.dao.platform.material.ComSkuMaterialMapper;
import com.masiis.shop.dao.po.ComSkuMaterial;
import com.masiis.shop.dao.po.ComSkuMaterialGroup;
import com.masiis.shop.dao.po.ComSkuMaterialLibrary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/7/1.
 */
@Service
@Transactional
public class MaterialService {

    @Resource
    private ComSkuMaterialMapper comSkuMaterialMapper;
    @Resource
    private ComSkuMaterialGroupMapper comSkuMaterialGroupMapper;
    @Resource
    private ComSkuMaterialLibraryMapper comSkuMaterialLibraryMapper;

    /**
     * 加载所有素材库
     * @return
     */
    public List<ComSkuMaterialLibrary> loadAll() {
        return comSkuMaterialLibraryMapper.selectAll();
    }

    /**
     * 保存素材库
     * @param comSkuMaterialLibrary
     */
    public void saveMaterialLibrary(ComSkuMaterialLibrary comSkuMaterialLibrary) {
        comSkuMaterialLibraryMapper.insert(comSkuMaterialLibrary);
    }

    /**
     * 保存素材组
     * @param comSkuMaterialGroup
     */
    public void saveMaterialGroup(ComSkuMaterialGroup comSkuMaterialGroup) {
        comSkuMaterialGroupMapper.insert(comSkuMaterialGroup);
    }

    /**
     * 获取所有素材库(包含素材组)
     * @return
     */
    public List<MaterialLibrary> listAllLibrary() {

        List<MaterialLibrary> materialLibraries = comSkuMaterialGroupMapper.selectMaterialLibrary();
        return materialLibraries;
    }
}
