package com.webrock.filefind.listener;


import com.webrock.FileUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServletListener implements ServletContextListener {
    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static String sql = "SELECT * FROM file where name LIKE ?";
    private static final String DBDRIVER = "org.gjt.mm.mysql.Driver";

    public ServletListener() {

    }

    public static PreparedStatement getPstmt() {
        return pstmt;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName(DBDRIVER);
            ServletContext sc = sce.getServletContext();
            String url = "jdbc:mysql://localhost:"+sc.getInitParameter("port")+"/"+sc.getInitParameter("dbName")+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
            String user = sc.getInitParameter("dbUser");
            String pass = sc.getInitParameter("dbPass");
            conn = DriverManager.getConnection(url,user,pass);
            pstmt = conn.prepareStatement(sql);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
