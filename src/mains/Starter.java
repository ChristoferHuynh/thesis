package mains;

import java.io.File;
import java.util.HashMap;

import parsers.RJParser;

public class Starter {

	public static void main(String[] args) {
		
		HashMap<String, String> customerValues = new HashMap<String, String>();
		HashMap<String, String> secureValues = new HashMap<String, String>();
		
		File customerFile;
		File secureFile;
		
		//Kan kolla filnamn f�r att se fr�n vilken audit det �r ifr�n men for now..
		
		
		//remote-job
		RJParser rjParser = new RJParser();
		customerFile = new File(""); // < path
		secureFile = new File(""); 	 // < path
		
		customerValues = rjParser.read(customerFile);
		secureValues = rjParser.read(secureFile);
		
	}
}
