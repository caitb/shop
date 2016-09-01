package com.masiis.shop.admin.service.user;

import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.po.ComAgentLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hzz on 2016/6/15.
 */
@Service
@Transactional
public class ComAgentLevelService {

    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;

    public  ComAgentLevel selectByPrimaryKey(Integer id){
        return comAgentLevelMapper.selectByPrimaryKey(id);
    }

    public  List<ComAgentLevel> selectAll(){ return comAgentLevelMapper.selectAll(); }

}
