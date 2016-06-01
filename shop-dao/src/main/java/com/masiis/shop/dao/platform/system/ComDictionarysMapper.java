package com.masiis.shop.dao.platform.system;

import com.masiis.shop.dao.po.ComDictionary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    List<ComDictionary> PickListByCode(@Param("code")String code);
}
