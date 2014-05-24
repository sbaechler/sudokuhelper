package ch.zahw.students.sudokuhelper.solve.algorithm;

import ch.zahw.students.sudokuhelper.solve.Sudoku;

public abstract class AAlgorithm {

    protected Sudoku sudoku;
    
    public AAlgorithm(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

	public abstract Sudoku solve();
	
	
}
