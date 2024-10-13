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

    public List<ProductRecord> getProducts() {
        return products.stream()
                .map(ProductRecord::map)
                .collect(Collectors.toList());
    }

    public void addProduct(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        products.add(product);
        product.setId(nextId++);
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

    @PostConstruct
    public void init() {
        Stream.of(
                new Product(nextId++, "Bread", Product.Category.FOOD, 4, LocalDate.of(2024, 9, 12)),
                new Product(nextId++, "Milk", Product.Category.FOOD, 5, LocalDate.of(2024, 9, 12)),
                new Product(nextId++, "Butter", Product.Category.FOOD, 3, LocalDate.of(2024, 9, 12)),
                new Product(nextId++, "Cheese", Product.Category.FOOD, 4, LocalDate.of(2024, 9, 12)),
                new Product(nextId++, "Apple", Product.Category.FOOD, 5, LocalDate.of(2024, 9, 12)),
                new Product(nextId++, "Banana", Product.Category.FOOD, 4, LocalDate.of(2024, 9, 12)),
                new Product(nextId++, "Table", Product.Category.FURNITURE, 3, LocalDate.of(2024, 9, 12)),
                new Product(nextId++, "Chair", Product.Category.FURNITURE, 4, LocalDate.of(2024, 9, 12)),
                new Product(nextId++, "Sofa", Product.Category.FURNITURE, 5, LocalDate.of(2024, 9, 12)),
                new Product(nextId++, "Screwdriver", Product.Category.TOOLS, 4, LocalDate.of(2024, 9, 12)),
                new Product(nextId++, "Hammer", Product.Category.TOOLS, 5, LocalDate.of(2024, 9, 12)),
                new Product(nextId++, "Saw", Product.Category.TOOLS, 4, LocalDate.of(2024, 9, 12)),
                new Product(nextId++, "Drill", Product.Category.TOOLS, 5, LocalDate.of(2024, 9, 12)),
                new Product(nextId++, "Screw", Product.Category.TOOLS, 4, LocalDate.of(2024, 9, 12))
        ).forEach(this::addProduct);
    }

    }