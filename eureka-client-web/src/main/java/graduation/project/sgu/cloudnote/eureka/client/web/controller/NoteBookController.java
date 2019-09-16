package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
  * 笔记本表 Controller 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@RestController
@RequestMapping("noteBook")
public class NoteBookController {

    @Autowired
    NoteBookService noteBookService;

    @RequestMapping(value = {"/add"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String add(HttpServletRequest request){
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        String title = request.getParameter("title");
        return noteBookService.createNoteBook(userId,title);
    }

    @RequestMapping(value = {"/delete"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String del(HttpServletRequest request){
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        //TODO 操作验证

        String noteBookId = request.getParameter("note_book_id");
        return noteBookService.remove(noteBookId);
    }
}