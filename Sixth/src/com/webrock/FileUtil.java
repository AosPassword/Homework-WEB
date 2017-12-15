package com.webrock;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.sql.*;


public class FileUtil {
    private static String dbUser = null;
    private static String dbPass = null;
    private static String dbUrl = null;

    static {
        SAXReader saxReader = new SAXReader();
        Document doc = null;
        try {
            doc = saxReader.read(new File("config.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = doc.getRootElement();
        Element port = root.element("port");
        Element user = root.element("dbUser");
        Element pass = root.element("dbPass");
        Element name = root.element("dbName");
        dbUrl = "jdbc:mysql://localhost:" + port.getText() + "/" + name.getText() + "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        dbUser = user.getText();
        dbPass = pass.getText();
    }

    public static String getDbUser() {
        return dbUser;
    }

    public static String getDbPass() {
        return dbPass;
    }

    public static String getDbUrl() {
        return dbUrl;
    }

    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static String sql = "SELECT id FROM file WHERE name = ?";


    public static String[] separteName(String name) {
        String[] result = new String[2];
        if (name.lastIndexOf(".") != -1) {
            result[1] = name.substring(name.lastIndexOf(".") + 1);
            result[0] = name.substring(0, name.lastIndexOf("."));
            if (name.length() == 0||name.length()>9) {
                result[0] = "." + result[0];
                result[1] = "unknown";
            }
        } else {
            result[1] = "unknown";
            result[0] = name;
        }
        return result;
    }


    public static int findId(String name) {
        int pid = -1;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet result = pstmt.executeQuery();
            result.next();
            pid = result.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(name);
        }
        return pid;
    }
}
