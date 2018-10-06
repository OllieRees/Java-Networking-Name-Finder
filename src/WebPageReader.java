import java.io.*; //contains Buffered Reader class
import java.net.*;

//reads a webpage based on the email id, and prints out the name belonging to the email id
public class WebPageReader {
	
	protected String email_id; //stores the email id
	protected String web_address; //stores the web address link
	protected String name;
	
	public static void main(String[] args) {
		WebPageReader wbp = new WebPageReader(); 
		System.out.println(wbp.get_emailID()); 
		wbp.parse_webAdress("property=\"name\">"); 
		System.out.println(wbp.get_name());
	}
	
	//will construct the web page address based off the email id 
	public WebPageReader() {
		this.read_emailID(); //reads the emailID of the user
		web_address = "https://www.ecs.soton.ac.uk/people/" + get_emailID(); //uses the email ID to find the web address the user wants
		
	}
	
	//reads the emailID from the user
	public void read_emailID() { //possibly a once-use method, but better to abstract
		
		//finding the emailID via Buffer Reader
		BufferedReader read_email_id = new BufferedReader(new InputStreamReader((System.in))); //creates an object to better read input
		System.out.print("What's the email ID: "); 
				
		//get the email ID. Have to try because it's scared that I may not use my OWN program correctly. 
		try {
			email_id = read_email_id.readLine(); //user input for email id is stored in email_id
			read_email_id.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	//parses through the URL object and returns the value of the keyword
	protected void parse_webAdress(String keyword) {
		try {
			URL web_address_url = new URL(web_address); //turn the web address link to a URL object
			BufferedReader read_URL = new BufferedReader(new InputStreamReader(web_address_url.openStream())); //read text returned from server (or read the URL)
			String keyword_line; //takes in the string of characters with property = "name"> <name> <
			
			//cycle through every line in the URL source code until the line with the keyword comes up
			while((keyword_line = read_URL.readLine()) != null) { //stores a line of the URL in keyword line every iteration
				if(keyword_line.contains(keyword)) { //break the while loop if the line with the keyword appears
					break;
				}
			}
			
			//finding the keyword value within the line
			name = keyword_line.substring(keyword_line.indexOf(keyword), keyword_line.indexOf("</h1>")); //contains the keyword + value
			name = name.replace(keyword, ""); //removing the keyword (the hash-key if you will)			
			
			read_URL.close();
			
		} catch (MalformedURLException mue) {
			mue.printStackTrace(); //print error 
		}
		  catch (IOException ioe) {
			  ioe.printStackTrace(); //print error
		  }
	}
	
	//returns the email ID - I could have not used this, but again abstraction and encapsulation. 
	protected String get_emailID() {
		return email_id;
	}
	
	//returns the web address
	protected String get_webAddress() {
		return web_address;
	}
	
	protected String get_name() { 
		return name;
	}
}