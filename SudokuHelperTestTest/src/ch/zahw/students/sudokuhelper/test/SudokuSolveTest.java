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
	private  Resources res;
	
	public void setUp() {
		sudokuManager = new SudokuManager();
	    res = getInstrumentation().getContext().getResources();
	}



//	http://www.sudoku-solutions.com/
	public void testSudokuClasses() {
	    SudokuParser parser = new SudokuParser();
	    int[][] candidates = parser.parseString(res.getString(R.string.sudoku1));
	    Sudoku sudoku = new Sudoku(candidates);
	    Log.v(TAG, "testSudokuClasses: "+candidates.toString());
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
	    
	    // test the fields
	    // all fields are valid
	    
	    // all fields with numbers are founded
	    
	    // solve test, should be not solved
	}

//	http://www.sudoku-solutions.com/
	public void testSimpleSudoku() {
	    SudokuParser parser = new SudokuParser();
	    int[][] candidates = parser.parseString(res.getString(R.string.sudoku2));
	    Sudoku sudoku = new Sudoku(candidates);
	    Log.v(TAG, "testSimpleSudoku: "+candidates.toString());
	    sudoku.lockSudoku();
	    
	    int[] referenceRow = new int[]{0,7,8,1,0,0,0,2,0};
	    int[] sudokuRow = sudoku.getRowValues(0);
	   
	    assertTrue("The first row  of sudoku2Loesung is set correctly", Arrays.equals(referenceRow, sudokuRow));
	    
	    referenceRow = new int[]{0,5,0,7,0,0,9,0,0};
	    sudokuRow = sudoku.getRowValues(8);
	    assertTrue("The ninth row of sudoku2Loesung is set correctly", Arrays.equals(referenceRow, sudokuRow));
	 
	    
	    
	    int[][] candidatesLsg = parser.parseString(res.getString(R.string.sudoku2Loesung));
	    Sudoku sudokuLoesung = new Sudoku(candidatesLsg);
	    Log.v(TAG, candidatesLsg.toString());
	    referenceRow = new int[]{3,7,8,1,4,5,6,2,9};
	    sudokuRow = sudokuLoesung.getRowValues(0);
	   
	    assertTrue("The first row is set correctly", Arrays.equals(referenceRow, sudokuRow));
	    
	    referenceRow = new int[]{4,5,2,7,3,6,9,8,1};
	    sudokuRow = sudokuLoesung.getRowValues(8);
	    assertTrue("The ninth row is set correctly", Arrays.equals(referenceRow, sudokuRow));
	
	    
	    
	    //TODO solve Sudoku
	    
	    // test the Sudoku class
//	    assertTrue(sudoku.isValid());
//	    assertFalse(sudoku.isSolved());
//	    assertEquals(sudoku, sudoku);
	    
	    
//	    assertTrue("The first row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(0), sudoku.getRowValues(0)));
//	    assertTrue("The second row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(1), sudoku.getRowValues(1)));
//	    assertTrue("The third row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(2), sudoku.getRowValues(2)));
//	    assertTrue("The fourth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(3), sudoku.getRowValues(3)));
//	    assertTrue("The fifth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(4), sudoku.getRowValues(4)));
//	    assertTrue("The sixth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(5), sudoku.getRowValues(5)));
//	    assertTrue("The seventh row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(6), sudoku.getRowValues(6)));
//	    assertTrue("The eighth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(7), sudoku.getRowValues(7)));
//	    assertTrue("The ninth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(8), sudoku.getRowValues(8)));
			   
	    // all fields are valid
	}
	
	public void testEasySudoku() {
	    SudokuParser parser = new SudokuParser();
	    int[][] candidates = parser.parseString(res.getString(R.string.sudoku3));
	    Sudoku sudoku = new Sudoku(candidates);
	    Log.v(TAG, "testEasySudoku: "+candidates.toString());
	    sudoku.lockSudoku();
	    
	    int[] referenceRow = new int[]{0,4,0,0,0,8,0,0,0};
	    int[] sudokuRow = sudoku.getRowValues(0);
	   
	    assertTrue("The first row is set correctly", Arrays.equals(referenceRow, sudokuRow));
	    
	    referenceRow = new int[]{0,0,0,5,0,0,0,6,0};
	    sudokuRow = sudoku.getRowValues(8);
	    assertTrue("The ninth is set correctly", Arrays.equals(referenceRow, sudokuRow));
	 
	    
	    
	    int[][] candidatesLsg = parser.parseString(res.getString(R.string.sudoku3Loesung));
	    Sudoku sudokuLoesung = new Sudoku(candidatesLsg);
	    Log.v(TAG, candidatesLsg.toString());
	    referenceRow = new int[]{7,4,2,3,5,8,6,9,1};
	    sudokuRow = sudokuLoesung.getRowValues(0);
	   
	    assertTrue("The first row of sudoku3Loesung is set correctly", Arrays.equals(referenceRow, sudokuRow));
	    
	    referenceRow = new int[]{9,1,8,5,4,3,7,6,2};
	    sudokuRow = sudokuLoesung.getRowValues(8);
	    assertTrue("The ninth row of sudoku3Loesung is set correctly", Arrays.equals(referenceRow, sudokuRow));
	
	    
	    //TODO solve Sudoku
	    
	    // test the Sudoku class
//	    assertTrue(sudoku.isValid());
//	    assertFalse(sudoku.isSolved());
//	    assertEquals(sudoku, sudoku);
	    
	    
//	    assertTrue("The first row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(0), sudoku.getRowValues(0)));
//	    assertTrue("The second row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(1), sudoku.getRowValues(1)));
//	    assertTrue("The third row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(2), sudoku.getRowValues(2)));
//	    assertTrue("The fourth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(3), sudoku.getRowValues(3)));
//	    assertTrue("The fifth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(4), sudoku.getRowValues(4)));
//	    assertTrue("The sixth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(5), sudoku.getRowValues(5)));
//	    assertTrue("The seventh row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(6), sudoku.getRowValues(6)));
//	    assertTrue("The eighth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(7), sudoku.getRowValues(7)));
//	    assertTrue("The ninth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(8), sudoku.getRowValues(8)));
			   
	    // all fields are valid
	}
	
	public void testMediumSudoku() {
	    SudokuParser parser = new SudokuParser();
	    int[][] candidates = parser.parseString(res.getString(R.string.sudoku4));
	    Sudoku sudoku = new Sudoku(candidates);
	    Log.v(TAG, "testMediumSudoku: "+candidates.toString());
	    sudoku.lockSudoku();
	    
	    int[] referenceRow = new int[]{7,0,0,6,0,3,0,0,0};
	    int[] sudokuRow = sudoku.getRowValues(0);
	   
	    assertTrue("The first row is set correctly", Arrays.equals(referenceRow, sudokuRow));
	    
	    referenceRow = new int[]{0,0,0,5,0,2,0,0,3};
	    sudokuRow = sudoku.getRowValues(8);
	    assertTrue("The ninth is set correctly", Arrays.equals(referenceRow, sudokuRow));
	 
	    
	    
	    int[][] candidatesLsg = parser.parseString(res.getString(R.string.sudoku4Loesung));
	    Sudoku sudokuLoesung = new Sudoku(candidatesLsg);
	    Log.v(TAG, candidatesLsg.toString());
	    referenceRow = new int[]{7,9,4,6,1,3,8,5,2};
	    sudokuRow = sudokuLoesung.getRowValues(0);
	   
	    assertTrue("The first row of sudoku4Loesung is set correctly", Arrays.equals(referenceRow, sudokuRow));
	    
	    referenceRow = new int[]{9,7,1,5,6,2,4,8,3};
	    sudokuRow = sudokuLoesung.getRowValues(8);
	    assertTrue("The ninth row of sudoku4Loesung is set correctly", Arrays.equals(referenceRow, sudokuRow));
	
	    
	    //TODO solve Sudoku
	    
	    // test the Sudoku class
//	    assertTrue(sudoku.isValid());
//	    assertFalse(sudoku.isSolved());
//	    assertEquals(sudoku, sudoku);
	    
	    
//	    assertTrue("The first row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(0), sudoku.getRowValues(0)));
//	    assertTrue("The second row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(1), sudoku.getRowValues(1)));
//	    assertTrue("The third row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(2), sudoku.getRowValues(2)));
//	    assertTrue("The fourth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(3), sudoku.getRowValues(3)));
//	    assertTrue("The fifth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(4), sudoku.getRowValues(4)));
//	    assertTrue("The sixth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(5), sudoku.getRowValues(5)));
//	    assertTrue("The seventh row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(6), sudoku.getRowValues(6)));
//	    assertTrue("The eighth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(7), sudoku.getRowValues(7)));
//	    assertTrue("The ninth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(8), sudoku.getRowValues(8)));
			   
	    // all fields are valid
	}
	
	public void testHardSudoku() {
	    SudokuParser parser = new SudokuParser();
	    int[][] candidates = parser.parseString(res.getString(R.string.sudoku5));
	    Sudoku sudoku = new Sudoku(candidates);
	    Log.v(TAG, "testHardSudoku: "+candidates.toString());
	    sudoku.lockSudoku();
	    
	    int[] referenceRow = new int[]{0,2,7,0,0,8,0,0,0};
	    int[] sudokuRow = sudoku.getRowValues(0);
	   
	    assertTrue("The first row is set correctly", Arrays.equals(referenceRow, sudokuRow));
	    
	    referenceRow = new int[]{0,0,0,6,0,0,9,3,0};
	    sudokuRow = sudoku.getRowValues(8);
	    assertTrue("The ninth row is set correctly", Arrays.equals(referenceRow, sudokuRow));
	 
	    
	    
	    int[][] candidatesLsg = parser.parseString(res.getString(R.string.sudoku5Loesung));
	    Sudoku sudokuLoesung = new Sudoku(candidatesLsg);
	    Log.v(TAG, candidatesLsg.toString());
	    referenceRow = new int[]{3,2,7,5,6,8,1,9,4};
	    sudokuRow = sudokuLoesung.getRowValues(0);
	   
	    assertTrue("The first row of sudoku5Loesung is set correctly", Arrays.equals(referenceRow, sudokuRow));
	    
	    referenceRow = new int[]{2,7,8,6,5,4,9,3,1};
	    sudokuRow = sudokuLoesung.getRowValues(8);
	    assertTrue("The ninth row of sudoku5Loesung is set correctly", Arrays.equals(referenceRow, sudokuRow));
	
	    
	    //TODO solve Sudoku
	    
	    // test the Sudoku class
//	    assertTrue(sudoku.isValid());
//	    assertFalse(sudoku.isSolved());
//	    assertEquals(sudoku, sudoku);
	    
//	    
//	    assertTrue("The first row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(0), sudoku.getRowValues(0)));
//	    assertTrue("The second row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(1), sudoku.getRowValues(1)));
//	    assertTrue("The third row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(2), sudoku.getRowValues(2)));
//	    assertTrue("The fourth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(3), sudoku.getRowValues(3)));
//	    assertTrue("The fifth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(4), sudoku.getRowValues(4)));
//	    assertTrue("The sixth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(5), sudoku.getRowValues(5)));
//	    assertTrue("The seventh row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(6), sudoku.getRowValues(6)));
//	    assertTrue("The eighth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(7), sudoku.getRowValues(7)));
//	    assertTrue("The ninth row is solved correctly", Arrays.equals(sudokuLoesung.getRowValues(8), sudoku.getRowValues(8)));
//			   
	    // all fields are valid
	}
	
}
