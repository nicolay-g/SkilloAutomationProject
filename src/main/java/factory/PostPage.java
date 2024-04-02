package factory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class PostPage {
    private WebDriver webDriver;
    private WebDriverWait wait;
    @FindBy(xpath = "//input[contains(@class,'file')]")
    private WebElement uploadFileInput;
    @FindBy(xpath = "//input[@class='form-control input-lg']")
    private WebElement uploadedFileName;
    @FindBy(xpath = "//input[@name='caption']")
    private WebElement postCaptionInput;
    @FindBy(xpath = "//button[@id='create-post']")
    private WebElement submitPostButton;
    @FindBy(xpath = "//input[@id='customSwitch2']")
    private WebElement postStatusSwitch;

    public PostPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        PageFactory.initElements(webDriver, this);
    }

    public void uploadPicture(File file) {
        wait.until(ExpectedConditions.invisibilityOf(uploadFileInput));
        uploadFileInput.sendKeys(file.getAbsolutePath());
    }

    public boolean isFileUploaded(String picFileName) {
        wait.until(ExpectedConditions.visibilityOf(uploadedFileName));
        return (picFileName.equals(uploadedFileName.getAttribute("placeholder"))) ? true : false;
    }

    public void setPostCaption(String text) {
        wait.until(ExpectedConditions.visibilityOf(postCaptionInput));
        postCaptionInput.sendKeys(text);
    }

    public void clickSubmitPostButton() {
        wait.until(ExpectedConditions.visibilityOf(submitPostButton));
        submitPostButton.click();
    }
}
