package com.subasadhikari.product.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subasadhikari.product.product.controller.ProductController;
import com.subasadhikari.product.product.dtos.ProductDTO;
import com.subasadhikari.product.product.dtos.ProductMapper;
import com.subasadhikari.product.product.repository.ProductRepository;
import com.subasadhikari.product.product.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
public class ControllerTest {
    @MockBean
    private ProductServiceImpl productService;
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private MockMvc mockMvc ;
    @Mock
    private ProductMapper productMapper;

    @Test
    public void testFindAllProducts() throws Exception {
            List<ProductDTO> prd = new ArrayList<>();
            when(productService.findAll()).thenReturn(ResponseEntity.ok(prd));
            mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    public void testFindProduct() throws Exception{
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        when(productService.findById(1L)).thenReturn(ResponseEntity.ok(productDTO));
        mockMvc.perform(get("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{ \"id\": 1 }"));


    }
    @Test
    public void testSaveProduct() throws Exception {
        ProductDTO prd = new ProductDTO();
        prd.setId(1L);
        prd.setCategory("clothes");
        prd.setDescription("Very nice clothes");
        prd.setPrice(965.2D);
        prd.setName("T-shirt");
        when(productService.addNewProduct(any(ProductDTO.class))).thenReturn(ResponseEntity.ok(prd));

        String mp = new ObjectMapper().writeValueAsString(prd);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mp))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.price").value(965.2))
                .andExpect(jsonPath("$.name").value("T-shirt"))
                .andExpect(jsonPath("$.description").value("Very nice clothes"))
                .andExpect(jsonPath("$.category").value("clothes"));
    }
    @Test
    public void testFindByCategory() throws Exception {
        List<ProductDTO> productDTOS = new ArrayList<>();
        when(productService.findByCategory("tshirt")).thenReturn(ResponseEntity.ok(productDTOS));
        mockMvc.perform(get("/api/products/category/tshirt")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testDeleteProduct() throws Exception{
        when(productService.deleteById(5L)).thenReturn(new ResponseEntity(HttpStatus.OK));
        mockMvc.perform(delete("/api/products/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdateProduct() throws Exception
    {
        ProductDTO prd = new ProductDTO();
        prd.setId(1L);
        prd.setCategory("clothes");
        prd.setDescription("Very nice clothes");
        prd.setPrice(965.2D);
        prd.setName("T-shirt");
        when(productService.updateProduct(any(Long.class),any(ProductDTO.class))).thenReturn(ResponseEntity.ok(prd));

        String mp = new ObjectMapper().writeValueAsString(prd);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mp))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.price").value(965.2))
                .andExpect(jsonPath("$.name").value("T-shirt"))
                .andExpect(jsonPath("$.description").value("Very nice clothes"))
                .andExpect(jsonPath("$.category").value("clothes"));
    }



}
