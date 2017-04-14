package parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class LynisParser {
	
	Scanner scan;
	
	public HashMap<String, String[]> read(File customerFile) {
		
		HashMap<String, String[]> values = new HashMap<String, String[]>();
		String warning = "";
		String test = "";
		String details = "";
		String solution = "";
		String next = "";
		int id = 1;
		
		scan = null; //WHYYY
		try {
			scan = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(scan.hasNext()) {
			String nextLine = scan.nextLine();
			if (!(nextLine.contains("Warning:") || nextLine.contains("Suggestion:"))) continue;
			warning = "";
			Scanner scan2 = new Scanner(nextLine);
			System.out.println(nextLine);
			scan2.next(); scan2.next();
			while(scan2.hasNext()) {
				next = scan2.next();
				if (next.contains("[")) {
					if (!next.equals(test)) id = 1;
					test = next;
					break;
				}
				warning = warning.concat(next + " ");
			}
			while(scan2.hasNext()) {
				next = scan2.next();
				if (next.contains("[")) {
					details = next;
					break;
				}
			}
			while(scan2.hasNext()) {
				next = scan2.next();
				if (next.contains("[")) {
					solution = next;
					break;
				}
			}
			System.out.println(test + "." + id);
			System.out.println(warning);
			values.put(test + "." + id++, new String[]{warning, details, solution});
		}
		
		
		
		return values;
		
	}
	
	public String print(HashMap<String, String[]> map) {
		String returnString = "";
		
		for (String key : map.keySet()) {
			System.out.println("key: " + key);
			System.out.println("W/S: " + map.get(key)[0]);
			returnString = returnString.concat(key + "\n" + map.get(key)[0] + "\n");
		}
		
		return returnString;
		
	}
	
}
