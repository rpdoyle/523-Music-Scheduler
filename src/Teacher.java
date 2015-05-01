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
	 * @param name First and last name of a Teacher
	 * @param returningStudent First and last name of the returning student of a Teacher.
	 * @param returningInstrument The returning instrument of a Teacher.
	 * @param keepReturningStudent Whether a Teacher wants to keep their returning student.
	 * @param instruments String[] of instruments the Teacher can teach.
	 * @param instrumentExperience String[] of instrument experience of a Teacher.
	 * @param genderPreference Student gender preference of a Teacher.
	 * @param agePreference Student age preference of a Teacher.
	 * @param levelPreference Student level preference of a Teacher.
	 * @param languages String[] of languages other than English a Teacher can speak.
	 * @param crimeRecord Whether a Teacher has a crime record.
	 * @param availableTimes String[] of available times a Teacher can teach.
	 */
	public Teacher(int id, String name, String returningStudent, String returningInstrument, 
			String keepReturningStudent, String[] instruments, String[] instrumentExperience, String genderPreference,
			String agePreference, String levelPreference, String[] languages, String crimeRecord, int[] availableTimes) {
		
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
	 * @return Id of a Teacher.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return Name of a Teacher.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Returning student of a Teacher.
	 */
	public String getReturningStudent() {
		return returningStudent;
	}

	/**
	 * @return Returning instrument of a Teacher.
	 */
	public String getReturningInstrument() {
		return returningInstrument;
	}

	/**
	 * @return Whether a Teacher is keeping the returning student.
	 */
	public String getKeepReturningStudent() {
		return keepReturningStudent;
	}

	/**
	 * @return Instruments a Teacher can teach.
	 */
	public String[] getInstruments() {
		return instruments;
	}

	/**
	 * @return Instrument experience of a Teacher.
	 */
	public String[] getInstrumentExperience() {
		return instrumentExperience;
	}

	/**
	 * @return Student gender preference of a Teacher.
	 */
	public String getGenderPreference() {
		return genderPreference;
	}

	/**
	 * @return Student age preference of a Teacher.
	 */
	public String getAgePreference() {
		return agePreference;
	}

	/**
	 * @return Student level preference of a Teacher.
	 */
	public String getLevelPreference() {
		return levelPreference;
	}

	/**
	 * @return Languages a Teacher can speak other than English.
	 */
	public String[] getLanguages() {
		return languages;
	}

	/**
	 * @return Crime record of a Teacher.
	 */
	public String getCrimeRecord() {
		return crimeRecord;
	}

	/**
	 * @return Available times of a Teacher.
	 */
	public int[] getAvailableTimes() {
		return availableTimes;
	}
}
