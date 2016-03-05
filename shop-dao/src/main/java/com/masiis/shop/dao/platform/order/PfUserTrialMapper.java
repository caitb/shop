package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserTrial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 49134 on 2016/3/3.
 */
public interface PfUserTrialMapper {
//    /**
//     * 搜索和列表
//     * @param pfUserTrialProduct
//     * @return
//     */
//    List<PfUserTrialProduct> findByCondition(@Param("pfUserTrialProduct")PfUserTrialProduct pfUserTrialProduct);

    ComUser findByParentId(@Param("parentUserId")Long parentUserId);

    int updateByPrimaryKey(PfUserTrial pfUserTrial);
    int  insert(PfUserTrial pfUserTrial);
}
