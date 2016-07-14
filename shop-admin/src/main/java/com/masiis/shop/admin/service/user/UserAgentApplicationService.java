package com.masiis.shop.admin.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.platform.user.PfUserAgentApplicationMapper;
import com.masiis.shop.dao.po.PfUserAgentApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合伙意向申请
 * Created by cai_tb on 16/7/13.
 */
@Service
@Transactional
public class UserAgentApplicationService {

    @Resource
    private PfUserAgentApplicationMapper pfUserAgentApplicationMapper;

    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap) {
        String sort = "uaa.create_time desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<Map<String, Object>> userAgentApplications = pfUserAgentApplicationMapper.selectByCondition(conditionMap);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(userAgentApplications);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", userAgentApplications);

        return pageMap;
    }

    /**
     * 更新合伙意向申请
     * @param pfUserAgentApplication
     */
    public void updateById(PfUserAgentApplication pfUserAgentApplication) {
        pfUserAgentApplicationMapper.updateByPrimaryKey(pfUserAgentApplication);
    }


}
