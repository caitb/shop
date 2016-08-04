package com.masiis.shop.admin.service.gift;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.dao.platform.gift.ComGiftMapper;
import com.masiis.shop.dao.po.ComGift;
import com.masiis.shop.dao.po.PbUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 奖品　service
 */
@Service
public class ComGiftService {

    @Resource
    private ComGiftMapper comGiftMapper;


    public ComGift getComGiftById(Integer id){
        return comGiftMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存或更新　ComGift
     */
    public ComGift saveOrUpdate(ComGift comGift, PbUser pbUser) {
        if(comGift == null || pbUser ==null) {
            return null;
        }

        if( comGift.getId() == null) {
            comGift.setCreateMan(pbUser.getId());
            comGift.setCreateTime(new Date());
            comGift.setStatus(1);
            comGift.setIsGift(comGift.getIsGift());
            comGiftMapper.insert(comGift);
            comGift = comGiftMapper.selectByImgUrl(comGift.getImgUrl());
        } else {
            comGift.setModifyMan(pbUser.getId());
            comGift.setModifyTime(new Date());
            comGiftMapper.updateByPrimaryKey(comGift);
        }

        return comGift;
    }

    /**
     * 按条件分页查询　ComGift
     */
    public List<ComGift> listByCondition(Integer pageNumber, Integer pageSize, Map<String,Object> conditionMap) {
        PageHelper.startPage(pageNumber, pageSize, "create_time desc");
        List<ComGift> giftList = comGiftMapper.selectByMap(conditionMap);
        return giftList;
    }
}
