package BasePage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import util.FBConstants;
import util.Xls_Reader;

import static java.lang.System.getProperty;

public class TestBase {

    public static final String run_env = "MIRO";
    public static Properties config = null;

    public WebDriver driver;
    public static Logger log = Logger.getLogger("");

    public static boolean loggedIn = false;
    public static Xls_Reader datatable = null;
    public ExtentReports extentReports = null;
    public ExtentTest extentTest = null;
    public static final String LOG_PATH = FBConstants.REPORTS_PATH + "WEB\\";
    private static final String FileUtils = null;

    @BeforeSuite
    public void initialize() throws IOException, InterruptedException {

        PropertyConfigurator.configure(System.getProperty("user.dir") + "\\src\\log\\log4j.properties");

        config = new Properties();
        FileInputStream fp = new FileInputStream(System.getProperty("user.dir") + "\\src\\config\\config.properties");
        config.load(fp);

        datatable = new Xls_Reader(System.getProperty("user.dir") + "\\src\\data\\" + run_env + "_Data.xlsx");
        log.info("Initializing all elements");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException {
        extentReports.endTest(extentTest);
        extentReports.flush();
        extentReports.close();
    }

    @AfterTest
    public void quit(){
        driver.quit();
    }

    public void invoke() throws IOException, InterruptedException {
        String platform = getProperty("os.name");

        if (platform.toLowerCase().contains("win")) {
            windowsDriverSetup();
        } else if (platform.toLowerCase().contains("mac")) {
            macDriverSetup();
        } else if (platform.toLowerCase().contains("lin")) {
            linuxDriverSetup();
        }
        driver.get(config.getProperty("baseURL"));
    }

    public void reportFailure(String failureMessage) {
        extentTest.log(LogStatus.FAIL, failureMessage);
        Assert.fail(failureMessage);
    }

    public void startreport(String s) {
        Date d = new Date();
        String fileName = d.toString().replace(":", "_").replace(" ", "_") + ".html";
        String reportPath = LOG_PATH + fileName;
        extentReports = new ExtentReports(reportPath, true, DisplayOrder.NEWEST_FIRST);
        extentReports.loadConfig(new File(System.getProperty("user.dir") + "\\ReportsConfig.xml"));
        extentReports.addSystemInfo("Selenium Version", "3.53.0").addSystemInfo("Environment", run_env);

        extentTest = extentReports.startTest(s);
    }

    public void takeScreenShot() throws IOException {

        Date d = new Date();
        String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
        String filePath = FBConstants.REPORTS_PATH + "\\screenshots\\" + screenshotFile;
        // store screenshot in that file
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(scrFile, new File(filePath));
        extentTest.log(LogStatus.INFO, extentTest.addScreenCapture(filePath));
    }

    private void windowsDriverSetup() {

        String browser = config.getProperty("browserType");

        Map<String, Object> prefsMap = new HashMap<String, Object>();
        prefsMap.put("profile.default_content_settings.popups", 0);
        //prefsMap.put("download.default_directory", loc);
        prefsMap.put("download.prompt_for_download", false);
        prefsMap.put("safebrowsing.enabled", "false");
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions win_Options = new ChromeOptions();
            win_Options.setExperimentalOption("prefs", prefsMap);
            win_Options.addArguments("window-size=1920x1080");
            win_Options.addArguments("--start-maximized");
            win_Options.addArguments("--disable-gpu");
            win_Options.addArguments("--disable-extensions");
            win_Options.addArguments("--no-sandbox");
            win_Options.addArguments("acceptSslCerts=true");
            win_Options.addArguments("acceptInsecureCerts=true");
            win_Options.addArguments("--allow-running-insecure-content");
            //win_Options.addArguments("--headless");
            log.info("Running on Windows Operating System with " + browser + " browser");
            driver = WebDriverManager.chromedriver().capabilities(win_Options).create();
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions win_Options = new FirefoxOptions();
            //win_Options.setExperimentalOption("prefs", prefsMap);
            win_Options.addArguments("window-size=1920x1080");
            win_Options.addArguments("--start-maximized");
            win_Options.addArguments("--disable-gpu");
            win_Options.addArguments("--disable-extensions");
            win_Options.addArguments("--no-sandbox");
            win_Options.addArguments("acceptSslCerts=true");
            win_Options.addArguments("acceptInsecureCerts=true");
            win_Options.addArguments("--allow-running-insecure-content");
            win_Options.addArguments("--headless");
            log.info("Running on Windows Operating System with " + browser + " browser");
            driver = WebDriverManager.firefoxdriver().capabilities(win_Options).create();

        }
    }

