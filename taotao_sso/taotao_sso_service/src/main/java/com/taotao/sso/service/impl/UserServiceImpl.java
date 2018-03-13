package com.taotao.sso.service.impl;

import com.taotao.commom.vo.redis.RedisService;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    //用户在redis中的用户信息对应的key的前缀
    private static final String REDIS_TICKET_KEY_PREFIX = "TT_TICKET_";
    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * 检查数据是否可用
     *
     * @param param 校验的数据
     * @param type  数据类型；如：1、2、3分别代表username、phone、email
     * @return
     */
    @Override
    public Boolean check(String param, Integer type) {
        //到数据库中查询是否存在参数
        User user = new User();
        switch (type) {
            case 1:
                user.setUsername(param);
                break;
            case 2:
                user.setPhone(param);
                break;
            default:
                user.setEmail(param);
                break;
        }
        int count = userMapper.selectCount(user);
        return count == 0;
    }

    /**
     * 根据ticket查询用户
     *
     * @param ticket 用户登录凭证
     * @return 用户json格式字符串
     */
    @Override
    public String queryUserStrByTicket(String ticket) {
        String key = REDIS_TICKET_KEY_PREFIX + ticket;
        String userJsonStr = redisService.get(key);
        if (StringUtils.isNotBlank(userJsonStr)) {
            redisService.expire(key, 3600);
            return userJsonStr;
        }
        return null;
    }

    /**
     * 注册
     *
     * @param user 注册信息
     */
    @Override
    public void register(User user) {
        //加密
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        userMapper.insertSelective(user);
    }

    /**
     * 登陆
     *
     * @param user 登陆信息
     * @return
     */
    @Override
    public String login(User user) throws Exception {
        String ticket = "";
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        List<User> list = userMapper.select(user);
        if (list != null && list.size() > 0) {
            User temp = list.get(0);
            //生成ticket
            ticket = DigestUtils.md5Hex(user.getUsername() + System.currentTimeMillis());
            //保存到redis中
            redisService.setex(REDIS_TICKET_KEY_PREFIX + ticket, 3600, MAPPER.writeValueAsString(temp));
        }
        return ticket;
    }

    /**
     * 退出登陆删除redis
     * @param cookieValue
     */
    @Override
    public void deleteRedisUser(String cookieValue) {
        redisService.del(REDIS_TICKET_KEY_PREFIX + cookieValue);
    }
}
