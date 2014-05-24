package ch.zahw.students.sudokuhelper.solve;

public interface Field {
    int getNumber();

    boolean isFound();

    boolean isInitialValue();

    boolean isValid();

}
