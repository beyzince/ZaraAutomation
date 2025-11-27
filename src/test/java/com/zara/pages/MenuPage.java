package com.zara.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class MenuPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(MenuPage.class);

    @FindBy(xpath = "//*[@id='theme-app']/div/div/header/button")
    private WebElement menuButton;

    @FindBy(xpath = "//a[contains(text(), 'Erkek') or contains(@href, '/tr/tr/erkek')]")
    private WebElement erkekMenu;

    @FindBy(xpath = "(//a[@class='layout-categories-category-wrapper link'])[47]")
    private WebElement tumunuGorButton;

    public MenuPage(WebDriver driver) {
        super(driver);
        logger.debug("MenuPage oluşturuldu");
    }

    /**
     * Menü butonuna tıklar
     */
    public void clickMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(menuButton));
        js.executeScript("arguments[0].click();", menuButton);
        logger.info("Menü butonuna tıklandı");
    }

    /**
     * Erkek menüsüne tıklar
     */
    public void clickErkekMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(erkekMenu));
        click(erkekMenu);
        logger.info("Erkek menüsüne tıklandı");
    }

    /**
     * "Tümünü Gör" butonuna tıklar
     */
    public void clickTumunuGor() {
        wait.until(ExpectedConditions.elementToBeClickable(tumunuGorButton));
        click(tumunuGorButton);
        logger.info("Tümünü Gör butonuna tıklandı");
    }
}
