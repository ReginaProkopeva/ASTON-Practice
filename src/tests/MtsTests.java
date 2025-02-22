package tests;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.OnlinePaymentPage;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class MtsTests {
    private WebDriver driver;
    private OnlinePaymentPage onlinePaymentPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://mts.by"); // Открываем сайт

        onlinePaymentPage = new OnlinePaymentPage(driver);
    }

    @Test
    @DisplayName("Проверка названия блока 'Онлайн пополнение без комиссии'")
    public void testBlockTitle() {
        assertTrue(onlinePaymentPage.isBlockTitleDisplayed(), "Название блока не найдено");
    }

    @Test
    @DisplayName("Проверка наличия логотипов платежных систем")
    public void testPaymentLogos() {
        assertTrue(onlinePaymentPage.arePaymentLogosDisplayed(), "Логотипы платежных систем не найдены");
    }

    @Test
    @DisplayName("Проверка текста ошибок в незаполненных полях")
    public void testEmptyFieldErrors() {
        String[] services = {"Услуги связи", "Домашний интернет", "Рассрочка", "Задолженность"};

        for (String service : services) {
            onlinePaymentPage.selectServiceOption(service);
            onlinePaymentPage.clickContinueButton();

            List<WebElement> errors = onlinePaymentPage.getErrorMessages();
            assertFalse(errors.isEmpty(), "Ошибка в незаполненных полях не отображается для: " + service);
        }
    }

    @Test
    @DisplayName("Проверка данных после нажатия 'Продолжить' для 'Услуги связи'")
    public void testContinueButtonForService() {
        onlinePaymentPage.selectServiceOption("Услуги связи");
        onlinePaymentPage.fillPhoneNumber("297777777");
        onlinePaymentPage.clickContinueButton();

        // Проверка суммы
        assertEquals("10 BYN", onlinePaymentPage.getDisplayedAmount(), "Некорректная сумма");

        // Проверка номера телефона
        assertEquals("297777777", onlinePaymentPage.getDisplayedPhone(), "Номер телефона отображается некорректно");

        // Проверка наличия полей для реквизитов карты
        assertTrue(onlinePaymentPage.areCardFieldsDisplayed(), "Поля для ввода карты не отображаются");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

