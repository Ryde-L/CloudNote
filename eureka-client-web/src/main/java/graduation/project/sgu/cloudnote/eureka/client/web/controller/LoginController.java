package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.bean.JsonBuilder;
import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.User;
import graduation.project.sgu.cloudnote.eureka.client.web.service.UserService;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.CheckerUtil;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    UserService userService;


    @RequestMapping(value = {"/login"})
    @ResponseBody
    public ResponseDto login(HttpServletRequest request) throws Exception {
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        if (CheckerUtil.checkNulls(username, pwd))
            return ResultUtil.error("用户名或密码不为空", null);

        if (userService.isValidLogin(username, pwd)) {
            User u = userService.getUser(username, pwd);
            HttpSession session = request.getSession();
            session.setAttribute("userId", u.getId());
            session.setAttribute("username", username);
            session.setMaxInactiveInterval(7200);
            return ResultUtil.success("", null);
        } else
            return ResultUtil.error("不正确的账号密码", null);
    }
}
