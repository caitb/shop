package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.ComUserExtractwayInfoMapper;
import com.masiis.shop.dao.po.ComUserExtractwayInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangbingjian on 2016/3/18.
 */
@Service
@Transactional
public class UserExtractwayInfoService {

    @Resource
    private ComUserExtractwayInfoMapper comUserExtractwayInfoMapper;

    /**
     * 根据id查询ComUserExtractwayInfo
     * @param id
     * @return
     */
    public ComUserExtractwayInfo findById(Long id){
        ComUserExtractwayInfo comUserExtractwayInfo = comUserExtractwayInfoMapper.selectByPrimaryKey(id);
        return comUserExtractwayInfo;
    }

    /**
     * 根据id删除ComUserExtractwayInfo
     * @param id
     * @return
     */
    public int deleteById(Long id){
        return comUserExtractwayInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增用户提现方式信息
     * @param comUserExtractwayInfo
     * @return
     */
    public int addComUserExtractwayInfo(ComUserExtractwayInfo comUserExtractwayInfo){
        return comUserExtractwayInfoMapper.insert(comUserExtractwayInfo);
    }

    /**
     * 通过银行卡号和持卡人姓名查询
     * @param bankCard         银行卡号
     * @param cardOwnerName    持卡人姓名
     * @return
     */
    public ComUserExtractwayInfo ByBankcardAndCardownernamefind(String bankCard,String cardOwnerName){
        ComUserExtractwayInfo comUserExtractwayInfo = new ComUserExtractwayInfo();
        comUserExtractwayInfo.setBankCard(bankCard);
        comUserExtractwayInfo.setCardOwnerName(cardOwnerName);
        return comUserExtractwayInfoMapper.selectByBankcardAndCardownername(comUserExtractwayInfo);
    }

    public int updataComUserExtractwayInfo(ComUserExtractwayInfo comUserExtractwayInfo){
        return comUserExtractwayInfoMapper.updateByPrimaryKey(comUserExtractwayInfo);
    }

    /**
     * 查询该用户的提现方式信息
     *
     * @param id
     * @return
     */
    public List<ComUserExtractwayInfo> findByUserid(Long id) {
        return comUserExtractwayInfoMapper.selectByUserid(id);
    }
}
