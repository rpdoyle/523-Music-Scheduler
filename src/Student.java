import java.util.ArrayList;

/**
 * Represents a Student to be scheduled
 */
public class Student {

	private int id;
	private String name;
	private String age;
	private String gender;
	private String returningTeacher;
	private String instrumentOfReturningStudent;
	private String[] instruments;
	private String[] instrumentYears;
	private String language;
	private int[] availableTimes;
	private ArrayList<Student> siblings;

	/**
	 * Constructor for a Student
	 * 
	 * @param id
	 *            Identification number
	 * @param name
	 *            Student's name
	 * @param age
	 *            Student's age
	 * @param gender
	 *            Student's gender
	 * @param returningTeacher
	 *            Student's returning teacher (if applicable)
	 * @param instrumentOfReturningStudent
	 *            Student's returning instrument (if applicable)
	 * @param instruments
	 *            Instruments the Student wants to play
	 * @param instrumentYears
	 *            Amount of time the Student has played each instrument
	 * @param language
	 *            Language the Student wishes to be taught lessons in
	 * @param availableTimes
	 *            Times the Student is available for lessons
	 */
	public Student(int id, String name, String age, String gender,
			String returningTeacher, String instrumentOfReturningStudent,
			String[] instruments, String[] instrumentYears, String language,
			int[] availableTimes) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.returningTeacher = returningTeacher;
		this.instrumentOfReturningStudent = instrumentOfReturningStudent;
		this.instruments = instruments;
		this.instrumentYears = instrumentYears;
		this.language = language;
		this.availableTimes = availableTimes;
		siblings = new ArrayList<Student>();
	}

	/**
	 * Returns the id of the Student
	 * 
	 * @return Id of the Student
	 */
	public int getID() {
		return id;
	}

	/**
	 * Returns the name of the Student
	 * 
	 * @return Name of the Student
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the age of the Student
	 * 
	 * @return Age of the Student
	 */
	public String getAge() {
		return age;
	}

	/**
	 * Returns the gender of the student
	 * 
	 * @return Gender of the Student
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Returns the Teacher who taught the Student last year
	 * 
	 * @return Returning Teacher of the Student
	 */
	public String getReturningTeacher() {
		return returningTeacher;
	}

	/**
	 * Returns the instrument the Student learned last year
	 * 
	 * @return Returning instrument of the Student
	 */
	public String getInstrumentOfReturningStudent() {
		return instrumentOfReturningStudent;
	}

	/**
	 * Returns the instruments the Student wishes to learn
	 * 
	 * @return String[] of instruments the Student wishes to learn
	 */
	public String[] getInstruments() {
		return instruments;
	}

	/**
	 * Returns the experience level of the Student for each instrument they wish
	 * to learn
	 * 
	 * @return String[] of experience the Student has with each instrument
	 */
	public String[] getInstrumentYears() {
		return instrumentYears;
	}

	/**
	 * Returns the preferred language of the Student
	 * 
	 * @return Preferred language of the Student
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Returns the times the Student is available for lessons
	 * 
	 * @return Times the Student is available for lessons
	 */
	public int[] getAvailableTimes() {
		return availableTimes;
	}

	/**
	 * Returns the siblings of the Student
	 * 
	 * @return ArrayList of the Student's siblings
	 */
	public ArrayList<Student> getSiblings() {
		return siblings;
	}
}
