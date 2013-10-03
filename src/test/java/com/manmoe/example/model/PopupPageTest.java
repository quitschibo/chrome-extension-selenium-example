package com.manmoe.example.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * Tests the PopupPage model.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class PopupPageTest {
	/**
	 * the object to test
	 */
	private PopupPage popupPage;

	/**
	 * the mock we want to inject into our testobject
	 */
	private RemoteWebDriver webDriver;

	private static final String EXTENSION_NAME = "test-extension";

	/**
	 * mock we want to use within the test
	 */
	private WebElement webElement;

	/**
	 * set up our test evironment
	 */
	@BeforeMethod
	public void setUp() {
		webDriver = mock(RemoteWebDriver.class);
		webElement = mock(WebElement.class);

		popupPage = spy(new PopupPage(webDriver, EXTENSION_NAME));
	}

	@Test
	public void testOpen() {
		doNothing().when(popupPage).navigateTo(anyString());

		// run test method
		popupPage.open();

		verify(popupPage, atLeastOnce()).navigateTo("popup.html");
	}

	@Test
	public void testGetTitle() {
		String testText = "Test";

		when(popupPage.getDriver()).thenReturn(webDriver);
		when(webDriver.findElementById("title")).thenReturn(webElement);
		when(webElement.getText()).thenReturn(testText);

		// run test method
		String result = popupPage.getTitle();

		assertEquals(result, testText);
	}

	@Test
	public void testGetEntryTitle() {
		int row = 42;
		String xPath = "//*[@id=\"feed\"]/tr[" + row + "]/td[2]/a[1]";
		String testString = "test";

		when(popupPage.getDriver()).thenReturn(webDriver);
		when(webDriver.findElementByXPath(xPath)).thenReturn(webElement);
		when(webElement.getText()).thenReturn(testString);

		String result = popupPage.getEntryTitle(row);

		assertEquals(result, testString);
	}

	@Test
	public void testClickOnEntryLink() {
		String linkText = "linkText";

		when(popupPage.getDriver()).thenReturn(webDriver);
		when(webDriver.findElementByLinkText(linkText)).thenReturn(webElement);

		popupPage.clickOnEntryLink(linkText);

		verify(webElement, atLeastOnce()).click();
	}

	@Test
	public void testGetBack() {
		WebDriver.Navigation navigation = mock(WebDriver.Navigation.class);

		when(popupPage.getDriver()).thenReturn(webDriver);
		when(webDriver.navigate()).thenReturn(navigation);

		popupPage.getBack();

		verify(navigation, atLeastOnce()).back();
	}

	@Test
	public void testGetIssues() {
		when(popupPage.getDriver()).thenReturn(webDriver);

		popupPage.getIssues();

		verify(webDriver, atLeastOnce()).findElementByXPath("//*[@id=\"issues\"]");
	}

	@Test
	public void testGetRefreshLink() {
		when(popupPage.getDriver()).thenReturn(webDriver);

		popupPage.getRefreshLink();

		verify(webDriver, atLeastOnce()).findElementByXPath("//*[@id=\"refresh\"]");
	}

	@Test
	public void testGetOptionsLink() {
		when(popupPage.getDriver()).thenReturn(webDriver);

		popupPage.getOptionsLink();

		verify(webDriver, atLeastOnce()).findElementByXPath("//*[@id=\"options\"]");
	}
}
