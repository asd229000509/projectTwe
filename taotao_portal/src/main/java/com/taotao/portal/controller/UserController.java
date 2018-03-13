package com.taotao.portal.controller;

import com.taotao.commom.vo.redis.RedisService;
import com.taotao.portal.util.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    //用户的登录成功标识符ticket在cookie的key名称
    private static final String COOKIE_TICKET = "TT_TICKET";
    private static final Integer COOKIE_MAX_AGE = 3600;
    /**
     * 将用户的注册信息保存到单点登录系统
     * @param user 用户的注册信息
     * @return
     */
    @RequestMapping("/doRegister")
    public ResponseEntity<Map<String, Object>> register(User user) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", 500);
        //调用单点登陆系统服务进行注册
        try {
            userService.register(user);
            result.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
        }
        return  ResponseEntity.ok(result);
    }

    /**
     * 用户登陆
     * @param user 登陆信息
     * @param request 请求对象
     * @param response 响应对象
     * @return
     */
    @RequestMapping("/doLogin")
    public ResponseEntity<Map<String,Object>> login(
            User user, HttpServletRequest request , HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", 500);
        try {
            //实现登陆校验
            String ticket = userService.login(user);
            if (StringUtils.isNotBlank(ticket)) {
                result.put("status", 200);
                //保存到Cookie中,生效时间为一小时
                CookieUtils.setCookie(request,response,COOKIE_TICKET,ticket,COOKIE_MAX_AGE,true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request , HttpServletResponse response) {
        //删除redis中的用户信息
        userService.deleteRedisUser(CookieUtils.getCookieValue(request,COOKIE_TICKET));
        //删除存储的cookie总的ticket对象
        CookieUtils.deleteCookie(request,response,COOKIE_TICKET);
        //重定向到商城首页
        return "redirect:/index.html";
    }
}
