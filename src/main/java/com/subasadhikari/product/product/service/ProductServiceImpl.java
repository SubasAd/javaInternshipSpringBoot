package com.subasadhikari.product.product.service;

import com.subasadhikari.product.product.dtos.ProductDTO;
import com.subasadhikari.product.product.dtos.ProductMapper;
import com.subasadhikari.product.product.entity.Product;
import com.subasadhikari.product.product.exception.ProductNotFoundException;
import com.subasadhikari.product.product.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {
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
        return ResponseEntity.ok(this.productRepository.findAll()
                .stream()
                .map(this.productMapper::productToProductDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<ProductDTO> findById(long id) {
        return ResponseEntity.ok(this.productRepository.findById(id)
                .map(this.productMapper::productToProductDTO)
                .orElseThrow(
                        () -> new ProductNotFoundException("The product of this Id not found"))
        );
    }

    @Override
    public ResponseEntity<List<ProductDTO>> findByCategory(String category) {

        Optional<List<Product>> result = Optional.ofNullable(productRepository.findByCategory(category));

        return ResponseEntity.ok(result.get()
                .stream()
                .map(this.productMapper::productToProductDTO)
                .toList());
    }

    @Override
    public ResponseEntity<ProductDTO> addNewProduct(ProductDTO productDTO) {

       Product pr =  this.productRepository.save(this.productMapper.productDTOToProduct(productDTO));
       return ResponseEntity.ok(this.productMapper.productToProductDTO(pr));
    }

    @Override
    public ResponseEntity<ProductDTO> updateProduct(Long id, ProductDTO productDTO) {
        Product pr = getProduct(id, productDTO);
        this.productRepository.save(pr);
        return ResponseEntity.ok(this.productMapper.productToProductDTO(pr));
    }

    private Product getProduct(Long id, ProductDTO productDTO) {
        Optional<Product> optPr = this.productRepository.findById(id);
        Product pr = optPr.orElseThrow(() -> new ProductNotFoundException("The product of this Id not found"));
        pr.setPrice(productDTO.getPrice() != null ? productDTO.getPrice() : pr.getPrice());
        pr.setName(productDTO.getName() != null ? productDTO.getName() : pr.getName());
        pr.setDescription(productDTO.getDescription() != null ? productDTO.getDescription() : pr.getDescription());
        pr.setCategory(productDTO.getCategory() != null ? productDTO.getCategory() : pr.getCategory());
        return pr;
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id) {
        Optional<Product> optPr = this.productRepository.findById(id);
        Product pr = optPr.orElseThrow(() -> new ProductNotFoundException("The product of this Id not found"));
        this.productRepository.deleteById(pr.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
