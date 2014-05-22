package ch.zahw.students.sudokuhelper.solve;

import java.util.EventListener;

public interface NakedSingleEventListener extends EventListener {
    void nakedSingleFound(NakedSingleEvent e);
}
