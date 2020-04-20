package com.cloudnote.note.dao.mapper;

import com.cloudnote.note.dto.ResponseDto;
import com.cloudnote.note.pojo.NoteTag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteTagMapper {
    @Delete(" delete from note_tag where id = #{id,jdbcType=INTEGER}")
    int deleteByPrimaryKey(Integer id);

    @Insert("insert into note_tag (id, note_id, tag)\n" +
            "    values (#{id,jdbcType=INTEGER}, #{noteId,jdbcType=INTEGER}, #{tag,jdbcType=VARCHAR})")
    @Options(useGeneratedKeys = true)
    int insert(NoteTag record);

    @Select(" select * from note_tag where id = #{id,jdbcType=INTEGER}")
    NoteTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoteTag record);

    int updateByPrimaryKey(NoteTag record);

    @Delete("delete from `note_tag` where note_id=#{param}")
    int deleteByNoteId(Integer noteId);

    @Select("select * from note_tag where note_id=#{param}")
    List<NoteTag> selectByNoteId(Integer noteId);

    @Delete("delete from note_tag where note_id=#{param1} and tag=#{param2}")
    int delByNoteIdAndTag(Integer noteId, String tag);
}