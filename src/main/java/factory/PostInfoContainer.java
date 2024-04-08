package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PostInfoContainer {
    private final WebDriver webDriver;
    private final WebDriverWait wait;
    @FindBy(xpath = "//label[@class='delete-ask']")
    private WebElement deletePost;
    @FindBy(xpath = "//button[text()='Yes']")
    private WebElement deletePostYesButton;
    @FindBy(xpath = "//button[text()='No']")
    private WebElement deletePostNoButton;

    public PostInfoContainer(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(60));

        PageFactory.initElements(webDriver, this);
    }

    public void clickDeletePostBtn() {
        wait.until(ExpectedConditions.visibilityOf(deletePost));
        deletePost.click();
        wait.until(ExpectedConditions.visibilityOf(deletePostYesButton));
        deletePostYesButton.click();
    }
}
