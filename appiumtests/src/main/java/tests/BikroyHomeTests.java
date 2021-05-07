package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import appiumtests.pages.HomePage;
import appiumtests.utils.AppiumBase;

public class BikroyHomeTests extends AppiumBase {
	private static final long THREAD_TIME = 1000;

	HomePage homePage = new HomePage();

	@Ignore("Testing ignore")
	@Test
	public void isHomePageDisplayed() throws InterruptedException {
		boolean actual = homePage.isHomePageDisplayed(driver);
		assertTrue(actual);
		Thread.sleep(THREAD_TIME);
		homePage.scrollDown(driver);
		homePage.getRandomAd(driver).click();
		assertNotNull(homePage.getAdTitle(driver));
		assertNotNull(homePage.getAdPrice(driver));

		for (int i = 0; i < 2; i++) {
			homePage.getCheapestProductFromDetailPageAds(driver).click();
			Thread.sleep(THREAD_TIME);
		}

//		Thread.sleep(THREAD_TIME);
//		driver.navigate().back();
//		Thread.sleep(THREAD_TIME);
//		homePage.scrollDown(driver);
//		Thread.sleep(THREAD_TIME);
//		
//		assertTrue(homePage.productsOnPageIsBeingShown(driver));
//		Thread.sleep(THREAD_TIME);
//		
//		homePage.getCheapestProduct(driver).click();
//		Thread.sleep(THREAD_TIME+THREAD_TIME+THREAD_TIME);
//		assertNotNull(homePage.getAdTitle(driver));
//		assertNotNull(homePage.getAdPrice(driver));
//		Thread.sleep(THREAD_TIME);
//		driver.navigate().back();
//		Thread.sleep(THREAD_TIME);
//		homePage.scrollDown(driver);
//		Thread.sleep(THREAD_TIME);
//		

	}

	@Test
	public void TestCase02() throws InterruptedException {
		homePage.getSearchBtn(driver).click();
		homePage.getPropertyElement(driver).click();
		homePage.getRoomRantalElement(driver).click();
		Thread.sleep(THREAD_TIME * 3);
		homePage.scrollDown(driver);
		Thread.sleep(THREAD_TIME * 2);
		homePage.getAdFromRentalWebElements(driver).click();
		Thread.sleep(THREAD_TIME * 2);
		assertNotNull(homePage.getAdTitle(driver));
		assertNotNull(homePage.getAdPrice(driver));
		homePage.getCheapestProductFromDetailPageAds(driver).click();
		Thread.sleep(THREAD_TIME * 2);

	}

}
