package tests;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import BasePage.TestBase;

import util.DataUtil;

public class SignUpTest extends TestBase {



	@Test(dataProvider = "getData", priority = 1)
	public void SignUpPage(Hashtable<String, String> data) throws Exception {
		startreport("Test case 1");
		invoke();
		extentTest.log(LogStatus.PASS, "Logged in ");

		//System.out.println(data.get("Username"));
		pages.SignUpPage signUpPage = new pages.SignUpPage(driver, extentTest);
		signUpPage.signUPNormal(data.get("Username"), data.get("Email"), data.get("Password"), data.get("Description"));
		Assert.assertTrue(signUpPage.verifySingUpSuccessfull());

	}

	
	@DataProvider
	public Object[][] getData() {
		return DataUtil.getData(datatable, "SignUpTest", "MIRO_SIGNUP_TASK");

	}  
}
