package com.subasadhikari.product.product.entity;
import com.subasadhikari.product.product.dtos.ProductDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private String name;
    private String description;
    private String category;

    public Long getId() {
        return id;
    }


    public Double getPrice() {

        return price;
    }

    public Product() {
    }

    public Product(Long id, Double price, String name, String description, String category) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}