<p align="center">
  <b>Computer Security Project</b><br>
  <b>Gabriela Fisher - Isaigh Pugh - Robert Gonzales</b><br>
  <b>© 2021 Group 16</b><br>

# Table of Contents

    1 Introduction.................................................................1
      1.1 GitHub Repository Link...................................................1
      1.2 How to Run the Code......................................................1
    
## Introduction
This README file provides an overview of the project and how to run the project code. The neural network portion of the code uses the Encog library, which can be downloaded here: https://github.com/jeffheaton/encog-java-core

### GitHub Repository Link
https://github.com/robertgonzales71698/Comp-Sec-Proj

### How to Run the Code
To run phishing detector:
1) Download Visual Studio Code (https://code.visualstudio.com/)
2) Under the extensions tab in VSC, download the Java extension created by Red Hat
3) Open the PhishingDetector.java file located in the project directory
4) In the project directory, find the "Test Cases" folder and locate a test case file you would like to test
5) On line 194 in the PhishingDetector.java file, change the file path for the scanner to your desired test file (KEEP "Test Cases/" part, just change the file name after that)
6) Save the changes and run
7) To run the included saved neural network, run the PhishNetSaved file. You can also use the Encog Workbench, downloadable from the Encog link above, to run the results-copy.eg file used by this directly.
8) To view the probabilistic neural network, load the PB-copy.eg file in Encog Workbench.
9) If you want to make and evaluate a new network based on the results of the checker, run the checker to obtain a results.csv file and then run the NewNetwork.java file.

The output file with the results should appear in the project directory (default is "results.csv", if wanting to change output file name edit line 189 in the PhishingDetector.java file)
