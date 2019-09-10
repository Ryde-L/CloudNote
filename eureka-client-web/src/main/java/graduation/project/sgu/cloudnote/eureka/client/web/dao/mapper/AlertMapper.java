package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.Alert;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.AlertExample;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface AlertMapper {
    int countByExample(AlertExample example);

    int deleteByExample(AlertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Alert record);

    int insertSelective(Alert record);

    List<Alert> selectByExample(AlertExample example);

    Alert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Alert record, @Param("example") AlertExample example);

    int updateByExample(@Param("record") Alert record, @Param("example") AlertExample example);

    int updateByPrimaryKeySelective(Alert record);

    int updateByPrimaryKey(Alert record);
}