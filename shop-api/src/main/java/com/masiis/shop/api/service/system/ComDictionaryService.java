package com.masiis.shop.api.service.system;

import com.masiis.shop.dao.platform.system.ComDictionarysMapper;
import com.masiis.shop.dao.po.ComDictionary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public ComDictionary findById(Integer id){
        return comDictionarysMapper.selectById(id);
    }

    /**
     * 根据code和key查询字典表对象
     *
     * @param code
     * @param key
     * @return
     */
    public ComDictionary findByCodeAndKey(String code, Integer key){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("key", key);
        return comDictionarysMapper.selectByCodeAndKey(map);
    }
}
