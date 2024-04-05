package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverInfo;
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

    public PostInfoContainer(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));

        PageFactory.initElements(webDriver, this);
    }

    public void clickDeletePost() {
        wait.until(ExpectedConditions.visibilityOf(deletePost));
        deletePost.click();
    }
}
