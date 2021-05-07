package appiumtests;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class TestClass {
	

	private static final int THREAD_TIME = 1000;
	private AndroidDriver mobileDriver;
	
	@Before
	public void setUp() throws Exception {	
		
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("appium-version", "1.20.2");
//        desiredCapabilities.setCapability(AndroidMobileCapabilityType.AVD, "Pixel_3a_API_30_x86");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.AVD, "5.1_WVGA_API_22");

        desiredCapabilities.setCapability(AndroidMobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, "se.saltside.activity.SplashActivity");
//        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION, "1000");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.bikroy");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "se.saltside.activity.SplashActivity");
//        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        
        mobileDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
        
        mobileDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        
        
		
		
	}
	
	
	@After
	public void tearDown() {
		mobileDriver.quit();
	}
	
	public void waitForVisible(final By by, int waitTime) {
	    WebDriverWait wait = new WebDriverWait(mobileDriver, 5);
	    for (int attempt = 0; attempt < waitTime; attempt++) {
	        try {
	        	mobileDriver.findElement(by);
	            break;
	        } catch (Exception e) {
	        	mobileDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	        }
	    }
	    wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	public void waitForVisibleElement(final By by, int waitTime) throws InterruptedException {
		Dimension bannerdim=null;
	    WebDriverWait wait = new WebDriverWait(mobileDriver, 5);
	    for (int attempt = 0; attempt < waitTime; attempt++) {
	        try {
	        	WebElement banner = mobileDriver.findElement(by);
	        	bannerdim = banner.getSize();
	        	System.out.println(bannerdim);
	        	System.out.println(mobileDriver.manage().window().getSize());
	            break;
	        } catch (Exception e) {
	        	mobileDriver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);
	        }
	        scrollDown();
	    }
	    
	    wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	    
	    scrollUp(bannerdim);
	}
	
	@Test
	public void TestMethods() {		

		 try{
			MobileElement selectLanguageBtn = (MobileElement) mobileDriver.
	            		findElementsByXPath("//android.widget.TextView[position()=1]").get(3);
	        System.out.println(selectLanguageBtn.getText());
					
	        selectLanguageBtn.click();

			MobileElement maybeLaterBtn = (MobileElement) mobileDriver.
	            		findElementsByXPath("//android.widget.TextView[position()=1]").get(2);
	        System.out.println(maybeLaterBtn.getText());
					
			maybeLaterBtn.click();

			Thread.sleep(THREAD_TIME);
//			
//			By by = By.xpath("//android.widget.ImageButton");
//			waitForVisible(by, 10);
//	        
			
//			List<String> prices = new ArrayList();
	            
//            scrollDown();
            

            
//			prices.addAll(getPrices());
			
//			productsBeingShownProperly();
//			scrollDown();
//			prices.addAll(getPrices());
//			productsBeingShownProperly();
//			scrollDown();
//			prices.addAll(getPrices());

//			parseTk(prices);
            
//            randomSelectAds();
//            testcase01();

        	            
        	
			

//	            titile,price, time => //androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout[5]/*//android.widget.TextView	  
				
	            
	        }catch(Exception e){
	            System.out.println(e.getMessage());
	        }
	}


	
	
	private void testcase01() throws InterruptedException {
		By rcv = By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout");
		waitForVisible(rcv, 10);
		List<WebElement> framelayout = mobileDriver.findElementsByXPath("//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout");
//    		System.out.println(framelayout.size());
		
		Random rand = new Random();
		
		WebElement select = framelayout.get(rand.nextInt(framelayout.size()));
		select.click();
		Thread.sleep(THREAD_TIME);
		
//            scrollTo("Similar ads");
		
		
		By similarAdsContainer = MobileBy.xpath("//android.widget.LinearLayout[@index=2]/android.widget.RelativeLayout"); //it contains 5 relativeLayout
		By bannerContainer = MobileBy.id("com.bikroy:id/ad_detail_dfp_banner_container");
		
		waitForVisibleElement(bannerContainer, 10);
//    		scrollToClass("com.bikroy:id/ad_detail_similar_ads_panel");
		Thread.sleep(1000);
		waitForVisible(similarAdsContainer, 10);
		List<WebElement> linearLayout = mobileDriver.findElements(similarAdsContainer);
		System.out.println(linearLayout.size());
		By price = MobileBy.xpath("./*//android.widget.TextView[@index=2]"); ///android.widget.TextView
		for (WebElement webElement : linearLayout) {
			System.out.println(webElement.findElement(price).getText());
//				Thread.sleep(1000);
		}
	}

	public void scrollTo(String text)
	{                
	mobileDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+text+"\").instance(0))");
	}
	public void scrollToClass(String text)
	{                
	mobileDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\""+text+"\").instance(0))");
	}


	private void randomSelectAds() throws InterruptedException {
		List<WebElement> framelayout = mobileDriver.findElementsByXPath("//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout");
		System.out.println(framelayout.size());
		Random rand = new Random();
		
		WebElement select = framelayout.get(rand.nextInt(framelayout.size()));
		select.click();
		Thread.sleep(THREAD_TIME);
		
		
		String title = mobileDriver.findElement(MobileBy.id("com.bikroy:id/ad_detail_title")).getText();
		String price = mobileDriver.findElement(MobileBy.id("com.bikroy:id/ad_detail_price")).getText();
		
		System.out.println(title);
		System.out.println(price);
		Thread.sleep(THREAD_TIME);
		mobileDriver.navigate().back();
		productsBeingShownProperly();
		scrollDown();
		productsBeingShownProperly();
	}

	
	private void parseTk(List<String> prices) {
		final String regex = "Tk ([\\d+\\d*+,\\d*]*)\\s?";
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        
        for (String price : prices) {
			Matcher matcher = pattern.matcher(price);
			if(matcher.find()) {
				String matched = matcher.group(1);
				Collection<String> splited = Arrays.asList(matched.split(",", 0));
				String res = splited.stream().collect(Collectors.joining(""));
				
//				System.out.println(matched);
//				System.out.println(res);
				System.out.println(Double.parseDouble(res));
			}
			
			
		}

	}

	private List<String> getPrices() {
		By linearLayout = By.id("com.bikroy:id/ad_item_fluid_price");
		List<WebElement> rcv = mobileDriver.findElementById("com.bikroy:id/search_results_recyclerview").findElements(linearLayout);
		List<String> prices = new ArrayList<String>();
		for (WebElement webElement : rcv) {
			String priceText = webElement.getText();
			prices.add(priceText);
//			System.out.println(priceText);
		}
		return prices;
	}


	private void productsBeingShownProperly() {
		List<MobileElement> productedInDisplay = mobileDriver.findElementsByXPath("//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout");
		for (MobileElement mobileElement : productedInDisplay) {
			
			List<MobileElement> childElements = mobileElement.findElements(By.xpath("./*//android.widget.TextView"));
			
			for (MobileElement child : childElements) {
				System.out.println(child.getText());
			}
			
		}
	}


	private void scrollDown() throws InterruptedException {
		Dimension dimention = mobileDriver.manage().window().getSize();

		int start_x = (int) (dimention.width * 0.5);
		int start_y = (int) (dimention.height * 0.7);
		
		int end_x = (int) (dimention.width * 0.2);
		int end_y = (int) (dimention.height * 0.2);
		
		TouchAction touch = new TouchAction(mobileDriver);
		touch.press(PointOption.point(start_x, start_y))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
		.moveTo(PointOption.point(end_x, end_y)).release().perform();
		
//		Thread.sleep(THREAD_TIME);
	}

	private void scrollUp() throws InterruptedException {
		Dimension dimention = mobileDriver.manage().window().getSize();
		int start_x = (int) (dimention.width * 0.2);
		int start_y = (int) (dimention.height * 0.2);
		
		int end_x = (int) (dimention.width * 0.2);
		int end_y = (int) (dimention.height * 0.3);
		
		TouchAction touch = new TouchAction(mobileDriver);
		touch.press(PointOption.point(start_x, start_y))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
		.moveTo(PointOption.point(end_x, end_y)).release().perform();
		
//		Thread.sleep(THREAD_TIME);
	}
	private void scrollUp(Dimension other) throws InterruptedException {
		Dimension dimention = mobileDriver.manage().window().getSize();
		int start_x = (int) (dimention.width * 0.2);
		int start_y = (int) (dimention.height * 0.2);
		
		int end_x = (int) (dimention.width * 0.2);
		int end_y = (int) (start_y + other.height);
		TouchAction touch = new TouchAction(mobileDriver);
		touch.press(PointOption.point(start_x, start_y))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
		.moveTo(PointOption.point(end_x, end_y)).release().perform();
		
		Thread.sleep(THREAD_TIME);
	}
	private void scrollDown(float height) throws InterruptedException {
		Dimension dimention = mobileDriver.manage().window().getSize();
		System.out.println("SCROLLDOWN_F " + dimention);
		int start_x = (int) (dimention.width * 0.5);
		int start_y = (int) (dimention.height * height);
		
		int end_x = (int) (dimention.width * 0.2);
		int end_y = (int) (dimention.height * 0.2);
		
		TouchAction touch = new TouchAction(mobileDriver);
		touch.press(PointOption.point(start_x, start_y))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
		.moveTo(PointOption.point(end_x, end_y)).release().perform();
		
//		Thread.sleep(THREAD_TIME);
	}
	
}








