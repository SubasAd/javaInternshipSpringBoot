package com.subasadhikari.product.product.repository;

import com.subasadhikari.product.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
