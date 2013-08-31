package com.manmoe.example.test;

import com.manmoe.example.config.RemoteDriverConfig;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Testing the abstract test case.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class AbstractChromeExtensionTestTest {

	/**
	 * Tests, if we get a remoteDriver. We use the firespotting child class for testing.
	 */
	@Test
	public void testGetWebDriverTest() {
		RemoteDriverConfig remoteDriverConfig = mock(RemoteDriverConfig.class);
		RemoteWebDriver webDriver = mock(RemoteWebDriver.class);

		when(remoteDriverConfig.buildRemoteDriver()).thenReturn(webDriver);

		AbstractChromeExtensionTest testObject = new FirespottingTest();
		 // we insert our testmock in testObject
		testObject.remoteDriverConfig = remoteDriverConfig;

		// run test
		RemoteWebDriver result = testObject.getWebDriver();

		assertEquals(result, webDriver);
	}
}
