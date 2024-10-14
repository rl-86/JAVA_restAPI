## Java rest API

### Summary of Implemented Functions and Endpoints:



Base URL: http://localhost:8080/JAVA_restAPI-1.0-SNAPSHOT

1. Get all products
   
    **GET** /api/products


3. Get product by ID
   
   **GET** /api/products/{id}


5. Get roducts by category( FOOD, FURNITURE, TOOLS, OTHER )
   
   **GET** /api/products/category/{category}


7. Add new product
   
   **POST** /api/products

   Content-Type: application/json
   
   Body:
   
        {
        "name": "New Product",
        "category": "FOOD",
        "rating": 5
        }
