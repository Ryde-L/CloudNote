package com.cloudnote.sso.controller;

import com.cloudnote.common.dto.ResponseDto;
import com.cloudnote.common.pojo.Administrator;
import com.cloudnote.common.utils.JwtUtil;
import com.cloudnote.common.utils.ResultUtil;
import com.cloudnote.sso.service.AdministratorService;
import com.cloudnote.common.utils.CheckerUtil;
import com.cloudnote.common.utils.JedisClientPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
  *  Controller 接口
 * </p>
 *
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@RestController
@RequestMapping("administrator")
public class AdministratorController {

    @Autowired
    AdministratorService administratorService;

    @Autowired
    JedisClientPool jedisClientPool;

    @PostMapping("/login")
    public ResponseDto login(@RequestParam(value = "username",required = false)String username,
                             @RequestParam(value = "pwd",required = false)String pwd,
                             HttpServletResponse response)  {
        if (CheckerUtil.checkNulls(username, pwd)) return ResultUtil.error("用户名或密码不为空");
        Administrator administrator = administratorService.getAdministrator(username, pwd);
        if (administrator == null) return ResultUtil.error("不正确的账号密码");
        //生成token
        String token = JwtUtil.sign(administrator.getId());
        response.setHeader("token",token);
        response.setHeader("is_Admin","true");
        //token写入cookie
        Cookie cookie = new Cookie("sso_admin_token",token);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge(7200);
        response.addCookie(cookie);
        //token写入redis
        jedisClientPool.set(token, String.valueOf(administrator.getId()));
        jedisClientPool.set(String.valueOf(administrator.getId()),token);
        //设置过期时间
        jedisClientPool.expire(token,3600);
        jedisClientPool.expire(String.valueOf(administrator.getId()),3600);
        //返回成功结果
        return ResultUtil.success();
    }

    /**
     * token有效性检查
     */
    @RequestMapping("/checkToken")
    public ResponseDto checkToken(String token){
        if(jedisClientPool.exists(token))
            return ResultUtil.success("token有效",jedisClientPool.get(token));
        return ResultUtil.error("token无效");
    }

    /**
     * 单个token无效化
     */
    @RequestMapping("/tokenInvalidate")
    public ResponseDto tokenInvalidate(String token, HttpServletRequest request, HttpServletResponse response){
        jedisClientPool.del(token);
        Cookie c = new Cookie("token", null);
        c.setMaxAge(0);
        c.setPath("/");
        c.setDomain("localhost");
        response.addCookie(c);
        return ResultUtil.success("管理员单token无效生效");
    }

}