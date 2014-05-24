package ch.zahw.students.sudokuhelper.solve.algorithm;

import android.util.Log;
import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuField;

public class HiddenSingleAlgorithm implements SudokuSolver {
    private static final String TAG = "SudokuHelper::HiddenSingleAlgorithm";

    private Sudoku sudoku;

    @Override
    public boolean solve() {
        SudokuField sf;

        start:

        // über zeilen iterieren
        for (int row = 0; row < 9; row++) {

            // über spalten iterieren
            for (int column = 0; column < 9; column++) {
                // sudokuField
                sf = sudoku.getField(row, column);

                // es werden nur sudoku Felder genommen die keine gesetzte,
                // definitive Zahl haben
                if (sf.isFounded() == false) {

                    // über alle mögliche Zahlen iterieren
                    for (int number : sf.getAvailableNumbers()) {

                        // row-, col- and square check
                        if (isHiddenSingle(sudoku.getRowSudokuFields(row), number, sf)
                                || isHiddenSingle(sudoku.getColumnSudokuFields(column), number, sf)
                                || isHiddenSingle(sudoku.getSudokuSquare(row), number, sf)) {

                            sudoku.setValue(row, column, number);
                            sf.setFounded(true);

                            // von vorne anfangen
                            continue start;
                        }

                    }
                }

            }

        }

        // TODO
        return true;
    }

    /**
     * 
     * @param nineFields
     *            die zu untersuchende SudokuFelder
     * @param number
     *            Diese Zahl wird überprüft ob es ein hidden single ist
     * @param field
     *            Das Feld welche die untersuchende Zahl besitzt
     * @return true -> hidden single, false -> kein hidden single
     */
    private Boolean isHiddenSingle(SudokuField[] nineFields, int number, SudokuField field) {
        SudokuField sField;

        for (int i = 0; i < nineFields.length; i++) {
            sField = nineFields[i];

            if (sField.isFounded() || sField.equals(field)) {
                continue;
            }

            // Falls ein anderes Sudokufeld die gleiche Zahl als mögliche Zahl
            // hat, wird false zurückgegeben da es kein hidden single ist
            if (sField.getAvailableNumbers().contains(number)) {
                return false;
            }
        }

        Log.v(TAG, "HiddenSingleFound: row = " + field.getRow() + ", column = " + field.getColumn()
                + "->" + number);
        return true;
    }

    @Override
    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    @Override
    public boolean step() {
        return false;
    }

}
