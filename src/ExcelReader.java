/*
 * File: ExcelReader.java
 * Description: Implements the methods used for parsing
 * 				Room, Student, and Teacher data into
 * 				Room, Student, and Teacher objects.
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


// Excel Reader contains static methods for parsing Room, Student, and Teacher spreadsheet files
// into ArrayLists of Room, Student, and Teacher objects.
public class ExcelReader {
	// The number of cells in each row of the Room, Student, and Teacher spreadsheets.
	private final static int ROOM_ROW_LENGTH = 8;
	private final static int STUDENT_ROW_LENGTH = -1; //TODO: implement
	private final static int TEACHER_ROW_LENGTH = -1; //TODO: implement
	
	// Open an excel document with Room data, parse the data, and return an ArrayList of Room objects
	// TODO: Actually create Room objects and return them instead of printing to stdout
	public static void parseRoomData(String filepath) throws FileNotFoundException, IOException {
		FileInputStream inputStream = new FileInputStream(filepath);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowsIterator = sheet.rowIterator();
		
		// Loop through each row, creating a Room object with each iteration.
		// TODO: Actually create Room objects and return them instead of printing to stdout
		while (rowsIterator.hasNext()) { 
			XSSFRow currentRow = (XSSFRow)rowsIterator.next();
			
			// Iterate through each cell in a given row.
			for(int i=0; i < ROOM_ROW_LENGTH; i++) {
		       // Get the cell of interest. If the cell is missing from the file, generate a blank one.
		       Cell currentCell = currentRow.getCell(i, Row.CREATE_NULL_AS_BLANK);
		       
		       int currentCellType = currentCell.getCellType();
		       
				switch (currentCellType) {
					case Cell.CELL_TYPE_BLANK:
						System.out.println("Blank");
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						System.out.println("Boolean");
						System.out.println(currentCell.getBooleanCellValue());
						break;
						
					case Cell.CELL_TYPE_ERROR:
						System.out.println("Error");
						System.out.println(currentCell.getErrorCellValue());
						break;
						
					case Cell.CELL_TYPE_FORMULA:
						System.out.println("Formula");
						break;
						
					case Cell.CELL_TYPE_NUMERIC:
						System.out.println("Numeric");
						System.out.println(currentCell.getNumericCellValue());
						break;
						
					case Cell.CELL_TYPE_STRING:
						System.out.println("String");
						System.out.println(currentCell.getStringCellValue());
						break;
						
					default:
						System.out.println("Ain't got a clue");
				}
		   }
		}
		
		workbook.close();
	}
	
	// Open an excel document with Student data, parse the data, and return an ArrayList of Student objects
	// TODO: implement
	public static void parseStudentData(String filepath) {
		
	}
	
	// Open an excel document with Teacher data, parse the data, and return an ArrayList of Teacher objects
	// TODO: implement
	public static void parseTeacherData(String filepath) {
		
	}
}