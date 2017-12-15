package com.webrock.filestore;

import com.webrock.FileUtil;

import java.io.File;
import java.sql.SQLException;

public class Main {
    private static final String DBDRIVER = "org.gjt.mm.mysql.Driver";
    public static void main(String[] args) {
        try {
            Class.forName(DBDRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        File file = new File("D:" + File.separator);
        Scan.scan(file,-1);
        System.out.println("Just OK");
        try {
            Scan.getPstmt().close();
            Scan.getConn().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
