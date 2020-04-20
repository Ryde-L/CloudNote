package com.cloudnote.eureka.client.web.service;

import com.cloudnote.eureka.client.web.pojo.RecycleBin;
import com.cloudnote.eureka.client.web.dto.ResponseDto;

/**
 * <p>
  * 废纸篓表 Service 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
public interface RecycleBinService {

    /**
     * 根据笔记id获取RecycleBin
     * @param noteId 笔记id
     * @return RecycleBin对象
     */
    RecycleBin getRecycleBin(Integer noteId);


    /**
     * 将笔记本扔进回收站
     * @param userId 用户id
     * @param noteIds 笔记id
     * @return ResponseDto
     */
    ResponseDto throwNoteIntoRecycleBin(Integer userId,Integer[] noteIds);

    /**
     * 将笔记本从回收站恢复
     * @param noteIds 笔记id
     * @return ResponseDto
     */

    ResponseDto noteRecover(Integer userId,Integer ...noteIds);

    /**
     * 获取笔记详情
     * @param userId 用户id
     * @param binId 废纸篓id
     * @return ResponseDto：data是 note对象
     */
    ResponseDto getNoteWithNoteBookAndContent(Integer userId, Integer binId);

    /**
     * 根据用户id获取废纸篓里的笔记集合
     * @param userId 用户id
     * @return ResponseDto data是RecycleBinList
     */
    ResponseDto getNotesByUserId(Integer userId);

    /**
     * 彻底删除回收站里的笔记
     * @param userId 用户id
     * @param ids id数组
     * @return ResponseDto
     */
    ResponseDto removeNotes(Integer userId,Integer[] ids);
}