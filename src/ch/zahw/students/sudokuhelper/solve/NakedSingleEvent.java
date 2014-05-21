package ch.zahw.students.sudokuhelper.solve;

import java.util.EventObject;

public class NakedSingleEvent extends EventObject {
    SudokuField field;

    public NakedSingleEvent(Object source) {
        super(source);
        field = (SudokuField) source;
    }
    
    public int getRow(){
        return field.getRow();
    }
    public int getColumn(){
        return field.getColumn();
    }
    public int getNumber(){
        return field.getNumber();
    }
}