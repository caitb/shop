package com.masiis.shop.admin.service.order;

import com.masiis.shop.dao.beans.order.PfUserTrialProduct;
import com.masiis.shop.dao.platform.order.PfUserTrialMapper;
import com.masiis.shop.dao.platform.order.SfUserRelationMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserTrial;
import com.masiis.shop.dao.po.SfUserRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 49134 on 2016/3/3.
 */
@Service
public class PfUserTrialService {

    @Resource
    public PfUserTrialMapper pfTrialMapper;
    @Resource
    public SfUserRelationMapper userRelationMapper;
    @Resource
    public ComSkuMapper comSkuMapper;
    /**
     * 查询试用列表
     * @param
     * @return
     */
    public List<PfUserTrialProduct> findByCondition(PfUserTrialProduct pfUserTrialProduct) {
        return pfTrialMapper.findByCondition(pfUserTrialProduct);
    }
    /**
     * 查询用户和推荐人关系对象
     * @param id
     * @return
     */
    public SfUserRelation findByUserId(Long id) {
        return userRelationMapper.findByUserId(id);
    }
    /**
     * 查询推荐人
     * @param parentUserId
     * @return
     */
    public ComUser findByParentId(Long parentUserId) {
        return pfTrialMapper.findByParentId(parentUserId);
    }

    /**
     * 查找申请商品
     * @param skuId
     * @return
     */
    public ComSku findBySkuId(Integer skuId) {
        return comSkuMapper.findBySkuId(skuId);
    }
}
