/*
 * File: HungarianTestFaith.java
 * Description: Implements JUnit tests for the following methods in Hungarian.java:
 * createMinMatrix, rowReduce, columnReduce, augmentMatrix, choosePairTimes, siblingCheck and calculateScore
 * 	
 */

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

public class HungarianTestFaith {
	
	@Test
	public void testCreateMinMatrix(){
		int[][] matrix = new int[4][4];
		int num = 0;
		for (int i = 0; i < 4; i++){
			for (int j = 0; j<4;j++){
				matrix[i][j] = num;
				num++;
			}
		}
		int[][] resultingMatrix = {{15,14,13,12}, {11,10,9,8}, {7,6,5,4}, {3,2,1,0}};
		assertArrayEquals(resultingMatrix, Hungarian.createMinMatrix(matrix));
	}
	
	@Test
	public void testCreateMinMatrix2(){
		int[][] matrix = {{3,5,6,1}, {2,3,4,1}, {5,2,2,3}, {4,3,1,0}};
		int[][] resultingMatrix = {{3,1,0,5}, {4,3,2,5}, {1,4,4,3}, {2,3,5,6}};
		assertArrayEquals(resultingMatrix, Hungarian.createMinMatrix(matrix));
	}
	
	@Test
	public void testRowReduce(){
		int[][] matrix = {{3,1,0,5}, {4,3,2,5}, {1,4,4,3}, {2,3,5,6}};
		int[][] resultingMatrix = {{3,1,0,5}, {2,1,0,3}, {0,3,3,2}, {0,1,3,4}};
		Hungarian.rowReduce(matrix);
		assertArrayEquals(resultingMatrix, matrix);
	}
	
	@Test
	public void testRowReduce2(){
		int[][] matrix = {{3,5,6,1}, {2,3,4,1}, {5,2,2,3}, {4,3,1,0}};
		int[][] resultingMatrix = {{2,4,5,0}, {1,2,3,0}, {3,0,0,1}, {4,3,1,0}};
		Hungarian.rowReduce(matrix);
		assertArrayEquals(resultingMatrix, matrix);
	}
	
	@Test
	public void testColumnReduce(){
		int[][] matrix = {{3,1,0,5}, {4,3,2,5}, {1,4,4,3}, {2,3,5,6}};
		int[][] resultingMatrix = {{2,0,0,2}, {3,2,2,2}, {0,3,4,0}, {1,2,5,3}};
		Hungarian.columnReduce(matrix);
		assertArrayEquals(resultingMatrix, matrix);
	}
	
	@Test
	public void testColumnReduce2(){
		int[][] matrix = {{3,5,6,1}, {2,3,4,1}, {5,2,2,3}, {4,3,1,0}};
		int[][] resultingMatrix = {{1,3,5,1}, {0,1,3,1}, {3,0,1,3}, {2,1,0,0}};
		Hungarian.columnReduce(matrix);
		assertArrayEquals(resultingMatrix, matrix);
	}

