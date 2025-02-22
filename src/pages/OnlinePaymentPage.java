package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class OnlinePaymentPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы
    private By blockTitle = By.xpath("//h2[contains(text(), 'Онлайн пополнение без комиссии')]");
    private By paymentLogos = By.className("pay-partners");
    private By serviceLink = By.linkText("Подробнее о сервисе");
    private By phoneInput = By.className("phone");
    private By serviceDropdown = By.className("select_header");
    private By continueButton = By.xpath("//button[contains(text(), 'Продолжить')]");

    // Локаторы для новых проверок
    private By errorMessages = By.className("input-wrapper");
    private By amountDisplay = By.xpath("//span[normalize-space()]");
    private By phoneDisplay = By.xpath("//span[contains(text(),':')]");
    private By cardFields = By.xpath("//div[@class='card ng-tns-c61-0']"); // Поля для ввода реквизитов карты

    public OnlinePaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Проверка наличия заголовка блока
    public boolean isBlockTitleDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(blockTitle)).isDisplayed();
    }

    // Проверка наличия логотипов платежных систем
    public boolean arePaymentLogosDisplayed() {
        return driver.findElements(paymentLogos).size() > 0;
    }

    // Переход по ссылке "Подробнее о сервисе"
    public void clickServiceLink() {
        wait.until(ExpectedConditions.elementToBeClickable(serviceLink)).click();
    }

    // Выбор варианта оплаты
    public void selectServiceOption(String service) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(serviceDropdown));
        dropdown.sendKeys(service);
    }

    // Ввод номера телефона
    public void fillPhoneNumber(String number) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInput));
        input.clear();
        input.sendKeys(number);
    }

    // Нажатие кнопки "Продолжить"
    public void clickContinueButton() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    // Получение текстов ошибок в незаполненных полях
    public List<WebElement> getErrorMessages() {
        return driver.findElements(errorMessages);
    }

    // Проверка корректности суммы
    public String getDisplayedAmount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(amountDisplay)).getText();
    }

    // Проверка номера телефона
    public String getDisplayedPhone() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(phoneDisplay)).getText();
    }

    // Проверка наличия полей для ввода реквизитов карты
    public boolean areCardFieldsDisplayed() {
        return driver.findElements(cardFields).size() > 0;
    }
}

