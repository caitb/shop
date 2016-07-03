package com.masiis.shop.web.platform.service.shop;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.dao.beans.order.SfDistributionPerson;
import com.masiis.shop.dao.beans.order.SfDistributionRecord;
import com.masiis.shop.dao.mall.order.SfDistributionRecordMapper;
import com.masiis.shop.dao.mall.order.SfOrderItemDistributionMapper;
import com.masiis.shop.dao.po.SfOrderItemDistribution;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 小铺订单商品分润Service
 * Created by wangbingjian on 2016/4/8.
 */
@Service
public class SfOrderItemDistributionService {

    @Autowired
    private SfOrderItemDistributionMapper sfOrderItemDistributionMapper;
    @Autowired
    private SfDistributionRecordMapper sfDistributionRecordMapper;

    private final Logger log = Logger.getLogger(SfOrderItemDistributionService.class);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public int insert(SfOrderItemDistribution orderItemDis) {
        return sfOrderItemDistributionMapper.insert(orderItemDis);
    }


    /**
     * 查询分销记录总数和总参与人数
     *
     * @param userId
     * @return
     */
    public SfDistributionRecord findCountSfDistributionRecord(Long userId) {
        return sfDistributionRecordMapper.selectCountByUserId(userId);
    }

    /**
     * 询分销记录list
     *
     * @param userid
     * @param start
     * @param end
     * @param currentPage
     * @param pageSize
     * @return
     */
    public List<SfDistributionRecord> findListSfDistributionRecordLimit(Long userid, Date start, Date end, Integer currentPage, Integer pageSize) {
        if (currentPage == 0 || pageSize == 0) {
            return sfDistributionRecordMapper.selectListByUserIdLimt(userid, start, end);
        }
        PageHelper.startPage(currentPage, pageSize);
        return sfDistributionRecordMapper.selectListByUserIdLimt(userid, start, end);
    }

    public Integer findCountSfDistributionRecordLimit(Long userid, Date start, Date end) {
        return sfDistributionRecordMapper.selectCountByUserIdLimit(userid, start, end);
    }

    /**
     * 根据订单商品子表 id查询分润人信息
     *
     * @param itemId
     * @return
     */
    public List<SfDistributionPerson> findListSfDistributionPerson(Long itemId) {
        return sfDistributionRecordMapper.selectListSfDistributionPersonByItemId(itemId);
    }
}
