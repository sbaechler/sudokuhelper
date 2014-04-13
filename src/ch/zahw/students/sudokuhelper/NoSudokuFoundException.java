package ch.zahw.students.sudokuhelper;

@SuppressWarnings("serial")
public class NoSudokuFoundException extends Exception {

    public NoSudokuFoundException() {
        // TODO Auto-generated constructor stub
    }

    public NoSudokuFoundException(String detailMessage) {
        super(detailMessage);
        // TODO Auto-generated constructor stub
    }

    public NoSudokuFoundException(Throwable throwable) {
        super(throwable);
        // TODO Auto-generated constructor stub
    }

    public NoSudokuFoundException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        // TODO Auto-generated constructor stub
    }

}
