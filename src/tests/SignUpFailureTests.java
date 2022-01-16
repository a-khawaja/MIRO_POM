package tests;

import BasePage.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.DataUtil;

import java.util.Hashtable;

public class SignUpFailureTests extends TestBase {

    @Test(dataProvider = "getData", priority = 1)
    public void SignUpFailure(Hashtable<String, String> data) throws Exception {

        extentTest = extentReports.createTest("Test case 2");

        container.signUpPage.signUPNormal(data.get("Username"), data.get("Email"), data.get("Password"), data.get("Description"));
        if (data.get("Description").equalsIgnoreCase("Wrong email")) {
            container.signUpPage.verifySignUpFailureWrongEmail();
        } else if (data.get("Description").equalsIgnoreCase("Wrong password")) {
            container.signUpPage.verifySignUpFailureWrongPassword();
        } else if (data.get("Description").equalsIgnoreCase("Not agreeing to policy")) {
            container.signUpPage.verifySignUpFailurePolicyAgreement();
        }
    }

    @DataProvider
    public Object[][] getData() {
        return DataUtil.getData(datatable, "SignUpFailure", "MIRO_SIGNUP_TASK");

    }
}

// The other option is to use SignUpPage signUpPage = new SignUpPage(driver, extentTest) inside the SignUpFailure function. In this way, we do not need page container class and also no need of calling the page container in the invoke function.