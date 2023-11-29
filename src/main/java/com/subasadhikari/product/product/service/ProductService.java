package com.subasadhikari.product.product.service;

import com.subasadhikari.product.product.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    ResponseEntity<Product> findById(Long id);
    ResponseEntity<List<Product>> findAll();
    ResponseEntity<Product> deleteById(Long id);
    ResponseEntity<Product> Update(Product product);
    void Create(Product product);
    ResponseEntity<List<Product>> findByTagsName(String tagName);
}
