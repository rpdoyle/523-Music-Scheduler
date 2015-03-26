/*
 * File: ExcelReader.java
 * Description: Implements the methods used for parsing
 * 				Room, Student, and Teacher data into
 * 				Room, Student, and Teacher objects.
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// TODO: We may eventually need to make a decision regarding whether or not, and how, 
//to handle end times for meetings.We can ignore this for now.

// TODO: It's not fantastic to have each error message inline in this file. 
//We can rework these later, but they're fine for now.

// Excel Reader contains static methods for parsing Room, Student, and Teacher spreadsheet files
// into ArrayLists of Room, Student, and Teacher objects.
public class ExcelReader {
	// Store the current cell of interest globally so the helper methods can
	// easily access it
	private static Cell currentCell = null;
	
	// Open an excel document with Room data, parse the data, and return an ArrayList of Room objects
	public static void parseRoomData(String filepath) throws FileNotFoundException, IOException, InvalidInputFormatException {
		// Open the excel document, select the first sheet, and create an iterator to iterate over the rows
		FileInputStream inputStream = new FileInputStream(filepath);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowsIterator = sheet.rowIterator();
		
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		// Skip the header row in the spreadsheet
		rowsIterator.next();
		
		// Loop through each row of data, creating a Room object with each iteration 
		
		while (rowsIterator.hasNext()) { 
			XSSFRow currentRow = (XSSFRow)rowsIterator.next();
			
			String roomName = getStringFromCell(currentRow, 1);
			
			String[] specialInstrumentsArr = getStringArrayFromCell(currentRow, 2);
			
			int[] availableTimes = getAvailableTimes(3,4, 5, 6,7, currentRow);
			
			// Create a new room object with the data we just parsed out of the spreadsheet
			Room room = new Room(roomName, specialInstrumentsArr, availableTimes);
			rooms.add(room);
			
			// TODO: remove these print statements after showing Dr. Stotts it works
			System.out.println("Found another room");
			System.out.println(room.getName());
			//System.out.println(Arrays.toString(room.getSpecialInstruments()));
			//System.out.println(Arrays.toString(room.getAvailableTimes()));
		}
		
		workbook.close();
	}
	
	// TODO: return the ArrayList, i.e. change from void to ArrayList<Student>
	public static void parseStudentData(String filepath) throws FileNotFoundException, IOException, InvalidInputFormatException {
		
		FileInputStream inputStream = new FileInputStream(filepath);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowsIterator = sheet.rowIterator();
		
		ArrayList<Student> students = new ArrayList<Student>();
		
		// Skip the header row in the spreadsheet
		rowsIterator.next();
		
		//an id number to keep track of the student
		int id = 0;
		//declaring the variables that will hold the column values to check if the student has (a) sibling(s)
		int s1, s2, s3;
		//declaring variables that are passed to the student method to create a new student. These variables hold the
		//column name of their corresponding pieces of information
		int fName;
		int lName;
		int age;
		int gender;
		int check;
		int returningTeacher;
		int iORStudent;
		int fInstrument, sInstrument, tInstrument;
		int fILevel, sILevel, tILevel;
		int language;
		int t1, t2, t3, t4, t5;
		
		
		while(rowsIterator.hasNext()){
			//assigning the information variables values
			fName = 1;
			lName = 2;
			age = 3;
			gender = 6;
			check = 13;
			returningTeacher = 12;
			iORStudent = 14;
			fInstrument = 16;
			sInstrument = 18;
			tInstrument = 20;
			fILevel = 17;
			sILevel = 19;
			tILevel = 21;
			language = 75;
			t1 = 78;
			t2 = 79;
			t3 = 80;
			t4 = 81;
			t5 = 82;
			
			
			XSSFRow currentRow = (XSSFRow)rowsIterator.next();
						
			//calls the createStudent method to create a new student from the information that we want
			Student student = createStudent(id,fName,lName,age,gender,check,returningTeacher,iORStudent,fInstrument,sInstrument, tInstrument,fILevel,sILevel,tILevel, language,t1,t2,t3,t4,t5,currentRow);
			students.add(student);
			
			// TODO: remove these print statements after showing Dr. Stotts it works
			//printOutput(student);
			
			//increase the id so the next student has a new id
			id++;
			
			//check to see if this person has a sibling
			s1= 22;
			
			if (getStringFromCell(currentRow,s1).equalsIgnoreCase("yes")){
				
				fName = 23;
				lName = 24;
				age = 25;
				gender = 28;
				check = 30;
				returningTeacher = 29;
				iORStudent = 31;
				fInstrument = 33;
				sInstrument = 35;
				tInstrument = 37;
				fILevel = 34;
				sILevel = 36;
				tILevel = 38;
				language = 75;
				t1 = 78;
				t2 = 79;
				t3 = 80;
				t4 = 81;
				t5 = 82;
				
				//currently the sibling has no field of returning instrument, so I will capture their first choice instrument as their returning instrument
				Siblings.sibling1 = createStudent(id,fName,lName,age,gender,check,returningTeacher,iORStudent,fInstrument,sInstrument, tInstrument,fILevel,sILevel,tILevel, language,t1,t2,t3,t4,t5,currentRow);

				//add the sibling to the overall students ArrayList
				students.add(Siblings.sibling1);
				
				//add the new sibling to the student's sibling ArrayList
				student.getSiblings().add(Siblings.sibling1);
				Siblings.sibling1.getSiblings().add(student);
				
				//printOutput(Siblings.sibling1);
				id++;
			}
			s2 = 39;
			//check to see if they have a second sibling
			if (getStringFromCell(currentRow, s2).equalsIgnoreCase("yes")){
				
				fName = 40;
				lName = 41;
				age = 42;
				gender = 45;
				check = 47;
				returningTeacher = 46;
				iORStudent = 48;
				fInstrument = 50;
				sInstrument = 52;
				tInstrument = 54;
				fILevel = 51;
				sILevel = 53;
				tILevel = 55;
				language = 75;
				t1 = 78;
				t2 = 79;
				t3 = 80;
				t4 = 81;
				t5 = 82;
				
				//currently the sibling has no field of returning instrument, so I will capture their first choice instrument as their returning instrument
				Siblings.sibling2= createStudent(id,fName,lName,age,gender,check,returningTeacher,iORStudent,fInstrument,sInstrument, tInstrument,fILevel,sILevel,tILevel, language,t1,t2,t3,t4,t5,currentRow);

				students.add(Siblings.sibling2);
				
				//update the Sibling ArrayList of all existing students
				student.getSiblings().add(Siblings.sibling2);
				Siblings.sibling1.getSiblings().add(Siblings.sibling2);
				Siblings.sibling2.getSiblings().add(student);
				Siblings.sibling2.getSiblings().add(Siblings.sibling1);
				
				
				//printOutput(Siblings.sibling2);
				id++;
			}
			
			
			s3 = 56;
			//check to see if this student has more siblings. 
			if (getStringFromCell(currentRow, s3).equalsIgnoreCase("yes")){
				
				fName = 57;
				lName = 58;
				age = 59;
				gender = 62;
				check = 64;
				returningTeacher = 63;
				iORStudent = 65;
				fInstrument = 67;
				sInstrument = 69;
				tInstrument = 71;
				fILevel = 68;
				sILevel = 70;
				tILevel = 72;
				language = 75;
				t1 = 78;
				t2 = 79;
				t3 = 80;
				t4 = 81;
				t5 = 82;
				
				//currently the sibling has no field of returning instrument, so I will capture their first choice instrument as their returning instrument
				Siblings.sibling3= createStudent(id,fName,lName,age,gender,check,returningTeacher,iORStudent,fInstrument,sInstrument, tInstrument,fILevel,sILevel,tILevel, language,t1,t2,t3,t4,t5,currentRow);

				students.add(Siblings.sibling3);
				//adding the siblings to each other's Siblings ArrayLists
				student.getSiblings().add(Siblings.sibling3);
				Siblings.sibling1.getSiblings().add(Siblings.sibling3);
				Siblings.sibling2.getSiblings().add(Siblings.sibling3);
				Siblings.sibling3.getSiblings().add(student);
				Siblings.sibling3.getSiblings().add(Siblings.sibling1);
				Siblings.sibling3.getSiblings().add(Siblings.sibling2);
								

				//printOutput(Siblings.sibling3);
				
				id++;
			}
		}
		
		//students.toString()
		//for (int i = 0; i< students.get(3).getSiblings().size();i++){
			//System.out.println(students.get(3).getSiblings().get(i).getName());
		//}
		for (int i = 0; i<students.size();i++){
			System.out.println("The siblings of " + students.get(i).getName() + " are the following:");
			for (int j = 0; j < students.get(i).getSiblings().size();j++){
			System.out.print(students.get(i).getSiblings().get(j).getName() + " ");
			}
			System.out.println(" ");
		}
		
		workbook.close();
		//TODO: return the students ArrayList
		//return students;
	}
	
	// Open an excel document with Teacher data, parse the data, and return an ArrayList of Teacher objects
	// TODO: implement
	public static void parseTeacherData(String filepath) {
		
	}
	
	//helper function to print output to the console
	private static void printOutput(Student student){
		System.out.println("Found another student");
		System.out.println(student.getID());
		System.out.println(student.getName());
		System.out.println(student.getAge());
		System.out.println(student.getGender());
		System.out.println(student.getReturningTeacher());
		System.out.println(student.getInstrumentOfReturningStudent());
		System.out.println(Arrays.toString(student.getInstruments()));
		System.out.println(Arrays.toString(student.getInstrumentYears()));
		System.out.println(student.getLanguage());
		System.out.println(Arrays.toString(student.getAvailableTimes()));
		}
	
	// If possible, get the string representation of the value of a cell at a given row and cell index
	private static String getStringFromCell(Row row, int index) throws InvalidInputFormatException {
		// Get the value of the requested cell, or create a blank cell if the requested cell is null (no value)
		currentCell = row.getCell(index, Row.CREATE_NULL_AS_BLANK);
		
		if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
			return currentCell.getStringCellValue();
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			// TODO: this has a known limitation of not allowing decimals in numerical cells.
			// This is very minor for our use cases.
			return String.valueOf((int)currentCell.getNumericCellValue());
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_BLANK) {
			return "";
		} else {
			// If we could not get a string from the cell, show an error with the human-readable cell reference (e.g. A4)
			CellReference cellRef = new CellReference(row.getRowNum(), index);
			throw new InvalidInputFormatException("Room Data Error\n\nCould not read value from cell " + cellRef.formatAsString() + ".\nPlease make sure the cell is formatted properly.");
		}
	}
	
	// If possible, read the value of a cell at a given row and cell index as a comma separated array of strings
	private static String[] getStringArrayFromCell(Row row, int index) throws InvalidInputFormatException {
		// Get the value of the requested cell, or create a blank cell if the requested cell is null (no value)
		currentCell = row.getCell(index, Row.CREATE_NULL_AS_BLANK);
		
		if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
			return currentCell.getStringCellValue().split(",\\s*");
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			// TODO: this has a known limitation of not allowing decimals in numerical cells.
			// This is very minor for our use cases.
			String[] result = {String.valueOf((int)currentCell.getNumericCellValue())};
			return result;
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_BLANK) {
			return new String[0];
		} else {
			// If we could not get a string array from the cell, show an error with the human-readable cell reference (e.g. A4)
			CellReference cellRef = new CellReference(row.getRowNum(), index);
			throw new InvalidInputFormatException("Room Data Error\n\nCould not read value from cell " + cellRef.formatAsString() + ".\nPlease check that it is formatted properly.");
		}
	}
	
	// Given a string array of meeting times, create an integer array of start times, with each integer
	// representing the number of minutes that have passed since 12:00 AM Monday.
	//
	// dayOffset represents the number of days since Monday. Monday would have a 0 dayOffset, Tuesday a 1,
	// Wednesday a 2, etc.
	//
	// Date are expected to follow the following format guidelines:
	//		- Dates must be in AM/PM format, not military time
	// 		- Leading 0s before single digit hours are optional, but accepted
	//		- There may be any number of spaces, or none, between the minutes and AM or PM
	//		- AM and PM may be written as am, AM, pm, or PM, but mixed capitalization is not allowed
	private static int[] getStartTimeArr(String[] times, int dayOffset) throws InvalidInputFormatException {
		int[] startTimes = new int[times.length];
		boolean isPM = false;
		
		// Regular expression to make sure the date we're reading in is parsable as expected
		Pattern timePattern = Pattern.compile("(1[012]|0?[1-9]):[0-5][0-9]-(1[012]|0?[1-9]):[0-5][0-9](\\s*)(am|AM|pm|PM)");
		
		// For each time string we have, convert it to an integer
		for (int i = 0; i < times.length; i++) {
			
			if (!timePattern.matcher(times[i]).matches()) {
				// If we encounter an invalid date format, show an error with the invalid format and examples of proper format
				throw new InvalidInputFormatException("Room Data Error\n\nThe following time is not formatted properly: " + times[i] + ".\n\nExamples of acceptable time formats:\n"
						+ "9:00-10:00 PM\n9:00-10:00 pm\n9:00-10:00 AM\n9:00-10:00 am\n\nMultiple times must be separated by commas.");
			}

			// Separate the start and end times for the meetings
			String[] startEndTimes = times[i].split("-");
			
			// Split the start time into hours and minutes
			String[] startTimeParts = startEndTimes[0].split(":");

			if (startEndTimes[1].endsWith("PM") || startEndTimes[1].endsWith("pm")) {
				isPM = true;
			}
			
			// TODO: verify that this works for all cases
			// Compute the integer representation of the start time
			try {
				int startHour = Integer.parseInt(startTimeParts[0]);
				int startMinute = Integer.parseInt(startTimeParts[1]);
				
				int time = (dayOffset*1440) + (60*(startHour%12) + startMinute);
				
				if (isPM) {
					// Adds 720 minutes to account for 12:00AM - 11:59PM
					time += 720;
				}
				
				startTimes[i] = time;
			} catch (Exception e) {
				// If we encounter an invalid date format, show an error with the invalid format and examples of proper format
				throw new InvalidInputFormatException("Room Data Error\n\nThe following time is not formatted properly: " + times[i] + ".\n\nExamples of acceptable time formats:\n"
						+ "9:00-10:00 PM\n9:00-10:00 pm\n9:00-10:00 AM\n9:00-10:00 am\n\nMultiple times must be separated by commas.");
			}
		}
		
		return startTimes;
	}
	
	private static int[] getAvailableTimes(int t1, int t2, int t3, int t4, int t5, XSSFRow current) throws InvalidInputFormatException{
		// Get arrays of strings representing meetings times throughout the week
		String[] mondayTimesArr = getStringArrayFromCell(current, t1);
		String[] tuesdayTimesArr = getStringArrayFromCell(current, t2);
		String[] wednesdayTimesArr = getStringArrayFromCell(current, t3);
		String[] thursdayTimesArr = getStringArrayFromCell(current, t4);
		String[] fridayTimesArr = getStringArrayFromCell(current, t5);

		// Convert the arrays of strings to arrays of integers representing
		// the meeting start times as the number of minutes since 12:00 AM Monday
		int[] mondayIntTimes = getStartTimeArr(mondayTimesArr, 0);
		int[] tuesdayIntTimes = getStartTimeArr(tuesdayTimesArr, 1);
		int[] wednesdayIntTimes = getStartTimeArr(wednesdayTimesArr, 2);
		int[] thursdayIntTimes = getStartTimeArr(thursdayTimesArr, 3);
		int[] fridayIntTimes = getStartTimeArr(fridayTimesArr, 4);

		// Calculate the total number of times the room is available
		int numberOfTimes = mondayIntTimes.length + tuesdayIntTimes.length + 
							wednesdayIntTimes.length + thursdayIntTimes.length + 
							fridayIntTimes.length;
		
		int[] availableTimes = new int[numberOfTimes];
		int startIndex = 0;
		
		// For every day that has meeting times, copy those times into the availableTimes array
		System.arraycopy(mondayIntTimes, 0, availableTimes, startIndex, mondayIntTimes.length);
		
		if (mondayIntTimes.length > 0) {
			startIndex += mondayIntTimes.length;
			System.arraycopy(tuesdayIntTimes, 0, availableTimes, startIndex, tuesdayIntTimes.length);
		}
		
		if (tuesdayIntTimes.length > 0) {
			startIndex += tuesdayIntTimes.length;
			System.arraycopy(wednesdayIntTimes, 0, availableTimes, startIndex, wednesdayIntTimes.length);
		}
			
		if (wednesdayIntTimes.length > 0) {
			startIndex += wednesdayIntTimes.length;
			System.arraycopy(thursdayIntTimes, 0, availableTimes, startIndex, thursdayIntTimes.length);
		}
			
		if (thursdayIntTimes.length > 0) {
			startIndex += thursdayIntTimes.length;
			System.arraycopy(fridayIntTimes, 0, availableTimes, startIndex, fridayIntTimes.length);
		}
		return availableTimes;
	}
	
	//creates a Student object with the necessary data
	private static Student createStudent(int id, int fName, int lName, int a, int g, int check, int rTeacher, int iORStudent, int i1, int i2, int i3, int y1,
			int y2, int y3, int l, int t1, int t2, int t3, int t4, int t5, XSSFRow currentRow) throws InvalidInputFormatException{
		
		String name = getStringFromCell(currentRow, fName) + " " + getStringFromCell(currentRow,lName);
		
		String age = getStringFromCell(currentRow,a);
		
		String gender = getStringFromCell(currentRow,g);
		
		//check to see if this student is returning and wants to have the same teacher
		String checker = getStringFromCell (currentRow, check);
		String returningTeacher;
		if (checker.equalsIgnoreCase("yes")){
			returningTeacher = getStringFromCell (currentRow, rTeacher);	
		}
		else{
			returningTeacher = "none";
		}
		
		String instrumentOfReturningStudent = getStringFromCell(currentRow, iORStudent);
		
		//stores the instrument preferences in increasing order i.e. index 0 = choice 1, index 1 = choice 2, etc. 
		String[] instruments = {getStringFromCell(currentRow, i1),getStringFromCell(currentRow, i2), getStringFromCell(currentRow, i3)}; 
		
		//stores the years of experience with each instrument, in increasing order
		
		String[] instrumentYears = { getStringFromCell(currentRow, y1), getStringFromCell(currentRow, y2), getStringFromCell(currentRow, y3)};
		
		String language = getStringFromCell(currentRow, l);
		
		//calls the helper method that captures the available times from the spreadsheet and returns an array with the times in minutes from
		// 12 a.m Monday
		int[] availableTimes = getAvailableTimes(t1, t2, t3, t4, t5, currentRow);
		
		Student student = new Student(id, name, age, gender, returningTeacher, instrumentOfReturningStudent, instruments, instrumentYears, language, availableTimes);

		return student;
	}
}

// class to declare Student sibling objects so that they can be used globally
class Siblings{
	public static Student sibling1;
	public static Student sibling2;
	public static Student sibling3;
}
