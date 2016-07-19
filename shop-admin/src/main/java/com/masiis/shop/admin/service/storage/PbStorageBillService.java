package com.masiis.shop.admin.service.storage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.platform.storage.PbStorageBillMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2016/7/19
 * @Author lzh
 */
@Service
public class PbStorageBillService {

    @Resource
    private PbStorageBillMapper pbStorageBillMapper;


    /**
     * 库存变更单列表
     * @param pageNo
     * @param pageSize
     * @param sortName
     * @param sortOrder
     * @param conditionMap
     * @return
     */
    public Map<String, Object> storagechangeList(Integer pageNo, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap){
        /*String sort = "pb.create_time desc";
        if (sortName != null) sort = sortName + " " + sortOrder;
        PageHelper.startPage(pageNo, pageSize, sort);*/

        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> bOrderMaps = pbStorageBillMapper.storagechangeList(conditionMap);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(bOrderMaps);
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", bOrderMaps);
        return pageMap;
    }

}
