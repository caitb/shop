package com.masiis.shop.admin.service.order;

import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.po.PfBorderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by cai_tb on 16/6/7.
 */
@Service
@Transactional
public class BorderItemService {

    @Resource
    private PfBorderItemMapper pfBorderItemMapper;

    /**
     * 查找borderItem
     * @param borderId
     * @return
     */
    public PfBorderItem findByBorderId(Long borderId){
        return pfBorderItemMapper.selectByOrderId(borderId);
    }
}
