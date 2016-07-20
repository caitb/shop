package com.masiis.shop.web.material.service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.material.MaterialLibrary;
import com.masiis.shop.dao.platform.material.ComSkuMaterialGroupMapper;
import com.masiis.shop.dao.platform.material.ComSkuMaterialLibraryMapper;
import com.masiis.shop.dao.platform.material.ComUserSubscriptionMapper;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.user.PfUserSkuService;
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
    @Resource
    private ComUserSubscriptionMapper comUserSubscriptionMapper;
    @Resource
    private PfUserSkuService pfUserSkuService;
    /**
     * 获取商品素材库
     * jjh
     * @return
     */
    public List<MaterialLibrary> SkuMaterialLibraryList(int currentPage,int pageSize,Long UserId) throws Exception{
        List<MaterialLibrary> materialLibraryList = new ArrayList<>();
        //check 是否代理
        List<PfUserSku> pfUserSkuList = pfUserSkuService.getPfUserSkuInfoByUserId(UserId);
        List<ComSkuMaterialLibrary> comSkuMaterialLibraryList = null;
        if(pfUserSkuList==null || pfUserSkuList.size()<=0){ //未代理用户
             PageHelper.startPage(currentPage, pageSize,false);
             comSkuMaterialLibraryList = comSkuMaterialLibraryMapper.selectAll();
        }else {
             PageHelper.startPage(currentPage, pageSize,false);
             comSkuMaterialLibraryList = comSkuMaterialLibraryMapper.selectAllByPfUserSkuAgent(UserId);
        }
        String Value = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        for (ComSkuMaterialLibrary comSkuMaterialLibrary :comSkuMaterialLibraryList){
            MaterialLibrary materialLibrary = new MaterialLibrary();
            ComSku comSku =comSkuMapper.selectByPrimaryKey(comSkuMaterialLibrary.getSkuId());
            ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(comSkuMaterialLibrary.getSkuId());
            ComUserSubscription comUserSubscription = comUserSubscriptionMapper.selectByUserIdAndMaterialId(UserId,comSkuMaterialLibrary.getId());
            materialLibrary.setComSku(comSku);
            materialLibrary.setId(comSkuMaterialLibrary.getId());
            materialLibrary.setCreateTime(comSkuMaterialLibrary.getCreateTime());
            materialLibrary.setRemark(Value + comSkuImage.getImgUrl());
            materialLibrary.setName(comSkuMaterialLibrary.getName());
            materialLibrary.setSubscriptionNum(comSkuMaterialLibrary.getSubscriptionNum());
            if(comUserSubscription!=null){
                materialLibrary.setIsSubscript(comUserSubscription.getStatus());
            }else{
                materialLibrary.setIsSubscript(0);
            }
            materialLibraryList.add(materialLibrary);
        }
        return materialLibraryList;
    }

    public List<ComSkuMaterialGroup> MaterialLibraryGroup(Integer materialId,Integer isBShow,Integer isCShow){
         List<ComSkuMaterialGroup> comSkuMaterialGroupList = comSkuMaterialGroupMapper.selectMaterialGroupByMlId(materialId, isBShow, isCShow);
        String Value = PropertiesUtils.getStringValue("oss.OSS_MATERIAL");
         for (ComSkuMaterialGroup comSkuMaterialGroup :comSkuMaterialGroupList){
             comSkuMaterialGroup.setIcon(Value + comSkuMaterialGroup.getIcon());
         }
        return comSkuMaterialGroupList;
    }


    public ComSkuMaterialLibrary getComSkuMaterialLibraryByPrimaryId(Integer mlId){
        return comSkuMaterialLibraryMapper.selectByPrimaryKey(mlId);
    }

    public int CountSkuMaterialLibrary(Long userId){
       return comSkuMaterialLibraryMapper.countLibrary(userId);
    }
}
