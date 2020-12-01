package com.example.springboot;

import com.example.springboot.ScriptRunner.RunningScripts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        RunningScripts.runScript();
        SpringApplication.run(Application.class, args);
    }
}