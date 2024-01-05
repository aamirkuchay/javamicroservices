package com.developer.Product.Service.service;

import com.developer.Product.Service.entity.Product;
import com.developer.Product.Service.model.ProductRequest;
import com.developer.Product.Service.model.ProductResponse;

import java.util.List;

public interface ProductService {
    long addProduct(ProductRequest productRequest);


    List<Product> getAll();

    ProductResponse getProductById(long productId);

    List<ProductResponse> getAllProducts();

    void reduceQuantity(long productId, long quantity);
}
