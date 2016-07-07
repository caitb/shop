package com.masiis.shop.admin.service.material;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.platform.material.ComUserMaterielApplicationMapper;
import com.masiis.shop.dao.po.ComUserMaterielApplication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 线下素材申请
 * Created by cai_tb on 16/7/7.
 */
@Service
public class MaterielApplicationService {

    @Resource
    private ComUserMaterielApplicationMapper comUserMaterielApplicationMapper;

    /**
     * 条件分页查询
     * @param pageNumber
     * @param pageSize
     * @param sortName
     * @param sortOrder
     * @param conditionMap
     * @return
     */
    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap) {
        String sort = "uma.create_time desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<Map<String, Object>> materialApplications = comUserMaterielApplicationMapper.selectByCondition(conditionMap);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(materialApplications);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", materialApplications);

        return pageMap;
    }

    /**
     * 更新
     * @param comUserMaterielApplication
     */
    public void updateById(ComUserMaterielApplication comUserMaterielApplication){
        comUserMaterielApplicationMapper.updateByPrimaryKey(comUserMaterielApplication);
    }
}
