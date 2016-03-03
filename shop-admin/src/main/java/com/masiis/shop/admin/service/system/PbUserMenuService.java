package com.masiis.shop.admin.service.system;

import com.masiis.shop.dao.platform.system.PbUserMenuMapper;
import com.masiis.shop.dao.po.PbUserMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/2/29.
 */
@Service
public class PbUserMenuService {

    @Resource
    private PbUserMenuMapper pbUserMenuMapper;

    /**
     * 根据条件查询
     * @param pbUserMenu
     * @return
     */
    public List<PbUserMenu> findByCondition(PbUserMenu pbUserMenu){
        return pbUserMenuMapper.selectByCondition(pbUserMenu);
    }

    /**
     * 保存记录
     * @param pbUserMenu
     */
    public void add(PbUserMenu pbUserMenu){
        pbUserMenuMapper.insert(pbUserMenu);
    }

    /**
     * 根据pbUserId删除记录
     * @param pbUserId
     */
    public void deleteByPbUserId(Long pbUserId){
        pbUserMenuMapper.deleteByPbUserId(pbUserId);
    }
}
