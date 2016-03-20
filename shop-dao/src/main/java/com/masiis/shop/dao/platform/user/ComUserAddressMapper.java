/*
 * ComUserAddressMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.ComCategory;
import com.masiis.shop.dao.po.ComUserAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ComUserAddressMapper {
    int deleteByPrimaryKey(Long id);

    int settingDefaultAddress(Long id);

    int cancelDefaultAddress(Long userId);

    int insert(ComUserAddress record);

    ComUserAddress selectByPrimaryKey(Long id);

    List<ComUserAddress> selectAll();

    int updateByPrimaryKey(ComUserAddress record);

    List<ComUserAddress> selectAllByComUserId(Long ComUserId);

    List<ComUserAddress> queryComUserAddressesByParam(@Param("comUserAddress")ComUserAddress comUserAddress);
}