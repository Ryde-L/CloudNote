package com.cloudnote.user.service;

import com.cloudnote.user.dto.ResponseDto;
import com.cloudnote.user.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
  * 用户表 Service 接口
 * </p>
 *
 *
 */
@Service
public interface UserService {


    /**
     * 根据用户名、密码获取用户
     * @param username 用户名
     * @param pwd 密码
     * @return 目标用户或null
     */
    User getUser(String username, String pwd);

    ResponseDto lock(Integer userId);
    ResponseDto unlock(Integer userId);

    List<User> selectList(int start, int length);

    int countAll();

    ResponseDto getById(Integer userId);
}