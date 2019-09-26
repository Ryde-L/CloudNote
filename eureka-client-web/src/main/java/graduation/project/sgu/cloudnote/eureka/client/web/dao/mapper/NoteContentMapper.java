package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteContent;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteContentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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

    @Select("select * from note_content where note_id=#{param1} limit 0,1")
    NoteContent selectOneByNoteId(Integer noteId);

    @Update("update note_content set content=#{content,jdbcType=LONGVARCHAR} where id=#{id,jdbcType=INTEGER}")
    int update(NoteContent record);
}