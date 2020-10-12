        <%@ page import="java.util.List" %>
<%@ page import="Data.field.GetField" %>
<%@ page import="java.net.http.HttpRequest" %>
<%@ page import="Data.Sqlapi" %>
<%@ page import="Data.execute.Infomation" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: EASY
  Date: 2020/10/8
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>后台登陆界面</title>
  </head>
  <body>
  <center>用户列表</center>
  <div style="text-align: center;">
 <table border="1" border="1" align="center">
   <% Sqlapi showall = new Infomation();
       List<GetField> users = null;
       try {
           users = showall.showall();
       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
       if(users!=null){
       for (GetField user : users) { %>
     <tr align="center">
       <td><%=user.getUsername()%></td>
       <td><%=user.getPassword()%></td>
       <td><%=user.getAvatar()%></td>
     </tr>
   <%}
   }%>
 </table>
  </div>
  </body>
</html>
