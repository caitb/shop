package com.masiis.shop.admin.service.system;

import com.masiis.shop.dao.platform.system.PfSysMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/9/5.
 */
@Service
public class PfSysMenuService {

    @Resource
    private PfSysMenuMapper pfSysMenuMapper;

    public int delete(Integer id){
        return pfSysMenuMapper.deleteByPrimaryKey(id);
    }
}
