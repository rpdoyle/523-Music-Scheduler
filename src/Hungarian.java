import java.util.ArrayList;
// All credit for this work goes to wikihow.com
// http://www.wikihow.com/Use-the-Hungarian-Algorithm

public class Hungarian {
	
	private static int numLines = 0;
	
	public static HungarianResult run(ArrayList<Pair> pairs, ArrayList<RoomDayTime> roomDayTimes, ArrayList<String> specialInstruments) {
		int[][] originalMatrix = createOriginalMatrix(pairs, roomDayTimes, specialInstruments);
		int[][] minMatrix = createMinMatrix(originalMatrix);
		rowReduce(minMatrix); //TODO: if things don't work, make sure we're actually modifying this matrix
		columnReduce(minMatrix); // TODO: if things don't work, make sure we're actually modifying this matrix
		int[][] lineMatrix = calculateLineMatrix(minMatrix);
		
		while (numLines != minMatrix.length) {
			augmentMatrix(minMatrix, lineMatrix); // TODO: if things don't work, make sure we're actually modifying this matrix
			lineMatrix = calculateLineMatrix(minMatrix);
		}
		
		ArrayList<PairTime> pairTimes = choosePairTimes(minMatrix, pairs, roomDayTimes);
		
		if (siblingCheck(pairTimes)) {
			int score = calculateScore(pairTimes);
			return new HungarianResult(pairTimes, score);
		} else {
			return null;
		}
	}
	// From the randomly generated pairs and room-day-times, set up the matrix to start the Hungarian algorithm
	private static int[][] createOriginalMatrix(ArrayList<Pair> pairs, ArrayList<RoomDayTime> roomDayTimes, ArrayList<String> specialInstruments) {
		int numPairs = pairs.size();
		int numRDTs = roomDayTimes.size();
		int[][] originalMatrix;
		
		// Generate original matrix of pairs vs. room day times
		if (numPairs > numRDTs) {
			originalMatrix = new int[numPairs][numPairs];
			
			for (int i = 0; i < numPairs; i++) {
				for (int j = 0; j < numRDTs; j++) {
					if (checkCompatible(pairs.get(i), roomDayTimes.get(j), specialInstruments)) {
						originalMatrix[i][j] = pairs.get(i).getScore();
					} else {
						originalMatrix[i][j] = 0;
					}
				}
				
				for (int j = numRDTs; j < numPairs; j++) {
					originalMatrix[i][j] = 0;
				}
			}
			
		} else {
			originalMatrix = new int[numRDTs][numRDTs];
			
			for (int i = 0; i < numPairs; i++) {
				for (int j = 0; j < numRDTs; j++) {
					if (checkCompatible(pairs.get(i), roomDayTimes.get(j), specialInstruments)) {
						originalMatrix[i][j] = pairs.get(i).getScore();
					} else {
						originalMatrix[i][j] = 0;
					}
				}
			}
			
			for (int i = numPairs; i < numRDTs; i++) {
				for (int j = 0; j < numRDTs; j++) {
					originalMatrix[i][j] = 0;
				}
			}
		}
		
		return originalMatrix;
	}
	
