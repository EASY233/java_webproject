[TOC]

## 前言

学习Java也有一段时间了，但是一直都没有进行进行过项目实战。为了巩固基础自已学习写了一个简单的登录后台功能，虽然简单但是麻雀虽小五脏俱全，依旧在写的过程中学习到了非常多的东西。以下代码下载地址:https://github.com/EASY233/java_webproject

## 项目环境搭建

很多新手在IDEA搭建web项目都会自闭，这里简单写一下IDEA如何搭建web项目。简单来说以下几个步骤:

- 把Java项目添加Web Application框架支持
- 在WEB-INF上添加classes文件夹和lib文件夹，其中classes设置为模块编译输出模块，lib设置为库并且导入servlet-api.jar库。
- 配置tomcat到IDEA上

### 把Java项目添加Web Application框架支持

新创建一个项目，设置自已项目的名字然后一路点next即可。

![](https://cdn.jsdelivr.net/gh/EASY233/picture//img/20201011195658.png)

为我们的项目添加框架支持，右键项目选择框架支持

![](https://cdn.jsdelivr.net/gh/EASY233/picture//img/image-20201011195944581.png)

添加支持后我们就可以看到一个web文件夹

![](https://cdn.jsdelivr.net/gh/EASY233/picture//img/20201011200116.png)

### 在WEB-INF上添加classes文件夹和lib文件夹

因为IDEA会默认在项目底下新建一个OUT文件夹来存储模块编译的文件，但是存储到这里无法被tomcat解释执行。所以我们需要在WEB-INF上新添加classes文件夹并且把该文件夹设置为设置为模块编译输出模块。

选择文件->项目结构->模块->路径

![](https://cdn.jsdelivr.net/gh/EASY233/picture//img/20201012104205.png)

把lib添加库，选择文件->项目结构->库点击+号把我们的lib文件夹添加进去才行，

![](https://cdn.jsdelivr.net/gh/EASY233/picture//img/image-20201012150546709.png)

此时我们是不能使用servlet的我们需要导包进入servlet-api.jar包到lib中，而servlet-api.jar就在apache-tomcat文件夹的lib文件夹下。

此时我们就可以正常导包了

![](https://cdn.jsdelivr.net/gh/EASY233/picture//img/image-20201012151414973.png)

### 配置tomcat到IDEA上

选择右上角的添加配置->点击+号->选择本地tomcat，正常情况下右下角会有一个部署修复按钮点击一下。

![](https://cdn.jsdelivr.net/gh/EASY233/picture//img/image-20201012151831127.png)

![](https://cdn.jsdelivr.net/gh/EASY233/picture//img/image-20201012152046418.png)

此时我们路径访问的根路径为xx/easy233_war_exploded自已可以自行进行修改。

![](https://cdn.jsdelivr.net/gh/EASY233/picture//img/image-20201012152211839.png)

看到如下界面就成功了：

![](https://cdn.jsdelivr.net/gh/EASY233/picture//img/image-20201012152652574.png)

### IDEA的各种编码问题

**首先要分清是tomcat日志编码，与idea的日志显示控制台编码**

- tomcat日志编码：cmd内 "cd /d tomcat根目录" "bin\\catalina.bat run" 运行，"chcp65001"切换cmd为utf8，"chcp 936"切换cmd为gbk，确定tomcat日志编码，一般因为tomcat/conf/logging.properties java.util.logging.ConsoleHandler.encoding = UTF-8设置为utf8
- idea显示编码：windows默认用gbk所以idea显示默认为gbk编码，【一定】在 Help-- custom vm options 添加-Dfile.encoding=UTF-8，强制为utf8编码显示，不要自己改.vmoptions可能位置不对，idea会在用户目录复制一个
- 【切忌】自己改tomcat的logging.properties 为GBk 会导致调试时get/post参数乱码
- 重启IDEA

## 项目结构分析

项目目录结构如下:

![](https://cdn.jsdelivr.net/gh/EASY233/picture//img/image-20201012153431022.png)

- code是创建验证码模块
- Data是数据库配置与语句执行模块
- fileter为过滤器
- login为登录逻辑分析模块
- service主模块

### 主页面

![](https://cdn.jsdelivr.net/gh/EASY233/picture//img/image-20201012154249871.png)

后台页面显示所有用户信息，细心的同学应该可以发现我用的是DVWA的数据库进行的测试。

### 后台页面

![](https://cdn.jsdelivr.net/gh/EASY233/picture//img/image-20201012154308732.png)

**PS:因为前端实在太菜了所以页面设计的贼丑，并且功能简陋，不过这也能够应用上的不少java的web开发知识，作为一个新手练练手**

**(翻译一下就是太菜了，窒息~~)**

## 数据库

### 数据库配置

因为项目存在大量的小任务需要执行，并且频繁地创建和销毁线程，实际上会消耗大量的系统资源，往往创建和消耗线程所耗费的时间比执行任务的时间还长，所以，为了提高效率，可以用线程池。这里使用目前比较流行的HikariCP，需要导入包HikariCP-3.4.5 .jar。

```java
package Data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class Config {
    static final String jdburl="jdbc:mysql://localhost/dvwa?useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
    static final  String jdbuser = "root";
    static final String jdbpassword = "12345";
    public static DataSource getConnection(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdburl);
        config.setUsername(jdbuser);
        config.setPassword(jdbpassword);
        config.addDataSourceProperty("cachePrepStmts","true");
        config.addDataSourceProperty("prepStmtCacheSize", "100");
        config.addDataSourceProperty("maximumPoolSize", "100");
        try{
            return (DataSource) new HikariDataSource(config);
        }
        catch (RuntimeException ignored){
            return (DataSource) new HikariDataSource(config);
        }
    }
}

```

### Sql执行

规范Sql执行接口

```
package Data;

import Data.field.GetField;

import java.sql.SQLException;
import java.util.List;

public interface Sqlapi {
    public GetField select(String username) throws SQLException;
    public List<GetField> showall() throws SQLException;
}

```

字段的存储

```java
package Data.field;

public class GetField {
    private String username;
    private String password;
    private String avatar;
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getAvatar(){
        return avatar;
    }
    public GetField(String name,String password,String avatar){
        this.username = name;
        this.password = password;
        this.avatar = avatar;
    }
    public GetField(){
    }
}

```

接口的实现类,主要包含的就是实现的两个功能一个是登录功能一个是后台用户列表功能。

```java
package Data.execute;

import Data.Config;
import Data.Sqlapi;
import Data.field.GetField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Infomation implements Sqlapi{
    @Override
    public GetField select(String username) throws SQLException {
        GetField field = null;
        try(Connection conn = Config.getConnection().getConnection()){
           String sql = "select * from users where user = ?";
           try(PreparedStatement ps = conn.prepareStatement(sql)){
               ps.setObject(1,username);
               try(ResultSet result = ps.executeQuery()){
                   while(result.next()){
                       field = new GetField(result.getString("user"),result.getString("password"),result.getString("avatar"));
                   }
               }
           }
        }
        return field;
    }
    @Override
    public List<GetField> showall() throws SQLException{
        List<GetField> users = new ArrayList<>();
        try(Connection conn = Config.getConnection().getConnection()){
            String sql  = "select * from users";
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                try(ResultSet result = ps.executeQuery()){
                    while(result.next()){
                        users.add(new GetField(result.getString("user"), result.getString("password"),result.getString("avatar")));
                    }
                }
            }
        }
        return users;
    }
}

```

## 过滤器

过滤器执行地位Servlet之前，客户端发送请求时候，会先经过过滤器，再到目标的Servlet中，响应时候，会根据执行流程再次反向执行过滤器。我们可以使用这来解决很多Servlet共性代码的冗余问题。

这里我拿它解决Session验证与编码问题:

```java
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

```

## 其他部分代码

### 主函数

```
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

```

### 验证码

```java
package code;

import cn.dsna.util.images.ValidateCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.PrimitiveIterator;

@WebServlet("/code")
public class CreateCode extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ValidateCode vc =new ValidateCode(100,30,4,10);
        String code = vc.getCode();
        HttpSession session = req.getSession();
        session.setAttribute("code",code);
        vc.write(resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}


```

