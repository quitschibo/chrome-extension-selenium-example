package com.manmoe.example.test;

import com.manmoe.example.config.RemoteDriverConfig;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Testing the abstract test case.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class AbstractChromeExtensionTestTest {
	private RemoteDriverConfig remoteDriverConfig;
	private RemoteWebDriver remoteWebDriver;

	private AbstractChromeExtensionTest testObject;

	/**
	 * Method for setting up the test environment.
	 */
	@BeforeMethod
	public void setUp() {
		remoteDriverConfig = mock(RemoteDriverConfig.class);
		remoteWebDriver = mock(RemoteWebDriver.class);

		testObject = new FirespottingIT();

		// we insert our testmock in testObject
		testObject.remoteDriverConfig = remoteDriverConfig;
	}

	/**
	 * Tests, if we get a remoteDriver. We use the firespotting child class for testing.
	 */
	@Test
	public void testGetWebDriverTest() {
		when(remoteDriverConfig.buildRemoteDriver()).thenReturn(remoteWebDriver);

		// run test
		RemoteWebDriver result = testObject.getWebDriver();

		assertEquals(result, remoteWebDriver);
	}

	/**
	 * Tests the creation of the local driver.
	 */
	@Test
	public void testGetLocalDriver() {
		ChromeDriver chromeDriver = mock(ChromeDriver.class);
		when(remoteDriverConfig.buildLocalDriver()).thenReturn(chromeDriver);

		// run test
		RemoteWebDriver result = testObject.getLocalDriver();

		assertEquals(result, chromeDriver);
	}
}
