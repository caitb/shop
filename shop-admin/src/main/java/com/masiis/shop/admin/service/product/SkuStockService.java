package com.masiis.shop.admin.service.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.product.ProductInfo;
import com.masiis.shop.common.enums.BOrder.OperationType;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.system.PbOperationLogMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.*;

/**
 * Created by cai_tb on 16/3/12.
 */
@Service
@Transactional
public class SkuStockService {

    @Resource
    private PfSkuStockService pfSkuStockService;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComSpuMapper comSpuMapper;
    @Resource
    private PbOperationLogMapper pbOperationLogMapper;

    /**
     * 根据条件查询记录
     *
     * @param pageNo
     * @param pageSize
     * @param pfSkuStock
     * @return
     */
    public Map<String, Object> listByCondition(Integer pageNo, Integer pageSize, PfSkuStock pfSkuStock) {
        PageHelper.startPage(pageNo, pageSize, "create_time desc");
        List<PfSkuStock> pfSkuStocks = pfSkuStockService.selectByCondition(pfSkuStock);
        PageInfo<PfSkuStock> pageInfo = new PageInfo<>(pfSkuStocks);

        List<ProductInfo> productInfos = new ArrayList<>();
        for (PfSkuStock pss : pfSkuStocks) {
            ComSku comSku = comSkuMapper.selectById(pss.getSkuId());
            ComSpu comSpu = comSpuMapper.selectById(pss.getSpuId());

            ProductInfo productInfo = new ProductInfo();
            productInfo.setPfSkuStock(pss);
            productInfo.setComSku(comSku);
            productInfo.setComSpu(comSpu);

            productInfos.add(productInfo);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", productInfos);

        return pageMap;
    }

    /**
     * 更新库存
     *
     * @param pfSkuStock
     */
    public void update(PfSkuStock pfSkuStock,PbUser pbUser) throws Exception{
        if (pfSkuStock.getStock() != null && pfSkuStock.getStock().intValue() < 0) {
            pfSkuStock.setStock(0);
        }
        pfSkuStockService.updateByIdAndVersions(pfSkuStock);

        PbOperationLog pbOperationLog = new PbOperationLog();
        pbOperationLog.setOperateIp(InetAddress.getLocalHost().getHostAddress());
        pbOperationLog.setCreateTime(new Date());
        pbOperationLog.setPbUserId(pbUser.getId());
        pbOperationLog.setPbUserName(pbUser.getUserName());
        pbOperationLog.setOperateType(OperationType.Update.getCode());
        pbOperationLog.setRemark("更新库存");
        pbOperationLog.setOperateContent(pfSkuStock.toString());
        int updateByPrimaryKey = pbOperationLogMapper.insert(pbOperationLog);
        if(updateByPrimaryKey==0){
            throw new Exception("新建更新库存日志失败!");
        }
    }
}
