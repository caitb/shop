package com.masiis.shop.admin.service.order;

import com.masiis.shop.admin.service.product.PfSkuStockService;
import com.masiis.shop.admin.service.product.PfUserSkuStockService;
import com.masiis.shop.admin.service.product.SkuService;
import com.masiis.shop.common.enums.platform.*;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.platform.order.PfBorderPromotionMapper;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hzz on 2016/9/6.
 */
@Service
@Transactional
public class PfBorderPromotionService {

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfBorderPromotionMapper pfBorderPromotionMapper;

    @Resource
    private PfSkuStockService pfSkuStockService;

    @Resource
    private PfUserSkuStockService pfUserSkuStockService;


    public int update(PfBorderPromotion pfBorderPromotion) {
        return pfBorderPromotionMapper.updateByPrimaryKey(pfBorderPromotion);
    }

    public void doPromotion(PfBorder pfBorder) {
        log.info(" 更新平台赠送给小白的商品库存----start");
        try {
            List<PfBorderPromotion> pfBorderPromotions = pfBorderPromotionMapper.selectByPfBorderId(pfBorder.getId());
            if (pfBorderPromotions != null && pfBorderPromotions.size() > 0) {
                for (PfBorderPromotion pfBorderPromotion : pfBorderPromotions) {
                    if (pfBorderPromotion.getQuantity() > 0 && pfBorderPromotion.getIsSend().intValue() == 0) {
                        log.info("---------------------减少平台库存--------------------");
                        //增加平台冻结库存
                        PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(pfBorderPromotion.getSkuId());
                        if (pfSkuStock.getStock() < pfBorderPromotion.getQuantity()) {
                            throw new BusinessException("库存不足，操作失败");
                        }
                        if (pfSkuStock.getFrozenStock() < pfBorderPromotion.getQuantity()) {
                            throw new BusinessException("库存冻结不足，操作失败");
                        }
                        pfSkuStockService.updateFrozenStock(pfBorderPromotion.getQuantity(), "小白注册赠送商品，下单时增加平台冻结库存",pfSkuStock);
                        //减少平台库存
                        pfSkuStock = pfSkuStockService.selectBySkuId(pfBorderPromotion.getSkuId());
                        pfSkuStockService.updateSkuStockWithLog(pfBorderPromotion.getQuantity(), pfSkuStock, pfBorder.getId(), SkuStockLogType.registerGiveSku);
                        log.info("---------------------增加代理用户库存--------------------");
                        PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfBorder.getUserId(), pfBorderPromotion.getSkuId());
                        if (pfUserSkuStock==null){
                            pfUserSkuStockService.initPfUserSkuStock(pfBorder.getUserId(),pfBorderPromotion.getSkuId(),pfBorderPromotion.getSpuId());
                        }
                        pfUserSkuStockService.updateUserSkuStockWithLog(pfBorderPromotion.getQuantity(), pfUserSkuStock, pfBorder.getId(), UserSkuStockLogType.PROMOTION_ADD);
                        //修改发送状态
                        updateIsSend(pfBorderPromotion);
                    }
                }
            }
        }catch (Exception e){
            throw new BusinessException("更新平台赠送给小白的商品库存失败-----"+e.getMessage());
        }
        log.info(" 更新平台赠送给小白的商品库存----end");
    }

    /**
     * 更新订单促销活动赠品表中是否已发放商品
     *
     * @param pfBorderPromotion
     */
    private void updateIsSend(PfBorderPromotion pfBorderPromotion) {
        if (pfBorderPromotion == null) {
            throw new BusinessException("------数据异常!!!------");
//            pfBorderPromotion = getBorderPromotionsByBorderIdAndIsSend(pfBorderId, PfBorderPromotionIsSendEnum.NO_GiVE.getCode());
        }
        pfBorderPromotion.setIsSend(PfBorderPromotionIsSendEnum.GiVED.getCode());
        int i = update(pfBorderPromotion);
        if (i != 1) {
            throw new BusinessException("------更新订单促销活动赠品表中是否已发放商品失败------");
        }
    }
}
