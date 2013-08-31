package com.manmoe.example.model;

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * Tests the ChromeExtension model.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class ChromeExtensionTest {
	/**
	 * The webdriver we want to inject into our test object
	 */
	private RemoteWebDriver webDriver;

	/**
	 * Our testobject
	 */
	private ChromeExtension chromeExtension;

	/**
	 * Set up test environment
	 */
	@BeforeMethod
	public void setUp() {
		webDriver = mock(RemoteWebDriver.class);
		chromeExtension = new ChromeExtension(webDriver, "name");
	}

	/**
	 * Tests, if the parsing of the extension if works fine.
	 */
	@Test
	public void testGetId() {
		WebElement webElement = mock(WebElement.class);
		when(webElement.getText()).thenReturn("Firespotting! Interesting Ideas, Every Day! chrome-extension://oegigdbpahkplimfbwbenmpgbbijccig/background.html inspect");
		when(webDriver.findElements(By.id("extensions"))).thenReturn(ImmutableList.of(webElement));

		String result = chromeExtension.getId();

		assertEquals(result, "oegigdbpahkplimfbwbenmpgbbijccig");
		verify(webDriver, atLeastOnce()).get(ChromeExtension.EXTENSION_INSPECT_PAGE);
	}

	/**
	 * Tests, if the tear down works.
	 */
	@Test
	public void testTearDown() {
		chromeExtension.tearDown();

		verify(webDriver, atLeastOnce()).close();
		verify(webDriver, atLeastOnce()).quit();
	}
}
