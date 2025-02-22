import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Main {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://mts.by"); // Открываем сайт
    }

    @Test
    @DisplayName("Проверка названия блока 'Онлайн пополнение без комиссии'")
    public void testBlockTitle() {
        WebElement blockTitle = driver.findElement(By.xpath("//h2[contains(text(), 'Онлайн пополнение без комиссии')]"));
        Assertions.assertNotNull(blockTitle, "Название блока не найдено");
    }

    @Test
    @DisplayName("Проверка наличия логотипов платежных систем")
    public void testPaymentLogos() {
        int logosCount = driver.findElements(By.className("payment-logo")).size(); // Укажите реальный класс
        Assertions.assertTrue(logosCount > 0, "Логотипы платежных систем не найдены");
    }

    @Test
    @DisplayName("Проверка ссылки 'Подробнее о сервисе'")
    public void testServiceLink() {
        WebElement link = driver.findElement(By.linkText("Подробнее о сервисе"));
        Assertions.assertNotNull(link, "Ссылка 'Подробнее о сервисе' не найдена");
        link.click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("service"), "Ссылка ведет не на ту страницу");
    }

    @Test
    @DisplayName("Заполнение формы и проверка кнопки 'Продолжить'")
    public void testContinueButton() {
        WebElement phoneInput = driver.findElement(By.name("phone")); // Укажите реальный селектор
        phoneInput.sendKeys("297777777");

        WebElement serviceDropdown = driver.findElement(By.name("service")); // Укажите реальный селектор
        serviceDropdown.sendKeys("Услуги связи");

        WebElement continueButton = driver.findElement(By.xpath("//button[contains(text(), 'Продолжить')]"));
        continueButton.click();

        Assertions.assertTrue(driver.getCurrentUrl().contains("payment"), "Форма не отправилась корректно");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}