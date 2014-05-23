package ch.zahw.students.sudokuhelper.solve;

import java.util.Observable;
import java.util.Observer;

import ch.zahw.students.sudokuhelper.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

public class SudokuFieldView extends TextView implements Observer {
    
    private static final String TAG = "SudokuHelper::SudokuFieldView";
    private int row;
    private int column;

    public SudokuFieldView(Context context, int row, int column) {
        super(context);
        this.row = row;
        this.column = column;
        this.setTextAppearance(context, R.style.SudokuFont);
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
        Field updatedField = (Field) value;

        setValue(updatedField.getNumber());
        if(updatedField.getNumber() > 0) {
            if(!updatedField.isValid()){
                setBackgroundColor(Color.RED);
            } else if (updatedField.isFounded()){
                setBackgroundColor(Color.WHITE);
            } else {
                setBackgroundColor(Color.GREEN);
            }
        } else {
            setBackgroundColor(Color.WHITE);
        }

    }
   
}
