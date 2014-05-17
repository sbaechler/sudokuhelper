package ch.zahw.students.sudokuhelper.test;

import ch.zahw.students.sudokuhelper.MainActivity;
import ch.zahw.students.sudokuhelper.MainHelper;
import ch.zahw.students.sudokuhelper.R;
import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuManager;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.TableLayout;

public class GuiTest extends ActivityInstrumentationTestCase2<MainActivity> {
    
    public GuiTest() {
        super(MainActivity.class);
    }

    private Sudoku sudoku;
    private MainActivity mActivity;
    private Instrumentation mInstrumentation;
    private MainHelper mainHelper;
    
    protected void setUp() throws Exception{
        super.setUp();
        mInstrumentation = getInstrumentation();
        mActivity = getActivity(); // get a references to the app under test
    }
    
    @UiThreadTest
    public void testManualOverrideNumbers(){
        mainHelper = new MainHelper(mActivity);
        mainHelper.createTable(mActivity.findViewById(ch.zahw.students.sudokuhelper.R.id.sudoku_table));

        
        // The first and second cells are empty.
        
        
        
        // The first cell has the number 1
        // The number is marked as good (green)
        
        
        
        // The user taps on the cell
        
        
        // The keyboard appears. He enters the number 2
        
        
        // The number 2 is in cell 1.
        
        // The number is marked as good (green)
        
        // Add the number 2 to cell 2
        
        // Both cells are marked as invalid
        
        
        // Delete the first cell
        
        // The 2nd cell is valid again.
        
        
    }

}
