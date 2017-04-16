package mains;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import parsers.LynisParser;
import parsers.RJParser;
import parsers.UnixParser;

public class RJStarter {
	
	public static void main(String[] args) {
		System.out.println("I am a meme");
	}

	
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
	//"/home/jesper/Documents/remote_job_linux_osx-master-d98598cf1ea7ba905e77c62efb3b52a4bcacf366/result"
	String rjSavePath;
	///home/jesper/Desktop/RJOutput.txt
	
	public String test() {
		return "memes";
	}
		
	
	public void evaluate(String path, String save) {
		//remote job
		rjResultPath = path;
		rjSavePath = save;
		
		rjResults = new File(rjResultPath);
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
		
		PrintWriter out;

		try {
			out = new PrintWriter(rjSavePath);
			System.out.println(finalString);
			out.println(finalString);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
