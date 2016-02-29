package com.masiis.shop.service;

import com.masiis.shop.dao.pbuser.PbUser;
import com.masiis.shop.dao.pbuser.PbUserExample;
import com.masiis.shop.dao.pbuser.PbUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/2/29.
 */
@Service
public class PbUserService {

    @Resource
    private PbUserMapper pbUserMapper;

    /**
     * 根据ID查找
     * @param id
     * @return
     */
    public PbUser findByPrimaryKey(Long id){
        return pbUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据条件查询
     * @param pbUserExample
     * @return
     */
    public List<PbUser> findByExample(PbUserExample pbUserExample){
        return pbUserMapper.selectByExample(pbUserExample);
    }

    /**
     * 保存记录
     * @param pbUser
     */
    public void add(PbUser pbUser){
        pbUserMapper.insert(pbUser);
    }

    /**
     * 根据ID更新记录
     * @param pbUser
     */
    public void updateByPrimaryKey(PbUser pbUser){
        pbUserMapper.updateByPrimaryKey(pbUser);
    }
}
