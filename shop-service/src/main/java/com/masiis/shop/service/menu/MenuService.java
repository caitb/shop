package com.masiis.shop.service.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import com.masiis.shop.dao.menu.*;
import org.springframework.core.convert.converter.Converter;
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
     * 获取菜单项
     * @param id
     * @return
     */
    public BMenu findById(Long id){
        return bMenuMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取菜单列表
     *
     * @return
     * @throws JsonProcessingException
     */
    public List<BMenu> getData(BMenuExample bMenuExample) throws JsonProcessingException {
        return bMenuMapper.selectByExample(bMenuExample);
    }

    /**
     * 获取用户菜单
     *
     * @param userID
     * @return
     */
    public List<BUserMenu> getUserMenu(Long userID) {
        BUserMenuExample bme = new BUserMenuExample();
        bme.createCriteria().andUserIdEqualTo(userID);
        return bUserMenuMapper.selectByExample(bme);
    }

    /**
     * 更新用户菜单:先删除后添加
     *
     * @return
     */
    public void updateUserMenu(Long userID, String[] menuIDs) {
        BUserMenuExample bme = new BUserMenuExample();
        bme.createCriteria().andUserIdEqualTo(userID);
        int n = bUserMenuMapper.deleteByExample(bme);
        BUserMenu bm = null;
        for (String menuID : menuIDs) {
            bm = new BUserMenu();
            bm.setMenuId(Long.parseLong(menuID));
            bm.setUserId(userID);
            bUserMenuMapper.insert(bm);
        }
    }
}
