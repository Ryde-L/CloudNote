package graduation.project.sgu.cloudnote.sso.dao.mapper;

import graduation.project.sgu.cloudnote.sso.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    @Insert("insert into user (id, name, pwd, gender, is_admin, status)\n" +
            "    values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, #{pwd,jdbcType=VARCHAR}, \n" +
            "    #{gender,jdbcType=INTEGER}, #{isAdmin,jdbcType=INTEGER}, #{status,jdbcType=INTEGER})")
    @Options(useGeneratedKeys = true)
    int insert(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(User record);

    /**
     * 通过用户名和密码查找用户
     * @param username 用户名
     * @param pwd 密码
     * @return 单个User对象或null
     */
    @Select("select * from user where name=#{param1} and pwd=#{param2} limit 0,1")
    User select(String username, String pwd);

    @Select("select * from user where name=#{param} limit 0,1")
    User isUsernameDuplicate(String username);

}