package factory;

import abstraction.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;

public class PostPage extends PageObject {
    private static final String NEW_POST_PAGE_BASE_URL = "http://training.skillo-bg.com:4200/posts/create";
    @FindBy(xpath = "//input[contains(@class,'file')]")
    private WebElement uploadFileInput;
    @FindBy(xpath = "//input[@class='form-control input-lg']")
    private WebElement uploadedFileName;
    @FindBy(xpath = "//input[@name='caption']")
    private WebElement postCaptionInput;
    @FindBy(id = "create-post")
    private WebElement submitPostButton;
    //Won't be able to use the switch as it's not working on the site!
    @FindBy(xpath = "//input[@id='customSwitch2']")
    private WebElement postStatusSwitch;

    public PostPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void uploadPicture(File file) {
        wait.until(ExpectedConditions.invisibilityOf(uploadFileInput));
        uploadFileInput.sendKeys(file.getAbsolutePath());
    }

    public boolean isFileUploaded(String picFileName) {
        wait.until(ExpectedConditions.visibilityOf(uploadedFileName));
        return picFileName.equals(uploadedFileName.getAttribute("placeholder"));
    }

    public void setPostCaption(String text) {
        wait.until(ExpectedConditions.visibilityOf(postCaptionInput));
        postCaptionInput.sendKeys(text);
    }

    public void clickSubmitPostButton() {
        wait.until(ExpectedConditions.visibilityOf(submitPostButton));
        submitPostButton.click();
    }

    public boolean isPageLoaded() {
        return wait.until(ExpectedConditions.urlToBe(NEW_POST_PAGE_BASE_URL));
    }


    public boolean isPostCaptionSet(String expectedPostCaption) {
        wait.until(ExpectedConditions.visibilityOf(postCaptionInput));
        String actualPostCaption = postCaptionInput.getAttribute("value");
        return (expectedPostCaption.equals(actualPostCaption));
    }
}
