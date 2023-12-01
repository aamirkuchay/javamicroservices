package com.developer.Product.Service.controller;

import com.developer.Product.Service.entity.Product;
import com.developer.Product.Service.model.ProductRequest;
import com.developer.Product.Service.model.ProductResponse;
import com.developer.Product.Service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
       long productId = productService.addProduct(productRequest);
       return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
   public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId){
        ProductResponse productResponses = (ProductResponse) productService.getProductById(productId);
        return new ResponseEntity<>(productResponses,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        List<ProductResponse> responses = productService.getAllProducts();
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }


}
