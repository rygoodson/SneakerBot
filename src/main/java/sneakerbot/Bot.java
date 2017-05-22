package main.java.sneakerbot;

import java.util.ArrayList;
import java.util.Map;

import main.java.sneakerbot.atc.Adidas;
import main.java.sneakerbot.loaders.Credentials;
import main.java.sneakerbot.loaders.Credentials.CredentialObject;
import main.java.sneakerbot.loaders.Proxy;
import main.java.sneakerbot.loaders.Proxy.ProxyObject;
import main.java.sneakerbot.thread.ThreadPool;

public class Bot {
	
	static ThreadPool pool;
	static ArrayList<ProxyObject> proxies;
	static Map<String, CredentialObject> credentials;
	
	public static void init() {
		proxies = Proxy.load("data/proxies.txt");
		credentials = Credentials.load("data/credentials.json");
	}

	public static void main(String[] args) {
		init();
		
		CredentialObject creds = credentials.get("CC 1");
		System.out.println("Creds: " + creds.getEmail() + " creds2: " + creds.getCard());
		
		/*pool = new ThreadPool(proxies.size());
		proxies.stream().forEach(p -> {
			pool.run(new Adidas(p, false));	
		});	
		pool.flush();*/
		
	
	}
	
	static {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
	}
}
