package com.service.bereport.service;

import com.service.bereport.constant.Constant;
import com.service.bereport.dao.ProductReportDao;
import com.service.bereport.entity.custommapping.ProductCustom;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class ProductReportService {

    @Autowired
    private ProductReportDao productReportDao;
    @Autowired
    private HttpServletResponse httpServletResponse;

    public void generateProductReport(String startDate, String endDate, String status) throws ParseException, IOException {
        Date start = Constant.DateFormatter.DB_DATE_FORMATTER.parse(startDate);
        Date end = Constant.DateFormatter.DB_DATE_FORMATTER.parse(endDate);
        List<ProductCustom> productList = productReportDao.getProductReportList(start, end, mappingStatus(status));

        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.setHeader("Content-Disposition", String.format("attachment; filename=tes.xls"));
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet 1");
        writeHeader(workbook, sheet);
        writeBody(workbook,sheet, productList);

        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


    private boolean mappingStatus(String status) {
        return "Active".equalsIgnoreCase(status) ? false : true;
    }

    private void writeBody(HSSFWorkbook workbook, Sheet sheet, List<ProductCustom> productList) throws ParseException {
        int rowCount = 0;
        int number = 1;
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        style.setFont(font);
        //border
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);


        if(!productList.isEmpty()) {
            for (ProductCustom data : productList) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;

                createCell(sheet, row, columnCount++, number, style);
                createCell(sheet, row, columnCount++, data.getName(), style);
                createCell(sheet, row, columnCount++, data.getPrice(), style);
                createCell(sheet, row, columnCount++, data.getQuantity(), style);
                createCell(sheet, row, columnCount++, data.getCategoryCode(), style);
                createCell(sheet, row, columnCount++, data.getCategoryName(), style);
                createCell(sheet, row, columnCount++, data.getIsDeleted(), style);
                createCell(sheet, row, columnCount++, data.getCreatedDate(), style);
                number++;
            }
        }
    }

    private void writeHeader(HSSFWorkbook workbook, Sheet sheet) {
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //border
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);

        createCell(sheet, row, 0, "NO", style);
        createCell(sheet, row, 1, "PRODUCT NAME", style);
        createCell(sheet, row, 2, "PRICE", style);
        createCell(sheet, row, 3, "QUANTITY", style);
        createCell(sheet, row, 4, "CATEGORY CODE", style);
        createCell(sheet, row, 5, "CATEGORY NAME", style);
        createCell(sheet, row, 6, "STATUS PRODUCT", style);
        createCell(sheet, row, 7, "INPUT DATE", style);
    }

    private void createCell(Sheet sheet, Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

}
