package com.mtcl.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {

	@SuppressWarnings("resource")
	public List<String> filterBySdm(String excelFileName, String sdmNameFilter) {
		String fileName = "";
		String sdmName = "";
		List<String> fileNameList = new ArrayList<String>();
		try {
			FileInputStream file = new FileInputStream(new File(excelFileName));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				int columnCount = 0;
				String cellValue = "";
				while (cellIterator.hasNext()) {
					columnCount++;
					Cell cell = cellIterator.next();
					// Check the cell type and format accordingly
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						// System.out.print(cell.getNumericCellValue() + "t");
						break;
					case Cell.CELL_TYPE_STRING:
						cellValue = cell.getStringCellValue();
						break;
					}
					if (columnCount == 1) {
						fileName = cellValue;
					}
					if (columnCount == 8) {
						sdmName = cellValue;
					}

				}
				// System.out.println(sdmName + " " + fileName);
				if (sdmName.contains(sdmNameFilter)) {
					//System.out.println(fileName);
					fileNameList.add(fileName);
				}
				// System.out.println("");
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileNameList;
	}
}
