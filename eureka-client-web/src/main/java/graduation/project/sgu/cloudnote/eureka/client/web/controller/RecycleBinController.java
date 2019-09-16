package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.service.RecycleBinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = {"/noteThrowAway"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String noteThrowAway(HttpServletRequest request) {
        String noteId = request.getParameter("note_id");
        return recycleBinService.throwNoteIntoRecycleBin(noteId);
    }

    /**
     * 笔记从回收站恢复
     */
    @RequestMapping(value = {"/noteRecover"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String noteRecover(HttpServletRequest request) {
        String noteId = request.getParameter("note_id");
        Integer  userId = (Integer) request.getSession().getAttribute("userId");
        return recycleBinService.noteRecover(userId,noteId);
    }
	
}