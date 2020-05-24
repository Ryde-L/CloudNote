package com.cloudnote.sso.dao.mapper;

import com.cloudnote.common.pojo.Administrator;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@Repository
public interface AdministratorMapper {

    @Insert("insert into administrator (id, name, pwd)\n" +
            "    values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, #{pwd,jdbcType=VARCHAR})")
    @Options(useGeneratedKeys = true)
    int insert(Administrator record);

    /**
     * 通过用户名和密码查找管理员
     * @param username 用户名
     * @param pwd 密码
     * @return 单个Administrator对象或null
     */
    @Select("select * from administrator where name=#{param1} and pwd=#{param2} limit 0,1")
    Administrator selectByNameAndPwd(String username, String pwd);
}