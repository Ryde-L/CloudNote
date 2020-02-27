package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 通过用户名和密码查找用户
     * @param username 用户名
     * @param pwd 密码
     * @return 单个User对象或null
     */
    @Select("select * from user where name=#{param1} and pwd=#{param2} limit 0,1")
    User select(String username,String pwd);

}