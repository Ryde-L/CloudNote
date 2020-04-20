package com.cloudnote.notebook.dao.mapper;

import com.cloudnote.notebook.pojo.NoteTag;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteTagMapper {

    @Select("select * from note_tag where note_id=#{param}")
    List<NoteTag> selectByNoteId(Integer noteId);

}