	// Assumes square matrix
	private static int[][] createMinMatrix(int[][] originalMatrix) {
		int dim = originalMatrix.length;
		
		int[][] minMatrix = new int[dim][dim];
		
		int max = Integer.MIN_VALUE;
		
		// Find maximum value in the original matrix
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (originalMatrix[i][j] > max) {
					max = originalMatrix[i][j];
				}
			}
		}
		
		// Adjust every value by subtracting it from the original matrix's max value
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				minMatrix[i][j] = max - originalMatrix[i][j];
			}
		}
		
		return minMatrix;
	}
	
	// Assumes square matrix
	private static void rowReduce(int[][] matrix) {
		int dim = matrix.length;
		
		for (int i = 0; i < dim; i++) {
			int min = Integer.MAX_VALUE;
			
			// Find minimum value in row
			for (int j = 0; j < dim; j++) {
				if (matrix[i][j] < min) {
					min = matrix[i][j];
				}
			}
			
			// Subtract min value from every item in row
			for (int j = 0; j < dim; j++) {
				matrix[i][j] = matrix[i][j] - min;
			}
		}
	}
	
	// Assumes square matrix
	private static void columnReduce(int[][] matrix) {
		int dim = matrix.length;
		
		for (int j = 0; j < dim; j++) {
			boolean reduce = true;
			
			for (int i = 0; i < dim; i++) {
				if (matrix[i][j] == 0) {
					reduce = false;
					break;
				}
			}
			
			if (reduce) {
			
				int min = Integer.MAX_VALUE;
				
				// Find minimum value in column
				for (int i = 0; i < dim; i++) {
					if (matrix[i][j] < min) {
						min = matrix[i][j];
					}
				}
				
				// Reduce the column if the min is not 0
				if (min != 0) {
					for (int i = 0; i < dim; i++) {
						matrix[i][j] = matrix[i][j] - min;
					}
				}
			}
		}
	}
	
	// Assumes a square matrix
	private static int[][] calculateLineMatrix(int[][] matrix) {
		numLines = 0;
		
		int dim = matrix.length;
		// in each spot, covered will hold integer values that indicate how many lines are covering that spot
		int[][] covered = new int[dim][dim];
		// rowZeros and colZeros hold integer values indicating how many uncovered 0s are in each row and column 
		// to begin with
		int[] rowZeros = getRowZeros(matrix, covered);
		int[] colZeros = getColZeros(matrix, covered);
		boolean uncoveredZeros = true;
		boolean lineDrawn;
		
		// Note: we assume there are uncovered zeros to begin with 
		// TODO: see if this is a problem
		while (uncoveredZeros) {
			int maxZeros = Integer.MIN_VALUE;
			lineDrawn = false;
			// Determine which row or column has the most 0s
			for (int i = 0; i < dim; i++) {
				if (rowZeros[i] > maxZeros) {
					maxZeros = rowZeros[i];
				}
				
				if (colZeros[i] > maxZeros) {
					maxZeros = rowZeros[i];
				}
			}
			// Check: if maxZeros was found in a row, we need to update the elements in covered in the row to be 1, since we are "drawing a line"
			
			for (int i = 0; i < dim; i++) {
				if (rowZeros[i] == maxZeros) {
					for (int j = 0; j < dim; j++) {
						covered[i][j]++;
					}
					lineDrawn = true;
					numLines++;
					break;
				}
			}
			// If we did not draw the line across a row, then we need to find the column that has the most 0s and draw the line down the column
			
			if (!lineDrawn) {
				for (int j = 0; j < dim; j++) {
					if (colZeros[j] == maxZeros) {
						for (int i = 0; i < dim; i++) {
							covered[i][j]++;
						}
						lineDrawn = true;
						numLines++;
						break;
					}
				}
			}
			// Recalculate the new number of uncovered 0s in each row and column
			rowZeros = getRowZeros(matrix, covered);
			colZeros = getColZeros(matrix, covered);
			
			uncoveredZeros = false;
			// Check to see if we still have any uncovered 0s in the matrix. If so, continue with the while loop
			for (int i = 0; i < dim; i++) {
				if (rowZeros[i] > 0) {
					uncoveredZeros = true;
					break;
				}
				
				if (colZeros[i] > 0) {
					uncoveredZeros = true;
					break;
				}
			}
		}
		
		return covered;
		
	}
	// Calculates how many uncovered zeros are in a row and returns those values in the 1-D array rowZeros
	private static int[] getRowZeros(int[][] matrix, int[][] covered) {
		int dim = matrix.length;
		int[] rowZeros = new int[dim];
		
		for (int i = 0; i < dim; i++) {
			int num = 0;
			for (int j = 0; j < dim; j++) {
				if ((matrix[i][j] == 0) && (covered[i][j] == 0)) {
					num++;
				}
			}
			rowZeros[i] = num;
		}
		
		return rowZeros;
	}
	// Calculates how many uncovered zeros are in a column and returns those values in the 1-D array ColZeros
	private static int[] getColZeros(int[][] matrix, int[][] covered) {
		int dim = matrix.length;
		int[] colZeros = new int[dim];
		
		for (int j = 0; j < dim; j++) {
			int num = 0;
			for (int i = 0; i < dim; i++) {
				if ((matrix[i][j] == 0) && (covered[i][j] == 0)) {
					num++;
				}
			}
			colZeros[j] = num;
		}
		
		return colZeros;
	}
	// Calculates the the number of 0s in each row in the given matrix
	private static int[] getRowZeros(int[][] matrix) {
		int dim = matrix.length;
		int[] rowZeros = new int[dim];
		
		for (int i = 0; i < dim; i++) {
			int num = 0;
			for (int j = 0; j < dim; j++) {
				if (matrix[i][j] == 0) {
					num++;
				}
			}
			rowZeros[i] = num;
		}
		
		return rowZeros;
	}
	// Calculates the number of 0s in each column in the given matrix
	private static int[] getColZeros(int[][] matrix) {
		int dim = matrix.length;
		int[] colZeros = new int[dim];
		
		for (int j = 0; j < dim; j++) {
			int num = 0;
			for (int i = 0; i < dim; i++) {
				if (matrix[i][j] == 0) {
					num++;
				}
			}
			colZeros[j] = num;
		}
		
		return colZeros;
	}
	// Performs the augmentation of the "covered" matrix
	private static void augmentMatrix(int[][] matrix, int[][] lineMatrix) {
		int dim = matrix.length;
		
		int min = Integer.MAX_VALUE;
		
		// Find minimum uncovered element
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (matrix[i][j] < min && lineMatrix[i][j] == 0) {
					min = matrix[i][j];
				}
			}
		}
		
		// Add minimum to every covered element, twice to elements covered twice
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (lineMatrix[i][j] > 0) {
					matrix[i][j] += lineMatrix[i][j]*min;
				}
			}
		}
		// TODO: check why we are doing these steps below
		// Find minimum element in matrix
		min = Integer.MAX_VALUE;
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (matrix[i][j] < min) {
					min = matrix[i][j];
				}
			}
		}
		
		// Subtract the min from every element in the matrix
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				matrix[i][j] -= min;
			}
		}
	}
	// Given a matrix that has been through all of the steps of the Hungarian algorithm, create the PairTime objects
	private static ArrayList<PairTime> choosePairTimes(int[][] matrix, ArrayList<Pair> pairs, ArrayList<RoomDayTime> roomDayTimes) {
		int dim = matrix.length;
		
		ArrayList<PairTime> pairTimes = new ArrayList<PairTime>();
		// Calculate how many 0s are in each row that the Hungarian algorithm produces
		int[] rowZeros = getRowZeros(matrix);
		int[] colZeros = getColZeros(matrix);
		
		while (true) {
			boolean minInRow = true;
			int minIndex = 0;
			int min = Integer.MAX_VALUE;
			
			// Find the minimum positive number
			for (int i = 0; i < dim; i++) {
				if (rowZeros[i] < min && rowZeros[i] > 0) {
					min = rowZeros[i];
					minInRow = true;
					minIndex = i;
				}
				
				if (colZeros[i] < min && colZeros[i] > 0) {
					min = colZeros[i];
					minInRow = false;
					minIndex = i;
				}
			}
			
			// If the minimum value does not change, then we know we have a complete solution
			if (min == Integer.MAX_VALUE) {
				break;
			}
			
			if (minInRow) {
				for (int j = 0; j < dim; j++) {
					if (matrix[minIndex][j] == 0) {
						rowZeros[minIndex] = 0;// TODO: Clarify: we are arbitrarily choosing the first 0 in the row that has the minimum number of 0s right?
						colZeros[j] = 0;
						
						for (int k = 0; k < dim; k++) {
							if (matrix[minIndex][k] == 0) {
								colZeros[k]--;
							}
							
							if (matrix[k][j] == 0) {
								rowZeros[k]--;
							}
						}
						
						// Make sure the pair and room day time we want both exist
						if (minIndex < pairs.size() && j < roomDayTimes.size()) {
							PairTime pairTime = new PairTime(pairs.get(minIndex), roomDayTimes.get(j));
							pairTimes.add(pairTime);
						}

						break;
					}
				}
			} else {
				for (int i = 0; i < dim; i++) {
					if (matrix[i][minIndex] == 0) {
						rowZeros[i] = 0;
						colZeros[minIndex] = 0;
						
						for (int k = 0; k < dim; k++) {
							if (matrix[k][minIndex] == 0) {
								rowZeros[k]--;
							}
							
							if (matrix[i][k] ==  0) {
								colZeros[k]--;
							}
						}
						
						// Make sure the pair and room day time we want both exist
						if (i < pairs.size() && minIndex < roomDayTimes.size()) {
							PairTime pairTime = new PairTime(pairs.get(i), roomDayTimes.get(minIndex));
							pairTimes.add(pairTime);
						}
						
						break;
					}
				}
			}
		}
		
		return pairTimes;
	}

	// Siblings must be scheduled at the same time. siblingCheck verifies that all siblings were scheduled for the same time
	private static boolean siblingCheck(ArrayList<PairTime> pairTimes) {
		ArrayList<Student> siblingsToCheck = new ArrayList<Student>();
		ArrayList<Integer> siblingTimes = new ArrayList<Integer>();
		ArrayList<Student> checkedSiblings = new ArrayList<Student>();
		
		for (int i = 0; i < pairTimes.size(); i++) {
			if (pairTimes.get(i).getPair().getStudent().getSiblings().size() > 0) {
				Student student = pairTimes.get(i).getPair().getStudent();
				int time = pairTimes.get(i).getRoomDayTime().getTime();
				
				if (checkedSiblings.contains(student)) {
					continue;
				}
				
				if (siblingsToCheck.contains(student)) {
					int index = siblingsToCheck.indexOf(student);
					if (time == siblingTimes.get(index)) {
						checkedSiblings.add(student);
						siblingsToCheck.remove(index);
						siblingTimes.remove(index);
						continue;
					} else {
						return false;
					}
				}
				
				for (int s = 0; s < student.getSiblings().size(); s++) {
					Student sibling = student.getSiblings().get(s);
					siblingsToCheck.add(sibling);
					siblingTimes.add(time);
				}
			}
		}
		
		if (siblingsToCheck.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	// Returns the sum of all of the Student-Teacher pairs that were included in the list pairTimes
	private static int calculateScore(ArrayList<PairTime> pairTimes) {
		int score = 0;
		
		for (int i = 0; i < pairTimes.size(); i++) {
			score += pairTimes.get(i).getPair().getScore();
		}
		
		return score;
	}
	
	// Verifies that if a pair is assigned to a room that the pair's instrument is supported by that room
	private static boolean checkCompatible(Pair pair, RoomDayTime roomDayTime, ArrayList<String> specialInstruments) {
		if (specialInstruments.contains(pair.getInstrument())) {
			if (!roomDayTime.getRoom().getSpecialInstruments().contains(pair.getInstrument())) {
				return false;
			}
		}
		
		if (!pair.getMutualTimes().contains(roomDayTime.getTime())) {
			return false;
		}
		
		return true;
		
	}
	
}
