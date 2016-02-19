package com.masiis.shop.service.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.dao.menu.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

/**
 * Created by ZhaoLiang on 2016/2/18.
 */
@Service
public class MenuService {
    @Resource
    private BMenuMapper bMenuMapper;
    @Resource
    private BUserMenuMapper bUserMenuMapper;

    /**
     * 获取菜单列表
     *
     * @return
     * @throws JsonProcessingException
     */
    public List<BMenu> getData() throws JsonProcessingException {
        return bMenuMapper.selectByExample(new BMenuExample());
    }

    /**
     * 获取用户菜单
     * @param userID
     * @return
     */
    public List<BUserMenu> getUserMenu(Long userID) {
        BUserMenuExample bme = new BUserMenuExample();
        bme.createCriteria().andUserIdEqualTo(userID);
        return bUserMenuMapper.selectByExample(bme);
    }
}
