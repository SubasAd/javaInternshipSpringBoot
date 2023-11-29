package com.subasadhikari.product.product.dtos;

import com.subasadhikari.product.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {
    @Mapping(target = "id",source="id")
    @Mapping(target = "price",source = "price")
    @Mapping(target="name",source="name")
    @Mapping(target="description",source = "description")
    @Mapping(target="category",source="category")
    ProductDTO productToProductDTO(Product p);

}
