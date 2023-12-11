package com.subasadhikari.product.product.service;

import com.subasadhikari.product.product.dtos.ProductDTO;
import com.subasadhikari.product.product.dtos.ProductMapper;
import com.subasadhikari.product.product.entity.Product;
import com.subasadhikari.product.product.exception.ProductNotFoundException;
import com.subasadhikari.product.product.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Product findById(long id) {
        return this.productRepository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException("The product of this Id not found"));
    }

    @Override
    public List<Product> findByCategory(String category) {

        Optional<List<Product>> result = Optional.ofNullable(productRepository.findByCategory(category));

        return result.get();
    }

    @Override
    public Product addNewProduct(ProductDTO productDTO) {

       Product pr =  this.productRepository.save(this.productMapper.productDTOToProduct(productDTO));
       return pr;
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product pr = getProduct(id, productDTO);
        this.productRepository.save(pr);
        return pr;
    }

    private Product getProduct(Long id, ProductDTO productDTO) {
        Optional<Product> optPr = this.productRepository.findById(id);
        Product pr = optPr.orElseThrow(() -> new ProductNotFoundException("The product of this Id not found"));
        extracted(productDTO, pr);
        return pr;
    }

    private static void extracted(ProductDTO productDTO, Product pr) {
        pr.setPrice(productDTO.getPrice() != null ? productDTO.getPrice() : pr.getPrice());
        pr.setName(CheckNullElse.getName(pr, productDTO));
        pr.setDescription(CheckNullElse.getDescription(pr,productDTO));
        pr.setCategory(CheckNullElse.getCategory(pr,productDTO));
    }


    @Override
    public Product deleteById(Long id) {
        Optional<Product> optPr = this.productRepository.findById(id);
        Product pr = optPr.orElseThrow(() -> new ProductNotFoundException("The product of this Id not found"));
        this.productRepository.deleteById(pr.getId());

        return optPr.get();
    }

    @Override
    public List<Product> findAll(PageRequest pageable) {
        Page<Product> productPage = this.productRepository.findAll(pageable);
        return productPage
                .stream()
                .toList();
    }
}
