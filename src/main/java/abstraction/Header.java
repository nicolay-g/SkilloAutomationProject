package abstraction;

import factory.HeaderLoggedOut;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class Header {

    @FindBy(id="nav-link-home")
    protected WebElement homeLink;

    protected WebDriver webDriver;
    protected WebDriverWait wait;

    public Header(WebDriver webDriver) {
        this.webDriver = webDriver;
        wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
    }

    public void clickOnHomeLink() {
        wait.until(ExpectedConditions.visibilityOf(homeLink));
        homeLink.click();
    }
}
