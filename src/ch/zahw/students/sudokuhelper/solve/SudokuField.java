package ch.zahw.students.sudokuhelper.solve;

import java.util.ArrayList;

public class SudokuField {

	private ArrayList<Integer> availableNumbers;
	private int number;
	private boolean startGap;
	private boolean isFounded;
	private int row;
	private int column;

	public SudokuField(int row, int column) {
		this.number = 0;
		this.isFounded = false;
		this.startGap = false;
		this.row = row;
		this.column = column;
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
	
}
