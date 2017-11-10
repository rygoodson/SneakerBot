package main.java.sneakerbot.atc;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.ProxyConfig;
import com.machinepublishers.jbrowserdriver.ProxyConfig.Type;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Settings.Builder;
import com.machinepublishers.jbrowserdriver.UserAgent;

import main.java.sneakerbot.loaders.Credentials.CredentialObject;
import main.java.sneakerbot.loaders.Proxy.ProxyObject;

public class Adidas implements Runnable {
	
	public Adidas(ProxyObject proxy, CredentialObject credentials, String url, boolean splash, boolean manual, double[] sizes) {
		super();
		
		this.proxy = proxy;
		this.url = url;
		this.splash = splash;
		this.manual = manual;
		this.sizes = sizes;
		carted = false;
	}

	@Override
	public void run() {
					
	} 
	public void print(String text) {
		System.out.println("[" + Thread.currentThread().getName() + "] " + text);
	}
	
	ProxyObject proxy;
	CredentialObject credentials;
	String url;
	String hmac;
	String siteKey;
	String clientId;
	double[] sizes;
	boolean splash;
	boolean carted;
	boolean manual;

}
