package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ToastContainer {
    private final WebDriver webDriver;
    private final WebDriverWait wait;
    @FindBy(css = "#toast-container")
    private WebElement toastContainerElement;
    @FindBy(xpath = "//div[@role='alertdialog']")
    private WebElement alertDialog;
    public ToastContainer(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        PageFactory.initElements(webDriver, this);
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
