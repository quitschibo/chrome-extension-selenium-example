package com.manmoe.example.config;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.io.File;

import static org.mockito.Mockito.*;

/**
 * Tests the RemoteConfig
 */
public class RemoteConfigTest {
	// our object under test
	private RemoteDriverConfig remoteDriverConfig = spy(new RemoteDriverConfig());

	/**
	 * Test, if the ChromeOptions config is correct.
	 */
	@Test
	public void testBuildChromeOptions() {
		ChromeOptions chromeOptions = mock(ChromeOptions.class);

		when(remoteDriverConfig.createCromeOptions()).thenReturn(chromeOptions);

		remoteDriverConfig.buildChromeOptions();

		verify(chromeOptions, atLeastOnce()).addExtensions(any(File.class));
		verify(chromeOptions, atLeastOnce()).addArguments("--start-maximized");
	}

	/**
	 * Check, if at least the chrome options are added.
	 */
	@Test
	public void testDesiredCapabilities() {
		DesiredCapabilities desiredCapabilities = mock(DesiredCapabilities.class);

		when(remoteDriverConfig.createDesiredCapabilitiesForChrome()).thenReturn(desiredCapabilities);

		remoteDriverConfig.buildDesiredCapabilities();

		verify(desiredCapabilities, atLeastOnce()).setCapability(eq("platform"), any(Platform.class));
		verify(desiredCapabilities, atLeastOnce()).setCapability(eq(ChromeOptions.CAPABILITY), any(ChromeOptions.class));
	}
}
