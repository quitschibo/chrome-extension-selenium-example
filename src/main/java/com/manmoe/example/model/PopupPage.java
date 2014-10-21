package com.manmoe.example.model;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

/**
 * This model is for testing the firespotting popup page.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class PopupPage extends ChromeExtension {
	private static final String PAGE_NAME = "popup.html";

	/**
	 * We need the webdriver and the name of the extension you want to test. Have a look in your manifest, if you are
	 * not sure.
	 *
	 * @param driver
	 * @param extensionName
	 */
	public PopupPage(RemoteWebDriver driver, String extensionName) {
		super(driver, extensionName);
	}

	/**
	 * Method for open the page.
	 */
	public void open() {
		navigateTo(PAGE_NAME);

		waitUntilLoaded();
	}

	/**
	 * Method for accessing the title of the popup page.
	 *
	 * @return The title of the popup page.
	 */
	public String getTitle() {
		return getDriver().findElementById("title").getText();
	}

	/**
	 * Returns the title of the given entry number.
	 *
	 * @param row The entry row count (1-15)
	 * @return title of the given row
	 */
	public String getEntryTitle(int row) {
		WebElement webElement = getDriver().findElementByXPath("//*[@id=\"feed\"]/tr[" + row + "]/td[2]/a[1]");
		return webElement.getText();
	}

	/**
	 * Method clicks on the link provided by the entry
	 */
	public void clickOnEntryLink(String linkText) {
		getDriver().findElementByLinkText(linkText).click();
	}

	/**
	 * Just go back.
	 */
	public void getBack() {
		getDriver().navigate().back();

		waitUntilLoaded();
	}

	/**
	 * Returns the issues link
	 */
	public WebElement getIssues() {
		return getDriver().findElementByXPath("//*[@id=\"issues\"]");
	}

	/**
	 * Returns the refresh link
	 */
	public WebElement getRefreshLink() {
		return getDriver().findElementByXPath("//*[@id=\"refresh\"]");
	}

	/**
	 * Returns the Options link
	 */
	public WebElement getOptionsLink() {
		return getDriver().findElementByXPath("//*[@id=\"options\"]");
	}

	/**
	 * This method is for waiting until the site is loaded.
	 */
	public void waitUntilLoaded() {
		getDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}
}
