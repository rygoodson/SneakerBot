package main.java.sneakerbot.loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import main.java.sneakerbot.loaders.Credentials.CredentialObject;
import main.java.sneakerbot.loaders.Proxy.ProxyObject;

public class Config {
	
	public static ArrayList<ConfigObject> load(String name) {
        File file = new File(name); 
        
        if(!file.exists()) {
        	System.out.println(name + " does not exist; One has been created for you.");
        	create(name);
        	return null;
        }
        
        Type type = new TypeToken<ArrayList<ConfigObject>>() { }.getType();
		try {
			return new GsonBuilder().create().fromJson(new FileReader(name), type);
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void create(String name) {
        ArrayList<ConfigObject> configs = new ArrayList<ConfigObject>();
        
        configs.add(new ConfigObject("http://adidas.com/yeezy", true, new double[] {8, 8.5, 11, 12.5}, "CC 1", 10));
        configs.add(new ConfigObject("http://adidas.com/yeezy", true, new double[] {6, 6.5, 9, 9.5}, "CC 2", 5));
        
		try (FileWriter writer = new FileWriter(name)) {
			new GsonBuilder().enableComplexMapKeySerialization()
				.setPrettyPrinting().create().toJson(configs, writer);
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	
	public static class ConfigObject {
		
		public ConfigObject(String link, boolean splash, double[] sizes, String payment, int tasks) {
			super();
			this.link = link;
			this.splash = splash;
			this.sizes = sizes;
			this.payment = payment;
			this.tasks = tasks;
		}
		
		public String getLink() {
			return link;
		}
		
		public boolean isSplash() {
			return splash;
		}
		
		public double[] getSizes() {
			return sizes;
		}
		
		public String getPayment() {
			return payment;
		}
		
		public int getTasks() {
			return tasks;
		}

		@Override
		public String toString() {
			return "ConfigObject [link=" + link + ", splash=" + splash + ", sizes=" + Arrays.toString(sizes)
					+ ", payment=" + payment + ", tasks=" + tasks + "]";
		}

		private String link;
		private boolean splash;
		private double[] sizes;
		private String payment;
		private int tasks;
	}
}
