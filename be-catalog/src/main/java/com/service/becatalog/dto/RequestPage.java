package com.service.becatalog.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestPage {

    @NotNull(message = "Price name is mandatory, please fill it!")
    private int pageNumber;
    @NotNull(message = "Price name is mandatory, please fill it!")
    private int pageSize;
    private String searchFilter;

}
