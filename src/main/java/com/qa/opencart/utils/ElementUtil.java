package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.ElementException;
import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.LoginPage;

import io.qameta.allure.Step;

public class ElementUtil {

	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil jsUtil;

	private static final Logger log = LogManager.getLogger(ElementUtil.class);

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	public void doSendKeys(By locator, String value) {
		log.info("entering the value : " + value + " into locator: " + locator);
		if (value == null) {
			log.error("value : " + value + " is null...");
			throw new ElementException("===value can not be null====");
		}
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}

	public void doMultipleSendKeys(By locator, CharSequence... value) {
		getElement(locator).sendKeys(value);
	}

	public void doClick(By locator) {
		log.info("clicking on element using : " + locator);
		getElement(locator).click();
	}

	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	public boolean isElementCheck(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			throw new ElementException("===ELEMENT NOT FOUND===");
		}
	}

	@Step("checking the element :{0} is displayed on the page.. ")
	public boolean isElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not displayed on the page: " + locator);
			return false;
		}
	}

	public boolean isElementDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not displayed on the page: " + element);
			return false;
		}
	}

	public boolean isElementEnabled(By locator) {
		try {
			return getElement(locator).isEnabled();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not displayed on the page: " + locator);
			return false;
		}
	}

	public String getElementDOMAttributeValue(By locator, String attrName) {
		return getElement(locator).getDomAttribute(attrName);
	}

	public String getElementDOMPropertyValue(By locator, String propName) {
		return getElement(locator).getDomProperty(propName);
	}

	public WebElement getElement(By locator) {
		WebElement element = null;
		try {
			element = driver.findElement(locator);
			log.info("element is found using : " + locator);
			if (Boolean.parseBoolean(DriverFactory.highlightEle)) {
				jsUtil.flash(element);
			}
		} catch (Exception e) {
			FrameworkException fe = new FrameworkException("invalid locator " + " : " + locator);
			log.info("Element not found using " + locator, fe);
		}
		return element;
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();// pc=0,vc=10; []
		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.length() != 0) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	public boolean isElementExist(By locator) {
		if (getElementsCount(locator) == 1) {
			System.out.println("the element : " + locator + " is present on the page one time");
			return true;
		} else {
			System.out.println("the element : " + locator + " is not present on the page");
			return false;
		}
	}

	public boolean isElementExist(By locator, int expectedEleCount) {
		if (getElementsCount(locator) == expectedEleCount) {
			System.out.println("the element : " + locator + " is present on the page " + expectedEleCount + " times");
			return true;
		} else {
			System.out
					.println("the element : " + locator + " is not present on the page " + expectedEleCount + " times");
			return false;
		}
	}

	public void clickElement(By locator, String eleText) {
		List<WebElement> eleList = getElements(locator);
		System.out.println("total number of elements: " + eleList.size());

		for (WebElement e : eleList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(eleText)) {
				e.click();
				break;
			}
		}
	}

	public void doSearch(By searchLocator, String searchKey, By suggestionsLocator, String suggestionValue)
			throws InterruptedException {

		doSendKeys(searchLocator, searchKey);
		Thread.sleep(4000);

		List<WebElement> suggList = getElements(suggestionsLocator);
		System.out.println("total number of suggestions: " + suggList.size());

		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(suggestionValue)) {
				e.click();
				break;
			}
		}
	}

	// *******************Select drop down utils*************//

	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectByVisibleText(By locator, String eleText) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(eleText);
	}

	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();
	}

	public List<String> getDropDownValuesList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		System.out.println("total number of options: " + optionsList.size());

		List<String> optionsValueList = new ArrayList<String>();// pc=0, size=0, []
		for (WebElement e : optionsList) {
			String text = e.getText();
			optionsValueList.add(text);
		}

		return optionsValueList;
	}

	public void selectDropDownValue(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);
		System.out.println(optionsList.size());
		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.contains(value)) {
				e.click();
				break;
			}
		}
	}

	public String getDropDownFirstSelectValue(By locator) {
		Select select = new Select(getElement(locator));
		return select.getFirstSelectedOption().getText();
	}

	// ****************Actions utils*****************//

	private void moveToElement(By locator) {
		act.moveToElement(getElement(locator)).perform();
	}

	public void menuSubMenuHandlingLevel2(By parentMenu, By childMenu) throws InterruptedException {
		moveToElement(parentMenu);
		Thread.sleep(2000);
		doClick(childMenu);
	}

	public void menuSubMenuHandlingLevel3(By menuLevel1, By menuLevel2, By menuLevel3) throws InterruptedException {
		doClick(menuLevel1);
		Thread.sleep(1000);
		moveToElement(menuLevel2);
		Thread.sleep(1000);
		doClick(menuLevel3);
	}

	public void menuSubMenuHandlingLevel4(By menuLevel1, By menuLevel2, By menuLevel3, By menuLevel4, String actionType)
			throws InterruptedException {
		if (actionType.equalsIgnoreCase("click")) {
			doClick(menuLevel1);
		} else if (actionType.equalsIgnoreCase("mousehover")) {
			moveToElement(menuLevel1);
		}
		Thread.sleep(1000);
		moveToElement(menuLevel2);
		Thread.sleep(1000);
		moveToElement(menuLevel3);
		Thread.sleep(1000);
		doClick(menuLevel4);
	}

	public void doActionsSendKeys(By locator, String value) {
		act.sendKeys(getElement(locator), value).perform();
	}

	public void doActionsClick(By locator) {
		act.click(getElement(locator)).perform();
	}

	public void doSendKeysWithPause(By locator, String value, long pauseTime) {

		if (value == null) {
			throw new RuntimeException("===value can not be null");
		}

		char val[] = value.toCharArray();
		for (char ch : val) {
			act.sendKeys(getElement(locator), String.valueOf(ch)).pause(pauseTime).perform();
		}

	}

	public void doSendKeysWithPause(By locator, String value) {

		if (value == null) {
			throw new RuntimeException("===value can not be null");
		}

		char val[] = value.toCharArray();
		for (char ch : val) {
			act.sendKeys(getElement(locator), String.valueOf(ch)).pause(200).perform();
		}

	}

	// *********************Wait util****************//

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		if (Boolean.parseBoolean(DriverFactory.highlightEle)) {
			jsUtil.flash(element);
		}
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	@Step("waiting for element :{0} visible within the timeout: {1}")
	public WebElement waitForElementVisible(By locator, int timeout) {
		log.info("waiting for element using By locator: " + locator + " within time out: " + timeout);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		if (Boolean.parseBoolean(DriverFactory.highlightEle)) {
			jsUtil.flash(element);
		}
		return element;
	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForElementsVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeout
	 */
	public void clickElementWhenReady(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	public Alert waitForAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeout) {
		return waitForAlert(timeout).getText();
	}

	public void acceptAlert(int timeout) {
		waitForAlert(timeout).accept();
	}

	public void dismissAlert(int timeout) {
		waitForAlert(timeout).dismiss();
	}

	public void sendKeysInAlert(int timeout, String value) {
		waitForAlert(timeout).sendKeys(value);
	}

	public String waitForTitleContains(String fractionTitleValue, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			wait.until(ExpectedConditions.titleContains(fractionTitleValue));
		} catch (TimeoutException e) {
			System.out.println("expected title value : " + fractionTitleValue + " is not present");
		}

		return driver.getTitle();
	}

	@Step("waiting for page title with expected value: {0}")
	public String waitForTitleIs(String expectedTitleValue, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			wait.until(ExpectedConditions.titleIs(expectedTitleValue));
		} catch (TimeoutException e) {
			System.out.println("expected title value : " + expectedTitleValue + " is not present");
		}

		return driver.getTitle();
	}

	@Step("waiting for page url with expected fraction value: {0}")
	public String waitForURLContains(String fractionURLValue, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			wait.until(ExpectedConditions.urlContains(fractionURLValue));
		} catch (TimeoutException e) {
			System.out.println("expected URL value : " + fractionURLValue + " is not present");
		}

		return driver.getCurrentUrl();
	}

	public String waitForURLIs(String epxectedURLValue, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			wait.until(ExpectedConditions.urlToBe(epxectedURLValue));
		} catch (TimeoutException e) {
			System.out.println("expected URL value : " + epxectedURLValue + " is not present");
		}

		return driver.getCurrentUrl();
	}

	public boolean waitForWindow(int expectedNoOfWindows, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNoOfWindows));
		} catch (TimeoutException e) {
			System.out.println("expected number of windows are correct");
			return false;
		}
	}

	public boolean waitForFrame(By frameLocator, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
			return true;
		} catch (TimeoutException e) {
			System.out.println("frame is not present on the page");
			return false;
		}

	}

	public boolean waitForFrame(int frameIndex, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
			return true;
		} catch (TimeoutException e) {
			System.out.println("frame is not present on the page");
			return false;
		}

	}

	public boolean waitForFrame(String frameNameOrID, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrID));
			return true;
		} catch (TimeoutException e) {
			System.out.println("frame is not present on the page");
			return false;
		}

	}

	// ******************FluentWait Utils************//

	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeout, int pollingtime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofMillis(pollingtime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).withMessage("=====ELEMENT NOT VISIBLE ON THE PAGE====");

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeout, int pollingtime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofMillis(pollingtime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).withMessage("=====ELEMENT NOT PRESENT ON THE PAGE====");

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void waitForFrameWithFluentWait(By frameLocator, int timeout, int pollingtime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofMillis(pollingtime)).ignoring(NoSuchFrameException.class)
				.withMessage("=====FRAME NOT VISIBLE ON THE PAGE====");

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public Alert waitForAlertWithFluentWait(int timeout, int pollingtime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofMillis(pollingtime)).ignoring(NoAlertPresentException.class)
				.withMessage("=====Alert NOT VISIBLE ON THE PAGE====");

		return wait.until(ExpectedConditions.alertIsPresent());
	}

}
