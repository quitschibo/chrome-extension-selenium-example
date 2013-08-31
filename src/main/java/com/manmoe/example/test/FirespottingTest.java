package com.manmoe.example.test;

import com.manmoe.example.model.ChromeExtension;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Just an example test.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class FirespottingTest extends AbstractChromeExtensionTest {
	/**
	 * This is our testmodel. So we don't get lost in details, how to get some elements.
	 */
	protected ChromeExtension chromeExtension;

	// -------------------- Setting up and down the test environment
	/**
	 * Method for setting up the test environment.
	 */
	@BeforeClass
	public void setUp() {
		this.chromeExtension = new ChromeExtension(getWebDriver(), "Firespotting! Interesting Ideas, Every Day!");
	}

	/**
	 * We tell the remote selenium server here, that we have finished.
	 */
	@AfterClass
	public void tearDown() {
		this.chromeExtension.tearDown();
	}

	// -------------------- Tests for the extension
	/**
	 * This test checks, if the chrome extension is installed on the remote system. We want to get the extension id
	 * by the remote host (every chrome browser will generate another id). If the id is present, we assume, the the
	 * extension is installed correctly.
	 */
	@Test
	public void isInstalled() {
		assertTrue(chromeExtension.getId() != null, "We got null back. The extension is not installed properly");
	}
}
