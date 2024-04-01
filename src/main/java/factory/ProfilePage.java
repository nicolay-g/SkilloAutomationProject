package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private WebDriver webDriver;

    private WebDriverWait wait;

    public static final String PROFILE_PAGE_BASE_URL = "http://training.skillo-bg.com:4200/users/";

    public ProfilePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
    }

    public boolean isPageLoadedForUser(String userId) {
        return wait.until(ExpectedConditions.urlToBe(PROFILE_PAGE_BASE_URL + userId));
    }
}
