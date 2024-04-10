package abstraction;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class Header extends PageObject {
    @FindBy(id="nav-link-home")
    protected WebElement homeLink;

    public Header(WebDriver webDriver) {
        super(webDriver);
    }

    public void clickOnHomeLink() {
        wait.until(ExpectedConditions.visibilityOf(homeLink));
        homeLink.click();
    }
}
