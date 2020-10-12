package fileter;

import org.w3c.dom.html.HTMLTableCaptionElement;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/index.jsp")
public class Pass implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //解决乱码问题
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html;charset=utf-8");
        //解决权限问题
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String  login = (String) session.getAttribute("login");
        if (login == null){
            response.sendRedirect(request.getContextPath()+"/index.html");
        }
        else{
            filterChain.doFilter(request,response);
        }
    }

}
