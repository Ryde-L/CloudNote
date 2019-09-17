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
    public ResponseDto noteThrowAway(@RequestParam("note_id[]")Integer[] noteIds) {
        return recycleBinService.throwNoteIntoRecycleBin(noteIds);
    }

    /**
     * 笔记从回收站恢复
     */
    @RequestMapping(value = {"/noteRecover"})
    public ResponseDto noteRecover(@RequestParam("note_id")Integer noteId, HttpSession session) {
        Integer  userId = (Integer) session.getAttribute("userId");
        return recycleBinService.noteRecover(userId,noteId);
    }
	
}