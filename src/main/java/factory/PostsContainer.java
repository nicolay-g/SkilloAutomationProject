package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PostsContainer {
    private final WebDriver webDriver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//div[@class='container']//app-post")
    private List<WebElement> posts;

    public PostsContainer(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));

        PageFactory.initElements(webDriver, this);
    }

    public WebElement getFirstPost() {
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

    public WebElement getNthPost(int index) {
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
