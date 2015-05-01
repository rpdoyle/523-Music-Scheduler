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
	 * @param id Identification number
	 * @param name Student's name
	 * @param age Student's age
	 * @param gender Student's gender
	 * @param returningTeacher Student's returning teacher (if applicable)
	 * @param instrumentOfReturningStudent Student's returning instrument (if applicable)
	 * @param instruments Instruments the Student wants to play
	 * @param instrumentYears Amount of time the Student has played each instrument
	 * @param language Language the Student wishes to be taught lessons in
	 * @param availableTimes Times the Student is available for lessons
	 */
	public Student(int id, String name, String age, String gender, String returningTeacher, String instrumentOfReturningStudent, String[] instruments,  
			String[] instrumentYears, String language, int[] availableTimes){
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.returningTeacher = returningTeacher;
		this.instrumentOfReturningStudent = instrumentOfReturningStudent;
		this.instruments = instruments;
		this.instrumentYears = instrumentYears;
		this.language = language;
		this.availableTimes= availableTimes;
		siblings = new ArrayList<Student>();
	}
	
	/**
	 * @return Student's Identification number
	 */
	public int getID(){
		return id;
	}
	
	/**
	 * @return Student's name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return Student's age
	 */
	public String getAge(){
		return age;
	}
	
	/**
	 * @return Student's gender
	 */
	public String getGender(){
		return gender;
	}
	
	/**
	 * @return Student's returning teacher
	 */
	public String getReturningTeacher(){
		return returningTeacher;
	}
	
	/**
	 * @return Student's returning instrument
	 */
	public String getInstrumentOfReturningStudent(){
		return instrumentOfReturningStudent;
	}
	
	/**
	 * @return String[] of instruments the student wishes to learn
	 */
	public String[] getInstruments(){
		return instruments;
	}
	
	/**
	 * @return String[] of experience the Student has with each instrument
	 */
	public String[] getInstrumentYears(){
		return instrumentYears;
	}
	
	/**
	 * @return Student's preferred language for lessons
	 */
	public String getLanguage(){
		return language;
	}
	
	/**
	 * @return Times the student is available for lessons
	 */
	public int[] getAvailableTimes(){
		return availableTimes;
	}
	
	/**
	 * @return ArrayList of the Student's siblings
	 */
	public ArrayList<Student> getSiblings(){
		return siblings;
	}
}
