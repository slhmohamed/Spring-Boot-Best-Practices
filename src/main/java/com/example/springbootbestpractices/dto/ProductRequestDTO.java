package com.example.springbootbestpractices.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductRequestDTO {

    @NotBlank(message = "product name shouldn't be NULL OR EMPTY")
    private String name;

    private String description;

    @NotBlank(message = "product type shouldn't be NULL OR EMPTY")
    private String productType;

    @Min(value = 1,message = "quantity is not defined !")
    private int quantity;

    @Min(value = 200, message = "product price can't be less than 200")
    @Max(value = 500000, message = "product price can't be more than 5000")
    private double price;


}