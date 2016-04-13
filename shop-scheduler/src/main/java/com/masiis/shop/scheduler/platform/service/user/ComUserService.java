package com.masiis.shop.scheduler.platform.service.user;

import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lzh on 2016/3/23.
 */
@Service
public class ComUserService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private ComUserMapper userMapper;

    public List<ComUser> findAll() {
        return userMapper.selectAll();
    }
}
