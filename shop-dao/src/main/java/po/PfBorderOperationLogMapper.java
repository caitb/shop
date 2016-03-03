/*
 * PfBorderOperationLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.PfBorderOperationLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PfBorderOperationLogMapper {
    @Delete({
        "delete from pf_border_operation_log",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pf_border_operation_log (id, create_time, ",
        "create_man, pf_border_id, ",
        "pf_border_status, remark)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createMan,jdbcType=BIGINT}, #{pfBorderId,jdbcType=BIGINT}, ",
        "#{pfBorderStatus,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(PfBorderOperationLog record);

    int insertSelective(PfBorderOperationLog record);

    @Select({
        "select",
        "id, create_time, create_man, pf_border_id, pf_border_status, remark",
        "from pf_border_operation_log",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PfBorderOperationLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PfBorderOperationLog record);

    @Update({
        "update pf_border_operation_log",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_man = #{createMan,jdbcType=BIGINT},",
          "pf_border_id = #{pfBorderId,jdbcType=BIGINT},",
          "pf_border_status = #{pfBorderStatus,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PfBorderOperationLog record);
}