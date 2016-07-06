package com.masiis.shop.web.material.service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.dao.platform.material.ComSkuMaterialGroupMapper;
import com.masiis.shop.dao.platform.material.ComSkuMaterialLibraryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

}
