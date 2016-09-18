package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.common.enums.platform.SkuStockLogType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.product.PfSkuStockLogMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.po.PfSkuStock;
import com.masiis.shop.dao.po.PfSkuStockLog;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2016/5/13
 * @Auther lzh
 */
@Service
public class PfSkuStockService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfSkuStockMapper skuStockMapper;
    @Resource
    private PfSkuStockLogMapper skuStockLogMapper;

    public void updateSkuStockWithLog(Integer change, PfSkuStock stock,
                                      Long billId, SkuStockLogType handleType) {
        log.info("修改平台库存开始,变动库存为:" + change + ",操作类型为:" + handleType.getCode());

        Integer beforeStock = stock.getStock();

        log.info("变动之前库存为:" + beforeStock);

        // 根据handleType修改库存
        changeStockByHandldType(change, stock, handleType);
        // 获取改变后的库存
        Integer afterStock = stock.getStock();

        log.info("变动之前库存为:" + afterStock);

        if (updateByIdAndVersions(stock) != 1) {
            throw new BusinessException("修改平台库存失败");
        }

        //添加平台库存变动日志
        PfSkuStockLog skuStockLog = createPfSkuStockLog(stock, beforeStock, afterStock, handleType, billId);
        skuStockLogMapper.insert(skuStockLog);

        log.info("修改平台库存成功,变动库存为:" + change + ",操作类型为:" + handleType.getCode());
    }

    public PfSkuStockLog createPfSkuStockLog(PfSkuStock stock, Integer beforeStock, Integer afterStock, SkuStockLogType handleType, Long billId) {
        PfSkuStockLog res = new PfSkuStockLog();

        res.setBillId(billId);
        res.setCreateTime(new Date());
        res.setPfSkuStockId(Long.valueOf(stock.getId()));
        res.setPrevStock(beforeStock);
        res.setNextStock(afterStock);
        res.setSkuId(stock.getSkuId());
        res.setSpuId(stock.getSpuId());
        res.setType(handleType.getCode());
        res.setRemark(handleType.getDesc());

        return res;
    }

    private void changeStockByHandldType(Integer change, PfSkuStock stock, SkuStockLogType handleType) {
        Integer afterStock = stock.getStock();
        Integer afterFrozeStock = stock.getFrozenStock();

        switch (handleType) {
            case downAgent:
            case registerGiveSku:
                afterStock -= change;
                afterFrozeStock -= change;
                break;
            case recoveryGiveSku:
                afterStock += change;
                afterFrozeStock += change;
                break;
            default:
                throw new BusinessException("该操作类型不支持");
        }

        stock.setStock(afterStock);
        stock.setFrozenStock(afterFrozeStock);

        if (stock.getStock().intValue() < 0) {
            throw new BusinessException("库存变动后小于0,错误");
        }
        if (stock.getFrozenStock().intValue() < 0) {
            throw new BusinessException("冻结库存变动后小于0,错误");
        }
    }

    /**
     * 判断平台商品是否有足够的库存
     * @param skuQuantity
     * @param spuId
     * @param skuId
     * @return
     */
    public Map<String,Object> isEnoughPlatformSku(Integer skuQuantity, Integer spuId, Integer skuId){
        //查询平台库存
        PfSkuStock pfSkuStock = skuStockMapper.selectBySkuId(skuId);
        Map<String,Object> map = new LinkedHashMap<>();
        if (pfSkuStock!=null){
            if (pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < skuQuantity) {
                map.put("isEnoughPlatformSku",false);
            }
        }else{
            log.info("------没有查到此商品的库存-------");
            map.put("isEnoughPlatformSku",false);
        }
        map.put("pfSkuStock",pfSkuStock);
        map.put("isEnoughPlatformSku",true);
        return map;
    }

    public void updateFrozenStock(Integer changeQuantity,String remark,PfSkuStock pfSkuStock){
        if (pfSkuStock!=null){
            //增加平台冻结库存
            pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() + changeQuantity);
            if (updateByIdAndVersions(pfSkuStock) != 1) {
                throw new BusinessException(remark);
            }
        }else{
            throw new BusinessException("----更新平台库存失败-----");
        }
    }


    public PfSkuStock selectById(Integer id) {
        return skuStockMapper.selectById(id);
    }

    public List<PfSkuStock> selectByCondition(PfSkuStock pfSkuStock) {
        return skuStockMapper.selectByCondition(pfSkuStock);
    }

    public List<PfSkuStock> selectAll() {
        return skuStockMapper.selectAll();
    }

    public void insert(PfSkuStock pfSkuStock) {
        skuStockMapper.insert(pfSkuStock);
    }

    public void deleteById(Integer id) {
        skuStockMapper.deleteById(id);
    }

    public PfSkuStock selectBySkuId(Integer skuId) {
        return skuStockMapper.selectBySkuId(skuId);
    }

    public int updateByIdAndVersions(PfSkuStock pfSkuStock) {
        return skuStockMapper.updateByIdAndVersion(pfSkuStock);
    }

    public void updateById(PfSkuStock pfSkuStock) {
        skuStockMapper.updateById(pfSkuStock);
    }
}
