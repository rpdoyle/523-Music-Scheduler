import java.util.ArrayList;

/**
 * Pair is the class that encapsulates a Student object and a Teacher object.
 * This pairing includes the score (assigned by ScoringEngine), the mutual times
 * at which both individuals can attend a lesson, and the instrument for which
 * the lesson will be administered.
 *
 */
public class Pair {

	private Student student;
	private Teacher teacher;
	private int score;
	private ArrayList<Integer> mutualTimes;
	private String instrument;

	/**
	 * The constructor for a Pair object.
	 * 
	 * @param student
	 *            the student in the Pair
	 * @param teacher
	 *            the teacher in the Pair
	 * @param score
	 *            the score of the Pair
	 * @param mutualTimes
	 *            the mutual times at which the student and teacher can meet
	 * @param instrument
	 *            the instrument for which the lesson will be administered
	 */
	public Pair(Student student, Teacher teacher, int score,
			ArrayList<Integer> mutualTimes, String instrument) {
		this.student = student;
		this.teacher = teacher;
		this.score = score;
		this.mutualTimes = mutualTimes;
		this.instrument = instrument;
	}

	/**
	 * Returns the Student that is in this Pair.
	 * 
	 * @return the Student in the Pair
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * Returns the Teacher in the Pair.
	 * 
	 * @return the Teacher in the Pair
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * Returns the score of this Pair. This score is calculated using
	 * ScoringEngine.
	 * 
	 * @return the score given to the Pair
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Returns the mutual times at which a Pair can meet.
	 * 
	 * @return the mutual times at which the Pair can meet
	 */
	public ArrayList<Integer> getMutualTimes() {
		return mutualTimes;
	}

	/**
	 * Returns the instrument for which the lesson will be administered. This
	 * instrument will be preferred by the student and can be taught by the
	 * teacher. The student's highest preference that can be taught by the
	 * teacher will be selected.
	 * 
	 * @return the instrument for which the lesson will be administered
	 */
	public String getInstrument() {
		return instrument;
	}
}