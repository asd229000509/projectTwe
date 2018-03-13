package com.taotao.sso.service;

import com.taotao.sso.pojo.User;

import java.io.IOException;

public interface UserService  {
    /**
     * 校验数据书否可用
     * @param param 校验的数据
     * @param type 数据类型；如：1、2、3分别代表username、phone、email
     * @return
     */
    Boolean check(String param, Integer type);

    /**
     * 根据ticket查询用户
     * @param ticket 用户登录凭证
     * @return 用户json格式字符串
     */
    String queryUserStrByTicket(String ticket);

    /**
     * 注册
     * @param user 注册信息
     */
    void register(User user);

    /**
     * 登陆
     * @param user 登陆信息
     * @return ticket值
     */
    String login(User user) throws IOException, Exception;

    /**
     * 退出登陆 删除redis
     * @param cookieValue
     */
    void deleteRedisUser(String cookieValue);
}
