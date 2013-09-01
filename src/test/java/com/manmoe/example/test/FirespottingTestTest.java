package com.manmoe.example.test;

import com.manmoe.example.model.PopupPage;
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

	private PopupPage popupPage = mock(PopupPage.class);

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
		assertNotNull(firespottingTest.popupPage);
	}

	/**
	 * Check the tear down.
	 */
	@Test
	public void testTearDown() {
		// insert mock to test object
		firespottingTest.popupPage = this.popupPage;

		// run test method
		firespottingTest.tearDown();

		// is the method called to tear down correctly?
		verify(popupPage, atLeastOnce()).tearDown();
	}

	/**
	 * We check the isInstalled method.
	 */
	@Test
	public void testIsInstalled() {
		firespottingTest.popupPage = this.popupPage;

		when(popupPage.getId()).thenReturn("testId");

		firespottingTest.isInstalled();
	}
}
