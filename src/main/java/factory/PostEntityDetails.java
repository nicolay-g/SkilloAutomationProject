package factory;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PostEntityDetails {
    private final WebDriver webDriver;
    private final WebDriverWait wait;
    public final PostInfoContainer postInfoContainer;
    public final PostComments postComments;
    @FindBy (xpath = "//app-post-modal")
    private WebElement postEntityDetailsDialog;
    public PostEntityDetails(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(20));
        this.postInfoContainer = new PostInfoContainer(this.webDriver);
        this.postComments = new PostComments(this.webDriver);

        PageFactory.initElements(webDriver, this);
    }

    public void deletePost() {
        postInfoContainer.clickDeletePostBtn();
    }

    public void closePostDetailsDialog() {
        WebElement commentInput = postEntityDetailsDialog.findElement(By.
                xpath("//fieldset//input[@formcontrolname='content']"));
        wait.until(ExpectedConditions.visibilityOf(commentInput));
        commentInput.click();
        commentInput.sendKeys(Keys.ESCAPE);
    }
}
