package ch.zahw.students.sudokuhelper.solve;

import java.util.ArrayList;

import android.widget.TextView;

public class SudokuField {

	private ArrayList<Integer> availableNumbers;
	private int number;
	private boolean startGap;
	private boolean isFounded;
	private int row;
	private int column;
	private TextView view = null;

	public SudokuField(int row, int column) {
	    this.row = row;
	    this.column = column;
	    this.reset();
	}

	/*
	 * Resets the field to its initial state.
	 */
	public void reset(){
	    this.number = 0;
	    this.isFounded = false;
	    this.startGap = false;
	    this.availableNumbers = new ArrayList<Integer>();
	    initAvailableNumbers();
	}
	
	private void initAvailableNumbers() {
		for (int i = 1; i < 10; i++) {
			availableNumbers.add(i);
		}
	}

	public void setNumber(int number) {
	    this.number = number;
	}
	


	public int getNumber() {
		return number;
	}
	
	public String getNumberAsString(){
	    if (number > 0){
	        return Integer.toString(number);
	    } else {
	        return "";
	    }
	}

	public ArrayList<Integer> getAvailableNumbers() {
		return availableNumbers;
	}

	public int getSizeOfAvailableNumbers() {
		return availableNumbers.size();
	}

	public void setFounded(boolean isFounded) {
		this.isFounded = isFounded;
	}

	public boolean isFounded() {
		return isFounded;
	}

	public void setStartGap(boolean startGap) {
		this.startGap = startGap;
	}

	public boolean isStartGap() {
		return startGap;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void clearAvailableNumbers() {
		availableNumbers.clear();
		availableNumbers.add(0);
	}

	public TextView getView(){
	    return this.view;
	}
	
        public void setView(TextView sudokuCell) {
            this.view = sudokuCell;
        }
    
    
	
}
