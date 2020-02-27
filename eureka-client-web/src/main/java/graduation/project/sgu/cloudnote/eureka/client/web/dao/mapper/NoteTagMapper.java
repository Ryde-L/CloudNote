package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteTag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoteTag record);

    int insertSelective(NoteTag record);

    NoteTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoteTag record);

    int updateByPrimaryKey(NoteTag record);

    @Delete("delete from `note_tag` where note_id=#{param}")
    int deleteByNoteId(Integer noteId);

    @Select("select * from note_tag where note_id=#{param}")
    List<NoteTag> selectByNoteId(Integer noteId);

}