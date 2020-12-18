package com.example.springboot.Domain;

import javax.persistence.*;

@Entity
@Table(name = "Menu_item")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private float price;
    private String type;

    public MenuItem(long id, String name, float price, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public MenuItem() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
    public String printInfo() {
        return "MenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MenuItem))
            return false;
        if (obj == this)
            return true;
        return ((MenuItem) obj).getName().equals(name);
    }
}
