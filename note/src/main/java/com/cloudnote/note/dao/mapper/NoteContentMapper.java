package com.cloudnote.note.dao.mapper;

import com.cloudnote.common.pojo.NoteContent;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


@Repository
public interface NoteContentMapper {
    int deleteByPrimaryKey(Integer id);

    @Insert("insert into note_content (id, note_id, content)\n" +
            "    values (#{id,jdbcType=INTEGER}, #{noteId,jdbcType=INTEGER}, #{content,jdbcType=LONGVARCHAR} )")
    @Options(useGeneratedKeys = true)
    int insert(NoteContent record);

    @Select("select * from note_content where note_id=#{param1} limit 0,1")
    NoteContent selectOneByNoteId(Integer noteId);

    @Update("update note_content set content=#{content,jdbcType=LONGVARCHAR} where id=#{id,jdbcType=INTEGER}")
    int update(NoteContent record);

    @Delete("delete from note_content where note_id=#{param}")
    int deleteByNoteId(Integer noteId);

    @Select("SELECT id FROM note_content where content REGEXP #{param} limit 0,1")
    Integer isContainTarget(String target);
}