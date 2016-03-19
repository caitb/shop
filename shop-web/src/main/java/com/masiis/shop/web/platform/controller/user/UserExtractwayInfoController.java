package com.masiis.shop.web.platform.controller.user;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.dao.po.ComBank;
import com.masiis.shop.dao.po.ComDictionary;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserExtractwayInfo;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.system.ComBankService;
import com.masiis.shop.web.platform.service.system.ComDictionaryService;
import com.masiis.shop.web.platform.service.user.UserExtractwayInfoService;
import com.masiis.shop.web.platform.service.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by wbj on 2016/3/18.
 */
@Controller
@RequestMapping(value = "/extractwayinfo")
public class UserExtractwayInfoController extends BaseController {

    private final static Log log = LogFactory.getLog(UserExtractwayInfoController.class);

    @Resource
    private ComDictionaryService comDictionaryService;
    @Resource
    private UserExtractwayInfoService userExtractwayInfoService;
    @Resource
    private UserService userService;
    @Resource
    private ComBankService comBankService;
    /**
     * 新增用户提现方式信息
     * @param bankcard          银行卡号
     * @param bankid            银行id
     * @param depositbankname   开户行名称
     * @param cardownername     持卡人姓名
     * @return
     */
    @RequestMapping(value = "/add.do",method = RequestMethod.POST)
    @ResponseBody
    public String userExtractwayInfoAdd(@RequestParam(value = "bankcard",required = true) String bankcard,
                                        @RequestParam(value = "bankid",required = true) String bankid,
                                        @RequestParam(value = "depositbankname",required = true) String depositbankname,
                                        @RequestParam(value = "cardownername",required = true) String cardownername,
                                        HttpServletRequest request){

        log.info("bankcard:"+bankcard);
        log.info("bankid:"+bankid);
        log.info("depositbankname:"+depositbankname);
        log.info("cardownername:"+cardownername);

        ComUser user = getComUser(request);
        JSONObject jsonobject = new JSONObject();
        try{
            if (StringUtils.isBlank(bankcard)){
                jsonobject.put("isTrue","false");
                jsonobject.put("message","新增用户提现方式信息【银行卡号不能为空】");
                log.info(jsonobject.toJSONString());
                return jsonobject.toJSONString();
            }
            if (StringUtils.isBlank(bankid)){
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
            if (user == null){
//                jsonobject.put("isTrue","false");
//                jsonobject.put("message","新增用户提现方式信息【腥增前请登陆】");
//                log.info(jsonobject.toJSONString());
//                return jsonobject.toJSONString();
//                user = userService.getUserByOpenid("oUIwkwgLzn8CKMDrvbCSE3T-u5fs");
            }
//            Long userId = user.getId();
            Long userId = Long.valueOf(1);
            //根据id查询字典表数据
            ComDictionary comDictionary = comDictionaryService.findById(35);
            //根据银行id查询银行基础信息表
            ComBank comBank = comBankService.findById(Integer.valueOf(bankid));
            log.info(String.valueOf(comDictionary.getKey()));
            ComUserExtractwayInfo extractway = userExtractwayInfoService.findByBankcardAndCardownername(bankcard,cardownername);
            if (extractway == null){
                extractway = new ComUserExtractwayInfo();
                extractway.setBankCard(bankcard);
                extractway.setBankName(comBank.getBankName());
                extractway.setDepositBankName(depositbankname);
                extractway.setCardOwnerName(cardownername);
                extractway.setComUserId(Long.valueOf(userId));
                extractway.setExtractWay(comDictionary.getKey()==null?1:comDictionary.getKey().longValue());
                extractway.setCardImg(comBank.getBankImg());
                extractway.setIsEnable(0);//新增用户体现方式，是否启用默认为启用
                extractway.setIsDefault(0);//设置为提现默认银行卡
                extractway.setChangedBy("add");
                extractway.setCreatedTime(new Date());
                extractway.setChangedTime(new Date());
                userExtractwayInfoService.addComUserExtractwayInfo(extractway);
            }else {
                //存在数据并且为未启用状态
                if (extractway.getIsEnable() > 0) {
                    extractway.setBankCard(bankcard);
                    extractway.setBankName(comBank.getBankName());
                    extractway.setDepositBankName(depositbankname);
                    extractway.setCardOwnerName(cardownername);
                    extractway.setComUserId(Long.valueOf(userId));
                    extractway.setExtractWay(comDictionary.getKey() == null ? 1 : comDictionary.getKey().longValue());
                    extractway.setCardImg(comBank.getBankImg());
                    extractway.setIsEnable(0);//将未启用状态改为启用状态
                    extractway.setIsDefault(0);//设置为提现默认银行卡
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

    /**
     * 通过userId查询已绑定卡片信息
     * @return
     */
    @RequestMapping(value = "/findByUserId.do")
    @ResponseBody
    public ModelAndView findByUserId(HttpServletRequest request){
        log.info("通过userId查询已绑定卡片信息");

        ComUser user = getComUser(request);
        ModelAndView mv = new ModelAndView();
//        if (user == null){
//            user = userService.getUserByOpenid("oUIwkwgLzn8CKMDrvbCSE3T-u5fs");
//        }
//        Long userId = user.getId();
        Long userId = Long.valueOf(1);
        List<ComUserExtractwayInfo> list;
        try{
            list = userExtractwayInfoService.findByUserId(userId);
            mv.addObject("userId",userId);
            mv.addObject("extractwayList",list);
        }catch (Exception e){
            e.printStackTrace();
        }
        mv.setViewName("platform/user/bankcardSelect");
        return mv;
    }

    /**
     * 跳转至新增银行卡页面
     * @return
     */
    @RequestMapping(value = "/toCreateBankcard.do")
    @ResponseBody
    public ModelAndView toBankCardCreate(HttpServletRequest request){

        log.info("准备跳转至新增银行卡页面");
        ComUser user = getComUser(request);
//        if (user == null){
//            user = userService.getUserByOpenid("oUIwkwgLzn8CKMDrvbCSE3T-u5fs");
//        }
//        Long userId = user.getId();
//        log.info("userId="+userId);
        Long userId = Long.valueOf(1);
        ModelAndView mv = new ModelAndView();
        List<ComBank> list = null;
        try{
            list = comBankService.findAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        mv.addObject("userId",userId);
        mv.addObject("bankList",list);
        mv.setViewName("platform/user/bankcardCreate");
        return mv;
    }

    /**
     * 设置银行卡为支付默认卡号
     * @param userId
     * @param id
     * @return
     */
    @RequestMapping(value = "/setbankdefault.do")
    @ResponseBody
    public String setBankDefault(@RequestParam(value = "userId",required = true) String userId ,
                                 @RequestParam(value = "id",required = true) String id){
        log.info("userId:"+userId);
        log.info("id:"+id);
        List<ComUserExtractwayInfo> list = userExtractwayInfoService.findByUserId(Long.valueOf(userId));
        JSONObject jsonobject = new JSONObject();
        try{
            for (ComUserExtractwayInfo info:list){
                if (info.getId()==Long.valueOf(id)){
                    if (info.getIsDefault() != 0){
                        info.setIsDefault(0);
                        userExtractwayInfoService.updataComUserExtractwayInfo(info);
                    }
                }else {
                    if (info.getIsDefault() != 1){
                        info.setIsDefault(1);
                        userExtractwayInfoService.updataComUserExtractwayInfo(info);
                    }
                }
            }
            jsonobject.put("isTrue","true");
        }catch (Exception e){
            e.printStackTrace();
            jsonobject.put("isTrue","false");
            jsonobject.put("message","服务忙请稍后。。。");
        }
        return jsonobject.toJSONString();
    }
}

