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
        this.hiddenSingleAlgorithm = new HiddenSingleAlgorithm();
        this.backtrackingAlgorithm = new BacktrackingAlgorithm();
        this.nakedSingleAlgorithm = new NakedSingleAlgorithm();
        init();
    }

    // creates a Sudoku from a given list of candidates
    public SudokuManager(int[][] sudokuArray) {
        this.sudoku = new Sudoku(sudokuArray);
        init();
    }

    // Uses an existing Sudoku (i.e. from saved state)
    public SudokuManager(Sudoku sudoku) {
        this.sudoku = sudoku;
        init();
    }

    private void init() {
        hiddenSingleAlgorithm.setSudoku(sudoku);
        backtrackingAlgorithm.setSudoku(sudoku);
        nakedSingleAlgorithm.setSudoku(sudoku);
    }

    // TODO: use sudoku.lock() as first argument in solve()
    public Sudoku solve() {
        sudoku.lockSudoku();
        
        if (!sudoku.isValid()) {
            return sudoku;
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

        return sudoku;
    }

    public void step() {
        sudoku.lockSudoku();

        if (!sudoku.isValid()) {
            return ;
        }

        if (nakedSingleAlgorithm.step()) {
            return;
        } else if (hiddenSingleAlgorithm.step()) {
            return;
        } else if (backtrackingAlgorithm.step()) {
            return;
        }

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
        init();
    }

}
