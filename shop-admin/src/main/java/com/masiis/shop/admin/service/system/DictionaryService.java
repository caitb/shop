package com.masiis.shop.admin.service.system;

import com.masiis.shop.dao.platform.system.ComDictionarysMapper;
import com.masiis.shop.dao.po.ComDictionary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by JingHao on 2016/5/30 0030.
 */
@Service
public class DictionaryService {

    @Resource
    private ComDictionarysMapper comDictionarysMapper;

    /**
      * @Author jjh
      * @Date 2016/5/30 0030 下午 4:06
      * 根据code查询字典表对象
      */
     public List<ComDictionary> pickListOfBaseData(String code) throws Exception{
         return  comDictionarysMapper.PickListByCode(code);
     }
}
