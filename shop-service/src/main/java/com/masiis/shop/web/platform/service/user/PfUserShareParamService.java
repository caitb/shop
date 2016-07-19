package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.PfUserShareParamMapper;
import com.masiis.shop.dao.po.PfUserShareParam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 合伙人推广分享表service
 *
 * @Date 2016/6/14
 * @Author lzh
 */
@Service
@Transactional
public class PfUserShareParamService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfUserShareParamMapper pfUserShareParamMapper;

    public PfUserShareParam findById(Long id){
        return pfUserShareParamMapper.selectByPrimaryKey(id);
    }

    public List<PfUserShareParam> findByCondition(PfUserShareParam pfUserShareParam){
        return pfUserShareParamMapper.selectByCondition(pfUserShareParam);
    }

    /**
     * 添加海报二维码参数
     * @param pfUserShareParam
     * @param type
     */
    public void add(PfUserShareParam pfUserShareParam, Integer type){
        pfUserShareParamMapper.insert(pfUserShareParam);
    }
}
