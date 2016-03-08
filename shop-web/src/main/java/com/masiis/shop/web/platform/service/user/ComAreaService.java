package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.ComAreaMapper;
import com.masiis.shop.dao.po.ComArea;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hzzh on 2016/3/7.
 */
@Service
@Transactional
public class ComAreaService {

    @Resource
    private ComAreaMapper comAreaMapper;

    public List<ComArea> queryComAreasByParams(ComArea comArea){
        return comAreaMapper.queryComAreasByParam(comArea);
    }
}
