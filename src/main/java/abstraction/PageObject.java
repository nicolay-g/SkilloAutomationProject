package abstraction;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class PageObject {
    protected WebDriver webDriver;
    protected WebDriverWait wait;

    public PageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(60));

        PageFactory.initElements(this.webDriver, this);
    }
}
