package com.masiis.shop.web.mall.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComWxUser;
import com.masiis.shop.dao.po.SfUserAccount;
import com.masiis.shop.dao.po.SfUserExtractApply;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.user.SfUserAccountService;
import com.masiis.shop.web.mall.service.user.SfUserExtractApplyService;
import com.masiis.shop.web.mall.service.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wangbingjian on 2016/4/10.
 */
@Controller
@RequestMapping(value = "/withdraw")
public class SfUserExtractApplyController extends BaseController{

    private final Logger log = Logger.getLogger(SfUserAccountController.class);

    @Autowired
    private SfUserAccountService userAccountService;
    @Autowired
    private UserService userService;
    @Autowired
    private SfUserExtractApplyService sfUserExtractApplyService;

    /**
     * 用户提现申请
     * @param request
     * @return
     * @author:wbj
     */
    @RequestMapping(value = "/withdrawRequest.shtml")
    public ModelAndView withdrawRequest(HttpServletRequest request) throws Exception{
        log.info("进入用户提现申请");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户没有登录");
        }
        ModelAndView mv = new ModelAndView();
        Long userId = comUser.getId();
        log.info("userId="+userId);
        SfUserAccount userAccount = userAccountService.findAccountByUserId(userId);
        if (userAccount == null){
            userAccount = new SfUserAccount();
            userAccount.setExtractableFee(new BigDecimal(0));
        }
        mv.addObject("userAccount",userAccount);
        mv.addObject("comUser",comUser);
        mv.setViewName("mall/user/sf_withdrawRequest");
        return mv;
    }

    /**
     *  分销用户确认提现
     * @param inputAccount  页面输入金额
     * @param userId        用户id
     * @param request
     * @return
     * @author:wbj
     */
    @RequestMapping(value = "/confirmWithdraw.do")
    @ResponseBody
    public String confirmWithdraw(@RequestParam(value = "inputAccount",required = true) String inputAccount,
                                  @RequestParam(value = "userId",required = true) Long userId,
                                  HttpServletRequest request){

        ComUser user = getComUser(request);
        log.info("ajax 分销用户确认提现");
        log.info("用户申请提现金额：" + inputAccount);
        JSONObject jsonobject = new JSONObject();
        if (user == null){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","用户未登录");
            log.info(jsonobject.toJSONString());
            return jsonobject.toJSONString();
        }
        if (user.getId() != userId){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","用户信息错误");
            log.info(jsonobject.toJSONString());
            return jsonobject.toJSONString();
        }
        if(StringUtils.isBlank(inputAccount)){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","提现金额未填写!");
            log.info(jsonobject.toJSONString());
            return jsonobject.toJSONString();
        }
        if(!SysBeanUtils.isNumeric(inputAccount)){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","金额不是数字类型的值!");
            log.info(jsonobject.toJSONString());
            return jsonobject.toJSONString();
        }
        BigDecimal b_inputAccount = new BigDecimal(inputAccount);
        if (b_inputAccount.compareTo(new BigDecimal(1)) < 0){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","提现金额不小于1元!");
            log.info(jsonobject.toJSONString());
            return jsonobject.toJSONString();
        }
        //根据userId查询用户账户信息
        SfUserAccount userAccount = userAccountService.findAccountByUserId(userId);
        if (userAccount == null || b_inputAccount.compareTo(userAccount.getExtractableFee().subtract(userAccount.getAppliedFee())) == 1 || b_inputAccount.compareTo(new BigDecimal(0)) <= 0){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","可提现余额错误");
            log.info(jsonobject.toJSONString());
            return jsonobject.toJSONString();
        }

        try {
            //提现申请处理
            sfUserExtractApplyService.applyExtract(b_inputAccount, user, userAccount);
        }catch (Exception e){
            e.printStackTrace();
            jsonobject.put("isTrue","false");
            jsonobject.put("message","用户提现处理失败");
        }
        jsonobject.put("isTrue","true");
        jsonobject.put("resUrl","requestSuccess.shtml");
        log.info(jsonobject.toJSONString());
        return jsonobject.toJSONString();
    }

    /**
     * 分销用户提现申请成功
     * @param request
     * @return
     * @throws Exception
     * @author:wbj
     */
    @RequestMapping(value = "/requestSuccess.shtml")
    public ModelAndView requestSuccess(HttpServletRequest request) throws Exception{

        log.info("分销用户提现申请成功");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户没有登录");
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mall/user/sf_confirmWithdrawRequest");
        return mv;
    }

    /**
     * 分销用户查询提现记录
     * @param request
     * @return
     * @throws Exception
     * @auth:wbj
     */
    @RequestMapping(value = "/withdrawRecord.shtml")
    public ModelAndView queryExtractApplyList(HttpServletRequest request) throws Exception{
        log.info("分销用户查询提现记录");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户没有登录");
        }
        ModelAndView mv = new ModelAndView();
        Date start = DateUtil.getFirstTimeInMonth(new Date());
        Date end = DateUtil.getLastTimeInMonth(new Date());
        // 根据用户id查询提现记录,查询当前月的提现记录
        Integer count = sfUserExtractApplyService.findCountByUserAndDate(comUser, start, end);
        log.info("分销用户提现记录数量="+count);
        List<SfUserExtractApply> list = null;
        if (count > 0){
            list = sfUserExtractApplyService.findListByUserAndDate(comUser,start,end,1,20);
        }
        //默认设置每页显示20条
        Integer pageNums = count%20 == 0 ? count/20 : count/20 + 1;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthString = "";
        if (month<10){
            monthString = "0"+month;
        }else {
            monthString += month;
        }
        mv.addObject("totalPage",pageNums);
        mv.addObject("currentPage",1);
        mv.addObject("pageSize",20);
        mv.addObject("year",year);
        mv.addObject("month",monthString);
        mv.addObject("list",list);
        mv.setViewName("mall/user/sf_withdrawRecord");
        return mv;
    }

    /**
     * ajax查询提现记录
     * @param year
     * @param month
     * @param currentPage
     * @param pageSize
     * @return
     * @author:wbj
     */
    @RequestMapping(value = "/ajaxExtractApplyList.do",method = RequestMethod.POST)
    @ResponseBody
    public String ajaxExtractApplyList(@RequestParam(value = "year",required = true) String year,
                                       @RequestParam(value = "month",required = true) String month,
                                       @RequestParam(value = "currentPage",required = true) Integer currentPage,
                                       @RequestParam(value = "pageSize",required = true) Integer pageSize,
                                       HttpServletRequest request) throws Exception{
        log.info("分销用户查询提现记录");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户没有登录");
        }
        Date date = DateUtil.String2Date(year+month+"01");
        Date start = DateUtil.getFirstTimeInMonth(date);
        Date end = DateUtil.getLastTimeInMonth(date);
        JSONArray jsonArray = new JSONArray();
        try{
            // 根据用户id查询提现记录,查询当前月的提现记录
            Integer count = sfUserExtractApplyService.findCountByUserAndDate(comUser, start, end);
            log.info("分销用户提现记录数量="+count);
            List<SfUserExtractApply> sfUserExtractApplies = null;
            if (count > 0){
                sfUserExtractApplies = sfUserExtractApplyService.findListByUserAndDate(comUser,start,end,currentPage,pageSize);
                Integer pageNums = count%20 == 0 ? count/20 : count/20 + 1;
                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                for (SfUserExtractApply sfUserExtractApply : sfUserExtractApplies){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("date",sdf.format(sfUserExtractApply.getApplyTime())+"日");
                    jsonObject.put("extractFee",sfUserExtractApply.getExtractFee().toString());
                    jsonObject.put("status",sfUserExtractApply.getAuditType()==0?"待审核":sfUserExtractApply.getAuditType()==1?"已拒绝":sfUserExtractApply.getAuditType()==2?"待打款":"已付款");
                    jsonObject.put("totalPage",pageNums);
                    jsonArray.put(jsonObject);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("处理数据出错");
        }
        log.info(jsonArray.toString());
        return jsonArray.toString();
    }

    /**
     * 提现申请发送短信
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendMessageWithdrawRequest.do")
    @ResponseBody
    public String sendMessageWithdrawRequest(HttpServletRequest request){
        log.info("提现申请成功发送短信");
        ComUser user = getComUser(request);
        if (user == null){
            throw new BusinessException("该用户未登录");
        }
        if (user.getMobile()==null || "".equals(user.getMobile())){
            return "false";
        }
        return String.valueOf(MobileMessageUtil.withdrawRequestVerify(user.getMobile(),"1"));
    }
}
