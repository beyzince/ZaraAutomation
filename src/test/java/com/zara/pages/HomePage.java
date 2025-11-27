package com.zara.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(HomePage.class);

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement cookiesAcceptButton;

    public HomePage(WebDriver driver) {
        super(driver);
        logger.debug("HomePage oluşturuldu");
    }

   
    public void acceptCookies() {
        if (isElementDisplayed(cookiesAcceptButton)) {
            click(cookiesAcceptButton);
            logger.info("Çerez popup'ı kabul edildi");
        }
    }
}

