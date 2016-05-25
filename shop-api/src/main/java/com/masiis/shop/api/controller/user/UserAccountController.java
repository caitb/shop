package com.masiis.shop.api.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.common.CommonReq;
import com.masiis.shop.api.bean.system.ComBankRes;
import com.masiis.shop.api.bean.user.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.service.system.ComBankService;
import com.masiis.shop.api.service.system.ComDictionaryService;
import com.masiis.shop.api.service.user.ComUserAccountService;
import com.masiis.shop.api.service.user.PfUserBillService;
import com.masiis.shop.api.service.user.UserExtractApplyService;
import com.masiis.shop.api.service.user.UserExtractwayInfoService;
import com.masiis.shop.common.util.CheckBankCardUtil;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.po.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by wangbingjian on 2016/5/5.
 */
@Controller
@RequestMapping(value = "/account")
public class UserAccountController {

    private static final Logger logger = Logger.getLogger(UserAccountController.class);
    @Autowired
    private ComUserAccountService comUserAccountService;
    @Autowired
    private PfUserBillService pfUserBillService;
    @Autowired
    private ComUserAccountService accountService;
    @Autowired
    private UserExtractwayInfoService extractwayInfoService;
    @Autowired
    private ComDictionaryService dictionaryService;
    @Autowired
    private ComBankService comBankService;
    @Autowired
    private UserExtractApplyService applyService;

    /**
     * 每页显示条数
     */
    private static final Integer pageSize = 10;

