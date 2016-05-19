package com.masiis.shop.api.service.user;

import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.po.ComAgentLevel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/5/19.
 */
@Service
public class AgentLevelService {

    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;

    public ComAgentLevel getById(Integer levelId){
        return comAgentLevelMapper.selectByPrimaryKey(levelId);
    }

    public List<ComAgentLevel> listAll(){
        return comAgentLevelMapper.selectAll();
    }
}
