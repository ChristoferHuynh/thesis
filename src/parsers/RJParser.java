package parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
			if (nextLine.startsWith("#") || nextLine.equals("")) System.out.println("inside");
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
			for (int i = 1; i < SingleLineVector.length; i++) { //0 är dir
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
			if (innerValues[0].equals("LS_COLORS")) continue; //Blir sv�rare att parsea, tror inte det har n�got med s�kerhet att g�ra s� l�ter bli att l�sa in
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
	
	public HashMap<String, String[]> read_firewall_info(File customerFile) {
	HashMap<String, String[]> values = new HashMap<String, String[]>(); 
	
	String chain;
	String policy;
	String[] innerValues = new String[6];
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String next = scanner.next();
			if (next.equals("Chain")) {
				innerValues = scanner.nextLine().split(" ");
				chain = innerValues[1];
				policy = innerValues[3].substring(0, innerValues[3].length()-1);
				System.out.println(chain + " : " + policy);
				scanner.nextLine();
				values.put(chain, new String[]{policy});
			} else {
				innerValues[0] = next;
			
			
			for (int i = 1; i < innerValues.length - 1; i++) {
				innerValues[i] = scanner.next();
				System.out.print(innerValues[i] + "\t");
			}
			innerValues[5] = scanner.nextLine();
			System.out.println(innerValues[5]);
			}
		}
		
		return values;
	}
	
	public String evaluate_firewall_info(HashMap<String, String[]> customerInfo) { //Typ klar? Kanske kolla om specifika IP addresser �r blockade?
		int policy = 000;
		String returnString = "";
		if (customerInfo.get("INPUT")[0].equals("ACCEPT"))	policy += 1;
		if (customerInfo.get("FORWARD")[0].equals("ACCEPT"))policy += 10;
		if (customerInfo.get("OUTPUT")[0].equals("ACCEPT"))	policy += 100;

		if (policy < 100) {
			System.out.println(policy);
			returnString = returnString.concat("Warning: There is no firewall set up for incoming traffic.\n");
			policy -= 100;
		} 
		if (policy < 10) {
			System.out.println(policy);
			returnString = returnString.concat("Warning: There is no firewall set up for forwarding traffic.\n");
			policy -= 10;
		}
		if (policy < 1) {
			System.out.println(policy);
			returnString = returnString.concat("Warning: There is no firewall set up for outgoing traffic.\n");
			policy -= 1;
		}
		
		return returnString;
	}
	
	public HashMap<String, String[]> read_groups_info(File customerFile) {
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String[] innerValues = scanner.nextLine().split(":");
			values.put(innerValues[0], innerValues);
		}
		
		return values;
	}
	
	public String evaluate_groups_info(HashMap<String, String[]> customerInfo) {
		String returnString = "";
		
		try {
			String[] rootMembers = customerInfo.get("root")[3].split(",");
			for (int i = 0; i < rootMembers.length; i++) {
				System.out.println(rootMembers[i]);
				returnString.concat("Warning! The user " + rootMembers[i] 
			  					+ " has access to root");
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			System.out.println("no users have access to root");
		}
		

		
		return returnString;
		
	}
	
	public HashMap<String, String[]> read_lastlog_info(File customerFiler) {
		//IDK
		return null;
	}
	
	public String evaluate_lastlog_info(HashMap<String, String[]> customerInfo) {
		//IDK
		return null;
	}
	
	public HashMap<String, String[]> read_modprobe_info(File customerFile) {
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 
		String nextLine;
		String modprobe = "";
		String[] innerValues;
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			nextLine = scanner.nextLine();
			if (nextLine.contains("Module")) break;
			modprobe = modprobe.concat("%" + nextLine);
		}
		values.put("modprobe.d", modprobe.split("%"));
		while (scanner.hasNext()) {
			nextLine = scanner.nextLine();
			innerValues = nextLine.split(" ");
			values.put(innerValues[0], innerValues);
		}
		
		return values;
	}
	
	public String evaluate_modprobe_info(HashMap<String, String[]> customerInfo) {
		//IDK
		return null;
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
	
	public HashMap<String, String> read_sshd_info(File customerFile) {
		HashMap<String, String> values = new HashMap<String, String>(); 
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()){
			
			String nextKey = scanner.next();
			
			if (nextKey.contains("#")){
				scanner.nextLine();
				continue;
			}
			
			String nextValue = scanner.next();
			values.put(nextKey, nextValue);
		}	
		
	/*	for (String name: values.keySet()){
            String key = name.toString();
            System.out.print("\n" + key + "::" + values.get(key));

		}*/
		
		return values;
	}
	
	public String evaluate_sshd_info(HashMap<String, String[]> customerInfo) { //Typ klar? Kanske kolla om specifika IP addresser �r blockade?
		
		String returnString = "";
		return returnString;
	}
	
	public HashMap<String, String[]> read_startup_info(File customerFile) {
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < 2; i++){
		scanner.nextLine();
		}
		
			while (scanner.hasNext()) {
			String[] innerValues = new String[9];
			
				for (int i=0; i<innerValues.length; i++){
					innerValues[i] = scanner.next();
				}
				
				values.put(innerValues[8], innerValues);
			}
	/*	for (String name: values.keySet()){
            String key = name.toString();
            System.out.print("\n");
            String[] value = values.get(name);
            for (int i = 1; i < value.length; i++){
              System.out.print(value[i] + " ");  
            }

		}*/
		
		return values;
		
	}
	
	public String evaluate_startup_info(HashMap<String, String[]> customerInfo) { //Typ klar? Kanske kolla om specifika IP addresser �r blockade?
		
		String returnString = "";
		return returnString;
	}
	
	public ArrayList<String> read_sudoers_info(File customerFile) {
		ArrayList<String> values = new ArrayList<String>();
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String innerValues = scanner.nextLine();
			values.add(innerValues);
		}
		
		
		/*for (String name: values){

            String key = name.toString();
            System.out.print("\n"+ key);
          }*/
	
    	
		return values;
		
	}

	
	public String evaluate_sudoers_info(ArrayList<String> customerInfo) { //Typ klar? Kanske kolla om specifika IP addresser �r blockade?
		
		String returnString = "";
		
		if (customerInfo.size() > 20){
			returnString = "The system may have other users with unrestricted privileges.";
		}
		
		return returnString;
	}
	
	public ArrayList<String> read_suid_files_info(File customerFile) {
		ArrayList<String> values = new ArrayList<String>();
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String innerValues = scanner.nextLine();
			values.add(innerValues);
		}
		
		/*for (String name: values){

            String key = name.toString();
            System.out.print("\n"+ key);
          }*/
	
    	
		return values;
		
	}

	
	public String evaluate_suid_files_info(ArrayList<String> customerInfo) { //Typ klar? Kanske kolla om specifika IP addresser �r blockade?
		
		String returnString = "";
		return returnString;
	}
	
	public HashMap<String, String[]> read_system_info(File customerFile) {
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String[] innerValues = scanner.nextLine().split(" ");
			values.put(innerValues[0], innerValues);
		}
		
		/*for (String name: values.keySet()){

            String key = name.toString();
            System.out.print("\n"+ key);
            String[] value = values.get(name);
            for (int i = 1; i < value.length; i++){
              System.out.print(" " + value[i]);  
            }

		}*/
    	
		return values;
		
	}
	
	public String evaluate_system_info(HashMap<String, String[]> customerInfo) { //Typ klar? Kanske kolla om specifika IP addresser �r blockade?
		
		String returnString = "";
		return returnString;
	}
	
	public HashMap<String, String[]> read_users_info(File customerFile) {
		HashMap<String, String[]> values = new HashMap<String, String[]>(); 
		
		try {
			scanner = new Scanner(customerFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()) {
			String[] innerValues = scanner.nextLine().split(":");
			values.put(innerValues[0], innerValues);
		}
		
		/*for (String name: values.keySet()){

            String key = name.toString();
            System.out.print("\n"+ 
            key);
            String[] value = values.get(name);
            for (int i = 1; i < value.length; i++){
              System.out.print(" " + value[i]);  
            }

		} */
    	
		return values;
		
	}
	
	public String evaluate_users_info(HashMap<String, String[]> customerInfo) { //Typ klar? Kanske kolla om specifika IP addresser �r blockade?
		
		String returnString = "";
		return returnString;
	}
	
	
}

	

