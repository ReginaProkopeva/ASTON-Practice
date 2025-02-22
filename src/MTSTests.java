public class MTSTests {
    @Test
    @DisplayName("Проверка названия блока 'Онлайн пополнение без комиссии'")
    public void testBlockTitle() {
        WebElement blockTitle = driver.findElement(By.xpath("//h2[contains(text(), 'Онлайн пополнение без комиссии')]"));
        Assertions.assertNotNull(blockTitle, "Название блока не найдено");
    }

    @Test
    @DisplayName("Проверка наличия логотипов платежных систем")
    public void testPaymentLogos() {
        int logosCount = driver.findElements(By.className("pay-partners")).size();
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
        WebElement phoneInput = driver.findElement(By.className("phone"));
        phoneInput.sendKeys("297777777");

        WebElement serviceDropdown = driver.findElement(By.className("select_header"));
        serviceDropdown.sendKeys("Услуги связи");

        WebElement continueButton = driver.findElement(By.xpath("//button[contains(text(), 'Продолжить')]"));
        continueButton.click();

        Assertions.assertTrue(driver.getCurrentUrl().contains("payment"), "Форма не отправилась корректно");
    }

}
