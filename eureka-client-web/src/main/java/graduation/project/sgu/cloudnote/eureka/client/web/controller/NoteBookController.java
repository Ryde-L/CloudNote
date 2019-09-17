package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @RequestMapping(value = {"/add"})
    public ResponseDto add(@RequestParam("title") String title, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        return noteBookService.createNoteBook(userId,title);
    }

    @RequestMapping(value = {"/delete"})
    public ResponseDto del(@RequestParam("note_book_id") int noteBookId, HttpSession session){
        Integer userId = (Integer) session. getAttribute("userId");
        return noteBookService.remove(noteBookId);

    }

}