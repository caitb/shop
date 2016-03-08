package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.ComArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hzzh on 2016/3/7.
 */
public interface ComAreaMapper {

    List<ComArea> queryComAreasByParam(@Param("comArea")ComArea comArea);
}
