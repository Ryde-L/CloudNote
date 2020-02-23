package graduation.project.sgu.cloudnote.sso.controller;

import com.netflix.discovery.converters.Auto;
import graduation.project.sgu.cloudnote.sso.dto.ResponseDto;
import graduation.project.sgu.cloudnote.sso.pojo.User;
import graduation.project.sgu.cloudnote.sso.service.UserService;
import graduation.project.sgu.cloudnote.sso.utils.CheckerUtil;
import graduation.project.sgu.cloudnote.sso.utils.JedisClientPool;
import graduation.project.sgu.cloudnote.sso.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JedisClientPool jedisClientPool;

    @PostMapping("/login")
    public ResponseDto login(@RequestParam("username")String username, @RequestParam("pwd")String pwd,  HttpServletResponse response) throws IOException {
        if (CheckerUtil.checkNulls(username, pwd)) return ResultUtil.error("用户名或密码不为空", null);
        User user = userService.getUser(username, pwd);
        if (user == null) return ResultUtil.error("不正确的账号密码");

        //生成token
        String token = "token_" + user.getId() + "_" + UUID.randomUUID().toString();
        Cookie cookie = new Cookie("token",token);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge(7200);
        response.addCookie(cookie);
        //redis
        jedisClientPool.set(token, String.valueOf(user.getId()));
        jedisClientPool.expire(token,3600);
        //TODO 链接待改
        return ResultUtil.success("http://localhost:9000/page/new.html");
    }


    @PostMapping("/register")
    public ResponseDto register(@RequestParam("username")String username, @RequestParam("pwd")String pwd,@RequestParam("pwd_check")String pwdCheck, @RequestParam("phone")String phone,@RequestParam("email")String email,@RequestParam("gender")Integer gender, HttpServletResponse response) throws IOException {
        if (CheckerUtil.checkNulls(username, pwd,pwdCheck)) return ResultUtil.error("用户名或密码不为空");
        if (!pwd.equals(pwdCheck)) return ResultUtil.error("两次密码不相同");
        if (!userService.usernameValidate(username)) return ResultUtil.error("用户名重复");
        User user = new User(null,username,pwd,phone,email,gender,null,1);
        userService.insert(user);

        //生成token
        String token = "token_" + user.getId() + "_" + UUID.randomUUID().toString();
        Cookie cookie = new Cookie("token",token);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge(7200);
        response.addCookie(cookie);
        //redis
        jedisClientPool.set(token, String.valueOf(user.getId()));
        jedisClientPool.expire(token,3600);
        return ResultUtil.success("http://localhost:9000/page/new.html");

    }


    /**
     * token有效性检查
     */
    @RequestMapping("/checkToken")
    public ResponseDto checkToken(String token){
        //TODO 耗资源
        if(jedisClientPool.exists(token))
            return ResultUtil.success("token有效",jedisClientPool.get(token));
        return ResultUtil.error("token无效");
    }

    /**
     * 单个token无效化
     */
    @RequestMapping("/tokenInvalidate")
    public ResponseDto tokenInvalidate(String token,HttpServletRequest request,HttpServletResponse response){
        jedisClientPool.del(token);
        Cookie c = new Cookie("token", null);
        c.setMaxAge(0);
        c.setPath("/");
        c.setDomain("localhost");
        response.addCookie(c);
        return ResultUtil.success("单token无效生效");
    }

    /**
     * 全token无效化
     */
    @RequestMapping("/tokenFlush")
    public ResponseDto tokenFlush(){
        String s = jedisClientPool.flushDB();
        return ResultUtil.success("全token无效生效");
    }
}
