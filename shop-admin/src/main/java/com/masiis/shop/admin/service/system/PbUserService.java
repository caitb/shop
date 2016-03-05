package com.masiis.shop.admin.service.system;

import com.masiis.shop.dao.platform.system.PbUserMapper;
import com.masiis.shop.dao.po.PbUser;
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
    public PbUser findByid(Long id){
        return pbUserMapper.selectById(id);
    }

    /**
     * 根据条件查询
     * @param pbUser
     * @return
     */
    public List<PbUser> findByCondition(PbUser pbUser){
        return pbUserMapper.selectByCondition(pbUser);
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
    public void updateById(PbUser pbUser){
        pbUserMapper.updateById(pbUser);
    }
}
