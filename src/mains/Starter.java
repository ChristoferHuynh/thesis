package mains;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import parsers.RJParser;

public class Starter {

	public static void main(String[] args) {
		
		HashMap<String, String[]> processes = new HashMap<String, String[]>();

		// Should be decided when running the file.
		File resultDir;
		String hostname;
		String test_name;
		String resultPath;
		
		File processesFile;
		
		hostname = "jesper-Aspire-E5-571";
		test_name = "encrypted_disk_info";		
		resultPath = "C:\\\\Users\\\\Jesper\\\\Downloads\\\\result\\\\result\\\\";
		resultDir = new File("C:\\\\Users\\\\Jesper\\\\Downloads\\\\result\\\\result");
		
		processesFile = new File(resultPath
				+ test_name + "\\\\" + hostname + ".log");
		
		
		RJParser rjParser = new RJParser();
		

		try {
			Method method = rjParser.getClass().getMethod("read_" + test_name, File.class);
			processes = (HashMap<String, String[]>) method.invoke(rjParser, processesFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		
//		processes = rjParser.readProcesses(processesFile);
		
		System.out.println(processes.get("21"));
		String[] processVector = processes.get("21");
		for (int i = 0; i < processVector.length; i++) {
			System.out.print(processVector[i] + "\t");
		}
		System.out.println("\nDONE");
		
		
		
	}
}