    private void macDriverSetup() {

        String browser = config.getProperty("browserType");
        Map<String, Object> prefsMap = new HashMap<String, Object>();
        prefsMap.put("profile.default_content_settings.popups", 0);
        //prefsMap.put("download.default_directory", loc);
        prefsMap.put("download.prompt_for_download", false);
        prefsMap.put("safebrowsing.enabled", "false");
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions mac_options = new ChromeOptions();
            mac_options.setExperimentalOption("prefs", prefsMap);
            mac_options.addArguments("test-type");
            mac_options.addArguments("--kiosk");
            //mac_options.addArguments("--headless");
            mac_options.addArguments("acceptSslCerts=true");
            mac_options.addArguments("acceptInsecureCerts=true");
            System.out.println("Running on Mac Operating System with " + browser + " browser");
            driver = WebDriverManager.chromedriver().browserVersion("85.0.4103").capabilities(mac_options).create();
            //driver = WebDriverManager.chromedriver().capabilities(mac_options).create();
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions mac_options = new FirefoxOptions();
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("profile.default_content_settings.popups", 0);
            profile.setPreference("browser.download.folderList", 2);
            //profile.setPreference("browser.download.dir", loc);
            profile.setPreference("browser.download.viewableInternally.enabledTypes", "");
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/msword;application/ms-doc;application/doc;application/pdf;text/plain;application/text;text/xml;application/xml");
            mac_options.setProfile(profile);
            mac_options.addArguments("test-type");
            //mac_options.addArguments("window-size=1920x1080");
            mac_options.setHeadless(false);
            System.out.println("Running on Mac Operating System with " + browser + " browser");
            driver = WebDriverManager.firefoxdriver().capabilities(mac_options).create();
            driver.manage().window().fullscreen();
        }
    }


    private void linuxDriverSetup() {

        String browser = config.getProperty("browserType");

        Map<String, Object> prefsMap = new HashMap<String, Object>();
        prefsMap.put("profile.default_content_settings.popups", 0);
        //prefsMap.put("download.default_directory", loc);
        prefsMap.put("download.prompt_for_download", false);
        prefsMap.put("safebrowsing.enabled", "false");
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions lin_options = new ChromeOptions();
            lin_options.setExperimentalOption("prefs", prefsMap);
            lin_options.addArguments("test-type");
            lin_options.addArguments("window-size=1920x1080");
            lin_options.addArguments("--disable-gpu");
            lin_options.addArguments("--disable-extensions");
            lin_options.addArguments("--no-sandbox");
            lin_options.addArguments("acceptSslCerts=true");
            lin_options.addArguments("acceptInsecureCerts=true");
            lin_options.addArguments("--allow-running-insecure-content");
            lin_options.addArguments("--disable-dev-shm-usage");
            lin_options.addArguments("--headless");
            System.out.println("Running on Mac Operating System with " + browser + " browser");
            driver = WebDriverManager.chromedriver().capabilities(lin_options).create();
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions lin_options = new FirefoxOptions();
            //lin_options.setExperimentalOption("prefs", prefsMap);
            lin_options.addArguments("test-type");
            lin_options.addArguments("window-size=1920x1080");
            lin_options.addArguments("--disable-gpu");
            lin_options.addArguments("--disable-extensions");
            lin_options.addArguments("--no-sandbox");
            lin_options.addArguments("acceptSslCerts=true");
            lin_options.addArguments("acceptInsecureCerts=true");
            lin_options.addArguments("--allow-running-insecure-content");
            lin_options.addArguments("--disable-dev-shm-usage");
            lin_options.addArguments("--headless");
            System.out.println("Running on Mac Operating System with " + browser + " browser");
            driver = WebDriverManager.firefoxdriver().capabilities(lin_options).create();
        }
    }
}