package com.example.springboot.Domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Entity
@Table(name = "bot_message")
public class BotMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;
    private final String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

    public BotMessage(String content) {
        this.content = content;
    }

    public BotMessage(){

    }

    @Override
    public String toString() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}