package factory;

import abstraction.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ToastContainer extends PageObject {
    @FindBy(css = "#toast-container")
    private WebElement toastContainerElement;
    @FindBy(xpath = "//div[@role='alertdialog']")
    private WebElement alertDialog;
    public ToastContainer(WebDriver webDriver) {
        super(webDriver);
    }

    public WebElement getToastContainerElement() {
        return toastContainerElement;
    }

    public String getAlertMessageText() {
        wait.until(ExpectedConditions.visibilityOf(alertDialog));

        Actions actions = new Actions(webDriver);
        actions.moveToElement(alertDialog).perform();
        return alertDialog.getText();
    }
}
