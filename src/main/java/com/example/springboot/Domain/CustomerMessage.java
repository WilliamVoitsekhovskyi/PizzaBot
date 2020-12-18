package com.example.springboot.Domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "customer_message")
public class CustomerMessage {
    @Id
    private long id;

    private String content;
    private final String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

    public CustomerMessage(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public CustomerMessage() {

    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

}