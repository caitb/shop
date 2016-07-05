package com.masiis.shop.web.common.service;

import com.masiis.shop.dao.platform.gift.ComGiftMapper;
import com.masiis.shop.dao.po.ComGift;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/7/5.
 */
@Service
public class ComGiftService {

    @Resource
    private ComGiftMapper comGiftMapper;

    public ComGift getComGiftById(Integer id){
        return comGiftMapper.selectByPrimaryKey(id);
    }
}
