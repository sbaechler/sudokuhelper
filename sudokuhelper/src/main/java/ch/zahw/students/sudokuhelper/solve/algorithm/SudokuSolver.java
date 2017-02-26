package ch.zahw.students.sudokuhelper.solve.algorithm;

import ch.zahw.students.sudokuhelper.solve.Sudoku;

public interface SudokuSolver {
    
    public void setSudoku(Sudoku sudoku);
    
    /**
     * Tries to solve as much of the Sudoku as possible.
     * @return - True if at least one number was found. False otherwise.
     */
    public boolean solve();
    
    
    /**
     * Tries to solve exactly one number
     * @return - True if successful - false otherwise
     */
    public boolean step();

}
