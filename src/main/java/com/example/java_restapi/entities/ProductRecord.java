package com.example.java_restapi.entities;

import java.time.LocalDate;

public record ProductRecord(int id, String name, Product.Category category, int rating, LocalDate createdAt, LocalDate updatedAt) {

    public static ProductRecord map(Product product) {
        return new ProductRecord(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getRating(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    @Override
    public String toString() {
        return id + "\t\t" +
                name + "\t\t\t\t" + category + "\t\t\t\t" + rating +  "\t\t\t\t" + createdAt +  "\t\t\t\t" + updatedAt;
    }
}