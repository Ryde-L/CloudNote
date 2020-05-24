package com.cloudnote.sso.service.impl;

import com.cloudnote.sso.dao.mapper.UserMapper;
import com.cloudnote.common.pojo.User;
import com.cloudnote.sso.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
  * 用户表 Service 接口实现
 * </p>
 *
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    /**
     * 根据用户名、密码获取用户
     * @param username 用户名
     * @param pwd 密码
     * @return 目标用户或null
     */
    public User getUser(String username, String pwd){
         return userMapper.select(username, pwd);
    }

    /**
     * 用户名有效性检查
     * @param username 用户名
     * @return true有效；false无效
     */
    public boolean usernameValidate(String username){
         return userMapper.isUsernameDuplicate(username)==null;
    }

    /**
     * 用户名有效性检查
     * @param user 用户
     * @return 受影响行数
     */
    public int insert(User user){
        return userMapper.insert(user);
    }
}