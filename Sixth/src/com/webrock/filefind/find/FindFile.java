package com.webrock.filefind.find;

import com.webrock.FileUtil;
import com.webrock.filefind.listener.ServletListener;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class FindFile {
    private static String sql = "SELECT name FROM file WHERE pid = ?";

    public static List findFile(String fileName) {
        PreparedStatement pstmt = ServletListener.getPstmt();
        ResultSet resultSet;
        List<Map<String, String>> info = new ArrayList<>();
        try {
            pstmt.setString(1, ("%" + fileName + "%"));
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("文件名", resultSet.getString("name"));
                map.put("文件类型", resultSet.getString("type"));
                map.put("文件路径", resultSet.getString("path"));
                map.put("最后修改时间", resultSet.getString("last_modified"));
                map.put("是否可执行", resultSet.getString("can_execute"));
                info.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static void print(String name, int flag) throws SQLException {
        Connection conn = DriverManager.getConnection(FileUtil.getDbUrl(), FileUtil.getDbUser(), FileUtil.getDbPass());
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i = 0; i < flag; i++) {
            System.out.print("     ");
        }
        System.out.println(name);
        int pid = FileUtil.findId(name);
        pstmt.setInt(1, pid);
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            print(resultSet.getString("name"), flag + 1);
        }

    }
}
