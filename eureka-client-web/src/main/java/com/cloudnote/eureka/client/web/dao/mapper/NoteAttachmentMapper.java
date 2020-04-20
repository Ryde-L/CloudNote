package com.cloudnote.eureka.client.web.dao.mapper;

import com.cloudnote.eureka.client.web.pojo.NoteAttachment;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoteAttachment record);

    int insertSelective(NoteAttachment record);

    NoteAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoteAttachment record);

    int updateByPrimaryKey(NoteAttachment record);
}