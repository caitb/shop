package com.masiis.shop.web.platform.controller.user;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.dao.po.ComDictionary;
import com.masiis.shop.dao.po.ComUserExtractwayInfo;
import com.masiis.shop.web.platform.service.system.ComDictionaryService;
import com.masiis.shop.web.platform.service.user.UserExtractwayInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by wbj on 2016/3/18.
 */
@Controller
@RequestMapping(value = "/extractwayinfo")
public class UserExtractwayInfoController {

    private final static Log log = LogFactory.getLog(UserExtractwayInfoController.class);

    @Resource
    private ComDictionaryService comDictionaryService;
    @Resource
    private UserExtractwayInfoService userExtractwayInfoService;
    /**
     * 新增用户提现方式信息
     * @param bankcard          银行卡号
     * @param bankname          银行名称
     * @param depositbankname   开户行名称
     * @param cardownername     持卡人姓名
     * @param comuserid         用户id
     * @return
     */
    @RequestMapping(value = "/add.do",method = RequestMethod.POST)
    public String userExtractwayInfoAdd(@RequestParam(value = "bankcard",required = true) String bankcard,
                                        @RequestParam(value = "bankname",required = true) String bankname,
                                        @RequestParam(value = "depositbankname",required = true) String depositbankname,
                                        @RequestParam(value = "cardownername",required = true) String cardownername,
                                        @RequestParam(value = "comuserid",required = true) String comuserid ){

        log.info("bankcard:"+bankcard);
        log.info("bankname:"+bankname);
        log.info("depositbankname:"+depositbankname);
        log.info("cardownername:"+cardownername);
        log.info("comuserid:"+comuserid);
        JSONObject jsonobject = new JSONObject();
        try{
            if (StringUtils.isBlank(bankcard)){
                jsonobject.put("isTrue","false");
                jsonobject.put("message","新增用户提现方式信息【银行卡号不能为空】");
                log.info(jsonobject.toJSONString());
                return jsonobject.toJSONString();
            }
            if (StringUtils.isBlank(bankname)){
                jsonobject.put("isTrue","false");
                jsonobject.put("message","新增用户提现方式信息【银行名称不能为空】");
                log.info(jsonobject.toJSONString());
                return jsonobject.toJSONString();
            }
            if (StringUtils.isBlank(depositbankname)){
                jsonobject.put("isTrue","false");
                jsonobject.put("message","新增用户提现方式信息【开户行名称不能为空】");
                log.info(jsonobject.toJSONString());
                return jsonobject.toJSONString();
            }
            if (StringUtils.isBlank(cardownername)){
                jsonobject.put("isTrue","false");
                jsonobject.put("message","新增用户提现方式信息【持卡人姓名不能为空】");
                log.info(jsonobject.toJSONString());
                return jsonobject.toJSONString();
            }
            if (StringUtils.isBlank(comuserid)){
                jsonobject.put("isTrue","false");
                jsonobject.put("message","新增用户提现方式信息【用户id不能为空】");
                log.info(jsonobject.toJSONString());
                return jsonobject.toJSONString();
            }
            //根据id查询字典表数据
            ComDictionary comDictionary = comDictionaryService.findById(35);
            log.info(String.valueOf(comDictionary.getKey()));
            ComUserExtractwayInfo extractway = userExtractwayInfoService.ByBankcardAndCardownernamefind(bankcard,cardownername);
            if (extractway == null){
                extractway = new ComUserExtractwayInfo();
                extractway.setBankCard(bankcard);
                extractway.setBankName(bankname);
                extractway.setDepositBankName(depositbankname);
                extractway.setCardOwnerName(cardownername);
                extractway.setComUserId(Long.valueOf(comuserid));
                extractway.setExtractWay(comDictionary.getKey()==null?1:comDictionary.getKey().longValue());
                extractway.setIsEnable(0);//新增用户体现方式，是否启用默认为启用
                extractway.setChangedBy("add");
                extractway.setCreatedTime(new Date());
                extractway.setChangedTime(new Date());
                userExtractwayInfoService.addComUserExtractwayInfo(extractway);
            }else {
                //存在数据并且为未启用状态
                if (extractway.getIsEnable() > 0) {
                    extractway.setBankCard(bankcard);
                    extractway.setBankName(bankname);
                    extractway.setDepositBankName(depositbankname);
                    extractway.setCardOwnerName(cardownername);
                    extractway.setComUserId(Long.valueOf(comuserid));
                    extractway.setExtractWay(comDictionary.getKey() == null ? 1 : comDictionary.getKey().longValue());
                    extractway.setIsEnable(0);//将未启用状态改为启用状态
                    extractway.setChangedBy("edit");
                    extractway.setChangedTime(new Date());
                    userExtractwayInfoService.updataComUserExtractwayInfo(extractway);
                }else {
                    jsonobject.put("isTrue","false");
                    jsonobject.put("message","银行卡号已经存在");
                    log.info(jsonobject.toJSONString());
                    return jsonobject.toJSONString();
                }
            }

            jsonobject.put("isTrue","true");
        }catch (Exception e){
            jsonobject.put("isTrue","false");
            jsonobject.put("message",e.getMessage());
            e.printStackTrace();
        }
        log.info(jsonobject.toJSONString());
        return jsonobject.toJSONString();
    }
}

