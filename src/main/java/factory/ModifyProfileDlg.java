package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ModifyProfileDlg {
    private WebDriver webDriver;
    private WebDriverWait wait;
    @FindBy(xpath = "//textarea[@formcontrolname='publicInfo']")
    private WebElement publicInfoInput;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement profileSaveBtn;
    @FindBy(xpath = "//div[@class='container profile-edit-container']//form")
    private WebElement profileForm;

    public ModifyProfileDlg(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));

        PageFactory.initElements(this.webDriver, this);
    }

    public void setPublicInfo(String publicInfo) {
        wait.until(ExpectedConditions.visibilityOf(publicInfoInput));
        publicInfoInput.clear();
        publicInfoInput.sendKeys(publicInfo);
        wait.until(ExpectedConditions.attributeContains(profileForm,"class", "ng-valid"));
    }

    public void saveProfileBtn() {
        wait.until(ExpectedConditions.attributeContains(profileForm,"class", "ng-valid"));
        wait.until(ExpectedConditions.visibilityOf(profileSaveBtn));
        profileSaveBtn.click();
    }

}
