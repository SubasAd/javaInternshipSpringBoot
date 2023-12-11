package com.subasadhikari.product.product.service;

import com.subasadhikari.product.product.dtos.ProductDTO;
import com.subasadhikari.product.product.entity.Product;

public class CheckNullElse {
static String getDescription(Product pr, ProductDTO pd){
   return pd.getDescription() != null ? pd.getDescription() : pr.getDescription();

}
static String getName(Product pr, ProductDTO pd){
        return pd.getName() != null ? pd.getName() : pr.getName();
    }
 static  Double getPrice(Product pr, ProductDTO pd) {
     return pd.getPrice() != null ? pd.getPrice() : pr.getPrice();
 }
 static String getCategory(Product pr, ProductDTO pd){
     return pd.getCategory() != null ? pd.getCategory() : pr.getCategory();
 }
}
