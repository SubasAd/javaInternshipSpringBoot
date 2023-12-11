package com.subasadhikari.product.product.controller;

import com.subasadhikari.product.product.dtos.ProductDTO;
import com.subasadhikari.product.product.dtos.ProductMapper;
import com.subasadhikari.product.product.dtos.ProductMapperImpl;
import com.subasadhikari.product.product.entity.Product;
import com.subasadhikari.product.product.exception.ProductNotFoundException;
import com.subasadhikari.product.product.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import com.subasadhikari.product.product.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice
@RequestMapping("/api")
public class ProductController {
    final
    ProductService productService;
    ProductRepository productRepository;
    ProductMapper productMapper;
    public ProductController(ProductService productService,ProductRepository productRepository,ProductMapper productMapper) {
        this.productService = productService;
         this.productRepository=productRepository;
         this.productMapper = productMapper;
    }

    @CrossOrigin
    @GetMapping("/products/{productId}")
    ResponseEntity<ProductDTO> findProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productMapper.productToProductDTO(this.productService.findById(productId)));

    }

    @CrossOrigin
    @GetMapping("/products")
    ResponseEntity<List<ProductDTO>> findAllProducts() {
         List<Product> products = this.productService.findAll();
       return  ResponseEntity.ok(products
               .stream()
               .map(productMapper::productToProductDTO)
               .collect(Collectors.toList()));
    }

    @CrossOrigin
    @PostMapping("/products")
    ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO product) {
        return ResponseEntity.ok(this.productMapper.productToProductDTO(productService.addNewProduct(product)));
    }
    @CrossOrigin
    @GetMapping("/products/category/{category}")
    ResponseEntity<List<ProductDTO>> findByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.findByCategory(category).stream().map(productMapper::productToProductDTO).toList());
    }

    @CrossOrigin
    @DeleteMapping("/products/{productId}")
    ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productMapper.productToProductDTO(this.productService.deleteById(productId)));    }

    @CrossOrigin
    @PutMapping("/products/{id}")
    ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO product) {
        return ResponseEntity.ok(this.productMapper.productToProductDTO(productService.updateProduct(id,product)));

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
        return ResponseEntity.ok(this.productService.findAll(pageRequestData).stream().map(productMapper::productToProductDTO).toList());
    }




}
