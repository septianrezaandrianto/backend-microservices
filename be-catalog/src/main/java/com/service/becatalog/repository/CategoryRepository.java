package com.service.becatalog.repository;

import com.service.becatalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query(value = "select * from category where lower(code) = ?1 and is_deleted = false", nativeQuery = true)
    Category findByCode(String code);

    @Query(value = "select * from category where is_deleted = false order by created_date asc", nativeQuery = true)
    List<Category> findCategoryList();

    @Query(value = "select * from category where id = ?1 and is_deleted = false", nativeQuery = true)
    Category findByCategoryId(String id);

    @Transactional
    @Modifying
    @Query(value = "update category set is_deleted = true, modified_by = ?2, modified_date = ?3 where id = ?1", nativeQuery = true)
    void deleteById(String id, String modifiedBy, Date modifiedDate);
}
