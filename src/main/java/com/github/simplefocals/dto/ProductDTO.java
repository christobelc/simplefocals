package com.github.simplefocals.dto;


public class ProductDTO {

    private Integer id;
    private String name;
    private int categoryId;
    private float price;
    private String description;
    private String imageName;

    public ProductDTO() {
    }

    public ProductDTO(Integer id, String name, Integer categoryId, float price, String description, String imageName) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.description = description;
        this.imageName = imageName;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

}
