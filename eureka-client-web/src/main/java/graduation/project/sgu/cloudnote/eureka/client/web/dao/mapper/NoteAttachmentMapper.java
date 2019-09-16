package graduation.project.sgu.cloudnote.eureka.client.web.dao.mapper;

import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteAttachment;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteAttachmentExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteAttachmentMapper {
    int countByExample(NoteAttachmentExample example);

    int deleteByExample(NoteAttachmentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NoteAttachment record);

    int insertSelective(NoteAttachment record);

    List<NoteAttachment> selectByExample(NoteAttachmentExample example);

    NoteAttachment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NoteAttachment record, @Param("example") NoteAttachmentExample example);

    int updateByExample(@Param("record") NoteAttachment record, @Param("example") NoteAttachmentExample example);

    int updateByPrimaryKeySelective(NoteAttachment record);

    int updateByPrimaryKey(NoteAttachment record);
}