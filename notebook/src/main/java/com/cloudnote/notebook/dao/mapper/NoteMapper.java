package com.cloudnote.notebook.dao.mapper;

import com.cloudnote.notebook.pojo.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteMapper {


    @Select("select * from note where note_book_id=#{param}")
    List<Note> selectByNoteBookId(Integer noteBookId);


    /**
     * 根据笔记本id获取包含标签的笔记集合
     * @param noteBookId 笔记本id
     * @return List<Note>
     */
    @Select("select id as myId,title,note_book_id,is_has_pwd,pwd,is_has_remind,remind from note where note_book_id=#{param}")
    @Results({
            @Result(property = "id",column = "myId"),
            @Result(property = "noteTagList",column = "myId",
                    many=@Many(select = "com.cloudnote.notebook.dao.mapper.NoteTagMapper.selectByNoteId"))
    })
    List<Note> selectByNoteBookIdWithTags(Integer noteBookId);




}