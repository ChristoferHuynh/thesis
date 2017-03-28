package parsers;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Parser {
	

	Scanner scanner;
	

	public abstract HashMap<String, String> read(File file);
	
	
	public abstract boolean compare();
}
