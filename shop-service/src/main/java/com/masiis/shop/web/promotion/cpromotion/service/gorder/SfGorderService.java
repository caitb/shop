package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.masiis.shop.common.enums.mall.SfGOrderPayStatusEnum;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.dao.mall.promotion.SfGorderMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfGorder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * C端用户奖励订单表
 */
@Service
public class SfGorderService {

    @Resource
    private SfGorderMapper sfGorderMapper;



    public void addGorder(ComUser comUser, Integer promoId, Integer promoRuleId, Integer personType){
        SfGorder sfGorder = new SfGorder();
        sfGorder.setCreateTime(new Date());
        sfGorder.setCreateMan(comUser.getId());
        sfGorder.setPromoId(promoId);
        sfGorder.setPromoRuleId(promoRuleId);
        sfGorder.setUserId(comUser.getId());
        sfGorder.setGorderCode(OrderMakeUtils.makeOrder("G"));
        sfGorder.setGorderType(personType);//订单类型
        sfGorder.setGorderAmount(BigDecimal.ZERO);//订单金额
        sfGorder.setProductAmount(BigDecimal.ZERO);//奖品金额
        sfGorder.setShipAmount(BigDecimal.ZERO);//运费
        sfGorder.setReceivableAmount(BigDecimal.ZERO);//应收费用
        sfGorder.setPayAmount(BigDecimal.ZERO);//已付金额
        sfGorder.setPayTime(new Date());//付款时间
        sfGorder.setPayStatus(SfGOrderPayStatusEnum.ORDER_PAID.getCode());//支付状态
        sfGorder.setIsShip(0);
        sfGorder.setIsReceipt(0);
        sfGorder.setRemark("领取奖励插入订单");
    }
}
