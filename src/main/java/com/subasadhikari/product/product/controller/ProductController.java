package com.subasadhikari.product.product.controller;

import com.subasadhikari.product.product.entity.Product;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.subasadhikari.product.product.service.ProductService;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class ProductController {
  @Autowired
    ProductService productService;
    @GetMapping("/products/{productId}")
    ResponseEntity<Product> findProduct(@PathVariable Long productId) {
        return this.productService.findById(productId);
    }

    @GetMapping("/products")
    ResponseEntity<List<Product>> findAllProducts() {
        final ResponseEntity<List<Product>> all = this.productService.findAll();
        return all;
    }

    @PostMapping("/admin/products")
    ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        productService.Create(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/admin/products/{productId}")
    ResponseEntity<Product> deleteProduct(@PathVariable Long productId) {
        return this.productService.deleteById(productId);
    }



}
