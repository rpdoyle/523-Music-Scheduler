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

/**
 * Contains static methods for parsing Room, Student, and Teacher spreadsheet
 * files into ArrayLists of Room, Student and Teacher objects.
 */
public class ExcelReader {

	// Store the current cell of interest globally so the helper methods can
	// easily access it
	private static Cell currentCell = null;

	/**
	 * Open an excel document with Room data and parse the data.
	 * 
	 * @param filepath
	 *            the filepath of the spreadsheet of the Room data
	 * @return an ArrayList of Room Objects
	 * @throws FileNotFoundException
	 *             FileNotFoundException
	 * @throws IOException
	 *             IOException
	 * @throws InvalidInputFormatException
	 *             InvalidInputFormatException
	 */
	public static ArrayList<Room> parseRoomData(String filepath)
			throws FileNotFoundException, IOException,
			InvalidInputFormatException {
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

			String roomName = getStringFromCell(currentRow, Columns.ROOM_NAME,
					true);

			ArrayList<String> specialInstrumentsArr = new ArrayList<String>(
					Arrays.asList(getStringArrayFromCell(currentRow,
							Columns.ROOM_INSTRUMENTS, false)));

			int[] availableTimes = getAvailableTimes(Columns.ROOM_MONDAY_TIMES,
					Columns.ROOM_TUESDAY_TIMES, Columns.ROOM_WEDNESDAY_TIMES,
					Columns.ROOM_THURSDAY_TIMES, Columns.ROOM_FRIDAY_TIMES,
					currentRow);

			// Create a new room object with the data we just parsed out of the
			// spreadsheet
			Room room = new Room(roomName, specialInstrumentsArr,
					availableTimes);
			rooms.add(room);
		}

		workbook.close();

