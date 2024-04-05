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
    WebElement deletePost;
    @FindBy(xpath = "//button[text()='Yes']")
    WebElement deletePostYesButton;
    @FindBy(xpath = "//button[text()='No']")
    WebElement deletePostNoButton;

    public PostInfoContainer(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));

        PageFactory.initElements(webDriver, this);
    }

    public void deletePost() {
        ToastContainer toastContainer = new ToastContainer(this.webDriver);

        wait.until(ExpectedConditions.visibilityOf(deletePost));
        deletePost.click();
        wait.until(ExpectedConditions.visibilityOf(deletePostYesButton));
        deletePostYesButton.click();

        //TODO: Waiting the page to be fully loaded - probably not necessary code!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        wait.until(ExpectedConditions.visibilityOf(toastContainer.getToastContainerElement()));
        wait.until(ExpectedConditions.invisibilityOf(toastContainer.getToastContainerElement()));
    }
}
