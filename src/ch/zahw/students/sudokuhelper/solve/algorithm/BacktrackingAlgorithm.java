/**
 * Backtracking algorithm based on the pseudo-code from Rogerlebo
 * http://sudokuvision.blogspot.ca/2011/01/step-9-play-and-solve-sudoku.html
 */


package ch.zahw.students.sudokuhelper.solve.algorithm;

import java.util.LinkedList;

import android.util.Log;
import ch.zahw.students.sudokuhelper.solve.Sudoku;

/**
 * Solves the sudoku by trying out every possible number. This can take long but
 * can solve every valid Sudoku. The algorithm operates on a nested Array
 * instead of the Sudoku class.
 */
public class BacktrackingAlgorithm implements SudokuSolver {
    private static final String TAG = "SudokuHelper::BacktrackingAlgorithm";
    private Sudoku sudoku;
    private int[][] candidates = null;
    private boolean isSolved = false;
    private LinkedList<Integer> foundFields;
    private long steps = 0;
    
    public BacktrackingAlgorithm(){
        super();
        this.foundFields = new LinkedList<Integer>();
    }
    
    
    
    @Override
    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Solves the whole sudoku. Assumes the Sudoku is valid.
     */
    @Override
    public boolean solve() {
        steps = 0;
        candidates = sudoku.getTable();
//        long startTime = System.nanoTime();
        boolean solve = findSolution(); // updates values in-place

//        long endTime = System.nanoTime();
//        long duration = endTime - startTime;
        sudoku.setValues(candidates);
//        Log.v(TAG, "Backtracking algorithm took " + (double)duration / 1000000000.0 + 
//                   " seconds and took " + steps + " steps");
//        Log.v(TAG, "The solved Sudoku: \n" + Arrays.deepToString(candidates));
        return solve;
    }
    
    
    @Override
    public boolean step() {
        if(candidates == null) candidates = sudoku.getTable();
        if(!isSolved) findSolution();
        int row, column;
        Integer next;
        
        // pop the first Element from the queue.
//        Log.v(TAG, "Backtracking step: " + foundFields.toString());
        do {
            next = foundFields.poll();
            if(next == null)  return false;
            row = next/9;
            column = next % 9;
        } while (sudoku.getField(row, column).isFound());

        sudoku.setValue(row, column, candidates[row][column]);
        return true;
    }
    
    
    

    /**
     * The main recursive algorithm. Updates the values in-place.
     * 
     * @param candidates
     *            - The unsolved Sudoku as array.
     * @return true, if the sudoku was solved.
     */
    private boolean findSolution() {
//        steps++;
        // find the next empty square
        int next = findNextEmptySquare();
        // if there is no empty square: return the solution
        if (next == -1) {
            Log.v(TAG, "Solution found for Sudoku");
            isSolved = true;
            return true;
        }
        // else
        int rowIndex = next / 9;
        int columnIndex = next % 9;

        for (int c = 1; c <= 9; c++) {
            if (isNumberAllowedInField(c, rowIndex, columnIndex)) {
                candidates[rowIndex][columnIndex] = c;
                foundFields.add(next);
                if (findSolution()) {
                    return true;
                }

                candidates[rowIndex][columnIndex] = 0;
                foundFields.removeLast();
            }
        }
        return false;
    }

    // returns a 0-based index between 0 and 80. -1 if nothing found.
    private int findNextEmptySquare() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (candidates[i][j] == 0) {
                    // Log.v(TAG, "Found empty square at " + i + "," + j);
                    return (i * 9 + j);
                }
            }
        }
        return -1; // nothing found
    }

    /**
     * Checks if a number can be placed in the given field
     * 
     * @param number
     *            - the number to be placed
     * @param rowIndex
     *            - the row
     * @param columnIndex
     *            - the column
     * @param candidates
     *            - the Sudoku array.
     * @return - true or false
     */
    private boolean isNumberAllowedInField(int number, int rowIndex, int columnIndex) {
        return (isNumberAllowedInRow(number, rowIndex, columnIndex)
                && isNumberAllowedInColumn(number, rowIndex, columnIndex) && isNumberAllowedInSquare(
                    number, rowIndex, columnIndex));
    }

    private boolean isNumberAllowedInRow(int number, int rowIndex, int columnIndex) {
        for (int i = 0; i < 9; i++) {
            if (i != columnIndex && candidates[rowIndex][i] == number) {
                return false;
            }
        }
        return true;
    }

    private boolean isNumberAllowedInColumn(int number, int rowIndex, int columnIndex) {
        for (int i = 0; i < 9; i++) {
            if (i != rowIndex && candidates[i][columnIndex] == number) {
                return false;
            }
        }
        return true;
    }

    private boolean isNumberAllowedInSquare(int number, int rowIndex, int columnIndex) {
        int qx = (rowIndex / 3) * 3;
        int qy = (columnIndex / 3) * 3;
        for (int i = qx; i < qx + 3; i++) {
            for (int j = qy; j < qy + 3; j++) {
                if (i != rowIndex && i != columnIndex && candidates[i][j] == number) {
                    return false;
                }
            }
        }
        return true;
    }

}
