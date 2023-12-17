package com.service.becatalog.service;

import com.service.becatalog.constant.Constant;
import com.service.becatalog.dto.CategoryRequest;
import com.service.becatalog.dto.ProductRequest;
import com.service.becatalog.dto.RequestPage;
import com.service.becatalog.dto.Response;
import com.service.becatalog.entity.Category;
import com.service.becatalog.entity.Product;
import com.service.becatalog.exception.DataExistException;
import com.service.becatalog.exception.NotFoundException;
import com.service.becatalog.repository.CategoryRepository;
import com.service.becatalog.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    Category category = mappingCategory("kd001", "Makanan", "Reza");
    CategoryRequest categoryRequest = mappingCategoryRequest("kd001", "Makanan", "Reza");
    CategoryRequest categoryRequestFailed = mappingCategoryRequest("kd002", "Makanan", "Reza");

    ProductRequest productRequest = mappingProductRequest("bubur", BigDecimal.valueOf(1000), 10, "Reza", "kmzway87aa");
    Product product = mappingProduct("bubur", BigDecimal.valueOf(1000), 10, "kd001", "Makanan", "Reza");

    @Test
    void create_shouldCreateProductSuccessfully() {
        when(categoryRepository.findByCategoryId("kmzway87aa")).thenReturn(this.category);
        when(productRepository.findByName("nasi")).thenReturn(this.product);
        when(productRepository.save(any(Product.class))).thenReturn(this.product);

        Response<Object> response = productService.create(productRequest);

        // Assert
        assertNotNull(response);
        assertEquals(Constant.Response.SUCCESS_CODE, response.getResponseCode());
        assertEquals(Constant.Response.SUCCESS_MESSAGE, response.getResponseMessage());
        assertNotNull(response.getData());
        assertEquals(this.product, response.getData());
        verify(categoryRepository, times(1)).findByCategoryId("kmzway87aa");
        verify(productRepository, times(1)).findByName("bubur");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void create_shouldThrowNotFoundExceptionWhenCategoryIsNull() {
        when(categoryRepository.findByCategoryId("nonExistentCategoryId")).thenReturn(null);

        assertThrows(NotFoundException.class, () -> productService.create(this.productRequest));
        verify(categoryRepository, times(1)).findByCategoryId("kmzway87aa");
        verify(productRepository, never()).findByName(anyString());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void create_shouldThrowDataExistExceptionWhenProductExists() {
        when(categoryRepository.findByCategoryId("kmzway87aa")).thenReturn(this.category);
        when(productRepository.findByName("bubur")).thenReturn(this.product);

        assertThrows(DataExistException.class, () -> productService.create(productRequest));
        verify(categoryRepository, times(1)).findByCategoryId("kmzway87aa");
        verify(productRepository, times(1)).findByName("bubur");
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void getProductPerPage_shouldReturnProductsPerPage() {
        Date nowDate = new Date();

        RequestPage requestPage = new RequestPage(0, 10, "bubur", "kmzway87aa");
        Pageable paging = PageRequest.of(requestPage.getPageNumber(), requestPage.getPageSize());

        List<Product> productList = Arrays.asList(
                new Product("1", "bubur", BigDecimal.valueOf(10000), 20 ,"test 1", this.category, nowDate, "Reza", nowDate, "Reza", false),
                new Product("2", "bubur ayam", BigDecimal.valueOf(15000), 25, "test 2", this.category, nowDate, "Reza", nowDate, "Reza", false)
        );

        Page<Product> mockProductPage = new PageImpl<>(productList, paging, productList.size());
        when(productRepository.findPerPage("bubur", "kmzway87aa", paging)).thenReturn(mockProductPage);

        Response<Object> response = productService.getProductPerPage(requestPage);

        assertNotNull(response);
        assertEquals(Constant.Response.SUCCESS_CODE, response.getResponseCode());
        assertEquals(Constant.Response.SUCCESS_MESSAGE, response.getResponseMessage());
        assertNotNull(response.getData());
    }

    @Test
    void getById_shouldReturnProductSuccessfully() {
        when(productRepository.findByProductId("kmzway87aw")).thenReturn(this.product);

        Response<Object> response = productService.getById("kmzway87aw");

        assertNotNull(response);
        assertEquals(Constant.Response.SUCCESS_CODE, response.getResponseCode());
        assertEquals(Constant.Response.SUCCESS_MESSAGE, response.getResponseMessage());
        assertNotNull(response.getData());
        assertEquals(this.product, response.getData());
        verify(productRepository, times(1)).findByProductId("kmzway87aw");
    }

    @Test
    void getById_shouldThrowNotFoundExceptionWhenProductIsNull() {
        String productId = "kmzway87aw";
        when(productRepository.findByProductId(productId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> productService.getById(productId));
        verify(productRepository, times(1)).findByProductId(productId);
    }

    @Test
    void update_shouldUpdateProductSuccessfully() {
        String productId = "kmzway87aw";
        when(categoryRepository.findByCategoryId("kmzway87aa")).thenReturn(this.category);
        when(productRepository.findByProductId(productId)).thenReturn(this.product);
        when(productRepository.save(any(Product.class))).thenReturn(this.product);

        Response<Object> response = productService.update(productId, productRequest);

        assertNotNull(response);
        assertEquals(Constant.Response.SUCCESS_CODE, response.getResponseCode());
        assertEquals(Constant.Response.SUCCESS_MESSAGE, response.getResponseMessage());
        assertNotNull(response.getData());
        verify(categoryRepository, times(1)).findByCategoryId("kmzway87aa");
        verify(productRepository, times(1)).findByProductId(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void update_shouldThrowNotFoundExceptionWhenCategoryIsNull() {
        // Arrange
        String productId = "kmzway87aw";
        when(categoryRepository.findByCategoryId("nonExistentCategoryId")).thenReturn(null);

        assertThrows(NotFoundException.class, () -> productService.update(productId, productRequest));
        verify(categoryRepository, times(1)).findByCategoryId("kmzway87aa");
        verify(productRepository, never()).findByProductId(anyString());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void update_shouldThrowNotFoundExceptionWhenProductIsNull() {
        String  productId= "kmzway87aw";
        when(categoryRepository.findByCategoryId("kmzway87aa")).thenReturn(this.category);
        when(productRepository.findByProductId(productId)).thenReturn(null);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> productService.update(productId, productRequest));
        verify(categoryRepository, times(1)).findByCategoryId("kmzway87aa");
        verify(productRepository, times(1)).findByProductId(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void delete_shouldDeleteProductSuccessfully() {
        String productId = "kmzway87aw";
        String username = "Reza";
        when(productRepository.findByProductId(productId)).thenReturn(this.product);

        Response<Object> response = productService.delete(productId, username);

        assertNotNull(response);
        assertEquals(Constant.Response.SUCCESS_CODE, response.getResponseCode());
        assertEquals(Constant.Response.SUCCESS_MESSAGE, response.getResponseMessage());
        verify(productRepository, times(1)).findByProductId(productId);
    }

    @Test
    void delete_shouldThrowNotFoundExceptionWhenProductIsNull() {
        String productId = "kmzway87aw";
        String username = "Reza";
        when(productRepository.findByProductId(productId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> productService.delete(productId, username));
        verify(productRepository, times(1)).findByProductId(productId);
        verify(productRepository, never()).deleteById(anyString(), anyString(), any(Date.class));
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

    private Product mappingProduct(String name, BigDecimal price, int qty, String categoryCode, String categoryName, String username) {
        return Product.builder()
                .id("kmzway87aw")
                .name(name)
                .price(price)
                .quantity(qty)
                .createdBy(username)
                .createdDate(new Date())
                .modifiedBy(username)
                .modifiedDate(new Date())
                .category(mappingCategory(categoryCode, categoryName, username))
                .isDeleted(false)
                .build();
    }

    private ProductRequest mappingProductRequest(String name, BigDecimal price, int qty, String username, String categoryId) {
        return ProductRequest.builder()
                .name(name)
                .price(price)
                .quantity(qty)
                .description("Dummy description")
                .username(username)
                .categoryId(categoryId)
                .build();
    }
}
