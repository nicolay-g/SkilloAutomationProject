package factory;

import abstraction.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PostInfoContainer extends PageObject {
    @FindBy(xpath = "//label[@class='delete-ask']")
    private WebElement deletePost;
    @FindBy(xpath = "//button[text()='Yes']")
    private WebElement deletePostYesButton;
    @FindBy(xpath = "//button[text()='No']")
    private WebElement deletePostNoButton;

    public PostInfoContainer(WebDriver webDriver) {
        super(webDriver);
    }

    public void clickDeletePostBtn() {
        wait.until(ExpectedConditions.visibilityOf(deletePost));
        deletePost.click();
        wait.until(ExpectedConditions.visibilityOf(deletePostYesButton));
        deletePostYesButton.click();
    }
}
