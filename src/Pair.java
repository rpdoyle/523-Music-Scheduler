import java.util.ArrayList;

/* 
 * File: Pair.java
 * Description: This class stores data for a Student/Teacher pair.
 */

public class Pair {

	private Student student;
	private Teacher teacher;
	private int score;
	private ArrayList<Integer> mutualTimes;

	public Pair(Student student, Teacher teacher, int score,
			ArrayList<Integer> mutualTimes) {
		this.student = student;
		this.teacher = teacher;
		this.score = score;
		this.mutualTimes = mutualTimes;
	}

	public Student getStudent() {
		return student;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public int getScore() {
		return score;
	}

	public ArrayList<Integer> getMutualTimes() {
		return mutualTimes;
	}
}
