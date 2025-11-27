package com.zara.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.stream.Collectors;


public class SearchPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(SearchPage.class);

    @FindBy(css = ".layout-header-action-search__content")
    private WebElement searchButton;

    @FindBy(css = "#search-home-form-combo-input")
    private WebElement searchInput;

    @FindBy(css = "a.product-grid-product__link")
    private List<WebElement> productCardList;

    public SearchPage(WebDriver driver) {
        super(driver);
        logger.debug("SearchPage oluşturuldu");
    }

    public void search(String keyword) {
        if (isElementDisplayed(searchButton)) {
            click(searchButton);
        }
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        sendKeys(searchInput, keyword);
        logger.info("Arama kutusuna '{}' yazıldı", keyword);
    }

    /**
     * Arama kutusunu temizler
     */
    public void clearSearch() {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        
        searchInput.sendKeys(Keys.CONTROL + "a");
        searchInput.sendKeys(Keys.DELETE);
        
        js.executeScript("arguments[0].value = '';", searchInput);
        
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        logger.info("Arama kutusu temizlendi");
    }

    /**
     * Arama kutusuna Enter tuşu gönderir
     */
    public void pressEnter() {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.sendKeys(Keys.RETURN);
        logger.info("Arama kutusuna Enter tuşu gönderildi");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.product-grid-product__link")));
    }

    /**
     * Arama sonuçlarından rastgele bir ürüne tıklar
     */
    public void clickRandomProduct() {
        logger.info("Ürün listesi bekleniyor");
        List<WebElement> productLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.cssSelector("a.product-grid-product__link")));
        
        productLinks = productLinks.stream()
            .filter(link -> link.isDisplayed() && link.isEnabled())
            .collect(Collectors.toList());
        
        if (productLinks.isEmpty()) {
            throw new RuntimeException("Ürün bulunamadı!");
        }
        
        int maxIndex = Math.min(2, productLinks.size());
        int randomIndex = (int) (Math.random() * maxIndex);
        WebElement selectedProduct = productLinks.get(randomIndex);
        
        logger.info("Rastgele ürün seçildi (index: {}/{})", randomIndex, productLinks.size());
        
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", selectedProduct);
        js.executeScript("arguments[0].click();", selectedProduct);
        logger.info(" Ürün linkine tıklandı");
        
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("h1[data-qa-qualifier='product-detail-info-name']")));
        logger.info(" Ürün sayfası yüklendi");
    }
}
