package ch.zahw.students.sudokuhelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {
    private static final String TAG = "SudokuHelper::MainActivity";
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /** Called when the user clicks the Capture button */
    public void doCapture(View view) {
        Log.i(TAG, "called doCapture");
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivity(intent);
    }
    
    /** Called when the user clicks the Test button */
    public void doTest(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }
    

}
