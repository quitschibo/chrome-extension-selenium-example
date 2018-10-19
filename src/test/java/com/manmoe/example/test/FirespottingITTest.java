package com.manmoe.example.test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.manmoe.example.model.IssuesPage;
import com.manmoe.example.model.OptionsPage;
import com.manmoe.example.model.PopupPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import us.monoid.web.Resty;

import java.io.IOException;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Tests our Firespotting test example.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class FirespottingITTest {
	/**
	 * Our test object.
	 */
	private FirespottingIT firespottingIT;

	private PopupPage popupPage = mock(PopupPage.class);

	private IssuesPage issuesPage = mock(IssuesPage.class);

	private OptionsPage optionsPage = mock(OptionsPage.class);

	/**
	 *  Method for setting up the test environment.
	 */
	@BeforeMethod
	public void setUp() {
		this.firespottingIT = spy(new FirespottingIT());
		this.firespottingIT.popupPage = this.popupPage;
		this.firespottingIT.issuesPage = this.issuesPage;
		this.firespottingIT.optionsPage = this.optionsPage;
	}

	/**
	 * Let's check, if we set up our test environment properly.
	 */
	@Test
	public void testSetUp() {
		RemoteWebDriver remoteWebDriver = mock(RemoteWebDriver.class);
		WebDriver.Options options = mock(WebDriver.Options.class);
		WebDriver.Timeouts timeouts = mock(WebDriver.Timeouts.class);

		when(remoteWebDriver.manage()).thenReturn(options);
		when(options.timeouts()).thenReturn(timeouts);

		doReturn(remoteWebDriver).when(firespottingIT).getWebDriver();

		// run test
		firespottingIT.setUp();

		// check, if all went well
		assertNotNull(firespottingIT.popupPage);
	}

	/**
	 * Check the tear down.
	 */
	@Test
	public void testTearDown() throws IOException {
		// insert mock to test object
		firespottingIT.popupPage = this.popupPage;

		// mock rest client
		firespottingIT.restClient = mock(Resty.class);

		// mock some objects for session key
		RemoteWebDriver remoteWebDriver = mock(RemoteWebDriver.class);
		SessionId sessionId = mock(SessionId.class);
		when(popupPage.getDriver()).thenReturn(remoteWebDriver);
		when(remoteWebDriver.getSessionId()).thenReturn(sessionId);
		when(sessionId.toString()).thenReturn("72345863");

		doReturn("sauceUsername").when(firespottingIT).getSystemVariable("SAUCE_USERNAME");
		doReturn("sauceKey").when(firespottingIT).getSystemVariable("SAUCE_ACCESS_KEY");
		doReturn("platform").when(firespottingIT).getSystemVariable("PLATFORM");
		doReturn("travisBuildNr").when(firespottingIT).getSystemVariable("TRAVIS_BUILD_NUMBER");

		// run test method
		firespottingIT.tearDown();

		// is the method called to tear down correctly?
		verify(popupPage, atLeastOnce()).tearDown();

		// verify rest client actions if environment variables are set
		// @TODO: add better verification! (no more anyStrings; check the values!)
		verify(firespottingIT.restClient, atLeastOnce()).authenticate(anyString(), anyString(), anyString().toCharArray());
		verify(firespottingIT.restClient, atLeastOnce()).withHeader("Content-Type", "application/json");
	}

	@Test(dataProvider = "reportDataProvider")
	public void testReport(boolean testSuccess) {
		ITestResult itResultMock = mock(ITestResult.class);
		when(itResultMock.isSuccess()).thenReturn(testSuccess);
		// set initial value of test result
		firespottingIT.testResult = true;

		// run test method
		firespottingIT.report(itResultMock);

		assertEquals(firespottingIT.testResult, testSuccess);
	}

	@DataProvider
	public Object[][] reportDataProvider() {
		return new Object[][] {
			{true},
			{false}
		};
	}

	/**
	 * We check the isInstalled method.
	 */
	@Test
	public void testIsInstalled() {
		firespottingIT.popupPage = this.popupPage;

		when(popupPage.getId()).thenReturn("testId");

		firespottingIT.isInstalled();
	}

	@Test
	public void testPopupTest() {
		WebElement titleMock = mock(WebElement.class);

		when(popupPage.getTitle()).thenReturn(titleMock);
		when(titleMock.getText()).thenReturn("Firespotting!");

		firespottingIT.testPopup();

		verify(popupPage, atLeastOnce()).open();
		verify(popupPage, atLeastOnce()).getTitle();
	}

	@Test
	public void testIssuesTest() {
		RemoteWebDriver driver = mock(RemoteWebDriver.class);
		WebDriver.Navigation navigation = mock(WebDriver.Navigation.class);
		WebElement issuesElement = mock(WebElement.class);

		// popupPage.getDriver().navigate()--> .refresh(); follows in verifying section
		when(popupPage.getDriver()).thenReturn(driver);
		when(driver.navigate()).thenReturn(navigation);

		// popupPage.getIssues()-->.click(); follows in verifying section
		when(popupPage.getIssues()).thenReturn(issuesElement);

		when(driver.getTitle()).thenReturn(IssuesPage.PAGE_TITLE);

		firespottingIT.testIssues();

		verify(popupPage, atLeastOnce()).open();
		verify(issuesElement, atLeastOnce()).click();
	}

	@Test
	public void testRefreshTest() {
		WebElement refreshLink = mock(WebElement.class);
		WebDriverWait driverWait = mock(WebDriverWait.class);
		WebElement titleMock = mock(WebElement.class);

		when(popupPage.getRefreshLink()).thenReturn(refreshLink);
		when(popupPage.getTitle()).thenReturn(titleMock);
		when(titleMock.getText()).thenReturn("Firespotting!");
		doReturn(driverWait).when(firespottingIT).createWebDriverWait(any(WebDriver.class), eq(FirespottingIT.TIME_TO_WAIT_FOR_REFRESH));

		// run test method
		firespottingIT.testRefresh();

		verify(popupPage, atLeastOnce()).open();
		verify(refreshLink, atLeastOnce()).click();
		verify(driverWait, atLeastOnce()).until(any(Function.class));
	}

	@Test
	public void testOpenOptionsTest() {
		RemoteWebDriver webDriverMock = mock(RemoteWebDriver.class);
		WebElement optionsLinkMock = mock(WebElement.class);
		WebElement titleMock = mock(WebElement.class);

		// popupPage.getDriver().navigate().refresh();
		when(popupPage.getDriver()).thenReturn(webDriverMock);

		// popupPage.getOptionsLink().click();
		when(popupPage.getOptionsLink()).thenReturn(optionsLinkMock);

		// popupPage.getTitle()
		when(popupPage.getTitle()).thenReturn(titleMock);

		// popupPage.getTitle().getText()
		when(titleMock.getText()).thenReturn("title");

		// optionsPage.waitUntilLoaded();
		doNothing().when(optionsPage).waitUntilLoaded();

		// run test method
		firespottingIT.testOpenOptions();

		verify(popupPage, atLeastOnce()).open();
		verify(optionsLinkMock, atLeastOnce()).click();
		verify(popupPage, atLeastOnce()).switchToNewTab();
	}
}
