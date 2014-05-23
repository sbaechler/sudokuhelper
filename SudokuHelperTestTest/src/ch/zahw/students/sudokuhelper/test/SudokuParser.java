package ch.zahw.students.sudokuhelper.test;

import ch.zahw.students.sudokuhelper.solve.Sudoku;

public class SudokuParser {
	private Sudoku toSolve;
	private Sudoku solutionSudoku;

	
	public Sudoku getSolutionSudoku() {
		return solutionSudoku;
	}

	public Sudoku getToSolve() {
		return toSolve;
	}

	/**
	 * Converts a Sudoku saved as a String into an int array.
	 * @param sudokuString - The cells are separated by comma, lines are separated by ;.
	 * @return candidates array.
	 */
	public int[][] parseString(String sudokuString) {

		String[] rows = sudokuString.split(";");
		String row = null;
		int[][] candidates = new int[9][9];

		String[] value = null;

		for (int i = 0; i < 9; i++) {
			row = rows[i];
			row = row.trim();
			value = row.split(",");

			for (int j = 0; j < 9; j++) {
			    candidates[i][j] = Integer.parseInt(value[j]);
			}

		}

		return candidates;
	}
}
