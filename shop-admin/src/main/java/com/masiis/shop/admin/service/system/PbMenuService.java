package com.masiis.shop.admin.service.system;


import com.masiis.shop.dao.platform.system.PbMenuMapper;
import com.masiis.shop.dao.platform.system.PbUserMenuMapper;
import com.masiis.shop.dao.po.PbMenu;
import com.masiis.shop.dao.po.PbUserMenu;
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
    public PbMenu findById(Long id){
        return pbMenuMapper.selectById(id);
    }

    /**
     * 查找用户菜单项
     * @param pbUserId
     * @return
     */
    public List<PbMenu> findByPbUserId(Long pbUserId){
        List<PbMenu> pbMenus = new ArrayList<>();

        if(pbUserId == null){
            return pbMenus;
        }
        PbUserMenu pbUserMenuC = new PbUserMenu();
        pbUserMenuC.setPbUserId(pbUserId);
        List<PbUserMenu> pbUserMenus = pbUserMenuMapper.selectByCondition(pbUserMenuC);

        for(PbUserMenu pbUserMenu : pbUserMenus){
            PbMenu pbMenu = pbMenuMapper.selectById(pbUserMenu.getPbMenuId());
            if(pbMenu != null) pbMenus.add(pbMenu);
        }
        return pbMenus;
    }

    /**
     * 根据条件查询
     * @param pbMenu
     * @return
     */
    public List<PbMenu> findByCondition(PbMenu pbMenu){
        return pbMenuMapper.selectByCondition(pbMenu);
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
    public void updateById(PbMenu pbMenu){
        pbMenuMapper.updateById(pbMenu);
    }
}
