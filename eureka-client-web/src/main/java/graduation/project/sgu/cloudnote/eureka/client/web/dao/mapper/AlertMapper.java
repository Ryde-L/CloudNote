package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.Alert;
import org.springframework.stereotype.Repository;


@Repository
public interface AlertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Alert record);

    int insertSelective(Alert record);

    Alert selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Alert record);

    int updateByPrimaryKey(Alert record);
}