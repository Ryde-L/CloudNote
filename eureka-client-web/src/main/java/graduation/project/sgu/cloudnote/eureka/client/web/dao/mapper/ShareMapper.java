package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.Share;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Repository
public interface ShareMapper {

    int deleteByPrimaryKey(Integer id);

    @Insert("insert into share (id, user_id, note_id, \n" +
            "      link, isHasPwd, pwd, \n" +
            "      limit_type, limit_content, create_time, \n" +
            "      status)\n" +
            "    values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, #{noteId,jdbcType=INTEGER}, \n" +
            "      #{link,jdbcType=VARCHAR}, #{ishaspwd,jdbcType=INTEGER}, #{pwd,jdbcType=VARCHAR}, \n" +
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
            "      isHasPwd = #{ishaspwd,jdbcType=INTEGER},\n" +
            "      pwd = #{pwd,jdbcType=VARCHAR},\n" +
            "      limit_type = #{limitType,jdbcType=INTEGER},\n" +
            "      limit_content = #{limitContent,jdbcType=INTEGER},\n" +
            "      create_time = #{createTime,jdbcType=TIMESTAMP},\n" +
            "      status = #{status,jdbcType=INTEGER}\n" +
            "    where id = #{id,jdbcType=INTEGER}")
    int updateByPrimaryKey(Share record);

    @Select("select * from share where link=#{param} limit 0,1")
    Share select(String link);
}