package com.developer.Product.Service.service;

import com.developer.Product.Service.entity.Product;
import com.developer.Product.Service.exception.ProductServiceCustomException;
import com.developer.Product.Service.model.ProductRequest;
import com.developer.Product.Service.model.ProductResponse;
import com.developer.Product.Service.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("adding product..");
        Product product = Product.builder()
                .productName(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepository.save(product);
        log.info("Product Created..");
        return product.getProductId();
    }

    @Override
    public List<Product> getAll() {
     List<Product> p =    productRepository.findAll();
        ProductRequest productRequest = new ProductRequest();
        BeanUtils.copyProperties(p,productRequest);
        return p;
    }

    @Override
    public ProductResponse getProductById(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductServiceCustomException("Product with given id"+productId+" not found","PRODUCT_NOT_FOUND"));
        ProductResponse productResponse = ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
        return  productResponse;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
       Iterable<Product>  product =  productRepository.findAll();
       List<ProductResponse> productResponseList = new ArrayList<>();
       for(Product p: product){
           ProductResponse pp = ProductResponse.builder()
                   .productId(p.getProductId())
                   .productName(p.getProductName())
                   .price(p.getPrice())
                   .quantity(p.getQuantity()).build();
           productResponseList.add(pp);
       }

       return productResponseList;

    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce quantity {} for id:{}",productId,quantity);
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductServiceCustomException("Product with given id"+productId+" not found","PRODUCT_NOT_FOUND"));
        if(product.getQuantity() < quantity){
            throw new ProductServiceCustomException("Product does not have sufficient quantity","INSUFFICIENT QUANTITY");

        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }
}
