package com.service.bereport.entity.custommapping;

import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SqlResultSetMapping(name="ReportCustom",
    entities = {
            @EntityResult(entityClass = ProductCustom.class,
                fields = {
                    @FieldResult(name = "id", column = "id"),
                    @FieldResult(name = "name", column = "name"),
                    @FieldResult(name = "price", column = "price"),
                    @FieldResult(name = "quantity", column = "quantity"),
                    @FieldResult(name = "categoryCode", column = "category_code"),
                    @FieldResult(name = "categoryName", column = "category_name"),
                    @FieldResult(name = "isDeleted", column = "is_deleted"),
                    @FieldResult(name = "createdDate", column = "created_date")
                })
        })
public class ProductCustom {

    @Id
    private String id;
    private String name;
    private String price;
    private Integer quantity;
    private String categoryCode;
    private String categoryName;
    private String isDeleted;
    private String createdDate;

}
