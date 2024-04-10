package factory;

import abstraction.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PostsContainer extends PageObject {
    @FindBy(xpath = "//div[@class='container']//app-post")
    private List<WebElement> posts;

    public PostsContainer(WebDriver webDriver) {
        super(webDriver);
    }

    public void clickOnFirstPost() {
        getFirstPost().click();
    }

    public void clickOnLastPost() {
        getLastPost().click();
    }

    private WebElement getFirstPost() {
        if (!posts.isEmpty()) {
            return posts.getFirst();
        } else {
            return null;
        }
    }
    public WebElement getLastPost() {
        if (!posts.isEmpty()) {
            return posts.getLast();
        } else {
            return null;
        }
    }

    private WebElement getNthPost(int index) {
        if ((!posts.isEmpty()) && (index > -1) && (index < posts.size())) {
            return posts.get(index);
        } else {
            return null;
        }
    }

    public int getPostsCount() {
        return (!posts.isEmpty()) ? posts.size() : 0;
    }
}
