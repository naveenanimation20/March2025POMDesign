package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.ExcelUtil;

public class ProductInfoTest extends BaseTest{
	
	//BT(chrome+url) --> BC(login) --> @Test
	
	@BeforeClass
	public void prodInfoSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}	
	
	
	@DataProvider
	public Object[][] getProducts() {
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"imac", "iMac"},
			{"canon", "Canon EOS 5D"}
		};
	}
	
	
	@DataProvider
	public Object[][] getProductTestData() {
		return  ExcelUtil.getTestData("product");
	}
	
	
		
	@Test(dataProvider = "getProductTestData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		String actHeader = productInfoPage.getProductHeader();
		Assert.assertEquals(actHeader, productName);
	}

	
	@DataProvider
	public Object[][] getProductImages() {
		return new Object[][] {
			{"macbook", "MacBook Pro", 4},
			{"samsung", "Samsung SyncMaster 941BW", 1},
			{"canon", "Canon EOS 5D", 3}
		};
	}
	
	
	@Test(dataProvider = "getProductImages")
	public void productImagesCountTest(String searchKey, String productName, int imageCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		int actImagesCount = productInfoPage.getProductImages();
		Assert.assertEquals(actImagesCount, imageCount);
	}
	
	
	
	//macbook,MacBookPro,Apple,OutOfStock,800,Product18,2000.00,2000.00
	//macbook,MacBookAir,Apple,OutOfStock,800,Product18,2000.00,2000.00
	//imac,iMac,Apple,OutOfStock,800,Product18,2000.00,2000.00
	
	
	
	@Test
	public void productInfoTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productDataMap = productInfoPage.getProductData();
		
		SoftAssert softAssert = new SoftAssert();
		
		softAssert.assertEquals(productDataMap.get("productname"), "MacBook Pro");
		
		softAssert.assertEquals(productDataMap.get("Brand"), "Apple");
		softAssert.assertEquals(productDataMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(productDataMap.get("Reward Points"), "800");
		softAssert.assertEquals(productDataMap.get("Product Code"), "Product 18");

		softAssert.assertEquals(productDataMap.get("productprice"), "$2,000.00");
		softAssert.assertEquals(productDataMap.get("extaxprice"), "$2,000.00");

		softAssert.assertAll();//7 --> 1 failed
		
	}
	
	//AAA pattern -- Arrange Act Assert
	// we can have multiple soft assertions in a single test case
	//but only one hard assert in the test case
	
	

}
