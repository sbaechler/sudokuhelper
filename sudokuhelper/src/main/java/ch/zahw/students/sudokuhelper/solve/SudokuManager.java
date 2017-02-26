package ch.zahw.students.sudokuhelper.solve;

import java.util.Vector;

import android.util.Log;
import ch.zahw.students.sudokuhelper.solve.algorithm.BacktrackingAlgorithm;
import ch.zahw.students.sudokuhelper.solve.algorithm.HiddenSingleAlgorithm;
import ch.zahw.students.sudokuhelper.solve.algorithm.NakedSingleAlgorithm;

public class SudokuManager {

    private static final String TAG = "SudokuHelper::SudokuManager";
    private Sudoku sudoku;

    private HiddenSingleAlgorithm hiddenSingleAlgorithm;
    private BacktrackingAlgorithm backtrackingAlgorithm;
    private NakedSingleAlgorithm nakedSingleAlgorithm;

    // TODO solve order for step funktion
    private Vector<SudokuField> solveOrder;
    private int indexNextSolveOrder;

    // creates a new, empty Sudoku
    public SudokuManager() {
        this.sudoku = new Sudoku();
        initAlgorithms();
    }

    // creates a Sudoku from a given list of candidates
    public SudokuManager(int[][] sudokuArray) {
        this.sudoku = new Sudoku(sudokuArray);
        initAlgorithms();
    }

    // Uses an existing Sudoku (i.e. from saved state)
    public SudokuManager(Sudoku sudoku) {
        this.sudoku = sudoku;
        initAlgorithms();
    }

    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
        initAlgorithms();
    }
    
    private void initAlgorithms() {
        this.hiddenSingleAlgorithm = new HiddenSingleAlgorithm();
        this.backtrackingAlgorithm = new BacktrackingAlgorithm();
        this.nakedSingleAlgorithm = new NakedSingleAlgorithm();
        hiddenSingleAlgorithm.setSudoku(sudoku);
        backtrackingAlgorithm.setSudoku(sudoku);
        nakedSingleAlgorithm.setSudoku(sudoku);
    }

    /**
     * The main solve method. Keeps trying different algorithms until it succeeds.
     * @return true if successfull.
     */
    public boolean solve() {
        sudoku.lockSudoku();
        
        if (!sudoku.isValid()) {
            return false;
        }

        int failed = 0;

        while (failed < 2) {

            while (nakedSingleAlgorithm.solve()) {
                failed = 0;
            }

            failed++;

            while (hiddenSingleAlgorithm.solve()) {
                failed = 0;
            }

            failed++;
        }

        if (!sudoku.isSolved()) {
            backtrackingAlgorithm.solve();
        }

        return true;
    }

    public boolean step() {
        sudoku.lockSudoku();

        if (!sudoku.isValid()) {
            return false;
        }

        if (nakedSingleAlgorithm.step()) {
            Log.v(TAG, "Step with nakedSingle");
            return true;
        } else if (hiddenSingleAlgorithm.step()) {
            Log.v(TAG, "Step with hiddenSingle");
            return true;
        } else if (backtrackingAlgorithm.step()) {
            Log.v(TAG, "Step with backtracking");
            return true;
        }
        Log.v(TAG, "Step with nothing");
        return false;
    }

    /**
     * Sets the values for an existing Sudoku.
     * 
     * @param candidates
     *            - 2-dimensional array of numbers.
     */
    public void resetSudoku(int[][] candidates) {
        sudoku.setValues(candidates);
    }

    public void trySecondaryCandidates(int[][] candidates) {
        sudoku.trySecondaryCandidates(candidates);
    }

    public int[][] getArrSud(int[][] arrSud) {
        return arrSud;
    }

    public Sudoku getSudoku() {
        return sudoku;
    }

    public SudokuField[] getSudokuFields() {
        return sudoku.getFields();
    }

    public SudokuField getSudokuField(int row, int column) {
        return sudoku.getField(row, column);
    }

    public void setValue(int row, int column, int value) {
        sudoku.setValue(row, column, value);
    }

    public void manuallyOverrideValue(int row, int column, int value) {
        sudoku.manuallyOverrideValue(row, column, value);
    }

    public SudokuField getNextSolveOrder() {
        indexNextSolveOrder++;

        if (indexNextSolveOrder >= solveOrder.size()) {
            return null;
        }

        return solveOrder.get(indexNextSolveOrder);
    }

    public void print() {

        for (int i = 0; i < 9; i++) {
            Log.v(TAG, "\n-------------------\n|");
            int[] row = sudoku.getRowValues(i);

            for (int j = 0; j < 9; j++) {
                Log.v(TAG, row[j] + "|");
            }

        }
        Log.v(TAG, "\n-------------------");
    }

    public SudokuField getPreviousSolveOrder() {

        int prev = indexNextSolveOrder - 1;

        if (prev < 0) {
            return null;
        }

        return solveOrder.get(prev);
    }

    public boolean isSolved() {
        return sudoku.isSolved();
    }


}
