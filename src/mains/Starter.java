package mains;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import parsers.LynisParser;
import parsers.RJParser;
import parsers.UnixParser;

public class Starter {

	public static void main(String[] args) {
		
		HashMap<String, String[]> processes = new HashMap<String, String[]>();
		//ArrayList<String> processes = new ArrayList<String>();

		// Should be decided when running the file.
		File resultDir;
		String hostname;
		String test_name;
		String resultPath;
		
		File processesFile;
		
		hostname = "jesper-Aspire-E5-571";
		test_name = "passwdpolicy_info";		

		/*///For Windows
		resultPath = "C:\\\\Users\\\\Jesper\\\\Downloads\\\\result\\\\result\\\\";
		//For Windows
	/*	resultPath = "C:\\\\Users\\\\Jesper\\\\Downloads\\\\result\\\\result\\\\";
		resultDir = new File("C:\\\\Users\\\\Jesper\\\\Downloads\\\\result\\\\result");
		processesFile = new File(resultPath
				+ test_name + "\\\\" + hostname + ".log");
		
	*/	
	/*	//For Linux (Jesper)
		resultPath = "/home/jesper/Documents/remote_job_linux_osx-master-d98598cf1ea7ba905e77c62efb3b52a4bcacf366/result";
		resultDir = new File(resultPath);
		processesFile = new File(resultPath
				+ "/" + hostname + ".log");*/
	
		//For Linux (Stoff)
			resultPath = "/home/stoff/Desktop/Thesis/result/";
				resultDir = new File(resultPath);
				processesFile = new File(resultPath
						+ test_name + "/" + hostname + ".log");

		RJParser rjParser = new RJParser();
		

		try {
		//	rjParser.getClass().getMethod("read_groups_info").getReturnType()
			Method method = rjParser.getClass().getMethod("read_" + test_name, File.class);
			processes = (HashMap<String, String[]>) method.invoke(rjParser, processesFile);
		//	processes = (ArrayList<String>) method.invoke(rjParser, processesFile);
			Method method2 = rjParser.getClass().getMethod("evaluate_" + test_name, method.getReturnType());
			System.out.println("\n");
			System.out.println(method2.invoke(rjParser, processes));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		LynisParser lynisParser = new LynisParser();
		
		File customerFile = new File("/tmp/lynis.log");
		HashMap<String, String[]> lynisMap = new HashMap<String, String[]>();
		lynisMap = lynisParser.read(customerFile);
		
		System.out.println("REEEEEEEEEEEEEE");
		lynisParser.print(lynisMap);
		System.out.println(lynisParser.print(lynisMap));
		*/
		
	/*	UnixParser unixParser = new UnixParser();
		File customerFile = new File("/home/jesper/Downloads/unix-privesc-check-1.4/outputstandard.txt");
		File customerFile2 = new File("/home/jesper/Downloads/unix-privesc-check-1.4/outputdetailed.txt");

		HashMap<String, String> warnings = new HashMap<String, String>();
		warnings = unixParser.read(customerFile);
		unixParser.print(warnings);
		System.out.println(unixParser.print(warnings));
		*/
		
		
		/*
		Set<String> keySet = processes.keySet();
		
		Iterator keySetItr = keySet.iterator();
		System.out.println("AHSDIAHSDIOSAHIDASD\nASHDASHIDOISADHIOAS");
		while (keySetItr.hasNext()) {
			String key = (String) keySetItr.next();
			System.out.println("\n" + key);
			Set<String> keyKeySet = processes.get(key).keySet();
			Iterator keySetItrItr = keyKeySet.iterator();
			
			while (keySetItrItr.hasNext()) {
				String keykey = (String) keySetItrItr.next();
				System.out.println("key: " + key + " keykey: " + keykey);
				System.out.print(keykey + " = ");
				System.out.print(processes.get(key).get(keykey));
				System.out.println();
			}
					
		}
		*/
		
/*		processes = rjParser.readProcesses(processesFile);
		System.out.println(processes.get("21"));
		String[] processVector = processes.get("21");
		for (int i = 0; i < processVector.length; i++) {
			System.out.print(processVector[i] + "\t");
		}
		System.out.println("\nDONE");
*/		
		
		System.out.println("DONEEE");
		
		
	}
	
	
}
