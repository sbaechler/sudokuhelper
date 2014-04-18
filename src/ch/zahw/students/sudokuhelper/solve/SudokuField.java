package ch.zahw.students.sudokuhelper.solve;

import java.util.ArrayList;

public class SudokuField {

	private ArrayList<Integer> availableNumbers;
	private int number;
	private boolean startGap;
	private boolean isFounded;

	public SudokuField() {
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

}
