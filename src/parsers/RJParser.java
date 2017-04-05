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
			String nextKey = scanner.next(); // key = value i loggsen.. beh�ver nog g�ras p� annorlunda s�tt men du fattar
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
	
	public HashMap<String, String[]> read_cron_at_info(File customerFile) { //Unsure how to parse, how does it look when directory exists?
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 
		int id = 1;
		String PID = Integer.toString(id);
		String[] files = {"null", "/etc/cron.allow", "/etc/at.allow"};
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
			if (nextLine.contains("No such file or directory"))) {
				currFile = files[++fileIndex];
				values.put(currFile, "No such file or directory");
			}
			if (nextLine.contains("total")) currFile = files[++fileIndex];
			String[9] nextValue = nextLine.split(" ");
			values.put(currFile.concat(nextValue[9]), nextValue);
			id++;
		}
		
		return values;
	}

	public String evaluate_cron_at_info(HashMap<String, String[]> customerInfo) {
		System.out.println(customerInfo.get("2")[0]);
		if (customerInfo.get("1")[0].equals("ls: cannot access '/etc/cron.allow': No such file or directory")) { 
			//Anyone not in cron.deny can submit crontab files.
			return "Cannot access file";
			
		}
		return "HAX";
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
	
	public HashMap<String, String[]> read_diskvolume_info(File customerFile) {
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 
		
		int ID = 1;
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String[] nextValue = new String[6];
			System.out.println("\nb44");
			for (int i = 0; i < nextValue.length - 1; i++) {
				nextValue[i] = scanner.next();
				System.out.print(nextValue[i] + "\t");
			}
			nextValue[nextValue.length - 1] = scanner.nextLine();
			values.put(Integer.toString(ID), nextValue);
			ID++;
		}
		
		return values;
	}

	public String evaluate_diskvolume_info(HashMap<String, String[]> customerInfo) {
		
		
		
		return "";
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
			for (int i = 1; i < SingleLineVector.length; i++) { //0 är dir
				LineVectorVector = SingleLineVector[i].split("=");
				innerValues.put(LineVectorVector[0], LineVectorVector[1]);
			}
			values.put(SingleLineVector[0], new HashMap<String, String>(innerValues));
			innerValues.clear();
		}
		return values;
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

	public HashMap<String, String> read_environment_info(File customerFile) {
		
		HashMap<String, String> values = new HashMap<String, String>(); 
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String[] innerValues = scanner.nextLine().split("=");
			values.put(innerValues[0], innerValues[1]);
		}
		
		return values;
		
	}
	
}
