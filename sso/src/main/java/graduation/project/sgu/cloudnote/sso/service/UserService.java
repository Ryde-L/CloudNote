package graduation.project.sgu.cloudnote.sso.service;

import graduation.project.sgu.cloudnote.sso.pojo.User;
import org.springframework.stereotype.Service;

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
    public User getUser(String username,String pwd);

}