package ch.zahw.students.sudokuhelper.test;

import ch.zahw.students.sudokuhelper.MainActivity;
import ch.zahw.students.sudokuhelper.MainHelper;
import ch.zahw.students.sudokuhelper.solve.Sudoku;
import android.annotation.TargetApi;
import android.app.Instrumentation;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.NumberPicker;

public class GuiTest extends ActivityInstrumentationTestCase2<MainActivity> {
    
    public GuiTest() {
        super(MainActivity.class);
    }

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
        
        final TextView cell1 = (TextView) mActivity.findViewById(ch.zahw.students.sudokuhelper.R.id.table_cell_00);
        final TextView cell2 = (TextView) mActivity.findViewById(ch.zahw.students.sudokuhelper.R.id.table_cell_01);
        // The first and second cells are empty.
        assertEquals("The first cell must be empty", "", cell1.getText().toString());
        assertEquals("The second cell must be empty", "", cell2.getText().toString());
        
        // The first cell has the number 1
        mActivity.runOnUiThread(
            new Runnable(){
                public void run(){
                    cell1.setText("1");
                    assertEquals("The first cell has value 1", "1", cell1.getText().toString());
                }
            }           
        );
                                
        // The number is marked as good (green)
        
               
        // The user taps on the cell
        TouchUtils.clickView(this, cell1);
        

        
        // The keyboard appears. He enters the number 2
//        mActivity.runOnUiThread(
//            new Runnable(){
//                public void run(){
//                    NumberPicker np = (NumberPicker) mainHelper.getDialog().findViewById(
//                            ch.zahw.students.sudokuhelper.R.id.numberPicker1);
//                    Log.v("Number picker", np.toString());
//                    
////                    assertNotNull("Number picker is not null", np);
////                    assertEquals("The number picker displays a 1", 1, np.getValue());
//                    np.setValue(2);
////                    assertEquals("The number picker displays a 2", 2, np.getValue());
//                }
//            }           
//        );
        
        // assertEquals("The first cell has value 2", "2", cell1.getText().toString());
      
        
        // The number 2 is in cell 1.

        // The first cell has the number 1
        mActivity.runOnUiThread(
            new Runnable(){
                public void run(){
                    cell1.setText("2");
                    assertEquals("The first cell has value 2", "2", cell1.getText().toString());
                
                    cell2.setText("2");
                }
            }           
        );
        
        // The number is also in the Sudoku
        
        
        // The number is marked as good (green)
        
        // Add the number 2 to cell 2
       
        
        // Both cells are marked as invalid
        
        
        // Delete the first cell
        
        // The 2nd cell is valid again.
        
        
    }

}
