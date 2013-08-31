package com.manmoe.example.test;

import com.manmoe.example.model.ChromeExtension;
import org.mockito.Mock;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Tests our Firespotting test example.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class FirespottingTestTest {
	/**
	 * Our test object.
	 */
	private FirespottingTest firespottingTest;

	private ChromeExtension chromeExtension = mock(ChromeExtension.class);

	/**
	 *  Method for setting up the test environment.
	 */
	@BeforeMethod
	public void setUp() {
		this.firespottingTest = spy(new FirespottingTest());
	}

	/**
	 * Let's check, if we set up our test environment properly.
	 */
	@Test
	public void testSetUp() {
		RemoteWebDriver remoteWebDriver = mock(RemoteWebDriver.class);

		doReturn(remoteWebDriver).when(firespottingTest).getWebDriver();

		// run test
		firespottingTest.setUp();

		// check, if all went well
		assertNotNull(firespottingTest.chromeExtension);
	}

	/**
	 * Check the tear down.
	 */
	@Test
	public void testTearDown() {
		// insert mock to test object
		firespottingTest.chromeExtension = this.chromeExtension;

		// run test method
		firespottingTest.tearDown();

		// is the method called to tear down correctly?
		verify(chromeExtension, atLeastOnce()).tearDown();
	}

	/**
	 * We check the isInstalled method.
	 */
	@Test
	public void testIsInstalled() {
		firespottingTest.chromeExtension = this.chromeExtension;

		when(chromeExtension.getId()).thenReturn("testId");

		firespottingTest.isInstalled();
	}
}
