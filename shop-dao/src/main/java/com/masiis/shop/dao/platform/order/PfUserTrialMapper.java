package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.beans.order.PfUserTrialProduct;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserTrial;
import com.masiis.shop.dao.po.SfUserRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 49134 on 2016/3/3.
 */
public interface PfUserTrialMapper {
    /**
     * 搜索和列表
     * @param trial
     * @return
     */
    List<PfUserTrialProduct> findByCondition(@Param("pfUserTrialProduct")PfUserTrialProduct pfUserTrialProduct);

    ComUser findByParentId(@Param("parentUserId")Long parentUserId);

    int updateByPrimaryKey(PfUserTrial pfUserTrial);
    int  insert(PfUserTrial pfUserTrial);
}
