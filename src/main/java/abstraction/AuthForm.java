package abstraction;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class AuthForm extends PageObject {

    @FindBy(id="sign-in-button")
    protected WebElement signInButton;

    public AuthForm(WebDriver webDriver) {
        super(webDriver);
    }

    public abstract boolean isPageLoaded();
}
