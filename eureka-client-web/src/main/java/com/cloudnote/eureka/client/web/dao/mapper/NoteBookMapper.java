package com.cloudnote.eureka.client.web.dao.mapper;

import com.cloudnote.eureka.client.web.pojo.NoteBook;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteBookMapper {
    @Delete("delete from note_book where id=#{param}")
    int deleteByPrimaryKey(Integer id);

    @Insert("insert into note_book (id, user_id, title, deletable)\n" +
            "    values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, #{title,jdbcType=VARCHAR}, \n" +
            "      #{deletable,jdbcType=INTEGER})")
    @Options(useGeneratedKeys = true)
    int insert(NoteBook record);

    @Select("select * from note_book where id=#{param} limit 0,1")
    NoteBook selectByPrimaryKey(Integer id);

    @Select("select * from note_book where id=#{param}")
    NoteBook get(Integer id);

    @Select("select * from note_book where user_id=#{param} and deletable=0 limit 0,1")
    NoteBook selectDefaultNoteBook(Integer userId);

    @Select("select * from note_book where id=#{param}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "noteList",column = "id",many = @Many(select = "com.cloudnote.eureka.client.web.dao.mapper.NoteMapper.selectByNoteBookId"))
        })
    NoteBook selectWithNoteList(Integer id);

    @Select("select * from note_book where user_id=#{param}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "noteList",column = "id",many = @Many(select = "com.cloudnote.eureka.client.web.dao.mapper.NoteMapper.selectByNoteBookId"))
    })
    List<NoteBook>  selectUserNoteBooksWithNoteList(Integer userId);

    @Select("select * from note_book where user_id=#{param}")
    List<NoteBook>  selectUserNoteBooks(Integer userId);

    /**
     * 根据笔记本id获取包含标签的笔记集合
     * @param id 笔记本id
     * @return NoteBook
     */
    @Select("select * from note_book where id=#{param}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "noteList",column = "id",
                    many = @Many(select = "com.cloudnote.eureka.client." +
                            "web.dao.mapper.NoteMapper.selectByNoteBookIdWithTags"))
    })
    NoteBook selectByIdContainsNoteListWithTags(Integer id);

}