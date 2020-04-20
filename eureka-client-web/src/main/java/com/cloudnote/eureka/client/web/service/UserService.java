package com.cloudnote.eureka.client.web.service;

import com.cloudnote.eureka.client.web.pojo.User;

/**
 * <p>
  * 用户表 Service 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
public interface UserService {

    /**
     * 检查是否是有效登陆信息
     * @param username 用户名
     * @param pwd 密码
     * @return 有效返回true，否则返回false
     */
    boolean isValidLogin(String username,String pwd);

    User getUser(String username, String pwd);
    User getUser(Integer id);
}