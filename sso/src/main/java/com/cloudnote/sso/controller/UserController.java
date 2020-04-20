package com.cloudnote.sso.controller;

import com.cloudnote.sso.dto.ResponseDto;
import com.cloudnote.sso.pojo.User;
import com.cloudnote.sso.service.UserService;
import com.cloudnote.sso.utils.ResultUtil;
import com.cloudnote.sso.utils.CheckerUtil;
import com.cloudnote.sso.utils.JedisClientPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JedisClientPool jedisClientPool;

    @PostMapping("/login")
    public ResponseDto login(@RequestParam(value = "username",required = false)String username,
                             @RequestParam(value = "pwd",required = false)String pwd,
                             HttpServletResponse response)  {
        if (CheckerUtil.checkNulls(username, pwd)) return ResultUtil.error("用户名或密码不为空");
        User user = userService.getUser(username, pwd);
        if (user == null) return ResultUtil.error("不正确的账号密码");
        if (user.getStatus()==0) return ResultUtil.error("账号被封禁！");
        //生成token
        String token = "token_" + user.getId() + "_" + UUID.randomUUID().toString();
        //token写入cookie
        Cookie cookie = new Cookie("token",token);
        cookie.setPath("/");
        cookie.setDomain("cloudnote.com");
        cookie.setMaxAge(7200);
        response.addCookie(cookie);
        //token写入redis
        jedisClientPool.set(token, String.valueOf(user.getId()));
        jedisClientPool.set(String.valueOf(user.getId()),token);
        //设置过期时间
        jedisClientPool.expire(token,3600);
        jedisClientPool.expire(String.valueOf(user.getId()),3600);
        //返回成功结果
        return ResultUtil.success("http://cloudnote.com:9001/page/new.html");
    }


    @PostMapping("/register")
    public ResponseDto register(@RequestParam(value = "username",required=false)String username,
                                @RequestParam(value = "pwd",required=false)String pwd,
                                @RequestParam(value = "pwd_check",required=false)String pwdCheck,
                                @RequestParam(value = "gender",required=false)Integer gender,
                                HttpServletResponse response) {
        //数据校验
        if (CheckerUtil.checkNulls(username, pwd,pwdCheck)) return ResultUtil.error("用户名或密码不为空");
        if (!pwd.equals(pwdCheck)) return ResultUtil.error("两次密码不相同");
        if (!userService.usernameValidate(username)) return ResultUtil.error("用户名重复");
        if (gender!=null&&gender!=1&&gender!=2) return ResultUtil.error("性别选项出错");
        User user = new User(null,username,pwd,gender,1);
        userService.insert(user);
        //生成token
        String token = "token_" + user.getId() + "_" + UUID.randomUUID().toString();
        //token写入cookie
        Cookie cookie = new Cookie("token",token);
        cookie.setPath("/");
        cookie.setDomain("cloudnote.com");
        cookie.setMaxAge(7200);
        response.addCookie(cookie);
        //token写入redis
        jedisClientPool.set(token, String.valueOf(user.getId()));
        jedisClientPool.expire(token,3600);
        return ResultUtil.success("http://cloudnote.com:9001/page/new.html");
    }


    /*token创建*/
    public ResponseDto createToken(String userId,HttpServletResponse response){
        //生成token
        String token = "token_" + userId + "_" + UUID.randomUUID().toString();
        //token写入cookie
        Cookie cookie = new Cookie("token",token);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        response.addCookie(cookie);
        //token写入redis
        jedisClientPool.set(token, userId);
        jedisClientPool.set(userId,token);
        //设置过期时间
        jedisClientPool.expire(token,3600);
        jedisClientPool.expire(userId,3600);
        //返回成功结果
        return ResultUtil.success(token);
    }
    /*token有效性检查*/
    @RequestMapping("/checkToken")
    public ResponseDto checkToken(String token){
        //传入token查找
        if(jedisClientPool.exists(token))
            return ResultUtil.success("token有效", jedisClientPool.get(token));
        return ResultUtil.error("token无效");
    }

    /**
     * 单个token无效化
     */
    @RequestMapping("/tokenInvalidate")
    public ResponseDto tokenInvalidate(String token,HttpServletResponse response){
        jedisClientPool.del(token);
        Cookie c = new Cookie("token", null);
        c.setMaxAge(0);
        c.setPath("/");
        c.setDomain("localhost");
        response.addCookie(c);
        return ResultUtil.success("单token无效生效");
    }

    /**
     * 单个token无效化
     */
    @RequestMapping("/tokenInvalidateByUserId")
    public ResponseDto tokenInvalidateByUserId(String userId,HttpServletResponse response){
        String target = jedisClientPool.get(userId);
        if (target!=null) jedisClientPool.del(target);
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