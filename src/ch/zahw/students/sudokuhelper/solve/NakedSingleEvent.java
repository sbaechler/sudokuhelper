package ch.zahw.students.sudokuhelper.solve;

import java.util.EventObject;

public class NakedSingleEvent extends EventObject {
    private static final long serialVersionUID = 4113639673450735930L;
    SudokuField field;
    int candidate;

    public NakedSingleEvent(Object source, int candidate) {
        super(source);
        this.candidate = candidate;
        field = (SudokuField) source;
    }
    
    public int getRow(){
        return field.getRow();
    }
    public int getColumn(){
        return field.getColumn();
    }
    public int getCandidate(){
        return candidate;
    }
}