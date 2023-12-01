package com.subasadhikari.product.product.service;

import com.subasadhikari.product.product.dtos.ProductDTO;
import com.subasadhikari.product.product.dtos.ProductMapper;
import com.subasadhikari.product.product.entity.Product;
import com.subasadhikari.product.product.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService{
    final
    ProductRepository productRepository;
    final
    ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public ResponseEntity<List<ProductDTO>> findAll() {
        return ResponseEntity.ok(this.productRepository.findAll().stream().map(this.productMapper::productToProductDTO).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<ProductDTO> findById(long id) {
        return ResponseEntity.ok(this.productRepository.findById(id).map(this.productMapper::productToProductDTO).get());
    }

    @Override
    public ResponseEntity<List<ProductDTO>> findByCategory(String category) {
        List<Product> l = this.productRepository.findAll();
        l = l.stream().map((product)->{
            if(product.getCategory()==null) return null;
            if(product.getCategory().equals(category)) return product;
            return null;
        }).toList();
        List<Product> pr = new ArrayList<>();
        for(Product each:l){
            if(each==null) continue;
            pr.add(each);
        }

         return ResponseEntity.ok(pr.stream().map(this.productMapper::productToProductDTO).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<ProductDTO> addNewProduct(ProductDTO productDTO) {
        this.productRepository.save(this.productMapper.productDTOToProduct(productDTO));
        return ResponseEntity.ok(productDTO);
    }

    @Override
    public ResponseEntity<ProductDTO> updateProduct(Long id,ProductDTO productDTO) {
        Product pr = this.productRepository.getReferenceById(id);
        pr.setPrice(productDTO.getPrice() != null ? productDTO.getPrice() : pr.getPrice());
        pr.setName(productDTO.getName() != null ? productDTO.getName() : pr.getName());
        pr.setDescription(productDTO.getDescription() != null ? productDTO.getDescription() : pr.getDescription());
        pr.setCategory(productDTO.getCategory() != null ? productDTO.getCategory() : pr.getCategory());
        this.productRepository.save(pr);
        return ResponseEntity.ok(this.productMapper.productToProductDTO(pr));
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id) {

          this.productRepository.deleteById(id);

       return new ResponseEntity<>(HttpStatus.OK);
    }
}
