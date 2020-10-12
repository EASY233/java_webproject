package service;

import Data.Sqlapi;
import Data.execute.Infomation;
import Data.field.GetField;
import login.Login;
import login.Loginapi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class Main extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Loginapi check =new Login();
        String name = request.getParameter("name");
        String password =request.getParameter("password");
        String getcode = request.getParameter("code");
        String code = (String) request.getSession().getAttribute("code");
        System.out.println(getcode);
        System.out.println(code);
        if (!getcode.isEmpty()&&code.equalsIgnoreCase(getcode)){
            GetField result = null;
            try {
                result = check.login(name,password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if(result!=null){
                if(result.getPassword().equals(password)){
                    HttpSession session = request.getSession();
                    session.setAttribute("login",result.getUsername());
                    request.getRequestDispatcher("index.jsp").forward(request,response);
                }
            }
            else{
                response.sendRedirect("/webproject/index.html");
            }
        }
        else{
            response.sendRedirect("/webproject/index.html");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/webproject/index.html");
    }
}
