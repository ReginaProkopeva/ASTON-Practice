import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MtsTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup(); 
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://mts.by"); // Открываем сайт
    }

    @Test
    @DisplayName("Проверка названия блока 'Онлайн пополнение без комиссии'")
    public void testBlockTitle() {
        WebElement blockTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h2[contains(text(), 'Онлайн пополнение без комиссии')]")));
        Assertions.assertNotNull(blockTitle, "Название блока не найдено");
    }

    @Test
    @DisplayName("Проверка наличия логотипов платежных систем")
    public void testPaymentLogos() {
        int logosCount = driver.findElements(By.className("pay_partners")).size(); 
        Assertions.assertTrue(logosCount > 0, "Логотипы платежных систем не найдены");
    }

    @Test
    @DisplayName("Проверка ссылки 'Подробнее о сервисе'")
    public void testServiceLink() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Подробнее о сервисе")));
        Assertions.assertNotNull(link, "Ссылка 'Подробнее о сервисе' не найдена");
        link.click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("service"), "Ссылка ведет не на ту страницу");
    }

    @Test
    @DisplayName("Заполнение формы и проверка кнопки 'Продолжить'")
    public void testContinueButton() {
        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("phone"))); 
        phoneInput.sendKeys("297777777");

        WebElement serviceDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("select_header"))); 
        serviceDropdown.sendKeys("Услуги связи");

        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Продолжить')]")));
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
        
    