		return rooms;
	}

	/**
	 * Open an excel document with Student data and parse the data.
	 * 
	 * @param filepath
	 *            the filepath of the spreadsheet of the Student data
	 * @return an ArrayList of Student objects
	 * @throws FileNotFoundException
	 *             FileNotFoundException
	 * @throws IOException
	 *             IOException
	 * @throws InvalidInputFormatException
	 *             InvalidInputFormatException
	 */
	public static ArrayList<Student> parseStudentData(String filepath)
			throws FileNotFoundException, IOException,
			InvalidInputFormatException {
		FileInputStream inputStream = new FileInputStream(filepath);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowsIterator = sheet.rowIterator();

		ArrayList<Student> students = new ArrayList<Student>();

		// Skip the header row in the spreadsheet
		rowsIterator.next();

		// An id number to keep track of the student
		int id = 0;

		while (rowsIterator.hasNext()) {
			XSSFRow currentRow = (XSSFRow) rowsIterator.next();

			// Calls the createStudent method to create a new student from the
			// information that we want
			Student student = createStudent(id, Columns.STUDENT_FIRST_NAME,
					Columns.STUDENT_LAST_NAME, Columns.STUDENT_AGE,
					Columns.STUDENT_GENDER_PREF, Columns.STUDENT_CHECK,
					Columns.STUDENT_RETURNING_TEACHER,
					Columns.STUDENT_RETURNING_INSTRUMENT,
					Columns.STUDENT_FIRST_INSTRUMENT,
					Columns.STUDENT_SECOND_INSTRUMENT,
					Columns.STUDENT_THIRD_INSTRUMENT,
					Columns.STUDENT_FIRST_INSTRUMENT_LEVEL,
					Columns.STUDENT_SECOND_INSTRUMENT_LEVEL,
					Columns.STUDENT_THIRD_INSTRUMENT_LEVEL,
					Columns.STUDENT_LANGUAGE, Columns.STUDENT_MONDAY_TIMES,
					Columns.STUDENT_TUESDAY_TIMES,
					Columns.STUDENT_WEDNESDAY_TIMES,
					Columns.STUDENT_THURSDAY_TIMES,
					Columns.STUDENT_FRIDAY_TIMES, currentRow);
			students.add(student);

			// Increase the id so the next student has a new id
			id++;

			Student sibling1 = null;
			Student sibling2 = null;
			Student sibling3 = null;

			if (getStringFromCell(currentRow, Columns.STUDENT_HAS_SIBLING_1,
					true).equalsIgnoreCase("yes")) {

				// Currently the sibling has no field of returning instrument,
				// so I will capture their first choice instrument as their
				// returning instrument
				sibling1 = createStudent(id, Columns.SIBLING_1_FIRST_NAME,
						Columns.SIBLING_1_LAST_NAME, Columns.SIBLING_1_AGE,
						Columns.SIBLING_1_GENDER_PREF, Columns.SIBLING_1_CHECK,
						Columns.SIBLING_1_RETURNING_TEACHER,
						Columns.SIBLING_1_RETURNING_INSTRUMENT,
						Columns.SIBLING_1_FIRST_INSTRUMENT,
						Columns.SIBLING_1_SECOND_INSTRUMENT,
						Columns.SIBLING_1_THIRD_INSTRUMENT,
						Columns.SIBLING_1_FIRST_INSTRUMENT_LEVEL,
						Columns.SIBLING_1_SECOND_INSTRUMENT_LEVEL,
						Columns.SIBLING_1_THIRD_INSTRUMENT_LEVEL,
						Columns.SIBLING_1_LANGUAGE,
						Columns.SIBLING_1_MONDAY_TIMES,
						Columns.SIBLING_1_TUESDAY_TIMES,
						Columns.SIBLING_1_WEDNESDAY_TIMES,
						Columns.SIBLING_1_THURSDAY_TIMES,
						Columns.SIBLING_1_FRIDAY_TIMES, currentRow);

				// Add the new sibling to the student's sibling ArrayList
				student.getSiblings().add(sibling1);
				sibling1.getSiblings().add(student);
			}

			// Check to see if they have a second sibling
			if (getStringFromCell(currentRow, Columns.STUDENT_HAS_SIBLING_2,
					false).equalsIgnoreCase("yes")) {
				sibling2 = createStudent(id, Columns.SIBLING_2_FIRST_NAME,
						Columns.SIBLING_2_LAST_NAME, Columns.SIBLING_2_AGE,
						Columns.SIBLING_2_GENDER_PREF, Columns.SIBLING_2_CHECK,
						Columns.SIBLING_2_RETURNING_TEACHER,
						Columns.SIBLING_2_RETURNING_INSTRUMENT,
						Columns.SIBLING_2_FIRST_INSTRUMENT,
						Columns.SIBLING_2_SECOND_INSTRUMENT,
						Columns.SIBLING_2_THIRD_INSTRUMENT,
						Columns.SIBLING_2_FIRST_INSTRUMENT_LEVEL,
						Columns.SIBLING_2_SECOND_INSTRUMENT_LEVEL,
						Columns.SIBLING_2_THIRD_INSTRUMENT_LEVEL,
						Columns.SIBLING_2_LANGUAGE,
						Columns.SIBLING_2_MONDAY_TIMES,
						Columns.SIBLING_2_TUESDAY_TIMES,
						Columns.SIBLING_2_WEDNESDAY_TIMES,
						Columns.SIBLING_2_THURSDAY_TIMES,
						Columns.SIBLING_2_FRIDAY_TIMES, currentRow);

				// Update sibling ArrayLists
				student.getSiblings().add(sibling2);
				sibling1.getSiblings().add(sibling2);
				sibling2.getSiblings().add(student);
				sibling2.getSiblings().add(sibling1);

				id++;
			}

			// Check to see if this student has more siblings.
			if (getStringFromCell(currentRow, Columns.STUDENT_HAS_SIBLING_3,
					false).equalsIgnoreCase("yes")) {
				sibling3 = createStudent(id, Columns.SIBLING_3_FIRST_NAME,
						Columns.SIBLING_3_LAST_NAME, Columns.SIBLING_3_AGE,
						Columns.SIBLING_3_GENDER_PREF, Columns.SIBLING_3_CHECK,
						Columns.SIBLING_3_RETURNING_TEACHER,
						Columns.SIBLING_3_RETURNING_INSTRUMENT,
						Columns.SIBLING_3_FIRST_INSTRUMENT,
						Columns.SIBLING_3_SECOND_INSTRUMENT,
						Columns.SIBLING_3_THIRD_INSTRUMENT,
						Columns.SIBLING_3_FIRST_INSTRUMENT_LEVEL,
						Columns.SIBLING_3_SECOND_INSTRUMENT_LEVEL,
						Columns.SIBLING_3_THIRD_INSTRUMENT_LEVEL,
						Columns.SIBLING_3_LANGUAGE,
						Columns.SIBLING_3_MONDAY_TIMES,
						Columns.SIBLING_3_TUESDAY_TIMES,
						Columns.SIBLING_3_WEDNESDAY_TIMES,
						Columns.SIBLING_3_THURSDAY_TIMES,
						Columns.SIBLING_3_FRIDAY_TIMES, currentRow);

				// Adding the siblings to each other's Siblings ArrayLists
				student.getSiblings().add(sibling3);
				sibling1.getSiblings().add(sibling3);
				sibling2.getSiblings().add(sibling3);
				sibling3.getSiblings().add(student);
				sibling3.getSiblings().add(sibling1);
				sibling3.getSiblings().add(sibling2);

				id++;
			}
		}

		workbook.close();

		return students;
	}

	/**
	 * Open an excel document with Teacher data and parse the data.
	 * 
	 * @param filepath
	 *            the filepath of the spreadsheet of the Teacher data
	 * @return an ArrayList of Teacher objects
	 * @throws FileNotFoundException
	 *             FileNotFoundException
	 * @throws IOException
	 *             IOException
	 * @throws InvalidInputFormatException
	 *             InvalidInputFormatException
	 */
	public static ArrayList<Teacher> parseTeacherData(String filepath)
			throws FileNotFoundException, IOException,
			InvalidInputFormatException {
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
			String name = getStringFromCell(currentRow,
					Columns.TEACHER_FIRST_NAME, true)
					+ " "
					+ getStringFromCell(currentRow, Columns.TEACHER_LAST_NAME,
							true);

			// Check to see if the teacher is returning
			String returningTeacher = getStringFromCell(currentRow,
					Columns.TEACHER_IS_RETURNING, true);

			String returningStudent = "";
			String returningInstrument = "";
			String keepReturningStudent = "";

			if (returningTeacher.equalsIgnoreCase("yes")) {

				// Get information about the returning student if the teacher is
				// returning
				returningStudent = getStringFromCell(currentRow,
						Columns.TEACHER_RETURNING_STUDENT_NAME, true);
				returningInstrument = getStringFromCell(currentRow,
						Columns.TEACHER_RETURNING_INSTRUMENT, true);
				keepReturningStudent = getStringFromCell(currentRow,
						Columns.TEACHER_KEEP_RETURNING_STUDENT, true);
			}

			// Get information about the teacher's instruments
			String[] instrumentArr = getStringArrayFromCell(currentRow,
					Columns.TEACHER_INSTRUMENTS, true);
			String[] instrumentExperienceArr = getStringArrayFromCell(
					currentRow, Columns.TEACHER_INSTRUMENT_EXPERIENCE, true);

			// Get teacher's preferences for student gender, age, and level
			String genderPreference = getStringFromCell(currentRow,
					Columns.TEACHER_GENDER_PREF, true);
			String agePreference = getStringFromCell(currentRow,
					Columns.TEACHER_AGE_PREF, true);
			String levelPreference = getStringFromCell(currentRow,
					Columns.TEACHER_LEVEL_PREF, true);

			// Get teacher's language
			String[] languageArr = getStringArrayFromCell(currentRow,
					Columns.TEACHER_LANGUAGES, false);

			// Get information about teacher's crime record
			String crimeRecord = getStringFromCell(currentRow,
					Columns.TEACHER_CRIME_RECORD, false);

			// Get teacher's available times
			int[] availableTimes = getAvailableTimes(
					Columns.TEACHER_MONDAY_TIMES,
					Columns.TEACHER_TUESDAY_TIMES,
					Columns.TEACHER_WEDNESDAY_TIMES,
					Columns.TEACHER_THURSDAY_TIMES,
					Columns.TEACHER_FRIDAY_TIMES, currentRow);

			// Create a new teacher object with the data we just parsed out of
			// the spreadsheet
			Teacher teacher = new Teacher(id, name, returningStudent,
					returningInstrument, keepReturningStudent, instrumentArr,
					instrumentExperienceArr, genderPreference, agePreference,
					levelPreference, languageArr, crimeRecord, availableTimes);

			teachers.add(teacher);

			id++;
		}

		workbook.close();

		return teachers;
	}

	/**
	 * Get the string representation of the value of a cell at a given row and
	 * cell index.
	 * 
	 * @param row
	 *            the Row in the spreadsheet
	 * @param index
	 *            the index of the cell that we will be retrieving a String from
	 * @param required
	 *            true if this cell is a required input from the user, false if
	 *            this cell is not
	 * @return the String from the cell
	 * @throws InvalidInputFormatException
	 *             InvalidInputFormatException
	 */
	public static String getStringFromCell(Row row, int index, boolean required)
			throws InvalidInputFormatException {

		// Get the value of the requested cell, or create a blank cell if the
		// requested cell is null (no value)
		currentCell = row.getCell(index, Row.CREATE_NULL_AS_BLANK);

		if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
			return currentCell.getStringCellValue();
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return String.valueOf((int) currentCell.getNumericCellValue());

			// If the cell is blank and not required, this is okay and we can
			// save a blank screen. If not, throw an exception.
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_BLANK
				&& (!required)) {
			return "";
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_BLANK
				&& (required)) {

			// If we could not get a string from the required cell, show an
			// error with the human-readable cell reference (e.g. A4)
			CellReference cellRef = new CellReference(row.getRowNum(), index);
			System.out.println("cell is blank for a required cell");
			throw new InvalidInputFormatException(
					"Data Error\n\nCould not read value from the required cell "
							+ cellRef.formatAsString()
							+ ".\nPlease make sure the cell contains the required data.");
		} else {

			// If we could not get a string from the cell, show an error with
			// the human-readable cell reference (e.g. A4)
			CellReference cellRef = new CellReference(row.getRowNum(), index);
			throw new InvalidInputFormatException(
					"Data Error\n\nCould not read value from cell "
							+ cellRef.formatAsString()
							+ ".\nPlease make sure the cell is formatted properly.");
		}
	}

	/**
	 * Read the value of a cell at a given row and cell index as a comma
	 * separated array of strings
	 * 
	 * @param row
	 *            the Row in the spreadsheet
	 * @param index
	 *            the index of the cell that we will be retrieving the array of
	 *            Strings from
	 * @param required
	 *            true if this cell is a required input from the user, false if
	 *            this cell is not
	 * @return Contents of the cell as a String[]
	 * @throws InvalidInputFormatException
	 *             InvalidInputFormatException
	 */
	public static String[] getStringArrayFromCell(Row row, int index,
			boolean required) throws InvalidInputFormatException {

		// Get the value of the requested cell, or create a blank cell if the
		// requested cell is null (no value)
		currentCell = row.getCell(index, Row.CREATE_NULL_AS_BLANK);

		if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
			return currentCell.getStringCellValue().split(",\\s*");
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			String[] result = { String.valueOf((int) currentCell
					.getNumericCellValue()) };
			return result;

			// If the cell is blank and it is not required, that is okay. If
			// not, it will throw an exception.
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_BLANK
				&& (!required)) {
			return new String[0];
		} else if (currentCell.getCellType() == Cell.CELL_TYPE_BLANK
				&& (required)) {

			// If we could not get a string array from the required cell, show
			// an error with the human-readable cell reference (e.g. A4)
			CellReference cellRef = new CellReference(row.getRowNum(), index);
			System.out.println("cell is blank for a required cell");
			throw new InvalidInputFormatException(
					"Data Error\n\nCould not read value from the required cell "
							+ cellRef.formatAsString()
							+ ".\nPlease make sure the cell contains the required data.");
		} else {

			// If we could not get a string array from the cell, show an error
			// with the human-readable cell reference (e.g. A4)
			CellReference cellRef = new CellReference(row.getRowNum(), index);
			throw new InvalidInputFormatException(
					"Data Error\n\nCould not read value from cell "
							+ cellRef.formatAsString()
							+ ".\nPlease check that it is formatted properly.");
		}
	}

	/**
	 * Given a string array of meeting times, create an integer array of start
	 * times, with each integer representing the number of minutes that have
	 * passed since 12:00 AM Monday. dayOffset represents the number of days
	 * since Monday. Monday would have a 0 dayOffset, Tuesday a 1, Wednesday a
	 * 2, etc. Date are expected to follow the following format guidelines: -
	 * Dates must be in AM/PM format, not military time - Leading 0s before
	 * single digit hours are optional, but accepted - There may be any number
	 * of spaces, or none, between the minutes and AM or PM - AM and PM may be
	 * written as am, AM, pm, or PM, but mixed capitalization is not allowed
	 * 
	 * @param times
	 *            an array of String times i.e 12:00 AM
	 * @param dayOffset
	 *            the offset as specified above
	 * @return an integer Array of converted Strings to times
	 * @throws InvalidInputFormatException
	 *             InvalidInputFormatException
	 */
	public static int[] getStartTimeArr(String[] times, int dayOffset)
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
						"Data Error\n\nThe following time is not formatted properly: "
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
			} else {
				isPM = false;
			}

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
						"Data Error\n\nThe following time is not formatted properly: "
								+ times[i]
								+ ".\n\nExamples of acceptable time formats:\n"
								+ "9:00-10:00 PM\n9:00-10:00 pm\n9:00-10:00 AM\n9:00-10:00 am\n\nMultiple times must be separated by commas.");
			}
		}

		return startTimes;
	}

	/**
	 * Creates an array of available times from all the available times in a
	 * given row in the spreadsheet.
	 * 
	 * @param c1
	 *            the column value corresponding to Monday
	 * @param c2
	 *            the column value corresponding to Tuesday
	 * @param c3
	 *            the column value corresponding to Wednesday
	 * @param c4
	 *            the column value corresponding to Thursday
	 * @param c5
	 *            the column value corresponding to Friday
	 * @param current
	 *            the XSSFRow that we are currently working on
	 * @return an integer of the available times from a specific row
	 * @throws InvalidInputFormatException
	 *             InvalidInputFormatException
	 */
	public static int[] getAvailableTimes(int c1, int c2, int c3, int c4,
			int c5, XSSFRow current) throws InvalidInputFormatException {

		// Get arrays of strings representing meetings times throughout the week
		String[] mondayTimesArr = getStringArrayFromCell(current, c1, false);
		String[] tuesdayTimesArr = getStringArrayFromCell(current, c2, false);
		String[] wednesdayTimesArr = getStringArrayFromCell(current, c3, false);
		String[] thursdayTimesArr = getStringArrayFromCell(current, c4, false);
		String[] fridayTimesArr = getStringArrayFromCell(current, c5, false);

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

		startIndex += mondayIntTimes.length;
		System.arraycopy(tuesdayIntTimes, 0, availableTimes, startIndex,
				tuesdayIntTimes.length);

		startIndex += tuesdayIntTimes.length;
		System.arraycopy(wednesdayIntTimes, 0, availableTimes, startIndex,
				wednesdayIntTimes.length);

		startIndex += wednesdayIntTimes.length;
		System.arraycopy(thursdayIntTimes, 0, availableTimes, startIndex,
				thursdayIntTimes.length);

		startIndex += thursdayIntTimes.length;
		System.arraycopy(fridayIntTimes, 0, availableTimes, startIndex,
				fridayIntTimes.length);

		return availableTimes;
	}

	/**
	 * Creates a Student object with the necessary data
	 * 
	 * @param id
	 *            index of the id of a Student
	 * @param fName
	 *            index of the first name of the Student
	 * @param lName
	 *            index of the last name of the Student
	 * @param a
	 *            index of the age of the Student
	 * @param g
	 *            index of the gender of the Student
	 * @param check
	 *            index of "yes" or "no", based on if the student wants the same
	 *            teacher again
	 * @param rTeacher
	 *            index of name of the returning teacher
	 * @param iORStudent
	 *            index of instrument of the returning student if they are
	 *            returning
	 * @param i1
	 *            index of first choice instrument
	 * @param i2
	 *            index of second choice instrument
	 * @param i3
	 *            index of third choice instrument
	 * @param y1
	 *            index of first choice instrument years of experience
	 * @param y2
	 *            index of second choice instrument years of experience
	 * @param y3
	 *            index of third choice instrument years of experience
	 * @param l
	 *            index of language of the preferred student, if not English
	 * @param t1
	 *            index of times that the student is available on Monday
	 * @param t2
	 *            index of times that the student is available on Tuesday
	 * @param t3
	 *            index of times that the student is available on Wednesday
	 * @param t4
	 *            index of times that the student is available on Thursday
	 * @param t5
	 *            index of times that the student is available on Friday
	 * @param currentRow
	 *            the current XSSFRow that is being operated on in the
	 *            spreadsheet
	 * @return a new Student object
	 * @throws InvalidInputFormatException
	 *             InvalidInputFormatException
	 */
	public static Student createStudent(int id, int fName, int lName, int a,
			int g, int check, int rTeacher, int iORStudent, int i1, int i2,
			int i3, int y1, int y2, int y3, int l, int t1, int t2, int t3,
			int t4, int t5, XSSFRow currentRow)
			throws InvalidInputFormatException {

		String name = getStringFromCell(currentRow, fName, true) + " "
				+ getStringFromCell(currentRow, lName, true);

		String age = getStringFromCell(currentRow, a, true);

		String gender = getStringFromCell(currentRow, g, true);

		// Check to see if this student is returning and wants to have the same
		// teacher
		String checker = getStringFromCell(currentRow, check, false);
		String returningTeacher;
		if (checker.equalsIgnoreCase("yes")) {
			returningTeacher = getStringFromCell(currentRow, rTeacher, false);
		} else {
			returningTeacher = "";
		}

		String instrumentOfReturningStudent = getStringFromCell(currentRow,
				iORStudent, false);

		// Stores the instrument preferences in increasing order i.e. index 0 =
		// choice 1, index 1 = choice 2, etc.
		String[] instruments = { getStringFromCell(currentRow, i1, true),
				getStringFromCell(currentRow, i2, true),
				getStringFromCell(currentRow, i3, true) };

		// Stores the years of experience with each instrument, in increasing
		// order
		String[] instrumentYears = { getStringFromCell(currentRow, y1, true),
				getStringFromCell(currentRow, y2, true),
				getStringFromCell(currentRow, y3, true) };

		String language;

		if (getStringFromCell(currentRow, l - 1, true).equalsIgnoreCase("no")) {
			language = getStringFromCell(currentRow, l, true);
		} else {
			language = "";
		}

		// Calls the helper method that captures the available times from the
		// spreadsheet and returns an array with the times in minutes from
		// 12 a.m Monday
		int[] availableTimes = getAvailableTimes(t1, t2, t3, t4, t5, currentRow);

		Student student = new Student(id, name, age, gender, returningTeacher,
				instrumentOfReturningStudent, instruments, instrumentYears,
				language, availableTimes);

		return student;
	}
}
