package com.dev.javadevproject.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "product_entity")
public class ProductEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer Id;

    public String name;
    private String description;
    public String category;
    private Integer price;
    private Integer discount;

    public String getName() { return name; }

    public String getСategory() { return category; }

    public Integer getPrice() { return price; }

    public void setPrice(int Price) { this.price = Price; }

    public Integer getDiscount() { return discount; }

    public void setDiscount(int Discount) { this.discount = Discount; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Используется только в тестах
    public ProductEntity(Integer id, String name, String description, String category, Integer price, Integer discount) {
        this.Id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.discount = discount;
    }

    public ProductEntity(String name, String description, String category, Integer price, Integer discount) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.discount = discount;
    }

    public ProductEntity(){

    }
}
