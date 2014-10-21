package com.manmoe.example.model;

import com.google.common.base.Predicate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This model is for testing the firespotting issues page.
 *
 * @author Manuel Möhlmann <mail@manmoe.com>
 */
public class IssuesPage extends ChromeExtension {

    /**
     * Page title of this page
     */
    public static final String PAGE_TITLE = "Issues · quitschibo/firespotting-chrome-extension";

    /**
     * We need the webdriver and the name of the extension you want to test. Have a look in your manifest, if you are
     * not sure.
     *
     * @param driver
     * @param extensionName
     */
    public IssuesPage(RemoteWebDriver driver, String extensionName) {
        super(driver, extensionName);
    }

    /**
     * This method is for waiting until the site is loaded.
     */
    public void waitUntilLoaded() {
        WebDriverWait driverWait = new WebDriverWait(getDriver(), 10L);

        driverWait.until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(org.openqa.selenium.WebDriver webDriver) {
                return getDriver().getTitle().startsWith(PAGE_TITLE);
            };
        });
    }
}
