package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class SignUpPage extends PageBase {

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(how = How.XPATH, using = "//input[@id='name']")
    WebElement NAME_FIELD;

    @FindBy(how = How.XPATH, using = "//input[@id='email']")
    WebElement EMAIL_FIELD;

    @FindBy(how = How.XPATH, using = "//input[@id='password']")
    WebElement PASSWORD_FIELD;

    @FindBy(how = How.XPATH, using = "//div[@id='password-hint']")
    WebElement PASSWORD_HINT;

    @FindBy(how = How.XPATH, using = "//label[@id='emailError']")
    WebElement EMAIL_HINT;

    @FindBy(how = How.XPATH, using = "//div[@class='signup__error-wrap-login js-signup__error-wrap-login']//div[@class='signup__error']")
    WebElement TERMS_CONDITIONS_HINT;

    @FindBy(how = How.XPATH, using = "//label[@class='mr-checkbox-1__check ']")
    WebElement TERMS_CONDITIONS_CHECK_BOX;

    @FindBy(how = How.XPATH, using = "//span[@id='signup-error-emptyTerms']")
    WebElement TERMS_CONDITIONS_TEXT;

    @FindBy(how = How.XPATH, using = "//div[@class='signup__checkbox-wrap']//div[@class='mr-checkbox-1']//span[@class='mr-checkbox-1__wrap']//label[@class='mr-checkbox-1__check']")
    WebElement UPDATES_CHECK_BOX;

    @FindBy(how = How.XPATH, using = "//span[@id='signup-subscribe-desc']")
    WebElement UPDATES_TEXT;

    @FindBy(how = How.XPATH, using = "//button[@type='submit']")
    WebElement SIGNUP_BUTTON;

    @FindBy(how = How.XPATH, using = "//button[@id='onetrust-accept-btn-handler']")
    WebElement ACCEPT_COOKIES;

    @FindBy(how = How.XPATH, using = "//input[@id='code']")
    WebElement CODE_BOX;

    @FindBy(how = How.XPATH, using = "//button[@id='kmq-slack-button']")
    WebElement SLACK_ICON;

    @FindBy(how = How.XPATH, using = "//button[@id='kmq-office365-button']")
    WebElement OFFICE_ICON;

    @FindBy(how = How.XPATH, using = "//div[@data-color='black']")
    WebElement APPLE_ICON;

    @FindBy(how = How.XPATH, using = "//div[@class='signup__social-container']/button[4]/div[1]")
    WebElement FACEBOOK_ICON;

    @FindBy(how = How.XPATH, using = "//div[@id='a11y-signup-with-google']")
    WebElement GOOGLE_ICON;

    @FindBy(how = How.XPATH, using = "//div[@class='socialtos__terms-wrap']//div[@class='mr-checkbox-1']//span[@class='mr-checkbox-1__wrap']//label[@class='mr-checkbox-1__check']")
    WebElement DATA_POLICY;

    @FindBy(how = How.XPATH, using = "//div[@class='socialtos__terms-wrap socialtos__terms-wrap--last']//div[@class='mr-checkbox-1']//span[@class='mr-checkbox-1__wrap']//label[@class='mr-checkbox-1__check']")
    WebElement UPDATES_CHECKBOX;

//    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Mit der Anmeldung fortfahren')]")
//    WebElement SIGNUP_WITH_THIRD_PARTY;

    @FindBy(how = How.XPATH, using="//button[@class='socialtos__btn js__socialtos-signup']")
    WebElement SIGNUP_WITH_THIRD_PARTY;

    @FindBy(how = How.XPATH, using = "//input[@id='domain']")
    WebElement SLACK_DOMAIN;

    @FindBy(how = How.XPATH, using = "//input[@id='account_name_text_field']")
    WebElement APPLE_DOMAIN;


    public void enterName(String name) {

        waitForElementTobeClickable(NAME_FIELD);

        if (isElementPresent(NAME_FIELD)) {
            inputText(NAME_FIELD, name);
        }
    }

    public void enterEmail(String email) {
        waitForElementTobeClickable(EMAIL_FIELD);
        if (isElementPresent(EMAIL_FIELD)) {
            inputText(EMAIL_FIELD, email);
        }
    }

    public void enterPassword(String password) {
        waitForElementTobeClickable(PASSWORD_FIELD);
        if (isElementPresent(PASSWORD_FIELD)) {
            inputText(PASSWORD_FIELD, password);
        }
    }

    public void clickUpdatesCheckBox() throws InterruptedException {
        waitForElementTobeClickable(UPDATES_CHECK_BOX);
        click(UPDATES_CHECK_BOX);
    }

    public void clickSubmitButton() throws InterruptedException {
        waitForElementTobeClickable(SIGNUP_BUTTON);
        click(SIGNUP_BUTTON);
    }

    public void clickTermsAndConditionCheckBox() throws InterruptedException {
        waitForElementTobeClickable(TERMS_CONDITIONS_CHECK_BOX);
        click(TERMS_CONDITIONS_CHECK_BOX);
    }

    public void acceptCookies() throws InterruptedException {
        waitForElementTobeClickable(ACCEPT_COOKIES);
        if (isElementPresent(ACCEPT_COOKIES))
            click(ACCEPT_COOKIES);
    }

    public void signUPNormal(String name, String email, String password, String description) throws InterruptedException {
        acceptCookies();
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        clickUpdatesCheckBox();
    }

    public boolean verifySignUpSuccessfull() throws InterruptedException {
        boolean flag = false;

        clickTermsAndConditionCheckBox();
        clickSubmitButton();
        waitForPageToLoad();


        Thread.sleep(4000);

        if (isElementPresent(CODE_BOX)) {
            flag = true;
        }
        return flag;
    }

    public void verifySignUpFailureWrongEmail() throws InterruptedException {

        clickTermsAndConditionCheckBox();
        clickSubmitButton();

        waitForPageToLoad();
        waitForElementTobeClickable(EMAIL_HINT);
        Assert.assertTrue(isElementPresent(EMAIL_HINT));
        String EmailHint = EMAIL_HINT.getText();

        Assert.assertEquals("Diese E-Mail kann nicht registriert werden, versuchen Sie bitte eine andere Domain.", EmailHint);

    }

    public void verifySignUpFailurePolicyAgreement() throws InterruptedException {
        clickSubmitButton();

        Assert.assertTrue(isElementPresent(TERMS_CONDITIONS_HINT));
        String TermsHint = TERMS_CONDITIONS_HINT.getText();

        Assert.assertEquals("Bitte stimmen Sie den AGB's zu, um sich zu registrieren.", TermsHint);

    }

    public void verifySignUpFailureWrongPassword() throws InterruptedException {

        clickTermsAndConditionCheckBox();
        clickSubmitButton();

        Assert.assertTrue(isElementPresent(PASSWORD_HINT));
        String passwordHint = PASSWORD_HINT.getText();

        Assert.assertEquals("Bitte 8+ Zeichen für ein sicheres Passwort verwenden.", passwordHint);

    }

    public void signUPWithSlack() throws InterruptedException {
        acceptCookies();

        if (!isElementPresent(SLACK_ICON))
            scrollPageDown();
        click(SLACK_ICON);

        waitForElementTobeClickable(DATA_POLICY);
        click(DATA_POLICY);

        waitForElementTobeClickable(UPDATES_CHECKBOX);
        click(UPDATES_CHECKBOX);

        waitForElementTobeClickable(SIGNUP_WITH_THIRD_PARTY);
        click(SIGNUP_WITH_THIRD_PARTY);

        Assert.assertTrue(isElementPresent(SLACK_DOMAIN));
    }

    public void signUPWithOffice() throws InterruptedException {
        boolean flag = false;
        acceptCookies();

        if (!isElementPresent(OFFICE_ICON))
            scrollPageDown();
        click(OFFICE_ICON);

        waitForElementTobeClickable(DATA_POLICY);
        click(DATA_POLICY);

        waitForElementTobeClickable(UPDATES_CHECKBOX);
        click(UPDATES_CHECKBOX);

        waitForElementTobeClickable(SIGNUP_WITH_THIRD_PARTY);
        click(SIGNUP_WITH_THIRD_PARTY);

        String urlname = driver.getCurrentUrl();
        if (urlname.contains("microsoftonline")) {
            flag = true;
        }
        Assert.assertTrue(flag);
    }

    public void signUPWithApple() throws InterruptedException {
        acceptCookies();

        if (!isElementPresent(GOOGLE_ICON))
            scrollPageDown();
        click(APPLE_ICON);

        waitForElementTobeClickable(DATA_POLICY);
        click(DATA_POLICY);

        waitForElementTobeClickable(UPDATES_CHECKBOX);
        click(UPDATES_CHECKBOX);

        waitForElementTobeClickable(SIGNUP_WITH_THIRD_PARTY);
        click(SIGNUP_WITH_THIRD_PARTY);

        Assert.assertTrue(isElementPresent(APPLE_DOMAIN));
    }

    public void signUPWithFaceBook() throws InterruptedException {
        boolean flag = false;
        acceptCookies();

        if (!isElementPresent(GOOGLE_ICON))
            scrollPageDown();
        click(FACEBOOK_ICON);

        waitForElementTobeClickable(DATA_POLICY);
        click(DATA_POLICY);

        waitForElementTobeClickable(UPDATES_CHECKBOX);
        click(UPDATES_CHECKBOX);

        waitForElementTobeClickable(SIGNUP_WITH_THIRD_PARTY);
        click(SIGNUP_WITH_THIRD_PARTY);

        String urlname = driver.getCurrentUrl();
        if (urlname.contains("facebook")) {
            flag = true;
        }
        Assert.assertTrue(flag);
    }

    public void signUPWithGoogle() throws InterruptedException {
        boolean flag = false;
        acceptCookies();

        if (!isElementPresent(GOOGLE_ICON))
            scrollPageDown();
        click(GOOGLE_ICON);

        waitForElementTobeClickable(DATA_POLICY);
        click(DATA_POLICY);

        waitForElementTobeClickable(UPDATES_CHECKBOX);
        click(UPDATES_CHECKBOX);

        waitForElementTobeClickable(SIGNUP_WITH_THIRD_PARTY);
        click(SIGNUP_WITH_THIRD_PARTY);

        String urlname = driver.getCurrentUrl();
        if (urlname.contains("google")) {
            flag = true;
        }
        Assert.assertTrue(flag);
    }


    public void signUPWithThirdParty(String thirdparty) throws InterruptedException {
        if (thirdparty.equalsIgnoreCase("Slack")) {
            signUPWithSlack();
        } else if (thirdparty.equalsIgnoreCase("google")) {
            signUPWithGoogle();
        } else if (thirdparty.equalsIgnoreCase("facebook")) {
            signUPWithFaceBook();
        } else if (thirdparty.equalsIgnoreCase("office")) {
            signUPWithOffice();
        } else if (thirdparty.equalsIgnoreCase("apple")) {
            signUPWithApple();
        }
    }


}