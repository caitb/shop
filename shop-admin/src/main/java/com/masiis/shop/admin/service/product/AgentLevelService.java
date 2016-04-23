package com.masiis.shop.admin.service.product;

import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.po.ComAgentLevel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/4/23.
 */
@Service
public class AgentLevelService {

    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;

    /**
     * 获取代理等级列表
     * @return
     */
    public List<ComAgentLevel> listAll(){
        return comAgentLevelMapper.selectAll();
    }
}
