package com.manmoe.example.model;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * This model is for testing the firespotting options page.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class OptionsPage extends ChromeExtension {
	private static final String PAGE_NAME = "options.html";

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
	 * Method for getting the value of the selected option.
	 *
	 * @return The value of the selected RequestInteral e.g. "300000" and NOT "5 minutes"
	 */
	public String getRequestInterval() {
		return new Select(getDriver().findElementById("RequestInterval")).getFirstSelectedOption().getAttribute("value");
	}

	/**
	 * Method of getting the value of the selected notification timeout.
	 * @return
	 */
	public String getNotificationTimeout() {
		return new Select(getDriver().findElementById("NotificationTimeout")).getFirstSelectedOption().getAttribute("value");
	}
}
