package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.Note;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteBook;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteExample;
import org.apache.ibatis.annotations.*;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteMapper {
    int countByExample(NoteExample example);

    int deleteByExample(NoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Note record);

    int insertSelective(Note record);

    List<Note> selectByExample(NoteExample example);

    Note selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Note record, @Param("example") NoteExample example);

    int updateByExample(@Param("record") Note record, @Param("example") NoteExample example);

    int updateByPrimaryKeySelective(Note record);

    int updateByPrimaryKey(Note record);

    /**
     * 根据标签模糊匹配笔记
     *
     * @param tag 标签
     * @return 笔记集合
     */
    @Select("select n.id,n.title,n.note_book_id from note n left join note_tag ng on n.id=ng.note_id where ng.tag like  CONCAT('%',#{param},'%') ")
    List<Note> selectByTag( String tag);

    @Select("select * from note where note_book_id=#{param}")
    List<Note> selectByNoteBookId(Integer noteBookId);

    /**
     * 获取Note，包含笔记内容和笔记本相关的东西
     * @param id 笔记id
     * @return Note对象
     */
    @Select("select n.id,note_book_id,title,content from note n left join note_content nc on n.id=nc.note_id where n.id=#{param}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "noteBookId", column = "note_book_id"),
            @Result(property = "noteBook", column = "note_book_id", one = @One(select = "graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.NoteBookMapper.get"))
    })
    Note selectByIdWithNoteBookAndContent(Integer id);

    /**
     * 根据笔记本id获取包含标签的笔记集合
     * @param noteBookId 笔记本id
     * @return List<Note>
     */
    @Select("select id as myId,title,note_book_id from note where note_book_id=#{param}")
    @Results({
            @Result(property = "id",column = "myId"),
            @Result(property = "noteTagList",column = "myId",many=@Many(select = "graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.NoteTagMapper.selectByNoteId"))
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