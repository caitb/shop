package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.dao.platform.order.BOrderMapper;
import com.masiis.shop.dao.platform.order.BorderItemMapper;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
public class BOrderService {
    @Resource
    private BOrderMapper bOrderMapper;
    @Resource
    private BorderItemMapper borderItemMapper;
    /**
     * 添加订单
     * @param pfBorder
     * @param pfBorderItems
     */
    public void AddBOrder(PfBorder pfBorder, List<PfBorderItem> pfBorderItems){
        bOrderMapper.insert(pfBorder);

//        borderItemMapper.insert(pfBorderItems);
    }
}
