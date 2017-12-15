package com.webrock.filestore;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.webrock.FileUtil;

import java.io.File;
import java.sql.*;
import java.util.Date;

public class Scan {
    private static String sql = "INSERT INTO file(name,type,path,last_modified,can_execute,pid) VALUE (?,?,?,?,?,?)";

    private static Connection conn = null;
    private static PreparedStatement pstmt = null;

    public static Connection getConn() {
        return conn;
    }

    public static PreparedStatement getPstmt() {
        return pstmt;
    }

    public static void scan(File file, int pid) {
        store(file, pid);
        if (file.isDirectory()) {
            File files[] = file.listFiles();
            if (files != null) {
                int id;
                if (("D:" + File.separator).equals(file.toString())) {
                    id = 0;
                } else {
                    id = FileUtil.findId(file.getName());
                }
                for (int i = 0; i < files.length; i++) {
                    if (!files[i].isHidden())
                        scan(files[i], id);
                }
            }
        }
    }

    public static void store(File file, int pid) {
        if (!("D:" + File.separator).equals(file.toString())) {
            String type;
            String name;
            if (file.isDirectory()) {
                type = "directory";
                name = file.getName();
            } else {
                String[] str = FileUtil.separteName(file.getName());
                name = str[0];
                type = str[1];
            }
            String path = file.getPath();
            Timestamp lastModified = new Timestamp(new Date(file.lastModified()).getTime());
            String canExecutec = file.canExecute() ? "yes" : "no";
            try {
                conn = DriverManager.getConnection(FileUtil.getDbUrl(), FileUtil.getDbUser(), FileUtil.getDbPass());
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, type);
                pstmt.setString(3, path);
                pstmt.setTimestamp(4, lastModified);
                pstmt.setString(5, canExecutec);
                pstmt.setInt(6, pid);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(type);
            }
        }
    }
}
