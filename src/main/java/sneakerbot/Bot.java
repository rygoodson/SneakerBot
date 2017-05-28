package main.java.sneakerbot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.sneakerbot.atc.Adidas;
import main.java.sneakerbot.loaders.Config;
import main.java.sneakerbot.loaders.Config.ConfigObject;
import main.java.sneakerbot.loaders.Credentials;
import main.java.sneakerbot.loaders.Credentials.CredentialObject;
import main.java.sneakerbot.loaders.Proxy;
import main.java.sneakerbot.loaders.Proxy.ProxyObject;
import main.java.sneakerbot.thread.ThreadPool;

public class Bot {
	
	static ThreadPool pool;
	static ArrayList<ProxyObject> proxies;
	static ArrayList<ProxyObject> inUse;
	static Map<String, CredentialObject> credentials;
	static ArrayList<ConfigObject> configs;
	static int taskCount = 0;
	
	public static void init() {
		proxies = Proxy.load("data/proxies.txt");
		inUse = new ArrayList<ProxyObject>();
		credentials = Credentials.load("data/credentials.json");
		configs = Config.load("data/config.json");
		
		for (Object config : configs.stream().toArray())
			taskCount += (int)((ConfigObject) config).getTasks();
		
		pool = new ThreadPool(taskCount);
	}

	public static void main(String[] args) {
		init();
		
		if(configs == null)
			return;
		
		configs.stream().forEach(c -> {
			CredentialObject creds = null;
			
			try {
				credentials.get(c.getPayment());
			} catch (Exception e) { System.out.println(e.getMessage());}
			
			for (int start = 0; start < c.getTasks(); start++) 
				pool.run(new Adidas(getRandomProxy(), creds, c.isSplash(), creds != null ? false : true));
			
		});
		
		System.out.println("Proxies loaded: " + (proxies.size() + inUse.size()) + "\nTasks loaded: " + taskCount + "\nPress Enter to start tasks.");
		try{System.in.read();}
		catch(Exception e){}
		
		pool.flush();
		
	
	}
	
	public static ProxyObject getRandomProxy() {
		int proxyCount = proxies.size();
		int usedCount = inUse.size();
		
		if(proxyCount == 0 && usedCount == 0)
			return null; 
		
		int index = new Random().nextInt(proxyCount != 0 ? proxyCount : usedCount);
		if(proxyCount != 0) {
			ProxyObject proxy = proxies.remove(index);
			inUse.add(proxy);
			return proxy;
		} else 
			return inUse.get(index);

	}
	
	static {
		Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);
		System.setProperty("phantomjs.binary.path", "./drivers/phantomjs.exe");
		System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
	}
}
