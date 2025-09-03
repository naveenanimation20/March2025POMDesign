package com.qa.opencart.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author naveenautomationlabs
 *
 */
public class JavaScriptUtil {

	private WebDriver driver;
	private JavascriptExecutor js;

	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) driver;
	}

	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");//grey
		for (int i = 0; i < 7; i++) {
			changeColor("rgb(0,200,0)", element);// green
			changeColor(bgcolor, element);// grey
		}
	}

	private void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}

	public String getTitleByJS() {
		return js.executeScript("return document.title;").toString();
	}
	
	public String getURLByJS() {
		return js.executeScript("return document.URL;").toString();
	}
	

	public void refreshBrowserByJS() {
		js.executeScript("history.go(0)");
	}
	
	public void navigateToBackPage() {
		js.executeScript("history.go(-1)");
	}
	
	public void navigateToForwardPage() {
		js.executeScript("history.go(1)");
	}

	public void generateAlert(String message) {
		js.executeScript("alert('" + message + "')");
	}
	

	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText;").toString();
	}

	public void clickElementByJS(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}

	public void sendKeysUsingWithId(String id, String value) {
		js.executeScript("document.getElementById('" + id + "').value='" + value + "'");
	}

	public void scrollPageDown() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollPageDown(String height) {
		js.executeScript("window.scrollTo(0, '" + height + "')");
	}

	public void scrollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}

	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

}

