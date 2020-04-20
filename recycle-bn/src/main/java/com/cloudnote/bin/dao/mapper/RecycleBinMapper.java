package com.cloudnote.bin.dao.mapper;

import com.cloudnote.bin.pojo.RecycleBin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecycleBinMapper {

    @Delete(" delete from recycle_bin where id = #{id,jdbcType=INTEGER}")
    int deleteByPrimaryKey(Integer id);

    @Insert(" insert into recycle_bin (id, user_id, note_id, \n" +
            "      note_book_id, note_title, throw_away_time, \n" +
            "      clear_time)\n" +
            "    values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, #{noteId,jdbcType=INTEGER}, \n" +
            "      #{noteBookId,jdbcType=INTEGER}, #{noteTitle,jdbcType=VARCHAR}, #{throwAwayTime,jdbcType=TIMESTAMP}, \n" +
            "      #{clearTime,jdbcType=TIMESTAMP})")
    @Options(useGeneratedKeys = true)
    int insert(RecycleBin record);

    @Select("select * from recycle_bin where id = #{id,jdbcType=INTEGER}")
    RecycleBin selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(RecycleBin record);

    @Select("select * from recycle_bin where note_id=#{param} limit 0,1")
    RecycleBin selectByNoteId(Integer id);

    @Select("select * from recycle_bin where user_id=#{param}")
    List<RecycleBin> selectRecycleBinListByUserId(Integer userId);

}