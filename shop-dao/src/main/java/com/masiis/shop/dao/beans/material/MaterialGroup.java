package com.masiis.shop.dao.beans.material;

import com.masiis.shop.dao.po.ComSkuMaterialGroup;

import java.util.List;

/**
 * Created by cai_tb on 16/7/1.
 */
public class MaterialGroup extends ComSkuMaterialGroup {

    private List<Material> materials;

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }
}
