package com.masiis.shop.dao.platform.system;

import com.masiis.shop.dao.po.ComDictionary;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */

public interface ComDictionarysMapper {
    ComDictionary selectByCodeKey(Integer key);

    ComDictionary selectById(Integer id);
}
