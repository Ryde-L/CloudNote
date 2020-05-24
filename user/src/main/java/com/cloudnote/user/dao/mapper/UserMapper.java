package com.cloudnote.user.dao.mapper;

import com.cloudnote.common.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    @Insert("insert into user (id, name, pwd, gender, status)\n" +
            "    values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, #{pwd,jdbcType=VARCHAR}, \n" +
            "    #{gender,jdbcType=INTEGER},  #{status,jdbcType=INTEGER})")
    @Options(useGeneratedKeys = true)
    int insert(User record);

    @Select("select * from user where id=#{param}")
    User selectByPrimaryKey(Integer id);

    @Update("update user set name=#{name,jdbcType=VARCHAR},pwd= #{pwd,jdbcType=VARCHAR},\n" +
            "gender=#{gender,jdbcType=INTEGER}, status= #{status,jdbcType=INTEGER} where id=#{id,jdbcType=INTEGER}")
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

    @Select("select * from user limit #{param1},#{param2}")
    List<User> selectList(int start, int length);

    @Select("select count(id) from user")
    int countAll();
}