package com.masiis.shop.admin.service.promotion;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.common.enums.promotion.SfGOrderPayStatusEnum;
import com.masiis.shop.common.enums.promotion.SfGorderShipStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.promotion.SfGorderConsigneeMapper;
import com.masiis.shop.dao.mall.promotion.SfGorderItemMapper;
import com.masiis.shop.dao.mall.promotion.SfGorderMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by hzz on 2016/7/12.
 */
@Service
@Transactional
public class SfGorderService {

    private final static Log log = LogFactory.getLog(SfGorderService.class);

    @Resource
    private SfGorderMapper sfGorderMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private SfGorderConsigneeMapper consigneeMapper;
    @Resource
    private SfGorderItemMapper sfGorderItemMapper;

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

    public Object  listByCondition(Integer pageNumber, Integer pageSize, Map<String,Object> condition) {
        PageHelper.startPage(pageNumber, pageSize, "create_time desc");
        List<SfGorder> gorderList = sfGorderMapper.selectByCondition(condition);
        PageInfo<SfGorder> pageInfo = new PageInfo<>(gorderList);

        List<Map<String,Object>> gorderWraps = new LinkedList<>();
        for(SfGorder gorder : gorderList) {
            ComUser comUser = comUserMapper.selectByPrimaryKey(gorder.getCreateMan());
            SfGorderConsignee consignee =consigneeMapper.selectByGorderId(gorder.getId());
            SfGorderItem gorderItem = sfGorderItemMapper.selectByGorderId(gorder.getId());


            Map<String,Object> gorderWrap = new HashMap<>();
            gorderWrap.put("gorder", gorder);
            gorderWrap.put("comUser", comUser);
            gorderWrap.put("consignee", consignee);
            gorderWrap.put("item", gorderItem);
            gorderWraps.add(gorderWrap);
        }

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("total", pageInfo.getTotal());
        dataMap.put("rows", gorderWraps);

        return dataMap;
    }

}
