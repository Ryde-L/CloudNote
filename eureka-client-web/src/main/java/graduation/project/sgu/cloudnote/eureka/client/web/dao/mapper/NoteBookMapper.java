package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.NoteBook;
import pojo.NoteBookExample;

import java.util.List;

public interface NoteBookMapper {
    int countByExample(NoteBookExample example);

    int deleteByExample(NoteBookExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NoteBook record);

    int insertSelective(NoteBook record);

    List<NoteBook> selectByExample(NoteBookExample example);

    NoteBook selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NoteBook record, @Param("example") NoteBookExample example);

    int updateByExample(@Param("record") NoteBook record, @Param("example") NoteBookExample example);

    int updateByPrimaryKeySelective(NoteBook record);

    int updateByPrimaryKey(NoteBook record);
}