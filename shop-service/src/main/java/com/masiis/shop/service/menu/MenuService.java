package com.masiis.shop.service.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.masiis.shop.dao.menu.*;
import com.masiis.shop.dao.usermenu.SysUserMenu;
import com.masiis.shop.dao.usermenu.SysUserMenuExample;
import com.masiis.shop.dao.usermenu.SysUserMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ZhaoLiang on 2016/2/18.
 */
@Service
public class MenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysUserMenuMapper sysUserMenuMapper;


    /**
     * 获取菜单项
     * @param id
     * @return
     */
    public SysMenu findById(Long id){
        return sysMenuMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取菜单列表
     *
     * @return
     * @throws JsonProcessingException
     */
    public List<SysMenu> getData(SysMenuExample sysMenuExample) throws JsonProcessingException {
        return sysMenuMapper.selectByExample(sysMenuExample);
    }

    /**
     * 获取用户菜单
     *
     * @param userID
     * @return
     */
    public List<SysUserMenu> getUserMenu(Long userID) {
        SysUserMenuExample sysUserMenuExample = new SysUserMenuExample();
        sysUserMenuExample.createCriteria().andUserIdEqualTo(userID);
        return sysUserMenuMapper.selectByExample(sysUserMenuExample);
    }

    /**
     * 更新用户菜单:先删除后添加
     *
     * @return
     */
    public void updateUserMenu(Long userID, String[] menuIDs) {
        SysUserMenuExample sysUserMenuExample = new SysUserMenuExample();
        sysUserMenuExample.createCriteria().andUserIdEqualTo(userID);
        int n = sysUserMenuMapper.deleteByExample(sysUserMenuExample);
        SysUserMenu bm = null;
        for (String menuID : menuIDs) {
            bm = new SysUserMenu();
            bm.setMenuId(Long.parseLong(menuID));
            bm.setUserId(userID);
            sysUserMenuMapper.insert(bm);
        }
    }
}
