package com.service.becatalog.controller;

import com.service.becatalog.dto.CategoryRequest;
import com.service.becatalog.dto.Response;
import com.service.becatalog.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldReturnOkResponse() {
        CategoryRequest categoryRequest = new CategoryRequest("CategoryCode", "CategoryName", "username");
        when(categoryService.create(any(CategoryRequest.class))).thenReturn(createMockResponse());

        ResponseEntity<Object> response = categoryController.create(categoryRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(categoryService, times(1)).create(categoryRequest);
    }

    @Test
    void getCategoryList_shouldReturnOkResponse() {
        when(categoryService.getCategoryList()).thenReturn(createMockResponse());

        ResponseEntity<Object> response = categoryController.getCategoryList();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(categoryService, times(1)).getCategoryList();
    }

    @Test
    void getById_shouldReturnOkResponse() {
        String categoryId = "1";
        when(categoryService.getById(categoryId)).thenReturn(createMockResponse());

        ResponseEntity<Object> response = categoryController.getById(categoryId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(categoryService, times(1)).getById(categoryId);
    }

    @Test
    void update_shouldReturnOkResponse() {
        String categoryId = "1";
        CategoryRequest categoryRequest = new CategoryRequest("CategoryCode", "CategoryName", "username");
        when(categoryService.update(categoryId, categoryRequest)).thenReturn(createMockResponse());

        ResponseEntity<Object> response = categoryController.update(categoryId, categoryRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(categoryService, times(1)).update(categoryId, categoryRequest);
    }

    @Test
    void delete_shouldReturnOkResponse() {
        // Arrange
        String categoryId = "1";
        String username = "username";
        when(categoryService.delete(categoryId, username)).thenReturn(createMockResponse());

        ResponseEntity<Object> response = categoryController.delete(categoryId, username);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(categoryService, times(1)).delete(categoryId, username);
    }

    private Response<Object> createMockResponse() {
        return Response.builder()
                .responseCode(200)
                .responseMessage("Success")
                .data("Mock data")
                .build();
    }
}
