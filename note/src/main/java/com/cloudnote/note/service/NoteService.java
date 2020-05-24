package com.cloudnote.note.service;

import com.cloudnote.common.dto.ResponseDto;
import com.cloudnote.common.pojo.Note;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 笔记表 Service 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@Service
public interface NoteService {

    Note getNote(Integer id);

    int delete(Integer id);

    int insert(Note note);

    /**
     * 创建笔记
     *
     * @param noteBookId 笔记本id
     * @param title      笔记标题
     * @param content    笔记内容
     * @return json
     */
    ResponseDto createNote(Integer noteBookId, String title, String content);

    /**
     * 更新
     *
     * @param userId  用户id
     * @param noteId  笔记id
     * @param title   笔记标题
     * @param content 笔记内容
     * @return ResponseDto
     */
    ResponseDto update(Integer userId, Integer noteId, String title, String content);

    /**
     * 根据笔记本id获取笔记本里的笔记列表
     *
     * @param noteBookId 笔记本id
     * @return json {"msg":"","isSuccessful":"1","data":{"id":1,"userId":4,"title":"","deletable":0,"noteList":[{"id":1,"noteBookId":1,"title":""},]}}
     */
    ResponseDto getNoteBookList(Integer noteBookId);

    /**
     * 根据笔记id获取笔记里内容
     *
     * @param id 笔记id
     * @return json
     */
    ResponseDto getContent(Integer id);

    /**
     * 通过笔记标签模糊匹配出笔记
     *
     * @param tag 标签
     * @param userId 用户Id
     * @return ResponseDto
     */
    ResponseDto getNoteListByTag(String tag, Integer userId);


    /**
     * 获取笔记详情
     *
     * @param userId 用户id
     * @param id     笔记id
     * @param pwd     笔记密码
     * @param isAdmin  是否管理员，true是
     * @return ResponseDto
     */
    ResponseDto getNoteWithNoteBookAndContent(Integer userId, Integer id, String pwd, String isAdmin);

    /**
     * 获取包含NoteBook的Note
     * @param userId 用户id
     * @param noteId 笔记id
     * @return ResponseDto
     */
    ResponseDto<Note> getUserNoteWithNoteBookByUserIdAndNoteId(Integer userId, Integer noteId);

    /**
     * 笔记解密
     * @param userId 用户id
     * @param noteId 笔记id
     * @param pwd 笔记密码
     * @return ResponseDto
     */
    ResponseDto unlock(Integer userId, Integer noteId, String pwd);

    /**
     * 笔记加密
     * @param userId 用户id
     * @param noteId 笔记id
     * @param pwd 笔记密码
     * @return ResponseDto
     */
    ResponseDto lock(Integer userId, Integer noteId, String pwd);

    /**
     * 永久删除
     * @param noteId 笔记id
     * @return ResponseDto
     */
    ResponseDto deleteForever(Integer noteId);

    List<Map> selectList(int start, int length);

    int countAll();
}