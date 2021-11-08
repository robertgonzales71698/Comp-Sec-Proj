/*
 * 
 * @author Gabriela Fisher, Isaigh Pugh, & Robert Gonzales
 * @version Fall 2021
 * The University of Oklahoma CS 4173 - Computer Securtiy
 * 
 * 
 * 
 */
import java.lang.Object;
import java.lang.StringBuilder;

public class PhishingDetector
{

	public static String[] parseAddress(String emailAddress){
		int atIndex =  emailAddress.indexOf("@");
		int dotIndex = emailAddress.indexOf(".");
		String addressName = emailAddress.substring(0, atIndex);
		String addressWebName = emailAddress.substring(atIndex + 1, dotIndex);
		String addressDomain = emailAddress.substring(dotIndex + 1);
		String[] parsedEmailAddress = new String[3];
		parsedEmailAddress[0] = addressName;
		parsedEmailAddress[1] = addressWebName;
		parsedEmailAddress[2] = addressDomain;
		return parsedEmailAddress;
	}

	public static Boolean checkAddressName(String[] parsedAddress){
		if (parsedAddress[0].equals("example")){
			return true;
		}
		else {
			return false;
		}
	}

	public static Boolean checkDomain(String[] parsedAddress){
		if (parsedAddress[0].equals("com") || parsedAddress[1].equals("org") || parsedAddress[1].equals("edu") || parsedAddress[1].equals("gov")){
			return true;
		}
		else {
			return false;
		}
	}

	public static Boolean checkWebName(String[] parsedAddress){
		if (parsedAddress[1].equals("gmail") || parsedAddress[1].equals("ymail")){
			return true;
		}
		/*
		* Checks to see if .edu account, meaning more possible web names
		*/
		else if (checkDomain(parsedAddress) == true){
			return true;
		}
		else {
			return false;
		}
	}

	public static Boolean emailAddressVerifier(String[] parsedAddress){
		if ((checkAddressName(parsedAddress) == true) && (checkWebName(parsedAddress) == true)){
			return true;
		}
		else {
			return false;
		}
	}
     public static void main(String[] args) 
     {
          // Take in an email
		  String emailAddress = "example@gmail.com";
		  String subject = "Test Email";
		  String emailContents = "This is a test email. The email address, subject, and contents of this email are for the demo.";
		  String[] parsedEmailAddress = parseAddress(emailAddress);

		  // Parse sender address
		  System.out.println(emailAddressVerifier(parsedEmailAddress));

		  //TODO Run subject through checker

		  //TODO Run contents through grammer checker

		  //TODO Print results
     }
}