package graduation.project.sgu.cloudnote.sso.controller;

import graduation.project.sgu.cloudnote.sso.dto.ResponseDto;
import graduation.project.sgu.cloudnote.sso.pojo.Administrator;
import graduation.project.sgu.cloudnote.sso.pojo.User;
import graduation.project.sgu.cloudnote.sso.service.AdministratorService;
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
        String token = "admin_token_" + administrator.getId() + "_" + UUID.randomUUID().toString();
        //token写入cookie
        Cookie cookie = new Cookie("token",token);
        cookie.setPath("/");
        cookie.setDomain("cloudnote.com");
        cookie.setMaxAge(7200);
        response.addCookie(cookie);
        //token写入redis
        jedisClientPool.set(token, String.valueOf(administrator.getId()));
        jedisClientPool.expire(token,3600);
        //返回成功结果
        return ResultUtil.success("http://cloudnote.com:9000/page/new.html");
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