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

    int insert(User user);

    /**
     * 根据用户名、密码获取用户
     * @param username 用户名
     * @param pwd 密码
     * @return 目标用户或null
     */
    User getUser(String username,String pwd);

    /**
     * 用户名有效性检查
     * @param username 用户名
     * @return true有效；false无效
     */
    boolean usernameValidate(String username);

}