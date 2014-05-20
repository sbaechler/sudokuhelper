package ch.zahw.students.sudokuhelper.solve;

import java.util.ArrayList;
import java.util.Observable;


/**
 * A single sudoku field (Model layer)
 * GUI elements can listen to updates.
 */
public class SudokuField extends Observable implements Field {

    // TODO: Ev. können Felder einander gegenseitig beobachten und bei Änderungen
    // reagieren, z.B. mögliche Nummern selbständig löschen, oder einen Fehler werfen.
    // http://openbook.galileocomputing.de/javainsel/javainsel_10_002.html
    
	private ArrayList<Integer> availableNumbers;  // HashSet
	private int number;
	private boolean startGap;
	private boolean isFounded;
	private boolean isValid;
	private int row;
	private int column;

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
	

	void setNumber(int number) {
	    setChanged();
	    this.number = number;
	    this.isValid = this.validate();
	    notifyObservers(new FieldValues());
	}

	void setIsValid(Boolean valid){
		setChanged();
		this.isValid = valid;
		notifyObservers(new FieldValues());
	}

	private Boolean validate(number){
		// TODO validation logic
		return number == 0 || availableNumbers.length < 1 || availableNumbers.contains(number)
	}

	public Boolean isValid(){
		return isValid;
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
		if(isFounded) {
			clearAvailableNumbers();
		} else {
			// TODO
		}
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

	
	/**
	 * This class can be passed to the listener classes.
	 * Only discloses important information.
	 */
	class FieldValues implements Field {

	    @Override
	    public int getNumber() {
	        return number;
	    }
	    
	    @Override
	    public boolean isFounded() {
    	        return isFounded;
	    }
	    
	    @Override
	    public boolean isStartGap() {
	        return startGap;
	    }

	    @Override
	    public boolean isValid(){
	    	return isValid;
	    }
	            
	}
	
}
