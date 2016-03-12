package com.masiis.shop.admin.service.user;

import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/3/5.
 */
@Service
public class ComUserService {

    @Resource
    private ComUserMapper comUserMapper;

    /**
     * 根据id查找合伙人
     * @param id
     * @return
     */
    public ComUser findById(Long id){
        return comUserMapper.selectByPrimaryKey(id);
    }

    public List<ComUser> listByCondition(ComUser comUser){
        return null;
    }

    public String findByPid(Integer pid) {
        return comUserMapper.findByPid(pid);
    }
}
