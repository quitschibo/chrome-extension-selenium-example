package com.manmoe.example.model;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

/**
 * Tests the OptionsPage model.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class OptionsPageTest {
	/**
	 * the object to test
	 */
	private OptionsPage optionsPage;

	/**
	 * the mock we want to inject into our testobject
	 */
	private RemoteWebDriver webDriver;

	private static final String EXTENSION_NAME = "test-extension";

	/**
	 * set up our test evironment
	 */
	@BeforeMethod
	public void setUp() {
		webDriver = mock(RemoteWebDriver.class);

		optionsPage = spy(new OptionsPage(webDriver, EXTENSION_NAME));
	}

	@Test
	public void testOpen() {
		doNothing().when(optionsPage).navigateTo(anyString());

		// run test method
		optionsPage.open();

		verify(optionsPage, atLeastOnce()).navigateTo("options.html");
	}

	@Test
	public void testClose() {
		when(optionsPage.getDriver()).thenReturn(webDriver);

		// run test method
		optionsPage.close();

		verify(webDriver, atLeastOnce()).close();
	}
}
