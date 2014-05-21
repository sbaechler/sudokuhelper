package ch.zahw.students.sudokuhelper.test;

import android.util.Log;

import ch.zahw.students.sudokuhelper.solve.Sudoku;

public class SudokuParser {
        private static final String TAG = "SudokuHelperTest::SudokuParser";
	private Sudoku toSolve;
	private Sudoku solutionSudoku;

	
// No System.out!
	
	
//	public void print(Sudoku solvedSudoku) {
//		printSudoku(solvedSudoku);
//		printSudoku(solutionSudoku);
//	}

//	private void printSudoku(Sudoku sudoku) {
//		System.out.print("-----------------------------------");
//		for (int row = 0; row < 9; row++) {
//
//			for (int column = 0; column < 9; column++) {
//				System.out.print(solutionSudoku.getField(row, column));
//			}
//
//		}
//		System.out.print("-----------------------------------");
//	}

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
