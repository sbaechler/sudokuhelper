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

	/** Called when the user clicks the Capture button */
	public void doCapture(View view) {
		Log.i(TAG, "called doCapture");
		Intent intent = new Intent(this, CaptureActivity.class);
		startActivity(intent);
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

	/** Called when the user clicks the Test button */
	public void doTest(View view) {
		Intent intent = new Intent(this, TestActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mainHelper = new MainHelper(this);
		mainHelper.createTable(findViewById(R.id.sudoku_table));
		mainHelper.startSudoku();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}