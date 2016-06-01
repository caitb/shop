package com.masiis.shop.web.mall.service.order;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.dao.beans.order.SfDistributionPerson;
import com.masiis.shop.dao.beans.order.SfDistributionRecord;
import com.masiis.shop.dao.mall.order.SfDistributionRecordMapper;
import com.masiis.shop.dao.mall.order.SfOrderItemDistributionExtendMapper;
import com.masiis.shop.dao.mall.order.SfOrderItemDistributionMapper;
import com.masiis.shop.dao.po.SfOrderItemDistribution;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 小铺订单商品分润Service
 * Created by wangbingjian on 2016/4/8.
 */
@Service
public class SfOrderItemDistributionService {

    @Autowired
    private SfOrderItemDistributionMapper sfOrderItemDistributionMapper;
    @Autowired
    private SfOrderItemDistributionExtendMapper sfOrderItemDistributionExtendMapper;

    private final Logger log = Logger.getLogger(SfOrderItemDistributionService.class);

    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public int insert(SfOrderItemDistribution orderItemDis){
        return sfOrderItemDistributionMapper.insert(orderItemDis);
    }
    /**
     * 根据小铺详情订单id查找
     * @author hanzengzhi
     * @date 2016/5/17 10:08
     */
    public List<SfOrderItemDistribution> selectBySfOrderItemId(Long sfOrderId){
        return sfOrderItemDistributionMapper.selectBySfOrderItemId(sfOrderId);
    }

    /**
     * 根据小铺订单id查找
     * @author hanzengzhi
     * @date 2016/5/17 10:08
     */
    public List<SfOrderItemDistribution> selectBySfOrderId(Long sfOrderId){
        return sfOrderItemDistributionMapper.selectBySfOrderId(sfOrderId);
    }

    /**
     * 根据条件查询小铺订单商品分润 数量
     * @param record
     * @return
     */
    public int findCountByCondition(SfOrderItemDistribution record){
        return sfOrderItemDistributionMapper.selectCountByCondition(record);
    }

    /**
     * 通过userId查询佣金记录
     * @param userId        userId
     * @param currentPage   当前页
     * @param pageSize      每页数量
     * @return
     */
    public List<SfOrderItemDistribution> findCommissionRecordByUserIdLimitPage(Long userId,int currentPage,int pageSize){
        //当当前页或者每页数量为0时 不进行分页查询
        if (currentPage == 0 || pageSize == 0){
            return sfOrderItemDistributionExtendMapper.selectCommissionRecordByUserId(userId);
        }
        PageHelper.startPage(currentPage,pageSize);
        return sfOrderItemDistributionExtendMapper.selectCommissionRecordByUserId(userId);
    }
    /**
     * 通过userId查询佣金记录数量
     * @param userId        userId
     */
    public int findCommissionRecordCountByUserId(Long userId){
        return sfOrderItemDistributionExtendMapper.selectCommissionRecordCountByUserId(userId);
    }
    public List<Map<String,Object>> selectSumAmount(Long userId){
        return sfOrderItemDistributionExtendMapper.selectSumAmount(userId);
    }

    public Map<String,BigDecimal> selectUserAmounts(Long userId){
        return sfOrderItemDistributionExtendMapper.selectUserAmount(userId);
    }

}
