
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public class Main {
    public static void main(String[] args) {

        WebDriver driver;

        @BeforeAll
        static void setupAll() {
            WebDriverManager.chromedriver().setup();
        }

        @BeforeEach
        void setup() {
            driver = new ChromeDriver();
        }

        @AfterEach
        void teardown() {
            driver.quit();
        }
    }
}


