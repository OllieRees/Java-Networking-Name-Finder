import java.io.*; //contains Buffered Reader class
import java.net.*; //contains URL objects, etc.

//reads a webpage based on the email id, and prints out the name belonging to the email id
public class WebPageReader {
	
	protected String ID; //stores the email id
	protected String web_address; //stores the web address link
	protected String name; //stores the name of the email-holder
	
	public static void main(String[] args) {
		
		WebPageReader wbp = new WebPageReader();  //create a new class object
		wbp.read_ID(); //get the ID from the user
		wbp.set_webAddress("https://www.ecs.soton.ac.uk/people/"); //set the web address using the link provided and the ID state
		System.out.println(wbp.get_ID());  //return the emailID 
		wbp.find_keyword(wbp.get_webAddress(), "property=\"name\">", "</h1>"); //find the value of the property="name" key
		System.out.println(wbp.get_name()); //return name on console 
	}
	
	//reads the emailID from the user
	public void read_ID() { //possibly a once-use method, but better to abstract
		
		//finding the emailID via Buffer Reader
		BufferedReader in = new BufferedReader(new InputStreamReader((System.in))); //creates an object to better read input
		System.out.print("What's the ID: "); 
				
		//get the email ID. Have to try because it's scared that I may not use my OWN program correctly. 
		try {
			
			ID = in.readLine(); //user input for email id is stored in the field provided in the argument, in this case it will be email_id.
			in.close(); //closes input
	
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	//will construct the web page address based off the email id 
	public void set_webAddress(String link) {
		this.web_address = link + get_ID(); //uses the email ID to find the web address the user wants
	}
	
	//parses through the URL object and returns the line with the keyword in
	protected void find_keyword(String web_address, String keyword, String endingTag)  {
		
		try {
			URL web_address_url = new URL(web_address); //turn the web address link to a URL object
			BufferedReader read_URL = new BufferedReader(new InputStreamReader(web_address_url.openStream())); //read text returned from server (or read the URL)
			String keywordLine; //takes in the string of characters with property = "name"> <name> <
			
			//cycle through every line in the URL source code until the line with the keyword comes up
			while((keywordLine = read_URL.readLine()) != null) { //stores a line of the URL in keyword line every iteration
				if(keywordLine.contains(keyword)) { //break the while loop if the line with the keyword appears
					break;
				}
			}
			

			/* takes a keyword line and finds the value of the keyword	
			 * How can we make sure that any field can be used, instead of just name?
			 */
			this.name = keywordLine.substring(keywordLine.indexOf(keyword), keywordLine.indexOf(endingTag)); //contains the keyword + value
			this.name = this.name.replace(keyword, ""); //removing the keyword (the hash-key if you will)
			
			read_URL.close();

			
		} catch(MalformedURLException mue) {
			mue.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
	//returns the email ID - I could have not used this, but again abstraction and encapsulation. 
	protected String get_ID() {
		return this.ID;
	}
	
	//returns the web address
	protected String get_webAddress() {
		return this.web_address;
	}
	
	//returns the name of the email-holder
	protected String get_name() { 
		return this.name;
	}
}