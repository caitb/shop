package com.masiis.shop.dao.beans.material;

import com.masiis.shop.dao.po.ComSkuMaterial;
import com.masiis.shop.dao.po.ComSkuMaterialItem;

import java.util.List;

/**
 * Created by jjh on 16/7/1.
 */
public class SkuMaterial extends ComSkuMaterial {

    private List<ComSkuMaterialItem> comSkuMaterialItems;

    public List<ComSkuMaterialItem> getComSkuMaterialItems() {
        return comSkuMaterialItems;
    }

    public void setComSkuMaterialItems(List<ComSkuMaterialItem> comSkuMaterialItems) {
        this.comSkuMaterialItems = comSkuMaterialItems;
    }
}
