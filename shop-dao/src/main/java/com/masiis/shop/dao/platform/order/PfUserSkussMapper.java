package com.masiis.shop.dao.platform.order;
import com.masiis.shop.dao.po.PfUserSku;
import org.springframework.stereotype.Repository;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */

@Repository
public interface PfUserSkussMapper {
    /**
     * 通过订单id查用户商品关系
     * @author muchaofeng
     * @date 2016/3/9 18:04
     */

    PfUserSku selectPfUserSkusById(Long pfCorderId);

}