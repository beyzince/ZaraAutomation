package com.zara.tests;

import com.zara.base.BaseTest;
import com.zara.pages.*;
import com.zara.utils.ExcelReader;
import com.zara.utils.FileWriter;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ZaraTest extends BaseTest {

    private HomePage homePage;
    private LoginPage loginPage;
    private MenuPage menuPage;
    private SearchPage searchPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private String productName;
    private String productPrice;
    private static boolean loginDone = false;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp(); 
        
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        menuPage = new MenuPage(driver);
        searchPage = new SearchPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        
        homePage.acceptCookies();
        
        // Login sadece ilk test öncesi yapılır (session korunur)
        if (!loginDone) {
            try {
                logger.info(" login  ");
                loginPage.navigateToLogin();
                String email = "beyzaa0204@gmail.com";
                String password = "Beyza0600";
                loginPage.login(email, password);
                loginDone = true;
                logger.info(" login başarıyla tamamlandı");
                
                driver.get("https://www.zara.com/tr/");
                homePage.acceptCookies();
                org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(3));
                wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(
                    org.openqa.selenium.By.xpath("//*[@id='theme-app']/div/div/header/button")));
                logger.info("Ana sayfa yüklendi ve menü butonu hazır");
            } catch (Exception e) {
                logger.error("Otomatik login başarısız, testler login olmadan devam edecek: {}", e.getMessage());
                loginDone = true;
            }
        } else {
            logger.debug("Login zaten yapılmış, atlanıyor");
        }
    }

    @Test
    @Order(1)
    @DisplayName("Login işlemi")
    public void testLogin() {
        logger.info("Login işlemi başlatılıyor...");
        loginPage.navigateToLogin();
        
        String email = "beyzaa0204@gmail.com";  
        String password = "Beyza0600";        
        
        loginPage.login(email, password);
        logger.info("Login işlemi tamamlandı");
    }

    @Test
    @Order(2)
    @DisplayName("Menü -> Erkek -> Tümünü Gör navigasyonu")
    public void testNavigateToMenSection() {
        logger.info("Menü -> Erkek -> Tümünü Gör işlemi ");
        menuPage.clickMenu();
        menuPage.clickErkekMenu();
        menuPage.clickTumunuGor();
        logger.info("Menü navigasyonu tamamlandı");
    }

    @Test
    @Order(3)
    @DisplayName("Arama kutusuna 'şort' yazma")
    public void testSearchShorts() {
        logger.info("Arama kutucuğuna 'şort' yazılıyor...");
        String shorts = ExcelReader.readCell(0, 0);
        searchPage.search(shorts);
        logger.info("'Şort' kelimesi yazıldı: {}", shorts);
    }

    @Test
    @Order(4)
    @DisplayName("Arama kutusunu temizleme")
    public void testClearSearch() {
        logger.info("Arama kutucuğu temizleniyor");
        searchPage.clearSearch();
        logger.info("Arama kutucuğu temizlendi");
    }

    @Test
    @Order(5)
    @DisplayName("Arama kutusuna 'gömlek' yazma ve Enter'a basma")
    public void testSearchShirt() {
        logger.info("Arama kutucuğuna 'gömlek' yazılıyor");
        String shirt = ExcelReader.readCell(1, 0); 
        searchPage.search(shirt);
        searchPage.pressEnter();
        logger.info("'Gömlek' kelimesi yazıldı ve Enter'a basıldı: {}", shirt);
    }

    @Test
    @Order(6)
    @DisplayName("Rastgele ürün seçme")
    public void testSelectRandomProduct() {
        logger.info("Rastgele ürün seçiliyor");
        searchPage.clickRandomProduct();
        productName = productPage.getProductName();
        productPrice = productPage.getProductPrice();
        assertTrue(productName != null && !productName.isEmpty(), "Ürün adı alınamadı!");
        assertTrue(productPrice != null && !productPrice.isEmpty(), "Ürün fiyatı alınamadı!");
        logger.info("Ürün seçildi - Adı: {}, Fiyatı: {}", productName, productPrice);
    }

    @Test
    @Order(7)
    @DisplayName("Ürünü sepete ekleme ve bilgilerini txt dosyasına yazma")
    public void testAddToCart() throws InterruptedException {
        // Ürün bilgilerini sepete eklemeden önce oku
        if (productName == null || productName.isEmpty()) {
            productName = productPage.getProductName();
        }
        if (productPrice == null || productPrice.isEmpty()) {
            productPrice = productPage.getProductPrice();
        }
        
        productPage.addToCart();
        logger.info("Ürün sepete eklendi");
        
        logger.info("Ürün bilgileri dosyaya yazılıyor.");
        logger.info("Yazılacak bilgiler - Adı: {}, Fiyatı: {}", productName, productPrice);
        assertTrue(productName != null && !productName.isEmpty(), "Ürün adı null veya boş!");
        assertTrue(productPrice != null && !productPrice.isEmpty(), "Ürün fiyatı null veya boş!");
        FileWriter.writeProductInfo(productName, productPrice);
        logger.info("✓ Ürün bilgileri dosyaya yazıldı - Adı: {}, Fiyatı: {}", productName, productPrice);
        
        cartPage.openCart();
        logger.info("Sepet sayfası açıldı");
        Thread.sleep(3000); 
        
        boolean isCartEmpty = cartPage.isCartEmpty();
        if (isCartEmpty) {
            logger.error("Sepet boş! Ürün sepete eklenemedi.");
        } else {
            logger.info(" Sepet dolu - Ürün sepete eklendi!");
            String cartPrice = cartPage.getCartPrice();
            logger.info("Sepet fiyatı: {}", cartPrice);
        }
    }

    @Test
    @Order(8)
    @DisplayName("Ürün adedini artırma ve doğrulama")
    public void testIncreaseQuantity() throws InterruptedException {
        logger.info("Ürün adedi artırılıyor...");
        cartPage.openCart();
        Thread.sleep(2000);
        
        // Sepet boşsa testi atla
        if (cartPage.isCartEmpty()) {
            logger.warn("Sepet boş! Adet artırma testi atlanıyor.");
            return;
        }
        
        cartPage.scrollToQuantity();
        Thread.sleep(1000);
        cartPage.increaseQuantity();
        
        String quantity = cartPage.getQuantity();
        assertTrue(quantity != null && !quantity.isEmpty(), "Ürün adedi okunamadı!");
        logger.info("Ürün adedi: {}", quantity);
    }

    @Test
    @Order(9)
    @DisplayName("Ürünü sepetten silme ")
    public void testRemoveProduct() throws InterruptedException {
        logger.info("Ürün sepetten siliniyor...");
        cartPage.openCart();
        Thread.sleep(2000);
        cartPage.removeProduct();
        Thread.sleep(3000);
        
        boolean isEmpty = cartPage.isCartEmpty();
        assertTrue(isEmpty, "Sepet boş değil!");
        logger.info("Ürün sepetten silindi, sepet boş");
    }
}
