package com.masiis.shop.dao.beans.material;

import com.masiis.shop.dao.po.ComSkuMaterial;

import java.util.List;

/**
 * Created by cai_tb on 16/7/1.
 */
public class Material extends ComSkuMaterial {

    private List<MaterialItem> materialItems;

    public List<MaterialItem> getMaterialItems() {
        return materialItems;
    }

    public void setMaterialItems(List<MaterialItem> materialItems) {
        this.materialItems = materialItems;
    }
}
