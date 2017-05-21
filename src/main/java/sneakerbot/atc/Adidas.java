package main.java.sneakerbot.atc;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import main.java.sneakerbot.loaders.Proxy.ProxyObject;

public class Adidas implements Runnable {
	
	public Adidas(ProxyObject proxy, boolean manual) {
		super();
		driver = new HtmlUnitDriver(new BrowserVersion("Firefox", "5.0 (Windows)", null/*USER-AGENT*/, 28));
		this.proxy = proxy;
		this.manual = manual;
		carted = false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		driver.get("http://www.adidas.com/yeezy");
		
		//TODO: Get to splash page.
		
		System.out.println(proxy + " -> waiting at splash page!");		
		while(driver.findElement(By.className("sk-fading-circle")).isDisplayed()) {
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println(proxy + " -> passed splash page!");
		
		driver.manage().getCookies().stream().forEach(c -> {
			if(c.getValue().contains("hmac")) {
				hmac = c.getValue();
				hmacExpiration = c.getExpiry();
			}
		});
		
		if(driver.findElements(By.className("g-recaptcha")).size() > 0)
			siteKey = driver.findElement(By.className("g-recaptcha")).getAttribute("data-sitekey");
		
		if(driver.findElements(By.id("flashproductform")).size() > 0)
			clientId = driver.findElement(By.id("flashproductform")).getAttribute("action").split("clientId=")[1];
		
		Date timeLeft = new Date(hmacExpiration.getTime() - System.currentTimeMillis());
		System.out.println("[Success] -> SiteKey: " + siteKey + " Client ID: " + clientId + " HMAC: " + hmac + " Time Left: " + timeLeft.getMinutes() + "m" + timeLeft.getSeconds() + "s");
		
		if(manual) {
			WebDriver checkout = new FirefoxDriver();
			
			for (Cookie cookie : driver.manage().getCookies())
				checkout.manage().addCookie(cookie);
			
			checkout.get(driver.getCurrentUrl());
		} 
		else 
			while(!carted && hmacExpiration.getTime() > System.currentTimeMillis()) 
				atc();
				
	} 
	
	public void atc() {
		try {
			
			// auto checkout
			
		} catch (Exception e) {
			System.out.println("Exception: " + e.toString());
		}
	}
	
	/*@Override
	public void run() {
		
		//Start time
		long start = System.currentTimeMillis();


		try {
			driver.get("http://tools.yzy.io/hmac.html");
		} catch (Exception e) {
			driver.close();
		}
		
		//end time
		long end = System.currentTimeMillis();
		
		//driver.get("http://tools.yzy.io/hmac.html");
		
		//TODO: Get to splash page.
		
		System.out.println(proxy + " -> waiting at splash page! Response time: " + (end - start));	
		boolean found_hmac = false;
		while(!found_hmac) {
				try {
					driver.manage().getCookies().stream().forEach(c -> {
						System.out.println("Name: " + c.getName() + " Value: " + c.getValue());
						if(c.getValue().contains("hmac")) {
							hmac = c.getValue();
							hmacExpiration = c.getExpiry();
						}
					});
					
					if(hmac != null)
						found_hmac = true;
					
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println(proxy + " -> passed splash page!");				
		
	} */
	
	WebDriver driver;
	ProxyObject proxy;
	String hmac;
	Date hmacExpiration;
	String siteKey;
	String clientId;
	boolean carted;
	boolean manual;

}
