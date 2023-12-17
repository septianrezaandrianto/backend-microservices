package com.service.becatalog.service;

import com.service.becatalog.constant.Constant;
import com.service.becatalog.dto.CategoryRequest;
import com.service.becatalog.dto.Response;
import com.service.becatalog.entity.Category;
import com.service.becatalog.exception.BadRequestCustomException;
import com.service.becatalog.exception.DataExistException;
import com.service.becatalog.exception.NotFoundException;
import com.service.becatalog.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    Category category = mappingCategory("kd001", "Makanan", "Reza");
    CategoryRequest categoryRequest = mappingCategoryRequest("kd001", "Makanan", "Reza");
    CategoryRequest categoryRequestFailed = mappingCategoryRequest("kd002", "Makanan", "Reza");

    @Test
    void create_shouldCreateCategorySuccessfully() {
        when(categoryRepository.findByCode("kd001")).thenReturn(null);
        when(categoryRepository.save(any(Category.class))).thenReturn(this.category);

        Response<Object> response = categoryService.create(mappingCategoryRequest("kd001", "Makanan", "Reza"));

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(Constant.Response.SUCCESS_CODE, response.getResponseCode());
        assertEquals(Constant.Response.SUCCESS_MESSAGE, response.getResponseMessage());

        verify(categoryRepository, times(1)).findByCode("kd001");
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void create_shouldThrowDataExistExceptionWhenCategoryExists() {
        when(categoryRepository.findByCode("kd001")).thenReturn(this.category);

        assertThrows(DataExistException.class, () -> categoryService.create(mappingCategoryRequest("kd001", "Makanan", "Reza")));
        verify(categoryRepository, times(1)).findByCode("kd001");
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void getCategoryList_shouldReturnCategoryList() {
        when(categoryRepository.findCategoryList()).thenReturn(Collections.singletonList(this.category));

        Response<Object> response = categoryService.getCategoryList();

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(Constant.Response.SUCCESS_CODE, response.getResponseCode());
        assertEquals(Constant.Response.SUCCESS_MESSAGE, response.getResponseMessage());

        verify(categoryRepository, times(1)).findCategoryList();
    }

    @Test
    void getById_shouldReturnCategorySuccessfully() {
        String categoryId = "1";
        when(categoryRepository.findByCategoryId(categoryId)).thenReturn(this.category);

        Response<Object> response = categoryService.getById(categoryId);

        assertNotNull(response);
        assertEquals(Constant.Response.SUCCESS_CODE, response.getResponseCode());
        assertEquals(Constant.Response.SUCCESS_MESSAGE, response.getResponseMessage());
        assertNotNull(response.getData());
        assertEquals(this.category, response.getData());
        verify(categoryRepository, times(1)).findByCategoryId(categoryId);
    }

    @Test
    void getById_shouldThrowNotFoundExceptionWhenCategoryIsNull() {
        String categoryId = "1";
        when(categoryRepository.findByCategoryId(categoryId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> categoryService.getById(categoryId));
        verify(categoryRepository, times(1)).findByCategoryId(categoryId);
    }

    @Test
    void update_shouldUpdateCategorySuccessfully() {
        String categoryId = "kmzway87aa";
        when(categoryRepository.findByCategoryId(categoryId)).thenReturn(this.category);
        when(categoryRepository.save(any(Category.class))).thenReturn(this.category);

        Response<Object> response = categoryService.update(categoryId, this.categoryRequest);

        assertNotNull(response);
        assertEquals(Constant.Response.SUCCESS_CODE, response.getResponseCode());
        assertEquals(Constant.Response.SUCCESS_MESSAGE, response.getResponseMessage());
        assertNotNull(response.getData());
        verify(categoryRepository, times(1)).findByCategoryId(categoryId);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void update_shouldThrowNotFoundExceptionWhenCategoryIsNull() {
        String categoryId = "1";
        when(categoryRepository.findByCategoryId(categoryId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> categoryService.update(categoryId, categoryRequest));
        verify(categoryRepository, times(1)).findByCategoryId(categoryId);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void update_shouldThrowBadRequestExceptionWhenCodeMismatch() {
        // Arrange
        String categoryId = "kmzway87aa";
        when(categoryRepository.findByCategoryId(categoryId)).thenReturn(this.category);

        // Act and Assert
        assertThrows(BadRequestCustomException.class, () -> categoryService.update(categoryId, this.categoryRequestFailed));
        verify(categoryRepository, times(1)).findByCategoryId(categoryId);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void delete_shouldDeleteCategorySuccessfully() {
        String categoryId = "kmzway87aa";
        when(categoryRepository.findByCategoryId(categoryId)).thenReturn(this.category);

        Response<Object> response = categoryService.delete(categoryId, "Reza");

        assertNotNull(response);
        assertEquals(Constant.Response.SUCCESS_CODE, response.getResponseCode());
        assertEquals(Constant.Response.SUCCESS_MESSAGE, response.getResponseMessage());
        verify(categoryRepository, times(1)).findByCategoryId(categoryId);
        verify(categoryRepository, times(1)).deleteById(categoryId, "Reza", categoryService.nowDate);
    }

    @Test
    void delete_shouldThrowNotFoundExceptionWhenCategoryIsNull() {
        String categoryId = "kmzway87aa";
        when(categoryRepository.findByCategoryId(categoryId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> categoryService.delete(categoryId, "Reza"));
        verify(categoryRepository, times(1)).findByCategoryId(categoryId);
        verify(categoryRepository, never()).deleteById(anyString(), anyString(), any(Date.class));
    }

    private Category mappingCategory(String code, String name, String username) {
        return Category.builder()
                .id("kmzway87aa")
                .code(code)
                .name(name)
                .createdBy(username)
                .createdDate(new Date())
                .isDeleted(false)
                .build();
    }

    private CategoryRequest mappingCategoryRequest(String code, String name, String username) {
        return CategoryRequest.builder()
                .code(code)
                .name(name)
                .username(username)
                .build();
    }

}
