package com.masiis.shop.admin.service.turn;

import com.alibaba.druid.sql.ast.statement.SQLGrantStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.util.TurnTableMakeUtils;
import com.masiis.shop.dao.mall.promotion.SfTurnTableGiftMapper;
import com.masiis.shop.dao.mall.promotion.SfTurnTableMapper;
import com.masiis.shop.dao.po.PbUser;
import com.masiis.shop.dao.po.SfTurnTable;
import com.masiis.shop.dao.po.SfTurnTableGift;
import com.masiis.shop.dao.po.SfUserPromotion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SfTurnTableService {

    private final static Log log = LogFactory.getLog(SfTurnTableService.class);

    @Resource
    private SfTurnTableMapper turnTableMapper;
    @Resource
    private SfTurnTableGiftMapper turnTableGiftMapper;

    public List<SfTurnTableGift> createTurnTableGifts(
            Integer[] idArray,
            Integer[] sortArray,
            Integer[] giftIdArray,
            Integer[] probabilityArray,
            Integer[] totalQuantityArray) {

        // 检查参数长度是否一致
        int size = sortArray.length;
        if(giftIdArray.length != size || probabilityArray.length != size || totalQuantityArray.length != size) {
            log.debug("参数长度不一致："+ sortArray.length+", "+
                    giftIdArray.length+", "+
                    probabilityArray.length+", "+
                    totalQuantityArray.length
            );
        }

        List<SfTurnTableGift> giftList = new LinkedList<>();
        for(int i=0; i<size; i++) {
            Integer id = null;
            if(idArray != null && idArray.length>i) {
                id = idArray[i];
            }
            SfTurnTableGift turnTableGift = new SfTurnTableGift(id, sortArray[i], giftIdArray[i], probabilityArray[i], totalQuantityArray[i]);
            giftList.add(turnTableGift);
        }

        return giftList;
    }

    public void save(SfTurnTable turnTable, List<SfTurnTableGift> gifts, PbUser pbUser) {
        if(turnTable.getId() == null) {
            insert(turnTable, gifts, pbUser);
        } else {
            update(turnTable, gifts, pbUser);
        }
    }

    public void insert(SfTurnTable turnTable, List<SfTurnTableGift> gifts, PbUser pbUser) {
        turnTable.setCreateMan(pbUser.getId());
        turnTable.setCreateTime(new Date());
        turnTable.setCode(TurnTableMakeUtils.makeCode());
        turnTable.setStatus(0);

        turnTableMapper.insert(turnTable);
        for(SfTurnTableGift gift :gifts) {
            gift.setTurnTableId(turnTable.getId());
            gift.setQuantity(1);
            gift.setGiftedQuantity(0);
            gift.setStatus(0);
            turnTableGiftMapper.insert(gift);
        }
    }

    public void update(SfTurnTable turnTable, List<SfTurnTableGift> gifts, PbUser pbUser) {
        turnTable.setModifyMan(pbUser.getId());
        turnTable.setModifyTime(new Date());

        turnTableMapper.update(turnTable);
        for(SfTurnTableGift gift : gifts) {
            turnTableGiftMapper.update(gift);
        }
    }

    public Object getTurnTable(Integer id) {
        SfTurnTable turnTable = turnTableMapper.selectByPrimaryKey(id);
        List<SfTurnTableGift> giftList = turnTableGiftMapper.listByTurnTableId(id);

        Map<String,Object> data = new HashMap<>();
        data.put("turnTable", turnTable);
        data.put("turnTableGifts", giftList);

        return data;
    }

    public List<SfTurnTable> listByCondition(Integer pageNumber, Integer pageSize, Map<String,Object> conditionMap) {
        PageHelper.startPage(pageNumber, pageSize, "create_time desc");
        return turnTableMapper.selectByCondition(conditionMap);
    }

}
