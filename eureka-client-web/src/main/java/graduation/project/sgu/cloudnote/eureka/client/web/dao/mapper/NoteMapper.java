package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.Note;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteMapper {
    int countByExample(NoteExample example);

    int deleteByExample(NoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Note record);

    int insertSelective(Note record);

    List<Note> selectByExample(NoteExample example);

    Note selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Note record, @Param("example") NoteExample example);

    int updateByExample(@Param("record") Note record, @Param("example") NoteExample example);

    int updateByPrimaryKeySelective(Note record);

    int updateByPrimaryKey(Note record);
}