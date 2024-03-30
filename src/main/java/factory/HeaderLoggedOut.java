package factory;

import abstraction.Header;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HeaderLoggedOut extends Header {
    @FindBy(id="nav-link-login")
    private WebElement loginLink;

    public HeaderLoggedOut(WebDriver webDriver) {
        super(webDriver);

        PageFactory.initElements(this.webDriver, this);
    }

    public void clickOnLoginLink() {
        wait.until(ExpectedConditions.visibilityOf(loginLink));
        loginLink.click();
    }
}
