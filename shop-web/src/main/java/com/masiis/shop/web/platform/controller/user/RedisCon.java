package com.masiis.shop.web.platform.controller.user;

import com.masiis.shop.web.platform.beans.user.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @autor jipengkun
 */
@Controller
public class RedisCon {


    @RequestMapping(value = "/redis-set")
    public String redisSet(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Student stu = new Student();
        stu.setUsername("sunny");
        stu.setAge("21");

        session.setAttribute("admin","good");
        session.setAttribute("stu",stu);
        return "platform/redis";
    }

    @RequestMapping(value = "/redis-get")
    public String redisGet(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String admin = (String) session.getAttribute("admin");
        System.out.println("admin---" + admin);

        Student stu = (Student) session.getAttribute("stu");
        System.out.println("username" + stu.getUsername());
        return "platform/redis";
    }

}
