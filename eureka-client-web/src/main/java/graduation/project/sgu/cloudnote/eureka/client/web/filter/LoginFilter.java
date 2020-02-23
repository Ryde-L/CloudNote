package graduation.project.sgu.cloudnote.eureka.client.web.filter;

import graduation.project.sgu.cloudnote.eureka.client.web.dto.ResponseDto;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.CheckerUtil;
import graduation.project.sgu.cloudnote.eureka.client.web.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Autowired
    RestTemplate restTemplate;


    Set<String> resourceSet;
    public void init(FilterConfig filterConfig) throws ServletException {
        resourceSet=new HashSet<String>();
        resourceSet.add(".js");resourceSet.add(".css");resourceSet.add(".svg");
        resourceSet.add(".jpg");resourceSet.add(".jpeg");resourceSet.add(".gif");
        resourceSet.add(".png");resourceSet.add(".font");resourceSet.add(".ico");

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String postfix="";
        try {

            String uri = request.getRequestURI();
            if (uri.lastIndexOf('.')!=-1)
                postfix=uri.substring(uri.lastIndexOf('.'));
//            if (uri.contains("login"))
//                filterChain.doFilter(servletRequest, servletResponse);
//            else if (uri.contains("register"))
//                filterChain.doFilter(servletRequest, servletResponse);
            if (resourceSet.contains(postfix))//静态资源放行
                filterChain.doFilter(servletRequest, servletResponse);
            else {
                //token有效性检查
                Cookie[] cookies = request.getCookies();
                for (Cookie c:cookies){
                    if (c.getName().equals("token")){
                            String json = restTemplate.getForEntity("http://sso/user/checkToken?token="+c.getValue(), String.class).getBody();
                        if (CheckerUtil.checkNulls(json))
                            response.sendRedirect("http://localhost:9010/page/login.html");
                        ResponseDto result = JsonUtil.jsonToPojo(json, ResponseDto.class);
                        if (result!=null&&"1".equals(result.getIsSuccessful()) ) { //验证通过
                            HttpSession session = request.getSession();
                            session.setAttribute("userId", result.getData());
                            session.setMaxInactiveInterval(3600);
                            filterChain.doFilter(request, response);
                            return;
                        } else break;//验证不通过
                    }
                }
                //不放行，未找到cookie或验证不通过
                response.sendRedirect("http://localhost:9010/page/login.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("http://localhost:9010/page/login.html");
        }




//        HttpSession session = request.getSession();
//        if (session.getAttribute("userId") == null) {
//            session.setAttribute("userId", 4);
//            session.setMaxInactiveInterval(7200);
//        }
//        filterChain.doFilter(request,response);
    }

    public void destroy() {

    }
}
