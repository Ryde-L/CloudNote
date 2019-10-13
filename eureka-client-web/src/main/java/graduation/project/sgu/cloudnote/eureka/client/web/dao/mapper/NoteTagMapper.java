package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteTag;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteTagExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteTagMapper {
    int countByExample(NoteTagExample example);

    int deleteByExample(NoteTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NoteTag record);

    int insertSelective(NoteTag record);

    List<NoteTag> selectByExample(NoteTagExample example);

    NoteTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NoteTag record, @Param("example") NoteTagExample example);

    int updateByExample(@Param("record") NoteTag record, @Param("example") NoteTagExample example);

    int updateByPrimaryKeySelective(NoteTag record);

    int updateByPrimaryKey(NoteTag record);

    @Delete("delete from `note_tag` where note_id=#{param}")
    int deleteByNoteId(Integer noteId);

    @Select("select * from note_tag where note_id=#{param}")
    List<NoteTag> selectByNoteId(Integer noteId);

}