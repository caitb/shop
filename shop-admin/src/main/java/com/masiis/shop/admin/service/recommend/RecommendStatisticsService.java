package com.masiis.shop.admin.service.recommend;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.beans.recommend.RecommendStatistics;
import com.masiis.shop.dao.platform.user.PfUserStatisticsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/6/15.
 */
@Service
@Transactional
public class RecommendStatisticsService {

    @Resource
    private PfUserStatisticsMapper pfUserStatisticsMapper;

    /**
     * 推荐统计
     * @param pageNumber
     * @param pageSize
     * @param sortName
     * @param sortOrder
     * @param conditionMap
     * @return
     */
    public Map<String, Object> list(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap){
        String sort = "pus.create_time desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<RecommendStatistics> recommendStatisticses = pfUserStatisticsMapper.recommendStatistics(conditionMap);
        PageInfo<RecommendStatistics> pageInfo = new PageInfo<>(recommendStatisticses);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("rows", recommendStatisticses);
        pageMap.put("total", pageInfo.getTotal());

        return pageMap;
    }

}
