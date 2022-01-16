package BasePage;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import pages.PageContainer;
import util.Constants;
import util.Xls_Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.lang.System.getProperty;

public class TestBase {

    public static final String run_env = "MIRO";
    public static Properties config = null;

    public WebDriver driver;
    public static Logger log = Logger.getLogger(TestBase.class);

    public static Xls_Reader datatable = null;

    public ExtentSparkReporter sparkReport;
    public ExtentReports extentReports;
    public ExtentTest extentTest;
    public static final String LOG_PATH = Constants.REPORTS_PATH + "extentReports\\";
    private static final String FileUtils = null;
    public PageContainer container;

    @BeforeSuite
    public void initializeConfigAndTestData() throws IOException {

        PropertyConfigurator.configure(System.getProperty("user.dir") + "\\src\\log\\log4j.properties");
        config = new Properties();
        FileInputStream fp = new FileInputStream(System.getProperty("user.dir") + "\\src\\config\\config.properties");
        config.load(fp);

        datatable = new Xls_Reader(System.getProperty("user.dir") + "\\src\\data\\" + run_env + "_Data.xlsx");
        log.info("Initializing all elements");
    }

    @BeforeTest
    public void invoke() {
        String platform = getProperty("os.name");

        if (platform.toLowerCase().contains("win")) {
            windowsDriverSetup();
        } else if (platform.toLowerCase().contains("mac")) {
            macDriverSetup();
        } else if (platform.toLowerCase().contains("lin")) {
            linuxDriverSetup();
        }
        driver.get(config.getProperty("baseURL"));
        container = new PageContainer(driver);
    }

    @AfterTest
    public void quit() {
        driver.quit();
    }

    @BeforeTest
    public void startReport() {

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
        Date date = new Date();
        String actualDate = format.format(date);

        sparkReport = new ExtentSparkReporter(System.getProperty("user.dir") + "/report/extentReports" + actualDate + ".html"); // report path

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReport);

