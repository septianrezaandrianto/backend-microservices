package com.service.becatalog.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestPage {

    @NotNull(message = "Price name is mandatory, please fill it!")
    private int pageNumber;
    @NotNull(message = "Price name is mandatory, please fill it!")
    private int pageSize;
    private String productName;
    private String categoryId;

}
