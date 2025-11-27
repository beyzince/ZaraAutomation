package com.zara.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * Thread-safe WebDriver factory sınıfı
 * Her thread için ayrı WebDriver instance'ı yönetir
 */
public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Thread-safe WebDriver instance'ı döner
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            initializeDriver();
        }
        return driverThreadLocal.get();
    }

    /**
     * Chrome WebDriver'ı başlatır ve yapılandırır
     */
    private static void initializeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        
        WebDriver driver = new ChromeDriver(options);
        
        // Implicit wait ayarla (3 saniye - daha hızlı)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
        
        driverThreadLocal.set(driver);
    }

    /**
     * WebDriver'ı tamamen kapatır ve ThreadLocal'dan temizler
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }

    /**
     * WebDriver'ı kapatır (sadece mevcut pencereyi kapatır) ve ThreadLocal'dan temizler
     */
    public static void closeDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.close();
            driverThreadLocal.remove();
        }
    }
}

