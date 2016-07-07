package com.masiis.shop.admin.service.material;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.beans.material.Material;
import com.masiis.shop.dao.beans.material.MaterialLibrary;
import com.masiis.shop.dao.platform.material.ComSkuMaterialGroupMapper;
import com.masiis.shop.dao.platform.material.ComSkuMaterialItemMapper;
import com.masiis.shop.dao.platform.material.ComSkuMaterialLibraryMapper;
import com.masiis.shop.dao.platform.material.ComSkuMaterialMapper;
import com.masiis.shop.dao.po.ComSkuMaterial;
import com.masiis.shop.dao.po.ComSkuMaterialGroup;
import com.masiis.shop.dao.po.ComSkuMaterialItem;
import com.masiis.shop.dao.po.ComSkuMaterialLibrary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 素材业务
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
    @Resource
    private ComSkuMaterialItemMapper comSkuMaterialItemMapper;

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
     * 更改素材库
     * @param comSkuMaterialLibrary
     */
    public void updateMaterialLibrary(ComSkuMaterialLibrary comSkuMaterialLibrary) {
        comSkuMaterialLibraryMapper.updateByPrimaryKey(comSkuMaterialLibrary);
    }

    /**
     * 更改素材组
     * @param comSkuMaterialGroup
     */
    public void updateMaterialGroup(ComSkuMaterialGroup comSkuMaterialGroup) {
        comSkuMaterialGroupMapper.updateByPrimaryKey(comSkuMaterialGroup);
    }

    /**
     * 保存素材组
     * @param comSkuMaterialGroup
     */
    public void saveMaterialGroup(ComSkuMaterialGroup comSkuMaterialGroup) {
        comSkuMaterialGroupMapper.insert(comSkuMaterialGroup);
    }

    /**
     * 保存素材
     * @param comSkuMaterial
     * @param comSkuMaterialItems
     */
    public void saveMaterial(ComSkuMaterial comSkuMaterial, List<ComSkuMaterialItem> comSkuMaterialItems) {
        comSkuMaterialMapper.insert(comSkuMaterial);
        for(ComSkuMaterialItem comSkuMaterialItem : comSkuMaterialItems){
            comSkuMaterialItem.setMaterialId(comSkuMaterial.getId());
            comSkuMaterialItemMapper.insert(comSkuMaterialItem);
        }
    }

    /**
     * 获取所有素材库(包含素材组)
     * @return
     */
    public List<MaterialLibrary> listAllLibrary() {

        List<MaterialLibrary> materialLibraries = comSkuMaterialGroupMapper.selectMaterialLibrary(null);
        return materialLibraries;
    }

    /**
     * 条件分页查询素材库
     * @param pageNumber
     * @param pageSize
     * @param sortName
     * @param sortOrder
     * @param conditionMap
     * @return
     */
    public Map<String, Object> listLibraryByCondition(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap) {
        String sort = "ml.create_time desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<MaterialLibrary> materialLibraries = comSkuMaterialGroupMapper.selectMaterialLibrary(conditionMap);
        PageInfo<MaterialLibrary> pageInfo = new PageInfo<>(materialLibraries);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", materialLibraries);

        return pageMap;
    }

    /**
     * 条件分页查询素材库模块
     * @param pageNumber
     * @param pageSize
     * @param sortName
     * @param sortOrder
     * @param conditionMap
     * @return
     */
    public Map<String, Object> listGroupByCondition(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap) {
        String sort = "create_time desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<ComSkuMaterialGroup> comSkuMaterialGroups = comSkuMaterialGroupMapper.selectMaterialGroup(conditionMap);
        PageInfo<ComSkuMaterialGroup> pageInfo = new PageInfo<>(comSkuMaterialGroups);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", comSkuMaterialGroups);

        return pageMap;
    }

    /**
     * 素材列表
     * @param conditionMap
     * @return
     */
    public Map<String, Object> listMaterialItem(Map<String, Object> conditionMap) {
        List<Material> materials = comSkuMaterialMapper.selectMaterialItem(conditionMap);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", materials.size());
        pageMap.put("rows", materials);

        return pageMap;
    }
}
