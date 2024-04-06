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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostEntityDetails {
    private final WebDriver webDriver;
    private final WebDriverWait wait;
    public final PostInfoContainer postInfoContainer;
    public final PostComments postComments;
    @FindBy (xpath = "//app-post-modal")
    private WebElement postEntityDetailsDialog;
    public PostEntityDetails(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        this.postInfoContainer = new PostInfoContainer(this.webDriver);
        this.postComments = new PostComments(this.webDriver);

        PageFactory.initElements(webDriver, this);
    }

    public void deletePost() {
        postInfoContainer.clickDeletePostBtn();
    }

    /*
    public void addPostComment(String message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));

        postComments.addPostComment(message);
    }

    public String getPostLastCommentText() {
        WebElement postLastComment = postComments.getPostLastComment();
        String commentContent = postLastComment.getText();
                //findElement(By.xpath("//div[@class='col-12 comment-content']")).getText();
        return commentContent;
    }

    public String getPostFirstCommentText() {
        WebElement postFirstComment = postComments.getPostFirstComment();
        String commentContent = postFirstComment.
                findElement(By.xpath("//div[@class='col-12 comment-content']")).getText();
        return commentContent;
    }
*/
    public void closePostDetailsDialog() {
        WebElement commentInput = postEntityDetailsDialog.findElement(By.
                xpath("//fieldset//input[@formcontrolname='content']"));
        wait.until(ExpectedConditions.visibilityOf(commentInput));
        commentInput.click();
        commentInput.sendKeys(Keys.ESCAPE);
    }
}
