package com.masiis.shop.web.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.PfUserBill;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.ComUserAccountService;
import com.masiis.shop.web.platform.service.user.PfUserBillService;
import com.masiis.shop.web.platform.service.user.UserExtractApplyService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lzh on 2016/3/18.
 */
@Controller
@RequestMapping("/account")
public class UserAccountController extends BaseController{
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private ComUserAccountService accountService;
    @Resource
    private PfUserBillService pfUserBillService;
    @Resource
    private UserExtractApplyService userExtractApplyService;

    /*@RequestMapping("/home")
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
        Map<String, BigDecimal> map = userExtractApplyService.findSumExtractfeeByUserId(user.getId());
        BigDecimal withdraw = map == null?new BigDecimal(0.00):map.get("extractFee");
        model.addAttribute("currentPage",1);
        model.addAttribute("pageSize",10);
        model.addAttribute("year",year);
        model.addAttribute("month",monthString);
        model.addAttribute("day",day);
        model.addAttribute("totalIncom",account.getExtractableFee().add(withdraw).add(account.getCountingFee()));
        model.addAttribute("account", account);
        model.addAttribute("userBills",userBills);
        return "platform/user/account_bak";
    }*/

    @RequestMapping("/home")
    public ModelAndView accountHomeEdit1(HttpServletRequest request) throws Exception{
        log.info("我的资产首页（需求修改）");
        ModelAndView mv = new ModelAndView();
        ComUser comUser = getComUser(request);
        if(comUser == null){
            throw new BusinessException("用户未登录!");
        }
        log.info("userId = " + comUser.getId());
        String currentDate = DateUtil.Date2String(new Date(),"yyyy-MM-dd");
        //查询用户资产表，用于展示累计收入和可提现金额
        ComUserAccount account = accountService.findAccountByUserid(comUser.getId());
        if (account == null){
            log.info("无用户账户信息");
            BigDecimal fee = new BigDecimal(0.00);
            account = new ComUserAccount();
            account.setTotalIncomeFee(fee);
            account.setExtractableFee(fee);
            account.setCountingFee(fee);
            account.setProfitFee(fee);
        }else {
            if (account.getAppliedFee() == null){
                account.setAppliedFee(new BigDecimal(0.00));
            }
        }
        NumberFormat rmbFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
        log.info("查询b端结算中金额begin");
        BigDecimal agentAmount = new BigDecimal(0);
        List<Map<String, Object>> billAmountMaps = accountService.getPfCountindFee(comUser.getId());
        if (billAmountMaps != null && billAmountMaps.size() > 0){
            for (Map<String, Object> map : billAmountMaps){
                if ("0".equals(map.get("orderSubType").toString())){
                    agentAmount = agentAmount.add(new BigDecimal(map.get("amount").toString()));
                }else {
                    agentAmount = agentAmount.subtract(new BigDecimal(map.get("amount").toString()));
                }
            }
        }
        log.info("查询b端结算中金额end");
        log.info("查询已提现金额begin");
        Map<String, BigDecimal> map = userExtractApplyService.findSumExtractfeeByUserId(comUser.getId());
        BigDecimal withdrawd = map == null?new BigDecimal(0.00):map.get("extractFee");
        log.info("查询已提现金额end");
        mv.addObject("comUser",comUser);
        mv.addObject("withdrawd",rmbFormat.format(withdrawd));
        mv.addObject("currentDate",currentDate);
        mv.addObject("totalIncom",rmbFormat.format(account.getExtractableFee().add(withdrawd).add(account.getAgentBillAmount()).add(account.getDistributionBillAmount())));
        account.setCountingFee(account.getAgentBillAmount() == null?new BigDecimal(0): account.getAgentBillAmount().
                                add(account.getDistributionBillAmount() == null?new BigDecimal(0): account.getDistributionBillAmount()).
                                add(account.getRecommenBillAmount() == null?new BigDecimal(0):account.getRecommenBillAmount()));
        account.setExtractableFee(account.getExtractableFee().subtract(account.getAppliedFee()));
        mv.addObject("account",account);
        mv.setViewName("platform/user/account");
        return mv;
    }

    @RequestMapping("/home/shuoming.shtml")
    public ModelAndView gotoShuoming(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("platform/user/shuoming");
        return mv;
    }

    @RequestMapping(value = "/getIncomRecord.shtml")
    public ModelAndView getIncomRecord(HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView();
        ComUser comUser = getComUser(request);
        if(comUser == null){
            throw new BusinessException("用户未登录!");
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
        List<PfUserBill> userBills = pfUserBillService.findByUserIdLimtPage(comUser.getId(),currentMonth,0,0);
        mv.addObject("record",userBills);
        mv.setViewName("platform/user/incomeRecord");
        return mv;
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

}
