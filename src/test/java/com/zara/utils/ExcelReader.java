package com.zara.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Excel dosyası okuma işlemlerini yöneten utility sınıfı
 */
public class ExcelReader {

    private static final Logger logger = LogManager.getLogger(ExcelReader.class);
    private static final String EXCEL_PATH = "src/test/resources/testdata.xlsx";

    
    public static String readCell(int columnIndex, int rowIndex) {
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                return "";
            }
            Cell cell = row.getCell(columnIndex);
            if (cell == null) {
                return "";
            }

            String cellValue = "";
            switch (cell.getCellType()) {
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = cell.getDateCellValue().toString();
                    } else {
                        cellValue = String.valueOf((long) cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                default:
                    cellValue = "";
            }
            
            logger.debug("Excel hücresi okundu: [Sütun: {}, Satır: {}] = {}", columnIndex, rowIndex, cellValue);
            return cellValue;
        } catch (IOException e) {
            logger.error("Excel dosyası okunamadı: {}", EXCEL_PATH, e);
            throw new RuntimeException("Excel dosyası okunamadı: " + EXCEL_PATH, e);
        }
    }
}

