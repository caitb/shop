package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.common.enums.product.UserSkuStockLogType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.product.PfUserSkuStockLogMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.PfUserSkuStock;
import com.masiis.shop.dao.po.PfUserSkuStockLog;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Date 2016/5/13
 * @Auther lzh
 */
@Service
public class PfUserSkuStockService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfUserSkuStockMapper userSkuStockMapper;
    @Resource
    private PfUserSkuStockLogMapper userSkuStockLogMapper;

    public void updateUserSkuStockWithLog(Integer change, PfUserSkuStock stock, Long billId, UserSkuStockLogType handleType){
        Integer beforeStock = stock.getStock();
        // 改变stock库存
        getAfterStockByType(change, stock, handleType);
        // 获取改变后的库存
        Integer afterStock = stock.getStock();

        if(userSkuStockMapper.updateByIdAndVersion(stock) != 1) {
            throw new BusinessException("修改用户库存失败");
        }

        PfUserSkuStockLog stockLog = createPfUserSkuStockLog(stock, beforeStock, afterStock, handleType, billId);
        userSkuStockLogMapper.insert(stockLog);
    }

    private PfUserSkuStockLog createPfUserSkuStockLog(PfUserSkuStock stock, Integer beforeStock, Integer afterStock, UserSkuStockLogType handleType, Long billId) {
        PfUserSkuStockLog res = new PfUserSkuStockLog();

        res.setBillId(billId);
        res.setCreateTime(new Date());
        res.setPfUserSkuStockId(stock.getId());
        res.setPrevStock(beforeStock);
        res.setNextStock(afterStock);
        res.setSkuId(stock.getSkuId());
        res.setSpuId(stock.getSpuId());
        res.setUserId(stock.getUserId());
        res.setType(handleType.getCode());
        res.setRemark(handleType.getDesc());

        return res;
    }

    /**
     * 根据操作类型和变化值来计算操作后的库存
     *
     * @param change
     * @param before
     * @param handleType
     */
    private void getAfterStockByType(Integer change, PfUserSkuStock before, UserSkuStockLogType handleType) {
        Integer afterStock = before.getStock();
        Integer fronzeStock = before.getFrozenStock();

        switch (handleType) {
            case agent:
                afterStock += change;
                break;
            case downAgent:
                afterStock -= change;
                fronzeStock -= change;
                break;
            case Supplement:
                afterStock += change;
                break;
            case Take:
                afterStock -= change;
                fronzeStock -= change;
                break;
            case shopOrder:
                afterStock -= change;
                fronzeStock -= change;
                break;
            case shopReturn:
                afterStock += change;
                break;
            default:
                throw new BusinessException();
        }

        before.setStock(afterStock);
        before.setFrozenStock(fronzeStock);

        if(before.getStock().intValue() < 0){
            throw new BusinessException("库存变动后小于0,错误");
        }
        if(before.getFrozenStock().intValue() < 0){
            throw new BusinessException("冻结库存变动后小于0,错误");
        }
    }
}
