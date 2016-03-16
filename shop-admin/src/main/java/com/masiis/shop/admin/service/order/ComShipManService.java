package com.masiis.shop.admin.service.order;

import com.masiis.shop.dao.platform.order.ComShipManMapper;
import com.masiis.shop.dao.po.ComShipMan;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/3/16.
 */
@Service
public class ComShipManService {

    @Resource
    private ComShipManMapper comShipManMapper;

    /**
     * 快递列表
     * @param comShipMan
     * @return
     */
    public List<ComShipMan> listByCondition(ComShipMan comShipMan){
        return comShipManMapper.selectByCondition(comShipMan);
    }
}
