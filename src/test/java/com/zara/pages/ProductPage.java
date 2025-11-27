package com.zara.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;


public class ProductPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(ProductPage.class);

    @FindBy(css = "h1[data-qa-qualifier='product-detail-info-name']")
    private WebElement productHeader;
    
    @FindBy(css = "span.money-amount__main")
    private WebElement productPrice;
    
    @FindBy(css = "button[data-qa-action='add-to-cart']")
    private WebElement addToCartButton;
    
    @FindBy(xpath = "//div[contains(@class, 'size-selector-sizes-size__element') and not(contains(@class, 'disabled'))]")
    private List<WebElement> sizeElements;


    public ProductPage(WebDriver driver) {
        super(driver);
        logger.debug("ProductPage oluşturuldu");
    }

    public String getProductName() {
        wait.until(ExpectedConditions.visibilityOf(productHeader));
        String name = getText(productHeader);
        logger.info("Ürün adı okundu: {}", name);
        return name != null ? name.trim() : "";
    }

    public String getProductPrice() {
        wait.until(ExpectedConditions.visibilityOf(productPrice));
        String price = getText(productPrice);
        logger.info("Ürün fiyatı okundu: {}", price);
        return price != null ? price.trim() : "";
    }

    public void clickAddToCartButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        click(addToCartButton);
        logger.info("Sepete ekle butonuna tıklandı");
    }
    
    public void selectSize() {
        logger.info("Beden seçiliyor");
        if (sizeElements.isEmpty()) {
            throw new RuntimeException("Beden bulunamadı!");
        }
        click(sizeElements.get(0));
        logger.info("Beden seçildi");
    }
    
    
    public void closePopupIfExists() throws InterruptedException {
        try {
            Thread.sleep(3000);
            org.openqa.selenium.WebElement button = driver.findElement(org.openqa.selenium.By.xpath("//button[contains(., 'Hayır')]"));
            js.executeScript("arguments[0].click();", button);
            logger.info("Hayır teşekkürler butonuna tıklandı");
        } catch (Exception e) {
            logger.warn("Popup kapatılamadı: {}", e.getMessage());
        }
    }
    
   
    public void addToCart() throws InterruptedException {
        clickAddToCartButton();
        Thread.sleep(2000);
        selectSize();
        Thread.sleep(8000); // Sepete ekleme işleminin tamamlanması için bekle
        closePopupIfExists();
        
        logger.info("Ürün başarıyla sepete eklendi!");
    }
}
