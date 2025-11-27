package com.zara.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelFileCreator {

    public static void createTestDataFile() {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream("src/test/resources/testdata.xlsx")) {

            Sheet sheet = workbook.createSheet("TestData");
            
            Row row = sheet.createRow(0);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue("şort");
            
            Cell cell2 = row.createCell(1);
            cell2.setCellValue("gömlek");
            
            workbook.write(fos);
            System.out.println("Excel dosyası oluşturuldu: src/test/resources/testdata.xlsx");
        } catch (IOException e) {
            throw new RuntimeException("Excel dosyası oluşturulamadı", e);
        }
    }

    public static void main(String[] args) {
        createTestDataFile();
    }
}

