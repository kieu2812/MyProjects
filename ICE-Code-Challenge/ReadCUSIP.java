package com.ice.test;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
/* **********
 * Author : KIEU NGUYEN
 * Date: 2/19/2018
 * **********
 * /
/**********************************************
 * Problem 1:
You are given a file formatted like this:

CUSIP
Price
Price
Price
…
CUSIP
Price
Price
CUSIP
Price
Price
Price
…
Price
CUSIP
Price
…

Think of it as a file of price ticks for a set of bonds identified by their CUSIPs.
You can assume a CUSIP is just an 8-character alphanumeric string.
Each CUSIP may have any number of prices (e.g., 95.752, 101.255) following it in
sequence, one per line.
The prices can be considered to be ordered by time in ascending order, earliest to latest.

Write me a Java program that will print the closing (or latest) price for each CUSIP in the file.
DO NOT assume the entire file can fit in memory!

 * 
 * 
 * ********************************************/
public class ReadCUSIP {

	public static void main(String[] args) {	
	
		String sourceFile = "./src/datatesting/cusip.txt";
		String resulstFile = "./src/datatesting/results.txt";
		String exceptionFile = "./src/datatesting/exception.txt";
		
		//Delete file results, exception if Exists.
		try {
			Files.deleteIfExists(Paths.get(resulstFile));
			Files.deleteIfExists(Paths.get(exceptionFile));
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
		
		// Read file
		FileInputStream fileInput= null;
		Scanner scanner =null;
		try{
			//use a java.util.Scanner to iterate one by one line
	    	//without keeping references to them 
	    	//in conclusion, without keeping them in memory
			fileInput = new FileInputStream(sourceFile);
			scanner = new Scanner(fileInput); 
			String line ="";
			String price="";
			StringBuilder exceptionData = new StringBuilder();
			
			
			//to store results CUSIP and Price
			StringBuilder resultsData= new StringBuilder();
			
			
			while(scanner.hasNextLine()){
				// check line if is CUSIP, it have include alphabet and number, length =8
				line = scanner.nextLine();
				if(line.matches("[A-Za-z0-9]{8}")){
			
					//if First CUSIP, don't append price="" 
					if(price.equals(""))
						resultsData.append(line+"\n");
					else
						resultsData.append(price +"\n" + line+"\n");
					//write valid data to File
					writeFile(resultsData, resulstFile);
				// if line is price, it must have only digit number and \\.	
				}else if(line.matches("\\d+\\.\\d+")){
					price= line;
				}else // it is not a price or CUSIP, write to exception file
				{
					exceptionData.append(line+"\n");
				}
					 
			}
			
			resultsData.append(price +"\n");
			scanner.close();

			//Create file to write results
			writeFile(resultsData, resulstFile);
			
			//clear data in resultsData =""
			resultsData.setLength(0);
			// Create file to write exception 
			
			if(exceptionData.length()>0){
				writeFile(exceptionData, exceptionFile);
				System.err.println("Invalid data is written in " + exceptionFile );
			}
			
		}catch(IOException e){
			System.err.println(e.getMessage());
		}finally{
			if(fileInput!=null)
				try {
					fileInput.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(scanner!=null)
				scanner.close();
		}

		System.out.println("Results is written on the file  " + resulstFile);
		
			
	}
	
	//write results/exceptions into file
	public static void writeFile(StringBuilder data, String nameFile){
		
		try{
			BufferedWriter writer= new BufferedWriter(new FileWriter(nameFile));
			writer.append(data.toString());
			writer.close();
		}catch(IOException io){
			System.err.println(io.getMessage());
		}
	}

}
