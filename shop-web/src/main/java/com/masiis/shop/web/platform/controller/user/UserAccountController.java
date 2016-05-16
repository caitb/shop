package com.masiis.shop.web.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.PfUserBill;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.ComUserAccountService;
import com.masiis.shop.web.platform.service.user.PfUserBillService;
import com.masiis.shop.web.platform.service.user.UserService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lzh on 2016/3/18.
 */
@Controller
@RequestMapping("/account")
public class UserAccountController extends BaseController{
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private UserService userService;
    @Resource
    private ComUserAccountService accountService;
    @Resource
    private PfUserBillService pfUserBillService;

    @RequestMapping("/home")
    public String accountHome(HttpServletRequest request, Model model) throws Exception{
        ComUser user = getComUser(request);
        log.info("进入我的资产首页");
        if(user == null){
            throw new BusinessException("用户未登录!");
        }
        //String kid = aesEncryptBySalt(String.valueOf(user.getId()), SysConstants.COOKIE_AES_KEY, SysConstants.COOKIE_KEY_SALT);
        log.info("用户id："+user.getId());
        //查询用户资产表，用于展示累计收入和可提现金额
        ComUserAccount account = accountService.findAccountByUserid(user.getId());
        if (account == null){
            BigDecimal fee = new BigDecimal(0.00);
            account = new ComUserAccount();
            account.setTotalIncomeFee(fee);
            account.setExtractableFee(fee);
            account.setCountingFee(fee);
            account.setProfitFee(fee);
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        String monthString = "";
        if (month<10){
            monthString = "0"+month;
        }else {
            monthString += month;
        }
        String currentMonth = String.valueOf(year)+monthString;
        List<PfUserBill> userBills = pfUserBillService.findByUserIdLimtPage(user.getId(),currentMonth,0,0);
        model.addAttribute("currentPage",1);
        model.addAttribute("pageSize",10);
        model.addAttribute("year",year);
        model.addAttribute("month",monthString);
        model.addAttribute("day",day);
        model.addAttribute("account", account);
        model.addAttribute("userBills",userBills);
        return "platform/user/account";
    }

    /**
     * ajax加载更多或者其他月份用户账单信息
     * @param year              选择年份
     * @param month             选择月份
     * @param paging            是否分页
     * @param pageTotalCount    页面显示信息总条数
     * @param request
     * @return
     */
    @RequestMapping(value = "getMoreUserBill",method = RequestMethod.POST)
    @ResponseBody
    public String getUserBillAjax(@RequestParam(value = "year",required = true) String year,
                                  @RequestParam(value = "month",required = true) String month,
                                  @RequestParam(value = "paging",required = true) String paging,
                                  @RequestParam(value = "pageTotalCount",required = true) String pageTotalCount,
                                  HttpServletRequest request){
        ComUser user = getComUser(request);
        log.info("获取更多用户账单信息");
        JSONArray jsonArray = null;
        try{
            if(user == null){
                throw new BusinessException("用户未登录!");
            }
            List<PfUserBill> userBills;
            //使用分页方式查询
            if (paging.equals("Y")){
                //当pageTotalCount等于0时，说明为查询其他月份数据
                if(pageTotalCount.equals("0")){
                    userBills = pfUserBillService.findByUserIdLimtPage(user.getId(),year+month,1,10);
                }else {
                    int pageSize = 5; //ajax请求时默认每页显示条数为5条
                    int currentPage = Integer.valueOf(pageTotalCount)/pageSize == 0?Integer.valueOf(pageTotalCount)/pageSize + 1:Integer.valueOf(pageTotalCount)/pageSize + 2;
                    userBills = pfUserBillService.findByUserIdLimtPage(user.getId(),year+month,currentPage,pageSize);
                }
            }else {
                userBills = pfUserBillService.findByUserIdLimtPage(user.getId(),year+month,0,0);
            }
            jsonArray = new JSONArray();
            SimpleDateFormat format = new SimpleDateFormat("dd");
            String date;
            for(PfUserBill pfUserBill:userBills){
                JSONObject jsonObject = new JSONObject();
                date = format.format(pfUserBill.getBalanceDate());
                jsonObject.put("date",date);
                jsonObject.put("incom",pfUserBill.getBillAmount().toString());
                jsonArray.put(jsonObject);
            }
            log.info("jsonArray:"+jsonArray.toString());
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("网络错误", e);
        }
        return jsonArray.toString();
    }

//    /**
//     * 跳转月份查询用户账单信息
//     * @param year
//     * @param month
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "getUserBillbyTurn",method = RequestMethod.POST)
//    @ResponseBody
//    public String getUserBillbyTurn(@RequestParam(value = "year",required = true) String year,
//                                    @RequestParam(value = "month",required = true) String month,
//                                    HttpServletRequest request){
//        ComUser user = getComUser(request);
//        log.info("获取其他年份月份的用户账单信息");
//        if(user == null){
//            user = userService.getUserByOpenid("oUIwkwgLzn8CKMDrvbCSE3T-u5fs");
//        }
//        //首次查询默认查询第一页，每页查询10条
//        List<PfUserBill> userBills = pfUserBillService.findByUserIdLimtPage(user.getId(),year+month,1,10);
//
//    }
}
