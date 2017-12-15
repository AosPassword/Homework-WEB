package pers.hucang.schoolinfo;

import java.sql.*;

public class DatabaseTools {
    private static final String DBDRIVER = "org.gjt.mm.mysql.Driver";
    private static final String DBUSER = "root";
    private static final String DBPASS = "zxc981201";
    private static final String INITDBURL = "jdbc:mysql://localhost:3306/";
    private static final String DBURL = "jdbc:mysql://localhost:3306/students";
    private static Connection conn;


    public int initializationDatabase() throws SQLException, ClassNotFoundException {
        int flag = 1;
        Class.forName(DBDRIVER);
        Connection firstConn = DriverManager.getConnection(INITDBURL, DBUSER, DBPASS);
        Statement firstStat = firstConn.createStatement();
        Connection secondConn = null;
        Statement secondStat = null;

        try {
            String sql = "CREATE DATABASE students;";
            firstStat.executeUpdate(sql);

            secondConn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            secondStat = secondConn.createStatement();

            sql = "CREATE TABLE students ( " +
                    "id INT AUTO_INCREMENT PRIMARY KEY ," +
                    "name VARCHAR(24) NOT NULL ," +
                    "stuid VARCHAR(12) NOT NULL," +
                    "sex VARCHAR(2) NOT NULL," +
                    "class VARCHAR(10) NOT NULL," +
                    "majorid VARCHAR(6) NOT NULL," +
                    "major VARCHAR(24) NOT NULL," +
                    "collage VARCHAR(32) NOT NULL," +
                    "enterYear VARCHAR(4) NOT NULL," +
                    "atSchool VARCHAR(2) NOT NULL" +
                    ");";
            secondStat.executeUpdate(sql);
            System.out.println("检测到首次加载，请耐心等待爬取");
        } catch (SQLException e) {
            System.out.println("已检测到数据库");
            flag = 0;
        } finally {
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            firstStat.close();
            firstConn.close();
            secondConn.close();
            secondStat.close();
        }
        return flag;
    }

    public void add(Student student) throws SQLException, ClassNotFoundException {
        Class.forName(DBDRIVER);
        String sql = "INSERT INTO students(name,stuid,sex,class,majorId,major,collage,enterYear,atSchool)"+
                "VALUES(?,?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,student.getName());
        pstmt.setString(2,student.getStuid());
        pstmt.setString(3,student.getSex());
        pstmt.setString(4,student.getClazz());
        pstmt.setString(5,student.getMajorId());
        pstmt.setString(6,student.getMajor());
        pstmt.setString(7,student.getCollage());
        pstmt.setString(8,student.getEnterYear());
        pstmt.setString(9,student.getAtSchool());
        pstmt.executeUpdate();
        pstmt.close();
    }
}
