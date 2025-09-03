package com.qa.opencart.tests;

import java.util.List;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class CommonPageTest extends BaseTest{

	
	@Test
	public void checkCommonElementsOnLoginPageTest() {
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(commonsPage.isLogoExist());
		softAssert.assertTrue(commonsPage.isSearchFieldExist());		
		List<String> footerList = commonsPage.footerLinksExist();
		softAssert.assertEquals(footerList.size(), AppConstants.DEFAULT_FOOTER_LINKS_COUNT);
		softAssert.assertAll();
	}
	
	@Test
	public void checkCommonElementsOnAccountsPageTest() {
		loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(commonsPage.isLogoExist());
		softAssert.assertTrue(commonsPage.isSearchFieldExist());		
		List<String> footerList = commonsPage.footerLinksExist();
		softAssert.assertEquals(footerList.size(), AppConstants.DEFAULT_FOOTER_LINKS_COUNT);
		softAssert.assertAll();
	}
	
	
	
}
