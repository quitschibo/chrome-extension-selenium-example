package com.manmoe.example.test;

import com.manmoe.example.model.PopupPage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Just an example test.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class FirespottingIT extends AbstractChromeExtensionTest {
	private static final String EXTENSION_NAME_FROM_MANIFEST =  "Firespotting! Interesting Ideas, Every Day!";

	/**
	 * This is our testmodel. So we don't get lost in details, how to get some elements.
	 */
	protected PopupPage popupPage;

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
	public void tearDown() {
		this.popupPage.tearDown();
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

		// check if all 15 entries are there
		for (int i = 1; i <= 15; i++) {
			String linkText = popupPage.getEntryTitle(i);
			assertNotNull(popupPage.getEntryTitle(i));
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

		assertTrue(popupPage.getDriver().getTitle().startsWith("Issues · quitschibo/firespotting-chrome-extension"));
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

		assertEquals(popupPage.getTitle(), "Firespotting!");
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
