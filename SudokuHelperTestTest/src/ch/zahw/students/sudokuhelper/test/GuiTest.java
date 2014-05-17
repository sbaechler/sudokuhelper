package ch.zahw.students.sudokuhelper.test;

import ch.zahw.students.sudokuhelper.MainActivity;
import ch.zahw.students.sudokuhelper.MainHelper;
import ch.zahw.students.sudokuhelper.R;
import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuManager;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.UiThreadTest;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView.BufferType;

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
    
    public void testManualOverrideNumbers(){
        mainHelper = new MainHelper(mActivity);
        
        mActivity.runOnUiThread(
            new Runnable(){
                public void run(){
                    mainHelper.createTable(mActivity.findViewById(ch.zahw.students.sudokuhelper.R.id.sudoku_table));
                }
            }           
        );
        
        final EditText cell1 = (EditText) mActivity.findViewById(ch.zahw.students.sudokuhelper.R.id.table_cell_00);
        final EditText cell2 = (EditText) mActivity.findViewById(ch.zahw.students.sudokuhelper.R.id.table_cell_00);
        // The first and second cells are empty.
        assertEquals("", cell1.getText().toString());
        assertEquals("", cell2.getText().toString());
        
        // The first cell has the number 1
        mActivity.runOnUiThread(
            new Runnable(){
                public void run(){
                    cell1.setText("1", BufferType.EDITABLE);
                    assertEquals("1", cell1.getText().toString());
                }
            }           
        );
                

                
        // The number is marked as good (green)
        
               
        // The user taps on the cell
        TouchUtils.clickView(this, cell1);
        
        
        // The keyboard appears. He enters the number 2
        
        
        // The number 2 is in cell 1.
        
        // The number is marked as good (green)
        
        // Add the number 2 to cell 2
        
        // Both cells are marked as invalid
        
        
        // Delete the first cell
        
        // The 2nd cell is valid again.
        
        
    }

}
