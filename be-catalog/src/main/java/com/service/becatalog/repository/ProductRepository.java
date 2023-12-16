package com.service.becatalog.repository;

import com.service.becatalog.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(value = "select * from product where lower(name) = ?1 and is_deleted = false", nativeQuery = true)
    Product findByName(String name);

    @Query(value = "select * from product where lower(name) like ?1 and is_deleted = false order by created_date asc",
            nativeQuery = true)
    Page<Product> findPerPage(String name, Pageable pageable);

    @Query(value="select * from product where id =?1 and is_deleted = false", nativeQuery = true)
    Product findByProductId(String id);

    @Transactional
    @Modifying
    @Query(value = "update product set is_deleted = true, modified_by = ?2, modified_date = ?3 where id = ?1", nativeQuery = true)
    void deleteById(String id, String modifiedBy, Date modifiedDate);
}
