package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.beans.user.PfIncomRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by wangbingjian on 2016/7/28.
 */
public interface IncomRecord14Mapper {

    List<PfIncomRecord> selectPfIncomRecords(@Param("userId") Long userId,
                                             @Param("firstDate") Date firstDate,
                                             @Param("lastDate") Date lastDate,
                                             @Param("flag") Integer flag,
                                             @Param("personUserId") Long personUserId);

    BigDecimal selectSumPfIncom(@Param("userId") Long userId,
                                @Param("firstDate") Date firstDate,
                                @Param("lastDate") Date lastDate,
                                @Param("flag") Integer flag,
                                @Param("personUserId") Long personUserId);

}
