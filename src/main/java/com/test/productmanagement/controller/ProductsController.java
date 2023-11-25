package com.test.productmanagement.controller;

import com.test.productmanagement.model.Products;
import com.test.productmanagement.model.dto.ProductsRequestDTO;
import com.test.productmanagement.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {
    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProducts(@RequestBody ProductsRequestDTO requestDTO) {
        productsService.addProducts(requestDTO);
        return ResponseEntity.ok("Records added successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> allProducts = productsService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }
}
