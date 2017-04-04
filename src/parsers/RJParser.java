package parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

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
			String nextKey = scanner.next(); // key = value i loggsen.. behöver nog göras på annorlunda sätt men du fattar
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
	
	public HashMap<String, String[]> read_cron_at_info(File customerFile) { //Unsure how to parse, how does it look when directory exists?
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 
		int id = 1;
		String PID = Integer.toString(id);
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String[] nextValue = new String[1];
			nextValue[0] = scanner.nextLine();
			PID = Integer.toString(id);			
			values.put(PID, nextValue);
			id++;
		}
		
		return values;
	}

	
	public HashMap<String, String[]> read_crontab_info(File customerFile) { //Unsure how to parse, how does it look when they exist?
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 
		
		int id = 1;
		String PID = Integer.toString(id);
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String[] nextValue = new String[4];
			for (int i = 0; i < nextValue.length; i++) {
				nextValue[i] = scanner.next();
			}
			nextValue[10] = scanner.nextLine();
			
			PID = Integer.toString(id);			
			values.put(PID, nextValue);
			id++;
		}
		
		return values;
	}
	
	public HashMap<String, String[]> read_disvolume_info(File customerFile) {
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String[] nextValue = new String[6];
			for (int i = 0; i < nextValue.length - 1; i++) {
				nextValue[i] = scanner.next();
			}
			nextValue[10] = scanner.nextLine();
			values.put(nextValue[1], nextValue);
		}
		
		return values;
	}

	
	public HashMap<String, String[]> read_encrypted_disk_info(File customerFile) {
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 
		String PID;
		String PTUUID;
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			PID = scanner.next();
			PTUUID = scanner.findInLine("PTUasdasd");
			System.out.println(PID + "\t" + PTUUID);
		}
		
		return values;
	}
	
}
