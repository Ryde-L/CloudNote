package graduation.project.sgu.cloudnote.sso.service.impl;

import graduation.project.sgu.cloudnote.sso.dao.mapper.UserMapper;
import graduation.project.sgu.cloudnote.sso.pojo.User;
import graduation.project.sgu.cloudnote.sso.service.UserService;
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
    public User getUser(String username,String pwd){
         return userMapper.select(username, pwd);
    }
}