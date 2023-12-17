package com.service.becatalog.repository;

import com.service.becatalog.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findByCode_shouldReturnCategory() {
        Category category = createTestCategory();
        categoryRepository.save(category);

        Category result = categoryRepository.findByCode(category.getCode().toLowerCase());

        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getCode(), result.getCode());
        assertFalse(result.getIsDeleted());
    }

    @Test
    void findCategoryList_shouldReturnCategoryList() {
        Category category1 = createTestCategory();
        Category category2 = createTestCategory();
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        List<Category> categoryList = categoryRepository.findCategoryList();

        assertNotNull(categoryList);
        assertEquals(2, categoryList.size());
    }

    @Test
    void findByCategoryId_shouldReturnCategory() {
        Category category = createTestCategory();
        categoryRepository.save(category);

        Category result = categoryRepository.findByCategoryId(category.getId());

        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertFalse(result.getIsDeleted());
    }

    private Category createTestCategory() {
        return Category.builder()
                .id("kmzway87aa")
                .code("testCode")
                .name("TestCategory")
                .createdBy("admin")
                .createdDate(new Date())
                .isDeleted(false)
                .build();
    }
}
