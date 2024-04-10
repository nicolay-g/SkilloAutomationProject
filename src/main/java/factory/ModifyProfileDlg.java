package factory;

import abstraction.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class ModifyProfileDlg extends PageObject {
    @FindBy(xpath = "//textarea[@formcontrolname='publicInfo']")
    private WebElement publicInfoInput;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement profileSaveBtn;
    @FindBy(xpath = "//div[@class='container profile-edit-container']//form")
    private WebElement profileForm;

    public ModifyProfileDlg(WebDriver webDriver) {
        super(webDriver);
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
