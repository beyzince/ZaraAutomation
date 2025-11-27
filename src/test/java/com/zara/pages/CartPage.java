package com.zara.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Zara sepet sayfası için Page Object Model sınıfı
 */
public class CartPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(CartPage.class);

    @FindBy(xpath = "//a[contains(@href, '/cart') or contains(@aria-label, 'Sepet')]")
    private WebElement cartIcon;

    @FindBy(css = ".zds-cart__empty")
    private WebElement emptyCartMessage;

    @FindBy(xpath = "//span[contains(text(),'SEPETİNİZ BOŞ')]")
    private WebElement emptyBasketText;
    
    @FindBy(css = ".zds-quantity-selector__value")
    private WebElement quantityValue;
    
    @FindBy(xpath = "(//*[name()='svg'][@class='zds-quantity-selector__icon'])[2]")
    private WebElement increaseQuantityButton;
    
    @FindBy(xpath = "(//*[name()='svg'][@class='zds-quantity-selector__icon'])[1]")
    private WebElement decreaseQuantityButton;
    
    @FindBy(css = "span.money-amount__main")
    private WebElement cartPriceElement;

    public CartPage(WebDriver driver) {
        super(driver);
        logger.debug("CartPage oluşturuldu");
    }

    public void openCart() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        js.executeScript("arguments[0].click();", cartIcon);
        logger.info("Sepet sayfası açıldı");
        Thread.sleep(5000);
    }

    public String getCartPrice() {
        wait.until(ExpectedConditions.visibilityOf(cartPriceElement));
        String price = getText(cartPriceElement);
        logger.info("Sepet fiyatı okundu: {}", price);
        return price != null ? price.trim() : "";
    }

    public void scrollToQuantity() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(increaseQuantityButton));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", increaseQuantityButton);
        Thread.sleep(1000);
    }
    
    public void increaseQuantity() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(increaseQuantityButton));
        js.executeScript("arguments[0].parentElement.click();", increaseQuantityButton);
        logger.info("Ürün adedi artırıldı");
        Thread.sleep(2000);
    }

    public String getQuantity() throws InterruptedException {
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOf(quantityValue));
        String quantity = getText(quantityValue);
        String numericQuantity = quantity != null ? quantity.replaceAll("[^0-9]", "") : "";
        logger.info("Ürün adedi okundu: {}", numericQuantity);
        return numericQuantity;
    }

    public void removeProduct() throws InterruptedException {
        scrollToQuantity();
        wait.until(ExpectedConditions.elementToBeClickable(decreaseQuantityButton));
        js.executeScript("arguments[0].parentElement.click();", decreaseQuantityButton);
        Thread.sleep(2000);
        js.executeScript("arguments[0].parentElement.click();", decreaseQuantityButton);
        Thread.sleep(3000);
        logger.info("Ürün sepetten silindi");
    }

   
    public boolean isCartEmpty() {
        return isElementDisplayed(emptyBasketText) || isElementDisplayed(emptyCartMessage);
    }
}

