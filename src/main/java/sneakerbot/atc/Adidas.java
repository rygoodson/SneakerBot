package main.java.sneakerbot.atc;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import main.java.sneakerbot.loaders.Credentials.CredentialObject;
import main.java.sneakerbot.loaders.Proxy.ProxyObject;

public class Adidas implements Runnable {
	
	public Adidas(ProxyObject proxy, CredentialObject credentials, boolean splash, boolean manual) {
		super();
	    DesiredCapabilities capability = new DesiredCapabilities();
	    
	    if(proxy != null) {
	    	String server = proxy.getAddress() + ":" + proxy.getPort();
	        capability.setCapability(CapabilityType.PROXY, new Proxy().setHttpProxy(server).setFtpProxy(server).setSslProxy(server));
	    }
	    
	    capability.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new  String[] { 
	    		"--webdriver-loglevel=NONE" });
	    Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF);
	    
	    capability.setCapability("phantomjs.page.settings.userAgent", ""/*USER-AGENT*/);
        
		driver = new PhantomJSDriver(capability);
		this.proxy = proxy;
		this.splash = splash;
		this.manual = manual;
		carted = false;
	}

	@Override
	public void run() {
		
		while(!carted) {
			
			if(splash)
				splash();
			else
				product();
		}			
	} 
	
	public void product() {
			System.out.println("product mode");
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
	}
	
	@SuppressWarnings("deprecation")
	public void splash() {
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
	
	WebDriver driver;
	ProxyObject proxy;
	CredentialObject credentials;
	Date hmacExpiration;
	String hmac;
	String siteKey;
	String clientId;
	boolean splash;
	boolean carted;
	boolean manual;

}
