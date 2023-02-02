package com.example.springbootbestpractices.service;


import com.example.springbootbestpractices.dto.ProductRequestDTO;
import com.example.springbootbestpractices.dto.ProductResponseDTO;
import com.example.springbootbestpractices.entity.Product;
import com.example.springbootbestpractices.exception.ProductNotFoundException;
import com.example.springbootbestpractices.exception.ProductServiceBusinessException;
import com.example.springbootbestpractices.repository.ProductRepository;
import com.example.springbootbestpractices.utils.ValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.Cacheable;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private ProductRepository productRepository;


    public ProductResponseDTO createNewProduct(ProductRequestDTO productRequestDTO) throws ProductServiceBusinessException {
        ProductResponseDTO productResponseDTO;

        try {
            log.info("ProductService:createNewProduct execution started.");
            Product product = ValueMapper.convertToEntity(productRequestDTO);
            log.debug("ProductService:createNewProduct request parameters {}", ValueMapper.jsonAsString(productRequestDTO));

            Product productResults = productRepository.save(product);
            productResponseDTO = ValueMapper.convertToDTO(productResults);
            log.debug("ProductService:createNewProduct received response from Database {}", ValueMapper.jsonAsString(productRequestDTO));

        } catch (Exception ex) {
            log.error("Exception occurred while persisting product to database , Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while create a new product");
        }
        log.info("ProductService:createNewProduct execution ended.");
        return productResponseDTO;
    }

    @Cacheable(value = "product")
    public List<ProductResponseDTO> getProducts() throws ProductServiceBusinessException {
        List<ProductResponseDTO> productResponseDTOS = null;

        try {
            log.info("ProductService:getProducts execution started.");

            List<Product> productList = productRepository.findAll();

            if (!productList.isEmpty()) {
                productResponseDTOS = productList.stream()
                        .map(ValueMapper::convertToDTO)
                        .collect(Collectors.toList());
            } else {
                productResponseDTOS = Collections.emptyList();
            }

            log.debug("ProductService:getProducts retrieving products from database  {}", ValueMapper.jsonAsString(productResponseDTOS));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving products from database , Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch all products from Database");
        }

        log.info("ProductService:getProducts execution ended.");
        return productResponseDTOS;
    }

    /**
     * this method will fetch product from DB by ID
     *
     * @param productId
     * @return product response from DB
     */
    @Cacheable(value = "product")
    public ProductResponseDTO getProductById(long productId) {
        ProductResponseDTO productResponseDTO;
        try {
            log.info("ProductService:getProductById execution started.");
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));
            productResponseDTO = ValueMapper.convertToDTO(product);

            log.debug("ProductService:getProductById retrieving product from database for id {} {}", productId, ValueMapper.jsonAsString(productResponseDTO));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving product {} from database , Exception message {}", productId, ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch product from Database " + productId);
        }
        log.info("ProductService:getProductById execution ended.");
        return productResponseDTO;
    }

    @Cacheable(value = "product")
    public Map<String, List<ProductResponseDTO>> getProductsByTypes() {
        try {
            log.info("ProductService:getProductsByTypes execution started.");

            Map<String, List<ProductResponseDTO>> productsMap =
                    productRepository.findAll().stream()
                            .map(ValueMapper::convertToDTO)
                            .filter(productResponseDTO -> productResponseDTO.getProductType() != null)
                            .collect(Collectors.groupingBy(ProductResponseDTO::getProductType));

            log.info("ProductService:getProductsByTypes execution ended.");
            return productsMap;

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving product grouping by type from database , Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch product from Database ");
        }
    }

    public ProductResponseDTO updateProduct(Long id,ProductRequestDTO productRequestDTO) throws ProductServiceBusinessException {
        ProductResponseDTO productResponseDTO;

        try {
            log.info("ProductService:updateProduct execution started.");
            Product product =productRepository.findById(id).get();
            product.setName(productRequestDTO.getName());
            product.setDescription(productRequestDTO.getDescription());
            product.setPrice(productRequestDTO.getPrice());
            product.setQuantity(productRequestDTO.getQuantity());
            product.setProductType(productRequestDTO.getProductType());
            log.debug("ProductService:updateProduct request parameters {}", ValueMapper.jsonAsString(productRequestDTO));

            Product productResults = productRepository.save(product);
            productResponseDTO = ValueMapper.convertToDTO(productResults);
            log.debug("ProductService:updateProduct received response from Database {}", ValueMapper.jsonAsString(productRequestDTO));

        } catch (Exception ex) {
            log.error("Exception occurred while persisting product to database , Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while update a product");
        }
        log.info("ProductService:updateProduct execution ended.");
        return productResponseDTO;
    }

    /**
     * this method will delete product from DB by ID
     *
     * @param productId
     */
    @Cacheable(value = "product")
    public void deleteProductById(long productId) {
        ProductResponseDTO productResponseDTO;
        try {
            log.info("ProductService:deleteProductById execution started.");
             productRepository.deleteById(productId);


            log.debug("ProductService:getProductById retrieving product from database for id {} {}", productId);

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving product {} from database , Exception message {}", productId, ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch product from Database " + productId);
        }
        log.info("ProductService:getProductById execution ended.");

    }


}