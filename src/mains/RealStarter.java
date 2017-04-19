package mains;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import parsers.LynisParser;
import parsers.RJParser;
import parsers.UnixParser;

public class RealStarter {

	public static void main(String[] args) {
		
		//Initiate Parsers
		RJParser rjParser = new RJParser();
		LynisParser lynisParser = new LynisParser();
		UnixParser unixParser = new UnixParser();
		
		//Initate Files
		File rjResults;
		File[] rjFiles;
		File lynisFile;
		File unixFile;
		
		String finalString = "";
		String rjResultPath;
		
		//remote job
		rjResults = new File("/home/jesper/Documents/remote_job_linux_osx-master-d98598cf1ea7ba905e77c62efb3b52a4bcacf366/result");
		rjFiles = rjResults.listFiles();
		
		
		finalString = finalString.concat("\n\n\n\n ####Remote Job#### \n\n");

		
		for (File file : rjFiles) {
			if (file.listFiles().length == 0) continue;
			try {
				Method readMethod = rjParser.getClass().getMethod("read_" + file.getName(), File.class);
				Class<?> returnType = readMethod.getReturnType();
				
				Method evaluateMethod = rjParser.getClass().getMethod("evaluate_" + file.getName(), returnType);
				
				Object obj = readMethod.invoke(rjParser, file.listFiles()[0]);
				
				finalString = finalString.concat("\n\n " + file.getName() + "\n\n" + evaluateMethod.invoke(rjParser, obj));
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Lynis
		
		lynisParser = new LynisParser();
		
		lynisFile = new File("/tmp/lynis.log");
		
		finalString = finalString.concat("\n\n\n #### Lynis \n\n\n");
				
		finalString = finalString.concat(lynisParser.evaluate(lynisParser.read(lynisFile)));
		
		// unix-privesc
		
		PrintWriter out;

		try {
			out = new PrintWriter("/home/jesper/Desktop/RJOutput.txt");
			System.out.println(finalString);
			out.println(finalString);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
