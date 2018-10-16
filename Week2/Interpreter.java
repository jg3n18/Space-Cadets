import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Interpreter{
	
	private HashMap<String, Integer> variables = new HashMap<>();
	private HashMap<Integer, String> code = new HashMap<>();
	private HashMap<Integer, Integer> whiles = new HashMap<>();
	private int count = 0;
	private int lineNumber = 0;
	private int numberOfWhiles = 0;
	
	
	public static void main(String[] args) {
		
		Interpreter interpreter = new Interpreter("test.txt");
		
		System.gc();
		
		System.exit(0);
	}
	
	public Interpreter(String fileName) {
		readInitial(fileName);
	}
	
	private void codeCheck() {
		
		String line;
		String[] lineSplit;
		int whiles = 0;
		int ends = 0;
		
		for (lineNumber = 0; lineNumber < count; ++lineNumber) {
			
			line = code.get(lineNumber);
			lineSplit = line.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
				
			line = line.replaceAll("(^\\s+|\\s+$)", " ");
			
			if (!line.equals(" ")) {
				if(!lineSplit[lineSplit.length-1].endsWith(";")) {
					System.out.println("; is missing at the end of line " + (lineNumber+1));
					System.exit(0);
				}
				
				if (!lineSplit[0].equals("incr") && !lineSplit[0].equals("decr") && !lineSplit[0].equals("end;") 
					&& !lineSplit[0].equals("clear") && !lineSplit[0].equals("while")) {
					System.out.println("Syntax error in the line " + (lineNumber+1));
					System.exit(0);
					
				} else if ((lineSplit[0].equals("incr")|| lineSplit[0].equals("decr") || lineSplit[0].equals("clear")) && lineSplit.length > 2) {
					System.out.println("Syntax error in the line " + (lineNumber+1));
					System.exit(0);
					
				} else if (lineSplit[0].equals("while") || lineSplit[0].equals("end;")) {
					if (lineSplit[0].equals("while")){
						whiles++;
						lineSplit[4] = lineSplit[1].replace(lineSplit[1].substring(lineSplit[1].length()-1), "");
					 
						if (!lineSplit[2].equals("not") || lineSplit[4].equals("do") || lineSplit.length > 5) {
							System.out.println("Syntax error inthe line " + (lineNumber+1));
							System.exit(0);
						}
					}
					if (lineSplit[0].equals("end;")) {
						ends++;
						if (lineSplit[0].equals("end;") && lineSplit.length > 1){
							System.out.println("Syntax error inthe line " + (lineNumber+1));
							System.exit(0);
						}
					}
				}
			}
		}
		if (whiles != ends) {
			if (whiles > ends) {
				System.out.println("There are more while statements than end statements");
			}
			else {
				System.out.println("There are more end statements than while statements");
			}
			System.exit(0);
		}
	}
	
	private void readInitial(String nameOfTheFile) {
		
		try (BufferedReader br = new BufferedReader(new FileReader(nameOfTheFile))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       code.put(count, line);
		       count++;
		    }
		    codeCheck();
		    readLine();
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void readLine() {
		
		String line;
		String[] lineSplit;
		
		for (lineNumber = 0; lineNumber < count; ++lineNumber) {
			
			line = code.get(lineNumber);
			lineSplit = line.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");		
			
			if (lineSplit[0].equals("incr")) {
				lineSplit[1] = lineSplit[1].replace(lineSplit[1].substring(lineSplit[1].length()-1), "");
				incr(lineSplit[1]);
				
			} else if (lineSplit[0].equals("decr")) {
				lineSplit[1] = lineSplit[1].replace(lineSplit[1].substring(lineSplit[1].length()-1), "");
				decr(lineSplit[1]);
				
			} else if (lineSplit[0].equals("clear")) {
				lineSplit[1] = lineSplit[1].replace(lineSplit[1].substring(lineSplit[1].length()-1), "");
				clear(lineSplit[1]);
				
			} else if (lineSplit[0].equals("while") || lineSplit[0].equals("end;")) {
				loop(lineSplit, line);
				
			}			
		}
	}
	
	private void incr(String name) {
		if (variables.get(name) != null) {
			variables.put(name, variables.get(name) + 1);
			
			System.out.println(name + " " + variables.get(name));
			
		} else {
			System.out.println("Error: Variable " + name + " doesn't exist");
			System.exit(0);
		}
	}
	
	private void decr(String name) {
		if (variables.get(name) != null && variables.get(name) != 0) {
			variables.put(name, variables.get(name) - 1);
			
			System.out.println(name + " " + variables.get(name));
			
		}else if (variables.get(name) != null && variables.get(name) == 0) {
			System.out.println("Error: Variable " + name + " cannot be negative");
			System.exit(0);
			
		} else {
			System.out.println("Error: Variable " + name + " doesn't exist");
			System.exit(0);
		}
	}
	
	private void clear(String name) {
		variables.put(name, 0);
		
		System.out.println(name + " " + variables.get(name));
	}
	
	private void loop(String[] lineSplit, String line) {
		if (lineSplit[0].equals("while")) {
			numberOfWhiles++;
			whiles.put(numberOfWhiles, lineNumber);

			int condition = Integer.parseInt(lineSplit[3]);
			if (variables.get(lineSplit[1]) != null) {
				if (variables.get(lineSplit[1]) == condition) {
					while (!lineSplit[0].equals("end;")){
						lineNumber++;
						line = code.get(lineNumber);
						lineSplit = line.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
					}
				}
			}
			
		} else {
			line = code.get(whiles.get(numberOfWhiles));
			lineSplit = line.replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
			int condition = Integer.parseInt(lineSplit[3]);
			
			if (variables.get(lineSplit[1]) != condition)
				lineNumber = whiles.get(numberOfWhiles);
			else {
				numberOfWhiles--;
			}
				
		}
	}

}
