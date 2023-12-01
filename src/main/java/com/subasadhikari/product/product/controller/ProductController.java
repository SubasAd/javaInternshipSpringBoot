package com.subasadhikari.product.product.controller;

import com.subasadhikari.product.product.dtos.ProductDTO;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import com.subasadhikari.product.product.service.ProductService;

import java.util.List;

@RestController
@ControllerAdvice
@RequestMapping("/api")
public class ProductController {
    final
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @CrossOrigin
    @GetMapping("/products/{productId}")
    ResponseEntity<ProductDTO> findProduct(@PathVariable Long productId) {
        return this.productService.findById(productId);
    }

    @CrossOrigin
    @GetMapping("/products")
    ResponseEntity<List<ProductDTO>> findAllProducts() {
        return this.productService.findAll();
    }

    @CrossOrigin
    @PostMapping("/products")
    ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO product) {
        return productService.addNewProduct(product);

    }
    @CrossOrigin
    @GetMapping("/products/category/{category}")
    ResponseEntity<List<ProductDTO>> findByCategory(@PathVariable String category) {
        return productService.findByCategory(category);
    }

    @CrossOrigin
    @DeleteMapping("/products/{productId}")
    ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        return this.productService.deleteById(productId);
    }

    @CrossOrigin
    @PutMapping("/products/{id}")
    ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO product) {
        return productService.updateProduct(id,product);

    }


}
