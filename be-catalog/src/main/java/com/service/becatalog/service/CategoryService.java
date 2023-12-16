package com.service.becatalog.service;

import com.service.becatalog.constant.Constant;
import com.service.becatalog.dto.CategoryRequest;
import com.service.becatalog.dto.Response;
import com.service.becatalog.entity.Category;
import com.service.becatalog.exception.BadRequestCustomException;
import com.service.becatalog.exception.DataExistException;
import com.service.becatalog.exception.NotFoundException;
import com.service.becatalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    Date nowDate = new Date();

    @Transactional
    public Response<Object> create(CategoryRequest categoryRequest) {
        Category exsistCode = categoryRepository.findByCode(categoryRequest.getCode().toLowerCase());
        if (Objects.nonNull(exsistCode)) {
            throw new DataExistException(Constant.Message.EXIST_DATA_MESSAGE);
        }

        Category savedCategory = categoryRepository.save(mappingCategory(categoryRequest));
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(savedCategory)
                .build();
    }

    public Response<Object> getCategoryList() {
        List<Category> categoryList = categoryRepository.findCategoryList();
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(categoryList)
                .totalData((long)categoryList.size())
                .build();
    }

    public Response<Object> getById(String id) {
        Category category = categoryRepository.findByCategoryId(id);
        if(Objects.isNull(category)) {
            throw new NotFoundException(Constant.Message.NOT_FOUND_DATA_MESSAGE);
        }

        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(category)
                .build();
    }

    @Transactional
    public Response<Object> update(String id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findByCategoryId(id);
        if (Objects.isNull(category)) {
            throw new NotFoundException(Constant.Message.NOT_FOUND_DATA_MESSAGE);
        }

        if (!categoryRequest.getCode().equalsIgnoreCase(category.getCode())) {
            throw new BadRequestCustomException(Constant.Message.FORBIDDEN_REQUEST_MESSAGE.replace("{value}", "code"));
        }

        Category updatedCategory = Category.builder()
                .id(id)
                .code(category.getCode())
                .name(categoryRequest.getName())
                .createdBy(category.getCreatedBy())
                .createdDate(category.getCreatedDate())
                .modifiedBy(categoryRequest.getUsername())
                .modifiedDate(this.nowDate)
                .isDeleted(category.getIsDeleted())
                .build();

        categoryRepository.save(updatedCategory);
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(updatedCategory)
                .build();
    }

    @Transactional
    public Response<Object> delete(String id, String username) {
        Category category = categoryRepository.findByCategoryId(id);
        if (Objects.isNull(category)) {
            throw new NotFoundException(Constant.Message.NOT_FOUND_DATA_MESSAGE);
        }

        categoryRepository.deleteById(id, username, this.nowDate);
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .build();
    }


    private Category mappingCategory(CategoryRequest categoryRequest) {
        return Category.builder()
                .code(categoryRequest.getCode())
                .name(categoryRequest.getName())
                .createdBy(categoryRequest.getUsername())
                .createdDate(this.nowDate)
                .isDeleted(false)
                .build();
    }
}
