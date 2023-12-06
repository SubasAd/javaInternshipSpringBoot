package com.subasadhikari.product.product;

import com.subasadhikari.product.product.dtos.ProductDTO;
import com.subasadhikari.product.product.dtos.ProductMapper;
import com.subasadhikari.product.product.entity.Product;
import com.subasadhikari.product.product.exception.ProductNotFoundException;
import com.subasadhikari.product.product.repository.ProductRepository;
import com.subasadhikari.product.product.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

public class ServiceTest {
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testFindAll(){

        List<Product> products= new ArrayList<>();
        products.add(new Product());
        when(this.productRepository.findAll()).thenReturn(products);

        ResponseEntity<List<ProductDTO>> outputAllProducts = productService.findAll();

        assertNotNull("The provided object shouldn't be null",outputAllProducts.getBody());
        assertEquals("One item has been inserted",outputAllProducts.getBody().size(),1);
    }
    @Test
    public void testFindById(){
      Product  product = new Product(5L,195D,"Soap","Very nice Soap","Hygiene");
      ProductDTO productDTO = new ProductDTO(5L,195D,"Soap","Very nice Soap","Hygiene");
      when(this.productRepository.findById(5L)).thenReturn(Optional.of(product));
      when(this.productMapper.productToProductDTO(any())).thenReturn(productDTO);

      ResponseEntity<ProductDTO> productDTOResponseEntity =productService.findById(5L);

      assertEquals("The productDTO must have id 5 and all specifications as product object",productDTOResponseEntity.getBody().getId(),5L);

    }
    @Test
    public void testFindByIdProductNotFoundException(){
        when(this.productRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,()->productService.findById(5L));

    }

    @Test
    public void testFindByCategory()
    {

        Product  product = new Product(5L,195D,"Soap","Very nice Soap","Hygiene");
        ProductDTO productDTO = new ProductDTO(5L,195D,"Soap","Very nice Soap","Hygiene");
        List<Product> products= new ArrayList<>();
        products.add(product);

        when(this.productRepository.findByCategory("Hygiene")).thenReturn(products);
        when(this.productMapper.productToProductDTO(any())).thenReturn(productDTO);


        assertEquals("The name of first product of the result should be soap",productService.findByCategory("Hygiene").getBody().get(0).getName(),"Soap");

    }
    @Test
    public void testAddNewProduct()
    {
        Product  product = new Product(5L,195D,"Soap","Very nice Soap","Hygiene");
        ProductDTO productDTO = new ProductDTO(5L,195D,"Soap","Very nice Soap","Hygiene");

        when(this.productRepository.save(any())).thenReturn(product);
        when(this.productMapper.productDTOToProduct(any())).thenReturn(product);
        when(this.productMapper.productToProductDTO(any())).thenReturn(productDTO);


        ResponseEntity<ProductDTO> responseEntity = this.productService.addNewProduct(productDTO);
        assertEquals("The name of product of the result should be soap",responseEntity.getBody().getName(),"Soap");

    }
    @Test
    public void testUpdateProduct()
    {
        Product  product = new Product(5L,195D,"Soap","Very nice Soap","Hygiene");
        ProductDTO productDTO = new ProductDTO(5L,250D,"Soap","Very nice Soap","Hygiene");
        ProductDTO updatedDTO = new ProductDTO();
        updatedDTO.setPrice(250D);
        when(this.productRepository.save(any())).thenReturn(product);
        when(this.productMapper.productDTOToProduct(any())).thenReturn(product);
        when(this.productMapper.productToProductDTO(any())).thenReturn(productDTO);
        when(this.productRepository.findById(5L)).thenReturn(Optional.of(product));

        assertEquals("The price should be updated to 250",this.productService.updateProduct(5L,updatedDTO).getBody().getPrice(),250D);
    }
    @Test
    public void testUpdateProductWithException()
    {
        when(this.productRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,()->{productService.updateProduct(5L,new ProductDTO());});
    }
    @Test
    public void testDeleteById()
    {
        Product  product = new Product(5L,195D,"Soap","Very nice Soap","Hygiene");
        ProductDTO productDTO = new ProductDTO(5L,250D,"Soap","Very nice Soap","Hygiene");


        when(this.productMapper.productDTOToProduct(any())).thenReturn(product);
        when(this.productMapper.productToProductDTO(any())).thenReturn(productDTO);
        when(this.productRepository.findById(5L)).thenReturn(Optional.of(product));

        assertEquals("After Deletion object should return ok",this.productService.deleteById(5L).getStatusCode(), HttpStatus.OK);

    }
    @Test
    public void testDeleteByIdWithException()
    {
        when(this.productRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,()->this.productService.deleteById(5L), "Id is not in db");

    }



}