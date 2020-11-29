package com.example.springboot;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.ibatis.jdbc.ScriptRunner;

public class RunningScripts {
    public static void runScript(){
        //Registering the Driver
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //Getting the connection
            String mysqlUrl = "jdbc:mysql://localhost:3306/pizza_bot?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            Connection con = DriverManager.getConnection(mysqlUrl, "root", "root");
            //Initialize the script runner
            ScriptRunner sr = new ScriptRunner(con);
            //Creating a reader object
            Reader reader = new BufferedReader(new FileReader("src/main/script.sql"));
            //Running the script
            sr.runScript(reader);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}