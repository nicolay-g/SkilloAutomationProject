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
import java.util.List;

public class PostComments {
    private final WebDriver webDriver;
    private final WebDriverWait wait;
    private final String commentsListLocator = "//app-comment-list";
    @FindBy(xpath = "//fieldset//input[@formcontrolname='content']")
    private WebElement commentInput;
    @FindBy(xpath = "//div[@class='comment-list-container']")
    private WebElement commentsContainer;
    @FindBy(xpath = "//div[@class='col-12 comment-content']")
    private List<WebElement> postComments;
    public PostComments(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        PageFactory.initElements(webDriver, this);
    }

    public void addComment(String message) {
        wait.until(ExpectedConditions.visibilityOf(commentInput));
        commentInput.sendKeys(message);
        commentInput.sendKeys(Keys.ENTER);
    }

    public String getFirstComment() {
        wait.until(ExpectedConditions.elementToBeClickable(commentsContainer));
        if (!postComments.isEmpty()) {
            return postComments.getFirst().getText();
        } else {
            return null;
        }
    }
    public String getLastComment() {
        /*
        wait.until(ExpectedConditions.elementToBeClickable(commentsContainer));


        } else {
            return null;
        }
        */

        wait.until(ExpectedConditions.elementToBeClickable(commentsContainer));
        boolean commentListExists = (commentsContainer.findElements(By.
                xpath(commentsListLocator))).size() == 1;
        if (commentListExists) {
            if (!postComments.isEmpty()) {
                return postComments.getLast().getText();
            }
        }
        return null;
    }

    public String getNthComment(int index) {
        wait.until(ExpectedConditions.elementToBeClickable(commentsContainer));
        if ((!postComments.isEmpty()) && (index > -1) && (index < postComments.size())) {
            return postComments.get(index).getText();
        } else {
            return null;
        }
    }

    public int getPostCommentsCount() {
        wait.until(ExpectedConditions.elementToBeClickable(commentsContainer));
        boolean commentListExists = (commentsContainer.findElements(By.
                xpath(commentsListLocator))).size() == 1;
        if (commentListExists) {
            return postComments.size();
        } else {
            return -1;
        }
    }
}
