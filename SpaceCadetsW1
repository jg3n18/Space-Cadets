import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class SpaceCadetsW1 {
	
	public static void main(String[] args) {
		
		//System.getProperties().put("proxySet", "true");
		//System.getProperties().put("proxyHost", "152.78.128.51");
		//System.getProperties().put("proxyPort", "3128");
		
		Scanner keyboard = new Scanner(System.in); 
		String username;
		username = keyboard.nextLine();
		keyboard.close();
		
		String urlString = "https://www.ecs.soton.ac.uk/people/" + username;
		
		try {
			URL url = new URL(urlString);

			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String inputLine;
			String name;
			String propertyName = "property=\"name\"";
			
	        while ((inputLine = reader.readLine()) != null) {	
		        if (inputLine.toLowerCase().contains(propertyName.toLowerCase())) {
		            int startIndex = inputLine.indexOf("\"name\"")+7;
		            int endIndex = inputLine.indexOf("</h1>");
		            
		            name = inputLine.substring(startIndex,endIndex);
		            System.out.println(name);
		            break;
		        }
	        }

	        reader.close();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
