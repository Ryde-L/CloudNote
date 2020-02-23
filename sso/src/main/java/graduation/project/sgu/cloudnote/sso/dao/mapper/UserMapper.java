package graduation.project.sgu.cloudnote.sso.dao.mapper;

import graduation.project.sgu.cloudnote.sso.pojo.User;
import graduation.project.sgu.cloudnote.sso.pojo.UserExample;
import lombok.Value;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    @Insert("insert into user (id, name, pwd, phone, email, gender, sculpture, status)\n" +
            "    values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, #{pwd,jdbcType=VARCHAR}, \n" +
            "      #{phone,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, #{gender,jdbcType=INTEGER}, \n" +
            "      #{sculpture,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER})")
    @Options(useGeneratedKeys = true)
    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

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