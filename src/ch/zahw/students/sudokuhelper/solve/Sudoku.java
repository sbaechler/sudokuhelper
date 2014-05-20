package ch.zahw.students.sudokuhelper.solve;

import java.util.HashSet;
import java.util.Set;

public class Sudoku {

    private SudokuField[] fields;
    private Set<SudokuField> invalidFields;
    private int emptyFields;

    /**
     * The constructor for a new, empty Sudoku
     */
    public Sudoku() {
        fields = new SudokuField[81];
        reset();
    }

    /**
     * The constructor for a new Sudoku with given numbers.
     * 
     * @param candidates - 2-dimensional array of numbers.
     */
    public Sudoku(int[][] candidates) {
        fields = new SudokuField[81];
        setValues(candidates);
    }
    
    public SudokuField[] getFields(){
        return fields;
    }

    private void reset() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(fields[(i*9)+j] != null){
                    fields[(i*9)+j].reset();
                } else {
                    fields[(i*9)+j] = new SudokuField(i, j);
                }
            }
        }
        invalidFields = new HashSet<SudokuField>();
        emptyFields = 81;
    }

    /**
     * Sets the values for an existing Sudoku.
     * 
     * @param candidates
     *            - 2-dimensional array of numbers.
     */
    public void setValues(int[][] candidates) {
        reset();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                setValue(i, j, candidates[i][j]);
            }
        }
    }

    public int getValue(){
        return sudoku.getValue();
    }

    /**
     * Sets the value of a single field.
     * 
     * @param row
     *            - int
     * @param column
     *            - int
     * @param value
     *            - The value (int) or 0 for none.
     */
    public void setValue(int row, int column, int value) {
        SudokuField field = fields[(row*9)+column];
        if (value != 0) {  // a number
            // decrease the empty field count
            if (field.getNumber() > 0) {
                emptyFields -= 1;
            }
        // TODO: validate field and update all referenced fields.
        } else {  // empty
            if (field.getNumber() > 0) {
                emptyFields += 1;
            }
        }
        field.setNumber(value);
        // TODO: restore all referenced fields for possible values
    }

    public void setFounded(int row, int column, Boolean value){
        fields[(row*9)+column].setFounded(value);
    }

    public SudokuField[] getColumnSudokuFields(int column) {
        SudokuField[] sf = new SudokuField[9];

        for (int i = 0; i < 9; i++) {
            sf[i] = fields[i*9+column];
        }

        return sf;
    }

    public SudokuField[] getRowSudokuFields(int row) {
        SudokuField[] sf = new SudokuField[9];

        for (int i = 0; i < 9; i++) {
            sf[i] = fields[row*9+i];
        }
        return sf;
    }

    /**
     * 
     * @param index
     *            geht von 0 bis 8
     * @return
     */
    public SudokuField[] getSudokuSquare(int index) {
        SudokuField[] sf = new SudokuField[9];
        int sfIndex = 0;

        int startRow = (index / 3) * 3;
        int startColumn = (index % 3) * 3;

        for (int row = startRow; row < startRow + 3; row++) {
            for (int column = startColumn; column < startColumn + 3; column++) {
                sf[sfIndex] = fields[(row*9)+column];
                sfIndex++;
            }
        }

        return sf;
    }

    public SudokuField getField(int row, int column) {
        return fields[(row*9)+column];
    }

    /**
     * Checks if the Sudoku has been solved (i.e. all fields have been filled)
     * 
     * @return true or false.
     */
    public Boolean isSolved() {
        return isValid() && emptyFields <= 0;
    }

    public Boolean isValid() {
        return invalidFields.size() == 0;
    }

}
