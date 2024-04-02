package ui_tests;

import factory.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;

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
}
