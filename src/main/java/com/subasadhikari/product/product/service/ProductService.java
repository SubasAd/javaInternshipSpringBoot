package com.subasadhikari.product.product.service;

import com.subasadhikari.product.product.dtos.ProductDTO;

import com.subasadhikari.product.product.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(long id);
    List<Product> findByCategory(String category);
    Product addNewProduct(ProductDTO productDTO);
    Product updateProduct(Long id,ProductDTO productDTO);
    Product deleteById(Long id);
    List<Product> findAll(PageRequest pageable);






}