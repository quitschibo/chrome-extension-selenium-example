package com.manmoe.example.test;

import com.google.common.base.Predicate;
import com.manmoe.example.model.PopupPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import us.monoid.web.Resty;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static us.monoid.web.Resty.content;
import static us.monoid.web.Resty.put;

/**
 * Just an example test.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class FirespottingIT extends AbstractChromeExtensionTest {
	private static final String EXTENSION_NAME_FROM_MANIFEST = "Firespotting! Interesting Ideas, Every Day!";
	public static final long TIME_TO_WAIT_FOR_REFRESH = 3L;

	/**
	 * sum of entries to be shown on popup page
	 */
	public static final int ENTRY_LIST_LENGTH = 15;
	public static final String ISSUES_PAGE_TITLE = "Issues · quitschibo/firespotting-chrome-extension";

	/**
	 * This is our testmodel. So we don't get lost in details, how to get some elements.
	 */
	protected PopupPage popupPage;

	/**
	 * We set it initially == true, so we can &= each test method result.
	 */
	protected boolean testResult = true;

	/**
	 * Our testClient to send the results to sauceLabs
	 */
	protected Resty restClient = new Resty();

	// -------------------- Setting up and down the test environment
	/**
	 * Method for setting up the test environment.
	 */
	@BeforeClass
	public void setUp() {
		this.popupPage = new PopupPage(getWebDriver(), EXTENSION_NAME_FROM_MANIFEST);
	}

	/**
	 * We tell the remote selenium server here, that we have finished.
	 */
	@AfterClass
	public void tearDown() throws IOException {
		// send saucelabs the result of the tests
		// @TODO should be use @AfterSuite in future
		String sauceUsername = System.getenv("SAUCE_USERNAME");
		String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
		String platformString = System.getenv("PLATFORM");
		String buildNr = System.getenv("TRAVIS_BUILD_NUMBER");

		// if sauceLabs is used, the results should be transmitted
		if (sauceUsername != null && sauceAccessKey != null && platformString != null) {
			String jobId = popupPage.getDriver().getSessionId().toString();

			// build sauceLabs result
			String url = "https://saucelabs.com/rest/v1/" + sauceUsername + "/jobs/" + jobId;
			restClient.authenticate("https://saucelabs.com", sauceUsername, sauceAccessKey.toCharArray());
			restClient.withHeader("Content-Type", "application/json");
			restClient.json(url, put(content("{\"passed\": " + testResult + ", \"name\": \"Firespotting! " + platformString + " Test\", \"build\": \"" + buildNr + "\"}")));
		}

		this.popupPage.tearDown();
	}

	/**
	 * We want to know, if every method has succeeded. If one method fails, testResult == false.
	 *
	 * @param result Results of all testMethods.
	 */
	@AfterMethod(alwaysRun = true)
	public void report(ITestResult result)  {
		testResult &= result.isSuccess();
	}

	// -------------------- Tests for the extension
	/**
	 * This test checks, if the chrome extension is installed on the remote system. We want to get the extension id
	 * by the remote host (every chrome browser will generate another id). If the id is present, we assume, the the
	 * extension is installed correctly.
	 */
	@Test
	public void isInstalled() {
		assertTrue(popupPage.getId() != null, "We got null back. The extension is not installed properly");
	}

	/**
	 * Test for checking the popup window.
	 */
	@Test
	public void testPopup() {
		popupPage.open();

		// check title
		assertEquals(popupPage.getTitle(), "Firespotting!");
	}

	/**
	 * Clicks on every item and checks, if it loads.
	 */
	@Test
	public void testEntry() {
		popupPage.open();

		// check if all entries are there
		for (int i = 1; i <= ENTRY_LIST_LENGTH; i++) {
			String linkText = popupPage.getEntryTitle(i);
			assertNotNull(linkText);
			popupPage.clickOnEntryLink(linkText);
			popupPage.getBack();
		}

	}

	/**
	 * Clicks on the issues link and checks, if it loads.
	 */
	@Test
	public void testIssues() {
		popupPage.open();

		popupPage.getDriver().navigate().refresh();

		popupPage.getIssues().click();

		assertTrue(popupPage.getDriver().getTitle().startsWith(ISSUES_PAGE_TITLE));
	}

	/**
	 * Clicks on refresh link.
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void testRefresh() throws InterruptedException {
		popupPage.open();

		popupPage.getRefreshLink().click();

		WebDriverWait driverWait = createWebDriverWait(popupPage.getDriver(), TIME_TO_WAIT_FOR_REFRESH);

		driverWait.until(new Predicate<WebDriver>() {
			@Override
			public boolean apply(org.openqa.selenium.WebDriver webDriver) {
				return popupPage.getTitle().equals("Firespotting!");
			};
		});
		assertEquals(popupPage.getTitle(), "Firespotting!");
	}

	/**
	 * Just a helper method to create a WebDriverWait
	 *
	 * @param driver The driver we want to configure
	 * @param timeToWaitForRefresh The time the driver should wait for a refresh
	 *
	 * @return a newly created WebDriverWait
	 */
	protected WebDriverWait createWebDriverWait(WebDriver driver, long timeToWaitForRefresh) {
		return new WebDriverWait(driver, timeToWaitForRefresh);
	}

	/**
	 * Clicks on Options link.
	 */
	@Test
	public void testOpenOptions() {
		popupPage.open();

		popupPage.getDriver().navigate().refresh();

		popupPage.getOptionsLink().click();

		popupPage.switchToNewTab();

		assertEquals(popupPage.getTitle(), "Options");
	}
}
