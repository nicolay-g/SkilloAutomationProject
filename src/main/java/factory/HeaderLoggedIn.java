package factory;

import abstraction.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class HeaderLoggedIn extends Header {
    @FindBy(id="nav-link-profile")
    private WebElement profileLink;
    @FindBy(id="nav-link-new-post")
    private WebElement newPostLink;
    @FindBy(xpath = "//i[@class='fas fa-sign-out-alt fa-lg']")
    private WebElement signOutButton;
    //private Search search;
    @FindBy(xpath = "//input[@id='search-bar']")
    private WebElement searchInput;
    @FindBy(xpath = "//i[@class='fas fa-search']")
    private WebElement searchButton;
    @FindBy(xpath = "//div[@class='dropdown-container']")
    private WebElement searchResultsContainer;

    public HeaderLoggedIn(WebDriver webDriver) {
        super(webDriver);
        //this.search = new Search(webDriver);

        PageFactory.initElements(this.webDriver, this);
    }

    public void clickOnProfileLink() {
        wait.until(ExpectedConditions.visibilityOf(profileLink));
        profileLink.click();
    }

    public void clickOnNewPostLink() {
        wait.until(ExpectedConditions.visibilityOf(newPostLink));
        newPostLink.click();
    }

    public void clickOnSignOutButton() {
        wait.until(ExpectedConditions.visibilityOf(signOutButton));
        signOutButton.click();
    }

    public void makeSearch(String textToSearch) {
        typeSearchText(textToSearch);
        clickSearchButton();
    }

    public List<WebElement> getSearchResultsCount(String textToSearch) {
        wait.until(ExpectedConditions.visibilityOf(searchResultsContainer));

        //This line is a kind of HACK that clicks on the search input to force getting all matching results!!
        //searchInput.click();

        List<WebElement> searchResultsElements = searchResultsContainer.
                findElements(By.xpath("//div[@class='dropdown-container']//a[contains(text(),'" +
                        textToSearch + "')]"));

        return searchResultsElements;
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
