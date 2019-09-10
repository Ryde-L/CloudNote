package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.RecycleBin;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.RecycleBinExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecycleBinMapper {
    int countByExample(RecycleBinExample example);

    int deleteByExample(RecycleBinExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RecycleBin record);

    int insertSelective(RecycleBin record);

    List<RecycleBin> selectByExample(RecycleBinExample example);

    RecycleBin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RecycleBin record, @Param("example") RecycleBinExample example);

    int updateByExample(@Param("record") RecycleBin record, @Param("example") RecycleBinExample example);

    int updateByPrimaryKeySelective(RecycleBin record);

    int updateByPrimaryKey(RecycleBin record);
}