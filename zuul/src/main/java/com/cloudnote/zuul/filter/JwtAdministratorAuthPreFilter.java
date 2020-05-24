package com.cloudnote.zuul.filter;

import com.cloudnote.common.dto.ResponseDto;
import com.cloudnote.common.utils.JwtUtil;
import com.cloudnote.common.utils.ResultUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
/**管理员登陆检查*/
@Component
public class JwtAdministratorAuthPreFilter extends ZuulFilter {
    @Autowired
    ObjectMapper objectMapper;

    /**
     * pre：路由之前
     * routing：路由之时
     * post： 路由之后
     * error：发送错误调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    /**
     * shouldFilter：逻辑是否要过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        //路径与配置的相匹配，则执行过滤
        RequestContext ctx = getCurrentContext();
        System.out.println(ctx.getRequest().getRequestURI()+"管理员过滤检查");
        String uri = ctx.getRequest().getRequestURI();
        return !uri.contains("login")&&(uri.contains("ByAdministrator") || uri.contains("/userServices/")||"true".equals(ctx.getRequest().getParameter("use_admin_filter")));
    }

    /**
     * 执行过滤器逻辑，验证token
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        System.out.println(request.getRequestURL());
        System.out.println("管理员token检查");
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
            for (int i = 0; i < cookies.length; i++)
                if (cookies[i].getName().equals("sso_admin_token"))
                    token = cookies[i].getValue();
        System.out.println(token);
        try {
            //解析没有异常则表示token验证通过，如有必要可根据自身需求增加验证逻辑
            Integer userId = JwtUtil.verify(token);
            if (userId == null) {
                ctx.setSendZuulResponse(false);
                if (request.getHeader("user-ajax") != null)
                    responseError(ctx, "302", "http://localhost:8800/sso/path/admin_login.html", "未登陆");
                else
                    response.sendRedirect("http://localhost:8800/sso/path/admin_login.html");
                return null;
            }
            response.setHeader("isAdmin","true");
            //对请求进行路由
            ctx.setSendZuulResponse(true);
            //请求头加入userId，传给业务服务
            ctx.addZuulRequestHeader("userId", String.valueOf(userId));
            ctx.addZuulRequestHeader("isAdmin", "true");
        } catch (Exception e) {
            e.printStackTrace();
            //不对请求进行路由
            ctx.setSendZuulResponse(false);
            if (request.getHeader("user-ajax") != null)
                responseError(ctx, "302", "http://localhost:8800/sso/path/admin_login.html", "未登陆");
            else {
                try {
                    response.sendRedirect("http://localhost:8800/sso/path/admin_login.html");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 将异常信息响应给前端
     */
    private void responseError(RequestContext ctx, String  code, String url,String message) {
        HttpServletResponse response = ctx.getResponse();
        ResponseDto errResult = ResultUtil.error(code,url,message);
        ctx.setResponseBody(toJsonString(errResult));
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json;charset=utf-8");
    }

    private String toJsonString(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
