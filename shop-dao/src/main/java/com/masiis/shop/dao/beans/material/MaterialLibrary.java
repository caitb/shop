package com.masiis.shop.dao.beans.material;

import com.masiis.shop.dao.po.ComSkuMaterialLibrary;

import java.util.List;

/**
 * 素材库数据bean
 * Created by cai_tb on 16/7/1.
 */
public class MaterialLibrary extends ComSkuMaterialLibrary {

    private List<MaterialGroup> materialGroups;

    public List<MaterialGroup> getMaterialGroups() {
        return materialGroups;
    }

    public void setMaterialGroups(List<MaterialGroup> materialGroups) {
        this.materialGroups = materialGroups;
    }
}
