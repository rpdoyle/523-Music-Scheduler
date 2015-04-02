/*
 * File: ExcelReaderTest.java
 * Description: Implements JUnit tests for ExcelReader.java
 * 	
 */

import static org.junit.Assert.*;

import org.junit.Test;

// This class implements tests for the methods in ExcelReader.java
public class ExcelReaderTest {

	@Test
	// This method tests ExcelReader.getStartTimeArr()
	public void testGetStartTimeArr() {
		String[] times;
		
		// Improperly formatted start times
		try {
			times = new String[] {"00:00-6:00 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"21:00-6:00 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"13:00-6:00 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"010:00-6:00 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"159-6:00 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"1:60-6:00 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"1:5-6:00 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}

		// No dash between start and end times
		try {
			times = new String[] {"1:596:00 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		// Improperly formatted end times
		try {
			times = new String[] {"1:00-00:00 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"1:00-21:00 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"1:00-13:00 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"1:00-010:00 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"1:00-159 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"1:00-1:60 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"1:00-1:5 PM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		// Invalid am, AM, pm, or PM
		// Improperly formatted start times		
		try {
			times = new String[] {"1:00-6:00 Pm"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"1:00-6:00 pM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"1:00-6:00 Am"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"1:00-6:00 aM"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"1:00-6:00 AA"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		try {
			times = new String[] {"1:00-6:00 PP"};
			ExcelReader.getStartTimeArr(times, 0);
			fail("Did not throw exception");
		} catch (InvalidInputFormatException e) {
			
		}
		
		// Empty array
		try {
			times = new String[0];
			assertArrayEquals(new int[0], ExcelReader.getStartTimeArr(times, 0));
		} catch (InvalidInputFormatException e) {
			fail("Threw unexpected exception");
		}
		
		// Valid inputs for different day offsets
		try {
			times = new String[] {"12:00-3:51AM", "12:00-3:51PM", "8:37-9:53 AM", "5:00-6:00 PM", "04:00-5:00 am", "10:00-11:00pm"};
			assertArrayEquals(new int[] {0, 720, 517, 1020, 240, 1320}, ExcelReader.getStartTimeArr(times, 0));
		} catch (InvalidInputFormatException e) {
			fail("Threw unexpected exception");
		}
		
		try {
			times = new String[] {"12:00-3:51AM", "12:00-3:51PM", "8:37-9:53 AM", "5:00-6:00 PM", "04:00-5:00 am", "10:00-11:00pm"};
			assertArrayEquals(new int[] {1440, 2160, 1957, 2460, 1680, 2760}, ExcelReader.getStartTimeArr(times, 1));
		} catch (InvalidInputFormatException e) {
			fail("Threw unexpected exception");
		}
		
		try {
			times = new String[] {"12:00-3:51AM", "12:00-3:51PM", "8:37-9:53 AM", "5:00-6:00 PM", "04:00-5:00 am", "10:00-11:00pm"};
			assertArrayEquals(new int[] {2880, 3600, 3397, 3900, 3120, 4200}, ExcelReader.getStartTimeArr(times, 2));
		} catch (InvalidInputFormatException e) {
			fail("Threw unexpected exception");
		}
		
		try {
			times = new String[] {"12:00-3:51AM", "12:00-3:51PM", "8:37-9:53 AM", "5:00-6:00 PM", "04:00-5:00 am", "10:00-11:00pm"};
			assertArrayEquals(new int[] {4320, 5040, 4837, 5340, 4560, 5640}, ExcelReader.getStartTimeArr(times, 3));
		} catch (InvalidInputFormatException e) {
			fail("Threw unexpected exception");
		}
		
		try {
			times = new String[] {"12:00-3:51AM", "12:00-3:51PM", "8:37-9:53 AM", "5:00-6:00 PM", "04:00-5:00 am", "10:00-11:00pm"};
			assertArrayEquals(new int[] {5760, 6480, 6277, 6780, 6000, 7080}, ExcelReader.getStartTimeArr(times, 4));
		} catch (InvalidInputFormatException e) {
			fail("Threw unexpected exception");
		}

	}

}
