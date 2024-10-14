package com.example.java_restapi.service;

import com.example.java_restapi.entities.Product;
import com.example.java_restapi.entities.ProductRecord;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class Warehouse {

    public Warehouse() {}

    private final List<Product> products = new CopyOnWriteArrayList<>();
    private static int nextId = 1;

    @PostConstruct
    public void init() {
        Stream.of(
                new Product(0, "Bread", Product.Category.FOOD, 4, LocalDate.of(2024, 9, 12)),
                new Product(0, "Milk", Product.Category.FOOD, 5, LocalDate.of(2024, 9, 12)),
                new Product(0, "Butter", Product.Category.FOOD, 3, LocalDate.of(2024, 9, 12)),
                new Product(0, "Cheese", Product.Category.FOOD, 4, LocalDate.of(2024, 9, 12)),
                new Product(0, "Apple", Product.Category.FOOD, 5, LocalDate.of(2024, 9, 12)),
                new Product(0, "Banana", Product.Category.FOOD, 4, LocalDate.of(2024, 9, 12)),
                new Product(0, "Table", Product.Category.FURNITURE, 3, LocalDate.of(2024, 9, 12)),
                new Product(0, "Chair", Product.Category.FURNITURE, 4, LocalDate.of(2024, 9, 12)),
                new Product(0, "Sofa", Product.Category.FURNITURE, 5, LocalDate.of(2024, 9, 12)),
                new Product(0, "Screwdriver", Product.Category.TOOLS, 4, LocalDate.of(2024, 9, 12)),
                new Product(0, "Hammer", Product.Category.TOOLS, 5, LocalDate.of(2024, 9, 12)),
                new Product(0, "Saw", Product.Category.TOOLS, 4, LocalDate.of(2024, 9, 12)),
                new Product(0, "Drill", Product.Category.TOOLS, 5, LocalDate.of(2024, 9, 12)),
                new Product(0, "Screw", Product.Category.TOOLS, 4, LocalDate.of(2024, 9, 12))
        ).forEach(this::addProduct);
    }

    public List<ProductRecord> getProducts() {
        return products.stream()
                .map(ProductRecord::map)
                .collect(Collectors.toList());
    }

    public void addProduct(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        product.setId(nextId++);
        products.add(product);
    }

    public ProductRecord getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .map(ProductRecord::map)
                .findFirst()
                .orElse(null);
    }

    public List<ProductRecord> getProductsByCategory(Product.Category category) {
        return products.stream()
                .filter(product -> product.getCategory() == category)
                .sorted(Comparator.comparing(Product::getName))
                .map(ProductRecord::map)
                .collect(Collectors.toList());
    }

}