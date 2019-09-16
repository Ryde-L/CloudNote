package graduation.project.sgu.cloudnote.eureka.client.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter("/*")
public class LoginFilter implements Filter {
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
//        String postfix="";
//        try {
//
//            String uri = request.getRequestURI();
//            if (uri.lastIndexOf('.')!=-1)
//                postfix=uri.substring(uri.lastIndexOf('.'));
//            if (uri.contains("login"))
//                filterChain.doFilter(servletRequest, servletResponse);
//            else if (uri.contains("register"))
//                filterChain.doFilter(servletRequest, servletResponse);
//            else if (resourceSet.contains(postfix))//静态资源放行
//                filterChain.doFilter(servletRequest, servletResponse);
//            else {
//                HttpSession session = request.getSession();
//                if (session.getAttribute("username") != null) {
//                    session.setMaxInactiveInterval(7200);
//                    filterChain.doFilter(servletRequest, servletResponse);
//                } else
//                    response.sendRedirect("/page/login.html");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendRedirect("/page/login.html");
//        }
        HttpSession session = request.getSession();
        if (session.getAttribute("userId") == null) {
            session.setAttribute("userId", 4);
            session.setMaxInactiveInterval(7200);
        }
        filterChain.doFilter(request,response);

    }

    public void destroy() {

    }
}
