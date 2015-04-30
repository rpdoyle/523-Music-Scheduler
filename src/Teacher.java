/*
 * File: Teacher.java
 * Description: This class stores data for a Teacher.
 */

// TODO: Remove keepReturningStudent and just don't store a returning student
// 		 if the teacher doesn't want to keep them?

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

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
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

	public String[] getLanguages() {
		return languages;
	}

	public String getCrimeRecord() {
		return crimeRecord;
	}

	public int[] getAvailableTimes() {
		return availableTimes;
	}
}
