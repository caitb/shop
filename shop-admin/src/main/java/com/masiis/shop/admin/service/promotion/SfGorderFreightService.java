package com.masiis.shop.admin.service.promotion;

import com.masiis.shop.dao.mall.promotion.SfGorderFreightMapper;
import com.masiis.shop.dao.po.SfGorderFreight;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/7/12.
 */
@Service
@Transactional
public class SfGorderFreightService {

    @Resource
    private SfGorderFreightMapper gorderFreightMapper;
    public int insert(SfGorderFreight record){
        return gorderFreightMapper.insert(record);
    }
}
