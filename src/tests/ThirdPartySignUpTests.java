package tests;

import BasePage.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.DataUtil;

import java.util.Hashtable;

public class ThirdPartySignUpTests extends TestBase {


    @Test(dataProvider = "getData", priority = 1)
    public void ThirdPartySignUp(Hashtable<String, String> data) throws Exception {
        extentTest = extentReports.createTest("Login Test " + data.get("Description"));
        container.signUpPage.signUPWithThirdParty(data.get("ThirdParty"));
        callHomePage();
    }


    @DataProvider
    public Object[][] getData() {
        return DataUtil.getData(datatable, "ThirdPartySignUp", "MIRO_SIGNUP_TASK");

    }
}
