package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.beans.user.CountGroup;
import com.masiis.shop.dao.platform.user.CountGroupMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 团队统计
 *
 * @author muchaofeng
 * @date 2016/6/7 10:59
 */

@Service
public class CountGroupService {
    @Resource
    private CountGroupMapper countGroupMapper;

    /**
     * 统计团队人数、销售额、订单总数
     *
     * @author muchaofeng
     * @date 2016/6/7 11:06
     */
    public CountGroup countGroupInfo(String treeCode) {
        return countGroupMapper.countGroup(treeCode);
    }

    /**
     * 统计推荐团队人数、销售额、订单总数
     *
     * @param treeCode pf_user_sku 表 tree_code
     * @return
     */
    public CountGroup countRecommendGroup(String treeCode) {
        return countGroupMapper.countRecommendGroup(treeCode);
    }

}