	@Test
	public void testCalculateScore(){
		Student testStudent = new Student(1, "Faith Collins", "21", "Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"}, new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		Teacher testTeacher = new Teacher(1, "Selena Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		Pair testPair = new Pair(testStudent, testTeacher, 120, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		Pair testPair2 = new Pair(testStudent, testTeacher, 230, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		Pair testPair3 = new Pair(testStudent, testTeacher, 300, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(Arrays.asList("Piano", "Drums")), new int[] {500, 1670, 4309});
		RoomDayTime testRoomDayTime = new RoomDayTime(testRoom, 500);
		PairTime testPairTime = new PairTime(testPair, testRoomDayTime);
		PairTime testPairTime2 = new PairTime(testPair2, testRoomDayTime);
		PairTime testPairTime3 = new PairTime(testPair3, testRoomDayTime);
		ArrayList<PairTime> pairTime= new ArrayList<PairTime>();
		pairTime.add(testPairTime);
		pairTime.add(testPairTime2);
		pairTime.add(testPairTime3);
		assertEquals(650, Hungarian.calculateScore(pairTime));
	}
	
	@Test
	public void testChoosePairTimes(){
		
		// The matrix to be passed
		int[][] matrix = {{2,0,1,1}, {0,3,4,5}, {0,2,2,0}, {4,3,0,1}};
		
		// Building up Student, Teacher and RoomDayTime objects
		Student testStudent = new Student(1, "Faith Collins", "21", "Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"}, new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		Student testStudent2 = new Student(2, "Sarah Collins", "21", "Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"}, new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		Student testStudent3 = new Student(3, "Claire Collins", "21", "Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"}, new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		Student testStudent4 = new Student(4, "Lydia Collins", "21", "Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"}, new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		Teacher testTeacher = new Teacher(1, "Selena Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		Teacher testTeacher2 = new Teacher(2, "Aida Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		Teacher testTeacher3 = new Teacher(3, "Juan Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		Teacher testTeacher4 = new Teacher(4, "Wanda Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(Arrays.asList("Piano", "Drums")), new int[] {500, 1234, 1670, 4309});
		RoomDayTime testRoomDayTime = new RoomDayTime(testRoom, 500);
		RoomDayTime testRoomDayTime2 = new RoomDayTime(testRoom, 1234);
		RoomDayTime testRoomDayTime3 = new RoomDayTime(testRoom, 1670);
		RoomDayTime testRoomDayTime4 = new RoomDayTime(testRoom, 4309);
		
		// Creating the ArrayList of all RoomDayTime objects
		ArrayList<RoomDayTime> rdt= new ArrayList<RoomDayTime>();
		rdt.add(testRoomDayTime);
		rdt.add(testRoomDayTime2);
		rdt.add(testRoomDayTime3);
		rdt.add(testRoomDayTime4);
		
		// Creating the test Pairs
		Pair testPair = new Pair(testStudent, testTeacher, 120, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		Pair testPair2 = new Pair(testStudent2, testTeacher2, 220, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		Pair testPair3 = new Pair(testStudent3, testTeacher3, 320, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		Pair testPair4 = new Pair(testStudent4, testTeacher4, 420, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		
		// Creating the ArrayList of all Pairs
		ArrayList<Pair> p = new ArrayList<Pair>();
		p.add(testPair);
		p.add(testPair2);
		p.add(testPair3);
		p.add(testPair4);
		
		// Creating the PairTimes that are to be expected
		PairTime testPairTime = new PairTime(testPair, testRoomDayTime2);
		PairTime testPairTime2 = new PairTime(testPair2, testRoomDayTime);
		PairTime testPairTime3 = new PairTime(testPair3, testRoomDayTime4);
		PairTime testPairTime4 = new PairTime(testPair4, testRoomDayTime3);
		
		// Creating the expected ArrayList of PairTimes
		ArrayList<PairTime> pairTime= new ArrayList<PairTime>();
		pairTime.add(testPairTime);
		pairTime.add(testPairTime2);
		pairTime.add(testPairTime3);
		pairTime.add(testPairTime4);
		PairTimeComparator ptc = new PairTimeComparator();
		assertEquals(0, ptc.compareLists(pairTime, Hungarian.choosePairTimes(matrix, p, rdt)));
	}
	
	@Test
	public void testAugmentMatrix(){
		int[][] original = {{6, 0, 0, 36, 43}, {48, 74, 9, 18, 0}, {0, 0, 10, 0, 8}, {22, 35, 17, 10, 0}, {61, 0, 45, 11, 4}};
		int[][] covered = {{1, 1, 1, 1, 2}, {0, 0, 0, 0, 1}, {1, 1, 1, 1, 2}, {0, 0, 0, 0, 1}, {1, 1, 1, 1, 2}};
		int[][] expected = {{6, 0, 0, 36, 47}, {44, 70 ,5, 14, 0}, {0, 0, 10, 0, 12}, {18, 31, 13, 6, 0}, {61, 0, 45, 11, 8}};
		Hungarian.augmentMatrix(original,covered);
		assertArrayEquals(expected, original);
	}
	
	@Test
	public void testAugmentMatrix2(){
		int[][] original = {{3, 63, 0, 50, 3}, {63, 0, 41, 68, 8}, {52, 49, 0, 50, 57}, {0, 7, 16, 0, 0}, {49, 53, 0, 91, 76}};
		int[][] covered = {{0, 0, 1, 0, 0}, {1, 1, 2, 1, 1}, {0, 0, 1, 0, 0}, {1, 1, 2, 1, 1}, {0, 0, 1, 0, 0}};
		int[][] expected = {{0, 60, 0, 47, 0}, {63, 0, 44, 68, 8}, {49, 46, 0, 47, 54}, {0, 7, 19, 0,0}, {46, 50, 0, 88, 73}};
		Hungarian.augmentMatrix(original,covered);
		assertArrayEquals(expected, original);
	}
	
	/*@Test
	public void testSiblingCheck(){
		Student testStudent = new Student(1, "Faith Collins", "21", "Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"}, new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		Student testStudent2 = new Student(2, "Sarah Collins", "21", "Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"}, new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		testStudent.getSiblings().add(testStudent2);
		testStudent2.getSiblings().add(testStudent);
		Teacher testTeacher = new Teacher(1, "Selena Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		Teacher testTeacher2 = new Teacher(2, "Aida Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		Pair testPair = new Pair(testStudent, testTeacher, 120, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		Pair testPair2 = new Pair(testStudent2, testTeacher2, 220, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(Arrays.asList("Piano", "Drums")), new int[] {500, 1234, 1670, 4309});
		Room testRoom2 = new Room("Test Name 210", new ArrayList<String>(Arrays.asList("Piano", "Drums")), new int[] {500, 1234, 1670, 4309});
		RoomDayTime testRoomDayTime = new RoomDayTime(testRoom, 500);
		RoomDayTime testRoomDayTime2 = new RoomDayTime(testRoom2, 500);
		PairTime testPairTime = new PairTime(testPair, testRoomDayTime);
		PairTime testPairTime2 = new PairTime(testPair2, testRoomDayTime2);
		ArrayList<PairTime> pairTime= new ArrayList<PairTime>();
		pairTime.add(testPairTime);
		pairTime.add(testPairTime2);
		assertEquals(true, Hungarian.siblingCheck(pairTime));
	}*/
}
