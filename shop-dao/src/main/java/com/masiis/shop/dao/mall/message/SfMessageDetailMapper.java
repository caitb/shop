package com.masiis.shop.dao.mall.message;

import com.masiis.shop.dao.beans.message.SfMessageDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date 2016/7/5
 * @Author lzh
 */
@Repository
public interface SfMessageDetailMapper {
    SfMessageDetail selectByPrimaryKey(Long id);

    List<SfMessageDetail> selectAll();

    int updateByPrimaryKey(SfMessageDetail record);
}
