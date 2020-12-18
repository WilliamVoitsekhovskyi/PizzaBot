package com.example.springboot.Domain;

import com.example.springboot.OrderReciever.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class BotResponse {

    private final long id;
    private final String content;
    private final float price;
    private final float discount;
    private final float deliveryPrice;
    private final String code;
    private final String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

    public BotResponse(long id, String customerOrder, Order order) {
        this.id = id;
        this.content = order.getResponse(customerOrder);
        this.discount = order.getDiscount();
        this.deliveryPrice = order.getDELIVERY_PRICE();
        this.price = order.getTotalPrice();
        this.code = order.getCode();
    }


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", content='" + content + '\'' +
                ", price='" + price + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public float getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }


    public float getDiscount() {
        return discount;
    }

    public float getDeliveryPrice() {
        return deliveryPrice;
    }

    public String getCode() {
        return code;
    }
}