package com.cloudnote.note.dao.mapper;

import com.cloudnote.common.pojo.Share;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


@Repository
public interface ShareMapper {

    int deleteByPrimaryKey(Integer id);

    @Insert("insert into share (id, user_id, note_id, \n" +
            "      link, is_has_pwd, pwd, \n" +
            "      limit_type, limit_content, create_time, \n" +
            "      status)\n" +
            "    values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, #{noteId,jdbcType=INTEGER}, \n" +
            "      #{link,jdbcType=VARCHAR}, #{isHasPwd,jdbcType=INTEGER}, #{pwd,jdbcType=VARCHAR}, \n" +
            "      #{limitType,jdbcType=INTEGER}, #{limitContent,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, \n" +
            "      #{status,jdbcType=INTEGER})")
    @Options(useGeneratedKeys = true)
    int insert(Share record);

    @Select("select * from share where id = #{id,jdbcType=INTEGER}")
    Share selectByPrimaryKey(Integer id);

    @Update(" update share\n" +
            "    set user_id = #{userId,jdbcType=INTEGER},\n" +
            "      note_id = #{noteId,jdbcType=INTEGER},\n" +
            "      link = #{link,jdbcType=VARCHAR},\n" +
            "      isHasPwd = #{is_has_pwd,jdbcType=INTEGER},\n" +
            "      pwd = #{pwd,jdbcType=VARCHAR},\n" +
            "      limit_type = #{limitType,jdbcType=INTEGER},\n" +
            "      limit_content = #{limitContent,jdbcType=INTEGER},\n" +
            "      create_time = #{createTime,jdbcType=TIMESTAMP},\n" +
            "      status = #{status,jdbcType=INTEGER}\n" +
            "    where id = #{id,jdbcType=INTEGER}")
    int updateByPrimaryKey(Share record);

    @Select("select * from share where link=#{param} limit 0,1")
    Share select(String link);

    @Delete("delete from share where note_id=#{param}")
    int deleteByNoteId(Integer noteId);
}