package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface NoteMapper {

    @Delete("delete from note where id = #{id,jdbcType=INTEGER}")
    int deleteByPrimaryKey(Integer id);

    @Insert(" insert into note (id, note_book_id, title, is_has_pwd, pwd,is_has_remind,remind)\n" +
            "    values (#{id,jdbcType=INTEGER}, #{noteBookId,jdbcType=INTEGER}, #{title,jdbcType=VARCHAR}, \n" +
            "      #{isHasPwd,jdbcType=INTEGER}, #{pwd,jdbcType=VARCHAR},#{isHasRemind,jdbcType=INTEGER}, #{remind,jdbcType=TIMESTAMP})")
    @Options(useGeneratedKeys = true)
    int insert(Note record);

    @Select(" select * from note where id = #{id,jdbcType=INTEGER}")
    Note selectByPrimaryKey(Integer id);

    @Update("update note\n" +
            "    set note_book_id = #{noteBookId,jdbcType=INTEGER},\n" +
            "      title = #{title,jdbcType=VARCHAR},\n" +
            "      is_has_pwd = #{isHasPwd,jdbcType=INTEGER},\n" +
            "      pwd = #{pwd,jdbcType=VARCHAR},\n" +
            "      is_has_remind = #{isHasRemind,jdbcType=INTEGER},\n" +
            "      remind = #{remind,jdbcType=TIMESTAMP}\n" +
            "    where id = #{id,jdbcType=INTEGER}")
    int updateByPrimaryKey(Note record);

    /**
     * 根据标签模糊匹配笔记
     *
     * @param tag 标签
     * @return 笔记集合
     */
    @Select("select n.id,n.title,n.note_book_id ,is_has_pwd,pwd,is_has_remind,remind from note n " +
            "left join note_tag ng on n.id=ng.note_id " +
            "left join note_book nb on n.note_book_id=nb.id " +
            "where ng.tag like  CONCAT('%',#{param1},'%') " +
            "and nb.user_id=#{param2}")
    Set<Note> selectByTag(String tag, Integer userId);

    @Select("select * from note where note_book_id=#{param}")
    List<Note> selectByNoteBookId(Integer noteBookId);

    /**
     * 获取Note，包含笔记内容和笔记本相关的东西
     * @param id 笔记id
     * @return Note对象
     */
    @Select("select n.id,note_book_id,title,is_has_pwd,pwd,is_has_remind,remind,content from note n " +
            "left join note_content nc on n.id=nc.note_id where n.id=#{param}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "noteBookId", column = "note_book_id"),
            @Result(property = "noteBook", column = "note_book_id",
                    one = @One(select = "graduation.project.sgu.cloudnote.eureka.client." +
                            "web.dao.mapper.NoteBookMapper.get"))
    })
    Note selectByIdWithNoteBookAndContent(Integer id);

    /**
     * 根据笔记本id获取包含标签的笔记集合
     * @param noteBookId 笔记本id
     * @return List<Note>
     */
    @Select("select id as myId,title,note_book_id,is_has_pwd,pwd,is_has_remind,remind from note where note_book_id=#{param}")
    @Results({
            @Result(property = "id",column = "myId"),
            @Result(property = "noteTagList",column = "myId",
                    many=@Many(select = "graduation.project.sgu.cloudnote.eureka.client." +
                            "web.dao.mapper.NoteTagMapper.selectByNoteId"))
    })
    List<Note> selectByNoteBookIdWithTags(Integer noteBookId);

    /**
     * 获取包含笔记本的笔记
     */
    @Select("select * from note where id=#{param}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "noteBookId",column = "note_book_id"),
            @Result(property = "noteBook",column = "note_book_id",one = @One(select = "graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.NoteBookMapper.get"))
    })
    Note selectWithNoteBook(Integer noteId);



}