package com.service.becatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "Code is mandatory, please fill it!")
    @NotNull(message = "Code name is mandatory, please fill it!")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Invalid format code")
    private String code;

    @NotBlank(message = "Category name is mandatory, please fill it!")
    @NotNull(message = "Category name is mandatory, please fill it!")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Invalid format name")
    private String name;
    private String username;

}
