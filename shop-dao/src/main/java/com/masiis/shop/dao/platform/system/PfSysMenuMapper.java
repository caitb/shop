/*
 * PfSysMenuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-08-10 Created
 */
package com.masiis.shop.dao.platform.system;

import com.masiis.shop.dao.po.ComBrand;
import com.masiis.shop.dao.po.PfSysMenu;

import java.util.List;
import java.util.Map;

public interface PfSysMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PfSysMenu record);

    PfSysMenu selectByPrimaryKey(Integer id);

    List<PfSysMenu> selectAll();

    int updateByPrimaryKey(PfSysMenu record);

    List<Map<String,Object>> bannerSortList();

    void updateSort(PfSysMenu pfSysMenu);

    List<Map<String,Object>> familySortList();

    List<Map<String,Object>> teamSortList();

    List<Map<String,Object>> listBrand();

    boolean addBrand(PfSysMenu pfSysMenu);

    boolean deleteBrand(Integer id);

    List<Map<String,Object>> listFamily();

    boolean addFamily(PfSysMenu pfSysMenu);

    List<Map<String,Object>> listTeam();

    boolean addTeam(PfSysMenu pfSysMenu);

    PfSysMenu selectMaxSort(Integer type);


}