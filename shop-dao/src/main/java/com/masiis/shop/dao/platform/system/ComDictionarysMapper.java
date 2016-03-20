package com.masiis.shop.dao.platform.system;

import com.masiis.shop.dao.po.ComDictionary;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */
@Repository
public interface ComDictionarysMapper {
    ComDictionary selectByCodeKey(Integer key);

    ComDictionary selectById(Integer id);

    ComDictionary selectByCodeAndKey(Map<String, Object> map);
}
