package com.masiis.shop.dao.pbuser;

import com.masiis.shop.dao.pbuser.PbUser;
import com.masiis.shop.dao.pbuser.PbUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PbUserMapper {
    int countByExample(PbUserExample example);

    int deleteByExample(PbUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PbUser record);

    int insertSelective(PbUser record);

    List<PbUser> selectByExample(PbUserExample example);

    PbUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PbUser record, @Param("example") PbUserExample example);

    int updateByExample(@Param("record") PbUser record, @Param("example") PbUserExample example);

    int updateByPrimaryKeySelective(PbUser record);

    int updateByPrimaryKey(PbUser record);
}