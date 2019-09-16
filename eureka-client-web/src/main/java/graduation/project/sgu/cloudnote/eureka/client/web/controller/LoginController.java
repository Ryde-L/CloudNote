package graduation.project.sgu.cloudnote.eureka.client.web.controller;

import graduation.project.sgu.cloudnote.eureka.client.web.bean.JsonBuilder;
import graduation.project.sgu.cloudnote.eureka.client.web.pojo.User;
import graduation.project.sgu.cloudnote.eureka.client.web.service.UserService;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.CheckerUtil;
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
    JsonBuilder jsonBuilder;

    @Autowired
    UserService userService;


    @RequestMapping(value = {"/login"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String login(HttpServletRequest request) {
        try {
            String username = request.getParameter("username");
            String pwd = request.getParameter("pwd");
            if (CheckerUtil.checkNull(username))
                return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "用户名不为空").build();
            if (CheckerUtil.checkNull(pwd))
                return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "密码不为空").build();

            if (userService.isValidLogin(username,pwd)){
                User u=userService.getUser(username,pwd);
                HttpSession session = request.getSession();
                session.setAttribute("userId",u.getId());
                session.setAttribute("username",username);
                session.setMaxInactiveInterval(7200);
            }else
                return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "不正确的账号密码").build();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户登陆抛出异常：" + e.getMessage());
            return jsonBuilder.createConstructor("isSuccessful", "0", "msg", "用户登陆抛出异常").build();
        }
        return jsonBuilder.createConstructor("isSuccessful", "1").build();

    }
}
