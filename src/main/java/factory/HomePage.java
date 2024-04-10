package factory;

import abstraction.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class HomePage extends PageObject {
    private static final String HOME_PAGE_URL = "http://training.skillo-bg.com:4200/posts/all";

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    public boolean isPageLoaded() {
        return wait.until(ExpectedConditions.urlToBe(HOME_PAGE_URL));
    }
}
