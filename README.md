# Zara Selenium Web Otomasyon Projesi

Spring Boot + Selenium + JUnit + Log4J kullanılarak geliştirilmiş Zara.com test otomasyon projesi.

## Teknolojiler

- **Java 17**
- **Spring Boot 3.3.4**
- **Maven**
- **Selenium WebDriver 4.27.0**
- **JUnit 5**
- **Log4J 2**

## Proje Yapısı

```
src/
├── main/java/com/zara/
│   └── ZaraAutomationApplication.java
└── test/
    ├── java/com/zara/
    │   ├── pages/          # Page Object Pattern sınıfları
    │   ├── tests/          # Test senaryoları
    │   ├── utils/          # Excel okuma, dosya yazma
    │   └── config/         # WebDriver konfigürasyonu
    └── resources/
        ├── testdata.xlsx   # Test verileri (şort, gömlek)
        └── log4j2.xml      # Log konfigürasyonu
```

## Gereksinimler

1. **Java 17+**
2. **Maven 3.9+**
3. **Chrome Browser** (ChromeDriver otomatik indirilir)
4. **Excel Dosyası**: `src/test/resources/testdata.xlsx`
   - 1. sütun, 1. satır: "şort"
   - 2. sütun, 1. satır: "gömlek"

## Test Senaryosu

1. www.zara.com/tr/ sitesi açılır
2. Login olunur
3. Menü -> Erkek -> Tümünü Gör
4. Arama kutucuğuna "şort" yazılır (Excel'den)
5. Arama temizlenir
6. Arama kutucuğuna "gömlek" yazılır ve Enter'a basılır (Excel'den)
7. Rastgele bir ürün seçilir
8. Ürün bilgileri TXT dosyasına yazılır
9. Ürün sepete eklenir
10. Fiyat karşılaştırması yapılır
11. Adet artırılır ve 2 olduğu doğrulanır
12. Ürün silinir ve sepet boş olduğu kontrol edilir

## Çalıştırma

```bash
# Tüm testleri çalıştır
mvn test

# Belirli bir test çalıştır
mvn test -Dtest=ZaraTest
```

## Notlar

- **Login bilgileri**: `ZaraTest.java` dosyasındaki `testLogin()` metoduna email ve password eklenmelidir
- **Excel dosyası**: `src/test/resources/testdata.xlsx` dosyası oluşturulmalı ve test verileri eklenmelidir
- **ChromeDriver**: Selenium Manager otomatik olarak uygun ChromeDriver'ı indirir

## Çıktılar

- **Log dosyası**: `logs/zara-automation.log`
- **Ürün bilgileri**: `src/test/resources/product_info.txt`

