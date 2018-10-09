import java.io.*;
import java.net.*;

import javax.net.ssl.HttpsURLConnection;

/* Reads a name, searches on google for that name, and takes the first URL provided and returns the home page of the name searched. 
 * 
 * 
 */
public class GoogleNameSearch {
	
	protected String name; //name/ID of person being searched
	protected String googleSearch_webAddress;
	protected String homePage_webAddress;

	public static void main(String[] args) {
		
		GoogleNameSearch gns = new GoogleNameSearch("https://www.google.co.uk/search?q="); 
		System.out.println(gns.get_name()); 
		System.out.println(gns.get_googleSearch_webAddress());
		gns.findGoogleSearch_first(gns.get_googleSearch_webAddress());
		System.out.println(gns.get_homePage_webAddress());
	}
	
	/*use WebPageReader's method, because I can't be bothered to rewrite it.
	 * Will take a link and find the googleSearch_webAddress and name of the search.
	 */
	public GoogleNameSearch(String link) {
		WebPageReader wbp = new WebPageReader(); 
		wbp.read_ID();
		wbp.set_webAddress(link);
		this.name = wbp.get_ID();
		this.googleSearch_webAddress = wbp.get_webAddress();
		this.googleSearch_webAddress = this.googleSearch_webAddress.replace(' ', '+'); //replace all spaces (in the name) with crosses
	}
	
	//Return the first URL google provides 
	 public void findGoogleSearch_first(String googleSearch_address) {
		try {
			URL googleSearch_URL = new URL(googleSearch_address);
		 	BufferedReader gs_stream = new BufferedReader(new InputStreamReader(googleSearch_URL.openStream())); //this seems to be the 403 error source
		 	String keywordLine;
		 	
		 	//cycles through the URL stream until the line with search results is found.
		 	while((keywordLine = gs_stream.readLine()) != null) {
		 		if(keywordLine.contains("Search Results")) {
		 			break;
		 		}
		 	}
		 	
		 	gs_stream.close();
		 	
		 	this.homePage_webAddress = keywordLine.substring(keywordLine.indexOf("<a href=\""), keywordLine.indexOf("ping"));  //stores the first link from the line with Search Results on
		 	
		} catch(MalformedURLException mue) {
			mue.printStackTrace(); 
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
	} 
	
	//accessor method for name
	protected String get_name() {
		return this.name;
	}
	
	//accessor method for web address
	protected String get_googleSearch_webAddress() {
		return this.googleSearch_webAddress;
	}
	
	protected String get_homePage_webAddress() {
		return this.homePage_webAddress;
	}
}
