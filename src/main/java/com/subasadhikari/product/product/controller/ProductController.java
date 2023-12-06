package com.subasadhikari.product.product.controller;

import com.subasadhikari.product.product.dtos.ProductDTO;
import com.subasadhikari.product.product.entity.Product;
import com.subasadhikari.product.product.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    ProductRepository productRepository;

    public ProductController(ProductService productService,ProductRepository productRepository) {
        this.productService = productService;
         this.productRepository=productRepository;
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
        System.out.println(product);
        ResponseEntity re =  productService.addNewProduct(product);
        System.out.println(re.getBody());
        return re;
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
    @CrossOrigin
    @GetMapping("/products/query")
    ResponseEntity<List<ProductDTO>> findProductwithQueries(

            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") String direction
    )
    {
        var pageRequestData = PageRequest.of(pageNumber - 1, size, Sort.Direction.valueOf(direction), sort);
        return this.productService.findAll(pageRequestData);
    }




}
