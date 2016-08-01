package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.common.enums.promotion.SfTurnTableStatusEnum;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.beans.promotion.TurnTableGiftInfo;
import com.masiis.shop.dao.beans.promotion.TurnTablelInfo;
import com.masiis.shop.dao.po.SfTurnTable;
import com.masiis.shop.web.promotion.cpromotion.service.gorder.SfTurnTableGiftService;
import com.masiis.shop.web.promotion.cpromotion.service.gorder.SfTurnTableService;
import org.springframework.mail.MailParseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzz on 2016/8/1.
 */
@Service
public class TurnTableDetailShowService {

    @Resource
    private SfTurnTableService turnTableService;
    @Resource
    private SfTurnTableGiftService turnTableGiftService;

    public List<TurnTablelInfo> getTurnTableInfo(){
        //查询所有进行中的转盘
        List<SfTurnTable> turnTables = turnTableService.getTurnTableByStatus(SfTurnTableStatusEnum.ING.getCode());
        List<TurnTablelInfo> turnTablelInfos = new ArrayList<>();
        for (SfTurnTable turnTable:turnTables){
            TurnTablelInfo turnTablelInfo = new TurnTablelInfo();
            List<TurnTableGiftInfo> turnTableGiftInfos =  turnTableGiftService.getTurnTableGiftsByTableId(turnTable.getId());
            turnTablelInfo.setTurnTableGiftInfo(turnTableGiftInfos);
            turnTablelInfo.setTurnTableId(turnTable.getId());
            turnTablelInfo.setBeginTimeString(DateUtil.Date2String(turnTable.getBeginTime(),DateUtil.CHINESE_YEAR_MONTH_DATE_FMT));
            turnTablelInfo.setEndTimeString(DateUtil.Date2String(turnTable.getEndTime(),DateUtil.CHINESE_YEAR_MONTH_DATE_FMT));
            turnTablelInfo.setDescribe(turnTable.getDescribe());
            turnTablelInfos.add(turnTablelInfo);
        }
        return turnTablelInfos;
    }

    public int getRandomByGiftRate(Integer turnTableId){
        //获取转盘中的奖品
        List<TurnTableGiftInfo> turnTableGiftInfos =  turnTableGiftService.getTurnTableGiftsByTableId(turnTableId);
        Map<Integer,Double> map = new LinkedHashMap<>();
        for (TurnTableGiftInfo turnTableGiftInfo: turnTableGiftInfos){
                map.put(turnTableGiftInfo.getSort(),Double.parseDouble(turnTableGiftInfo.getProbability()+""));
        }
        return 0;
    }

}
