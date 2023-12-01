package com.subasadhikari.product.product.service;

import com.subasadhikari.product.product.dtos.ProductDTO;
import com.subasadhikari.product.product.entity.Product;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface ProductService {
    ResponseEntity<List<ProductDTO>> findAll();
    ResponseEntity<ProductDTO> findById(long id);
    ResponseEntity<List<ProductDTO>> findByCategory(String category);
    ResponseEntity<ProductDTO> addNewProduct(ProductDTO productDTO);
    ResponseEntity<ProductDTO> updateProduct(Long id,ProductDTO productDTO);
    ResponseEntity<Void> deleteById(Long id);




}