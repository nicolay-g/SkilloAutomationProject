package ui_tests;

import factory.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SkilloSiteTests extends TestObject {
    @DataProvider(name="getUser")
    public Object[][] getUser(){
        return new Object[][]{
                {"n@g.con","n@g.con", "5631"},
                //{"n@g.co","n@g.co", "5632"},
        };
    }

    @DataProvider(name="getUserDetails")
    public Object[][] getUserDetails(){
        return new Object[][]{
                {"n@g.con","n@g.con", "NikiGWasHere"},
        };
    }

    @DataProvider(name="getUserAndPostData")
    public Object[][] getUserAndPostData(){
        File postPicture = new File(UPLOAD_DIR + "CtLeeQc1C.jpg");
        String postCaption = "Test post";
        return new Object[][]{
                {"n@g.con","n@g.con", "5631", postPicture, postCaption},
                //{"n@g.co","n@g.co", "5632"},
        };
    }

    @Test(dataProvider = "getUser")
    public void loginTest(String username, String password, String userId){
        WebDriver webDriver = super.getWebDriver();
        LoginPage loginPage = new LoginPage(webDriver);
        HeaderLoggedOut headerLoggedOut = new HeaderLoggedOut(webDriver);
        HeaderLoggedIn headerLoggedIn = new HeaderLoggedIn(webDriver);
        ProfilePage profilePage = new ProfilePage(webDriver);
        HomePage homePage = new HomePage(webDriver);

        headerLoggedOut.clickOnLoginLink();
        Assert.assertTrue(loginPage.isPageLoaded(), "The login page is not opened");

        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.selectRememberMeCheckbox();
        Assert.assertTrue(loginPage.isRememberMeCheckboxSelected(), "The Remember me checkbox is not selected");
        loginPage.clickOnSignInButton();
        Assert.assertTrue(homePage.isPageLoaded(), "The home page is not opened");

        headerLoggedIn.clickOnProfileLink();
        Assert.assertTrue(profilePage.isPageLoadedForUser(userId), "The profile page is not opened");

        headerLoggedIn.clickOnSignOutButton();

        //Check if on login page the username and password values are set and also the Remember me check box is selected
        Assert.assertTrue(loginPage.isPageLoaded(), "The login page is not opened");
        Assert.assertTrue(loginPage.isRememberMeCheckboxSelected(), "The Remember me checkbox is not selected");
        Assert.assertEquals(loginPage.getUsernameInputValue(), username, "Username mismatch");
        Assert.assertEquals(loginPage.getPasswordInputValue(), password, "Password mismatch");

        //Login again after signing out without entering credentials because of having the Remember me check box checked
        loginPage.clickOnSignInButton();
        Assert.assertTrue(homePage.isPageLoaded(), "The home page is not opened");

        headerLoggedIn.clickOnSignOutButton();
        loginPage.deSelectRememberMeCheckbox();
        Assert.assertFalse(loginPage.isRememberMeCheckboxSelected(), "The Remember me checkbox is selected");
        Assert.assertTrue(loginPage.isPageLoaded(), "The login page is not opened");
    }

    @Test(dataProvider = "getUserDetails")
    public void searchTest(String username, String password, String userProfileName){
        WebDriver webDriver = super.getWebDriver();
        LoginPage loginPage = new LoginPage(webDriver);
        HeaderLoggedOut headerLoggedOut = new HeaderLoggedOut(webDriver);
        HeaderLoggedIn headerLoggedIn = new HeaderLoggedIn(webDriver);

        headerLoggedOut.clickOnLoginLink();
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.clickOnSignInButton();

        headerLoggedIn.makeSearch(userProfileName);

        List<WebElement> searchResults = headerLoggedIn.getSearchResultsCount(userProfileName);

        validateSearchResults(userProfileName, searchResults);

        headerLoggedIn.clickOnSignOutButton();
    }

    private void validateSearchResults(String searchText, List<WebElement> searchResults) {
        SoftAssert softAssert = new SoftAssert();

        int searchResultsItems = searchResults.size();
        softAssert.assertTrue(searchResultsItems == 2,
                "Search results count is not 2! Actual results count is " + searchResultsItems);
        for (WebElement item : searchResults) {
            String itemText = item.getText();
            System.out.println(itemText);
            softAssert.assertTrue(itemText.contains(searchText), "Incorrect search results");
        }
        softAssert.assertAll();
    }

    @Test(dataProvider = "getUserAndPostData")
    public void createPostTest(String username, String password, String userId, File postPicture, String postCaption) {
        WebDriver webDriver = super.getWebDriver();
        LoginPage loginPage = new LoginPage(webDriver);
        HeaderLoggedOut headerLoggedOut = new HeaderLoggedOut(webDriver);
        HeaderLoggedIn headerLoggedIn = new HeaderLoggedIn(webDriver);
        ProfilePage profilePage = new ProfilePage(webDriver);
        HomePage homePage = new HomePage(webDriver);
        PostPage postPage = new PostPage(webDriver);

        headerLoggedOut.clickOnLoginLink();
        Assert.assertTrue(loginPage.isPageLoaded(), "The login page is not opened");

        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.clickOnSignInButton();
        Assert.assertTrue(homePage.isPageLoaded(), "The home page is not opened");

        headerLoggedIn.clickOnNewPostLink();
        Assert.assertTrue(postPage.isPageLoaded(), "The new post page is not opened");

        postPage.setPostCaption(postCaption);
        Assert.assertTrue(postPage.isPostCaptionSet(postCaption), "The post caption is not " + postCaption);

        postPage.uploadPicture(postPicture);
        String expectedPictureFileName = postPicture.getName();
        Assert.assertTrue(postPage.isFileUploaded(expectedPictureFileName), "Incorrect picture is uploaded");

        postPage.clickSubmitPostButton();
        Assert.assertTrue(profilePage.isPageLoadedForUser(userId), "Current page in not profile page for " + userId + " user");
    }

    @Test(dataProvider = "getUserDetails")
    public void updateUserProfileTest(String username, String password, String userProfileName) {
        WebDriver webDriver = super.getWebDriver();
        LoginPage loginPage = new LoginPage(webDriver);
        HeaderLoggedOut headerLoggedOut = new HeaderLoggedOut(webDriver);
        HeaderLoggedIn headerLoggedIn = new HeaderLoggedIn(webDriver);
        ProfilePage profilePage = new ProfilePage(webDriver);
        SoftAssert softAssert = new SoftAssert();

        headerLoggedOut.clickOnLoginLink();
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.clickOnSignInButton();

        headerLoggedIn.clickOnProfileLink();

        //Generate a random integer number in the range [0-1000] that will be used as part of the public info
        Random random = new Random();
        String expectedPublicInfo = "Public - " + String.valueOf(random.nextInt(1001));
        profilePage.modifyProfilePublicInfo(expectedPublicInfo);
        String actualProfilePublicInfo = profilePage.getProfilePublicInfo();

        //Validate that the profile details contain both the user profile name and the public info
        softAssert.assertTrue(actualProfilePublicInfo.contains(userProfileName),
                "Profile public info does not contain profile name: " + userProfileName);
        softAssert.assertTrue(actualProfilePublicInfo.contains(expectedPublicInfo),
                "Profile public info does not contain " + expectedPublicInfo);
        softAssert.assertAll();
    }

    @Test
    public void loginFailingTest(){
        WebDriver webDriver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        webDriver.get("http://training.skillo-bg.com:4200/posts/all");
        webDriver.manage().window().maximize();

        WebElement login = wait.until(ExpectedConditions.
                presenceOfElementLocated(new By.ByXPath("//*[@id='nav-link-login']")));

        String loginText = login.getText();
        Assert.assertEquals(loginText, "Login");
        login.click();

        WebElement loginFormTitle = wait.until(ExpectedConditions.
                presenceOfElementLocated(By.cssSelector(".h4.mb-4")));
        Assert.assertEquals(loginFormTitle.getText(), "Sign in");

        WebElement username = wait.until(ExpectedConditions.
                presenceOfElementLocated(By.id("defaultLoginFormUsername")));
        username.sendKeys("n@g.con");

        WebElement password = wait.until(ExpectedConditions.
                presenceOfElementLocated(By.id("defaultLoginFormPassword")));
        password.sendKeys("n@g1.con");

        WebElement loginButton = wait.until(ExpectedConditions.
                presenceOfElementLocated(By.id("sign-in-button")));
        loginButton.click();

        WebElement errorMessageBox = wait.until(ExpectedConditions.
                presenceOfElementLocated(By.xpath("//*[@id='toast-container']//*[@class='toast-message ng-star-inserted']")));

        Actions actionsForElements = new Actions(webDriver);
        actionsForElements.moveToElement(errorMessageBox).perform();

        String expectedText = "Invalid password";
        //" UsernameOrEmail cannot be empty ";
        String actualText = errorMessageBox.getText();
        Assert.assertEquals(actualText, expectedText, "The actual text is not matching the expected text");

        webDriver.quit();
    }
}
