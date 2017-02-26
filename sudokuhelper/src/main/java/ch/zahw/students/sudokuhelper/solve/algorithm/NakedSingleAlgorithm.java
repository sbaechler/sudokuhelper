package ch.zahw.students.sudokuhelper.solve.algorithm;

import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuField;

public class NakedSingleAlgorithm implements SudokuSolver {

    private Sudoku sudoku;

    @Override
    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    @Override
    public boolean solve() {
        SudokuField sf = null;
        boolean isAnythingFound = false;
        start:

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                sf = sudoku.getField(row, column);

                if (sf.isNakedSingle()) {
                    sudoku.setValue(row, column, sf.getFirstAllowedNumber());
                    isAnythingFound = true;
                    continue start;
                }
            }
        }

        return isAnythingFound;
    }

    @Override
    public boolean step() {
        SudokuField sf = null;

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                sf = sudoku.getField(row, column);

                if (sf.isNakedSingle()) {
                    sudoku.setValue(row, column, sf.getFirstAllowedNumber());
                    return true;
                }

            }
        }

        return false;
    }

}
