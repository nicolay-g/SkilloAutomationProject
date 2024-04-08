package factory;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private final WebDriver webDriver;

    private final WebDriverWait wait;

    public static final String PROFILE_PAGE_BASE_URL = "http://training.skillo-bg.com:4200/users/";

    @FindBy(xpath = "//h2")
    private WebElement userProfileName;
    @FindBy(xpath = "//i[contains(@class, 'fa-user-edit')]")
    private WebElement editUserButton;
    @FindBy(xpath = "//p")
    private WebElement publicInfo;
    @FindBy(css = "#toast-container")
    private WebElement toastContainerElement;
    @FindBy(xpath = "//app-spinner")
    private WebElement appSpinner;

    private final ToastContainer toastContainer;

    private final ModifyProfileDlg modifyProfileDlg;

    private final PostsContainer posts;


    public ProfilePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(60));
        this.modifyProfileDlg = new ModifyProfileDlg(this.webDriver);
        this.toastContainer = new ToastContainer(this.webDriver);
        this.posts = new PostsContainer(this.webDriver);

        PageFactory.initElements(this.webDriver, this);
    }

    public boolean isPageLoadedForUser(String userId) {
        return wait.until(ExpectedConditions.urlToBe(PROFILE_PAGE_BASE_URL + userId));
    }

    public void modifyProfilePublicInfo(String publicInfo) {
        wait.until(ExpectedConditions.visibilityOf(editUserButton));
        editUserButton.click();
        modifyProfileDlg.setPublicInfo(publicInfo);
        modifyProfileDlg.saveProfileBtn();
    }

    public String getProfilePublicInfo() {
        //Waits until the toast message disappears before getting the public info
        wait.until(ExpectedConditions.visibilityOf(toastContainer.getToastContainerElement()));
        wait.until(ExpectedConditions.invisibilityOf(toastContainer.getToastContainerElement()));

        wait.until(ExpectedConditions.visibilityOf(publicInfo));
        return publicInfo.getText();
    }

    public void scrollDownToBottom() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        int postsCountBeforeScrolling = posts.getPostsCount();
        int postsCountAfterScrolling;
        boolean allPostsLoaded;
        do {
            js.executeScript("window.scrollBy(0,2000)", "");
            Thread.sleep(5000);
            postsCountAfterScrolling = posts.getPostsCount();

            if (postsCountAfterScrolling > postsCountBeforeScrolling) {
                postsCountBeforeScrolling = postsCountAfterScrolling;
                allPostsLoaded = false;
            } else {
                allPostsLoaded = true;
            }
        } while (!allPostsLoaded);
    }
}
