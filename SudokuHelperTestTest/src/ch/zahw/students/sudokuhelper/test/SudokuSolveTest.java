package ch.zahw.students.sudokuhelper.test;

import java.util.Arrays;

import android.content.res.Resources;
import android.test.ActivityTestCase;
import android.util.Log;
import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuManager;

public class SudokuSolveTest extends ActivityTestCase {
    private static final String TAG = "SudokuHelperTest::SudokuSolveTest";

	private SudokuManager sudokuManager;

	public void setUp() {
		sudokuManager = new SudokuManager();
	}

	public void solveSudoku(int[][] toSolve, int[][] sudoku) {
		sudokuManager.resetSudoku(sudoku);
		Sudoku solvSudoku = sudokuManager.solve(toSolve);
		// int[][] solvedSudoku = sudokuManager.getSudokuAsArray(solvSudoku);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				assertEquals(sudoku[i][j], solvSudoku.getField(i, j));
			}

		}

	}

	public void naiveSolveSudoku(int[][] toSolve, int[][] sudoku) {
		// Sudoku solvSudoku2 = sudokuManager.solveWithNaiveApproach(sudoku);
		// int[][] solvedSudoku2 = sudokuManager.getSudokuAsArray(solvSudoku2);
		//
		// for (int i = 0; i < 9; i++) {
		// for (int j = 0; j < 9; j++) {
		// assertEquals(sudoku[i][j], solvedSudoku2[i][j]);
		// }
		//
		// }

	}


	public void testSudokuClasses() {
	    SudokuParser parser = new SudokuParser();
	    Resources res = getInstrumentation().getContext().getResources();
	    int[][] candidates = parser.parseString(res.getString(R.string.sudoku1));
	    Sudoku sudoku = new Sudoku(candidates);
	    Log.v(TAG, candidates.toString());
	    sudoku.lockSudoku();
	    
	    // test the Sudoku class
	    assertEquals(1, sudoku.getNumber(0,0));
	    assertEquals(2, sudoku.getNumber(0,1));
	    assertEquals(0, sudoku.getNumber(1,0));
	    assertTrue(sudoku.isValid());
	    assertFalse(sudoku.isSolved());
	    assertEquals(sudoku, sudoku);
	    int[] referenceRow = new int[]{1,2,3,4,5,6,7,8,9};
	    int[] sudokuRow = sudoku.getRowValues(0);
	    assertTrue("The first row is set correctly", Arrays.equals(referenceRow, sudokuRow));
	    referenceRow = new int[]{0,0,0,0,0,0,0,0,0};
	    sudokuRow = sudoku.getRowValues(1);
	    assertTrue("The second row is set correctly", Arrays.equals(referenceRow, sudokuRow));
	}

}
