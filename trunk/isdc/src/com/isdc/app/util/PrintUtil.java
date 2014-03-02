package com.isdc.app.util;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.Sides;

public class PrintUtil {

	public static void main(String[] args){
		PrinterJob pj = PrinterJob.getPrinterJob();
		System.out.println(pj.printDialog());
		    if (pj.printDialog()) {
		        try {pj.print();}
		        catch (PrinterException exc) {
		            System.out.println(exc);
		         }
		     }   
		
		new PrintUtil().print();
		System.out.println("hi");
	} 
	public void print(){
		// Input the file
		FileInputStream textStream = null; 
		try { 
			textStream = new FileInputStream("/home/isdc/Desktop/isdc_error.txt"); 
		}
		catch (FileNotFoundException ffne) {
			ffne.printStackTrace();
		} 
		if (textStream == null) { 
		        return; 
		} 
		// Set the document type
		DocFlavor myFormat = DocFlavor.INPUT_STREAM.TEXT_HTML_UTF_8;
									//INPUT_STREAM.TEXT_PLAIN_UTF_8;
									//TEXT_PLAIN_ASCII;
		// Create a Doc
		Doc myDoc = new SimpleDoc(textStream, myFormat, null); 
		// Build a set of attributes
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet(); 
		aset.add(new Copies(1)); 
		//aset.add(MediaSize.ISO_A4);//ISO_A4
		aset.add(Sides.DUPLEX); 
		// discover the printers that can print the format according to the
		// instructions in the attribute set
		PrintService[] services =
		        PrintServiceLookup.lookupPrintServices(myFormat, aset);
		// Create a print job from one of the print services
		if (services.length > 0) { 
		        DocPrintJob job = services[0].createPrintJob(); 
		        try { 
		                job.print(myDoc, aset);
		        } 
		        catch (PrintException pe) {
		        	pe.printStackTrace();
		        } 
		} 
	}
}
