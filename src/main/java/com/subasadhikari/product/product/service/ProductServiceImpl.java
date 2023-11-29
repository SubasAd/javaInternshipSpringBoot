package com.subasadhikari.product.product.service;

import com.subasadhikari.product.product.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Override
    public ResponseEntity<Product> findById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Product>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<Product> deleteById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Product> Update(Product product) {
        return null;
    }

    @Override
    public void Create(Product product) {

    }

    @Override
    public ResponseEntity<List<Product>> findByTagsName(String tagName) {
        return null;
    }
}
