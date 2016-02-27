package com.masiis.shop.service.user;

import com.masiis.shop.dao.user.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/2/16.
 */
@Service
public class UserService {

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 根据条件查询sysUser
     * @param sysUserExample
     * @return
     */
    public List<SysUser> findByExample(SysUserExample sysUserExample){
        return sysUserMapper.selectByExample(sysUserExample);
    }

    /**
     * 保存sysUser
     * @param sysUser
     */
    public void addSysUser(SysUser sysUser){
        sysUserMapper.insert(sysUser);
    }

    public void updateSysUserById(SysUser sysUser){
        sysUserMapper.updateByPrimaryKey(sysUser);
    }

}
