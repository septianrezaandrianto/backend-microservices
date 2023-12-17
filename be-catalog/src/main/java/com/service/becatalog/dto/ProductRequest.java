package com.service.becatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "Product name is mandatory, please fill it!")
    @NotNull(message = "Product name is mandatory, please fill it!")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Invalid format name")
    private String name;

    @NotNull(message = "Price name is mandatory, please fill it!")
    private BigDecimal price;

    @NotNull(message = "Quantity name is mandatory, please fill it!")
    private int quantity;

    private String description;

    @NotBlank(message = "Category is mandatory, please fill it!")
    @NotNull(message = "Category is mandatory, please fill it!")
    private String categoryId;

    private String username;
}
