/*
 * File: ExcelWriter.java
 * Description: Implements the methods used for writing
 * 				schedules to an Excel file.
 */

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

public class ExcelWriter {

	public static void writeDataToExcelFile(String fileName, String[][] data) {

		// Create the workbook and sheet that the file that will be written to
		HSSFWorkbook myWorkBook = new HSSFWorkbook();
		HSSFSheet mySheet = myWorkBook.createSheet();
		HSSFRow myRow = null;
		HSSFCell myCell = null;

		// Create the font used for the header row
		HSSFFont headerFont = myWorkBook.createFont();
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setFontName("Arial");
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		headerFont.setBold(true);

		// Create the style used for the header row
		CellStyle headerStyle = myWorkBook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT
				.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setFont(headerFont);

		// Create the font used for the time rows
		HSSFFont timeFont = myWorkBook.createFont();
		timeFont.setFontHeightInPoints((short) 12);
		timeFont.setFontName("Arial");
		timeFont.setColor(IndexedColors.WHITE.getIndex());
		timeFont.setBold(true);

		// Create the style used for the time rows
		CellStyle timeStyle = myWorkBook.createCellStyle();
		timeStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		timeStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		timeStyle.setFont(timeFont);

		for (int rowNum = 0; rowNum < data[0].length; rowNum++) {
			myRow = mySheet.createRow(rowNum);

			for (int cellNum = 0; cellNum < 6; cellNum++) {
				myCell = myRow.createCell(cellNum);
				myCell.setCellValue(data[rowNum][cellNum]);

				// Set the styles of each row
				if (myCell.getRowIndex() == 0 && myCell.getColumnIndex() != 0) {
					myCell.setCellStyle(headerStyle);
				} else if (myCell.getRowIndex() == 2) {
					myCell.setCellStyle(timeStyle);
				} else if (myCell.toString().contains("PM")
						|| myCell.toString().contains("AM")) {
					myCell.setCellStyle(timeStyle);
					for (int i = 1; i < 6; i++) {
						HSSFCell timeCell = myRow.createCell(i);
						timeCell.setCellStyle(timeStyle);
					}
					break;
				}

			}

		}

		// Set the column width to fit its contents
		mySheet.autoSizeColumn(0);
		mySheet.autoSizeColumn(1);
		mySheet.autoSizeColumn(2);
		mySheet.autoSizeColumn(3);
		mySheet.autoSizeColumn(4);
		mySheet.autoSizeColumn(5);
		mySheet.autoSizeColumn(6);

		try {
			// Write the output to the file
			FileOutputStream out = new FileOutputStream(fileName);
			myWorkBook.write(out);
			out.close();
			myWorkBook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String[][] prepareDataToWriteToExcel(
			HungarianResult hungarianResult, HashSet<Integer> roomDayTimeInts) {

		// Create an object array based on the roomDayTimeInts
		Object[] intArr = roomDayTimeInts.toArray();
		Arrays.sort(intArr);

		// Create a pairTime object from the hungarianResult
		ArrayList<PairTime> pairTime = hungarianResult.getPairTimes();
		
		// Create two arraylists of PairTimes
		ArrayList<PairTime> sortedPairTime = new ArrayList<PairTime>();
		ArrayList<PairTime> trimmedPairTime = new ArrayList<PairTime>();
				
		// Create a new excelaAta String[][] to write data to
		String[][] excelData = new String[1000][1000];

		int rowIndex;

		// Create the contents of the header row
		excelData[0][1] = "Monday (teacher, student, instrument)";
		excelData[0][2] = "Tuesday (teacher, student, instrument)";
		excelData[0][3] = "Wednesday (teacher, student, instrument)";
		excelData[0][4] = "Thursday (teacher, student, instrument)";
		excelData[0][5] = "Friday (teacher, student, instrument)";
					
		int count = 1;
		int first = (int) intArr[0];

		for (int i = 0; i < intArr.length; i++) {
			if ((int) intArr[i] % 1440 != first % 1440) {
				count++;
			}
		}
		
		first = (int) intArr[0];
				
		// Put the sortedPairTime arraylist in order by time instead of by increasing int
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < pairTime.size(); j++) {
				if (HelperMethods.intTimeToString(pairTime.get(j).getRoomDayTime().getTime()).equals(HelperMethods.intTimeToString(first))) {
					sortedPairTime.add(pairTime.get(j));
				}
			}
			first = (int) intArr[i];
		}
		
		// Put the sortedPairTime arraylist into the trimmedPairTime but only include one copy
		for (int i = 0; i < pairTime.size(); i++) {
			trimmedPairTime.add(sortedPairTime.get(i));
		}
		
		rowIndex = 2;
		
		// Create a boolean to keep track of when to create a new time row
		boolean newTime = true;
		
		String firstIndex = HelperMethods.intTimeToString(trimmedPairTime.get(0).getRoomDayTime().getTime());
				
		while (firstIndex.equals(HelperMethods.intTimeToString(trimmedPairTime.get(0).getRoomDayTime().getTime()))) {
			
			// Set the new time row if a new time was found
			if (newTime) {
				excelData[rowIndex][0] = HelperMethods.intTimeToString(trimmedPairTime.get(0).getRoomDayTime().getTime());
				newTime = false;
			}
			
			rowIndex++;
			
			// Create a pair object and a roomDayTime object from the pairTime
			Pair pair = trimmedPairTime.get(0).getPair();
			RoomDayTime roomDayTime = trimmedPairTime.get(0).getRoomDayTime();
			// Create a time, room, and day from the roomDayTime
			String time = HelperMethods.intTimeToString(trimmedPairTime.get(0).getRoomDayTime().getTime());
			String room = roomDayTime.getRoom().getName();
			// Create a student, teacher, and instrument from the pair
			String student = pair.getStudent().getName();
			String teacher = pair.getTeacher().getName();
			String instrument = pair.getInstrument();
			
			excelData[rowIndex][0] = room;
			
			// Monday
			if (HelperMethods.getDayOfLesson(trimmedPairTime.get(0).getRoomDayTime().getTime()) == 0) {
				// Write the names of the teacher and student in the correct column
				excelData[rowIndex][1] = teacher + ", " + student + ", " + instrument;
				
				for (int m = 0; m < trimmedPairTime.size(); m++) {
					// Room name and time are same (different day)
					if(trimmedPairTime.get(m).getRoomDayTime().getRoom().getName().equals(room) && 
						(HelperMethods.intTimeToString(trimmedPairTime.get(m).getRoomDayTime().getTime()).equals(time))) {
						String newTeacher = trimmedPairTime.get(m).getPair().getTeacher().getName();
						String newStudent = trimmedPairTime.get(m).getPair().getStudent().getName();
						String newInstrument = trimmedPairTime.get(m).getPair().getInstrument();
						int day = HelperMethods.getDayOfLesson(trimmedPairTime.get(m).getRoomDayTime().getTime());
						excelData[rowIndex][day + 1] = newTeacher + ", " + newStudent + ", " + newInstrument;
					}
				}

			// Tuesday
			} else if (HelperMethods.getDayOfLesson(trimmedPairTime.get(0).getRoomDayTime().getTime()) == 1) {
				// Write the names of the teacher and student in the correct column
				excelData[rowIndex][2] = teacher + ", " + student + ", " + instrument;
				
				for (int m = 0; m < trimmedPairTime.size(); m++) {
					// Room name and time are same (different day)
					if(trimmedPairTime.get(m).getRoomDayTime().getRoom().getName().equals(room) && 
						(HelperMethods.intTimeToString(trimmedPairTime.get(m).getRoomDayTime().getTime()).equals(time))) {
						String newTeacher = trimmedPairTime.get(m).getPair().getTeacher().getName();
						String newStudent = trimmedPairTime.get(m).getPair().getStudent().getName();
						String newInstrument = trimmedPairTime.get(m).getPair().getInstrument();
						int day = HelperMethods.getDayOfLesson(trimmedPairTime.get(m).getRoomDayTime().getTime());
						excelData[rowIndex][day + 1] = newTeacher + ", " + newStudent + ", " + newInstrument;
					}
				}
				
			// Wednesday
			} else if (HelperMethods.getDayOfLesson(trimmedPairTime.get(0).getRoomDayTime().getTime()) == 2) {
				// Write the names of the teacher and student in the correct column
				excelData[rowIndex][3] = teacher + ", " + student + ", " + instrument;
				
				for (int m = 0; m < trimmedPairTime.size(); m++) {
					// Room name and time are same (different day)
					if(trimmedPairTime.get(m).getRoomDayTime().getRoom().getName().equals(room) && 
						(HelperMethods.intTimeToString(trimmedPairTime.get(m).getRoomDayTime().getTime()).equals(time))) {
						String newTeacher = trimmedPairTime.get(m).getPair().getTeacher().getName();
						String newStudent = trimmedPairTime.get(m).getPair().getStudent().getName();
						String newInstrument = trimmedPairTime.get(m).getPair().getInstrument();
						int day = HelperMethods.getDayOfLesson(trimmedPairTime.get(m).getRoomDayTime().getTime());
						excelData[rowIndex][day + 1] = newTeacher + ", " + newStudent + ", " + newInstrument;
					}
				}

			// Thursday
			} else if (HelperMethods.getDayOfLesson(trimmedPairTime.get(0).getRoomDayTime().getTime()) == 3) {
				// Write the names of the teacher and student in the correct column
				excelData[rowIndex][4] = teacher + ", " + student + ", " + instrument;
				
				for (int m = 0; m < trimmedPairTime.size(); m++) {
					// Room name and time are same (different day)
					if(trimmedPairTime.get(m).getRoomDayTime().getRoom().getName().equals(room) && 
						(HelperMethods.intTimeToString(trimmedPairTime.get(m).getRoomDayTime().getTime()).equals(time))) {
						String newTeacher = trimmedPairTime.get(m).getPair().getTeacher().getName();
						String newStudent = trimmedPairTime.get(m).getPair().getStudent().getName();
						String newInstrument = trimmedPairTime.get(m).getPair().getInstrument();
						int day = HelperMethods.getDayOfLesson(trimmedPairTime.get(m).getRoomDayTime().getTime());
						excelData[rowIndex][day + 1] = newTeacher + ", " + newStudent + ", " + newInstrument;
					}
				}
				
			// Friday
			} else if (HelperMethods.getDayOfLesson(trimmedPairTime.get(0).getRoomDayTime().getTime()) == 4) {
				// Write the names of the teacher and student in the correct column
				excelData[rowIndex][5] = teacher + ", " + student + ", " + instrument;
			}
			
			// Remove the original pair from the arraylist
			trimmedPairTime.remove(0);
			
			if (trimmedPairTime.isEmpty()) {
				break;
			} else {
				// Check to see if there are other rooms at the same time
				if (firstIndex.equals(HelperMethods.intTimeToString(trimmedPairTime.get(0).getRoomDayTime().getTime()))) {
					//continue
				} else {
					// Set firstIndex to the new time string
					firstIndex = HelperMethods.intTimeToString(trimmedPairTime.get(0).getRoomDayTime().getTime());
					
					// A new time row should be created
					newTime = true;
					
					rowIndex++;
				}
			}
		}

		return excelData;
	}

}
