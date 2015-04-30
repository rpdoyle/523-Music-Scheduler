/*
 * File: Hungarian.java
 * Description: Implements the Hungarian Algorithm
 */

import java.util.ArrayList;

public class Hungarian {

	private static int numLines = 0;

	// Executes the Hungarian algorithm, which is used to schedule
	// Student/Teacher pairs
	public static HungarianResult run(ArrayList<Pair> pairs,
			ArrayList<RoomDayTime> roomDayTimes,
			ArrayList<String> specialInstruments) {
		int[][] originalMatrix = createOriginalMatrix(pairs, roomDayTimes,
				specialInstruments);
		int[][] minMatrix = createMinMatrix(originalMatrix);
		rowReduce(minMatrix);
		columnReduce(minMatrix);
		int[][] lineMatrix = calculateLineMatrix(minMatrix);

		while (numLines != minMatrix.length) {
			augmentMatrix(minMatrix, lineMatrix);
			lineMatrix = calculateLineMatrix(minMatrix);
		}

		ArrayList<PairTime> pairTimes = choosePairTimes(minMatrix, pairs,
				roomDayTimes);

		int score = calculateScore(pairTimes);
		return new HungarianResult(pairTimes, score);
	}

	// From the randomly generated pairs and room-day-times, set up the matrix
	// to start the Hungarian algorithm
	public static int[][] createOriginalMatrix(ArrayList<Pair> pairs,
			ArrayList<RoomDayTime> roomDayTimes,
			ArrayList<String> specialInstruments) {
		int numPairs = pairs.size();
		int numRDTs = roomDayTimes.size();
		int[][] originalMatrix;

		// Generate original matrix of pairs vs. RoomDayTimes
		if (numPairs > numRDTs) {
			originalMatrix = new int[numPairs][numPairs];

			for (int i = 0; i < numPairs; i++) {
				for (int j = 0; j < numRDTs; j++) {
					if (checkCompatible(pairs.get(i), roomDayTimes.get(j),
							specialInstruments)) {
						originalMatrix[i][j] = pairs.get(i).getScore();
					} else {
						originalMatrix[i][j] = 0;
					}
				}

				for (int j = numRDTs; j < numPairs; j++) {
					originalMatrix[i][j] = 0;
				}
			}

			// Note that this case also catches when the number of pairs and
			// room day times are the same. This is fine because of the
			// structure of the final loop in this case.
		} else {
			originalMatrix = new int[numRDTs][numRDTs];

			for (int i = 0; i < numPairs; i++) {
				for (int j = 0; j < numRDTs; j++) {
					if (checkCompatible(pairs.get(i), roomDayTimes.get(j),
							specialInstruments)) {
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
	public static int[][] createMinMatrix(int[][] originalMatrix) {
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

		// Adjust every value by subtracting it from the original
		// matrix's max value
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				minMatrix[i][j] = max - originalMatrix[i][j];
			}
		}

		return minMatrix;
	}

	// Assumes square matrix
	public static void rowReduce(int[][] matrix) {
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
	public static void columnReduce(int[][] matrix) {
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
	public static int[][] calculateLineMatrix(int[][] matrix) {
		numLines = 0;

		int dim = matrix.length;

		// In each spot, covered will hold integer values that indicate how many
		// lines are covering that spot
		int[][] covered = new int[dim][dim];

		// rowZeros and colZeros hold integer values indicating how many
		// uncovered 0s are in each row and column to begin with
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
					maxZeros = colZeros[i];
				}
			}

			// Check: if maxZeros was found in a row, we need to update the
			// elements in covered in the row to be 1, since we are
			// "drawing a line"
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

			// If we did not draw the line across a row, then we need to find
			// the column that has the most 0s and draw the line down the column
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

			// Check to see if we still have any uncovered 0s in the matrix. If
			// so, continue with the while loop
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

	// Calculates how many uncovered zeros are in a row and returns those values
	// in the 1-D array rowZeros
	public static int[] getRowZeros(int[][] matrix, int[][] covered) {
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

	// Calculates how many uncovered zeros are in a column and returns those
	// values in the 1-D array ColZeros
	public static int[] getColZeros(int[][] matrix, int[][] covered) {
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
	public static int[] getRowZeros(int[][] matrix) {
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
	public static int[] getColZeros(int[][] matrix) {
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
	public static void augmentMatrix(int[][] matrix, int[][] lineMatrix) {
		int dim = matrix.length;

		int min = Integer.MAX_VALUE;

		// Find minimum uncovered element
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (matrix[i][j] < min && matrix[i][j] > 0) {
					min = matrix[i][j];
				}
			}
		}

		// Add minimum to the double-crossed elements and subtract from all
		// uncovered elements
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (lineMatrix[i][j] == 0) {
					matrix[i][j] -= min;
				} else if (lineMatrix[i][j] == 2) {
					matrix[i][j] += min;

				}
			}
		}
	}

	// Given a matrix that has been through all of the steps of the Hungarian
	// algorithm, create the PairTime objects
	public static ArrayList<PairTime> choosePairTimes(int[][] matrix,
			ArrayList<Pair> pairs, ArrayList<RoomDayTime> roomDayTimes) {
		int dim = matrix.length;

		ArrayList<PairTime> pairTimes = new ArrayList<PairTime>();

		// Calculate how many 0s are in each row that the Hungarian algorithm
		// produces
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

			// If the minimum value does not change, then we know we have a
			// complete solution
			if (min == Integer.MAX_VALUE) {
				break;
			}

			if (minInRow) {
				for (int j = 0; j < dim; j++) {
					if (matrix[minIndex][j] == 0 && colZeros[j] > 0) {
						rowZeros[minIndex] = 0;
						colZeros[j] = 0;

						for (int k = 0; k < dim; k++) {
							if (matrix[minIndex][k] == 0) {
								colZeros[k]--;
							}

							if (matrix[k][j] == 0) {
								rowZeros[k]--;
							}
						}
						
						// Make sure the pair and room day time that
						// we want both exist
						if (minIndex < pairs.size() && j < roomDayTimes.size()) {
							PairTime pairTime = new PairTime(
									pairs.get(minIndex), roomDayTimes.get(j));
							pairTimes.add(pairTime);
						}

						break;
					}
				}
			} else {
				for (int i = 0; i < dim; i++) {
					if (matrix[i][minIndex] == 0 && rowZeros[i] > 0) {
						rowZeros[i] = 0;
						colZeros[minIndex] = 0;

						for (int k = 0; k < dim; k++) {
							if (matrix[k][minIndex] == 0) {
								rowZeros[k]--;
							}

							if (matrix[i][k] == 0) {
								colZeros[k]--;
							}
						}

						// Make sure the pair and room day time
						// that we want both exist
						if (i < pairs.size() && minIndex < roomDayTimes.size()) {
							PairTime pairTime = new PairTime(pairs.get(i),
									roomDayTimes.get(minIndex));
							pairTimes.add(pairTime);
						}

						break;
					}
				}
			}
		}
		return pairTimes;
	}

	// Returns the sum of all of the Student-Teacher pairs that were included in
	// the list pairTimes
	// This method was made public for testing
	public static int calculateScore(ArrayList<PairTime> pairTimes) {
		int score = 0;

		for (int i = 0; i < pairTimes.size(); i++) {
			score += pairTimes.get(i).getPair().getScore();
		}

		return score;
	}

	// Verifies that if a pair is assigned to a room, the pair's instrument
	// and time are supported by that room
	public static boolean checkCompatible(Pair pair, RoomDayTime roomDayTime,
			ArrayList<String> specialInstruments) {
		if (specialInstruments.contains(pair.getInstrument())) {
			if (!roomDayTime.getRoom().getSpecialInstruments()
					.contains(pair.getInstrument())) {
				return false;
			}
		}

		if (!pair.getMutualTimes().contains(roomDayTime.getTime())) {
			return false;
		}

		return true;
	}

	// Returns the number of lines drawn by the last call to
	// calculateLineMatrix()
	public static int getNumLines() {
		return numLines;
	}
}
