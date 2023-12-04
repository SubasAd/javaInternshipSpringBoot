package com.subasadhikari.product.product;

import com.subasadhikari.product.product.controller.ProductController;
import com.subasadhikari.product.product.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProductController.class)
public class ControllerTest {
    @MockBean
    private ProductServiceImpl productService;
    @Autowired
    private MockMvc mockMvc ;
    @Test
    public void testFindAllProducts(){

    }

}
