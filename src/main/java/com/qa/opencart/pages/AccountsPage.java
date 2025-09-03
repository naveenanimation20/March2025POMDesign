package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	private final By headers = By.cssSelector("div#content h2");
	private final By logoutLink = By.linkText("Logout");
	private final By search = By.name("search");
	private final By searchIcon = By.cssSelector("div#search button");

	public List<String> getAccPageHeaders() {

		List<WebElement> headersList = eleUtil.waitForElementsPresence(headers, AppConstants.DEFAULT_SHORT_WAIT);
		System.out.println("total number of headers: " + headersList.size());
		List<String> headersValList = new ArrayList<String>();
		for (WebElement e : headersList) {
			String text = e.getText();
			headersValList.add(text);
		}
		return headersValList;
	}

	public boolean isLogoutLinkExist() {
		WebElement logoutEle = eleUtil.waitForElementVisible(logoutLink, AppConstants.DEFAULT_MEDIUM_WAIT);
		return eleUtil.isElementDisplayed(logoutEle);
	}

	public SearchResultsPage doSearch(String searchKey) {
		System.out.println("search key -->"+ searchKey);
		WebElement searchEle = eleUtil.waitForElementVisible(search, AppConstants.DEFAULT_MEDIUM_WAIT);
		searchEle.clear();
		searchEle.sendKeys(searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
		
	}

	
	
	
	
	
	
	
	
	
}
