package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private WebDriver webDriver;

    private WebDriverWait wait;

    public static final String PROFILE_PAGE_BASE_URL = "http://training.skillo-bg.com:4200/users/";

    @FindBy(xpath = "//h2")
    private WebElement userProfileName;
    @FindBy(xpath = "//i[contains(@class, 'fa-user-edit')]")
    private WebElement editUserButton;
    @FindBy(xpath = "//p")
    private WebElement publicInfo;

    private ModifyProfileDlg modifyProfileDlg;


    public ProfilePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        this.modifyProfileDlg = new ModifyProfileDlg(webDriver);

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
        wait.until(ExpectedConditions.visibilityOf(publicInfo));
        String publicInfoText = publicInfo.getText();
        return publicInfoText;
    }
}
