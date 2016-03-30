package com.masiis.shop.admin.service.product;

import com.masiis.shop.dao.platform.product.ComUnitMeasureMapper;
import com.masiis.shop.dao.po.ComUnitMeasure;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/3/30.
 */
@Service
public class UnitMeasureService {

    @Resource
    private ComUnitMeasureMapper comUnitMeasureMapper;

    /**
     * 获取所有单位
     * @return
     */
    public List<ComUnitMeasure> listAll(){
        return comUnitMeasureMapper.selectByCondition(new ComUnitMeasure());
    }
}
