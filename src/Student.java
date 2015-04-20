/*
 * File: Student.java
 * Description: This class stores data for a Student.
 */
import java.util.ArrayList;


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
	
	public int getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAge(){
		return age;
	}
	
	public String getGender(){
		return gender;
	}
	
	public String getReturningTeacher(){
		return returningTeacher;
	}
	
	public String getInstrumentOfReturningStudent(){
		return instrumentOfReturningStudent;
	}
	
	public String[] getInstruments(){
		return instruments;
	}
	
	public String[] getInstrumentYears(){
		return instrumentYears;
	}
	
	public String getLanguage(){
		return language;
	}
	
	public int[] getAvailableTimes(){
		return availableTimes;
	}
	
	public ArrayList<Student> getSiblings(){
		return siblings;
	}
}
