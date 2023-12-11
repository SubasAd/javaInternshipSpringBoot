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
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
    public void whenFindAllProducts_shouldReturnListOfAllProducts(){

        List<Product> products= new ArrayList<>();
        products.add(new Product());
        when(this.productRepository.findAll()).thenReturn(products);

        List<Product> outputAllProducts = productService.findAll();

        assertNotNull("The provided object shouldn't be null",outputAllProducts);
        assertEquals("One item has been inserted",outputAllProducts.size(),1);
    }


    @Test
    public void givenId_shouldReturnProductOfThatId(){
      Product  product = new Product(5L,195D,"Soap","Very nice Soap","Hygiene");
      ProductDTO productDTO = new ProductDTO(5L,195D,"Soap","Very nice Soap","Hygiene");
      when(this.productRepository.findById(5L)).thenReturn(Optional.of(product));
      when(this.productMapper.productToProductDTO(any())).thenReturn(productDTO);

      Product  productFromService =productService.findById(5L);

      assertEquals("The productDTO must have id 5 and all specifications as product object",productFromService.getId(),5L);

    }
    @Test
    public void whenProductIdNotInDb_ShouldReturnProductNotFoundException(){
        when(this.productRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,()->productService.findById(5L));

    }

    @Test
    public void givenCategory_shouldReturnListOfProductsOfCategory()
    {

        Product  product = new Product(5L,195D,"Soap","Very nice Soap","Hygiene");
        ProductDTO productDTO = new ProductDTO(5L,195D,"Soap","Very nice Soap","Hygiene");
        List<Product> products= new ArrayList<>();
        products.add(product);

        when(this.productRepository.findByCategory("Hygiene")).thenReturn(products);
        when(this.productMapper.productToProductDTO(any())).thenReturn(productDTO);


        assertEquals("The name of first product of the result should be soaps category hygiene and that ha",productService.findByCategory("Hygiene").get(0).getCategory(),"Hygiene");

    }
    @Test
    public void givenNewProduct_inAddNewProduct_shouldReturnTheAddedProductWithId()
    {
        Product  product = new Product(5L,195D,"Soap","Very nice Soap","Hygiene");
        ProductDTO productDTO = new ProductDTO(5L,195D,"Soap","Very nice Soap","Hygiene");

        when(this.productRepository.save(any())).thenReturn(product);
        when(this.productMapper.productDTOToProduct(any())).thenReturn(product);
        when(this.productMapper.productToProductDTO(any())).thenReturn(productDTO);


        Product pr = this.productService.addNewProduct(productDTO);
        assertEquals("The name of product of the result should be soap",pr.getName(),"Soap");
        assertEquals("The name of product of the result should be soap",pr.getId(),5L);
    }
    @Test
    public void givenProduct_inUpdateProduct_shouldUpdateTheGivenProduct()
    {
        Product  product = new Product(5L,195D,"Soap","Very nice Soap","Hygiene");
        ProductDTO productDTO = new ProductDTO(5L,250D,"Soap","Very nice Soap","Hygiene");
        ProductDTO updatedDTO = new ProductDTO();
        updatedDTO.setPrice(250D);
        when(this.productRepository.save(any())).thenReturn(product);
        when(this.productMapper.productDTOToProduct(any())).thenReturn(product);
        when(this.productMapper.productToProductDTO(any())).thenReturn(productDTO);
        when(this.productRepository.findById(5L)).thenReturn(Optional.of(product));

        assertEquals("The price should be updated to 250",this.productService.updateProduct(5L,updatedDTO).getPrice(),250D);
    }
    @Test
    public void whenUpdate_ifNoProductInDB_shouldThrowException()
    {
        when(this.productRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,()->{productService.updateProduct(5L,new ProductDTO());});
    }
    @Test
    public void getId_whenDelete_shouldReturnDeletedProduct()
    {
        Product  product = new Product(5L,195D,"Soap","Very nice Soap","Hygiene");
        ProductDTO productDTO = new ProductDTO(5L,250D,"Soap","Very nice Soap","Hygiene");


        when(this.productMapper.productDTOToProduct(any())).thenReturn(product);
        when(this.productMapper.productToProductDTO(any())).thenReturn(productDTO);
        when(this.productRepository.findById(5L)).thenReturn(Optional.of(product));

        assertEquals("After Deletion,deleted object should be returned",this.productService.deleteById(5L).getId(),5L);

    }
    @Test
    public void getId_whenProductNotInDb_shouldThrowProductNotFoundException()
    {
        when(this.productRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,()->this.productService.deleteById(5L), "Id is not in db");

    }
    @Test
    public void givenPageNumber1PageSize2_shouldReturn2Products(){

        PageRequest pageable = PageRequest.of(1,2, Sort.Direction.ASC,"name");


        List<Product> productList = List.of(
                new Product(1L, 495D,"Product1","Desc1","cat1"),
                new Product(2L, 500D,"Product2","Desc2","cat2")
        );
        Page<Product> productPage = new PageImpl<>(productList);


        when(this.productRepository.findAll(any(PageRequest.class))).thenReturn(productPage);


        List<ProductDTO> productDTOList = List.of(
                new ProductDTO(1L, 495D,"Product1","Desc1","cat1"),
                new ProductDTO(2L, 500D,"Product2","Desc2","cat2")
        );


        when(productMapper.productToProductDTO(productList.get(0))).thenReturn(productDTOList.get(0));
        when(productMapper.productToProductDTO(productList.get(1))).thenReturn(productDTOList.get(1));

        List<Product> products = this.productService.findAll(pageable);


        assertEquals("Checking Size",productDTOList.size(), products.size());
    }

         }



