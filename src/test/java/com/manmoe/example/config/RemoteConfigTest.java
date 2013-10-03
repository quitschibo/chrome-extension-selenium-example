package com.manmoe.example.config;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * Tests the RemoteConfig
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class RemoteConfigTest {
	// our object under test
	private RemoteDriverConfig remoteDriverConfig;

	/**
	 * Method for setting up the test enviroment.
	 */
	@BeforeMethod
	public void setUp() {
		remoteDriverConfig = spy(new RemoteDriverConfig());
	}

	/**
	 * Test, if the ChromeOptions config is correct.
	 */
	@Test
	public void testBuildChromeOptions() {
		ChromeOptions chromeOptions = mock(ChromeOptions.class);

		when(remoteDriverConfig.createCromeOptions()).thenReturn(chromeOptions);

		remoteDriverConfig.buildChromeOptions();

		verify(chromeOptions, atLeastOnce()).addExtensions(any(File.class));
		verify(chromeOptions, atLeastOnce()).addArguments("--start-maximized");
	}

	/**
	 * Check, if at least the chrome options are added.
	 */
	@Test
	public void testDesiredCapabilities() {
		DesiredCapabilities desiredCapabilities = mock(DesiredCapabilities.class);

		when(remoteDriverConfig.createDesiredCapabilitiesForChrome()).thenReturn(desiredCapabilities);

		remoteDriverConfig.buildDesiredCapabilities();

		verify(desiredCapabilities, atLeastOnce()).setCapability(eq("platform"), any(Platform.class));
		verify(desiredCapabilities, atLeastOnce()).setCapability(eq(ChromeOptions.CAPABILITY), any(ChromeOptions.class));
	}

	/**
	 * Testing instantiating chrome options
	 */
	@Test
	public void testCreateChromeOptions() {
		ChromeOptions result = remoteDriverConfig.createCromeOptions();

		assertEquals(result, new ChromeOptions());
	}

	/**
	 * CHeck the instantiation of the correct browser capability.
	 */
	@Test
	public void testCreateDesiredCapabilitiesForChrome() {
		DesiredCapabilities result = remoteDriverConfig.createDesiredCapabilitiesForChrome();

		assertEquals(result.getBrowserName(), DesiredCapabilities.chrome().getBrowserName());
	}

	/**
	 * Testing the instantiation of the remoteDriver Url for remote Selenium tests.
	 */
	@Test
	public void testBuildRemoteDriver() throws MalformedURLException {
		String remoteUrl = "http://example.com";
		DesiredCapabilities desiredCapabilities = mock(DesiredCapabilities.class);
		remoteDriverConfig.desiredCapabilities = desiredCapabilities;
		doReturn(remoteUrl).when(remoteDriverConfig).getRemoteUrl();

		RemoteWebDriver remoteWebDriverMock = mock(RemoteWebDriver.class);
		doReturn(remoteWebDriverMock).when(remoteDriverConfig).createRemoteWebDriver(remoteUrl, desiredCapabilities);

		RemoteWebDriver result = remoteDriverConfig.buildRemoteDriver();

		assertEquals(result, remoteWebDriverMock);
	}
}
