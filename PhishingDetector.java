/*
 * 
 * @author Gabriela Fisher, Isaigh Pugh, Robert Gonzales
 * @version Fall 2021
 * The University of Oklahoma CS 4173/5173 - Computer Security
 * 
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


/*
* The phishing detector class serves as the java class to house the driver (main function) and all of the functions that are called in the driver
*/
public class PhishingDetector
{
	/*
	* Global variables declaration
	*/
	public static double subjectPercentageMisspelled; // Percentage of the email subject that is misspelled
	public static double contentsPercentageMisspelled; // Percentage of the email contents that are misspelled
	public static boolean addressResult; // The result of the email address verifier
	public static boolean subjectResult; // The result of the email subject verifier
	public static boolean contentsResult; // The result of the email contents verifier

	/*
	* Parse Address function takes the email address and splits it up into parts to be analyzied
	*/
	public static String[] parseAddress(String emailAddress){

		// Get the location of the @ and . characters in the email address
		int atIndex =  emailAddress.indexOf("@");
		int dotIndex = emailAddress.indexOf(".");

		// Obtain and store the email address's name, web name, and domain based on the character locations
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
		
		// If the email address name is a correctly spelled word
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
		if (parsedAddress[1].equals("com") || parsedAddress[1].equals("org") || parsedAddress[1].equals("edu") || parsedAddress[1].equals("gov")){
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
	public static Boolean checkWebName(String[] parsedAddress,ArrayList<String> providersDict){
		if (providersDict.contains(parsedAddress[1])){
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

	public static Boolean emailAddressVerifier(String[] parsedAddress, ArrayList<String> dict, ArrayList<String> providersDict)
	{
		if ((checkAddressName(parsedAddress, dict)) && (checkWebName(parsedAddress, providersDict))){
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
		else {
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


	/*
	* Final email verifier
	*/
	public static void saveResult(String filePath, String address, Boolean addressVerifier, String subject, Boolean subjectVerifier, String body, Boolean bodyVerifier, Double subjectPercent, Double bodyPercent, String actual){
		try {
			FileWriter outputFile = new FileWriter(filePath, true);
			BufferedWriter writer = new BufferedWriter(outputFile);
			PrintWriter pw = new PrintWriter(writer);

			pw.println(address + "," + addressVerifier + "," + subject + "," + subjectVerifier + "," + body + "," + bodyVerifier + "," + subjectPercent + "," + bodyPercent + "," + actual);
			pw.flush();
			pw.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	/*
	* Main function that serves as the driver
	*/
	public static void main(String[] args) throws FileNotFoundException {
		  
		// Create the word dictionary to spell check against
		Scanner s = new Scanner(new File("words_alpha.txt"));
		ArrayList<String> dictionary = new ArrayList<String>();
		while (s.hasNextLine()){
			dictionary.add(s.nextLine());
		}
		s.close();

		// Create the email provider dictionary to check verified email providers
		Scanner sc = new Scanner(new File("emailProviders_alpha.txt"));
		ArrayList<String> providersDict = new ArrayList<String>();
		while (sc.hasNextLine()){
			providersDict.add(sc.nextLine());
		}
		sc.close();

		String file = "results.csv";
		Scanner scnr = new Scanner(new File("Test Cases/some fake emails.txt"));
		String [] email = new String[4];
		while (scnr.hasNextLine()){
			email = scnr.nextLine().split("\\|");
		
			// Take in email components
			String emailAddress = email[0];
			String subject = email[1];
			String emailContents = email[2];
			String actual = email[3];

			// Split the email address parts up
			String[] parsedEmailAddress = parseAddress(emailAddress);

			// Run the parsed email address through checker
			addressResult = emailAddressVerifier(parsedEmailAddress, dictionary, providersDict);

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
		
			saveResult(file, emailAddress, addressResult, subject, subjectResult, emailContents, contentsResult, subjectPercentageMisspelled, contentsPercentageMisspelled, actual);	
		}
		scnr.close();
    }
}