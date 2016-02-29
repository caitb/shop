package com.masiis.shop.dao.pbmenu;

import com.masiis.shop.dao.pbmenu.PbMenu;
import com.masiis.shop.dao.pbmenu.PbMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PbMenuMapper {
    int countByExample(PbMenuExample example);

    int deleteByExample(PbMenuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PbMenu record);

    int insertSelective(PbMenu record);

    List<PbMenu> selectByExample(PbMenuExample example);

    PbMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PbMenu record, @Param("example") PbMenuExample example);

    int updateByExample(@Param("record") PbMenu record, @Param("example") PbMenuExample example);

    int updateByPrimaryKeySelective(PbMenu record);

    int updateByPrimaryKey(PbMenu record);
}