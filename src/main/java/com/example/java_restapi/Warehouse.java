package com.example.java_restapi;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Warehouse {

    public static Scanner sc = new Scanner(System.in);
    public static String index = ("id\t\tName\t\t\tCategory\t\tRating\t\tCreated at\t\t\tUpdated at");
    public static String goBack = ("\nPress \"Enter\" to go back to main menu");

    private final List<Product> products = new CopyOnWriteArrayList<>();
    private static int nextId = 1;
    // Reset nextId to 1 for testing
    public void resetNextId() {
        nextId = 1;
    }

    public Product getProductByIdAsProduct(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
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

    // Find product by id to remove
    public boolean removeProductById(int id) {
        Product productToRemove = products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);

        if (productToRemove != null) {
            products.remove(productToRemove);
            return true;
        }

        return false;
    }

    // Case "5"  Method to display products by category
    public void displayProductsByCategory() {
        System.out.println("Display products by category");
        System.out.println("Choose a category: 1. FOOD, 2. FURNITURE, 3. TOOLS or \"Enter\" for OTHER");
        String choice = sc.nextLine();

        Product.Category category;

        switch (choice) {
            case "1":
                category = Product.Category.FOOD;
                break;
            case "2":
                category = Product.Category.FURNITURE;
                break;
            case "3":
                category = Product.Category.TOOLS;
                break;
            default:
                category = Product.Category.OTHER;
        }

        List<ProductRecord> sortedProducts = products.stream()
                .filter(product -> product.getCategory() == category)
                .sorted(Comparator.comparing(Product::getName))
                .map(product -> new ProductRecord(
                        product.getId(),
                        product.getName(),
                        product.getCategory(),
                        product.getRating(),
                        product.getCreatedAt(),
                        product.getUpdatedAt()))
                .toList();

        if (sortedProducts.isEmpty()) {
            System.out.println("No products found in the selected category.");
        } else {
            System.out.println(index);
            sortedProducts.forEach(System.out::println);
        }
        System.out.println(goBack);
        sc.nextLine();
    }

    // Case 6 Show all new products
    public List<ProductRecord> getProductsCreatedAfter(LocalDate date) {
        return products.stream()
                .filter(product -> product.getCreatedAt().isAfter(date))
                .map(ProductRecord::map)
                .collect(Collectors.toList());
    }

    public void displayProductsCreatedAfter() {
        System.out.println("Find products created after a specific date");
        System.out.println("Enter the date (yyyy-mm-dd) to find products created after:");
        String inputDate = sc.nextLine();
        LocalDate date;

        try {
            date = LocalDate.parse(inputDate);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-mm-dd.");
            return;
        }
        System.out.println(index);
        List<ProductRecord> productsCreatedAfter = getProductsCreatedAfter(date);

        if (productsCreatedAfter.isEmpty()) {
            System.out.println("No products found created after " + date);
        } else {
            productsCreatedAfter.forEach(System.out::println);
        }
        System.out.println(goBack);
        sc.nextLine();
    }

    // Case 7 Show all modified products
    public List<ProductRecord> getProductsModifiedAfterCreation() {
        return products.stream()
                .filter(product -> !product.getCreatedAt().equals(product.getUpdatedAt()))
                .map(ProductRecord::map)
                .collect(Collectors.toList());
    }

    public void displayModifiedProducts() {
        List<ProductRecord> modifiedProducts = getProductsModifiedAfterCreation();
        System.out.println("Products modified after creation:");
        if (modifiedProducts.isEmpty()) {
            System.out.println("No products have been modified since they were created.");
        } else {
            System.out.println(index);
            modifiedProducts.forEach(System.out::println);
        }
        System.out.println(goBack);
        sc.nextLine();
    }

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();

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
        ).forEach(warehouse::addProduct);

        String choice;
        do {
            // Main menu
            System.out.println("\nThe Warehouse");
            System.out.println("========");
            System.out.println("1. Add product");
            System.out.println("2. Modify product");
            System.out.println("3. Remove product");
            System.out.println("4. Show all products");
            System.out.println("5. Sort by category");
            System.out.println("6. Find products by date");
            System.out.println("7. Show all modified products");
            System.out.println("e. Exit");
            System.out.println("\nMenu option + \"Enter\"");

            choice = sc.nextLine();

            switch (choice) {
                case "1":
                    // Enter name
                    String name = "";
                    System.out.println("Add new product");
                    while (name.isBlank()) {
                        System.out.println("Enter product name:");
                        name = sc.nextLine();
                        if (name.isBlank()) {
                            System.out.println("Name can't be empty, try again!");
                        }
                    }
                    // Enter category
                    Product.Category category;
                    System.out.println("Enter product category, Choose from: 1. Food, 2. Furniture, 3. Tools or press Enter for \"Other\"");
                    String input = sc.nextLine();

                    category = switch (input) {
                        case "1" -> Product.Category.FOOD;
                        case "2" -> Product.Category.FURNITURE;
                        case "3" -> Product.Category.TOOLS;
                        default -> Product.Category.OTHER;
                    };
                    // Enter rating
                    int rating = -1;
                    while (rating < 0 || rating > 10) {
                        System.out.println("Enter product rating (0-10):");

                        try {
                            rating = sc.nextInt();

                            if (rating < 0 || rating > 10) {
                                System.out.println("Invalid input, rating must be between 0 and 10.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input, please enter a number between 0 and 10.");
                            sc.next();
                        }
                    }
                    // New product added
                    sc.nextLine();
                    warehouse.addProduct(new Product(0, name, category, rating, LocalDate.now()));
                    System.out.println(name + " was successfully added");
                    System.out.println(goBack);
                    sc.nextLine();
                    break;

                case "2":
                    // Modify product by id
                    int modifyId;
                    System.out.println("Enter the id of the product you want to modify:");
                    modifyId = sc.nextInt();
                    sc.nextLine();
                    Product productToModify = warehouse.getProductByIdAsProduct(modifyId);
                    if (productToModify == null) {
                        System.out.println("Product with id " + modifyId + " not found");
                        System.out.println(goBack);
                        sc.nextLine();
                        break;
                    }
                    System.out.println(index);
                    System.out.println(warehouse.getProductById(modifyId));

                    System.out.println("\nWhat do you want to modify?");
                    System.out.println("1. Name, 2. Category, 3. Rating");
                    String modifyChoice = sc.nextLine();
                    switch (modifyChoice) {
                        case "1":
                            String newName = "";
                            while (newName.isBlank()) {
                                System.out.println("Enter the new product name:");
                                newName = sc.nextLine();
                                if (newName.isBlank()) {
                                    System.out.println("Name can't be empty, try again!");
                                }
                            }
                            productToModify.setName(newName);
                            System.out.println("Name was successfully updated");
                            break;
                        case "2":
                            System.out.println("Enter new category, Choose from: 1. Food, 2. Furniture, 3. Tools or press Enter for \"Other\"");
                            String newCategory = sc.nextLine();
                            Product.Category category1;
                            switch (newCategory) {
                                case "1":
                                    category1 = Product.Category.FOOD;
                                    break;
                                case "2":
                                    category1 = Product.Category.FURNITURE;
                                    break;
                                case "3":
                                    category1 = Product.Category.TOOLS;
                                    break;
                                default:
                                    category1 = Product.Category.OTHER;
                            }
                            productToModify.setCategory(category1);
                            System.out.println("Category was successfully updated");
                            break;
                        case "3":
                            int newRating = -1;
                            while (newRating < 0 || newRating > 10) {
                                System.out.println("Enter product rating (0-10):");

                                try {
                                    newRating = sc.nextInt();

                                    if (newRating < 0 || newRating > 10) {
                                        System.out.println("Invalid input, rating must be between 0 and 10.");
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input, please enter a number between 0 and 10.");
                                    sc.next();
                                }
                            }
                            productToModify.setRating(newRating);
                            System.out.println("Rating was successfully updated");
                            sc.nextLine();
                            break;
                        default:
                            System.out.println("Invalid input");
                    }
                    break;

                case "3":
                    // Remove product by id
                    int removeId;
                    System.out.print("Remove product by id: ");
                    removeId = sc.nextInt();
                    sc.nextLine();
                    warehouse.removeProductById(removeId);
                    System.out.println("Product with id " + removeId + " was successfully removed");
                    System.out.println(goBack);
                    sc.nextLine();
                    break;

                case "4":
                    // List all products
                    System.out.println("All products:\n");
                    System.out.println(index);
                    warehouse.getProducts().forEach(System.out::println);
                    System.out.println(goBack);
                    sc.nextLine();
                    break;

                case "5":
                    warehouse.displayProductsByCategory();
                    break;

                case "6":
                    warehouse.displayProductsCreatedAfter();
                    break;

                case "7":
                    warehouse.displayModifiedProducts();
                    break;

                default:
            }
        } while (!choice.equalsIgnoreCase("e"));

        System.out.println("Avslutar...");
    }
}