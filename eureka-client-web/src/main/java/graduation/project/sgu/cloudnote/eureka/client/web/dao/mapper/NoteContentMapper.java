package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteContent;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Repository
public interface NoteContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoteContent record);

    int insertSelective(NoteContent record);

    NoteContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoteContent record);

    int updateByPrimaryKeyWithBLOBs(NoteContent record);

    int updateByPrimaryKey(NoteContent record);

    @Select("select * from note_content where note_id=#{param1} limit 0,1")
    NoteContent selectOneByNoteId(Integer noteId);

    @Update("update note_content set content=#{content,jdbcType=LONGVARCHAR} where id=#{id,jdbcType=INTEGER}")
    int update(NoteContent record);
}