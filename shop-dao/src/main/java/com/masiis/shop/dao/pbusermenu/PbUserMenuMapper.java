package com.masiis.shop.dao.pbusermenu;

import com.masiis.shop.dao.pbusermenu.PbUserMenu;
import com.masiis.shop.dao.pbusermenu.PbUserMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PbUserMenuMapper {
    int countByExample(PbUserMenuExample example);

    int deleteByExample(PbUserMenuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PbUserMenu record);

    int insertSelective(PbUserMenu record);

    List<PbUserMenu> selectByExample(PbUserMenuExample example);

    PbUserMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PbUserMenu record, @Param("example") PbUserMenuExample example);

    int updateByExample(@Param("record") PbUserMenu record, @Param("example") PbUserMenuExample example);

    int updateByPrimaryKeySelective(PbUserMenu record);

    int updateByPrimaryKey(PbUserMenu record);
}