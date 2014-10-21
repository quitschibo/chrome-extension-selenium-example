package com.manmoe.example.model;

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
		chromeExtension = spy(new ChromeExtension(webDriver, "name"));
	}

	/**
	 * Tests, if the parsing of the extension if works fine.
	 */
	//@Test @TODO: this test seems to be wrong -> please repair
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

	/**
	 * Tests the opening of the given page.
	 */
	@Test
	public void testNavigateTo() {
		String testPage = "/index.html";
		String testId = "abcdef";

		doReturn(testId).when(chromeExtension).getId();

		chromeExtension.navigateTo(testPage);

		verify(webDriver, atLeastOnce()).get(ChromeExtension.EXTENSION_URL_PROTOCOL + testId + "/" + testPage);
	}

	/**
	 * Tests the switching to the last opened tab.
	 */
	@Test
	public void testSwitchToNewTab() {
		// creating some window handles for the test
		Set<String> testTabs = new TreeSet<String>();
		testTabs.add("test1");
		testTabs.add("test2");
		testTabs.add("test3");

		doReturn(testTabs).when(webDriver).getWindowHandles();

		WebDriver.TargetLocator targetLocatorMock = mock(WebDriver.TargetLocator.class);
		doReturn(targetLocatorMock).when(webDriver).switchTo();

		// run test method
		chromeExtension.switchToNewTab();

		// check, if the method always switches to the last tab
		verify(targetLocatorMock, atLeastOnce()).window("test3");
	}
}
