package com.subasadhikari.product.product.dtos;




public class ProductDTO {
    private Long id;
    private Double price;
    private String name;
    private String description;
    private String category;

    public ProductDTO() {
    }

    public ProductDTO(Long id, Double price, String name, String description, String category) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
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
