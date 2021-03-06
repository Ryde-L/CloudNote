package com.cloudnote.note.service;

import com.cloudnote.common.dto.ResponseDto;
import com.cloudnote.common.pojo.NoteTag;

import java.io.IOException;

/**
 * <p>
  * 笔记标签表 Service 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
public interface NoteTagService {
    int insert(NoteTag noteTag);

    /**
     * 为笔记添加标签
     * @param noteId 笔记id
     * @param tags 标签数组
     * @return json
     */
    ResponseDto addTags(Integer noteId, String[] tags) throws Exception;

    /**
     * 通过标签id删除标签
     * @param id 标签id
     * @return json
     */
    public ResponseDto delByTagId(Integer id) throws IOException;

    /**
     * 清空笔记标签
     * @param noteId 笔记id
     * @return ResponseDto
     */
    public ResponseDto delAll(Integer noteId);

    /**
     * 获取笔记的全部标签
     * @param noteId 标签id
     * @return ResponseDto
     */
    ResponseDto getTags(Integer noteId);

    int deleteByNoteId(Integer noteId);

    ResponseDto delTag(Integer noteId, String tag);
}