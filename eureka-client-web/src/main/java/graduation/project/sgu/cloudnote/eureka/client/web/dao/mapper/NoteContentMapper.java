package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteContent;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteContentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteContentMapper {
    int countByExample(NoteContentExample example);

    int deleteByExample(NoteContentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NoteContent record);

    int insertSelective(NoteContent record);

    List<NoteContent> selectByExampleWithBLOBs(NoteContentExample example);

    List<NoteContent> selectByExample(NoteContentExample example);

    NoteContent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NoteContent record, @Param("example") NoteContentExample example);

    int updateByExampleWithBLOBs(@Param("record") NoteContent record, @Param("example") NoteContentExample example);

    int updateByExample(@Param("record") NoteContent record, @Param("example") NoteContentExample example);

    int updateByPrimaryKeySelective(NoteContent record);

    int updateByPrimaryKeyWithBLOBs(NoteContent record);

    int updateByPrimaryKey(NoteContent record);
}