    /**
     * 我的佣金首页
     * @param req
     * @param comUser
     * @return
     */
    @RequestMapping(value = "/accountHome.do",method = RequestMethod.POST)
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public AccountHomeRes accountHome(HttpServletRequest request, CommonReq req, ComUser comUser){
        logger.info("我的资金首页");
        AccountHomeRes res = new AccountHomeRes();
        logger.info("userId=" + comUser.getId());
        ComUserAccount account = comUserAccountService.findAccountByUserid(comUser.getId());
        String amount = new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
        if (account == null){
            res.setTotalIncomeFee(amount);
            res.setExtractableFee(amount);
            res.setCountingFee(amount);
        }else {
            res.setTotalIncomeFee(account.getTotalIncomeFee() == null?amount:account.getTotalIncomeFee().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            res.setExtractableFee(account.getExtractableFee() == null?amount:account.getExtractableFee().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            res.setCountingFee(account.getCountingFee() == null?amount:account.getCountingFee().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        }
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 按月查询收入记录
     * @param request
     * @param req
     * @param comUser
     * @return
     */
    @RequestMapping(value = "/userBillByDate.do",method = RequestMethod.POST)
    @ResponseBody
    @SignValid(paramType = AccountUserBillReq.class)
    public AccountUserBillRes getUserBillByDate(HttpServletRequest request, AccountUserBillReq req, ComUser comUser){
        logger.info("通过日期查询收入记录");
        AccountUserBillRes res = new AccountUserBillRes();
        String date = req.getDate();
        if (StringUtils.isBlank(date)){
            logger.info("日期为空");
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG + "【日期为空】");
            return res;
        }
        try {
            logger.info("查询月份=" + date);
            List<PfUserBill> userBills = pfUserBillService.findByUserIdLimtPage(comUser.getId(),date,0,0);
            List<AccountUserBill> accountUserBills = new ArrayList<>();
            String amount = new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
            AccountUserBill accountUserBill;
            for (PfUserBill pfUserBill : userBills){
                accountUserBill = new AccountUserBill();
                accountUserBill.setBalanceDate(pfUserBill.getBalanceDate());
                accountUserBill.setBillAmount(pfUserBill.getBillAmount() == null?amount:pfUserBill.getBillAmount().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                accountUserBills.add(accountUserBill);
            }
            res.setUserBills(accountUserBills);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 申请提现首页
     * @param request
     * @param req
     * @param user
     * @return
     */
    @RequestMapping(value = "/withdrawRequestHome.do",method = RequestMethod.POST)
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public WithdrawRes withdrawRequest(HttpServletRequest request, CommonReq req, ComUser user){
        logger.info("进入提现申请首页");
        WithdrawRes res = new WithdrawRes();
        try {
            ComUserAccount account = accountService.findAccountByUserid(user.getId());
            if(account == null){
                logger.error("系统错误,请联系管理员!");
            }
            List<ComUserExtractwayInfo> extractwayInfos = extractwayInfoService.findByUserId(user.getId());
            boolean hasCard = false;
            ComUserExtractwayInfo extractwayInfo = null;
            String extractWay = null;
            if(extractwayInfos != null && extractwayInfos.size() > 0){
                hasCard = true;
                for(ComUserExtractwayInfo info:extractwayInfos){
                    if(info.getIsDefault() == 0){
                        extractwayInfo = info;
                    }
                }
                if(extractwayInfo == null){
                    extractwayInfo = extractwayInfos.get(0);
                }
                ComDictionary cd = dictionaryService.findByCodeAndKey("COM_USER_EXTRACT_WAY", extractwayInfo.getExtractWay().intValue());
                if(cd == null){
                    logger.error("系统错误,字典表没查到提现方式!");
                }
                extractWay = cd.getValue();
            }

            NumberFormat numberFormat = DecimalFormat.getCurrencyInstance(Locale.CHINA);
            String appliedFee = account == null?numberFormat.format(0):account.getAppliedFee() == null?numberFormat.format(0):numberFormat.format(account.getAppliedFee());
            String extractMoney = account == null?numberFormat.format(0):account.getExtractableFee() == null?numberFormat.format(0):numberFormat.format(account.getExtractableFee().subtract(new BigDecimal(appliedFee.replace("￥",""))));
//        if (extractwayInfo != null){
//            String cardCode = extractwayInfo.getBankCard();
//            extractwayInfo.setBankCard(cardCode.substring(0,4)+"*********"+cardCode.substring(cardCode.length()-4,cardCode.length()));
//        }
            res.setHasCard(hasCard);
            if (hasCard){
                res.setId(extractwayInfo.getId());
                res.setBankOwner(extractwayInfo.getCardOwnerName());
                res.setBankName(extractwayInfo.getBankName());
                res.setBankCode(extractwayInfo.getBankCard());
                res.setOpenedBankName(extractwayInfo.getDepositBankName());
            }
            res.setWithdrawWay(extractWay);
            res.setExtractFee(extractMoney);
            res.setAppliedFee(appliedFee);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        logger.info("提现申请首页end....");
        return res;
    }

    /**
     * 校验银行卡号
     * @param request
     * @param req
     * @param user
     * @return
     */
    @RequestMapping(value = "/checkBankCode.do",method = RequestMethod.POST)
    @ResponseBody
    @SignValid(paramType = CheckBankCardReq.class)
    public BaseRes checkBankCard(HttpServletRequest request, CheckBankCardReq req, ComUser user){
        logger.info("校验银行卡号是否正确");
        BaseRes res = new BaseRes();
        if (StringUtils.isBlank(req.getBankCode())){
            logger.info("银行卡号为空");
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG + "【银行卡号为空】");
            logger.info("返回参数：" + JSONObject.toJSONString(res));
            return res;
        }
        String checkBank = CheckBankCardUtil.luhmCheck(req.getBankCode());
        if (!"true".equals(checkBank)){
            logger.info(checkBank);
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG + "【" + checkBank + "】");
            logger.info("返回参数：" + JSONObject.toJSONString(res));
            return res;
        }
        ComUserExtractwayInfo extractway = extractwayInfoService.findByBankcardAndCardownername(req.getBankCode(),req.getBankOwner());
        if (extractway != null){
            logger.info("银行卡号已经存在");
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG + "【银行卡号已经存在】");
            logger.info("返回参数：" + JSONObject.toJSONString(res));
            return res;
        }
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 新增银行卡信息
     * @param request
     * @param req
     * @param user
     * @return
     */
    @RequestMapping(value = "/addBankCard.do",method = RequestMethod.POST)
    @ResponseBody
    @SignValid(paramType = BankAddReq.class)
    public BaseRes addBankCard(HttpServletRequest request, BankAddReq req, ComUser user){
        logger.info("添加银行卡信息");
        BaseRes res = new BaseRes();
        Long userId = user.getId();
        if (req.getBankId() == null){
            logger.info("参数bankid为null");
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG + "【参数bankid为null】");
            return res;
        }
        //根据银行id查询银行基础信息表
        ComBank comBank = comBankService.findById(req.getBankId());
        if (comBank == null){
            logger.info("bankid查询无数据");
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG  + "【bankid查询无数据】");
            return res;
        }
        if (StringUtils.isBlank(req.getBankcard())){
            logger.info("bankcard为空");
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg("银行卡号为空");
            return res;
        }
        String checkBank = CheckBankCardUtil.luhmCheck(req.getBankcard());
        if (!"true".equals(checkBank)){
            logger.info(checkBank);
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg(checkBank);
            logger.info("返回参数：" + JSONObject.toJSONString(res));
            return res;
        }
        if (StringUtils.isBlank(req.getBankOwner())){
            logger.info("持卡人姓名为空");
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG + "【持卡人姓名为空】");
            return res;
        }
        if (StringUtils.isBlank(req.getOpenedBankName())){
            logger.info("开户行名称为空");
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG + "【开户行名称为空】");
            return res;
        }
        try {
            ComUserExtractwayInfo extractway = extractwayInfoService.findByBankcardAndCardownername(req.getBankcard(),req.getBankOwner());
            List<ComUserExtractwayInfo> infos = extractwayInfoService.findByUserId(user.getId());
            if (extractway == null){
                extractway = new ComUserExtractwayInfo();
                extractway.setBankCard(req.getBankcard());
                extractway.setBankName(comBank.getBankName());
                extractway.setDepositBankName(req.getOpenedBankName());
                extractway.setCardOwnerName(req.getBankOwner());
                extractway.setComUserId(userId);
                extractway.setExtractWay(Long.valueOf(3));   //默认体现方式为银行卡提现
                extractway.setCardImg(comBank.getBankImg());
                extractway.setIsEnable(0);//新增用户体现方式，是否启用默认为启用
                if (infos == null || infos.size() == 0){
                    extractway.setIsDefault(0);//设置为提现默认银行卡
                }else {
                    extractway.setIsDefault(1);
                }
                extractway.setChangedBy("add");
                extractway.setCreatedTime(new Date());
                extractway.setChangedTime(new Date());
                extractwayInfoService.addComUserExtractwayInfo(extractway);
            }else {
                //询字典表数据
                ComDictionary comDictionary = dictionaryService.findByCodeAndKey("COM_USER_EXTRACT_WAY",extractway.getExtractWay().intValue());
                logger.info(String.valueOf(comDictionary.getKey()));
                //存在数据并且为未启用状态
                if (extractway.getIsEnable() > 0) {
                    extractway.setBankCard(req.getBankcard());
                    extractway.setBankName(comBank.getBankName());
                    extractway.setDepositBankName(req.getOpenedBankName());
                    extractway.setCardOwnerName(req.getBankOwner());
                    extractway.setComUserId(userId);
                    extractway.setExtractWay(comDictionary.getKey() == null ? 3 : comDictionary.getKey().longValue());
                    extractway.setCardImg(comBank.getBankImg());
                    extractway.setIsEnable(0);//将未启用状态改为启用状态
                    if (infos == null || infos.size() == 0){
                        extractway.setIsDefault(0);//设置为提现默认银行卡
                    }else {
                        extractway.setIsDefault(1);
                    }
                    extractway.setChangedBy("edit");
                    extractway.setChangedTime(new Date());
                    extractwayInfoService.updataComUserExtractwayInfo(extractway);
                }
            }
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 查询银行卡列表
     * @param request
     * @param req
     * @param user
     * @return
     */
    @RequestMapping(value = "/findBanks.do",method = RequestMethod.POST)
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public WithdrawBankRes getBank(HttpServletRequest request, CommonReq req, ComUser user){
        logger.info("查询银行卡列表");
        WithdrawBankRes res = new WithdrawBankRes();
        List<ComUserExtractwayInfo> list;
        List<WithdrawBank> withdrawBanks = new LinkedList<>();
        WithdrawBank withdrawBank;
        try{
            list = extractwayInfoService.findByUserId(user.getId());
            String cardCode;
            for (ComUserExtractwayInfo info : list){
                withdrawBank = new WithdrawBank();
                cardCode = info.getBankCard();
                info.setBankCard(cardCode.substring(0,4)+"*********"+cardCode.substring(cardCode.length()-4,cardCode.length()));
                withdrawBank.setId(info.getId());
                withdrawBank.setBankCode(info.getBankCard());
                withdrawBank.setBankName(info.getBankName());
                withdrawBank.setBankOwner(info.getCardOwnerName());
                withdrawBank.setDefault(info.getIsDefault() == 0?true:false);
                withdrawBanks.add(withdrawBank);
            }
            if (withdrawBanks.size() > 0){
                res.setHasData(true);
                res.setWithdrawBanks(withdrawBanks);
            }
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 删除银行卡信息
     * @param request
     * @param req
     * @param user
     * @return
     */
    @RequestMapping(value = "/delBank.do",method = RequestMethod.POST)
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public BaseRes delBank(HttpServletRequest request, CommonReq req, ComUser user){
        logger.info("删除银行卡信息");
        BaseRes res = new BaseRes();
        logger.info("bankId = " + req.getId());
        try {
            if (extractwayInfoService.deleteById(req.getId()) == 0){
                res.setResCode(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR_MSG);
            }else {
                res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 设置银行卡为默认银行卡
     * @param request
     * @param req
     * @param user
     * @return
     */
    @RequestMapping(value = "/setDefaultBank.do",method = RequestMethod.POST)
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public BaseRes setDefaultBank(HttpServletRequest request, CommonReq req, ComUser user){
        logger.info("设置银行卡为默认银行卡");
        BaseRes res = new BaseRes();
        try {
            List<ComUserExtractwayInfo> list = extractwayInfoService.findByUserId(user.getId());
            for (ComUserExtractwayInfo info:list){
                if (info.getId().longValue() == req.getId().longValue()){
                    if (info.getIsDefault() != 0){
                        info.setIsDefault(0);
                        extractwayInfoService.updataComUserExtractwayInfo(info);
                    }
                }else {
                    if (info.getIsDefault() != 1){
                        info.setIsDefault(1);
                        extractwayInfoService.updataComUserExtractwayInfo(info);
                    }
                }
            }
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 确认申请提现
     * @param request
     * @param req
     * @param user
     * @return
     */
    @RequestMapping(value = "/withdrawConfirm.do",method = RequestMethod.POST)
    @ResponseBody
    @SignValid(paramType = WithdrawConfirmReq.class)
    public BaseRes withdrawConfirm(HttpServletRequest request, WithdrawConfirmReq req, ComUser user){
        logger.info("确认申请提现");
        BaseRes res = new BaseRes();
        BigDecimal exMoney;
        try {
            if(StringUtils.isBlank(req.getMoney())){
                logger.error("提现金额未填写,用户id:" + user.getId());
                res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
                res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG + "【提现金额未填写】");
                logger.info("返回参数：" + JSONObject.toJSONString(res));
                return res;
            }
            if(!SysBeanUtils.isNumeric(req.getMoney())){
                logger.error("金额不是数字类型的值,用户id:" + user.getId());
                res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
                res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG + "【金额不是数字类型的值】");
                logger.info("返回参数：" + JSONObject.toJSONString(res));
                return res;
            }
            // 提现金额
            exMoney = new BigDecimal(req.getMoney());
            // 获取用户资产
            ComUserAccount account = accountService.findAccountByUserid(user.getId());
            if(account == null){
                logger.error("系统错误,用户资产未找到");
                res.setResCode(SysResCodeCons.RES_CODE_REQ_BUSINESS_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_REQ_BUSINESS_ERROR_MSG + "【用户资产未找到】");
                logger.info("返回参数：" + JSONObject.toJSONString(res));
                return res;
            }
            if (exMoney.compareTo(account.getExtractableFee().subtract(account.getAppliedFee() == null?new BigDecimal(0):account.getAppliedFee())) == 1 || exMoney.compareTo(new BigDecimal(0)) <= 0){
                logger.error("申请金额有误");
                res.setResCode(SysResCodeCons.RES_CODE_REQ_BUSINESS_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_REQ_BUSINESS_ERROR_MSG + "【申请金额有误】");
                logger.info("返回参数：" + JSONObject.toJSONString(res));
                return res;
            }
            // 查询默认的支付方式
//            ComUserExtractwayInfo info = extractwayInfoService.findDefaultInfo(user.getId());
            ComUserExtractwayInfo info = extractwayInfoService.findById(req.getId());
            if(info == null){
                res.setResCode(SysResCodeCons.RES_CODE_REQ_BUSINESS_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_REQ_BUSINESS_ERROR_MSG);
                logger.info("返回参数：" + JSONObject.toJSONString(res));
                return res;
            }
            // 开始提现业务
            applyService.applyExtract(account, exMoney, user, info);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 获取银行基础信息列表
     * @param request
     * @param req
     * @param user
     * @return
     */
    @RequestMapping(value = "getComBank.do",method = RequestMethod.POST)
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public ComBankRes getBankList(HttpServletRequest request, CommonReq req, ComUser user){
        logger.info("获取银行信息");
        ComBankRes res = new ComBankRes();
        List<ComBank> comBanks = comBankService.findAll();
        res.setComBanks(comBanks);
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 按月查询提现记录
     * @param request
     * @param req
     * @param user
     * @return
     */
    @RequestMapping(value = "getWithdrawRecordByDate.do",method = RequestMethod.POST)
    @ResponseBody
    @SignValid(paramType = WithdrawRecordReq.class)
    public WithdrawRecordRes getWithdrawRecordByDate(HttpServletRequest request, WithdrawRecordReq req, ComUser user){
        logger.info("按月查询提现记录");
        WithdrawRecordRes res = new WithdrawRecordRes();
        try{
            if(StringUtils.isBlank(req.getDate())
                    || req.getPageNum() == null || req.getPageNum() <= 0){
                res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
                res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG + "【日期或页码有误】");
                logger.info("返回参数：" + JSONObject.toJSONString(res));
                return res;
            }
            Date curtime = null;
            try{
                curtime = DateUtil.String2Date(req.getDate(), "yyyy-MM");
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("时间格式化错误,原时间字符串为:" + req.getDate());
                res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
                res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG + "【时间格式化错误,原时间字符串为" + req.getDate() + "】");
                logger.info("返回参数：" + JSONObject.toJSONString(res));
                return res;
            }
            // 展示提现申请记录的时间区间
            Date start = DateUtil.getFirstTimeInMonth(curtime);
            Date end = DateUtil.getLastTimeInMonth(curtime);
            // 获取时间区间内的总提现数
            Integer count = applyService.findNumsByUserAndDate(user, start, end);
            if(count == null || count <= 0){
                logger.error("当前时间区间内没有数据");
                res.setPageSize(0);
                res.setTotalCount(0);
                res.setTotalPage(0);
                res.setHasQueryData(0);
                res.setLast(true);
                res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
                logger.info("返回参数：" + JSONObject.toJSONString(res));
                return res;
            }
            logger.info("获取时间区间内的总提现数,总记录数:" + count);
            // 获取总页数
            Integer pageNums = count%pageSize == 0 ? count/pageSize : count/pageSize + 1;
            Integer startNum = (req.getPageNum() - 1) * pageSize;
            Integer qSize = pageSize;
            if(req.getPageNum() > pageNums){
                logger.error("当前所传页码超过总页码数,总页数:" + pageNums + ",所传页码数:" + req.getPageNum());
                res.setLast(true);
                res.setResCode(SysResCodeCons.RES_CODE_REQ_BUSINESS_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_REQ_BUSINESS_ERROR_MSG + "【所传页码超过总页数】");
                logger.info("返回参数：" + JSONObject.toJSONString(res));
                return res;
            }

            if(req.getPageNum() == pageNums){
                qSize = count - startNum;
                res.setLast(true);
            }
            List<ComUserExtractApply> list = applyService.findListByUserAndDateAndPageNum(user, start, end, startNum, qSize);

            List<WithdrawRecord> records = new LinkedList<>();
            WithdrawRecord record;
            NumberFormat numberFormat = DecimalFormat.getCurrencyInstance(Locale.CHINA);
            for (ComUserExtractApply apply : list){
                record = new WithdrawRecord();
                record.setDate(apply.getApplyTimeView());
                record.setBankCard(apply.getBankCardView());
                record.setAmount(numberFormat.format(apply.getExtractFee()));
                record.setBankName(apply.getBankName());
                record.setStatus(apply.getAuditType() == 0?"待审核":apply.getAuditType() == 1?"已拒绝":apply.getAuditType() == 2?"待打款":"已付款");
                records.add(record);
            }
            res.setCurrentPage(startNum);
            res.setHasQueryData(1);
            res.setTotalCount(count);
            res.setTotalPage(pageNums);
            res.setPageSize(pageSize);
            res.setRecords(records);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }

    public static void main(String[] args){
        NumberFormat numberFormat = DecimalFormat.getCurrencyInstance(Locale.CHINA);
        System.out.println(numberFormat.format(new BigDecimal(100)));
    }
}
