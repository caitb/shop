package com.masiis.shop.web.mall.controller.base;

import com.masiis.shop.common.util.AESUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.constants.SysConstants;
import com.masiis.shop.web.mall.service.user.UserService;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 基础controller,用来编写一些基础方法
 * <p/>
 * Created by lzh on 2016/2/27.
 */
public class BaseController {
    public final static String SUCCESS = "success";

    public final static String MSG = "msg";

    public final static String DATA = "data";

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private UserService userService;


    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    protected String createRedirectRes(String uri) {
        return "redirect:" + uri;
    }

    protected String createForwardRes(String uri) {
        return "forward:" + uri;
    }

    protected String getRequestBody(HttpServletRequest request) throws IOException {
        InputStream is = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String res = null;
        is = request.getInputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }

        res = new String(os.toByteArray(), "UTF-8");

        if (is != null) {
            is.close();
        }
        os.close();

        return res;
    }

    protected void sendResponseBody(HttpServletResponse response, String res) {
        try {
            OutputStream os = response.getOutputStream();
            ByteArrayInputStream is = new ByteArrayInputStream(res.getBytes("UTF-8"));
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            if (is != null) {
                is.close();
            }
            os.flush();
            os.close();
        } catch (UnsupportedEncodingException e) {

        } catch (IOException e) {

        }
    }

    /**
     * 从session中获取登录用户
     *
     * @param request
     * @return
     */
    protected ComUser getComUser(HttpServletRequest request) {
        ComUser user = (ComUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_NAME);
        if (user == null) {
            return null;
        }
        log.info("缓存user的id为----"+user.getId());
        user = userService.getUserById(user.getId());
        return user;
    }

    /**
     * 更新用户session
     *
     * @author ZhaoLiang
     * @date 2016/3/24 14:42
     */
    protected void setComUser(HttpServletRequest request, ComUser comUser) {
        request.getSession().setAttribute(SysConstants.SESSION_LOGIN_USER_NAME, comUser);
    }

    protected String aesEncryptBySalt(String content, String password, String salt) {
        return AESUtils.encrypt(content + salt, password);
    }

    protected String aesDecryptBySalt(String content, String password, String salt) {
        String res = AESUtils.decrypt(content, password);
        int end = res.lastIndexOf(salt);
        if (end > 0) {
            res = res.substring(0, end);
        }

        return res;
    }

    /**
     * 错误日志记录
     *
     * @author ZhaoLiang
     * @date 2016/3/24 15:07
     */
    protected void setErrorLog(Object message, Throwable t) {
        log.error(message, t);
    }

    protected void setWarnLog(Object message) {
        log.warn(message);
    }

    protected void setInfoLog(Object message) {
        log.info(message);
    }

    /**
     * 获取web项目webapp或者webroot的根目录绝对磁盘路径
     *
     * @param request
     * @return
     */
    protected String getWebRootPath(HttpServletRequest request){
        return request.getSession().getServletContext().getRealPath("/");
    }
    protected String getBasePath(HttpServletRequest request) {
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        return basePath;
    }

    protected String toXML(XStream xStream, Object req){
        xStream.processAnnotations(req.getClass());
        return xStream.toXML(req);
    }
}
