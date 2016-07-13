package com.masiis.shop.admin.service.promotion;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.enums.promotion.SfGOrderPayStatusEnum;
import com.masiis.shop.common.enums.promotion.SfGorderShipStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.promotion.SfGorderMapper;
import com.masiis.shop.dao.po.SfGorder;
import com.masiis.shop.dao.po.SfGorderFreight;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by hzz on 2016/7/12.
 */
@Service
@Transactional
public class SfGorderService {

    private final static Log log = LogFactory.getLog(SfGorderService.class);

    @Resource
    private SfGorderMapper sfGorderMapper;

    public SfGorder selectByPrimaryKey(Long id){
        return sfGorderMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(SfGorder sfGorder){
        return sfGorderMapper.updateByPrimaryKey(sfGorder);
    }

    /**
     * 发货修改订单的状态
     * @param gorderFreight  运单对象
     */
    public SfGorder deliveryGiftAndUpdate(SfGorderFreight gorderFreight){
        SfGorder sfGorder = selectByPrimaryKey(gorderFreight.getSfGorderId());
        if (sfGorder!=null){
            sfGorder.setGorderStatus(SfGOrderPayStatusEnum.ORDER_SHIPED.getCode());
            sfGorder.setShipStatus(SfGorderShipStatus.Ship.getCode());
            sfGorder.setIsShip(1);//已发货
            sfGorder.setShipManId(gorderFreight.getShipManId());
            sfGorder.setShipManName(gorderFreight.getShipManName());
            sfGorder.setShipTime(new Date());
            sfGorder.setShipType(0);//? 配送类型
            sfGorder.setModifyTime(new Date());
            int i = updateByPrimaryKey(sfGorder);
            if (i!=1){
                log.info("发货修改订单状态失败");
                throw new BusinessException("发货修改订单状态失败");
            }
        }else{
            log.info("发货修改订单状态失败---获取订单为null");
            throw new BusinessException("发货修改订单状态失败---获取订单为null");
        }
        return sfGorder;
    }
}
