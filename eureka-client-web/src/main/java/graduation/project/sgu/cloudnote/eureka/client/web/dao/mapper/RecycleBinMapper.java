package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.RecycleBin;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecycleBinMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(RecycleBin record);

    int insertSelective(RecycleBin record);

    RecycleBin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecycleBin record);

    int updateByPrimaryKey(RecycleBin record);

    @Select("select * from recycle_bin where note_id=#{param} limit 0,1")
    RecycleBin selectByNoteId(Integer id);

    @Select("select * from recycle_bin where user_id=#{param}")
    List<RecycleBin> selectRecycleBinListByUserId(Integer userId);

}