package com.manmoe.example.config;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class for configuring and build a ChromeWebDriver.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class RemoteDriverConfig {
	/**
	 * This is the remote Url of the selenium head. You will get the url by an selenium grid provider, e.g. saucelabs.com
	 */
	private static final String REMOTE_URL = "";
	private static final String EXTENSION_FOLDER = "src/main/resources/firespotting.crx";

	private DesiredCapabilities desiredCapabilities;
	private ChromeOptions chromeOptions;

	/**
	 * Here web build the properties we need. Attention: the order matters!
	 */
	public RemoteDriverConfig() {
		buildChromeOptions();
		buildDesiredCapabilities();
	}

	/**
	 * Here we build the ChromeWebDriver we need to run the tests.
	 *
	 * @return Our full configured ChromeWebDriver ready for testing.
	 *
	 * @throws MalformedURLException If the remote URL is not appropriate
	 */
	public RemoteWebDriver buildRemoteDriver() {
		try {
			return new RemoteWebDriver(new URL(REMOTE_URL), desiredCapabilities);
		} catch (MalformedURLException e) {
			System.out.println("Url for remote access is malformed. Please provide a valid URL.");
		}
		// should not happen.
		throw new RuntimeException("Remote Driver could not been build properly, because of an malformed remote url");
	}

	/**
	 * Builds a local driver for debugging issues.
	 *
	 * @return local driver with same configuration as remote driver
	 */
	public ChromeDriver buildLocalDriver() {
		// it may be, that you must provide the path to the local driver here.
		//System.setProperty("webdriver.chrome.driver", "");
		return new ChromeDriver(desiredCapabilities);
	}

	/**
	 * Here you can change the chrome options.
	 *
	 * @see https://code.google.com/p/chromedriver/wiki/CapabilitiesAndSwitches#Using_the_class
	 */
	protected void buildChromeOptions() {
		ChromeOptions options = createCromeOptions();
		options.addExtensions(new File(EXTENSION_FOLDER));
		options.addArguments("--start-maximized");

		this.chromeOptions = options;
	}

	/**
	 * Here you can add your capabilities you want to use.
	 *
	 * @see https://code.google.com/p/chromedriver/wiki/CapabilitiesAndSwitches#List_of_recognized_capabilities
	 */
	protected void buildDesiredCapabilities() {
		DesiredCapabilities capabilities = createDesiredCapabilitiesForChrome();
		capabilities.setCapability("platform", Platform.WIN8);

		// if chrome options are not build yet, we have to handle it
		if (chromeOptions == null) {
			buildChromeOptions();
		}
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

		this.desiredCapabilities = capabilities;
	}

	// ----------------- Just some helpful methods to make it more testable
	/**
	 * Just for creating a ChromeOptions instance.
	 */
	protected ChromeOptions createCromeOptions() {
		return new ChromeOptions();
	}

	/**
	 * Just for creating a DesiredCapabilities instance.
	 */
	protected DesiredCapabilities createDesiredCapabilitiesForChrome() {
		return DesiredCapabilities.chrome();
	}
}
