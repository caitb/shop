package com.masiis.shop.service;


import com.masiis.shop.dao.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cai_tb on 16/2/29.
 */
@Service
public class PbMenuService {

    @Resource
    private PbMenuMapper pbMenuMapper;
    @Resource
    private PbUserMenuMapper pbUserMenuMapper;

    /**
     * 根据ID查找
     * @param id
     * @return
     */
    public PbMenu findByPrimaryKey(Long id){
        return pbMenuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查找用户菜单项
     * @param userId
     * @return
     */
    public List<PbMenu> findByPbUserId(Long userId){
        List<PbMenu> pbMenus = new ArrayList<>();

        if(userId == null){
            return pbMenus;
        }

        PbUserMenuExample pbUserMenuExample = new PbUserMenuExample();
        PbUserMenuExample.Criteria criteria = pbUserMenuExample.createCriteria();
        criteria.andPbUserIdEqualTo(userId);
        List<PbUserMenu> pbUserMenus = pbUserMenuMapper.selectByExample(pbUserMenuExample);

        for(PbUserMenu pbUserMenu : pbUserMenus){
            PbMenu pbMenu = pbMenuMapper.selectByPrimaryKey(pbUserMenu.getPbMenuId());
            if(pbMenu != null) pbMenus.add(pbMenu);
        }
        return pbMenus;
    }

    /**
     * 根据条件查询
     * @param pbMenuExample
     * @return
     */
    public List<PbMenu> findByExample(PbMenuExample pbMenuExample){
        return pbMenuMapper.selectByExample(pbMenuExample);
    }

    /**
     * 保存记录
     * @param pbMenu
     */
    public void add(PbMenu pbMenu){
        pbMenuMapper.insert(pbMenu);
    }

    /**
     * 根据ID更新记录
     * @param pbMenu
     */
    public void updateByPrimaryKey(PbMenu pbMenu){
        pbMenuMapper.updateByPrimaryKey(pbMenu);
    }
}
