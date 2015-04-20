import java.io.FileOutputStream;
import java.util.ArrayList;

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
				} else if (myCell.toString() == "5:00 - 5:40 PM"
						|| myCell.toString() == "5:40 - 6:20 PM"
						|| myCell.toString() == "6:20 - 7:00 PM"
						|| myCell.toString() == "7:00 - 7:40 PM") {
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
			HungarianResult hungarianResult) {

		// Create a pairTime object from the hungarianResult
		ArrayList<PairTime> pairTime = hungarianResult.getPairTimes();

		// Create a new excelaAta String[][] to write data to
		String[][] excelData = new String[1000][1000];

		int rowIndex;

		// Create the contents of the header row
		excelData[0][1] = "Monday (teacher, student)";
		excelData[0][2] = "Tuesday (teacher, student)";
		excelData[0][3] = "Wednesday (teacher, student)";
		excelData[0][4] = "Thursday (teacher, student)";
		excelData[0][5] = "Friday (teacher, student)";

		excelData[2][0] = "4:20 - 5:00 PM";

		rowIndex = 3;

		// Room has availability at 4:20 PM
		for (int i = 0; i < pairTime.size(); i++) {

			// Create a pair object and a roomDayTime object from the pairTime
			Pair pair = pairTime.get(i).getPair();
			RoomDayTime roomDayTime = pairTime.get(i).getRoomDayTime();
			// Create a time and room from the roomDayTime
			int time = roomDayTime.getTime();
			Room room = roomDayTime.getRoom();
			// Create a student and teacher from the pair
			Student student = pair.getStudent();
			Teacher teacher = pair.getTeacher();

			// If the time matches 4:20 - write the teacher and student names
			if (time == 980 || time == 2420 || time == 3860 || time == 5300
					|| time == 6740) {

				// Write the room name in the first column of the row
				excelData[rowIndex][0] = room.getName();

				if (time == 980) {
					excelData[rowIndex][1] = teacher + ", " + student;
				}
				if (time == 2420) {
					excelData[rowIndex][2] = teacher + ", " + student;
				}
				if (time == 2860) {
					excelData[rowIndex][3] = teacher + ", " + student;
				}
				if (time == 5300) {
					excelData[rowIndex][4] = teacher + ", " + student;
				}
				if (time == 6740) {
					excelData[rowIndex][5] = teacher + ", " + student;
				}

				rowIndex++;
			}

		}

		// Room has availability at 5:00 PM
		excelData[rowIndex][0] = "5:00 - 5:40 PM";
		rowIndex++;

		for (int i = 0; i < pairTime.size(); i++) {

			// Create a pair object and a roomDayTime object from the pairTime
			Pair pair = pairTime.get(i).getPair();
			RoomDayTime roomDayTime = pairTime.get(i).getRoomDayTime();
			// Create a time and room from the roomDayTime
			int time = roomDayTime.getTime();
			Room room = roomDayTime.getRoom();
			// Create a student and teacher from the pair
			Student student = pair.getStudent();
			Teacher teacher = pair.getTeacher();

			// If the time matches 5:00 - write the teacher and student names
			if (time == 1020 || time == 2460 || time == 3900 || time == 5340
					|| time == 6780) {

				// Write the room name in the first column of the row
				excelData[rowIndex][0] = room.getName();

				if (time == 1020) {
					excelData[rowIndex][1] = teacher + ", " + student;
				} else if (time == 2460) {
					excelData[rowIndex][2] = teacher + ", " + student;
				} else if (time == 3900) {
					excelData[rowIndex][3] = teacher + ", " + student;
				} else if (time == 5340) {
					excelData[rowIndex][4] = teacher + ", " + student;
				} else if (time == 6780) {
					excelData[rowIndex][5] = teacher + ", " + student;
				}

				rowIndex++;
			}
		}

		// Room has availability at 5:40 PM
		excelData[rowIndex][0] = "5:40 - 6:20 PM";
		rowIndex++;

		for (int i = 0; i < pairTime.size(); i++) {

			// Create a pair object and a roomDayTime object from the pairTime
			Pair pair = pairTime.get(i).getPair();
			RoomDayTime roomDayTime = pairTime.get(i).getRoomDayTime();
			// Create a time and room from the roomDayTime
			int time = roomDayTime.getTime();
			Room room = roomDayTime.getRoom();
			// Create a student and teacher from the pair
			Student student = pair.getStudent();
			Teacher teacher = pair.getTeacher();

			// If the time matches 5:40 - write the teacher and student names
			if (time == 1060 || time == 2500 || time == 3940 || time == 5380
					|| time == 6820) {

				// Write the room name in the first column of the row
				excelData[rowIndex][0] = room.getName();

				if (time == 1060) {
					excelData[rowIndex][1] = teacher + ", " + student;
				} else if (time == 2500) {
					excelData[rowIndex][2] = teacher + ", " + student;
				} else if (time == 3940) {
					excelData[rowIndex][3] = teacher + ", " + student;
				} else if (time == 5380) {
					excelData[rowIndex][4] = teacher + ", " + student;
				} else if (time == 6820) {
					excelData[rowIndex][5] = teacher + ", " + student;
				}

				rowIndex++;
			}
		}

		// Room has availability at 6:20 PM
		excelData[rowIndex][0] = "6:20 - 7:00 PM";
		rowIndex++;

		for (int i = 0; i < pairTime.size(); i++) {

			// Create a pair object and a roomDayTime object from the pairTime
			Pair pair = pairTime.get(i).getPair();
			RoomDayTime roomDayTime = pairTime.get(i).getRoomDayTime();
			// Create a time and room from the roomDayTime
			int time = roomDayTime.getTime();
			Room room = roomDayTime.getRoom();
			// Create a student and teacher from the pair
			Student student = pair.getStudent();
			Teacher teacher = pair.getTeacher();

			// If the time matches 6:20 - write the teacher and student names
			if (time == 1100 || time == 2540 || time == 3980 || time == 5420
					|| time == 6860) {

				// Write the room name in the first column of the row
				excelData[rowIndex][0] = room.getName();

				if (time == 1100) {
					excelData[rowIndex][1] = teacher + ", " + student;
				} else if (time == 2540) {
					excelData[rowIndex][2] = teacher + ", " + student;
				} else if (time == 3980) {
					excelData[rowIndex][3] = teacher + ", " + student;
				} else if (time == 5420) {
					excelData[rowIndex][4] = teacher + ", " + student;
				} else if (time == 6860) {
					excelData[rowIndex][5] = teacher + ", " + student;
				}

				rowIndex++;
			}
		}

		// Room has availability at 7:00 PM
		excelData[rowIndex][0] = "7:00 - 7:40 PM";
		rowIndex++;

		for (int i = 0; i < pairTime.size(); i++) {

			// Create a pair object and a roomDayTime object from the pairTime
			Pair pair = pairTime.get(i).getPair();
			RoomDayTime roomDayTime = pairTime.get(i).getRoomDayTime();
			// Create a time and room from the roomDayTime
			int time = roomDayTime.getTime();
			Room room = roomDayTime.getRoom();
			// Create a student and teacher from the pair
			Student student = pair.getStudent();
			Teacher teacher = pair.getTeacher();

			// If the time matches 7:00 - write the teacher and student names
			if (time == 1140 || time == 2580 || time == 4020 || time == 5460
					|| time == 6900) {

				// Write the room name in the first column of the row
				excelData[rowIndex][0] = room.getName();

				if (time == 1140) {
					excelData[rowIndex][1] = teacher + ", " + student;
				} else if (time == 2580) {
					excelData[rowIndex][2] = teacher + ", " + student;
				} else if (time == 4020) {
					excelData[rowIndex][3] = teacher + ", " + student;
				} else if (time == 5460) {
					excelData[rowIndex][4] = teacher + ", " + student;
				} else if (time == 6900) {
					excelData[rowIndex][5] = teacher + ", " + student;
				}

				rowIndex++;
			}
		}

		return excelData;
	}
}
