package com.cloudnote.note.service.impl;

import com.cloudnote.common.dto.ResponseDto;
import com.cloudnote.common.utils.CheckerUtil;
import com.cloudnote.common.utils.ResultUtil;
import com.cloudnote.note.dao.mapper.NoteTagMapper;
import com.cloudnote.common.pojo.NoteTag;
import com.cloudnote.note.service.NoteService;
import com.cloudnote.note.service.NoteTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
  * 笔记标签表 Service 接口实现
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@Service()
@Transactional(rollbackFor = Exception.class)
public class NoteTagServiceImpl implements NoteTagService {

    private static final Logger logger = LoggerFactory.getLogger(NoteTagServiceImpl.class);

    @Autowired
    NoteTagMapper noteTagMapper;

    @Autowired
    NoteService noteService;



    public int insert(NoteTag noteTag){
        return noteTagMapper.insert(noteTag);
    }

    /**
     * 为笔记添加标签
     *
     * @param noteId 笔记id
     * @param tags   标签数组
     * @return ResponseDto
     */
    public ResponseDto addTags(Integer noteId, String[] tags) {
        //TODO 用户操作验证
        if (CheckerUtil.checkNulls(noteId, tags)) return ResultUtil.error( "缺少参数");
        if (noteService.getNote(noteId) == null) return ResultUtil.error( "笔记无效");
        for (String tag : tags)
            insert(new NoteTag(null, noteId, tag));
        return ResultUtil.success("200");
    }

    /**
     * 通过标签id删除标签
     * @param id 标签id
     * @return json
     */
    public ResponseDto delByTagId(Integer id) {
        //TODO 用户操作验证
        if (CheckerUtil.checkNulls(id))
            return ResultUtil.error("缺少参数");
        if (noteTagMapper.selectByPrimaryKey(id) == null)
            return ResultUtil.error( "存在无效标签对象");
        noteTagMapper.deleteByPrimaryKey(id);
        return ResultUtil.success("");

    }

    /**
     * 清空笔记标签
     * @param noteId 笔记id
     * @return ResponseDto
     */
    public ResponseDto delAll(Integer noteId){
        if (CheckerUtil.checkNulls(noteId)) return ResultUtil.error("缺少参数");
        noteTagMapper.deleteByNoteId(noteId);
        return ResultUtil.success("");
    }

    /**
     * 获取笔记的全部标签
     * @param noteId
     * @return
     */
    public ResponseDto getTags(Integer noteId){
        if (CheckerUtil.checkNulls(noteId)) return ResultUtil.error("缺少参数");
        return ResultUtil.success("", noteTagMapper.selectByNoteId(noteId));
    }

    @Override
    public int deleteByNoteId(Integer noteId) {
        return noteTagMapper.deleteByNoteId(noteId);
    }

    @Override
    public ResponseDto delTag(Integer noteId, String tag) {
        return ResultUtil.success("", noteTagMapper.delByNoteIdAndTag(noteId,tag));
    }

}