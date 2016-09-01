package com.masiis.shop.admin.service.sort;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.platform.system.PfSysMenuMapper;
import com.masiis.shop.dao.po.ComBrand;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.PfSysMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/8/15.
 */
@Service
public class BrandSortService {

    @Resource
    private PfSysMenuMapper pfsysMenuMapper;

    public Map<String, Object> bannerSortList(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> map) {
        String sort = "b.cname desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<Map<String, Object>> BannerSortList = pfsysMenuMapper.bannerSortList();
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(BannerSortList);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", BannerSortList);
        return pageMap;
    }

    public void update(PfSysMenu pfSysMenu) {
        pfsysMenuMapper.updateSort(pfSysMenu);
    }

    public Map<String, Object> familySortList(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> map) {
        String sort = "uo.name desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<Map<String, Object>> familySortList = pfsysMenuMapper.familySortList();
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(familySortList);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", familySortList);
        return pageMap;
    }

    public Map<String, Object> teamSortList(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> map) {
        String sort = "uo.name desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<Map<String, Object>> teamSortList = pfsysMenuMapper.teamSortList();
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(teamSortList);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", teamSortList);
        return pageMap;
    }

    public List<Map<String, Object>> brandList() {
        return pfsysMenuMapper.listBrand();
    }


    public boolean addBrand(PfSysMenu pfSysMenu) {
        PfSysMenu m = pfsysMenuMapper.selectMaxSort(1);
        pfSysMenu.setCreateTime(new Date());
        pfSysMenu.setType(1);
        pfSysMenu.setValue(pfSysMenu.getId());
        pfSysMenu.setSort(m==null?0:m.getSort()+1);
        return pfsysMenuMapper.addBrand(pfSysMenu);
    }


    public boolean deleteBrand(Integer id) {
        return pfsysMenuMapper.deleteBrand(id);
    }


    public List<Map<String,Object>> familyList() {
        return pfsysMenuMapper.listFamily();
    }

    public boolean addFamily(PfSysMenu pfSysMenu) {
        PfSysMenu m = pfsysMenuMapper.selectMaxSort(2);
        pfSysMenu.setCreateTime(new Date());
        pfSysMenu.setType(2);
        pfSysMenu.setValue(pfSysMenu.getId());
        pfSysMenu.setSort(m==null?0:m.getSort()+1);
        return pfsysMenuMapper.addFamily(pfSysMenu);
    }


    public List<Map<String,Object>> teamList() {
        return pfsysMenuMapper.listTeam();
    }

    public boolean addTeam(PfSysMenu pfSysMenu) {
        PfSysMenu m = pfsysMenuMapper.selectMaxSort(3);
        pfSysMenu.setCreateTime(new Date());
        pfSysMenu.setType(3);
        pfSysMenu.setValue(pfSysMenu.getId());
        pfSysMenu.setSort(m==null?0:m.getSort()+1);
        return pfsysMenuMapper.addTeam(pfSysMenu);
    }


}
