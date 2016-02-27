package com.masiis.shop.service.UserMenu;

import com.masiis.shop.dao.usermenu.SysUserMenu;
import com.masiis.shop.dao.usermenu.SysUserMenuExample;
import com.masiis.shop.dao.usermenu.SysUserMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/2/23.
 */
@Service
public class UserMenuService {

    @Resource
    private SysUserMenuMapper sysUserMenuMapper;

    /**
     * 根据条件查询
     * @param sysUserMenuExample
     * @return
     */
    public List<SysUserMenu> findByExample(SysUserMenuExample sysUserMenuExample){
        return sysUserMenuMapper.selectByExample(sysUserMenuExample);
    }
}
