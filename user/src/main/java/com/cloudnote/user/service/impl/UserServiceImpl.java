package com.cloudnote.user.service.impl;

import com.cloudnote.user.dao.mapper.UserMapper;
import com.cloudnote.user.dto.ResponseDto;
import com.cloudnote.user.pojo.User;
import com.cloudnote.user.service.UserService;
import com.cloudnote.user.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;


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


    @Autowired
    UserMapper userMapper;

    @Autowired
    RestTemplate restTemplate;

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
     * 用户封号
     * @param userId 用户id
     */
    public ResponseDto lock(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user==null) return ResultUtil.error("用户不存在");
        user.setStatus(0);
        userMapper.updateByPrimaryKey(user);
        restTemplate.getForEntity("http://sso/user/tokenInvalidateByUserId?userId=" + user.getId(), String.class);
        return ResultUtil.success();
    }

    /**
     * 用户解封
     * @param userId 用户id
     */
    public ResponseDto unlock(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user==null) return ResultUtil.error("用户不存在");
        user.setStatus(1);
        userMapper.updateByPrimaryKey(user);
        return ResultUtil.success();
    }

    @Override
    public List<User> selectList(int start, int length) {
        return userMapper.selectList(start,length);
    }

    @Override
    public int countAll() {
        return userMapper.countAll();
    }

    @Override
    public ResponseDto getById(Integer userId) {
        return ResultUtil.success("",userMapper.selectByPrimaryKey(userId));
    }
}