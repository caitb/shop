package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.ComAgentLevel;
import org.springframework.stereotype.Repository;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */

@Repository
public interface ComAgentLevelsMapper {
    /**
     * 获取合伙人等级
     * @author muchaofeng
     * @date 2016/3/9 18:22
     */
    ComAgentLevel selectByPrimaryKey(Integer id);
}
