package tests;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import BasePage.TestBase;

import util.DataUtil;

public class SignUpTest extends TestBase {

    @Test(dataProvider = "getData", priority = 1)
    public void SignUpPage(Hashtable<String, String> data) throws Exception {
        extentTest = extentReports.createTest("Login Tests" + data.get("Description"));
        container.signUpPage.signUPNormal(data.get("Username"), data.get("Email"), data.get("Password"), data.get("Description"));
        Assert.assertTrue(container.signUpPage.verifySingUpSuccessfull());
    }

    @DataProvider
    public Object[][] getData() {
        return DataUtil.getData(datatable, "SignUpTest", "MIRO_SIGNUP_TASK");

    }
}
