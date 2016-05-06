package com.masiis.shop.api.service.user;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.dao.platform.user.PfUserBillMapper;
import com.masiis.shop.dao.po.PfUserBill;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangbingjian on 2016/3/23.
 */
@Service
@Transactional
public class PfUserBillService {
    @Resource
    private PfUserBillMapper pfUserBillMapper;

    /**
     * 根据userId查询用户账单表
     * @param userId        用户id
     * @param currentMonth  当前月
     * @param currentPage   查询当前月
     * @param pageSize      页面显示数量
     * @return
     */
    public List<PfUserBill> findByUserIdLimtPage(Long userId,String currentMonth,int currentPage,int pageSize){
        if (currentPage == 0||pageSize == 0){
            return pfUserBillMapper.selectByUserIdLimitPage(userId,currentMonth);
        }
        PageHelper.startPage(currentPage,pageSize);
        return pfUserBillMapper.selectByUserIdLimitPage(userId,currentMonth);
    }
}
