package main.java.sneakerbot.loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class Credentials {
	
	public static HashMap<String, CredentialObject> load(String name) {
        File file = new File(name); 
        
        if(!file.exists()) {
        	System.out.println(name + " does not exist; One has been created for you.");
        	create(name);
        	return null;
        }
        Type type = new TypeToken<HashMap<String, CredentialObject>>() { }.getType();
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
		CredentialObject creds = new CredentialObject("", new CreditCard("", "", "", "", ""),
				new Billing("", "", "", "", "", "", ""), new Shipping("", "", "", "", "", ""));
		Map<String, CredentialObject> map = new HashMap<String, CredentialObject>();
		map.put("CC 1", creds);
		map.put("CC 2", creds);
		
	       try (FileWriter writer = new FileWriter(name)) {
	    	   new GsonBuilder().enableComplexMapKeySerialization()
	           .setPrettyPrinting().create().toJson(map, writer);
	        } catch (IOException e) { e.printStackTrace(); }
	}
	
	public static class CredentialObject {
		
		public CredentialObject(String email, CreditCard card, Billing billing, Shipping shipping) {
			super();
			this.email = email;
			this.card = card;
			this.billing = billing;
			this.shipping = shipping;
		}
		
		public String getEmail() {
			return email;
		}
		
		public CreditCard getCard() {
			return card;
		}
		
		public Billing getBilling() {
			return billing;
		}
		
		public Shipping getShipping() {
			return shipping;
		}
	
		@Override
		public String toString() {
			return "CredentialObject [email=" + email + ", card=" + card + ", billing=" + billing + ", shipping="
					+ shipping + "]";
		}

		private String email;
		private CreditCard card;
		private Billing billing;
		private Shipping shipping;
		
	}
	
	public static class Shipping {
		
		public Shipping(String firstName, String lastName, String address, String city, String state, String zip) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.address = address;
			this.city = city;
			this.state = state;
			this.zip = zip;
		}
		
		public String getFirstName() {
			return firstName;
		}
		
		public String getLastName() {
			return lastName;
		}
		
		public String getAddress() {
			return address;
		}
		
		public String getCity() {
			return city;
		}
		
		public String getState() {
			return state;
		}
		
		public String getZip() {
			return zip;
		}

		@Override
		public String toString() {
			return "Shipping [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", city="
					+ city + ", state=" + state + ", zip=" + zip + "]";
		}

		private String firstName;
		private String lastName;
		private String address;
		private String city;
		private String state;
		private String zip;
		
	}
	
	public static class Billing {
		
		public Billing(String firstName, String lastName, String address, String city, String state, String zip,
				String phone) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.address = address;
			this.city = city;
			this.state = state;
			this.zip = zip;
			this.phone = phone;
		}
		
		public String getFirstName() {
			return firstName;
		}
		
		public String getLastName() {
			return lastName;
		}
		
		public String getAddress() {
			return address;
		}
		
		public String getCity() {
			return city;
		}
		
		public String getState() {
			return state;
		}
		
		public String getZip() {
			return zip;
		}
		
		public String getPhone() {
			return phone;
		}

		@Override
		public String toString() {
			return "Billing [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", city="
					+ city + ", state=" + state + ", zip=" + zip + ", phone=" + phone + "]";
		}

		private String firstName;
		private String lastName;
		private String address;
		private String city;
		private String state;
		private String zip;
		private String phone;
		
	}
	
	public static class CreditCard {
		
		public CreditCard(String name, String number, String month, String year, String code) {
			super();
			this.name = name;
			this.number = number;
			this.month = month;
			this.year = year;
			this.code = code;
		}
		
		public String getName() {
			return name;
		}
		
		public String getNumber() {
			return number;
		}
		
		public String getMonth() {
			return month;
		}
		
		public String getYear() {
			return year;
		}
		
		public String getCode() {
			return code;
		}
		
		@Override
		public String toString() {
			return "CreditCard [name=" + name + ", number=" + number + ", month=" + month + ", year=" + year + ", code="
					+ code + "]";
		}

		private String name;
		private String number;
		private String month;
		private String year;
		private String code;
	}

}
