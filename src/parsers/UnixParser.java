package parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class UnixParser {

	public HashMap<String, String> read (File customerFile) {
		HashMap<String, String> values = new HashMap<String, String>();
		Scanner scan = null;
		int key = 1;
		
		try {
			scan = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (scan.hasNext()) {
			String nextLine = scan.nextLine();
			if (!nextLine.contains("WARNING:")) continue;
			values.put(Integer.toString(key++), nextLine);
		}
		return values;
	}
	
	public String print (HashMap<String, String> warnings) {
		String returnString = "";
		
		for (String key : warnings.keySet()) {
			System.out.println(warnings.get(key));
		}
		
		
		
		return returnString;
	}
}
