package ch.zahw.students.sudokuhelper.test;

import java.util.Arrays;
import java.util.Random;

import android.content.res.Resources;
import android.test.ActivityTestCase;
import android.util.Log;
import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuField;
import ch.zahw.students.sudokuhelper.solve.SudokuManager;
import ch.zahw.students.sudokuhelper.solve.algorithm.BacktrackingAlgorithm;

public class SudokuSolveTest extends ActivityTestCase {
    private static final String TAG = "SudokuHelperTest::SudokuSolveTest";

    private SudokuManager sudokuManager;
    private Resources res;

    public void setUp() {
        sudokuManager = new SudokuManager();
        res = getInstrumentation().getContext().getResources();
    }

    // http://www.sudoku-solutions.com/
    public void testSudokuClasses() {
        SudokuParser parser = new SudokuParser();
        int[][] candidates = parser.parseString(res.getString(R.string.sudoku1));
        Sudoku sudoku = new Sudoku(candidates);
        Log.v(TAG, "testSudokuClasses: " + candidates.toString());
        sudoku.lockSudoku();

        // test the Sudoku class
        assertEquals(1, sudoku.getNumber(0, 0));
        assertEquals(2, sudoku.getNumber(0, 1));
        assertEquals(0, sudoku.getNumber(1, 0));
        assertTrue(sudoku.isValid());
        // assertTrue(sudoku.isSolved());
        assertEquals(sudoku, sudoku);
        // Two Sudokus are equal if their nubers are equal
        Sudoku sudoku2 = new Sudoku(candidates);
        assertTrue(sudoku.equals(sudoku2));
        int[] referenceRow = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int[] sudokuRow = sudoku.getRowValues(0);
        assertTrue("The first row is set correctly", Arrays.equals(referenceRow, sudokuRow));
        referenceRow = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        sudokuRow = sudoku.getRowValues(1);
        assertTrue("The second row is set correctly", Arrays.equals(referenceRow, sudokuRow));

        // test the fields
        SudokuField firstField = sudoku.getField(0, 0);
        assertTrue(firstField.isValid());
        assertTrue(firstField.isFounded());
        assertEquals(1, firstField.getNumber());
        assertTrue(firstField.isInitialValue());

        // all fields with numbers are founded
        // solve test, should be not solved
    }

