package com.service.becatalog.controller;

import com.service.becatalog.dto.ProductRequest;
import com.service.becatalog.dto.RequestPage;
import com.service.becatalog.dto.Response;
import com.service.becatalog.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldReturnOkResponse() {
        ProductRequest productRequest = new ProductRequest("ProductName", BigDecimal.valueOf(10.0), 10, "test", "kmzway87aa", "username");
        when(productService.create(any(ProductRequest.class))).thenReturn(createMockResponse());

        ResponseEntity<Object> response = productController.create(productRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(productService, times(1)).create(productRequest);
    }

    @Test
    void getProductPerPage_shouldReturnOkResponse() {
        RequestPage requestPage = new RequestPage(0, 10, "testName", "testCategoryId");
        when(productService.getProductPerPage(any(RequestPage.class))).thenReturn(createMockResponse());

        ResponseEntity<Object> response = productController.getProductPerPage(requestPage);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(productService, times(1)).getProductPerPage(requestPage);
    }

    @Test
    void getById_shouldReturnOkResponse() {
        String productId = "1";
        when(productService.getById(productId)).thenReturn(createMockResponse());

        ResponseEntity<Object> response = productController.getById(productId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(productService, times(1)).getById(productId);
    }

    @Test
    void update_shouldReturnOkResponse() {
        String productId = "1";
        ProductRequest productRequest = new ProductRequest("ProductName", BigDecimal.valueOf(10.0), 10, "test", "kmzway87aa", "username");
        when(productService.update(productId, productRequest)).thenReturn(createMockResponse());

        ResponseEntity<Object> response = productController.update(productId, productRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(productService, times(1)).update(productId, productRequest);
    }

    @Test
    void delete_shouldReturnOkResponse() {
        String productId = "1";
        String username = "username";
        when(productService.delete(productId, username)).thenReturn(createMockResponse());

        ResponseEntity<Object> response = productController.delete(productId, username);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(productService, times(1)).delete(productId, username);
    }

    private Response<Object> createMockResponse() {
        return Response.builder()
                .responseCode(200)
                .responseMessage("Success")
                .data("Mock data")
                .build();
    }
}