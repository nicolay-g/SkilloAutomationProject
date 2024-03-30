package ui_tests;

import factory.LoginPage;
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
                {"n@g.com","n@g.com", "5540"},
        };
    }
    @Test(dataProvider = "getUser")
    public void loginTest(String username, String password, String userId){
        WebDriver webDriver = super.getWebDriver();
        LoginPage loginPage = new LoginPage(webDriver);

        loginPage.navigateTo();
        Assert.assertTrue(loginPage.isUrlLoaded(), "The login page is not opened");

        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.clickOnSignInButton();
    }
}
