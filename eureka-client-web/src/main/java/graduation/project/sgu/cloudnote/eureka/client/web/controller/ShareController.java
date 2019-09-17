package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
  * 分享表 Controller 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@RestController
@RequestMapping("share")
public class ShareController {

    @RequestMapping(value = {"/create"})
    public ResponseDto add(HttpServletRequest request){
        String note_id = request.getParameter("note_id");
        String isHasPwd = request.getParameter("is_has_pwd");
        String pwd = request.getParameter("pwd");
        String limitType = request.getParameter("limit_type");
        String limitContent = request.getParameter("limit_content");
        return null;
    }
	
}