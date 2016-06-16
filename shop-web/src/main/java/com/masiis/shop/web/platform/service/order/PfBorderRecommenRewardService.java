package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.dao.beans.order.BOrderAdd;
import com.masiis.shop.dao.platform.order.PfBorderConsigneeMapper;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.order.PfBorderRecommenRewardMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.product.PfUserSkuStockService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.PfUserStatisticsService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * BOrderAddService
 *
 * @author ZhaoLiang
 * @date 2016/4/14
 */
@Service
@Transactional
public class PfBorderRecommenRewardService {

    @Resource
    private PfBorderRecommenRewardMapper pfBorderRecommenRewardMapper;


    public int insert(PfBorderRecommenReward po){
        return pfBorderRecommenRewardMapper.insert(po);
    }

    /**
     * 获得奖励订单
     * @author muchaofeng
     * @date 2016/6/15 15:42
     */

    public Integer findBorders(Long userId)throws Exception{
        return pfBorderRecommenRewardMapper.selectBorders(userId);
    }

    /**
     * 发出奖励订单
     * @author muchaofeng
     * @date 2016/6/15 15:42
     */

    public Integer findPBorders(Long userId)throws Exception{
        return pfBorderRecommenRewardMapper.selectPBorders(userId);
    }
}