    // http://www.sudoku-solutions.com/
    public void testSimpleSudoku() {
        SudokuParser parser = new SudokuParser();
        int[][] candidates = parser.parseString(res.getString(R.string.simpleSudoku));
        Sudoku sudoku = new Sudoku(candidates);
        Log.v(TAG, "testSimpleSudoku: " + candidates.toString());
        sudoku.lockSudoku();

        int[] referenceRow = new int[] { 0, 7, 8, 1, 0, 0, 0, 2, 0 };
        int[] sudokuRow = sudoku.getRowValues(0);

        assertTrue("The first row of simpleSudoku is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 0, 5, 0, 7, 0, 0, 9, 0, 0 };
        sudokuRow = sudoku.getRowValues(8);
        assertTrue("The ninth row of simpleSudoku is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        int[][] candidatesLsg = parser.parseString(res.getString(R.string.simpleSudokuLoesung));
        Sudoku sudokuLoesung = new Sudoku(candidatesLsg);
        Log.v(TAG, candidatesLsg.toString());
        referenceRow = new int[] { 3, 7, 8, 1, 4, 5, 6, 2, 9 };
        sudokuRow = sudokuLoesung.getRowValues(0);

        assertTrue("The first row of simpleSudokuLoesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 4, 5, 2, 7, 3, 6, 9, 8, 1 };
        sudokuRow = sudokuLoesung.getRowValues(8);
        assertTrue("The ninth row of simpleSudokuLoesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        assertTrue(sudoku.isValid());
        sudokuManager.setSudoku(sudoku);
        sudoku = sudokuManager.solve();
        assertTrue(sudoku.isSolved());

        assertTrue("The first row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(0), sudoku.getRowValues(0)));
        assertTrue("The second row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(1), sudoku.getRowValues(1)));
        assertTrue("The third row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(2), sudoku.getRowValues(2)));
        assertTrue("The fourth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(3), sudoku.getRowValues(3)));
        assertTrue("The fifth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(4), sudoku.getRowValues(4)));
        assertTrue("The sixth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(5), sudoku.getRowValues(5)));
        assertTrue("The seventh row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(6), sudoku.getRowValues(6)));
        assertTrue("The eighth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(7), sudoku.getRowValues(7)));
        assertTrue("The ninth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(8), sudoku.getRowValues(8)));
    }

    public void testSimpleSudoku2() {
        SudokuParser parser = new SudokuParser();
        int[][] candidates = parser.parseString(res.getString(R.string.simpleSudoku2));
        Sudoku sudoku = new Sudoku(candidates);
        Log.v(TAG, "testSimpleSudoku2: " + candidates.toString());
        sudoku.lockSudoku();

        int[] referenceRow = new int[] { 8, 0, 0, 3, 0, 0, 0, 0, 0 };
        int[] sudokuRow = sudoku.getRowValues(0);

        assertTrue("The first row of simpleSudoku2 is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 6 };
        sudokuRow = sudoku.getRowValues(8);
        assertTrue("The ninth row of simpleSudoku2 is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        int[][] candidatesLsg = parser.parseString(res.getString(R.string.simpleSudoku2Loesung));
        Sudoku sudokuLoesung = new Sudoku(candidatesLsg);
        Log.v(TAG, candidatesLsg.toString());
        referenceRow = new int[] { 8, 1, 2, 3, 7, 9, 6, 5, 4 };
        sudokuRow = sudokuLoesung.getRowValues(0);

        assertTrue("The first row of simpleSudoku2Loesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 3, 4, 8, 7, 1, 2, 5, 9, 6 };
        sudokuRow = sudokuLoesung.getRowValues(8);
        assertTrue("The ninth row of simpleSudoku2Loesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        assertTrue(sudoku.isValid());
        sudokuManager.setSudoku(sudoku);
        sudoku = sudokuManager.solve();
        assertTrue(sudoku.isSolved());
        assertEquals(sudoku, sudokuLoesung);

        assertTrue("The first row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(0), sudoku.getRowValues(0)));
        assertTrue("The second row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(1), sudoku.getRowValues(1)));
        assertTrue("The third row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(2), sudoku.getRowValues(2)));
        assertTrue("The fourth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(3), sudoku.getRowValues(3)));
        assertTrue("The fifth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(4), sudoku.getRowValues(4)));
        assertTrue("The sixth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(5), sudoku.getRowValues(5)));
        assertTrue("The seventh row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(6), sudoku.getRowValues(6)));
        assertTrue("The eighth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(7), sudoku.getRowValues(7)));
        assertTrue("The ninth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(8), sudoku.getRowValues(8)));
    }

    public void testSimpleSudoku3() {
        SudokuParser parser = new SudokuParser();
        int[][] candidates = parser.parseString(res.getString(R.string.simpleSudoku3));
        Sudoku sudoku = new Sudoku(candidates);
        Log.v(TAG, "testSimpleSudoku2: " + candidates.toString());
        sudoku.lockSudoku();

        int[] referenceRow = new int[] { 8, 1, 2, 0, 0, 0, 0, 9, 0 };
        int[] sudokuRow = sudoku.getRowValues(0);

        assertTrue("The first row of simpleSudoku2 is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 3, 5, 8, 0, 0, 0, 0, 0, 0 };
        sudokuRow = sudoku.getRowValues(8);
        assertTrue("The ninth row of simpleSudoku2 is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        int[][] candidatesLsg = parser.parseString(res.getString(R.string.simpleSudoku3Loesung));
        Sudoku sudokuLoesung = new Sudoku(candidatesLsg);
        Log.v(TAG, candidatesLsg.toString());
        referenceRow = new int[] { 8, 1, 2, 7, 6, 3, 5, 9, 4 };
        sudokuRow = sudokuLoesung.getRowValues(0);

        assertTrue("The first row of simpleSudoku2Loesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 3, 5, 8, 9, 7, 2, 4, 6, 1 };
        sudokuRow = sudokuLoesung.getRowValues(8);
        assertTrue("The ninth row of simpleSudoku2Loesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        assertTrue(sudoku.isValid());
        sudokuManager.setSudoku(sudoku);
        sudoku = sudokuManager.solve();
        // assertTrue(sudoku.isSolved());
        // assertEquals(sudoku, sudokuLoesung);

        // Dieses Sudoku konnte vor dem refactoring gel�st werden
        for (int i = 0; i < 9; i++) {
            Log.v(TAG, sudokuLoesung.getRowValues(0)[i] + "-" + sudoku.getRowValues(0)[i] + "-"
                    + sudoku.getField(0, i).getAvailableNumbers());
        }

        assertTrue("The first row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(0), sudoku.getRowValues(0)));
        assertTrue("The second row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(1), sudoku.getRowValues(1)));
        assertTrue("The third row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(2), sudoku.getRowValues(2)));
        assertTrue("The fourth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(3), sudoku.getRowValues(3)));
        assertTrue("The fifth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(4), sudoku.getRowValues(4)));
        assertTrue("The sixth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(5), sudoku.getRowValues(5)));
        assertTrue("The seventh row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(6), sudoku.getRowValues(6)));
        assertTrue("The eighth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(7), sudoku.getRowValues(7)));
        assertTrue("The ninth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(8), sudoku.getRowValues(8)));
    }

    public void testEasySudoku() {
        SudokuParser parser = new SudokuParser();
        int[][] candidates = parser.parseString(res.getString(R.string.easySudoku));
        Sudoku sudoku = new Sudoku(candidates);
        Log.v(TAG, "testEasySudoku: " + candidates.toString());
        sudoku.lockSudoku();

        int[] referenceRow = new int[] { 0, 4, 0, 0, 0, 8, 0, 0, 0 };
        int[] sudokuRow = sudoku.getRowValues(0);

        assertTrue("The first row of easySudoku is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 0, 0, 0, 5, 0, 0, 0, 6, 0 };
        sudokuRow = sudoku.getRowValues(8);
        assertTrue("The ninth row of easySudoku is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        int[][] candidatesLsg = parser.parseString(res.getString(R.string.easySudokuLoesung));
        Sudoku sudokuLoesung = new Sudoku(candidatesLsg);
        Log.v(TAG, candidatesLsg.toString());
        referenceRow = new int[] { 7, 4, 2, 3, 5, 8, 6, 9, 1 };
        sudokuRow = sudokuLoesung.getRowValues(0);

        assertTrue("The first row of easySudokuLoesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 9, 1, 8, 5, 4, 3, 7, 6, 2 };
        sudokuRow = sudokuLoesung.getRowValues(8);
        assertTrue("The ninth row of easySudokuLoesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        assertTrue(sudoku.isValid());
        sudokuManager.setSudoku(sudoku);
        sudoku = sudokuManager.solve();
        assertTrue(sudoku.isSolved());
        assertEquals(sudoku, sudokuLoesung);

        assertTrue("The first row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(0), sudoku.getRowValues(0)));
        assertTrue("The second row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(1), sudoku.getRowValues(1)));
        assertTrue("The third row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(2), sudoku.getRowValues(2)));
        assertTrue("The fourth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(3), sudoku.getRowValues(3)));
        assertTrue("The fifth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(4), sudoku.getRowValues(4)));
        assertTrue("The sixth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(5), sudoku.getRowValues(5)));
        assertTrue("The seventh row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(6), sudoku.getRowValues(6)));
        assertTrue("The eighth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(7), sudoku.getRowValues(7)));
        assertTrue("The ninth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(8), sudoku.getRowValues(8)));
    }

    public void testMediumSudoku() {
        SudokuParser parser = new SudokuParser();
        int[][] candidates = parser.parseString(res.getString(R.string.mediumSudoku));
        Sudoku sudoku = new Sudoku(candidates);
        Log.v(TAG, "testMediumSudoku: " + candidates.toString());
        sudoku.lockSudoku();

        int[] referenceRow = new int[] { 7, 0, 0, 6, 0, 3, 0, 0, 0 };
        int[] sudokuRow = sudoku.getRowValues(0);

        assertTrue("The first row of mediumSudoku is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 0, 0, 0, 5, 0, 2, 0, 0, 3 };
        sudokuRow = sudoku.getRowValues(8);
        assertTrue("The ninth row of mediumSudoku is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        int[][] candidatesLsg = parser.parseString(res.getString(R.string.mediumSudokuLoesung));
        Sudoku sudokuLoesung = new Sudoku(candidatesLsg);
        Log.v(TAG, candidatesLsg.toString());
        referenceRow = new int[] { 7, 9, 4, 6, 1, 3, 8, 5, 2 };
        sudokuRow = sudokuLoesung.getRowValues(0);

        assertTrue("The first row of mediumSudokuLoesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 9, 7, 1, 5, 6, 2, 4, 8, 3 };
        sudokuRow = sudokuLoesung.getRowValues(8);
        assertTrue("The ninth row of mediumSudokuLoesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        assertTrue(sudoku.isValid());
        sudokuManager.setSudoku(sudoku);
        sudoku = sudokuManager.solve();
        assertTrue(sudoku.isSolved());
        assertEquals(sudoku, sudokuLoesung);

        assertTrue("The first row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(0), sudoku.getRowValues(0)));
        assertTrue("The second row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(1), sudoku.getRowValues(1)));
        assertTrue("The third row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(2), sudoku.getRowValues(2)));
        assertTrue("The fourth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(3), sudoku.getRowValues(3)));
        assertTrue("The fifth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(4), sudoku.getRowValues(4)));
        assertTrue("The sixth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(5), sudoku.getRowValues(5)));
        assertTrue("The seventh row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(6), sudoku.getRowValues(6)));
        assertTrue("The eighth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(7), sudoku.getRowValues(7)));
        assertTrue("The ninth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(8), sudoku.getRowValues(8)));
    }

    public void testMediumSudoku2() {
        SudokuParser parser = new SudokuParser();
        int[][] candidates = parser.parseString(res.getString(R.string.mediumSudoku2));
        Sudoku sudoku = new Sudoku(candidates);
        Log.v(TAG, "testMediumSudoku: " + candidates.toString());
        sudoku.lockSudoku();

        int[] referenceRow = new int[] { 6, 5, 0, 0, 9, 0, 0, 0, 0 };
        int[] sudokuRow = sudoku.getRowValues(0);

        assertTrue("The first row of mediumSudoku2 is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 0, 0, 0, 0, 1, 0, 0, 7, 5 };
        sudokuRow = sudoku.getRowValues(8);
        assertTrue("The ninth row of mediumSudoku2 is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        int[][] candidatesLsg = parser.parseString(res.getString(R.string.mediumSudoku2Loesung));
        Sudoku sudokuLoesung = new Sudoku(candidatesLsg);
        Log.v(TAG, candidatesLsg.toString());
        referenceRow = new int[] { 6, 5, 1, 8, 9, 3, 7, 2, 4 };
        sudokuRow = sudokuLoesung.getRowValues(0);

        assertTrue("The first row of mediumSudoku2Loesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 8, 3, 4, 6, 1, 2, 9, 7, 5 };
        sudokuRow = sudokuLoesung.getRowValues(8);
        assertTrue("The ninth row of mediumSudoku2Loesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        assertTrue(sudoku.isValid());
        sudokuManager.setSudoku(sudoku);
        sudoku = sudokuManager.solve();
        assertTrue(sudoku.isSolved());
        assertEquals(sudoku, sudokuLoesung);

        assertTrue("The first row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(0), sudoku.getRowValues(0)));
        assertTrue("The second row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(1), sudoku.getRowValues(1)));
        assertTrue("The third row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(2), sudoku.getRowValues(2)));
        assertTrue("The fourth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(3), sudoku.getRowValues(3)));
        assertTrue("The fifth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(4), sudoku.getRowValues(4)));
        assertTrue("The sixth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(5), sudoku.getRowValues(5)));
        assertTrue("The seventh row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(6), sudoku.getRowValues(6)));
        assertTrue("The eighth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(7), sudoku.getRowValues(7)));
        assertTrue("The ninth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(8), sudoku.getRowValues(8)));
    }

    public void testHardSudoku() {
        SudokuParser parser = new SudokuParser();
        int[][] candidates = parser.parseString(res.getString(R.string.hardSudoku));
        Sudoku sudoku = new Sudoku(candidates);
        Log.v(TAG, "testHardSudoku: " + candidates.toString());
        sudoku.lockSudoku();

        int[] referenceRow = new int[] { 0, 2, 7, 0, 0, 8, 0, 0, 0 };
        int[] sudokuRow = sudoku.getRowValues(0);

        assertTrue("The first row of hardSudoku is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 0, 0, 0, 6, 0, 0, 9, 3, 0 };
        sudokuRow = sudoku.getRowValues(8);
        assertTrue("The ninth row  of hardSudoku is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        int[][] candidatesLsg = parser.parseString(res.getString(R.string.hardSudokuLoesung));
        Sudoku sudokuLoesung = new Sudoku(candidatesLsg);
        Log.v(TAG, candidatesLsg.toString());
        referenceRow = new int[] { 3, 2, 7, 5, 6, 8, 1, 9, 4 };
        sudokuRow = sudokuLoesung.getRowValues(0);

        assertTrue("The first row of hardSudokuLoesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 2, 7, 8, 6, 5, 4, 9, 3, 1 };
        sudokuRow = sudokuLoesung.getRowValues(8);
        assertTrue("The ninth row of hardSudokuLoesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        assertTrue(sudoku.isValid());
        sudokuManager.setSudoku(sudoku);
        sudoku = sudokuManager.solve();
        assertTrue(sudoku.isSolved());
        assertEquals(sudoku, sudokuLoesung);

        assertTrue("The first row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(0), sudoku.getRowValues(0)));
        assertTrue("The second row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(1), sudoku.getRowValues(1)));
        assertTrue("The third row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(2), sudoku.getRowValues(2)));
        assertTrue("The fourth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(3), sudoku.getRowValues(3)));
        assertTrue("The fifth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(4), sudoku.getRowValues(4)));
        assertTrue("The sixth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(5), sudoku.getRowValues(5)));
        assertTrue("The seventh row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(6), sudoku.getRowValues(6)));
        assertTrue("The eighth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(7), sudoku.getRowValues(7)));
        assertTrue("The ninth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(8), sudoku.getRowValues(8)));
    }

    public void testHardSudoku2() {
        SudokuParser parser = new SudokuParser();
        int[][] candidates = parser.parseString(res.getString(R.string.hardSudoku2));
        Sudoku sudoku = new Sudoku(candidates);
        Log.v(TAG, "testHardSudoku: " + candidates.toString());
        sudoku.lockSudoku();

        int[] referenceRow = new int[] { 0, 0, 0, 0, 0, 7, 0, 0, 9 };
        int[] sudokuRow = sudoku.getRowValues(0);

        assertTrue("The first row of hardSudoku2 is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 5, 3, 1, 0, 7, 0, 0, 0, 0 };
        sudokuRow = sudoku.getRowValues(8);
        assertTrue("The ninth row  of hardSudoku2 is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        int[][] candidatesLsg = parser.parseString(res.getString(R.string.hardSudoku2Loesung));
        Sudoku sudokuLoesung = new Sudoku(candidatesLsg);
        Log.v(TAG, candidatesLsg.toString());
        referenceRow = new int[] { 3, 1, 2, 5, 4, 7, 8, 6, 9 };
        sudokuRow = sudokuLoesung.getRowValues(0);

        assertTrue("The first row of hardSudoku2Loesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        referenceRow = new int[] { 5, 3, 1, 4, 7, 9, 6, 2, 8 };
        sudokuRow = sudokuLoesung.getRowValues(8);
        assertTrue("The ninth row of hardSudoku2Loesung is set correctly",
                Arrays.equals(referenceRow, sudokuRow));

        assertTrue(sudoku.isValid());
        sudokuManager.setSudoku(sudoku);
        sudoku = sudokuManager.solve();
        assertTrue(sudoku.isSolved());
        assertEquals(sudoku, sudokuLoesung);

        assertTrue("The first row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(0), sudoku.getRowValues(0)));
        assertTrue("The second row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(1), sudoku.getRowValues(1)));
        assertTrue("The third row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(2), sudoku.getRowValues(2)));
        assertTrue("The fourth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(3), sudoku.getRowValues(3)));
        assertTrue("The fifth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(4), sudoku.getRowValues(4)));
        assertTrue("The sixth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(5), sudoku.getRowValues(5)));
        assertTrue("The seventh row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(6), sudoku.getRowValues(6)));
        assertTrue("The eighth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(7), sudoku.getRowValues(7)));
        assertTrue("The ninth row is solved correctly",
                Arrays.equals(sudokuLoesung.getRowValues(8), sudoku.getRowValues(8)));
    }

    public void testBacktrackingAlgorithm() {
        SudokuParser parser = new SudokuParser();
        Random generator = new Random();
        int[][] candidates = parser.parseString(res.getString(R.string.hardSudoku2Loesung));
        Sudoku reference = new Sudoku(candidates);
        // delete 3 random fields
        for (int i = 0; i < 3; i++) {
            int field = generator.nextInt(81);
            candidates[field / 9][field % 9] = 0;
        }
        Sudoku sudoku = new Sudoku(candidates);
        // Log.v(TAG, "testBacktrackingAlgorithm: " + candidates.toString());
        sudoku.lockSudoku();
        assertFalse(sudoku.isSolved());
        assertTrue(sudoku.isValid());
        assertTrue(reference.isSolved());

        BacktrackingAlgorithm ba = new BacktrackingAlgorithm();
        ba.setSudoku(sudoku);
        
        assertTrue(ba.solve());
        assertTrue(sudoku.equals(reference));

        // delete 10 random fields
        for (int i = 0; i < 10; i++) {
            int field = generator.nextInt(81);
            candidates[field / 9][field % 9] = 0;
        }

        sudoku = new Sudoku(candidates);
        sudoku.lockSudoku();
        assertFalse(sudoku.isSolved());
        assertTrue(sudoku.isValid());
        ba = new BacktrackingAlgorithm();
        ba.setSudoku(sudoku);

        assertTrue(ba.solve());
        assertTrue(sudoku.equals(reference));

    }

}
