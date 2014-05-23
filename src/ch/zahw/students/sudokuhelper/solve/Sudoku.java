package ch.zahw.students.sudokuhelper.solve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.util.Log;

public class Sudoku implements Observer, NakedSingleEventListener {

	private static final String TAG = "SudokuHelper::Sudoku";
	private SudokuField[] fields;
	private HashSet<SudokuField> invalidFields;
	private int emptyFields;
	private boolean isLocked = false;
	private List<NakedSingleEventListener> nakedSingleListeners;
	
	/**
	 * The constructor for a new, empty Sudoku
	 */
	public Sudoku() {
	    fields = new SudokuField[81];
            nakedSingleListeners = new ArrayList<NakedSingleEventListener>();
	    reset();
	}

	/**
	 * The constructor for a new Sudoku with given numbers.
	 * 
	 * @param candidates - 2-dimensional array of numbers.
	 */
	public Sudoku(int[][] candidates) {
		fields = new SudokuField[81];
	        nakedSingleListeners = new ArrayList<NakedSingleEventListener>();
		setValues(candidates);
	}

	public SudokuField[] getFields() {
		return fields;
	}

	/**
	 * Clears the sudoku and resets the fields so all numbers are allowed again.
	 */
	private void reset() {
		int index;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				index = (i * 9) + j;
				if (fields[index] != null) {
					fields[index].reset();
				} else {
					fields[index] = new SudokuField(i, j);
					fields[index].setListener(this);
				}
			}
		}
		invalidFields = new HashSet<SudokuField>();
		emptyFields = 81;
	}

	// call this method on solve.
	public void lockSudoku() {
		for (int i = 0; i < 81; i++) {
			fields[i].lock();
		}
		isLocked = true;
	}

	public int getNumber(int row, int column) {
		return getField(row, column).getNumber();
	}
	
	public int[][] getTable(){
	    int[][] sudokuTable = new int[9][9];
	    for(int i=0; i<9; i++){
	        for(int j=0; j<9; j++){
	            sudokuTable[i][j] = getField(i,j).getNumber();
	        }
	    }
	    return sudokuTable;
	}


	/**
        * Sets the values for an existing Sudoku.
        * @param candidates - 2-dimensional array of numbers.
        */
        public void setValues(int[][] candidates) {
                reset();
                for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                                setValue(i, j, candidates[i][j]);
                        }
                }
                // TODO: validate Sudoku
        }
	
	/**
	 * Sets the value of a single field.
	 * 
	 * @param row
	 *            - Sudoku row (0-index)
	 * @param column
	 *            - Sudoku column (0-index)
	 * @param value
	 *            - The value (int) or 0 for none.
	 */
	public void setValue(int row, int column, int value) {
		SudokuField field = fields[(row * 9) + column];
		int oldValue = field.getNumber();
		field.setNumber(value);
		if (value > 0) { // a number
			// decrease the empty field count
			if (oldValue > 0) {
			    emptyFields -= 1;
			}
			
			if (field.isValid()) {
			    removeAvailableNumbersOnOtherFields(row, column, value, field);
			}
					
		} else { // empty
			
			if (oldValue > 0) {
				emptyFields += 1;
			}
			
		}		
		Log.v(TAG, "setValue: " + row + ", "
				+ column+ " -> " +value+" Valid:"+field.isValid());		
	}
	
	/**
	 * Allows the user to correct a badly recognized number
	 * @param row
	 * @param column
	 * @param value
	 */
	public void manuallyOverrideValue(int row, int column, int value) {
	    SudokuField field = fields[(row * 9) + column];
	    int oldValue = getNumber(row, column);
	    setValue(row, column, 0);
	    addAvailableNumbersOnOtherFields(row, column, oldValue, field);
	    setValue(row, column, value);
	}

	@Deprecated
	public void setFounded(int row, int column, Boolean value) {
		fields[(row * 9) + column].setFounded(value);
	}

	public SudokuField[] getColumnSudokuFields(int column) {
		SudokuField[] sf = new SudokuField[9];

		for (int i = 0; i < 9; i++) {
			sf[i] = fields[i * 9 + column];
		}

		return sf;
	}

	/**
	 * Returns one row of the Sudoku as Fields
	 * 
	 * @param row
	 *            - row number
	 * @return Array of SudokuFields of the length 9.
	 */
	public SudokuField[] getRowSudokuFields(int row) {
		SudokuField[] sf = new SudokuField[9];

		for (int i = 0; i < 9; i++) {
			sf[i] = fields[row * 9 + i];
		}
		return sf;
	}

	public int[] getRowValues(int row) {
		int[] values = new int[9];
		for (int i = 0; i < 9; i++) {
			values[i] = fields[row * 9 + i].getNumber();
		}
		return values;
	}

	/**
	 * 
	 * @param index
	 *            - geht von 0 bis 8
	 * @return
	 */
	public SudokuField[] getSudokuSquare(int index) {
		SudokuField[] sf = new SudokuField[9];
		int sfIndex = 0;

		int startRow = (index / 3) * 3;
		int startColumn = (index % 3) * 3;

		for (int row = startRow; row < startRow + 3; row++) {
			for (int column = startColumn; column < startColumn + 3; column++) {
				sf[sfIndex] = fields[(row * 9) + column];
				sfIndex++;
			}
		}

		return sf;
	}

	public SudokuField[] getSudokuSquare(int row, int column) {
		SudokuField[] sf = new SudokuField[9];
		int startRow = (row / 3) * 3;
		int startColumn = (column / 3) * 3;
		int sfIndex = 0;
		for (int sr = startRow; sr < startRow + 3; sr++) {
			for (int sc = startColumn; sc < startColumn + 3; sc++) {
				sf[sfIndex] = fields[(sr * 9) + sc];
				sfIndex++;
			}
		}
		return sf;
	}

	public SudokuField getField(int row, int column) {
		return fields[(row * 9) + column];
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

	private void removeInvalidField(SudokuField field) {
		if (field.isValid()) {
			invalidFields.remove((SudokuField) field);
		}
	}

	/**
	 * Hier wird die gefundene Zahl aus der Zeile, Spalte und im Quadrat
	 * entfernt sodass die betreffende Felder diese Zahl nicht mehr zur
	 * Verfügung haben
	 */
	private void removeAvailableNumbersOnOtherFields(int row, int column,
			int number, SudokuField current) {

		SudokuField[] rowArray = getRowSudokuFields(row);
		SudokuField[] columnArray = getColumnSudokuFields(column);
		SudokuField[] squareArray = getSudokuSquare(row, column);

		for (int i = 0; i < 9; i++) {
			rowArray[i].removeAvailableNumber(number, current);
			columnArray[i].removeAvailableNumber(number, current);
			squareArray[i].removeAvailableNumber(number, current);
		}
	}
	 /**
         * Re-adds a number to the set of available numbers (after manual override)
         */
        private void addAvailableNumbersOnOtherFields(int row, int column,
                        int number, SudokuField current) {

                SudokuField[] rowArray = getRowSudokuFields(row);
                SudokuField[] columnArray = getColumnSudokuFields(column);
                SudokuField[] squareArray = getSudokuSquare(row, column);

                for (int i = 0; i < 9; i++) {
                        rowArray[i].addAvailableNumber(number, current);
                        columnArray[i].addAvailableNumber(number, current);
                        squareArray[i].addAvailableNumber(number, current);
                }
        }
	

	/**
	 * Two sudokus are equal if their values are equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!o.getClass().equals(getClass()))
			return false;
		return Arrays.deepEquals(this.getTable(), ((Sudoku) o).getTable());
	}

	/**
	 * The hashCode is calculated from the number array, so it is slow. Don't
	 * put a Sudoku in a Hashed Collection.
	 */
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(this.getTable());
	}

	/**
	 * Callback from the field class on update. Sets adds the field to invalid
	 * fields if they are valid
	 */
	@Override
	public void update(Observable field, Object value) {
		SudokuField sf = (SudokuField) field;
		
		if (((Field) value).isValid()) {
			removeInvalidField((SudokuField) field);
		} else {
			invalidFields.add((SudokuField) field);
		}
	}

	/**
	 * Adds a listener (a solve algorithm) to listen to the naked single found event.
	 * @param listener - A solve algorithm
	 */
	public void addNakedSingleEventListener(NakedSingleEventListener listener){
	    this.nakedSingleListeners.add(listener);
	}
	
	
	/**
	 * Callback sent from a cell if the count of allowed numbers reaches exactly
	 * 1. An event is sent to the Sudoku to enter this number.
	 */
	@Override
	public void nakedSingleFound(NakedSingleEvent e) {
		Log.v(TAG, "nakedSingleFound: row = " + e.getRow() + ", column = "
				+ e.getColumn() + "->" + e.getCandidate());
		// pass it to the solve algorithm and have them deal with it.
//		for(NakedSingleEventListener listener : nakedSingleListeners) {
//		    listener.nakedSingleFound(e);
//		}
	}

}
