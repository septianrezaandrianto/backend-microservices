package com.service.becatalog.service;

import com.service.becatalog.constant.Constant;
import com.service.becatalog.dto.RequestPage;
import com.service.becatalog.dto.ProductRequest;
import com.service.becatalog.dto.Response;
import com.service.becatalog.entity.Category;
import com.service.becatalog.entity.Product;
import com.service.becatalog.exception.DataExistException;
import com.service.becatalog.exception.NotFoundException;
import com.service.becatalog.repository.CategoryRepository;
import com.service.becatalog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    Date nowDate = new Date();

    @Transactional
    public Response<Object> create(ProductRequest productRequest) {
        Category category = categoryRepository.findByCategoryId(productRequest.getCategoryId());
        if(Objects.isNull(category)) {
            throw new NotFoundException(Constant.Message.NOT_FOUND_DATA_MESSAGE);
        }

        Product existProduct = productRepository.findByName(productRequest.getName().toLowerCase());
        if (Objects.nonNull(existProduct)) {
            throw new DataExistException(Constant.Message.EXIST_DATA_MESSAGE);
        }

        Product savedProduct = productRepository.save(mappingProduct(productRequest, category));
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(savedProduct)
                .build();

    }


    public Response<Object> getProductPerPage(RequestPage requestPage) {
        String name = filterMapping(requestPage.getProductName());
        String category = filterMapping(requestPage.getCategoryId());
        Pageable paging = PageRequest.of(requestPage.getPageNumber(), requestPage.getPageSize());
        Page<Product> productPage = productRepository.findPerPage(name, category, paging);

        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(Objects.nonNull(productPage) ? productPage.getContent() : new ArrayList<>())
                .totalPage(Objects.nonNull(productPage) ? productPage.getTotalPages() : null)
                .totalData(Objects.nonNull(productPage) ?  productPage.getTotalElements() : null)
                .pageNumber(requestPage.getPageNumber())
                .pageSize(requestPage.getPageSize())
                .build();
    }

    public Response<Object> getById(String id) {
        Product product = productRepository.findByProductId(id);
        if(Objects.isNull(product)) {
            throw new NotFoundException(Constant.Message.NOT_FOUND_DATA_MESSAGE);
        }

        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(product)
                .build();
    }

    @Transactional
    public Response<Object> update(String id, ProductRequest productRequest) {
        Category category = categoryRepository.findByCategoryId(productRequest.getCategoryId());
        if(Objects.isNull(category)) {
            throw new NotFoundException(Constant.Message.NOT_FOUND_DATA_MESSAGE);
        }

        Product product = productRepository.findByProductId(id);
        if (Objects.isNull(product)) {
            throw new NotFoundException(Constant.Message.NOT_FOUND_DATA_MESSAGE);
        }

        Product updatedProduct = Product.builder()
                .id(id)
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .description(productRequest.getDescription())
                .createdBy(product.getCreatedBy())
                .createdDate(product.getCreatedDate())
                .category(category)
                .modifiedBy(productRequest.getUsername())
                .modifiedDate(this.nowDate)
                .isDeleted(product.getIsDeleted())
                .build();
        productRepository.save(updatedProduct);

        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(updatedProduct)
                .build();

    }

    @Transactional
    public Response<Object> delete(String id, String username) {
        Product product = productRepository.findByProductId(id);
        if (Objects.isNull(product)) {
            throw new NotFoundException(Constant.Message.NOT_FOUND_DATA_MESSAGE);
        }

        productRepository.deleteById(id, username, this.nowDate);
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .build();
    }

    private String filterMapping(String value) {
        if(Objects.isNull(value) || ObjectUtils.isEmpty(value.trim())) {
            return "%%";
        } else {
            return "%".concat(value.toLowerCase()).concat("%");
        }
    }

    private Product mappingProduct(ProductRequest productRequest, Category category) {
        return Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .description(productRequest.getDescription())
                .category(category)
                .createdBy(productRequest.getUsername())
                .createdDate(this.nowDate)
                .isDeleted(false)
                .build();
    }
}
