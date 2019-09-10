package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.Share;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.ShareExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShareMapper {
    int countByExample(ShareExample example);

    int deleteByExample(ShareExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Share record);

    int insertSelective(Share record);

    List<Share> selectByExample(ShareExample example);

    Share selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Share record, @Param("example") ShareExample example);

    int updateByExample(@Param("record") Share record, @Param("example") ShareExample example);

    int updateByPrimaryKeySelective(Share record);

    int updateByPrimaryKey(Share record);
}