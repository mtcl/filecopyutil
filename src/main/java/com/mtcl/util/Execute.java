package com.mtcl.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Execute {

	public static void main(String[] args) {

		//How to execute:
		//Put all the invoices and the excel sheet in one folder, call it as root folder.
		//configure root folder location
		//Configure SDM name
		//Execute
		
		String rootFolder = "C:/Invoices/04-Apr";
		String excelFileName = rootFolder + File.separator
				+ "April_Invoice_details.xlsx";
		String sdmNameFilter = "Mukul";
		ReadExcel readExcel = new ReadExcel();
		FileOperations fo = new FileOperations();

		// Read excel file and get list of search patterns

		List<String> fileNameList = readExcel.filterBySdm(excelFileName,
				sdmNameFilter);
		System.out.println("Total invoices: " + fileNameList.size());

		// Create subfolder inside root folder
		if (fo.createSubFolder(rootFolder, sdmNameFilter)) {
			System.out.println("Folder created successfully.");
		}

		// Scan Folder and get complete file name

		CopyOption[] options = new CopyOption[] {
				StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES };
		Path from, to;

		for (String fileName : fileNameList) {
			String pattern = "*" + fileName + "*";
			String fullFileName = fo.scan(rootFolder, pattern);
			System.out.println("Invoice Found: " + fullFileName);
			
			//Copy files to SDM FOlder
			from = Paths.get(rootFolder + File.separator + fullFileName);
			to = Paths.get(rootFolder + File.separator + sdmNameFilter + File.separator	+ fullFileName);
			try {
				Files.copy(from, to , options);
				System.out.println("Invoice copied to location: "+ to.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// String searchString = "US410-0000954231";

	}
}
