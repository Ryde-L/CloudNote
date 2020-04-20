package com.cloudnote.note.dao.mapper;

import com.cloudnote.note.pojo.NoteBook;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteBookMapper {
    @Select("select * from note_book where id=#{param}")
    NoteBook get(Integer id);
}