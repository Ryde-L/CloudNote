package graduation.project.sgu.cloudnote.sso.service;

import graduation.project.sgu.cloudnote.sso.pojo.Administrator;
import org.springframework.stereotype.Service;

/**
 * <p>
  *  Service 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@Service
public interface AdministratorService {
    int insert(Administrator administrator);

    /**
     * 根据用户名、密码获取管理员
     * @param username 用户名
     * @param pwd 密码
     * @return 目标管理员或null
     */
    Administrator getAdministrator(String username, String pwd);

}