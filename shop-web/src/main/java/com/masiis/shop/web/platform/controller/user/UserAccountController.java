package com.masiis.shop.web.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.beans.user.PfIncomRecord;
import com.masiis.shop.dao.beans.user.PfIncomRecordPo;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.PfUserBill;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.common.service.ComUserAccountService;
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
    @Resource
    private UserService userService;

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
            BigDecimal fee = BigDecimal.ZERO;
            account = new ComUserAccount();
            account.setExtractableFee(fee);
            account.setAgentBillAmount(fee);
            account.setDistributionBillAmount(fee);
            account.setRecommenBillAmount(fee);
            account.setAppliedFee(fee);
        }else {
            if (account.getAppliedFee() == null){
                account.setAppliedFee(BigDecimal.ZERO);
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
        mv.addObject("totalIncom",rmbFormat.format(account.getExtractableFee().add(withdrawd).add(account.getAgentBillAmount()).add(account.getDistributionBillAmount()).add(account.getRecommenBillAmount())));
        BigDecimal countingFee = account.getAgentBillAmount().add(account.getDistributionBillAmount()).add(account.getRecommenBillAmount());
        account.setViewCountingFee(rmbFormat.format(countingFee));
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
     * 进入收入记录首页
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getIncomRecord14.shtml")
    public ModelAndView getIncomRecord14(HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView();
        ComUser comUser = getComUser(request);
        if(comUser == null){
            throw new BusinessException("用户未登录!");
        }
        Date date = new Date();
        Date firstDate = DateUtil.getFirstTimeInMonth(date);
        Date lastDate = DateUtil.getLastTimeInMonth(date);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthString;
        if (month < 10){
            monthString = "0" + month;
        }else {
            monthString = String.valueOf(month);
        }
        PfIncomRecordPo pfIncomRecordPo = pfUserBillService.getIncomRecord14(comUser.getId(), firstDate, lastDate, null, 1);
        mv.addObject("user",comUser);
        mv.addObject("year",year);
        mv.addObject("month",monthString);
        mv.addObject("pfIncomRecordPo",pfIncomRecordPo);
        mv.setViewName("platform/user/incomeRecord14");
        return mv;
    }

    /**
     *  ajax查询收入记录
     * @param date      查询月份
     * @param flag      B、C端标识  1：B  2：C  0：全部
     * @param currentPage   当前页
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getIncomRecord14.do")
    public String getIncomRecord14Ajax(HttpServletRequest request,
                                       @RequestParam("date") String date,
                                       @RequestParam("flag") Integer flag,
                                       @RequestParam("currentPage") Integer currentPage) throws Exception{
        log.info("ajax查询收入记录");
        log.info("date = " + date);
        log.info("flag = " + flag);
        log.info("currentPage = " + currentPage);
        ComUser sessionUser = getComUser(request);
        JSONObject jsonObject = new JSONObject();
        try {
            if (sessionUser == null){
                throw new BusinessException("用户未登录!");
            }
            log.info("userId = " + sessionUser.getId());
            Date dateTime = DateUtil.String2Date(date);
            Date firstDate = DateUtil.getFirstTimeInMonth(dateTime);
            Date lastDate = DateUtil.getLastTimeInMonth(dateTime);
            if (flag.intValue() == 0){
                flag = null;
            }
            PfIncomRecordPo pfIncomRecordPo = pfUserBillService.getIncomRecord14(sessionUser.getId(), firstDate, lastDate, flag, currentPage + 1);
            jsonObject.put("isTrue","true");
            jsonObject.put("currentPage", pfIncomRecordPo.getPageNum());
            jsonObject.put("totalCount", pfIncomRecordPo.getTotalCount());
            jsonObject.put("totalIncom", pfIncomRecordPo.getTotalIncomView());
            StringBuffer sb = new StringBuffer("");
            for (PfIncomRecord pfIncomRecord : pfIncomRecordPo.getPfIncomRecords()){
                sb.append("<div class=\"sec1\"><p>");
                sb.append("<span>" + pfIncomRecord.getYearView() + "</span>");
                sb.append("<span>" + pfIncomRecord.getMinView() + "</span></p>");
                sb.append("<img src=\"" + pfIncomRecord.getHeadImg() + "\" alt=\"\" onclick=\"toPersonIncom(" + pfIncomRecord.getUserId() + ")\">");
                sb.append("<div>");
                sb.append("<p><span><a onclick=\"toPersonIncom(" + pfIncomRecord.getUserId() + ")\">" + pfIncomRecord.getRealName() + "</a></span> <b>+" + pfIncomRecord.getInComView() + "</b></p>");
                sb.append("<p><span>" + pfIncomRecord.getSkuName() + "</span> <b onclick=\"toOrderDetail('" + pfIncomRecord.getOrderDetail() + "','" + pfIncomRecord.getOrderId() + "')\" style=\"color: #666;\">" + pfIncomRecord.getOrderTypeView() + "</b></p>");
                sb.append("</div></div>");
            }
            jsonObject.put("html",sb.toString());
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("isTrue","false");
            jsonObject.put("message",e.getMessage());
        }
        log.info(jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * 个人收入记录详情
     * @param userId    用户id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getIncomRecord14Person.shtml")
    public ModelAndView getIncomRecord14Person(@RequestParam(value = "uid", required = true) Long userId,
                                               @RequestParam(value = "date", required = true) String date,
                                               HttpServletRequest request) throws Exception{
        ComUser sessionUser = getComUser(request);
        if (sessionUser == null){
            throw new BusinessException("用户未登录!");
        }
        log.info("个人收入记录详情");
        log.info("personUserId = " + userId);
        log.info("date = " + date);
        ComUser comUser = userService.getUserById(userId);
        if (comUser == null){
            throw new BusinessException("用户不存在!");
        }
        ModelAndView mv = new ModelAndView();
        Date dateTime = DateUtil.String2Date(date);
        Date firstDate = DateUtil.getFirstTimeInMonth(dateTime);
        Date lastDate = DateUtil.getLastTimeInMonth(dateTime);
        String[] time = date.split("-");
        PfIncomRecordPo pfIncomRecordPo = pfUserBillService.getIncomRecord14Person(sessionUser.getId(), firstDate, lastDate, null, 1,userId);
        if (comUser.getRealName() == null){
            comUser.setRealName(comUser.getWxNkName());
        }
        mv.addObject("comUser",comUser);
        mv.addObject("year",time[0]);
        mv.addObject("month",time[1]);
        mv.addObject("pfIncomRecordPo",pfIncomRecordPo);
        mv.setViewName("platform/user/incomeRecord14Person");
        return mv;
    }
    /**
     *  ajax查询收入记录
     * @param date      查询月份
     * @param currentPage   当前页
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getIncomRecord14Person.do")
    public String getIncomRecord14PersonAjax(HttpServletRequest request,
                                             @RequestParam(value = "uid", required = true) Long userId,
                                             @RequestParam("date") String date,
                                             @RequestParam("currentPage") Integer currentPage) throws Exception{
        log.info("ajax查询收入记录");
        log.info("date = " + date);
        log.info("currentPage = " + currentPage);
        ComUser sessionUser = getComUser(request);
        JSONObject jsonObject = new JSONObject();
        try {
            if (sessionUser == null){
                throw new BusinessException("用户未登录!");
            }
            log.info("sessionUserId = " + sessionUser.getId());
            ComUser comUser = userService.getUserById(userId);
            if (comUser == null){
                throw new BusinessException("用户不存在!");
            }
            Date dateTime = DateUtil.String2Date(date);
            Date firstDate = DateUtil.getFirstTimeInMonth(dateTime);
            Date lastDate = DateUtil.getLastTimeInMonth(dateTime);
            PfIncomRecordPo pfIncomRecordPo = pfUserBillService.getIncomRecord14Person(sessionUser.getId(), firstDate, lastDate, null, currentPage + 1,userId);
            jsonObject.put("isTrue","true");
            jsonObject.put("currentPage", pfIncomRecordPo.getPageNum());
            jsonObject.put("totalCount", pfIncomRecordPo.getTotalCount());
            jsonObject.put("totalIncom", pfIncomRecordPo.getTotalIncomView());
            StringBuffer sb = new StringBuffer("");
            for (PfIncomRecord pfIncomRecord : pfIncomRecordPo.getPfIncomRecords()){
                sb.append("<div class=\"sec1\"><p>");
                sb.append("<span>" + pfIncomRecord.getYearView() + "</span>");
                sb.append("<span>" + pfIncomRecord.getMinView() + "</span></p>");
                sb.append("<img src=\"" + pfIncomRecord.getHeadImg() + "\" alt=\"\">");
                sb.append("<div>");
                sb.append("<p><span>" + pfIncomRecord.getRealName() + "</span> <b>+" + pfIncomRecord.getInComView() + "</b></p>");
                sb.append("<p><span>" + pfIncomRecord.getSkuName() + "</span> <b onclick=\"toOrderDetail('" + pfIncomRecord.getOrderDetail() + "','" + pfIncomRecord.getOrderId() + "')\" style=\"color: #666;\">" + pfIncomRecord.getOrderTypeView() + "</b></p>");
                sb.append("</div></div>");
            }
            jsonObject.put("html",sb.toString());
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("isTrue","false");
            jsonObject.put("message",e.getMessage());
        }
        log.info(jsonObject.toString());
        return jsonObject.toString();
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
