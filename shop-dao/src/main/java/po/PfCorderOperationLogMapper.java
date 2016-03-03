/*
 * PfCorderOperationLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.PfCorderOperationLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PfCorderOperationLogMapper {
    @Delete({
        "delete from pf_corder_operation_log",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into pf_corder_operation_log (id, create_time, ",
        "create_man, pf_corder_id, ",
        "pf_corder_status, remark)",
        "values (#{id,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createMan,jdbcType=BIGINT}, #{pfCorderId,jdbcType=BIGINT}, ",
        "#{pfCorderStatus,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(PfCorderOperationLog record);

    int insertSelective(PfCorderOperationLog record);

    @Select({
        "select",
        "id, create_time, create_man, pf_corder_id, pf_corder_status, remark",
        "from pf_corder_operation_log",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    PfCorderOperationLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PfCorderOperationLog record);

    @Update({
        "update pf_corder_operation_log",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_man = #{createMan,jdbcType=BIGINT},",
          "pf_corder_id = #{pfCorderId,jdbcType=BIGINT},",
          "pf_corder_status = #{pfCorderStatus,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PfCorderOperationLog record);
}