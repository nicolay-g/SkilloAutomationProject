package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private static final String HOME_PAGE_URL = "http://training.skillo-bg.com:4200/posts/all";

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
    }

    public boolean isPageLoaded() {
        return wait.until(ExpectedConditions.urlToBe(HOME_PAGE_URL));
    }
}
