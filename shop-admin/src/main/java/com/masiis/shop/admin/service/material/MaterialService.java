package com.masiis.shop.admin.service.material;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.material.MaterialLibrary;
import com.masiis.shop.dao.platform.material.ComSkuMaterialGroupMapper;
import com.masiis.shop.dao.platform.material.ComSkuMaterialLibraryMapper;
import com.masiis.shop.dao.platform.material.ComSkuMaterialMapper;
import com.masiis.shop.dao.po.ComSkuMaterial;
import com.masiis.shop.dao.po.ComSkuMaterialGroup;
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

    public void addMaterial(ComSkuMaterial comSkuMaterial) {

    }

    public void addMaterialGroup(ComSkuMaterialGroup comSkuMaterialGroup) {
        if(comSkuMaterialGroup == null) throw new BusinessException("素材模块数据为空!");
        if(comSkuMaterialGroup.getMaterialLibraryId() == null) {

        }
    }

    /**
     * 获取所有素材库
     * @return
     */
    public List<MaterialLibrary> listAllLibrary() {

        //List<MaterialLibrary> materialLibraries = comSkuMaterialLibraryMapper.selectByCondition(null);
        return null;
    }
}
