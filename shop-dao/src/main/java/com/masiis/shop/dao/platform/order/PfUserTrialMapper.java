package com.masiis.shop.dao.platform.order;

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
    List<PfUserTrial> findByCondition(@Param("trial")PfUserTrial trial);

}
