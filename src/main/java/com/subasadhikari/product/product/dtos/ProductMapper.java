package com.subasadhikari.product.product.dtos;

import ch.qos.logback.core.model.ComponentModel;
import com.subasadhikari.product.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ProductMapper {

    ProductDTO productToProductDTO(Product p);

    Product productDTOToProduct(ProductDTO productDTO);

}
