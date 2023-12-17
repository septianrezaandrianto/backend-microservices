package com.service.bereport.dao;

import com.service.bereport.constant.Constant;
import com.service.bereport.entity.custommapping.ProductCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ProductReportDao {

    @Autowired
    private EntityManager entityManager;

    final static String QUERY_PRODUCT_REPORT = "select p.id, p.name, p.price, p.quantity, c.code as categoryCode, " +
            "c.name as categoryname, p.isDeleted, c.createdDate from Product p " +
            "join Category c on p.category.id = c.id " +
            "where p.createdDate >= :startDate and p.createdDate <= :endDate and p.isDeleted = :isDeleted " +
            "order by p.createdDate ASC";

    public List<ProductCustom> getProductReportList(Date startDate, Date endDate, boolean isDeleted) {
        TypedQuery<Object[]> query = entityManager.createQuery(QUERY_PRODUCT_REPORT, Object[].class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("isDeleted", isDeleted);
        List<Object[]> objList = query.getResultList();
        List<ProductCustom> resultList = new ArrayList<>();
        if(!objList.isEmpty()) {
            for(Object[] obj : objList) {
                ProductCustom pc = ProductCustom.builder()
                        .id((String) obj[0])
                        .name((String)obj[1])
                        .price(convertRupiahFormat((BigDecimal)obj[2]))
                        .quantity((Integer)obj[3])
                        .categoryCode((String)obj[4])
                        .categoryName((String)obj[5])
                        .isDeleted(convertStatus((Boolean)obj[6]))
                        .createdDate(Constant.DateFormatter.DISPLAY_DATE_FORMATTER.format((Date)obj[7]))
                        .build();
                resultList.add(pc);
            }
        }
        return resultList;
    }


    private String convertStatus(boolean isDelete) {
        return isDelete ? "Non Active" : "Active";
    }

    private String convertRupiahFormat(BigDecimal price) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(price);
    }
}
