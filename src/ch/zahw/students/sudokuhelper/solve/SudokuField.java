package ch.zahw.students.sudokuhelper.solve;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * A single sudoku field (Model layer) GUI elements can listen to updates.
 */
public class SudokuField extends Observable implements Field {

    private static final String TAG = "SudokuHelper::SudokuField";
    private Set<Integer> availableNumbers; // ConcurrentSet
    private int number;
    private boolean isInitialValue;
    private boolean isValid;
    private final int row;
    private final int column;

    public SudokuField(int row, int column) {
        this.row = row;
        this.column = column;
        this.reset();
    }

    /*
     * Resets the field to its initial state.
     */
    public void reset() {
        this.number = 0;
        this.isInitialValue = false;
        this.availableNumbers = new ConcurrentSkipListSet<Integer>();
        initAvailableNumbers();
    }

    private void initAvailableNumbers() {
        for (int i = 1; i <= 9; i++) {
            availableNumbers.add(i);
        }
    }

    void setNumber(int number) {
        setChanged();
        this.number = number;
        this.isValid = this.validate(number);
        notifyObservers(new FieldValues());
    }

    void setIsValid(Boolean valid) {
        setChanged();
        this.isValid = valid;
        notifyObservers(new FieldValues());
    }

    private boolean validate(int number) {
        // TODO validation logic
        return number == 0 || availableNumbers.contains(number);
    }

    public boolean isValid() {
        return isValid;
    }

    public int getNumber() {
        return number;
    }

    public Set<Integer> getAvailableNumbers() {
        if(!isFound()){
            return (Set<Integer>) availableNumbers;
        } else {
            Set<Integer> numbers = new HashSet<Integer>();
            numbers.add(number);
            return numbers;
        }
        
    }

    public boolean isFound() {
        return  number > 0;
    }

    public boolean isNakedSingle() {
        return !isFound() && !isInitialValue && availableNumbers.size() == 1;
    }

    // lock the preset field. This method is called on solve.
    void lock() {
        isInitialValue = (number > 0);
        if(isInitialValue){
            setChanged();
            notifyObservers(new FieldValues());
        }
    }

    public boolean isInitialValue() {
        return isInitialValue;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    /**
     * Removes a number from the list of available numbers unless the passed
     * reference field is the actual field.
     * 
     * @param number
     *            - the number to remove
     * @param reference
     *            - the field to skip.
     */
    void removeAvailableNumber(int number, SudokuField skip) {
        if (skip != this) {
            availableNumbers.remove(number);
        }
    }

    void addAvailableNumber(int number, SudokuField skip) {
        if (skip != this) {
            availableNumbers.add(number);
            if (this.isValid != validate(number)) {
                setIsValid(validate(number));
            }

        }
    }

    /*
     * Returns the first allowed number (for the solve algorithms) or 0 if the
     * number cannot be changed.
     */
    public int getFirstAllowedNumber() {
        return isFound() ? number : availableNumbers.iterator().next();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SudokuField) {
            SudokuField field = (SudokuField) o;
            return field.getRow() == row && field.getColumn() == column;
        }

        return false;
    }
    
    @Override
    public int hashCode() {
        return (row*9+column);
    }

    /**
     * Checks if the given number would be allowed in the field
     * 
     * @param number
     *            - the number candidate
     * @return true or false.
     * @throws IllegalStateException
     *             if the allowed numbers has not been filled out yet.
     */
    public boolean isNumberAllowed(int candidate) {
        if (availableNumbers.isEmpty()) {
            throw new IllegalStateException("Allowed Numbers has not been filled out yet");
        } else if (candidate == 0) {
            throw new IllegalArgumentException("0 should not be tested (Field " + row + ", "
                    + column + ")");
        }
        return availableNumbers.contains(candidate);
    }

    /**
     * This class can be passed to the listener classes. Only discloses
     * important information.
     */
    class FieldValues implements Field {

        @Override
        public int getNumber() {
            return number;
        }

        @Override
        public boolean isFound() {
            return number > 0;
        }

        @Override
        public boolean isInitialValue() {
            return isInitialValue;
        }

        @Override
        public boolean isValid() {
            return isValid;
        }

    }

    @Override
    public String toString() {
        String value = "[" + row + ", " + column + "] : " + number;
        if (isValid)
            value = value + " V";
        if (isFound())
            value = value + " F";
        return value;
    }

}
