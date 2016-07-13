package com.masiis.shop.web.platform.service.order;

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
     * @author muchaofeng
     * @date 2016/4/25 15:29
     */

    public List<ComShipMan> list(){
        return comShipManMapper.selectAll();
    }
}
