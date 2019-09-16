package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NotePic;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NotePicExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotePicMapper {
    int countByExample(NotePicExample example);

    int deleteByExample(NotePicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NotePic record);

    int insertSelective(NotePic record);

    List<NotePic> selectByExample(NotePicExample example);

    NotePic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NotePic record, @Param("example") NotePicExample example);

    int updateByExample(@Param("record") NotePic record, @Param("example") NotePicExample example);

    int updateByPrimaryKeySelective(NotePic record);

    int updateByPrimaryKey(NotePic record);
}