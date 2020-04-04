package graduation.project.sgu.cloudnote.administrator.service.impl;

import graduation.project.sgu.cloudnote.administrator.dao.mapper.AdministratorMapper;
import graduation.project.sgu.cloudnote.administrator.dto.ResponseDto;
import graduation.project.sgu.cloudnote.administrator.service.AdministratorService;
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

}