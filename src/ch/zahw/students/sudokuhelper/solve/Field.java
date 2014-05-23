package ch.zahw.students.sudokuhelper.solve;

public interface Field {
    int getNumber();

    boolean isFounded();

    boolean isInitialValue();

    boolean isValid();

}
