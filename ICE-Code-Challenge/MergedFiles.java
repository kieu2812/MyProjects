package com.ice.test;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
/* **********
 * Author : KIEU NGUYEN
 * Date: 2/19/2018
 * **********
 * /
/**********************************************
 Problem 2: 
 Given two sorted files, write a Java program to merge them to preserve sort order.
 DO NOT assume either of these files can fit in memory!
*/
public class MergedFiles {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename1 = "./src/datatesting/sorted1.txt";
		String filename2 = "./src/datatesting/sorted2.txt";
		String mergeFile = "./src/datatesting/merge.txt";
		
		File file1 = new File(filename1);
		File file2 = new File(filename2);
		
		//Delete file mergeFile if Exists.
		try {
			Files.deleteIfExists(Paths.get(mergeFile));
		} catch (IOException e1) {		
			e1.printStackTrace();
		}
		
	    try {

	    	//use a java.util.Scanner to iterate one by one line
	    	//without keeping references to them 
	    	//in conclusion, without keeping them in memory
	        Scanner sc1 = new Scanner(file1);
	        Scanner sc2 = new Scanner(file2);
	        
	        boolean hasNext1 = sc1.hasNextLine();
	        boolean hasNext2 = sc2.hasNextLine();
	        
	        BigDecimal nextVal1=new BigDecimal(0), nextVal2=new BigDecimal(0);
	        
	        //this will check which list moves next
	        int whichNext = 0;
	        
	        while (hasNext1 && hasNext2) {
	        	
	        	//This first check runs only one at first time 
	        	if (whichNext == 0) {
	        		nextVal1 = sc1.nextBigDecimal();	
		        	nextVal2 = sc2.nextBigDecimal();
	        	} else if (whichNext == 1) {//if list1 less than list2 
	        		nextVal1 = sc1.nextBigDecimal();			        	
	        	} else if (whichNext == 2) {//if list2 less than or equal to list1 	        			
		        	nextVal2 = sc2.nextBigDecimal();
	        	}
	        	
	        	if (nextVal1.compareTo(nextVal2) < 0) {
	        		writeToFile(mergeFile,nextVal1);
	        		hasNext1 = sc1.hasNextLine();
	        		whichNext = 1;
	        	} else {
	        		writeToFile(mergeFile,nextVal2);
	        		hasNext2 = sc2.hasNextLine();
	        		whichNext = 2;
	        	}	        	
	        }
	        
	        //At this time one list still has items
	        if (hasNext1) {
	        	writeToFile(mergeFile,nextVal1);
	        	while (sc1.hasNextLine()) {	        	
		        	nextVal1 = sc1.nextBigDecimal();
		        	writeToFile(mergeFile,nextVal1);
		        }
	        } else if (hasNext2) {
	        	writeToFile(mergeFile,nextVal2);
	        	while (sc2.hasNextLine()) {	        	
		        	nextVal2 = sc2.nextBigDecimal();
		        	writeToFile(mergeFile,nextVal2);
		        }	
	        }
	        
	        sc1.close();
	        sc2.close();
	    } 
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	//Write each line to the file
	public static void writeToFile(String path, BigDecimal val) {

		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter print_line = null;

		try {

			fw = new FileWriter(path, true);
			bw = new BufferedWriter(fw);
			print_line = new PrintWriter(bw);
			print_line.printf("%s" + "%n", val);			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
			print_line.close();
		}
	}

}
