package appiumtests.utils;

import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Before;

public class Hooks {
	
	AppiumBase appiumBase = new AppiumBase();
	
	@Before
	public void beforeHookFunction() throws MalformedURLException, InterruptedException {
		appiumBase.createDriver();
	}
	
	@After
	public void afterHookFunction() {
		appiumBase.tearDown();
	}

}
