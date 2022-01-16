package pages;

import com.aventstack.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageBase {

    /**
     * @author Hassan Amjad
     */
    static Logger log = Logger.getLogger(PageBase.class);
    public WebDriver driver;
    public Actions actions;
    public ExtentTest extentTest;

    /**
     * Constructor of the class.
     *
     * @param driver - driver
     */

    public PageBase(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
    }


    /**
     * Method to clear and input the text for an WebElement.
     *
     * @param element - WebElement to be clickable
     * @param value   - Value to be inserted
     */

    public void inputText(WebElement element, String value) {
        log.info("Executing INPUT text command for the element:" + element.toString() + " with value: " + value);
        waitForElementTobeClickable(element);
        element.clear();
        element.sendKeys(value);
    }

    /**
     * Method to wait for the element on the web page to be clickable
     *
     * @param element - WebElement to be clickable
     */

    public void waitForElementTobeClickable(WebElement element) {
        log.info("Executing waitForElementTobeClickable command for the element: " + element);
        WebDriverWait w = new WebDriverWait(driver, 30);
        try {
            log.info("Waiting for the element:" + element + " to be clickable");
            w.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to verify whether the element is present or not.
     *
     * @param element - WebElement to be check whether present or not.
     * @return - TRUE, if element is present.
     */

    public boolean isElementPresent(WebElement element) {
        log.info("Executing isElementPresent command on the element: " + element);
        boolean present = false;
        try {
            if (element.isDisplayed() && element.isEnabled()) {
                present = true;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
        return present;
    }

    /**
     * Method to click on an element.
     *
     * @param element - WebElement to be clicked.
     */

    public void click(WebElement element) throws InterruptedException {
        log.info("Executing CLICK command on the element: " + element);
        if (isElementPresent(element)) {
            waitForElementTobeClickable(element);
            try {
                element.click();
            } catch (ElementClickInterceptedException e) {
                wait(2000);
                element.click();
            }
        } else {
            System.out.println("Element not visible" + element);
        }
    }

    /**
     * Method which waits for the complete page to load
     */

    public void waitForPageToLoad() {
        log.info("Executing waitForPageToLoad command");
        log.info("Waiting for the page to load completely");
        String pageLoadStatus;
        do {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            pageLoadStatus = (String) js.executeScript("return document.readyState");
        } while (!pageLoadStatus.equals("complete"));
        log.info("Page is loaded completely");
    }

    public void scrollPageDown() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,250)");
    }


   /* public void enterURL(String url) throws InterruptedException {

        try {
            log.info("Executing GET URL command on the browser");
            driver.get(url);
            waitForPageToLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Method to click on an element(click Element) and wait for other element
     * (wait Element).
     *
     * @param clickElement - WebElement to be clicked
     * @param waitElement  - WebElement to wait for after clicking
     */
    public void clickAndWait(WebElement clickElement, WebElement waitElement) {
        log.info("Executing CLICK command on the element: " + clickElement);
        try {
            clickElement.click();
            log.info("Waiting for the element: " + waitElement + " to be clickable");
            WebDriverWait wait = new WebDriverWait(driver, 90);
            wait.until(ExpectedConditions.elementToBeClickable(waitElement));
        } catch (ElementClickInterceptedException e) {
            jsclick(clickElement);
            log.info("Waiting again for the element: " + waitElement + " to be clickable");
            WebDriverWait wait = new WebDriverWait(driver, 90);
            wait.until(ExpectedConditions.elementToBeClickable(waitElement));
        }
    }

    /**
     * Method to wait until the element is NOT present on the page.
     *
     * @param element Element to be checked
     */
    public void waitForElementTillnotPresent(WebElement element) {
        log.info("Executing waitForElementTillnotPresent command for the element: " + element);
        WebDriverWait w = new WebDriverWait(driver, 90);
        try {
            log.info("Waiting for the element:" + element + " not to be visible");
            boolean isVisible = false;
            do {
                Thread.sleep(2000);
                isVisible = element.isDisplayed();
            } while (isVisible);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to wait until the element is present on the page.
     *
     * @param element Element to be checked
     */
    public void waitForElementToBePresent(WebElement element) {
        WebDriverWait w = new WebDriverWait(driver, 30);
        try {
            log.info("Waiting for the element:" + element + " to be visible");
            w.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to click on an element using Javascript executer.
     *
     * @param element - WebElement to be clicked
     */
    public void jsclick(WebElement element) {
        log.info("Executing JSCLICK command on the element: " + element);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    /**
     * Method to check if the element is selected/displayed or not.
     *
     * @param element - WebElement to be checked
     * @return TRUE, if WebElement is selected FALSE, if WebElement is not
     * selected
     */
    public boolean isElementSelected(WebElement element) {
        log.info("Executing isElementSelected command on the element: " + element);
        try {
            return element.isSelected();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Method to check whether the element is displayed or not
     *
     * @param element element to be checked
     * @return True, if element is displayed
     * False, if not.
     */

    public boolean isElementEnabled(WebElement element) {
        log.info("Executing isElementEnabled command on the element: " + element);
        try {
            return element.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Method to check if the text is present or not.
     *
     * @param waitElement  Element to be present
     * @param textToAppear Text to appear
     * @return True, if both of them present
     * False, if element is not present
     */
   /* public boolean isTextPresent(WebElement waitElement, String textToAppear) {
        log.info("Executing isTextPresent command on the element: " + waitElement);
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.textToBePresentInElement(waitElement, textToAppear));
        return isElementPresent(waitElement);
    }*/

    /**
     * Method to sleep for desired time
     *
     * @param milliSeconds - Time in milliseconds for the statement to wait
     */
    public void wait(int milliSeconds) {
        try {
            log.info("Waiting for " + milliSeconds + " milli seconds");
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


