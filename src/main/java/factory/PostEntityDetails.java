package factory;

import abstraction.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PostEntityDetails extends PageObject {
    public final PostInfoContainer postInfoContainer;
    public final PostComments postComments;
    @FindBy (xpath = "//app-post-modal")
    private WebElement postEntityDetailsDialog;
    public PostEntityDetails(WebDriver webDriver) {
        super(webDriver);

        this.postInfoContainer = new PostInfoContainer(this.webDriver);
        this.postComments = new PostComments(this.webDriver);
    }

    public void deletePost() throws InterruptedException {
        postComments.waitForPostCommentsLoading();
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
