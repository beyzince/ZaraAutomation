package com.zara.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public abstract class BasePage {

    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

   
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    protected void click(WebElement element) {
        try {
            wait.until(d -> {
                try {
                    return element.isDisplayed() && element.isEnabled();
                } catch (Exception e) {
                    logger.warn("Element kontrol edilirken hata: {}", e.getMessage());
                    return false;
                }
            });
            element.click();
        } catch (org.openqa.selenium.NoSuchSessionException e) {
            logger.error("WebDriver session kapalı: {}", e.getMessage());
            throw new RuntimeException("WebDriver session kapalı, test devam edemiyor", e);
        }
    }

    protected void sendKeys(WebElement element, String text) {
        try {
            wait.until(d -> {
                try {
                    return element.isDisplayed() && element.isEnabled();
                } catch (Exception e) {
                    return false;
                }
            });
            element.clear();
            element.sendKeys(text);
        } catch (org.openqa.selenium.NoSuchSessionException e) {
            logger.error("WebDriver session kapalı: {}", e.getMessage());
            throw new RuntimeException("WebDriver session kapalı, test devam edemiyor", e);
        }
    }

    protected String getText(WebElement element) {
        try {
            wait.until(d -> {
                try {
                    return element.isDisplayed();
                } catch (Exception e) {
                    return false;
                }
            });
            return element.getText();
        } catch (org.openqa.selenium.NoSuchSessionException e) {
            logger.error("WebDriver session kapalı: {}", e.getMessage());
            throw new RuntimeException("WebDriver session kapalı, test devam edemiyor", e);
        }
    }

    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

