package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.enums.mall.SfGOrderPayStatusEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.dao.mall.promotion.SfGorderMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfGorder;
import com.masiis.shop.dao.po.SfUserPromotion;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserPromotionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * C端用户奖励订单表
 */
@Service
@Transactional
public class SfGorderService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private SfUserPromotionService promotionService;

    @Resource
    private SfGorderMapper sfGorderMapper;



    public SfGorder addGorder(ComUser comUser, Integer promoId, Integer promoRuleId){
        SfGorder sfGorder = new SfGorder();
        SfUserPromotion promotion = promotionService.selectByPrimaryKey(promoId);
        sfGorder.setCreateTime(new Date());
        sfGorder.setCreateMan(comUser.getId());
        sfGorder.setPromoId(promoId);
        sfGorder.setPromoRuleId(promoRuleId);
        sfGorder.setUserId(comUser.getId());
        sfGorder.setGorderStatus(SfGOrderPayStatusEnum.ORDER_PAID.getCode());//订单状态
        sfGorder.setGorderCode(OrderMakeUtils.makeOrder("G"));
        if (promotion!=null){
            sfGorder.setGorderType(promotion.getPersonType());//订单类型(粉丝活动或者代言人活动)
        }
        sfGorder.setGorderAmount(BigDecimal.ZERO);//订单金额
        sfGorder.setProductAmount(BigDecimal.ZERO);//奖品金额
        sfGorder.setShipAmount(BigDecimal.ZERO);//运费
        sfGorder.setShipStatus(0);
        sfGorder.setReceivableAmount(BigDecimal.ZERO);//应收费用
        sfGorder.setPayAmount(BigDecimal.ZERO);//已付金额
        sfGorder.setPayTime(new Date());//付款时间
        sfGorder.setPayStatus(SfGOrderPayStatusEnum.ORDER_PAID.getCode());//支付状态
        sfGorder.setIsShip(0);
        sfGorder.setIsReceipt(0);
        sfGorder.setRemark("领取奖励插入订单");
        int  i = sfGorderMapper.insert(sfGorder);
        if (i==1){
            return sfGorder;
        }else{
            log.info("领取奖励插入订单失败");
            throw new BusinessException("领取奖励插入订单失败");
        }
    }
}
