package com.masiis.shop.api.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.po.PfCorder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzz on 2016/3/16.
 */
@Service
@Transactional
public class PfCorderService {

    @Resource
    private PfCorderMapper pfCorderMapper;


    public PfCorder getPfCorderById(Long id){
        return pfCorderMapper.selectById(id);
    }

    /**
     * 根据用户的id和skuId查询用户试用过的产品
     * @param pfCorder
     * @return
     */
    public List<PfCorder> trialCorder(PfCorder pfCorder)throws Exception{
        List<PfCorder> pfCorders = new ArrayList<PfCorder>();
        try{
            pfCorders = pfCorderMapper.trialCorder(pfCorder);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        return  pfCorders;
    }

    /**
     * 查询试用未支付的订单
     * @author hanzengzhi
     * @date 2016/3/21 16:08
     */
    List<PfCorder> queryTrialNoPayOrder(Long userId,Integer skuId){
        List<PfCorder> pfCorders = null;
        try{
            PfCorder pfCorder = new PfCorder();
            pfCorder.setUserId(userId);
            pfCorder.setSkuId(skuId);
            pfCorders = pfCorderMapper.queryTrialNoPayOrder(pfCorder);
        }catch (Exception e){
            throw  new BusinessException(e.getMessage());
        }
        return pfCorders;
    }

    public int updateUserMessageById(PfCorder pfCorder){
        return pfCorderMapper.updateUserMessageById(pfCorder);
    }
}
