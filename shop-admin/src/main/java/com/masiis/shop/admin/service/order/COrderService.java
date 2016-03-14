package com.masiis.shop.admin.service.order;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.service.base.BaseService;
import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.po.PfCorder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
public class COrderService extends BaseService {

    private PfCorderMapper pfCorderMapper;

    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, PfCorder pfCorder){
        PageHelper.startPage(pageNumber, pageSize);
        List<PfCorder> pfCorders = pfCorderMapper.selectByCondition(pfCorder);
        PageInfo<PfCorder> pageInfo = new PageInfo<>(pfCorders);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", pfCorders);

        return pageMap;
    }
}
