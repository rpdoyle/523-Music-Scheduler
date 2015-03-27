/*
 * File: Teacher.java
 * Description: This class stores data for a Teacher.
 */

public class Teacher {

	private int id;
	private String firstName;
	private String lastName;
	private String returningStudent;
	private String returningInstrument;
	private String keepReturningStudent;
	private String[] instruments;
	private String[] instrumentExperience;
	private String genderPreference;
	private String agePreference;
	private String levelPreference;
	private String[] language;
	private String crimeRecord;
	private int[] availableTimes;

	public Teacher(int id, String firstName, String lastName,
			String returningStudent, String returningInstrument,
			String keepReturningStudent, String[] instruments,
			String[] instrumentExperience, String genderPreference,
			String agePreference, String levelPreference, String[] language,
			String crimeRecord, int[] availableTimes) {
		
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.returningStudent = returningStudent;
		this.returningInstrument = returningInstrument;
		this.keepReturningStudent = keepReturningStudent;
		this.instruments = instruments;
		this.instrumentExperience = instrumentExperience;
		this.genderPreference = genderPreference;
		this.agePreference = agePreference;
		this.levelPreference = levelPreference;
		this.language = language;
		this.crimeRecord = crimeRecord;
		this.availableTimes = availableTimes;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getReturningStudent() {
		return returningStudent;
	}

	public String getReturningInstrument() {
		return returningInstrument;
	}

	public String getKeepReturningStudent() {
		return keepReturningStudent;
	}

	public String[] getInstruments() {
		return instruments;
	}

	public String[] getInstrumentExperience() {
		return instrumentExperience;
	}

	public String getGenderPreference() {
		return genderPreference;
	}

	public String getAgePreference() {
		return agePreference;
	}

	public String getLevelPreference() {
		return levelPreference;
	}

	public String[] getLanguage() {
		return language;
	}

	public String getCrimeRecord() {
		return crimeRecord;
	}

	public int[] getAvailableTimes() {
		return availableTimes;
	}
}
