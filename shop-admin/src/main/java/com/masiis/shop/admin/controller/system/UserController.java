package com.masiis.shop.admin.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.masiis.shop.admin.service.system.PbOperationLogService;
import com.masiis.shop.admin.service.system.PbUserMenuService;
import com.masiis.shop.admin.service.system.PbUserService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.AESUtils;
import com.masiis.shop.dao.po.PbOperationLog;
import com.masiis.shop.dao.po.PbUser;
import com.masiis.shop.dao.po.PbUserMenu;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/2/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final static String key = "^MASIIS-$2016!5!16";

    @Resource
    private PbUserService pbUserService;
    @Resource
    private PbUserMenuService pbUserMenuService;
    @Resource
    private PbOperationLogService pbOperationLogService;

    /**
     * 登陆页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login.shtml")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "user/login";
    }

    /**
     * 用户列表页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/list.shtml")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "user/list";
    }

    /**
     * 添加用户页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/add.shtml")
    public String add(HttpServletRequest request, HttpServletResponse response) {
        return "user/addUser";
    }

    /**
     * 登陆逻辑
     *
     * @param request
     * @param response
     * @param pbUser     登陆参数
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, PbUser pbUser) throws JsonProcessingException {
        ModelAndView mav = new ModelAndView();

        /* 已登陆 */
        HttpSession session = request.getSession();
        if (!session.isNew() && session.getAttribute("pbUser") != null) {
            mav.setViewName("redirect:/main/index.shtml");
            return mav;
        }

        /* 未登陆 */

        //用户名或密码为空
        if (StringUtil.isEmpty(pbUser.getUserName()) || StringUtil.isEmpty(pbUser.getPassword())) {
            mav.setViewName("redirect:login.shtml");
            mav.addObject("pbUser", pbUser);
            return mav;
        }

        /* 5分钟内登录错误超过5次 */
        Integer login_error_count = (Integer)session.getAttribute("login_error_count");//登录错误次数
                login_error_count = login_error_count==null ? 0 : login_error_count;
        Long    last_login_time   = (Long)session.getAttribute("last_login_time");//上次登录时间
                last_login_time   = last_login_time==null ? System.currentTimeMillis() : last_login_time;
        Long    current_time      = System.currentTimeMillis();//当前系统时间
        Long    limit_time        = 1000L*60*5;//限制时间
        if(login_error_count != null && login_error_count.intValue() >= 5 && current_time - last_login_time < limit_time){
            mav.setViewName("redirect:login.shtml");
            request.setAttribute("login_error_msg", "登录错误超过5次,5分钟后再试试吧!");
            return mav;
        }

        pbUser.setPassword(AESUtils.encrypt(pbUser.getPassword(), key));
        List<PbUser> pbUsers = this.pbUserService.findByCondition(pbUser);

        //登录日志
        PbOperationLog pbOperationLog = new PbOperationLog();
        pbOperationLog.setCreateTime(new Date());
        pbOperationLog.setOperateContent("[pbUser="+pbUser+"]试图登录");
        pbOperationLog.setOperateIp(request.getRemoteAddr());
        pbOperationLog.setOperateType(0);
        pbOperationLog.setPbUserId(pbUsers.size()==0?0:pbUsers.get(0).getId());
        pbOperationLog.setPbUserName(pbUsers.size()==0?pbUser.getUserName():pbUsers.get(0).getUserName());
        pbOperationLog.setRemark(pbUsers.size()==0?"登录失败":"登录成功");
        pbOperationLogService.add(pbOperationLog);

        //用户名或密码不对
        if (pbUsers == null || pbUsers.size() <= 0) {

            /* 记录登录错误次数 */
            if(current_time - last_login_time > limit_time){//超过5分钟再次登录错误
                login_error_count = 1;
            }else{
                login_error_count++;
            }
            session.setAttribute("login_error_count", login_error_count);
            session.setAttribute("last_login_time", System.currentTimeMillis());


            mav.setViewName("redirect:login.shtml");
            mav.addObject("pbUser", pbUser);
            return mav;
        }


        //登陆成功
        session.removeAttribute("login_error_count");
        session.removeAttribute("last_login_time");
        session.setAttribute("pbUser", pbUsers.get(0));
        mav.setViewName("redirect:/main/index.shtml");


        return mav;
    }

    /**
     * 退出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/loginOut.shtml")
    public String loginOut(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("pbUser");
        return "user/login";
    }
    /**
     * 条件分页查询用户数据
     * @param request
     * @param response
     * @param search
     * @param sort
     * @param order
     * @param offset
     * @param limit
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       String search,
                       String sort,
                       String order,
                       Integer offset,
                       Integer limit
    ) throws JsonProcessingException {

        Map<String, Object> pbUsersMap = new HashMap<>();


        offset = offset==null ? 0 : offset;
        limit  = limit ==null ? 0 : limit;
        Integer pageNo = offset/10 + 1;
        if(StringUtils.isNotBlank(sort)){
            if("userName".equals(sort)) sort = "user_name";
            if("trueName".equals(sort)) sort = "true_name";
            PageHelper.startPage(pageNo, limit, sort + " " + order);
        }else{
            PageHelper.startPage(pageNo, limit);
        }

        PbUser pbUserC = new PbUser();

        List<PbUser> pbUsers = pbUserService.findByCondition(pbUserC);
        PageInfo<PbUser> pageInfo = new PageInfo<>(pbUsers);

        pbUsersMap.put("total", pageInfo.getTotal());
        pbUsersMap.put("rows", pbUsers);

        return pbUsersMap;
    }

    @RequestMapping("/load.shtml")
    public ModelAndView load(HttpServletRequest request, HttpServletResponse response, Long id){
        ModelAndView mav = new ModelAndView("user/edit");

        PbUser pbUser = null;
        if(id != null){
            pbUser = pbUserService.findById(id);
        }

        mav.addObject("pbUser", pbUser);

        return mav;
    }

    /**
     * 添加用户
     *
     * @param request
     * @param response
     * @param pbUser     新添用户数据
     */
    @RequestMapping("/add")
    @ResponseBody
    public String add(HttpServletRequest request, HttpServletResponse response, PbUser pbUser) {
        if (StringUtil.isNotEmpty(pbUser.getPassword())) {
            pbUser.setPassword(AESUtils.encrypt(pbUser.getPassword(), key));
        }

        if (pbUser.getId() == null) {
            this.pbUserService.add(pbUser);
        } else {
            this.pbUserService.updateById(pbUser);
        }

        return "保存成功";
    }

    /**
     * 保存用户菜单权限信息
     * @param request
     * @param response
     * @param userId
     * @param pbMenuIds
     * @return
     */
    @RequestMapping("/updateUserMenu.do")
    @ResponseBody
    public String updateUserMenu(HttpServletRequest request, HttpServletResponse response,
                                 Long userId,
                                 @RequestParam(value = "pbMenuIds[]") Long[] pbMenuIds){

        pbUserMenuService.deleteByPbUserId(userId);

        PbUserMenu pbUserMenu = new PbUserMenu();
        for(Long pbMenuId : pbMenuIds){
            pbUserMenu.setPbUserId(userId);
            pbUserMenu.setPbMenuId(pbMenuId);
            pbUserMenu.setCreateTime(new Date());
            pbUserMenu.setUpdateTime(new Date());
            pbUserMenuService.add(pbUserMenu);
        }

        return "success";
    }


    /**
      * @Author jjh
      * @Date 2016/5/27 0027 下午 5:35
      * 修改密码
      */
    @RequestMapping("/updatePsd.do")
    @ResponseBody
    public String updatePassword(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value="oldPsd",required = true) String oldPsd,
                                 @RequestParam(value="newPsd",required = true) String newPsd){
        JSONObject object = new JSONObject();
        try{
            HttpSession session = request.getSession();
            PbUser pbUser = (PbUser)session.getAttribute("pbUser");
            String pasd = AESUtils.encrypt(oldPsd, key);
            if(!pbUser.getPassword().equals(pasd)){
               throw new BusinessException("原密码输入有误！");
            }
            pbUser.setPassword(AESUtils.encrypt(newPsd, key));
            pbUserService.updateById(pbUser);
            object.put("isError",false);
        }catch (Exception e){
            object.put("isError",true);
            object.put("msg",e.getMessage());
        }
       return object.toJSONString();
    }
}
