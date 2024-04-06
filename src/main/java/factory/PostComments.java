package factory;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PostComments {
    private final WebDriver webDriver;
    private final WebDriverWait wait;
    @FindBy(xpath = "//fieldset//input[@formcontrolname='content']")
    private WebElement commentInput;
    @FindBy(xpath = "//app-comment")
    private List<WebElement> postComments;
    public PostComments(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        PageFactory.initElements(webDriver, this);
    }

    public void addPostComment(String message) {
        wait.until(ExpectedConditions.visibilityOf(commentInput));
        commentInput.sendKeys(message);
        commentInput.sendKeys(Keys.ENTER);
    }

    public WebElement getFirstPostComment() {
        if (!postComments.isEmpty()) {
            return postComments.getFirst();
        } else {
            return null;
        }
    }
    public WebElement getLastPostComment() {
        if (!postComments.isEmpty()) {
            return postComments.getLast();
        } else {
            return null;
        }
    }

    public WebElement getNthPostComment(int index) {
        if ((!postComments.isEmpty()) && (index > -1) && (index < postComments.size())) {
            return postComments.get(index);
        } else {
            return null;
        }
    }

    public int getPostCommentsCount() {
        return (!postComments.isEmpty()) ? postComments.size() : 0;
    }
}
