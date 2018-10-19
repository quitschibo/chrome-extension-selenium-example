package com.manmoe.example.model;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This model is for testing the firespotting options page.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class OptionsPage extends ChromeExtension {
	private static final String PAGE_NAME = "options.html";
	private static final String PAGE_TITLE = "Options";

	/**
	 * We need the webdriver and the name of the extension you want to test. Have a look in your manifest, if you are
	 * not sure.
	 *
	 * @param driver
	 * @param extensionName
	 */
	public OptionsPage(RemoteWebDriver driver, String extensionName) {
		super(driver, extensionName);
	}

	/**
	 * Method for open the page.
	 */
	public void open() {
		navigateTo(PAGE_NAME);
	}

	/**
	 * Method for close the page.
	 */
	public void close() {
		getDriver().close();
	}

	/**
	 * Method for accessing the title of the page.
	 *
	 * @return The title of the page.
	 */
	public String getTitle() {
		return getDriver().findElementById("title").getText();
	}

	/**
	 * Method for getting the value of the selected option.
	 *
	 * @return The value of the selected RequestInteral e.g. "300000" and NOT "5 minutes"
	 */
	public String getRequestInterval() {
		return createSelectByWebElement(getDriver().findElementById("RequestInterval")).getFirstSelectedOption().getAttribute("value");
	}

	/**
	 * Method of getting the value of the selected notification timeout.
	 * @return
	 */
	public String getNotificationTimeout() {
		return createSelectByWebElement(getDriver().findElementById("NotificationTimeout")).getFirstSelectedOption().getAttribute("value");
	}

	/**
	 * Just for creating a new Select element by the given web element. It is isolated for test purposes.
	 *
	 * @param element the element we want to create the select from
	 *
	 * @return newly created select element
	 */
	protected Select createSelectByWebElement(WebElement element) {
		return new Select(element);
	}

	/**
	 * This method is for waiting until the site is loaded.
	 */
	public void waitUntilLoaded() {
		WebDriverWait driverWait = new WebDriverWait(getDriver(), 30L);

		driverWait.until(ExpectedConditions.titleIs(PAGE_TITLE));

	}
}
