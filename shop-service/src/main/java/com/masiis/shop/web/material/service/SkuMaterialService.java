package com.masiis.shop.web.material.service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.github.pagehelper.PageHelper;
import com.masiis.shop.dao.beans.material.Material;
import com.masiis.shop.dao.platform.material.ComSkuMaterialMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Material> skuMaterial(Integer mgId,Integer currentPage,Integer pageSize) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("mgId",mgId);
        PageHelper.startPage(currentPage, pageSize);
        return comSkuMaterialMapper.selectMaterialItem(map);
    }
}
