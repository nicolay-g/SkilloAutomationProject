package ui_tests;

import factory.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
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
}
