package com.cloudnote.note.dao.mapper;

import com.cloudnote.note.pojo.NoteAttachment;
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