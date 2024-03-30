package ui_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestObject {
    private final WebDriver webDriver;
    public static final String TEST_RESOURCES_DIR = "src\\test\\resources\\";
    public static final String DOWNLOAD_DIR = TEST_RESOURCES_DIR.concat("download\\");
    @BeforeSuite
    protected final void setupTestSuite() throws IOException {

        WebDriverManager.chromedriver().setup();
    }

    public TestObject() {
        this.webDriver = new ChromeDriver(configChromeOptions());
        this.webDriver.manage().window().maximize();
    }

    @BeforeMethod
    protected final void setUpTest() {

    }
    @AfterMethod
    protected final void tearDownTest(ITestResult testResult) {

        quitDriver();
    }
    @AfterSuite
    public void deleteDownloadFiles() throws IOException {


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
}
