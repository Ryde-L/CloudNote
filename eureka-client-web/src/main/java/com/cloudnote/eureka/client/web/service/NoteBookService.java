package com.cloudnote.eureka.client.web.service;

import com.cloudnote.eureka.client.web.dto.ResponseDto;
import com.cloudnote.eureka.client.web.pojo.NoteBook;

/**
 * <p>
  * 笔记本表 Service 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
public interface NoteBookService {

    int insert(NoteBook noteBook);

    /**
     * 创建一个新的笔记本
     *
     * @param userId 用户id
     * @param title  笔记本标题
     * @return {"msg":"消息内容","isSuccessful":"0|1"}
     */
    ResponseDto createNoteBook(Integer userId, String title);

    NoteBook getNoteBook(Integer id);

    /**
     * 根据笔记本id获取包含笔记集合和标签集合的笔记本
     *
     * @param id 笔记本id
     * @return 包含NoteList的NoteBook对象
     */
    ResponseDto getContainNoteListWithTag(Integer id);

    /**
     * 获取用户的默认笔记本
     * @param userId 用户id
     * @return 用户默认的笔记本
     */
    NoteBook getDefaultNoteBook(Integer userId);

    /**
     * 删除笔记本
     * @param userId 用户id
     * @param id 笔记本id
     * @return ResponseDto
     */
    public ResponseDto remove(Integer userId,Integer id);


    /**
     * 获取用户的所有笔记本和笔记
     * @param userId 用户id
     * @return ResponseDto
     */
    public ResponseDto getNoteBooksWithNoteList(Integer userId);

    /**
     * 获取用户的所有笔记本
     * @param userId 用户id
     * @return ResponseDto
     */
    public ResponseDto getNoteBooks(Integer userId);

}