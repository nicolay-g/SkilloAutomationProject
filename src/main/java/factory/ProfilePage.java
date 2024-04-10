package factory;

import abstraction.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage extends PageObject {
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

    By spinnerIndicatorLocator = By.xpath("//app-spinner");

    private final ToastContainer toastContainer;

    private final ModifyProfileDlg modifyProfileDlg;

    private final PostsContainer posts;


    public ProfilePage(WebDriver webDriver) {
        super(webDriver);

        this.modifyProfileDlg = new ModifyProfileDlg(this.webDriver);
        this.toastContainer = new ToastContainer(this.webDriver);
        this.posts = new PostsContainer(this.webDriver);
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

        boolean isBottomOfPageReached = false;
        do {
            js.executeScript("window.scrollBy(0,2000)", "");

            boolean isIndicatorVisible = isIndicatorVisible();

            if (isIndicatorVisible) {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(spinnerIndicatorLocator));
            } else {
                isBottomOfPageReached = true;
            }
        } while (!isBottomOfPageReached);
    }

    private boolean isIndicatorVisible() {
        int retries = 1;
        boolean visibility = false;
        WebDriverWait w = new WebDriverWait(webDriver, Duration.ofMillis(100));
        do {
            try {
                    w.until(ExpectedConditions.visibilityOfElementLocated(spinnerIndicatorLocator));
                    visibility = true;
                    break;
                } catch (Exception e) {
                retries++;
            }

        } while (retries < 6);

        return visibility;
    }
}
