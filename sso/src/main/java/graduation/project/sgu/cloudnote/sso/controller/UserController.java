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
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JedisClientPool jedisClientPool;

    @PostMapping("/login")
    public ResponseDto login(@RequestParam("username")String username, @RequestParam("pwd")String pwd,  HttpServletResponse response) {
        if (CheckerUtil.checkNulls(username, pwd)) return ResultUtil.error("用户名或密码不为空", null);

        User user = userService.getUser(username, pwd);
        if (user == null) return ResultUtil.error("不正确的账号密码");

        //生成token
        String token = "token_" + user.getId() + "_" + UUID.randomUUID().toString();Cookie cookie = new Cookie("token",token);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge(7200);
        response.addCookie(cookie);
        //redis
        jedisClientPool.set(token, String.valueOf(user.getId()));
        jedisClientPool.expire(token,3600);
        return ResultUtil.success("",token);
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
     * token无效化
     */
    @RequestMapping("/tokenInvalidate")
    public ResponseDto tokenInvalidate(String token,HttpServletRequest request,HttpServletResponse response){
        jedisClientPool.del(token);
        Cookie c = new Cookie("token", null);
        c.setMaxAge(0);
        c.setPath("/");
        c.setDomain("localhost");
        response.addCookie(c);
        return ResultUtil.success("token无效生效");
    }
}