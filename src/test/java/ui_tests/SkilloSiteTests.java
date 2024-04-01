package ui_tests;

import factory.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SkilloSiteTests extends TestObject {

    @Test
    public void initialTest() {
        Assert.assertTrue(true);
    }

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
                {"n@g.con","n@g.con", "5631", "NikiGWasHere again"},
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
        Assert.assertTrue(!loginPage.isRememberMeCheckboxSelected(), "The Remember me checkbox is selected");
        Assert.assertTrue(loginPage.isPageLoaded(), "The login page is not opened");
    }

    @Test(dataProvider = "getUserDetails")
    public void searchTest(String username, String password, String userId, String userProfileName){
        WebDriver webDriver = super.getWebDriver();
        LoginPage loginPage = new LoginPage(webDriver);
        HeaderLoggedOut headerLoggedOut = new HeaderLoggedOut(webDriver);
        HeaderLoggedIn headerLoggedIn = new HeaderLoggedIn(webDriver);

        headerLoggedOut.clickOnLoginLink();
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.clickOnSignInButton();

        headerLoggedIn.makeSearch(userProfileName);
        Assert.assertTrue(headerLoggedIn.getSearchResultsCount(userProfileName) == 1, "Search results does not contain a single result!");

        headerLoggedIn.clickOnSignOutButton();
    }
}
