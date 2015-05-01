/**
 * Stores data for a Teacher.
 *
 */
public class Teacher {

	private int id;
	private String name;
	private String returningStudent;
	private String returningInstrument;
	private String keepReturningStudent;
	private String[] instruments;
	private String[] instrumentExperience;
	private String genderPreference;
	private String agePreference;
	private String levelPreference;
	private String[] languages;
	private String crimeRecord;
	private int[] availableTimes;

	/**
	 * Teacher object stores teacher information for scheduling.
	 * 
	 * @param id
	 *            id of the Student
	 * @param name
	 *            First and last name of a Teacher
	 * @param returningStudent
	 *            First and last name of the returning student of a Teacher.
	 * @param returningInstrument
	 *            The returning instrument of a Teacher.
	 * @param keepReturningStudent
	 *            Whether a Teacher wants to keep their returning student.
	 * @param instruments
	 *            String[] of instruments the Teacher can teach.
	 * @param instrumentExperience
	 *            String[] of instrument experience of a Teacher.
	 * @param genderPreference
	 *            Student gender preference of a Teacher.
	 * @param agePreference
	 *            Student age preference of a Teacher.
	 * @param levelPreference
	 *            Student level preference of a Teacher.
	 * @param languages
	 *            String[] of languages other than English a Teacher can speak.
	 * @param crimeRecord
	 *            Whether a Teacher has a crime record.
	 * @param availableTimes
	 *            String[] of available times a Teacher can teach.
	 */
	public Teacher(int id, String name, String returningStudent,
			String returningInstrument, String keepReturningStudent,
			String[] instruments, String[] instrumentExperience,
			String genderPreference, String agePreference,
			String levelPreference, String[] languages, String crimeRecord,
			int[] availableTimes) {

		this.id = id;
		this.name = name;
		this.returningStudent = returningStudent;
		this.returningInstrument = returningInstrument;
		this.keepReturningStudent = keepReturningStudent;
		this.instruments = instruments;
		this.instrumentExperience = instrumentExperience;
		this.genderPreference = genderPreference;
		this.agePreference = agePreference;
		this.levelPreference = levelPreference;
		this.languages = languages;
		this.crimeRecord = crimeRecord;
		this.availableTimes = availableTimes;
	}

	/**
	 * Returns the id of the Teacher
	 * 
	 * @return id of a Teacher.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the name of the Teacher
	 * 
	 * @return Name of a Teacher.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the student the Teacher taught last year
	 * 
	 * @return Returning student of a Teacher.
	 */
	public String getReturningStudent() {
		return returningStudent;
	}

	/**
	 * Returns the instrument the Teacher taught last year
	 * 
	 * @return Returning instrument of a Teacher.
	 */
	public String getReturningInstrument() {
		return returningInstrument;
	}

	/**
	 * Returns the response to whether the Teacher wishes to keep their student
	 * from last year
	 * 
	 * @return Whether a Teacher is keeping the returning student.
	 */
	public String getKeepReturningStudent() {
		return keepReturningStudent;
	}

	/**
	 * Returns all of the instruments a Teacher can teach
	 * 
	 * @return Instruments a Teacher can teach.
	 */
	public String[] getInstruments() {
		return instruments;
	}

	/**
	 * Returns the experience the Teacher has with each instrument
	 * 
	 * @return Instrument experience of a Teacher.
	 */
	public String[] getInstrumentExperience() {
		return instrumentExperience;
	}

	/**
	 * Returns the gender preference of the Teacher
	 * 
	 * @return Student gender preference of a Teacher.
	 */
	public String getGenderPreference() {
		return genderPreference;
	}

	/**
	 * Returns the age preference of the Teacher
	 * 
	 * @return Student age preference of a Teacher.
	 */
	public String getAgePreference() {
		return agePreference;
	}

	/**
	 * Returns the experience level preference of the Teacher
	 * 
	 * @return Student level preference of a Teacher.
	 */
	public String getLevelPreference() {
		return levelPreference;
	}

	/**
	 * Returns the languages the Teacher can speak other than English
	 * 
	 * @return Languages a Teacher can speak other than English.
	 */
	public String[] getLanguages() {
		return languages;
	}

	/**
	 * Retuns the crime record of a Teacher
	 * 
	 * @return Crime record of a Teacher.
	 */
	public String getCrimeRecord() {
		return crimeRecord;
	}

	/**
	 * Returns the times a Teacher is available
	 * 
	 * @return Available times of a Teacher.
	 */
	public int[] getAvailableTimes() {
		return availableTimes;
	}
}
