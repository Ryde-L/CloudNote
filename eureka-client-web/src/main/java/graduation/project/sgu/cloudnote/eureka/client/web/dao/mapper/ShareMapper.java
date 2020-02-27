package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.Share;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
public interface ShareMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Share record);

    int insertSelective(Share record);

    Share selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Share record);

    int updateByPrimaryKey(Share record);

    @Select("select * from share where link=#{param} limit 0,1")
    Share select(String link);
}