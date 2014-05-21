package ch.zahw.students.sudokuhelper.solve;

import java.util.EventListener;

public interface HiddenSingleEventListener extends EventListener {
    void hiddenSingleFound(HiddenSingleEvent e);
}
