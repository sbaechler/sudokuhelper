package ch.zahw.students.sudokuhelper;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sudoku_fields);
		fillTable();
	}

	private void fillTable() {
		TableLayout tLayout = (TableLayout) findViewById(R.id.sudoku_table);
		sudokuRow(tLayout);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void sudokuRow(TableLayout tLayout) {

		int buttom;

		for (int i = 0; i < 9; i++) {
			TableRow row = new TableRow(this);

			if (i % 3 == 0) {
				buttom = 2;
			} else {
				buttom = 1;
			}

			sudokuCell(tLayout,row, buttom);

			tLayout.addView(row);
		}
	}

	private void sudokuCell(TableLayout tl, TableRow tr, int bottom) {
		int right;

		for (int i = 0; i < 9; i++) {

			if (i % 3 == 0) {
				right = 3;
			} else {
				right = 1;
			}
			
			EditText sudokuCell = new EditText(this);
			sudokuCell.setGravity(Gravity.CENTER);
			sudokuCell.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
			sudokuCell.setText(String.valueOf(i));
			sudokuCell.setBackgroundColor(Color.WHITE);
//TODO Backround table, params, fill with 9*9 array
			TableLayout.LayoutParams params = new TableLayout.LayoutParams(10, 30);
			params.setMargins(0, 0, right, bottom);
			params.weight = 1;
			sudokuCell.setLayoutParams(params);

			tr.addView(sudokuCell);
		}
	}

}
