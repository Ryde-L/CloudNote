package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NotePic;
import org.springframework.stereotype.Repository;


@Repository
public interface NotePicMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(NotePic record);

    int insertSelective(NotePic record);

    NotePic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NotePic record);

    int updateByPrimaryKey(NotePic record);
}