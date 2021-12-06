/*
 * 
 * @author Gabriela Fisher, Isaigh Pugh, & Robert Gonzales
 * @version Fall 2021
 * The University of Oklahoma CS 4173 - Computer Securtiy
 * 
 * 
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Object;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Scanner;



public class PhishingDetector
{
	public static double subjectPercentageMisspelled;
	public static double contentsPercentageMisspelled;
	public static boolean addressResult;
	public static boolean subjectResult;
	public static boolean contentsResult;

	/*
	* Parse Address function takes the email address and splits it up into parts to be analyzied
	*/
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

	/*
	* Check Address Name function checks the email address name to see if spelled correctly
	*/
	public static Boolean checkAddressName(String[] parsedAddress, ArrayList<String> dict){
		if (dict.contains(parsedAddress[0])){
			return true;
		}
		else {
			return false;
		}
	}

	/*
	* Check Domain function checks the domain of the email addressto see if:
	* 1 - The domain matches any of the verified domains in the verified list
	*/
	public static Boolean checkDomain(String[] parsedAddress){
		if (parsedAddress[0].equals("com") || parsedAddress[1].equals("org") || parsedAddress[1].equals("edu") || parsedAddress[1].equals("gov")){
			return true;
		}
		else {
			return false;
		}
	}

	/*
	* Check Domain function checks the domain of the email addressto see if:
	* 1 - The domain matches any of the verified domains in the verified list
	*/
	public static Boolean checkWebName(String[] parsedAddress){
		if (parsedAddress[1].equals("gmail") || parsedAddress[1].equals("ymail") || parsedAddress[1].equals("yahoo") || parsedAddress[1].equals("hotmail")){
			return true;
		}

		/*
		* Checks to see if .edu account, meaning more possible web names
		*/
		else if (checkDomain(parsedAddress)){
			return true;
		}

		else {
			return false;
		}
	}

	public static Boolean emailAddressVerifier(String[] parsedAddress, ArrayList<String> dict)
	{
		if ((checkAddressName(parsedAddress, dict)) && (checkWebName(parsedAddress))){
			return true;
		}
		
		return false;
	}


	public static double spellchecker(String[] text, ArrayList<String> dict)
	{
		double misspelledCount = 0;

		for (int i = 0; i < text.length; i++){
			if (!dict.contains(text[i])){
				misspelledCount++;
			}
		}

		double percentageMisspelled = (misspelledCount / text.length);
		percentageMisspelled = percentageMisspelled * 100;

		return percentageMisspelled;
	}

	public static Boolean emailSubjectVerifier(String[] splitSubject, ArrayList<String> dict){
		if (spellchecker(splitSubject, dict) <= 25.0){
			return true;
		}
		else{
			return false;
		}
	}

	public static Boolean emailContentsVerifier(String[] splitContents, ArrayList<String> dict){
		if (spellchecker(splitContents, dict) <= 35.0){
			return true;
		}
		else{
			return false;
		}
	}



	public static Boolean emailVerifier(Boolean addressVerifier, Boolean subjectVerifier, Boolean contentsVerifier){
		if ((addressVerifier) && (subjectVerifier) && (contentsVerifier)){
			return true;
		}
		else if ((!addressVerifier) && (subjectVerifier) && (contentsVerifier)){
			return true;
		}
		else if ((addressVerifier) && (!subjectVerifier) && (contentsVerifier)){
			return true;
		}
		else if ((addressVerifier) && (subjectVerifier) && (!contentsVerifier)){
			return true;
		}
		else{
			return false;
		}
	}
     public static void main(String[] args) throws FileNotFoundException {
          
		// Take in email components
		String emailAddress = "example@gmail.com";
		String subject = "Test Email";
		String emailContents = "This is a test email. The email address, subject, and contents of this email are for the demo.";
		  
		  
		// Create the dictionary to spell check against
		Scanner s = new Scanner(new File("words_alpha.txt"));
		ArrayList<String> dictionary = new ArrayList<String>();
		while (s.hasNextLine()){
			dictionary.add(s.nextLine());
		}
		s.close();

		// Split the email address parts up
		String[] parsedEmailAddress = parseAddress(emailAddress);

		// Run the parsed email address through checker
		addressResult = emailAddressVerifier(parsedEmailAddress, dictionary);

		// Remove commas and periods, then split the email subject into Array List of individual words
		subject = subject.replace(",", "");
		subject = subject.replace(".", "");
		subject = subject.toLowerCase();
		String[] splitSubject = subject.split(" ");

		// Run subject through checker
		subjectResult = emailSubjectVerifier(splitSubject, dictionary);

		// Remove commas and periods, then split the email contents into Array List of individual words
		emailContents = emailContents.replace(",", "");
		emailContents = emailContents.replace(".", "");
		emailContents = emailContents.toLowerCase();
		String[] splitContents = emailContents.split(" ");

		// Run email contents through checker
		contentsResult = emailContentsVerifier(splitContents, dictionary);

		// Obtain percentages
		subjectPercentageMisspelled = spellchecker(splitSubject, dictionary);
		contentsPercentageMisspelled = spellchecker(splitContents, dictionary);

		// Print results
		if (emailVerifier(addressResult, subjectResult, contentsResult)){
			System.out.println("All tests have passed: Email is verified");
		}
		else {
			System.out.println("Not all tests have passed: Email is unverified");
		}
     }
}