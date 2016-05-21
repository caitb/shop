package com.masiis.shop.api.service.order;

import com.masiis.shop.dao.platform.order.PfCorderConsigneeMapper;
import com.masiis.shop.dao.po.PfCorderConsignee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/3/17.
 */
@Service
@Transactional
public class PfCorderConsigneeService {
    @Resource
    private PfCorderConsigneeMapper pfCorderConsigneeMapper;

    public void insertPfCC(PfCorderConsignee pfCorderConsignee)throws Exception{
        try {
            pfCorderConsigneeMapper.insert(pfCorderConsignee);
        }catch (Exception e){
            throw new Exception("试用申请订单插入收获地址失败"+e);
        }
    }
}
