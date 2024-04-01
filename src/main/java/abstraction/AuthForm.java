package abstraction;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class AuthForm {

    @FindBy(id="sign-in-button")
    protected WebElement signInButton;

    public abstract boolean isPageLoaded();
}
