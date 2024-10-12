package com.example.java_restapi;
import java.time.LocalDate;

public class Product {

    public enum Category {
        FOOD, FURNITURE, TOOLS, OTHER
    }

    private int id;
    private String name;
    private Category category;
    private int rating;
    private final LocalDate createdAt;
    private LocalDate updatedAt;

    public Product(int id, String name, Category category, int rating, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Category getCategory() {
        return category;
    }
    public int getRating() {
        return rating;
    }
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setName(String name) {
        if (!this.name.equals(name)) {
            this.name = name;
            this.updatedAt = LocalDate.now();
        }
    }

    public void setCategory(Category category) {
        if (this.category != category) {
            this.category = category;
            this.updatedAt = LocalDate.now();
        }
    }

    public void setRating(int rating) {
        if (this.rating != rating) {
            this.rating = rating;
            this.updatedAt = LocalDate.now();
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "\t\t" +
                name + "\t\t\t\t" + category + "\t\t\t\t" + rating +  "\t\t\t\t" +createdAt +  "\t\t\t\t" +updatedAt;
    }
}