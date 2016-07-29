package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.masiis.shop.dao.mall.promotion.SfTurnTableMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 转盘service
 */
@Service
public class SfTurnTableService {

    @Resource
    private SfTurnTableMapper turnTableMapper;
}
