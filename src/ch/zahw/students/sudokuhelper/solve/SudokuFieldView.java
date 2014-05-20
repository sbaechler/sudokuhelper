package ch.zahw.students.sudokuhelper.solve;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.widget.TextView;

public class SudokuFieldView extends TextView implements Observer {
    
    private int row;
    private int column;

    public SudokuFieldView(Context context, int row, int column) {
        super(context);
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }
    
    public int getColumn() {
        return column;
    }
    
    /**
     * Returns the content of the GUI element as integer or 0 if it is empty
     * @return number 0-9
     */
    public int getValue(){
        try {
            return Integer.parseInt(getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    /**
     * Accepts an integer as input to display in the field.
     * If the value is 0, then the field is erased.
     * @param number - number to display
     */
    private void setValue(int number){
        if(number > 0 && number <= 9) {
            setText(Integer.toString(number));
        } else {
            setText(" ");
        }
        this.setMinHeight(this.getWidth());
    }

    /**
     * Callback function from SudokuField if the value is changed.
     */
    @Override
    public void update(Observable field, Object value) {
        setValue(((Field) value).getNumber());
        // TODO: do something with isFounded and isStartGap
    }
   
}
