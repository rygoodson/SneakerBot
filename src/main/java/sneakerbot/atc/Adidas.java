package main.java.sneakerbot.atc;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Adidas implements Runnable {
	
	public Adidas(String proxy) {
		super();
		System.out.println("ran?");
		driver = new HtmlUnitDriver();
		this.proxy = proxy;
	}

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
			
		System.out.println("[Success] -> SiteKey: " + siteKey + " Client ID: " + clientId + " HMAC: " + hmac);
		
		//TODO: Generate cart url? and checkout.
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
	String proxy;
	String hmac;
	Date hmacExpiration;
	String siteKey;
	String clientId;

}
