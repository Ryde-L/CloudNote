package com.cloudnote.bin.controller;

import com.cloudnote.bin.service.RecycleBinService;
import com.cloudnote.bin.utils.ArrayUtil;
import com.cloudnote.common.dto.ResponseDto;
import com.cloudnote.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
  * 废纸篓表 Controller 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@RestController
@RequestMapping("recycleBin")
public class RecycleBinController {

    @Autowired
    RecycleBinService recycleBinService;

    /**
     * 笔记丢入回收站
     */
    @RequestMapping(value = {"/noteThrowAway"})
    public ResponseDto noteThrowAway(HttpServletRequest request) {
        String[] noteIds = request.getParameterValues("note_id[]")!=null?request.getParameterValues("note_id[]"):request.getParameterValues("note_ids[]");
        if (noteIds==null) return ResultUtil.error("缺少参数");
        return recycleBinService.throwNoteIntoRecycleBin(Integer.parseInt (request.getHeader("userId")), ArrayUtil.StringToInteger(noteIds));
    }

    /**
     * 笔记从回收站彻底删除
     */
    @RequestMapping(value = {"/delete"})
    public ResponseDto removeNotes(@RequestParam("id[]")Integer[] ids, HttpServletRequest request) {
        return recycleBinService.removeNotes(Integer.parseInt (request.getHeader("userId")),ids);
    }

    /**
     * 笔记从回收站恢复
     */
    @RequestMapping(value = {"/noteRecover"})
    public ResponseDto noteRecover(@RequestParam("note_id")Integer noteId, HttpServletRequest request) {
        return recycleBinService.noteRecover(Integer.parseInt (request.getHeader("userId")),noteId);
    }

    /**
     * 获取废纸篓的单个笔记信息
     */
    @RequestMapping(value = {"/getNoteWithNoteBookAndContent"})
    public ResponseDto getNoteWithNoteBookAndContent(@RequestParam("note")Integer binId,HttpServletRequest request) {
        return recycleBinService.getNoteWithNoteBookAndContent(Integer.parseInt (request.getHeader("userId")),binId);
    }

    /**
     * 获取废纸篓的笔记List
     */
    @RequestMapping(value = {"/getNotes"})
    public ResponseDto getNoteListByUserId( HttpServletRequest request) {
        return recycleBinService.getNotesByUserId(Integer.parseInt (request.getHeader("userId")));
    }

}