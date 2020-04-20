package com.cloudnote.user.service.impl;

import com.cloudnote.user.dao.mapper.AdministratorMapper;
import com.cloudnote.user.service.AdministratorService;
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