package com.example.springbootbestpractices.utils;


import com.example.springbootbestpractices.dto.ProductRequestDTO;
import com.example.springbootbestpractices.dto.ProductResponseDTO;
import com.example.springbootbestpractices.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ValueMapper {

    public static Product convertToEntity(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setProductType(productRequestDTO.getProductType());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setPrice(productRequestDTO.getPrice());

        return product;
    }

    public static ProductResponseDTO convertToDTO(Product product){
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDesc(product.getDescription());
        productResponseDTO.setProductType(product.getProductType());
        productResponseDTO.setQuantity(product.getQuantity());
        productResponseDTO.setPrice(product.getPrice());

        return productResponseDTO;
    }


    public static String jsonAsString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}