package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteBook;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteBookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoteBook record);

    int insertSelective(NoteBook record);

    NoteBook selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoteBook record);

    int updateByPrimaryKey(NoteBook record);


    @Select("select * from note_book where id=#{param}")
    NoteBook get(Integer id);

    @Select("select * from note_book where user_id=#{param} and deletable=0 limit 0,1")
    NoteBook selectDefaultNoteBook(Integer userId);

    @Select("select * from note_book where id=#{param}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "noteList",column = "id",many = @Many(select = "graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.NoteMapper.selectByNoteBookId"))
        })
    NoteBook selectWithNoteList(Integer id);

    @Select("select * from note_book where user_id=#{param}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "noteList",column = "id",many = @Many(select = "graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.NoteMapper.selectByNoteBookId"))
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
            @Result(property = "noteList",column = "id",many = @Many(select = "graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper.NoteMapper.selectByNoteBookIdWithTags"))
    })
    NoteBook selectByIdContainsNoteListWithTags(Integer id);

}