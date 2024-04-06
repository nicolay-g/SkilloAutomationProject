package factory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PostEntityDetails {
    private final WebDriver webDriver;
    private final WebDriverWait wait;
    private PostInfoContainer postInfoContainer;
    private PostComments postComments;
    public PostEntityDetails(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        this.postInfoContainer = new PostInfoContainer(webDriver);
        this.postComments = new PostComments(webDriver);
    }

    public void deletePost() {
        postInfoContainer.clickDeletePostBtn();
    }

    public void addPostComment(String message) {
        postComments.addPostComment(message);
    }

    public String getPostLatestCommentText() {
        WebElement postLastComment = postComments.getLastPostComment();
        String commentContent = postLastComment.
                findElement(By.xpath("//div[@class='col-12 comment-content']")).getText();
        return commentContent;
    }

    public void closePostDetailsDialog() {
        //TODO: implement it by sending Esc key
    }
}
