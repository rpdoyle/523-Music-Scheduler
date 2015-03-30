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

// TODO: We may eventually need to make a decision regarding whether or not, and how, to handle end times for meetings.
// We can ignore this for now.

// TODO: It's not fantastic to have each error message inline in this file. We can rework these later, but they're fine for now.

// Excel Reader contains static methods for parsing Room, Student, and Teacher spreadsheet files
// into ArrayLists of Room, Student, and Teacher objects.
public class ExcelReader {
	// Store the current cell of interest globally so the helper methods can
	// easily access it
	private static Cell currentCell = null;

	// Open an excel document with Room data, parse the data, and return an
	// ArrayList of Room objects
	public static void parseRoomData(String filepath)
			throws FileNotFoundException, IOException,
			InvalidInputFormatException {
		// Open the excel document, select the first sheet, and create an
		// iterator to iterate over the rows
		FileInputStream inputStream = new FileInputStream(filepath);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowsIterator = sheet.rowIterator();

		ArrayList<Room> rooms = new ArrayList<Room>();

		// Skip the header row in the spreadsheet
		rowsIterator.next();

		// Loop through each row of data, creating a Room object with each
		// iteration
		while (rowsIterator.hasNext()) {
			XSSFRow currentRow = (XSSFRow) rowsIterator.next();

			String roomName = getStringFromCell(currentRow, 1, true);

			String[] specialInstrumentsArr = getStringArrayFromCell(currentRow,
					2, false);

			int[] availableTimes = getAvailableTimes(3, 4, 5, 6, 7, currentRow);

			// Create a new room object with the data we just parsed out of the
			// spreadsheet
			Room room = new Room(roomName, specialInstrumentsArr,
					availableTimes);
			rooms.add(room);

			// TODO: remove these print statements after showing Dr. Stotts it
			// works
			System.out.println("Found another room");
			System.out.println(room.getName());
			System.out.println(Arrays.toString(room.getSpecialInstruments()));
			System.out.println(Arrays.toString(room.getAvailableTimes()));
		}

		workbook.close();
	}

	// Open an excel document with Student data, parse the data, and return an
	// ArrayList of Student objects
	// TODO: implement
	public static void parseStudentData(String filepath) {

	}

	// Open an excel document with Teacher data, parse the data, and return an
	// ArrayList of Teacher objects
	public static void parseTeacherData(String filepath)
			throws FileNotFoundException, IOException,
			InvalidInputFormatException {
		// Open the excel document, select the first sheet, and create an
		// iterator to iterate over the rows
		FileInputStream inputStream = new FileInputStream(filepath);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowsIterator = sheet.rowIterator();

		ArrayList<Teacher> teachers = new ArrayList<Teacher>();

		// Skip the header row in the spreadsheet
		rowsIterator.next();

		int id = 0;

		while (rowsIterator.hasNext()) {
			XSSFRow currentRow = (XSSFRow) rowsIterator.next();

			// Get teacher's name
			String firstName = getStringFromCell(currentRow, 1, true);
			String lastName = getStringFromCell(currentRow, 2, true);

			// Check to see if the teacher is returning
			String returningTeacher = getStringFromCell(currentRow, 8, true);

			String returningStudent = "";
			String returningInstrument = "";
			String keepReturningStudent = "";

			if (returningTeacher.equalsIgnoreCase("yes")) {
				// Get information about the returning student if the teacher is
				// returning
				returningStudent = getStringFromCell(currentRow, 9, true);
				returningInstrument = getStringFromCell(currentRow, 10, true);
				keepReturningStudent = getStringFromCell(currentRow, 11, true);
			}
			// Get information about the teacher's instruments
			String[] instrumentArr = getStringArrayFromCell(currentRow, 12,
					true);
			String[] instrumentExperienceArr = getStringArrayFromCell(
					currentRow, 13, true);

			// Get teacher's preferences for student gender, age, and level
			String genderPreference = getStringFromCell(currentRow, 14, true);
			String agePreference = getStringFromCell(currentRow, 15, true);
			String levelPreference = getStringFromCell(currentRow, 16, true);

			// Get teacher's language
			String[] languageArr = getStringArrayFromCell(currentRow, 17, false);

			// Get information about teacher's crime record
			String crimeRecord = getStringFromCell(currentRow, 19, true);

			// Get teacher's available times
			int[] availableTimes = getAvailableTimes(24, 25, 26, 27, 28,
					currentRow);

			// Create a new teacher object with the data we just parsed out of
			// the
			// spreadsheet
			Teacher teacher = new Teacher(id, firstName, lastName,
					returningStudent, returningInstrument,
					keepReturningStudent, instrumentArr,
					instrumentExperienceArr, genderPreference, agePreference,
					levelPreference, languageArr, crimeRecord, availableTimes);

			teachers.add(teacher);

			// TODO: remove these print statements after showing Dr. Stotts it
			// works
			System.out.println("Found another teacher");
			System.out.println(teacher.getId());
			System.out.println(teacher.getFirstName());
			System.out.println(teacher.getLastName());
			System.out.println(teacher.getReturningStudent());
			System.out.println(teacher.getReturningInstrument());
			System.out.println(teacher.getKeepReturningStudent());
			System.out.println(Arrays.toString(teacher.getInstruments()));
			System.out.println(Arrays.toString(teacher
					.getInstrumentExperience()));
			System.out.println(teacher.getGenderPreference());
			System.out.println(teacher.getAgePreference());
			System.out.println(teacher.getLevelPreference());
			System.out.println(Arrays.toString(teacher.getLanguage()));
			System.out.println(teacher.getCrimeRecord());
			System.out.println(Arrays.toString(teacher.getAvailableTimes()));

			id++;
		}

		workbook.close();
	}

