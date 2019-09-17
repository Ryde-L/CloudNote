package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.bean.JsonBuilder;
import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.NoteBook;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.User;
import graduation.project.sgu.cloudnote.eureka.client.web.service.NoteBookService;
import graduation.project.sgu.cloudnote.eureka.client.web.service.UserService;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.CheckerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
  * 用户表 Controller 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@RestController
@RequestMapping("user")
@Transactional(rollbackFor = Exception.class)
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value={"/register"})
    @ResponseBody
    public ResponseDto register(HttpServletRequest request) {
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        return userService.register(username, pwd, phone, email, gender);
    }


}