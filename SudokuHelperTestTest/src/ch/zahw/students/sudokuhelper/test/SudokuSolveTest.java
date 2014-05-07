package ch.zahw.students.sudokuhelper.test;

import android.test.ActivityTestCase;
import android.util.Log;
import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuManager;

public class SudokuSolveTest extends ActivityTestCase {

	private SudokuManager sudokuManager;

	public SudokuSolveTest() {
		sudokuManager = new SudokuManager();
	}

	public void solveSudoku(int[][] toSolve, int[][] sudoku) {
		Sudoku solvSudoku = sudokuManager.solveWithBetterApproach(sudoku);
		int[][] solvedSudoku = sudokuManager.getSudokuAsArray(solvSudoku);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Log.v(" abcd", i+"-"+j+": "+sudoku[i][j]+"-"+solvedSudoku[i][j]);

				assertEquals(sudoku[i][j], solvedSudoku[i][j]);
			}

		}
		
	}

	
	public void naiveSolveSudoku(int[][] toSolve, int[][] sudoku) {
		Log.v(" abcd", "naiveSolveSudoku");
		Sudoku solvSudoku2 = sudokuManager.solveWithNaiveApproach(sudoku);
		int[][] solvedSudoku2 = sudokuManager.getSudokuAsArray(solvSudoku2);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Log.v(" abcd", i+"-"+j+": "+sudoku[i][j]+"-"+solvedSudoku2[i][j]);

				assertEquals(sudoku[i][j], solvedSudoku2[i][j]);
			}

		}

	}
	
	public void testSimpleSudoku1() {
		int[][] toFillSudoku = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// first: row, second: column
				toFillSudoku[i][j] = 0;
			}

		}

		toFillSudoku[0][0] = 8;
		toFillSudoku[0][1] = 1;
		toFillSudoku[0][2] = 2;
		toFillSudoku[0][7] = 9;

		toFillSudoku[1][0] = 5;
		toFillSudoku[1][2] = 3;
		toFillSudoku[1][4] = 2;
		toFillSudoku[1][6] = 1;

		toFillSudoku[2][3] = 1;
		toFillSudoku[2][4] = 5;
		toFillSudoku[2][8] = 6;

		toFillSudoku[3][3] = 2;
		toFillSudoku[3][4] = 4;
		toFillSudoku[3][7] = 1;
		toFillSudoku[3][8] = 5;

		toFillSudoku[4][2] = 7;
		toFillSudoku[4][3] = 5;
		toFillSudoku[4][7] = 2;

		toFillSudoku[5][1] = 8;
		toFillSudoku[5][3] = 3;
		toFillSudoku[5][7] = 4;

		toFillSudoku[6][0] = 9;
		toFillSudoku[6][5] = 4;

		toFillSudoku[7][2] = 1;
		toFillSudoku[7][6] = 7;
		toFillSudoku[7][8] = 9;

		toFillSudoku[8][0] = 3;
		toFillSudoku[8][1] = 5;
		toFillSudoku[8][2] = 8;

		int[][] solved = toFillSudoku;

		solved[0][3] = 7;
		solved[0][4] = 6;
		solved[0][5] = 3;
		solved[0][6] = 5;
		solved[0][8] = 4;

		solved[1][1] = 6;
		solved[1][3] = 4;
		solved[1][5] = 9;
		solved[1][7] = 7;
		solved[1][8] = 8;

		solved[2][0] = 7;
		solved[2][1] = 9;
		solved[2][2] = 4;
		solved[2][5] = 8;
		solved[2][6] = 2;
		solved[2][7] = 3;

		solved[3][0] = 6;
		solved[3][1] = 3;
		solved[3][2] = 9;
		solved[3][5] = 7;
		solved[3][6] = 8;

		solved[4][0] = 1;
		solved[4][1] = 4;
		solved[4][4] = 8;
		solved[4][5] = 6;
		solved[4][6] = 9;
		solved[4][8] = 3;

		solved[5][0] = 2;
		solved[5][2] = 5;
		solved[5][4] = 9;
		solved[5][5] = 1;
		solved[5][6] = 6;
		solved[5][8] = 7;

		solved[6][1] = 7;
		solved[6][2] = 6;
		solved[6][3] = 8;
		solved[6][4] = 1;
		solved[6][6] = 3;
		solved[6][7] = 5;
		solved[6][8] = 2;

		solved[7][0] = 4;
		solved[7][1] = 2;
		solved[7][3] = 6;
		solved[7][4] = 3;
		solved[7][5] = 5;
		solved[7][7] = 8;

		solved[8][3] = 9;
		solved[8][4] = 7;
		solved[8][5] = 2;
		solved[8][6] = 4;
		solved[8][7] = 6;
		solved[8][8] = 1;
		solveSudoku(toFillSudoku, solved);
		naiveSolveSudoku(toFillSudoku, solved);
	}

	public void testSimpleSudoku2() {
		int[][] toFillSudoku = new int[9][9];

		toFillSudoku[0][0] = 0;
		toFillSudoku[0][1] = 1;
		toFillSudoku[0][2] = 0;
		toFillSudoku[0][3] = 0;
		toFillSudoku[0][4] = 0;
		toFillSudoku[0][5] = 0;
		toFillSudoku[0][6] = 0;
		toFillSudoku[0][7] = 5;
		toFillSudoku[0][0] = 0;

		toFillSudoku[1][0] = 0;
		toFillSudoku[1][1] = 0;
		toFillSudoku[1][2] = 8;
		toFillSudoku[1][3] = 0;
		toFillSudoku[1][4] = 0;
		toFillSudoku[1][5] = 0;
		toFillSudoku[1][6] = 0;
		toFillSudoku[1][7] = 0;
		toFillSudoku[1][8] = 0;

		toFillSudoku[2][0] = 6;
		toFillSudoku[2][1] = 5;
		toFillSudoku[2][2] = 7;
		toFillSudoku[2][3] = 2;
		toFillSudoku[2][4] = 0;
		toFillSudoku[2][5] = 0;
		toFillSudoku[2][6] = 0;
		toFillSudoku[2][7] = 3;
		toFillSudoku[2][8] = 0;

		toFillSudoku[3][0] = 0;
		toFillSudoku[3][1] = 0;
		toFillSudoku[3][2] = 2;
		toFillSudoku[3][3] = 0;
		toFillSudoku[3][4] = 0;
		toFillSudoku[3][5] = 9;
		toFillSudoku[3][6] = 0;
		toFillSudoku[3][7] = 0;
		toFillSudoku[3][8] = 0;

		toFillSudoku[4][0] = 0;
		toFillSudoku[4][1] = 6;
		toFillSudoku[4][2] = 0;
		toFillSudoku[4][3] = 0;
		toFillSudoku[4][4] = 8;
		toFillSudoku[4][5] = 0;
		toFillSudoku[4][6] = 4;
		toFillSudoku[4][7] = 0;
		toFillSudoku[4][8] = 2;

		toFillSudoku[5][0] = 1;
		toFillSudoku[5][1] = 0;
		toFillSudoku[5][2] = 0;
		toFillSudoku[5][3] = 7;
		toFillSudoku[5][4] = 0;
		toFillSudoku[5][5] = 0;
		toFillSudoku[5][6] = 0;
		toFillSudoku[5][7] = 0;
		toFillSudoku[5][8] = 8;

		toFillSudoku[6][0] = 8;
		toFillSudoku[6][1] = 0;
		toFillSudoku[6][2] = 0;
		toFillSudoku[6][3] = 3;
		toFillSudoku[6][4] = 0;
		toFillSudoku[6][5] = 4;
		toFillSudoku[6][6] = 0;
		toFillSudoku[6][7] = 9;
		toFillSudoku[6][8] = 0;

		toFillSudoku[7][0] = 0;
		toFillSudoku[7][1] = 0;
		toFillSudoku[7][2] = 0;
		toFillSudoku[7][3] = 5;
		toFillSudoku[7][4] = 0;
		toFillSudoku[7][5] = 0;
		toFillSudoku[7][6] = 3;
		toFillSudoku[7][7] = 0;
		toFillSudoku[7][8] = 0;

		toFillSudoku[8][0] = 0;
		toFillSudoku[8][1] = 9;
		toFillSudoku[8][2] = 0;
		toFillSudoku[8][3] = 0;
		toFillSudoku[8][4] = 2;
		toFillSudoku[8][5] = 0;
		toFillSudoku[8][6] = 0;
		toFillSudoku[8][7] = 0;
		toFillSudoku[8][8] = 4;

		int[][] solved = new int[9][9];

		solved[0][0] = 9;
		solved[0][1] = 1;
		solved[0][2] = 4;
		solved[0][3] = 8;
		solved[0][4] = 3;
		solved[0][5] = 6;
		solved[0][6] = 2;
		solved[0][7] = 5;
		solved[0][8] = 7;

		solved[1][0] = 2;
		solved[1][1] = 3;
		solved[1][2] = 8;
		solved[1][3] = 9;
		solved[1][4] = 7;
		solved[1][5] = 5;
		solved[1][6] = 6;
		solved[1][7] = 4;
		solved[1][8] = 1;

		solved[2][0] = 6;
		solved[2][1] = 5;
		solved[2][2] = 7;
		solved[2][3] = 2;
		solved[2][4] = 4;
		solved[2][5] = 1;
		solved[2][6] = 8;
		solved[2][7] = 3;
		solved[2][8] = 9;

		solved[3][0] = 7;
		solved[3][1] = 8;
		solved[3][2] = 2;
		solved[3][3] = 4;
		solved[3][4] = 6;
		solved[3][5] = 9;
		solved[3][6] = 5;
		solved[3][7] = 1;
		solved[3][8] = 3;

		solved[4][0] = 5;
		solved[4][1] = 6;
		solved[4][2] = 9;
		solved[4][3] = 1;
		solved[4][4] = 8;
		solved[4][5] = 3;
		solved[4][6] = 4;
		solved[4][7] = 7;
		solved[4][8] = 2;

		solved[5][0] = 1;
		solved[5][1] = 4;
		solved[5][2] = 3;
		solved[5][3] = 7;
		solved[5][4] = 2;
		solved[5][5] = 9;
		solved[5][6] = 9;
		solved[5][7] = 6;
		solved[5][8] = 8;

		solved[6][0] = 8;
		solved[6][1] = 2;
		solved[6][2] = 6;
		solved[6][3] = 3;
		solved[6][4] = 1;
		solved[6][5] = 4;
		solved[6][6] = 7;
		solved[6][7] = 9;
		solved[6][8] = 5;

		solved[7][0] = 4;
		solved[7][1] = 7;
		solved[7][2] = 1;
		solved[7][3] = 5;
		solved[7][4] = 9;
		solved[7][5] = 8;
		solved[7][6] = 3;
		solved[7][7] = 2;
		solved[7][8] = 6;

		solved[8][0] = 3;
		solved[8][1] = 9;
		solved[8][2] = 5;
		solved[8][3] = 6;
		solved[8][4] = 2;
		solved[8][5] = 7;
		solved[8][6] = 1;
		solved[8][7] = 8;
		solved[8][8] = 4;

		solveSudoku(toFillSudoku, solved);
		naiveSolveSudoku(toFillSudoku, solved);
	}

}