	// If possible, get the string representation of the value of a cell at a
	// given row and cell index
	private static String getStringFromCell(Row row, int index, boolean required)
			throws InvalidInputFormatException {
		// Get the value of the requested cell, or create a blank cell if the
		// requested cell is null (no value)
		currentCell = row.getCell(index, Row.CREATE_NULL_AS_BLANK);

		if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
			return currentCell.getStringCellValue();
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			// TODO: this has a known limitation of not allowing decimals in
			// numerical cells.
			// This is very minor for our use cases.
			return String.valueOf((int) currentCell.getNumericCellValue());
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_BLANK
				&& (!required)) {
			return "";
		} else {
			// If we could not get a string from the cell, show an error with
			// the human-readable cell reference (e.g. A4)
			CellReference cellRef = new CellReference(row.getRowNum(), index);
			throw new InvalidInputFormatException(
					"Room Data Error\n\nCould not read value from cell "
							+ cellRef.formatAsString()
							+ ".\nPlease make sure the cell is formatted properly.");
		}
	}

	// If possible, read the value of a cell at a given row and cell index as a
	// comma separated array of strings
	private static String[] getStringArrayFromCell(Row row, int index,
			boolean required) throws InvalidInputFormatException {
		// Get the value of the requested cell, or create a blank cell if the
		// requested cell is null (no value)
		currentCell = row.getCell(index, Row.CREATE_NULL_AS_BLANK);

		if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
			return currentCell.getStringCellValue().split(",\\s*");
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			// TODO: this has a known limitation of not allowing decimals in
			// numerical cells.
			// This is very minor for our use cases.
			String[] result = { String.valueOf((int) currentCell
					.getNumericCellValue()) };
			return result;
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_BLANK
				&& (!required)) {
			return new String[0];
		} else {
			// If we could not get a string array from the cell, show an error
			// with the human-readable cell reference (e.g. A4)
			CellReference cellRef = new CellReference(row.getRowNum(), index);
			throw new InvalidInputFormatException(
					"Room Data Error\n\nCould not read value from cell "
							+ cellRef.formatAsString()
							+ ".\nPlease check that it is formatted properly.");
		}
	}

	// Given a string array of meeting times, create an integer array of start
	// times, with each integer
	// representing the number of minutes that have passed since 12:00 AM
	// Monday.
	//
	// dayOffset represents the number of days since Monday. Monday would have a
	// 0 dayOffset, Tuesday a 1,
	// Wednesday a 2, etc.
	//
	// Date are expected to follow the following format guidelines:
	// - Dates must be in AM/PM format, not military time
	// - Leading 0s before single digit hours are optional, but accepted
	// - There may be any number of spaces, or none, between the minutes and AM
	// or PM
	// - AM and PM may be written as am, AM, pm, or PM, but mixed capitalization
	// is not allowed
	private static int[] getStartTimeArr(String[] times, int dayOffset)
			throws InvalidInputFormatException {
		int[] startTimes = new int[times.length];
		boolean isPM = false;

		// Regular expression to make sure the date we're reading in is parsable
		// as expected
		Pattern timePattern = Pattern
				.compile("(1[012]|0?[1-9]):[0-5][0-9]-(1[012]|0?[1-9]):[0-5][0-9](\\s*)(am|AM|pm|PM)");

		// For each time string we have, convert it to an integer
		for (int i = 0; i < times.length; i++) {

			if (!timePattern.matcher(times[i]).matches()) {
				// If we encounter an invalid date format, show an error with
				// the invalid format and examples of proper format
				throw new InvalidInputFormatException(
						"Room Data Error\n\nThe following time is not formatted properly: "
								+ times[i]
								+ ".\n\nExamples of acceptable time formats:\n"
								+ "9:00-10:00 PM\n9:00-10:00 pm\n9:00-10:00 AM\n9:00-10:00 am\n\nMultiple times must be separated by commas.");
			}

			// Separate the start and end times for the meetings
			String[] startEndTimes = times[i].split("-");

			// Split the start time into hours and minutes
			String[] startTimeParts = startEndTimes[0].split(":");

			if (startEndTimes[1].endsWith("PM")
					|| startEndTimes[1].endsWith("pm")) {
				isPM = true;
			}

			// TODO: verify that this works for all cases
			// Compute the integer representation of the start time
			try {
				int startHour = Integer.parseInt(startTimeParts[0]);
				int startMinute = Integer.parseInt(startTimeParts[1]);

				int time = (dayOffset * 1440)
						+ (60 * (startHour % 12) + startMinute);

				if (isPM) {
					// Adds 720 minutes to account for 12:00AM - 11:59PM
					time += 720;
				}

				startTimes[i] = time;
			} catch (Exception e) {
				// If we encounter an invalid date format, show an error with
				// the invalid format and examples of proper format
				throw new InvalidInputFormatException(
						"Room Data Error\n\nThe following time is not formatted properly: "
								+ times[i]
								+ ".\n\nExamples of acceptable time formats:\n"
								+ "9:00-10:00 PM\n9:00-10:00 pm\n9:00-10:00 AM\n9:00-10:00 am\n\nMultiple times must be separated by commas.");
			}
		}

		return startTimes;
	}

	private static int[] getAvailableTimes(int c1, int c2, int c3, int c4,
			int c5, XSSFRow currentRow) throws InvalidInputFormatException {
		// Get arrays of strings representing meetings times throughout the
		// week
		String[] mondayTimesArr = getStringArrayFromCell(currentRow, c1, true);
		String[] tuesdayTimesArr = getStringArrayFromCell(currentRow, c2, true);
		String[] wednesdayTimesArr = getStringArrayFromCell(currentRow, c3,
				true);
		String[] thursdayTimesArr = getStringArrayFromCell(currentRow, c4, true);
		String[] fridayTimesArr = getStringArrayFromCell(currentRow, c5, true);

		// Convert the arrays of strings to arrays of integers representing
		// the meeting start times as the number of minutes since 12:00 AM
		// Monday
		int[] mondayIntTimes = getStartTimeArr(mondayTimesArr, 0);
		int[] tuesdayIntTimes = getStartTimeArr(tuesdayTimesArr, 1);
		int[] wednesdayIntTimes = getStartTimeArr(wednesdayTimesArr, 2);
		int[] thursdayIntTimes = getStartTimeArr(thursdayTimesArr, 3);
		int[] fridayIntTimes = getStartTimeArr(fridayTimesArr, 4);

		// Calculate the total number of times the room is available
		int numberOfTimes = mondayIntTimes.length + tuesdayIntTimes.length
				+ wednesdayIntTimes.length + thursdayIntTimes.length
				+ fridayIntTimes.length;

		int[] availableTimes = new int[numberOfTimes];
		int startIndex = 0;

		// For every day that has meeting times, copy those times into the
		// availableTimes array
		System.arraycopy(mondayIntTimes, 0, availableTimes, startIndex,
				mondayIntTimes.length);

		if (mondayIntTimes.length > 0) {
			startIndex += mondayIntTimes.length;
			System.arraycopy(tuesdayIntTimes, 0, availableTimes, startIndex,
					tuesdayIntTimes.length);
		}

		if (tuesdayIntTimes.length > 0) {
			startIndex += tuesdayIntTimes.length;
			System.arraycopy(wednesdayIntTimes, 0, availableTimes, startIndex,
					wednesdayIntTimes.length);
		}

		if (wednesdayIntTimes.length > 0) {
			startIndex += wednesdayIntTimes.length;
			System.arraycopy(thursdayIntTimes, 0, availableTimes, startIndex,
					thursdayIntTimes.length);
		}

		if (thursdayIntTimes.length > 0) {
			startIndex += thursdayIntTimes.length;
			System.arraycopy(fridayIntTimes, 0, availableTimes, startIndex,
					fridayIntTimes.length);
		}
		return availableTimes;
	}
}