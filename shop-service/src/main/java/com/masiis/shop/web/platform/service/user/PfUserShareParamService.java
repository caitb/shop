package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.PfUserShareParamMapper;
import com.masiis.shop.dao.po.PfUserShareParam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 合伙人推广分享表service
 *
 * @Date 2016/6/14
 * @Author lzh
 */
@Service
public class PfUserShareParamService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfUserShareParamMapper pfUserShareParamMapper;

    public PfUserShareParam findById(Long id){
        return pfUserShareParamMapper.selectByPrimaryKey(id);
    }
}
