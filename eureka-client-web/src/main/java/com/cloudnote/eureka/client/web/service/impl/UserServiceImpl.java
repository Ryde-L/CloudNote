package com.cloudnote.eureka.client.web.service.impl;

import com.cloudnote.eureka.client.web.dao.mapper.UserMapper;
import com.cloudnote.eureka.client.web.service.NoteBookService;
import com.cloudnote.eureka.client.web.service.UserService;
import com.cloudnote.eureka.client.web.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
  * 用户表 Service 接口实现
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;

    @Autowired
    NoteBookService noteBookService;

    /**
     * 检查是否是有效登陆信息
     * @param username 用户名
     * @param pwd 密码
     * @return 有效返回true，否则返回false
     */
    public boolean isValidLogin(String username, String pwd) {
        User user = userMapper.select(username, pwd);
        return !(user==null);
    }

    public User getUser(String username, String pwd){
        return userMapper.select(username, pwd);
    }


    public User getUser(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }
}