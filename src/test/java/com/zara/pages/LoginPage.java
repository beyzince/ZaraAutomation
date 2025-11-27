package com.zara.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class LoginPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    @FindBy(name = "username")
    private WebElement emailInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[normalize-space()='Oturum aç']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
        logger.debug("LoginPage oluşturuldu");
    }

    /**
     * Login sayfasına yönlendir
     */
    public void navigateToLogin() {
        WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'GİRİŞ YAP')]")));
        click(loginLink);
        logger.info("GİRİŞ YAP linkine tıklandı");
    }

    
    public void login(String email, String password) {
        sendKeys(emailInput, email);
        logger.info("Email girildi: {}", email);
        
        sendKeys(passwordInput, password);
        logger.info("Şifre girildi");
        
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        click(loginButton);
        logger.info("Login butonuna tıklandı");
    }
}
