/*
 * PfSupplierBankMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-25 Created
 */
package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfSupplierBank;
import java.util.List;

public interface PfSupplierBankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PfSupplierBank record);

    PfSupplierBank selectByPrimaryKey(Integer id);

    PfSupplierBank getDefaultBank();

    List<PfSupplierBank> selectAll();

    int updateByPrimaryKey(PfSupplierBank record);


}