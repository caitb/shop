package com.masiis.shop.service.UserMenu;

import com.masiis.shop.dao.menu.BUserMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/2/23.
 */
@Service
public class UserMenuService {

    @Resource
    private BUserMenuMapper bUserMenuMapper;

    public List<Long> findMenuIdsByUserId(Long userId){
        return bUserMenuMapper.findMenuIdsByUserId(userId);
    }
}
