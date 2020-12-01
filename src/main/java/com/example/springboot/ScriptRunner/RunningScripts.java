package com.example.springboot.ScriptRunner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.ibatis.jdbc.ScriptRunner;

public class RunningScripts {
    public static void runScript(){
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String mysqlUrl = "jdbc:mysql://localhost:3306/pizza_bot?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            Connection con = DriverManager.getConnection(mysqlUrl, "root", "root");
            ScriptRunner scriptRunner = new ScriptRunner(con);
            Reader reader = new BufferedReader(new FileReader("src/main/script.sql"));
            scriptRunner.runScript(reader);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}