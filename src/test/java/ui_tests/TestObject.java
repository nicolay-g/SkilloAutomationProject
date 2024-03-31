package ui_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestObject {
    public static final String HOME_URL = "http://training.skillo-bg.com:4200/posts/all";
    public static final String TEST_RESOURCES_DIR = "src\\test\\resources\\";
    public static final String DOWNLOAD_DIR = TEST_RESOURCES_DIR.concat("download\\");
    public static final String SCREENSHOTS_DIR = TEST_RESOURCES_DIR.concat("screenshots\\");
    public WebDriver getWebDriver() {
        return webDriver;
    }
    private WebDriver webDriver;
    @BeforeSuite
    protected final void setupTestSuite() throws IOException {
        cleanDirectory(SCREENSHOTS_DIR);
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    protected void setUpTest() {
        this.webDriver = new ChromeDriver(configChromeOptions());
        this.webDriver.manage().window().maximize();
        this.webDriver.get(HOME_URL);
    }
    @AfterMethod
    protected final void tearDownTest(ITestResult testResult) {
        takeScreenshot(testResult);
        quitDriver();
    }
    @AfterSuite
    public void deleteDownloadFiles() {
        quitDriver();
    }

    private void quitDriver() {
        if (this.webDriver != null){
            this.webDriver.quit();
        }
    }

    private ChromeOptions configChromeOptions(){
        //Create path and setting for download folder
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory",
                System.getProperty("user.dir").concat("\\").concat(DOWNLOAD_DIR));

        ChromeOptions chromeOptions = new ChromeOptions();
        //Set new default download folder
        chromeOptions.setExperimentalOption("prefs", prefs);
        //Force the download to be automatic
        chromeOptions.addArguments("disable-popup-blocking");

        return chromeOptions;
    }

    private void takeScreenshot(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            String testName = testResult.getName();
            try {
                TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
                File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, new File(SCREENSHOTS_DIR.concat(testName).concat(".png")));
            } catch (IOException exception) {
                System.out.println("Unable to create a screenshot file for test " + testName + ". Error message: " +
                        exception.getMessage());
            }
        }
    }

    private void cleanDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        Assert.assertTrue(directory.isDirectory(), "Invalid directory!");

        try {
            FileUtils.cleanDirectory(directory);
            System.out.printf("All files are deleted in Directory: %s%n", directoryPath);
        } catch (IOException exception) {
            System.out.printf("Unable to delete the files in Directory: %s%n", directoryPath);
        }
    }
}
