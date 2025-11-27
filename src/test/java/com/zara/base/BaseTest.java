package com.zara.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

/**
 * Tüm test sınıflarının extend edeceği base class
 * WebDriver setup ve teardown işlemlerini yönetir
 */
public abstract class BaseTest {

    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected static WebDriver driver;
    protected static final String BASE_URL = "https://www.zara.com/tr/";
    private static boolean driverInitialized = false;

    /**
     * Her test öncesi WebDriver'ı kontrol eder ve gerekirse başlatır
     * Driver session'ı kapalıysa yeniden başlatır
     */
    @BeforeEach
    public void setUp() {
        boolean needsInitialization = false;
        
        try {
            // Driver var mı ve session açık mı kontrol et
            if (driver != null) {
                try {
                    driver.getCurrentUrl(); // Session'ın açık olup olmadığını kontrol et
                    logger.debug("Driver session açık, mevcut session kullanılıyor");
                } catch (Exception e) {
                    // Session kapalı
                    logger.warn("Driver session kapalı, yeniden başlatılıyor: {}", e.getMessage());
                    needsInitialization = true;
                }
            } else {
                needsInitialization = true;
            }
        } catch (Exception e) {
            needsInitialization = true;
        }
        
        // Driver yok veya session kapalı, yeniden başlat
        if (needsInitialization || !driverInitialized) {
            driver = DriverFactory.getDriver();
            driver.get(BASE_URL);
            driverInitialized = true;
            logger.info("WebDriver başlatıldı ve {} adresine gidildi", BASE_URL);
        }
    }

    /**
     * Her test sonrası çalışan teardown metodu
     * WebDriver'ı kapatmaz - tüm testler bitince @AfterAll'da kapatılır
     * Böylece testler arasında state korunur
     */
    @AfterEach
    public void tearDown() {
        // Driver'ı kapatma - testler sıralı çalışıyor ve state paylaşıyor
        // Driver @AfterAll'da kapatılacak
    }
    
    /**
     * Tüm testler bittikten sonra WebDriver'ı kapatır
     */
    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() {
        DriverFactory.quitDriver();
        logger.info("WebDriver kapatıldı (tüm testler tamamlandı)");
    }
}

