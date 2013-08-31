package com.manmoe.example.test;

import com.manmoe.example.config.RemoteDriverConfig;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This is an abstract class for a Chrome extension test. Just override this class and get started with writing tests.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public abstract class AbstractChromeExtensionTest {
	protected RemoteDriverConfig remoteDriverConfig = new RemoteDriverConfig();

	/**
	 * We will provide the WebDriver for the test, so they don't have to care about.
	 *
	 * @return
	 */
	public RemoteWebDriver getWebDriver() {
		return remoteDriverConfig.buildRemoteDriver();
	}
}
