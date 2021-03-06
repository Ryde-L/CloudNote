package com.cloudnote.note.service.impl;

import com.cloudnote.note.dao.mapper.NoteContentMapper;
import com.cloudnote.common.pojo.NoteContent;
import com.cloudnote.note.service.NoteContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
  * 笔记内容表 Service 接口实现
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@Service()
public class NoteContentServiceImpl implements NoteContentService {

    @Autowired
    NoteContentMapper noteContentMapper;

    public int insert(NoteContent noteContent){
        return noteContentMapper.insert(noteContent);
    }
    public int update(NoteContent noteContent){
        return noteContentMapper.update(noteContent);
    }
    /**
     * 通过笔记id获取笔记内容
     * @param noteId 笔记id
     * @return NoteContent对象
     */
    public NoteContent getNoteContent(Integer noteId){
        return  noteContentMapper.selectOneByNoteId(noteId);
    }

    @Override
    public int deleteByNoteId(Integer noteId) {
        return noteContentMapper.deleteByNoteId(noteId);
    }

    @Override
    public Integer isContainTarget(String target) {
        return noteContentMapper.isContainTarget(target);
    }



}