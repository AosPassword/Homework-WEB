package com.webrock.filefind;

import com.webrock.filefind.find.FindFile;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            final String DBDRIVER = "org.gjt.mm.mysql.Driver";
            Class.forName(DBDRIVER);
            FindFile.print("克苏鲁原著翻译",0);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