        //extentReports.loadConfig(new File(System.getProperty("user.dir") + "\\ReportsConfig.xml"));
        extentReports.setSystemInfo("Selenium Version", "4.0.1");
        extentReports.setSystemInfo("Environment", run_env);

    }

    @AfterTest
    public void endReport() {
        extentReports.flush();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException {

        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
            extentTest.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in extent report
            String screenshotPath = getScreenshot(driver, result.getName());
            extentTest.addScreenCaptureFromPath(screenshotPath);// adding screen shot
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, "Test Case PASSED IS " + result.getName());
        }
    }

    private void windowsDriverSetup() {

        String browser = config.getProperty("browserType");

        Map<String, Object> prefsMap = new HashMap<>();
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
            win_Options.addArguments("--headless");
            log.info("Running on Windows Operating System with " + browser + " browser");
            driver = WebDriverManager.chromedriver().capabilities(win_Options).create();
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("profile.default_content_settings.popups", 0);
            profile.setPreference("browser.download.folderList", 2);
            //profile.setPreference("browser.download.dir", loc);
            profile.setPreference("browser.download.viewableInternally.enabledTypes", "");
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/msword;application/ms-doc;application/doc;application/pdf;text/plain;application/text;text/xml;application/xml");
            FirefoxOptions win_options = new FirefoxOptions().setProfile(profile);
            win_options.setHeadless(false);
            System.out.println("Running on Mac Operating System with " + browser + " browser");
            driver = WebDriverManager.firefoxdriver().capabilities(win_options).create();
            driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions win_Options = new EdgeOptions();
            win_Options.setExperimentalOption("prefs", prefsMap);
            win_Options.addArguments("window-size=1920x1080");
            win_Options.addArguments("--start-maximized");
            win_Options.addArguments("--disable-gpu");
            win_Options.addArguments("--disable-extensions");
            win_Options.addArguments("--no-sandbox");
            win_Options.addArguments("acceptSslCerts=true");
            win_Options.addArguments("acceptInsecureCerts=true");
            win_Options.addArguments("--allow-running-insecure-content");
            win_Options.addArguments("--headless");
            System.out.println("Running on Mac Operating System with " + browser + " browser");
            driver = WebDriverManager.edgedriver().capabilities(win_Options).create();
        }
    }

    private void macDriverSetup() {

        String browser = config.getProperty("browserType");
        Map<String, Object> prefsMap = new HashMap<>();
        prefsMap.put("profile.default_content_settings.popups", 0);
        //prefsMap.put("download.default_directory", loc);
        prefsMap.put("download.prompt_for_download", false);
        prefsMap.put("safebrowsing.enabled", "false");
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions mac_options = new ChromeOptions();
            mac_options.setExperimentalOption("prefs", prefsMap);
            mac_options.addArguments("--kiosk");
            mac_options.addArguments("window-size=1920x1080");
            mac_options.addArguments("--headless");
            mac_options.addArguments("acceptSslCerts=true");
            mac_options.addArguments("acceptInsecureCerts=true");
            System.out.println("Running on Mac Operating System with " + browser + " browser");
            driver = WebDriverManager.chromedriver().capabilities(mac_options).create();
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("profile.default_content_settings.popups", 0);
            profile.setPreference("browser.download.folderList", 2);
            //profile.setPreference("browser.download.dir", loc);
            profile.setPreference("browser.download.viewableInternally.enabledTypes", "");
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/msword;application/ms-doc;application/doc;application/pdf;text/plain;application/text;text/xml;application/xml");
            FirefoxOptions mac_options = new FirefoxOptions().setProfile(profile);
            mac_options.setHeadless(false);
            System.out.println("Running on Mac Operating System with " + browser + " browser");
            driver = WebDriverManager.firefoxdriver().capabilities(mac_options).create();
            driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions mac_options = new EdgeOptions();
            mac_options.setExperimentalOption("prefs", prefsMap);
            mac_options.addArguments("window-size=1920x1080");
            mac_options.addArguments("--headless");
            mac_options.addArguments("acceptSslCerts=true");
            mac_options.addArguments("acceptInsecureCerts=true");
            System.out.println("Running on Mac Operating System with " + browser + " browser");
            driver = WebDriverManager.edgedriver().capabilities(mac_options).create();
        }
    }

    private void linuxDriverSetup() {

        String browser = config.getProperty("browserType");

        Map<String, Object> prefsMap = new HashMap<>();
        prefsMap.put("profile.default_content_settings.popups", 0);
        //prefsMap.put("download.default_directory", loc);
        prefsMap.put("download.prompt_for_download", false);
        prefsMap.put("safebrowsing.enabled", "false");
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions lin_options = new ChromeOptions();
            lin_options.setExperimentalOption("prefs", prefsMap);
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
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("profile.default_content_settings.popups", 0);
            profile.setPreference("browser.download.folderList", 2);
            //profile.setPreference("browser.download.dir", loc);
            profile.setPreference("browser.download.viewableInternally.enabledTypes", "");
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/msword;application/ms-doc;application/doc;application/pdf;text/plain;application/text;text/xml;application/xml");
            FirefoxOptions lin_options = new FirefoxOptions().setProfile(profile);
            lin_options.setHeadless(false);
            System.out.println("Running on Mac Operating System with " + browser + " browser");
            driver = WebDriverManager.firefoxdriver().capabilities(lin_options).create();
            driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions lin_options = new EdgeOptions();
            lin_options.setExperimentalOption("prefs", prefsMap);
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
            driver = WebDriverManager.edgedriver().capabilities(lin_options).create();
        }
    }

    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).replace(":", "_").replace(" ", "_");
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/report/Screenshots/" + screenshotName + dateName + ".png";
        File finalDestination = new File(destination);
        FileHandler.copy(scrFile, finalDestination);
        return destination;
    }
}