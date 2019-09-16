package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
  * 笔记标签表 Controller 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@RestController
@RequestMapping("noteTag")
public class NoteTagController {

    @Autowired
    NoteTagService noteTagService;


    @RequestMapping(value = {"/addTags"})
    @ResponseBody
    public ResponseDto addTags(HttpServletRequest request) throws Exception {
        String noteId = request.getParameter("note_id");
        String[] tags = request.getParameterValues("tags[]");
        return noteTagService.addTags(noteId, tags);
    }

    @RequestMapping("/delByTagId")
    @ResponseBody
    public ResponseDto delOne(HttpServletRequest request, @RequestParam("tag_id") int tagId) {
        System.out.println(tagId);
        //TODO 用户操作验证
        return new ResponseDto();
    }



	
}