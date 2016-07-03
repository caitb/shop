package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.ComAreaMapper;
import com.masiis.shop.dao.po.ComArea;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hzzh on 2016/3/7.
 */
@Service
@Transactional
public class ComAreaService{

    @Resource
    private ComAreaMapper comAreaMapper;

    public static List<ComArea> comAreas;

    public List<ComArea> queryComAreasByParams(ComArea comArea){
        return comAreaMapper.queryComAreasByParam(new ComArea());
    }

    /**
     * 查询所有的省
     * @author  hanzengzhi
     * @date  2016/3/20 20:35
     */
    public List<ComArea> queryAllProvince(){
        return comAreaMapper.queryAllProvince();
    }

    /**
     * 根据省的id查询市
     * @author  hanzengzhi
     * @date  2016/3/20 21:07
     */
    public List<ComArea> queryCityByProviceId(Integer provinceId){
        return comAreaMapper.queryCityByProviceId(provinceId);
    }

    /**
     * 根据市的id查询县区
     * @param cityId
     * @return
     */
    public List<ComArea> queryCountyByCityId(Integer cityId){
        return comAreaMapper.queryCountyByCityId(cityId);
    }
}
