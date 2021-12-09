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
	public static double bodyPercentageMisspelled; // Percentage of the email body that are misspelled
	public static boolean addressResult; // The result of the email address verifier
	public static boolean subjectResult; // The result of the email subject verifier
	public static boolean bodyResult; // The result of the email body verifier
	public static boolean urlResult; // the result of the url checker
	public static int numSus; //The result of the suspicious words checker

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

		// Store the components of the email together and return the joint data
		String[] parsedEmailAddress = new String[3];
		parsedEmailAddress[0] = addressName;
		parsedEmailAddress[1] = addressWebName;
		parsedEmailAddress[2] = addressDomain;
		return parsedEmailAddress;
	}

	//Function to check if the e-mail contains a URL
	public static Boolean checkURL(String[] text){
		for(int i = 0; i < text.length; i++){
			if(text[i].contains("www")){
				return true;
			}
		}
		return false;
	}

	//function to check for the presence of suspicious words in the body
	public static int susWords(String[] text){
		int temp = 0;
		for(int i = 0; i < text.length; i++){
			if(text[i].equals("account")||text[i].equals("password")){
				temp++;
			}
		}
		return temp;
	}

	/*
	* Check Domain function checks the domain of the email address to see if it is a verified web domain
	*/
	public static Boolean checkDomain(String[] parsedAddress){

		// Checks to see if the email address domain is one of the following: .com, .org, .edu, or .gov
		if (parsedAddress[1].equals("com") || parsedAddress[1].equals("org") || parsedAddress[1].equals("edu") || parsedAddress[1].equals("gov")){
			return true;
		}
		else {
			return false;
		}
	}

	/*
	* Check web name function checks the web name of the email address to see if it is a verified web name
	*/
	public static Boolean checkWebName(String[] parsedAddress,ArrayList<String> providersDict){
		
		// Checks to see if the web name is in the list of verified providers
		if (providersDict.contains(parsedAddress[1])){
			return true;
		}
		// Checks to see if the domain is verified, meaning more verifiable web names are possible
		else if (checkDomain(parsedAddress)){
			return true;
		}
		else {
			return false;
		}
	}

	/*
	* The email address verifier function drives the functions that check to verify the email address
	*/
	public static Boolean emailAddressVerifier(String[] parsedAddress, ArrayList<String> providersDict)
	{
		// Checks to see if the web name is verifiable
		if (checkWebName(parsedAddress, providersDict)){
			return true;
		}
		return false;
	}

	/*
	* The spell checker function takes the text and check to see if each word is listed in the dictionary list
	*/
	public static double spellchecker(String[] text, ArrayList<String> dict)
	{
		double misspelledCount = 0; // Keeps track of the number of mispelled words in the text

		// For all of the words in the text, if a word is not listed in the dictionary then it is considered misspelled
		for (int i = 0; i < text.length; i++){
			if (!dict.contains(text[i])){
				misspelledCount++;
			}
		}

		// Get the percentage of the text that is misspelled and return it
		double percentageMisspelled = (misspelledCount / text.length);
		percentageMisspelled = percentageMisspelled * 100;
		return percentageMisspelled;
	}

	/*
	* The email subject verifier function drives the functions that check to verify the email address
	*/
	public static Boolean emailSubjectVerifier(String[] splitSubject, ArrayList<String> dict){

		// If 25% or less of the email subject is misspelled, then return that the subject is validated
		if (spellchecker(splitSubject, dict) <= 25.0){
			return true;
		}
		else {
			return false;
		}
	}

	/*
	* The email subject verifier function drives the functions that check to verify the email address
	*/
	public static Boolean emailBodyVerifier(String[] splitBody, ArrayList<String> dict){

		// If 35% or less of the email body is misspelled, then return that the body is validated
		if (spellchecker(splitBody, dict) <= 35.0){
			return true;
		}
		else{
			return false;
		}
	}


	/*
	* The save result function takes all of the data points and inputs collected and saves them in a formatted csv file
	*/
	public static void saveResult(String filePath, Boolean addressVerifier, Boolean subjectVerifier, Boolean bodyVerifier, Double subjectPercent, Double bodyPercent, Boolean urlResult, int numSus, String actual){
		try {
			// Setup the file variables/writers
			FileWriter outputFile = new FileWriter(filePath, true);
			BufferedWriter writer = new BufferedWriter(outputFile);
			PrintWriter pw = new PrintWriter(writer);

			// Write the data out in a csv format, and then close the writer
			pw.println(addressVerifier + "," + subjectVerifier +  "," + bodyVerifier + "," + subjectPercent + "," + bodyPercent + "," + urlResult + "," + numSus + "," + actual);
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
		Scanner s = new Scanner(new File("Dictionary and Helpers/words_alpha.txt"));
		ArrayList<String> dictionary = new ArrayList<String>();
		while (s.hasNextLine()){
			dictionary.add(s.nextLine());
		}
		s.close();

		// Create the email provider dictionary to check verified email providers
		Scanner sc = new Scanner(new File("Dictionary and Helpers/emailProviders_alpha.txt"));
		ArrayList<String> providersDict = new ArrayList<String>();
		while (sc.hasNextLine()){
			providersDict.add(sc.nextLine());
		}
		sc.close();

		// Create the output file name
		String file = "results.csv";

		/***************************************************
		* Change this to the desired test file for testing *
		***************************************************/
		Scanner scnr = new Scanner(new File("Test Cases/some fake emails.txt"));

		// Create the storage array and read in the input file with the emails
		String [] email = new String[4];
		while (scnr.hasNextLine()){

			// Split the input file up on each line by the pipe character "|" that differentiates the different components of each email
			email = scnr.nextLine().split("\\|");
		
			// Take in email components
			String emailAddress = email[0];
			String subject = email[1];
			String emailBody = email[2];
			String actual = email[3];

			// Split the email address parts up
			String[] parsedEmailAddress = parseAddress(emailAddress);

			// Run the parsed email address through checker
			addressResult = emailAddressVerifier(parsedEmailAddress, providersDict);

			// Remove commas and periods, then split the email subject into Array List of individual words
			subject = subject.replace(",", "");
			subject = subject.replace(".", "");
			subject = subject.toLowerCase();
			String[] splitSubject = subject.split(" ");

			// Run subject through checker
			subjectResult = emailSubjectVerifier(splitSubject, dictionary);

			// Remove commas and periods, then split the email body into Array List of individual words
			emailBody = emailBody.replace(",", "");
			emailBody = emailBody.replace(".", "");
			emailBody = emailBody.toLowerCase();
			String[] splitBody = emailBody.split(" ");

			// Run email body through checker
			bodyResult = emailBodyVerifier(splitBody, dictionary);
			urlResult = checkURL(splitBody);
			numSus = susWords(splitBody);

			// Obtain percentages
			subjectPercentageMisspelled = spellchecker(splitSubject, dictionary);
			bodyPercentageMisspelled = spellchecker(splitBody, dictionary);
		
			// Compile all of the data collected/calculated and save it to the output file
			saveResult(file, addressResult, subjectResult, bodyResult, subjectPercentageMisspelled, bodyPercentageMisspelled, urlResult, numSus, actual);	
		}
		scnr.close();
    }
}
