package parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class RJParser extends Parser {

	@Override
	public HashMap<String, String> read(File file) {
		HashMap<String, String> values = new HashMap<String, String>(); 
		
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String nextKey = scanner.next(); // key = value i loggsen.. behï¿½ver nog gï¿½ras pï¿½ annorlunda sï¿½tt men du fattar
			String nextValue = scanner.next();
			System.out.println(nextKey);
			System.out.println(nextValue);
			values.put(nextKey, nextValue);
		}
		
		return values;
	}

	@Override
	public boolean compare() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public HashMap<String, String[]> read_cron_at_info(File customerFile) {
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 

		String[] files = {"null", "/etc/cron.allow/", "/etc/at.allow/"};
		int fileIndex = 0;
		String currFile = files[fileIndex];
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String nextLine = scanner.nextLine();
			if (nextLine.contains("No such file or directory")) {
				System.out.println(fileIndex);
				currFile = files[++fileIndex];
				System.out.println(currFile);
				values.put(currFile, new String[]{"No such file or directory"});
			}
			else if (nextLine.contains("total")) currFile = files[++fileIndex];
			else { 
			//	String[] nextValue = nextLine.split("separator"); //Doesn't work with " " :(
				String[] nextValue = new String[9];
				for (int i = 0; i < nextValue.length; i++) {
					nextValue[i] = scanner.next();
					System.out.print("%" + nextValue[i] + "%" + "\t");
				}
				System.out.println();
				values.put(currFile.concat(nextValue[8]), nextValue);
			}
		}
		
		return values;
	}

	public String evaluate_cron_at_info(HashMap<String, String[]> customerInfo) {
		Iterator itr = customerInfo.keySet().iterator();
		String returnString = "";
		
		while (itr.hasNext()){
			String key = (String) itr.next();
			String[] value = customerInfo.get(key);
			if (value[0].contains("No such file or directory")) {
				returnString = returnString.concat("No " + key  + " has been set up.\n");
			}
			else { 
				String[] permissions = value[0].split("");
				if (permissions[8].equals("w")) {
					returnString = returnString.concat("\n Warning! Any user can alter the cron for " + key
							+ "\n It would be a good idea to change permissions so that only the owner and group can write to this file");
				}
			}
		}
		return returnString;
	}

	public HashMap<String, String[]> read_crontab_info(File customerFile) { //Unsure how to parse, how does it look when they exist?
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 
		int line = 1;
		String notSetupString = "No crontab has been set up for the following: \n";

		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String nextLine = scanner.nextLine();
			System.out.println("line " + line++ + ": " + nextLine);
			if (nextLine.startsWith("#") || nextLine.equals("")) System.out.println("im 12 btw haHAA");
			else {
				notSetupString = notSetupString.concat(nextLine.split(" ")[3] + ", ");
				System.out.println("There is no crontab for "  + nextLine.split(" ")[3] + " set up");
			}
		}
		
		
		return values;
	}
	
	public String evaluate_crontab_info(HashMap<String, String[]> customerInfo) {
		// Already implemented in read method oops
		return "";
	}
	
	public HashMap<String, String[]> read_diskvolume_info(File customerFile) {
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 
		
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			//[Filesystem][Size][Used][Avail][Use%][Mounted on]
			String[] nextValues = new String[6];
			
			for (int i = 0; i < nextValues.length - 1; i++) {
				nextValues[i] = scanner.next();
			}
			nextValues[5] = scanner.nextLine();
			
			for (int i = 0; i < nextValues.length; i++) {
				System.out.print(nextValues[i] + "\t");
			}
			System.out.println();
			
			values.put(nextValues[5], nextValues);
		}
		
		return values;
	}

	public String evaluate_diskvolume_info(HashMap<String, String[]> customerInfo) {
		String returnString = "Current mounted discs(?): \n";
		
		Iterator itr = customerInfo.keySet().iterator();
		
		while (itr.hasNext()) {
			String key = (String) itr.next();
			returnString = returnString.concat(key + ", ");
		}
		
		return returnString;
	}
	
	
	public HashMap<String, HashMap<String, String>> read_encrypted_disk_info(File customerFile) {
		HashMap<String, HashMap<String, String>> values = new HashMap<String, HashMap<String, String>>(); 
		String SingleLine;
		String[] SingleLineVector;
		String[] LineVectorVector; //lol..
		
		HashMap<String, String> innerValues = new HashMap<String, String>();
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			SingleLine = scanner.nextLine();
			SingleLineVector = SingleLine.split(" ");
			for (int i = 0; i < SingleLineVector.length; i++) {
				System.out.print(SingleLineVector[i] + "\t");
			}
			for (int i = 1; i < SingleLineVector.length; i++) { //0 Ã¤r dir
				LineVectorVector = SingleLineVector[i].split("=");
				innerValues.put(LineVectorVector[0], LineVectorVector[1]);
			}
			values.put(SingleLineVector[0], new HashMap<String, String>(innerValues));
			innerValues.clear();
		}
		return values;
	}
	
	public String evaluate_encrypted_disk_info(HashMap<String, HashMap<String, String>> customerInfo) {
		
		Set<String> keys = customerInfo.keySet();
		Iterator keysItr = keys.iterator();
		
		while (keysItr.hasNext()) {
			String key = (String) keysItr.next();
			System.out.print("key: " + key + ", ");
			Set<String> keysKeys = customerInfo.get(key).keySet();
			Iterator keysKeysItr = keysKeys.iterator();
			
			while (keysKeysItr.hasNext()) {
				String keyKey = (String) keysKeysItr.next();
				System.out.print("keyKey: " + keyKey + ": ");
				String value = customerInfo.get(key).get(keyKey);
				
				System.out.println(value);
			}
		}
		
		
		return "";
	}
	
	public HashMap<String, String> read_environment_info(File customerFile) {
		
		HashMap<String, String> values = new HashMap<String, String>(); 
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String nextLine = scanner.nextLine();
			String[] innerValues = nextLine.split("=");
			if (innerValues[0].equals("LS_COLORS")) continue; //Blir svårare att parsea, tror inte det har något med säkerhet att göra så låter bli att läsa in
			values.put(innerValues[0], innerValues[1]);
		}
		
		return values;
		
	}
	
	public String evaluate_environment_info(HashMap<String, String> customerInfo) {
		String returnString = "";
		Iterator itr = customerInfo.keySet().iterator();
		
		while (itr.hasNext()) {
			String key = (String) itr.next();
			
			System.out.println(key + " = " + customerInfo.get(key));
		}
		
		return returnString;
	}
	
	public HashMap<String, String[]> read_processes_info(File customerFile) {
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String[] nextValue = new String[11];
			for (int i = 0; i < nextValue.length - 1; i++) {
				nextValue[i] = scanner.next();
			}
			nextValue[10] = scanner.nextLine();
			values.put(nextValue[1], nextValue);
		}
		
		return values;
	}
	
}
