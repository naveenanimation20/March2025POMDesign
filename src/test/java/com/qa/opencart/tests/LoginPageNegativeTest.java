package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class LoginPageNegativeTest extends BaseTest {
	
	
	
	@DataProvider
	public Object[][] getNegativeLoginData() {
		return new Object[][] {
			{"testselelettttt@gmail.com", "test@123"},
			{"march2024@open.com", "test@123"},
			{"march2024@@open.com", "test@@123"},
			{"", "test@123"},
			{"", ""}
		};
	}
	

	@Test(dataProvider = "getNegativeLoginData")
	public void negativeLoginTest(String invalidUN, String invalidPWD) {
		Assert.assertTrue(loginPage.doLoginWithInvalidCredentails(invalidUN, invalidPWD));
	}

}


