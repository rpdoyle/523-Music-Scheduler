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

		mySheet.autoSizeColumn(0);
		mySheet.autoSizeColumn(1);
		mySheet.autoSizeColumn(2);
		mySheet.autoSizeColumn(3);
		mySheet.autoSizeColumn(4);
		mySheet.autoSizeColumn(5);
		mySheet.autoSizeColumn(6);

		try {
			FileOutputStream out = new FileOutputStream(fileName);
			myWorkBook.write(out);
			out.close();
			myWorkBook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// TODO: this will only take in hungarianResults in the future
	public static String[][] prepareDataToWriteToExcel(ArrayList<RoomDayTime> roomDayTimes/*,
			ArrayList<HungarianResult> hungarianResults*/) {
		String[][] excelData = new String[1000][1000];

		int rowIndex;

		excelData[0][1] = "Monday (teacher, student)";
		excelData[0][2] = "Tuesday (teacher, student)";
		excelData[0][3] = "Wednesday (teacher, student)";
		excelData[0][4] = "Thursday (teacher, student)";
		excelData[0][5] = "Friday (teacher, student)";

		excelData[2][0] = "4:20 - 5:00 PM";

		rowIndex = 3;

		// Room has availability at 4:20 PM
		// TODO: change roomDayTimes to hungarianResults and work from there
		// and each of the following for loops
		for (int i = 0; i < roomDayTimes.size(); i++) {

			RoomDayTime roomDayTime = roomDayTimes.get(i);
			Room room = roomDayTime.getRoom();

			//HungarianResult hungarianResult = hungarianResults.get(i);
			//RoomDayTime hungarianRDT = hungarianResult.getPairTimes().get(i).getRoomDayTime();

			if (roomDayTime.getTime() == 980 || roomDayTime.getTime() == 2420
					|| roomDayTime.getTime() == 3860 || roomDayTime.getTime() == 5300
					|| roomDayTime.getTime() == 6740) {

				excelData[rowIndex][0] = room.getName();

				// TODO: Change the room is available text to the
				// hungarianResult.getPairTimes().get(i).getPair().getStudent()
				// .getName()/.getTeacher().getName() if
				// hungarianRDT == the correct room and day time
				// Also include the score from hungarianResult for each pair

				if (roomDayTime.getTime() == 980) {
					excelData[rowIndex][1] = "Room is available";
				}
				if (roomDayTime.getTime() == 2420) {
					excelData[rowIndex][2] = "Room is available";
				}
				if (roomDayTime.getTime() == 2860) {
					excelData[rowIndex][3] = "Room is available";
				}
				if (roomDayTime.getTime() == 5300) {
					excelData[rowIndex][4] = "Room is available";
				}
				if (roomDayTime.getTime() == 6740) {
					excelData[rowIndex][5] = "Room is available";
				}

				rowIndex++;
			}

		}

		// Room has availability at 5:00 PM
		excelData[rowIndex][0] = "5:00 - 5:40 PM";
		rowIndex++;

		// Room has availability at 5:00 PM
		for (int i = 0; i < roomDayTimes.size(); i++) {
			RoomDayTime roomDayTime = roomDayTimes.get(i);
			Room room = roomDayTime.getRoom();

			//HungarianResult hungarianResult = hungarianResults.get(i);
			//RoomDayTime hungarianRDT = hungarianResult.getPairTimes().get(i).getRoomDayTime();

			if (roomDayTime.getTime() == 1020 || roomDayTime.getTime() == 2460
					|| roomDayTime.getTime() == 3900
					|| roomDayTime.getTime() == 5340
					|| roomDayTime.getTime() == 6780) {

				excelData[rowIndex][0] = room.getName();

				// TODO
				// Change the room is available text to the
				// hungarianResult.getPairTimes().get(i).getPair().getStudent()
				// .getName()/.getTeacher().getName() if
				// hungarianRDT == the correct room and day time
				// Also include the score from hungarianResult for each pair

				if (roomDayTime.getTime() == 1020) {
					excelData[rowIndex][1] = "Room is available";
				} else if (roomDayTime.getTime() == 2460) {
					excelData[rowIndex][2] = "Room is available";
				} else if (roomDayTime.getTime() == 3900) {
					excelData[rowIndex][3] = "Room is available";
				} else if (roomDayTime.getTime() == 5340) {
					excelData[rowIndex][4] = "Room is available";
				} else if (roomDayTime.getTime() == 6780) {
					excelData[rowIndex][5] = "Room is available";
				}

				rowIndex++;
			}
		}

		// Room has availability at 5:40 PM
		excelData[rowIndex][0] = "5:40 - 6:20 PM";
		rowIndex++;

		for (int i = 0; i < roomDayTimes.size(); i++) {
			RoomDayTime roomDayTime = roomDayTimes.get(i);
			Room room = roomDayTime.getRoom();

			//HungarianResult hungarianResult = hungarianResults.get(i);
			//RoomDayTime hungarianRDT = hungarianResult.getPairTimes().get(i).getRoomDayTime();

			if (roomDayTime.getTime() == 1060 || roomDayTime.getTime() == 2500
					|| roomDayTime.getTime() == 3940
					|| roomDayTime.getTime() == 5380
					|| roomDayTime.getTime() == 6820) {

				excelData[rowIndex][0] = room.getName();

				// TODO
				// Change the room is available text to the
				// hungarianResult.getPairTimes().get(i).getPair().getStudent()
				// .getName()/.getTeacher().getName() if
				// hungarianRDT == the correct room and day time
				// Also include the score from hungarianResult for each pair

				if (roomDayTime.getTime() == 1060) {
					excelData[rowIndex][1] = "Room is available";
				} else if (roomDayTime.getTime() == 2500) {
					excelData[rowIndex][2] = "Room is available";
				} else if (roomDayTime.getTime() == 3940) {
					excelData[rowIndex][3] = "Room is available";
				} else if (roomDayTime.getTime() == 5380) {
					excelData[rowIndex][4] = "Room is available";
				} else if (roomDayTime.getTime() == 6820) {
					excelData[rowIndex][5] = "Room is available";
				}

				rowIndex++;
			}
		}

		// Room has availability at 6:20 PM
		excelData[rowIndex][0] = "6:20 - 7:00 PM";
		rowIndex++;

		for (int i = 0; i < roomDayTimes.size(); i++) {
			RoomDayTime roomDayTime = roomDayTimes.get(i);
			Room room = roomDayTime.getRoom();

			//HungarianResult hungarianResult = hungarianResults.get(i);
			//RoomDayTime hungarianRDT = hungarianResult.getPairTimes().get(i).getRoomDayTime();

			if (roomDayTime.getTime() == 1100 || roomDayTime.getTime() == 2540
					|| roomDayTime.getTime() == 3980
					|| roomDayTime.getTime() == 5420
					|| roomDayTime.getTime() == 6860) {

				excelData[rowIndex][0] = room.getName();

				// TODO
				// Change the room is available text to the
				// hungarianResult.getPairTimes().get(i).getPair().getStudent()
				// .getName()/.getTeacher().getName() if
				// hungarianRDT == the correct room and day time
				// Also include the score from hungarianResult for each pair

				if (roomDayTime.getTime() == 1100) {
					excelData[rowIndex][1] = "Room is available";
				} else if (roomDayTime.getTime() == 2540) {
					excelData[rowIndex][2] = "Room is available";
				} else if (roomDayTime.getTime() == 3980) {
					excelData[rowIndex][3] = "Room is available";
				} else if (roomDayTime.getTime() == 5420) {
					excelData[rowIndex][4] = "Room is available";
				} else if (roomDayTime.getTime() == 6860) {
					excelData[rowIndex][5] = "Room is available";
				}

				rowIndex++;
			}
		}

		// Room has availability at 7:00 PM
		excelData[rowIndex][0] = "7:00 - 7:40 PM";
		rowIndex++;

		for (int i = 0; i < roomDayTimes.size(); i++) {
			RoomDayTime roomDayTime = roomDayTimes.get(i);
			Room room = roomDayTime.getRoom();

			//HungarianResult hungarianResult = hungarianResults.get(i);
			//RoomDayTime hungarianRDT = hungarianResult.getPairTimes().get(i).getRoomDayTime();

			if (roomDayTime.getTime() == 1140 || roomDayTime.getTime() == 2580
					|| roomDayTime.getTime() == 4020
					|| roomDayTime.getTime() == 5460
					|| roomDayTime.getTime() == 6900) {

				excelData[rowIndex][0] = room.getName();

				// TODO
				// Change the room is available text to the
				// hungarianResult.getPairTimes().get(i).getPair().getStudent()
				// .getName()/.getTeacher().getName() if
				// hungarianRDT == the correct room and day time
				// Also include the score from hungarianResult for each pair

				if (roomDayTime.getTime() == 1140) {
					excelData[rowIndex][1] = "Room is available";
				} else if (roomDayTime.getTime() == 2580) {
					excelData[rowIndex][2] = "Room is available";
				} else if (roomDayTime.getTime() == 4020) {
					excelData[rowIndex][3] = "Room is available";
				} else if (roomDayTime.getTime() == 5460) {
					excelData[rowIndex][4] = "Room is available";
				} else if (roomDayTime.getTime() == 6900) {
					excelData[rowIndex][5] = "Room is available";
				}

				rowIndex++;
			}
		}

		return excelData;
	}
}
