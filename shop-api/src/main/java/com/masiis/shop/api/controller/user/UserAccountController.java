package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.common.CommonReq;
import com.masiis.shop.api.bean.user.AccountHomeRes;
import com.masiis.shop.api.bean.user.AccountUserBill;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.service.user.ComUserAccountService;
import com.masiis.shop.api.service.user.PfUserBillService;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.PfUserBill;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    /**
     * 我的佣金首页
     * @param req
     * @param comUser
     * @return
     */
    @RequestMapping(value = "/accountHome.do")
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
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthString = "";
        if (month<10){
            monthString = "0"+month;
        }else {
            monthString += month;
        }
        String currentMonth = String.valueOf(year)+monthString;
        logger.info("查询月份="+currentMonth);
        List<PfUserBill> userBills = pfUserBillService.findByUserIdLimtPage(comUser.getId(),currentMonth,0,0);
        List<AccountUserBill> accountUserBills = new ArrayList<>();
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
        return res;
    }
}
