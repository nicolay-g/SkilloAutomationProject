package factory;

import abstraction.PageObject;
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

public class PostComments extends PageObject {
    @FindBy(xpath = "//fieldset//input[@formcontrolname='content']")
    private WebElement commentInput;
    @FindBy(xpath = "//div[@class='comment-list-container']")
    private WebElement commentsContainer;
    @FindBy(xpath = "//app-comment-list")
    private WebElement commentsList;
    @FindBy(xpath = "//div[@class='col-12 comment-content']")
    private List<WebElement> postComments;
    private final String commentLocator = "//app-comment";
    public PostComments(WebDriver webDriver) {
        super(webDriver);
    }

    public void addComment(String message) {
        wait.until(ExpectedConditions.elementToBeClickable(commentInput));
        commentInput.sendKeys(message);
        commentInput.sendKeys(Keys.ENTER);
    }

    public String getFirstComment() throws InterruptedException {
        waitForPostCommentsLoading();
        if (!postComments.isEmpty()) {
            return postComments.getFirst().getText();
        } else {
            return null;
        }
    }
    public String getLastComment() throws InterruptedException {
        waitForPostCommentsLoading();
        if (!postComments.isEmpty()) {
            return postComments.getLast().getText();
        } else {
            return null;
        }
    }

    public String getNthComment(int index) throws InterruptedException {
        waitForPostCommentsLoading();
        if ((!postComments.isEmpty()) && (index > -1) && (index < postComments.size())) {
            return postComments.get(index).getText();
        } else {
            return null;
        }
    }

    public int getPostCommentsCount() throws InterruptedException {
        waitForPostCommentsLoading();
        return postComments.size();
    }

    public void waitForPostCommentsLoading() throws InterruptedException {
        int retries = 1;
        do {
            Thread.sleep(500);
            List<WebElement> comments = commentsList.findElements(By.xpath(commentLocator));
            if (!comments.isEmpty()) {
                break;
            }
            retries++;
        } while (retries < 6);
    }
}
