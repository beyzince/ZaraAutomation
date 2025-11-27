package com.zara.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FileWriter {

    private static final Logger logger = LogManager.getLogger(FileWriter.class);
    private static final String OUTPUT_FILE = "src/test/resources/productInfo.txt";

    
     // Ürün bilgilerini productInfo.txt dosyasına yazar
     
    public static void writeProductInfo(String productName, String productPrice) {
        try (java.io.FileWriter writer = new java.io.FileWriter(OUTPUT_FILE, true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("========================================\n");
            writer.write("Tarih: " + timestamp + "\n");
            writer.write("Ürün Adı: " + productName + "\n");
            writer.write("Ürün Fiyatı: " + productPrice + "\n");
            writer.write("========================================\n\n");
            logger.info("Ürün bilgileri dosyaya yazıldı: {}", OUTPUT_FILE);
        } catch (IOException e) {
            logger.error("Dosyaya yazma hatası: {}", e.getMessage());
            throw new RuntimeException("Dosyaya yazılamadı: " + OUTPUT_FILE, e);
        }
    }
}

