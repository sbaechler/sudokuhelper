package ch.zahw.students.sudokuhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	private static final String TAG = "SudokuHelper::MainActivity";
	private MainHelper mainHelper;
	private final int REQUEST_CODE = 1;

	/** Called when the user clicks the Capture button */
	public void doCapture(View view) {
		Log.i(TAG, "called doCapture");
		Intent intent = new Intent(this, CaptureActivity.class);
		startActivityForResult(intent, REQUEST_CODE);
	}

	/** Called when the user clicks the Solve button */
	public void doSolve(View view) {
		Log.i(TAG, "called doSolve");
		mainHelper.solve(view);
	}

	/** Called when the user clicks the Solve button */
	public void doStep(View view) {
		Log.i(TAG, "called doStep");
		mainHelper.step(view);
	}

	/** Called when the user clicks the Test button 
	 *  (currently not in use) */
	public void doTest(View view) {
		Intent intent = new Intent(this, TestActivity.class);
		startActivityForResult(intent, REQUEST_CODE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mainHelper = new MainHelper(this);
		mainHelper.createTable(findViewById(R.id.sudoku_table));
	}

	/**
	 * Callback from the CaptureActivity.
	 * If successful returns a string containing all the found digits.
	 * The format of one element is row,column:primary-secondary;
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  Log.v(TAG, "Result Callback called");
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	    if (data.hasExtra("candidates")) {
	        String candidates = data.getExtras().getString("candidates");
                Log.v(TAG, "Got candidates: "+candidates);
                mainHelper.parseResult(candidates);
	    }
	  }
	  
	  mainHelper.showSudoku();
	  	  
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}