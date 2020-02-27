package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteAttachment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoteAttachment record);

    int insertSelective(NoteAttachment record);

    NoteAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoteAttachment record);

    int updateByPrimaryKey(NoteAttachment record);
}