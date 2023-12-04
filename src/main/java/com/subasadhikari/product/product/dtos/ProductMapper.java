package com.subasadhikari.product.product.dtos;

import com.subasadhikari.product.product.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ProductMapper {

    ProductDTO productToProductDTO(Product p);

    Product productDTOToProduct(ProductDTO productDTO);

}
