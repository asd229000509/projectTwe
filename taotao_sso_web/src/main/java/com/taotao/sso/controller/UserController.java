package com.taotao.sso.controller;

import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 校验数据是否可用
     *  url:'http://sso.taotao.com/user/check/{param}/{type}',method:'GET'
     * @param param 校验的数据
     * @param type 校验的类型:1、2、3分别代表username、phone、email
     * @param callback 为了支持jsonp的返回方法名称支持jsonp
     * @return 返回校验的http响应状态码:200：请求成功,400：请求参数不合法;true：数据可用，false：数据不可用
     */
    @RequestMapping(value = "/check/{param}/{type}", method = RequestMethod.GET)
    private ResponseEntity<String> check(
            @PathVariable("param") String param,@PathVariable("type")Integer type,
            @RequestParam(value = "callback",required = false)String callback) {
        try {
            if (type >= 1 && type <= 3) {
                String result = "false";
                Boolean bool = userService.check(param, type);
                result = bool.toString();
                if (StringUtils.isNotBlank(callback)) {
                    result = callback + "(" + result + ");";
                }
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    /**
     * 根据ticket查询用户
     *  url:'http://sso.taotao.com/user/{ticket}/{type}',method:'GET'
     * @param ticket 用户登录凭证
     * @param callback 为了支持jsonp的返回方法名称支持jsonp
     * @return 返回校验的http响应状态码:200：请求成功,400：请求参数不合法;用户对象的jsonp格式字符串
     */
    @RequestMapping(value = "/{ticket}", method = RequestMethod.GET)
    private ResponseEntity<String> queryUserByTicket(
            @PathVariable("ticket")String ticket,
            @RequestParam(value = "callback",required = false)String callback) {
        try {
            if (StringUtils.isNotBlank(ticket)) {
                String result= userService.queryUserStrByTicket(ticket);
                if (StringUtils.isNotBlank(callback)) {
                    result = callback + "(" + result + ");";
                }
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
