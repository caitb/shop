package com.masiis.shop.web.platform.service.system;

import com.masiis.shop.dao.platform.system.ComDictionarysMapper;
import com.masiis.shop.dao.po.ComDictionary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */
@Service
@Transactional
public class ComDictionaryService {
    @Resource
    ComDictionarysMapper comDictionarysMapper;

    /**
     * 获取订单状态
     * @author muchaofeng
     * @date 2016/3/16 14:19
     */
    public ComDictionary findComDictionary(Integer key){
        return comDictionarysMapper.selectByCodeKey(key);
    }
}
