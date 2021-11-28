package tests;

import java.util.Hashtable;

import BasePage.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import pages.SignUpPage;
import util.DataUtil;

public class SignUpFailureTests extends TestBase {

	
	
	@Test(dataProvider = "getData", priority = 1)
	public void SignUpFailure(Hashtable<String, String> data) throws Exception {
		startreport("Test case 2");
		
		invoke();
		extentTest.log(LogStatus.PASS, "Logged in ");
		System.out.println(data.get("Username"));
		SignUpPage signUpPage = new SignUpPage(driver, extentTest);
		//lp.login(data.get("Username"), data.get("Password"));
		signUpPage.signUPNormal(data.get("Username"), data.get("Email"), data.get("Password"), data.get("Description"));
		if (data.get("Description").equalsIgnoreCase("Wrong email")){
			signUpPage.verifySignUpFailureWrongEmail();
		} else if (data.get("Description").equalsIgnoreCase("Wrong password")){
			signUpPage.verifySignUpFailureWrongPassword();
		} else if (data.get("Description").equalsIgnoreCase("Not agreeing to policy")){
			signUpPage.verifySignUpFailurePolicyAgreement();
		}


	}

	
	@DataProvider
	public Object[][] getData() {
		return DataUtil.getData(datatable, "SignUpFailure", "MIRO_SIGNUP_TASK");

	}  
}
