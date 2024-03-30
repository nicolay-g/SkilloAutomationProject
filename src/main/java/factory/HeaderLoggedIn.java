package factory;

import abstraction.Header;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HeaderLoggedIn extends Header {
    @FindBy(id="nav-link-profile")
    private WebElement profileLink;
    @FindBy(id="nav-link-new-post")
    private WebElement newPostLink;
    @FindBy(xpath = "//i[@class='fas fa-sign-out-alt fa-lg']")
    private WebElement signOutButton;


    //TODO: Define Search class which holds the search control and does operations on it
    //private Search search;

    public HeaderLoggedIn(WebDriver webDriver) {
        super(webDriver);

        PageFactory.initElements(this.webDriver, this);
    }

    public void clickOnProfileLink() {
        wait.until(ExpectedConditions.visibilityOf(profileLink));
        profileLink.click();
    }

    public void clickOnNewPostLink() {
        wait.until(ExpectedConditions.visibilityOf(newPostLink));
        newPostLink.click();
    }

    public void clickOnSignOutButton() {
        wait.until(ExpectedConditions.visibilityOf(signOutButton));
        signOutButton.click();
    }
}
