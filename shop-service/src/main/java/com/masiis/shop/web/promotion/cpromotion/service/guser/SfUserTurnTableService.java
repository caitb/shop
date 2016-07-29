package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.dao.mall.promotion.SfUserTurnTableMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户转盘信息service
 */
@Service
public class SfUserTurnTableService {

    @Resource
    private SfUserTurnTableMapper userTurnTableMapper;
}
