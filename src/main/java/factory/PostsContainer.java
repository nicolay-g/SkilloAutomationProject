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

public class PostsContainer {
    private final WebDriver webDriver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//div[@class='container']//app-post")
    List<WebElement> posts;

    public PostsContainer(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));

        PageFactory.initElements(webDriver, this);
    }

    public WebElement getLastPost() {
        return posts.getLast();
    }

    public int getNumberOfPosts() {
        return (posts.size() > 0) ? posts.size() : 0;
    }
}
