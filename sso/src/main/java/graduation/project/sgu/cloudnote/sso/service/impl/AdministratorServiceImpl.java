package graduation.project.sgu.cloudnote.sso.service.impl;

import graduation.project.sgu.cloudnote.sso.dao.mapper.AdministratorMapper;
import graduation.project.sgu.cloudnote.sso.pojo.Administrator;
import graduation.project.sgu.cloudnote.sso.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
  *  Service 接口实现
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@Service()
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    AdministratorMapper administratorMapper;

    public int insert(Administrator administrator) {
        return administratorMapper.insert(administrator);
    }

    public Administrator getAdministrator(String username, String pwd) {
        return administratorMapper.selectByNameAndPwd(username,pwd);
    }
}