package ch.zahw.students.sudokuhelper.solve;

import java.util.Vector;

import android.util.Log;
import ch.zahw.students.sudokuhelper.solve.algorithm.HiddenSingleAlgorithm;

public class SudokuManager {

    private static final String TAG = "SudokuHelper::SudokuManager";
    private Sudoku sudoku;

    private HiddenSingleAlgorithm hiddenSingle;

    // TODO solve order for step funktion
    private Vector<SudokuField> solveOrder;
    private int indexNextSolveOrder;

    // TODO: use sudoku.lock() as first argument in solve()
    public Sudoku solve() {
        sudoku.lockSudoku();
        
        
//        hiddenSingle = new HiddenSingleAlgorithm(sudoku);
//        sudoku = hiddenSingle.solve();

        return sudoku;
    }

    // creates a new, empty Sudoku
    public SudokuManager() {
        this.sudoku = new Sudoku();
    }

    // creates a Sudoku from a given list of candidates
    public SudokuManager(int[][] sudokuArray) {
        this.sudoku = new Sudoku(sudokuArray);
    }

    // Uses an existing Sudoku (i.e. from saved state)
    public SudokuManager(Sudoku sudoku) {
        this.sudoku = sudoku;
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

    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

}
