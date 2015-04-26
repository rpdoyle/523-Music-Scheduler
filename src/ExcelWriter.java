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
		headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
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
				} else if (myCell.toString().contains("PM") || myCell.toString().contains("AM")) {
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

	public static String[][] prepareDataToWriteToExcel(HungarianResult hungarianResult, HashSet<Integer> roomDayTimeInts) {
		
		// Create an object array based on the roomDayTimeInts
		Object[] intArr = roomDayTimeInts.toArray();
		Arrays.sort(intArr);
		
		// Create a pairTime object from the hungarianResult
		ArrayList<PairTime> pairTime = hungarianResult.getPairTimes();

		// Create a new excelaAta String[][] to write data to
		String[][] excelData = new String[1000][1000];

		int rowIndex;

		// Create the contents of the header row
		excelData[0][1] = "Monday (teacher, student, instrument)";
		excelData[0][2] = "Tuesday (teacher, student, instrument)";
		excelData[0][3] = "Wednesday (teacher, student, instrument)";
		excelData[0][4] = "Thursday (teacher, student, instrument)";
		excelData[0][5] = "Friday (teacher, student, instrument)";
			
		rowIndex = 2;

		for (int j = 0; j < intArr.length; j++) {
			
			for (int i = 0; i < pairTime.size(); i++) {
				
				// Create a pair object and a roomDayTime object from the pairTime
				Pair pair = pairTime.get(i).getPair();
				RoomDayTime roomDayTime = pairTime.get(i).getRoomDayTime();
				// Create a time and room from the roomDayTime
				int time = roomDayTime.getTime();
				Room room = roomDayTime.getRoom();
				// Create a student, teacher, and instrument from the pair
				String student = pair.getStudent().getName();
				String teacher = pair.getTeacher().getName();
				String instrument = pair.getInstrument();

				if ((int) intArr[j] == time) {
					
					// Write the time and room name in the first column of the row
					excelData[rowIndex][0] = HelperMethods.intTimeToString((int) intArr[j]);
					rowIndex++;

					excelData[rowIndex][0] = room.getName();
					
					// Write the names of the teacher and student in the correct column
					if (HelperMethods.getDayOfLesson(time) == 0) {
						excelData[rowIndex][1] = teacher + ", " + student + ", " + instrument;
					} else if (HelperMethods.getDayOfLesson(time) == 1) {
						excelData[rowIndex][2] = teacher + ", " + student + ", " + instrument;
					} else if (HelperMethods.getDayOfLesson(time) == 2) {
						excelData[rowIndex][3] = teacher + ", " + student + ", " + instrument;
					} else if (HelperMethods.getDayOfLesson(time) == 3) {
						excelData[rowIndex][4] = teacher + ", " + student + ", " + instrument;
					} else if (HelperMethods.getDayOfLesson(time) == 4) {
						excelData[rowIndex][5] = teacher + ", " + student + ", " + instrument;
					}
					
					rowIndex++;
				}
			}
		}
		
		return excelData;
	}
}
