package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.service.RecycleBinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public ResponseDto noteThrowAway(@RequestParam("note_id[]")Integer[] noteIds, HttpSession session) {
        Integer  userId = Integer.valueOf ((String) session.getAttribute("userId"));
        return recycleBinService.throwNoteIntoRecycleBin(userId,noteIds);
    }

    /**
     * 笔记从回收站彻底删除
     */
    @RequestMapping(value = {"/delete"})
    public ResponseDto removeNotes(@RequestParam("id[]")Integer[] ids, HttpSession session) {
        Integer  userId = Integer.valueOf ((String) session.getAttribute("userId"));
        return recycleBinService.removeNotes(userId,ids);
    }

    /**
     * 笔记从回收站恢复
     */
    @RequestMapping(value = {"/noteRecover"})
    public ResponseDto noteRecover(@RequestParam("note_id")Integer noteId, HttpSession session) {
        Integer  userId = Integer.valueOf ((String) session.getAttribute("userId"));
        return recycleBinService.noteRecover(userId,noteId);
    }

    /**
     * 获取废纸篓的单个笔记信息
     */
    @RequestMapping(value = {"/getNoteWithNoteBookAndContent"})
    public ResponseDto getNoteWithNoteBookAndContent(@RequestParam("note")Integer binId, HttpSession session) {
        Integer  userId = Integer.valueOf ((String) session.getAttribute("userId"));
        return recycleBinService.getNoteWithNoteBookAndContent(userId,binId);
    }

    /**
     * 获取废纸篓的笔记List
     */
    @RequestMapping(value = {"/getNotes"})
    public ResponseDto getNoteListByUserId( HttpSession session) {
        Integer  userId = Integer.valueOf ((String) session.getAttribute("userId"));
        return recycleBinService.getNotesByUserId(userId);
    }

}