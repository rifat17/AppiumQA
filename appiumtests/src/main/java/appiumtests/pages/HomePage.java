package appiumtests.pages;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class HomePage {

	By selectLangLocator = MobileBy.xpath("//android.widget.TextView[position()=1]");
	By maybeBtnLocator = MobileBy.id("com.bikroy:id/txt_text");
	By recycleViewLocator = MobileBy.id("com.bikroy:id/search_results_recyclerview");
	By allAdsOnPageLocator = MobileBy.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout");
	By linearLayoutLocator = By.id("com.bikroy:id/ad_item_fluid_price");
	By searchBtnLocator = MobileBy.id("com.bikroy:id/main_bottom_panel_search");

	// DETAIL Page
	By adTitleLocator = MobileBy.id("com.bikroy:id/ad_detail_title");
	By adPriceLocator = MobileBy.id("com.bikroy:id/ad_detail_price");

	// similar ads container
//	By similarAdsContainer = MobileBy.xpath("//android.widget.LinearLayout[@index=2]/android.widget.RelativeLayout"); // it
	By similarAdsContainerLocator = MobileBy.xpath("//android.widget.LinearLayout/android.widget.RelativeLayout"); // it
	// contains
	// 5
	// relativeLayout
	By priceLocator = MobileBy.xpath("./*//android.widget.TextView[@index=2]"); // ./*//android.widget.TextView[@index=2]
	By footerAdBannerContainerLocator = MobileBy.id("com.bikroy:id/ad_detail_dfp_banner_container");

	// Search menu page
	By propertyLocator = MobileBy.xpath("//android.widget.TextView[@text=\"Property\"]");

	// Property menu Page
	By roomRentalLocator = MobileBy.xpath("//android.widget.TextView[@text=\"Room Rentals\"]");

	// RoomRentalPage
	By adsLocator = MobileBy.xpath("//androidx.recyclerview.widget.RecyclerView").xpath("//android.widget.FrameLayout"); // contains
																															// 2
																															// ads
																															// as
																															// per
																															// screensize

	public boolean isHomePageDisplayed(AndroidDriver driver) throws InterruptedException {

		return driver.findElement(recycleViewLocator).isDisplayed();
	}

	public void testCase02(AndroidDriver driver) {

		WebElement searchBtn = getSearchBtn(driver);
		searchBtn.click();

		WebElement propertyElement = getPropertyElement(driver);
		propertyElement.click();

		WebElement roomRantalElement = getRoomRantalElement(driver);
		roomRantalElement.click();

//		scrollDown(driver);
//
//		List<WebElement> ads = getAdsFromRentalPage(driver);

	}

	public WebElement getAdFromRentalWebElements(AndroidDriver driver) {
		List<WebElement> ads = getAdsFromRentalPage(driver); // return 2 elements
		WebElement ad = ads.get(0); // intentionally talking first from 2 element
		return ad;
	}

	public List getAdsFromRentalPage(AndroidDriver driver) {
		waitForVisible(adsLocator, 0, driver);
		return driver.findElements(adsLocator);
	}

	public WebElement getRoomRantalElement(AndroidDriver driver) {
		scrollTo("Room Rentals", driver);
		WebElement roomRentalElement = driver.findElement(roomRentalLocator);
		return roomRentalElement;
	}

	public WebElement getPropertyElement(AndroidDriver driver) {
		waitForVisible(propertyLocator, 10, driver);
		return driver.findElement(propertyLocator);
	}

	public WebElement getSearchBtn(AndroidDriver driver) {
		waitForVisible(searchBtnLocator, 10, driver);
		return driver.findElement(searchBtnLocator);
	}

	public void scrollTo(String text, AndroidDriver driver) {
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
						+ text + "\").instance(0))");
	}

	public WebElement getRandomAd(AndroidDriver driver) {
		List<WebElement> framelayout = driver.findElements(allAdsOnPageLocator);
		System.out.println("getRandomAd " + framelayout.size());
		Random rand = new Random();

		WebElement randomElement = framelayout.get(rand.nextInt(framelayout.size()));

		return randomElement;

	}

	public String getAdTitle(AndroidDriver driver) {
		String title = null;
		try {
			title = driver.findElement(adTitleLocator).getText();
			System.out.println(title);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return title;
	}

	public String getAdPrice(AndroidDriver driver) {

		String price = null;
		try {
			price = driver.findElement(adPriceLocator).getText();
			System.out.println(price);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return price;
	}

	public WebElement getCheapestProductFromDetailPageAds(AndroidDriver driver) throws InterruptedException {
		List<WebElement> ads = getProductDetailPageAds(driver);
		WebElement cheapestAd = cheapestAd(ads);
		return cheapestAd;

	}

	private WebElement cheapestAd(List<WebElement> ads) {

		final String regexTK = "Tk ([\\d+\\d*+,\\d*]*)\\s?";
		final Pattern pattern = Pattern.compile(regexTK, Pattern.CASE_INSENSITIVE);
		double minPrice = Double.POSITIVE_INFINITY;
		WebElement minPriceElement = null;

		System.out.println("cheapestAd() " + ads.size());

		for (WebElement ad : ads) {
			String priceText = ad.findElement(priceLocator).getText();
//			System.out.println("cheapestAd() " + priceText);
			Matcher matcher = pattern.matcher(priceText);
			double currPrice = 0.0;
			if (matcher.find()) {
				currPrice = parsePriceFromRegexGroup(matcher);
				if (currPrice < minPrice) {
					minPrice = currPrice;
					minPriceElement = ad;
				}
			}
		}
		System.out.println("cheapestAd() MIN PRICE " + minPrice);
		return minPriceElement;
	}

	private List<WebElement> getProductDetailPageAds(AndroidDriver driver) throws InterruptedException {
		Dimension bannerDim = waitForVisibleElement(footerAdBannerContainerLocator, 10, driver);
		scrollUp(bannerDim, driver);
		Thread.sleep(1000);
		waitForVisible(similarAdsContainerLocator, 10, driver);
		List<WebElement> ads = driver.findElements(similarAdsContainerLocator);
		System.out.println("getProductDetailPageAds() " + ads.size());
		return ads;
//		System.out.println("getProductDetailPageAds "+ linearLayout.size());
//		By price = MobileBy.xpath("./*//android.widget.TextView[@index=2]"); /// android.widget.TextView
//		for (WebElement webElement : linearLayout) {
//			System.out.println(webElement.findElement(price).getText());
////			Thread.sleep(1000);
//		}
	}

	public void scrollDown(AndroidDriver driver) {

		Dimension dimention = driver.manage().window().getSize();
		int start_x = (int) (dimention.width * 0.5);
		int start_y = (int) (dimention.height * 0.7);

		int end_x = (int) (dimention.width * 0.2);
		int end_y = (int) (dimention.height * 0.2);

		TouchAction touch = new TouchAction(driver);
		touch.press(PointOption.point(start_x, start_y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				.moveTo(PointOption.point(end_x, end_y)).release().perform();

	}

	public boolean productsOnPageIsBeingShown(AndroidDriver driver) {
		boolean result = true;
		List<MobileElement> productsBeingShown = driver.findElements(allAdsOnPageLocator);
		for (MobileElement mobileElement : productsBeingShown) {

			List<MobileElement> childElements = mobileElement.findElements(By.xpath("./*//android.widget.TextView"));

			for (MobileElement child : childElements) {
				String textFieldValue = child.getText();
				System.out.println(textFieldValue);
				if (textFieldValue.isEmpty()) {
					result = false;
					break;
				}

			}

		}
		return result;
	}

	public WebElement getCheapestProduct(AndroidDriver driver) {

		final String regexTK = "Tk ([\\d+\\d*+,\\d*]*)\\s?";
		final Pattern pattern = Pattern.compile(regexTK, Pattern.CASE_INSENSITIVE);
		double minPrice = Double.POSITIVE_INFINITY;
		WebElement minPriceElement = null;

		List<WebElement> rcv = driver.findElement(recycleViewLocator).findElements(linearLayoutLocator);
		for (WebElement webElement : rcv) {
			String priceText = webElement.getText();
			Matcher matcher = pattern.matcher(priceText);
			double currPrice = 0.0;
			if (matcher.find()) {
				currPrice = parsePriceFromRegexGroup(matcher);
				if (currPrice < minPrice) {
					minPrice = currPrice;
					minPriceElement = webElement;
				}
			}
		}
		System.out.println("MIN PRICE " + minPrice);
		System.out.println("MIN PRICE ELEMENT " + minPriceElement.getText());
		return minPriceElement;
	}

	private double parsePriceFromRegexGroup(Matcher matcher) {
		double currPrice;
		String matched = matcher.group(1);
		Collection<String> splited = Arrays.asList(matched.split(",", 0));
		String res = splited.stream().collect(Collectors.joining(""));
		System.out.println(Double.parseDouble(res));
		currPrice = Double.parseDouble(res);
		return currPrice;
	}

	private void scrollUp(Dimension other, AndroidDriver driver) {
		Dimension dimention = driver.manage().window().getSize();
		int start_x = (int) (dimention.width * 0.2);
		int start_y = (int) (dimention.height * 0.2);

		int end_x = (int) (dimention.width * 0.2);
		int end_y = (int) (start_y + other.height);

		TouchAction touch = new TouchAction(driver);
		touch.press(PointOption.point(start_x, start_y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				.moveTo(PointOption.point(end_x, end_y)).release().perform();

	}

	public Dimension waitForVisibleElement(final By by, int waitTime, AndroidDriver driver) {
		Dimension bannerdim = null;
		WebDriverWait wait = new WebDriverWait(driver, 5);
		for (int attempt = 0; attempt < waitTime; attempt++) {
			try {
				WebElement banner = driver.findElement(by);
				bannerdim = banner.getSize();
				System.out.println(bannerdim);
				System.out.println(driver.manage().window().getSize());
				break;
			} catch (Exception e) {
				driver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);
			}
			scrollDown(driver);
		}

		wait.until(ExpectedConditions.visibilityOfElementLocated(by));

		return bannerdim;
	}

	public void waitForVisible(final By by, int waitTime, AndroidDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		for (int attempt = 0; attempt < waitTime; attempt++) {
			try {
				driver.findElement(by);
				break;
			} catch (Exception e) {
				driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			}
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

}
