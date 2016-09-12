package com.masiis.shop.web.common.service;

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

    /**
     * 查询前端显示的最低等级
     * @param isOrganizationShow
     * @return
     */
    public  ComAgentLevel getMinLevelByIsOrganizationShow(Integer isOrganizationShow){
        return comAgentLevelMapper.getMaxIdByIsOrganizationShow(isOrganizationShow);
    }


    public  List<ComAgentLevel> selectAll(){
        return comAgentLevelMapper.selectAll();
    }

    public List<ComAgentLevel> selectImgUrlById(Long id){return comAgentLevelMapper.selectImgUrlById(id);}

    public List<ComAgentLevel> selectLastAll(Integer id){
        return comAgentLevelMapper.selectLastAll(id);
    }
}
