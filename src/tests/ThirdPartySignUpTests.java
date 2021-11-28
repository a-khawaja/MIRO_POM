package tests;

import java.util.Hashtable;

import BasePage.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import pages.SignUpPage;
import util.DataUtil;

public class ThirdPartySignUpTests extends TestBase {

	
	
	@Test(dataProvider = "getData", priority = 1)
	public void ThirdPartySignUp(Hashtable<String, String> data) throws Exception {
		startreport("Test case 1");
		invoke();
		extentTest.log(LogStatus.PASS, "Logged in ");
		System.out.println(data.get("Username"));
		SignUpPage signUpPage = new SignUpPage(driver, extentTest);
		signUpPage.signUPWithThirdParty(data.get("ThirdParty"));
	}

	
	@DataProvider
	public Object[][] getData() {
		return DataUtil.getData(datatable, "ThirdPartySignUp", "MIRO_SIGNUP_TASK");

	}  
}
