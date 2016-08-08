package com.masiis.shop.admin.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/8/8.
 */
@Service
public class UserSkuStockService {

    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;

//    public Map<String, Object> list(Integer pageNo, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap){
//        String sort = "pb.create_time desc";
//        if (sortName != null) sort = sortName + " " + sortOrder;
//        PageHelper.startPage(pageNo, pageSize, sort);
//        List<Map<String, Object>> bOrderMaps = pfUserSkuStockMapper.selectByCondition(conditionMap);
//        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(bOrderMaps);
//
//        Map<String, Object> pageMap = new HashMap<>();
//        pageMap.put("total", pageInfo.getTotal());
//        pageMap.put("rows", bOrderMaps);
//
//        return pageMap;
//    }
}
