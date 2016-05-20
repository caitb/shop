package com.masiis.shop.api.service.system;

import com.masiis.shop.dao.platform.system.ComBankMapper;
import com.masiis.shop.dao.po.ComBank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangbingjian on 2016/3/19.
 */
@Service
@Transactional
public class ComBankService {

    @Resource
    private ComBankMapper comBankMapper;

    /**
     * 查询所有银行信息
     * @return
     */
    public List<ComBank> findAll(){
        return comBankMapper.selectAll();
    }

    /**
     * 根据id查询银行信息
     * @param id
     * @return
     */
    public ComBank findById(Integer id){
        return comBankMapper.selectByPrimaryKey(id);
    }
}
