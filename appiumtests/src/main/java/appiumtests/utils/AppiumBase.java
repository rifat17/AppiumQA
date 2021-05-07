package appiumtests.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class AppiumBase {

	public AndroidDriver driver;

	public WebDriverWait waitVar;

	@Before
	public void createDriver() throws MalformedURLException, InterruptedException {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

		desiredCapabilities.setCapability("appium-version", "1.20.2");
//        desiredCapabilities.setCapability(AndroidMobileCapabilityType.AVD, "Pixel_3a_API_30_x86");
		desiredCapabilities.setCapability(AndroidMobileCapabilityType.AVD, "5.1_WVGA_API_22");

		desiredCapabilities.setCapability(AndroidMobileCapabilityType.PLATFORM_NAME, "Android");
		desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY,
				"se.saltside.activity.SplashActivity");
//        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION, "1000");
		desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5556");
		desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.bikroy");
		desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
				"se.saltside.activity.SplashActivity");

		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		waitVar = new WebDriverWait(driver, 10);

		loadHomePage();

		System.out.println("BEFORE");

	}

	private void loadHomePage() throws InterruptedException {
		By selectLang = MobileBy.xpath("//android.widget.TextView[position()=1]");
		List splashPageElements = driver.findElements(selectLang);

//		System.out.println(splashPageElements.size());
		WebElement lang = (WebElement) splashPageElements.get(3);
		if (lang.getText().equalsIgnoreCase("English")) {
			lang.click();
		}

		WebElement maybe = (WebElement) splashPageElements.get(2);
		maybe.click();
		Thread.sleep(1000);
	}

	@After
	public void tearDown() {
		System.out.println("AFTER");
		driver.quit();
	}

}
