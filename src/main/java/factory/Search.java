package factory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Search {
    private WebDriver webDriver;
    private WebDriverWait wait;

    @FindBy(xpath = "//input[@id='search-bar']")
    private WebElement searchInput;
    @FindBy(xpath = "//i[@class='fas fa-search']")
    private WebElement searchButton;
    @FindBy(xpath = "//div[@class='dropdown-container']")
    private WebElement searchResultsContainer;


    public Search(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(300));

        PageFactory.initElements(this.webDriver, this);
    }

    public void makeSearch(String textToSearch) {
        typeSearchText(textToSearch);
        clickSearchButton();
    }

    public void validateSearchResults(String textToSearch) {
        wait.until(ExpectedConditions.visibilityOf(searchResultsContainer));

        List<WebElement> searchResultsElements = searchResultsContainer.
                findElements(By.xpath("//div[@class='dropdown-container']//a[contains(text(),'" +
                textToSearch + "')]"));

        for (WebElement resultElement : searchResultsElements) {
            // Perform actions on each child element
            System.out.println("Text of child element: " + resultElement.getText());
        }
    }

    private void typeSearchText(String text) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.sendKeys(text);
    }

    private void clickSearchButton() {
        wait.until(ExpectedConditions.visibilityOf(searchButton));
        searchButton.click();
    }
}
