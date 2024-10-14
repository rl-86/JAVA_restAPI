package com.example.java_restapi;

import com.example.java_restapi.entities.Product;
import com.example.java_restapi.entities.ProductRecord;
import com.example.java_restapi.service.Warehouse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/products")
public class ProductResource {

    private static final Logger logger = LoggerFactory.getLogger(ProductResource.class);

    @Inject
    private Warehouse warehouse;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(@Valid Product product) {
        logger.info("Trying to add product: {}", product.getName());
        try {
            warehouse.addProduct(product);
            logger.info("Product {} added", product.getName());
            return Response.status(Response.Status.CREATED).entity(product).build();
        } catch (IllegalArgumentException e) {
            logger.error("Error adding product: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts() {
        logger.info("Fetching all products");
        List<ProductRecord> products = warehouse.getProducts();
        return Response.ok(products).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam("id") @Min(1) int id) {
        logger.info("Fetching product with id: {}", id);
        ProductRecord product = warehouse.getProductById(id);
        if (product == null) {
            logger.warn("Product with id: {} not found", id);
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }
        return Response.ok(product).build();
    }

    @GET
    @Path("/category/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByCategory(@PathParam("category") @NotEmpty String category) {
        logger.info("Fetching products in category: {}", category);
        try {
            Product.Category productCategory = Product.Category.valueOf(category.toUpperCase());
            List<ProductRecord> products = warehouse.getProductsByCategory(productCategory);
            return Response.ok(products).build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid category: {}", category);
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid category").build();
        }
    }
}