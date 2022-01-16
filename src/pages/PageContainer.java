package pages;


import org.openqa.selenium.WebDriver;

public class PageContainer {

    public WebDriver driver;
    public SignUpPage signUpPage;


    public PageContainer(WebDriver driver) {
        this.driver = driver;
        initPages();
    }

    public void initPages() {
        signUpPage = new SignUpPage(driver);
    }
}
