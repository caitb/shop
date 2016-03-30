package com.masiis.shop.admin.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.user.User;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/3/5.
 */
@Service
public class ComUserService {

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComUserAccountMapper comUserAccountMapper;

    /**
     * 根据id查找合伙人
     * @param id
     * @return
     */
    public ComUser findById(Long id){
        return comUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据条件查询合伙人
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页记录数
     * @param comUser    查询条件
     * @return
     */
    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, ComUser comUser){

        PageHelper.startPage(pageNumber, pageSize);
        List<ComUser> comUsers = comUserMapper.selectByCondition(comUser);
        PageInfo<ComUser> pageInfo = new PageInfo<>(comUsers);

        List<User> users = new ArrayList<>(comUsers.size());
        for(ComUser cu : comUsers){
            ComUserAccount comUserAccount = comUserAccountMapper.findByUserId(cu.getId());

            User user = new User();
            user.setComUser(cu);
            user.setComUserAccount(comUserAccount);

            users.add(user);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", users);

        return pageMap;
    }

    /**
     * 获取待审核会员
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页记录数
     * @param comUser    查询条件
     * @return
     */
    public Map<String, Object> auditListByCondition(Integer pageNumber, Integer pageSize, ComUser comUser){
        PageHelper.startPage(pageNumber, pageSize);
        List<ComUser> comUsers = comUserMapper.auditList(comUser);
        PageInfo<ComUser> pageInfo = new PageInfo<>(comUsers);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", comUsers);

        return pageMap;
    }

    public String findByPid(Integer pid) {
        return comUserMapper.findByPid(pid);
    }
}
