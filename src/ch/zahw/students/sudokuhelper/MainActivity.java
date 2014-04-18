package ch.zahw.students.sudokuhelper;

import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuField;
import ch.zahw.students.sudokuhelper.solve.SudokuManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

public class MainActivity extends Activity {
	private static final String TAG = "SudokuHelper::MainActivity";
	private int[][] cellIds = new int[9][9];
	private int[][] sudoku;
	private SudokuManager sudokuManager;
	private int lightGreen = Color.rgb(186, 243, 183);
	private boolean isSudokuSolved = false;

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.activity_main);
	// }

	/** Called when the user clicks the Capture button */
	public void doCapture(View view) {
		Log.i(TAG, "called doCapture");
		Intent intent = new Intent(this, CaptureActivity.class);
		startActivity(intent);

		isSudokuSolved = false;
	}

	/** Called when the user clicks the Solve button */
	public void doSolve(View view) {
		Log.i(TAG, "called doSolve");

		Sudoku sudokuFields = new Sudoku(sudoku);
		sudokuManager = new SudokuManager();

		sudokuManager.solveWithBetterApproach(sudokuFields);
		fillSolvedNumber(sudokuManager.solveWithBetterApproach(sudokuFields));
		isSudokuSolved = true;
	}

	/** Called when the user clicks the Solve button */
	public void doStep(View view) {
		Log.i(TAG, "called doStep");

		if (!isSudokuSolved) {
			Sudoku sudokuFields = new Sudoku(sudoku);
			sudokuManager = new SudokuManager();
			sudokuManager.solveWithBetterApproach(sudokuFields);
			isSudokuSolved = true;
		}
		
		fillSolvedCell(sudokuManager.getNextSolveOrder());
	}

	/** Called when the user clicks the Test button */
	public void doTest(View view) {
		Intent intent = new Intent(this, TestActivity.class);
		startActivity(intent);
	}

	public int[][] createSudokuSimply() {
		int[][] toFillSudoku = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// first: row, second: column
				toFillSudoku[i][j] = 0;
			}

		}

		toFillSudoku[0][0] = 8;
		toFillSudoku[0][1] = 1;
		toFillSudoku[0][2] = 2;
		toFillSudoku[0][7] = 9;

		toFillSudoku[1][0] = 5;
		toFillSudoku[1][2] = 3;
		toFillSudoku[1][4] = 2;
		toFillSudoku[1][6] = 1;

		toFillSudoku[2][3] = 1;
		toFillSudoku[2][4] = 5;
		toFillSudoku[2][8] = 6;

		toFillSudoku[3][3] = 2;
		toFillSudoku[3][4] = 4;
		toFillSudoku[3][7] = 1;
		toFillSudoku[3][8] = 5;

		toFillSudoku[4][2] = 7;
		toFillSudoku[4][3] = 5;
		toFillSudoku[4][7] = 2;

		toFillSudoku[5][1] = 8;
		toFillSudoku[5][3] = 3;
		toFillSudoku[5][7] = 4;

		toFillSudoku[6][0] = 9;
		toFillSudoku[6][5] = 4;

		toFillSudoku[7][2] = 1;
		toFillSudoku[7][6] = 7;
		toFillSudoku[7][8] = 9;

		toFillSudoku[8][0] = 3;
		toFillSudoku[8][1] = 5;
		toFillSudoku[8][2] = 8;

		return toFillSudoku;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		setContentView(R.layout.activity_main);
		createTable();
		sudoku = createSudokuSimply();
		fillSudokuTable(sudoku);
	}

	private int[][] dummySudoku() {

		int k = 0;
		int fillCount = 1;
		int subGrid = 1;
		int N = 3;
		int[][] sudoku = new int[N * N][N * N];
		for (int i = 0; i < N * N; i++) {
			if (k == N) {
				k = 1;
				subGrid++;
				fillCount = subGrid;
			} else {
				k++;
				if (i != 0)
					fillCount = fillCount + N;
			}
			for (int j = 0; j < N * N; j++) {
				if (fillCount == N * N) {
					sudoku[i][j] = fillCount;
					fillCount = 1;
				} else {
					sudoku[i][j] = fillCount++;
				}
			}
		}

		return sudoku;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void createTable() {
		TableLayout tLayout = (TableLayout) findViewById(R.id.sudoku_table);
		createRow(tLayout);
	}

	private void createRow(TableLayout tableLayout) {
		TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.WRAP_CONTENT,
				TableLayout.LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT);
		tableLayout.setLayoutParams(tableParams);

		int buttom;

		for (int i = 0; i < 9; i++) {

			TableRow row = new TableRow(this);
			row.setLayoutParams(tableParams);

			buttom = i == 2 || i == 5 ? 3 : 1;
			rowParams.weight = 1;
			row.setBackgroundColor(Color.BLACK);
			row.setLayoutParams(rowParams);

			createCell(tableLayout, rowParams, row, i, buttom);
			tableLayout.addView(row);
		}
	}

	private void createCell(TableLayout tableLayout, LayoutParams rowParams,
			TableRow tableRow, int i, int bottom) {
		int right;

		for (int j = 0; j < 9; j++) {

			right = j == 2 || j == 5 ? 3 : 1;

			EditText sudokuCell = new EditText(this);
			sudokuCell.setGravity(Gravity.CENTER);
			sudokuCell.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
			sudokuCell.setBackgroundColor(Color.WHITE);
			sudokuCell.setId(cellIds[i][j]);

			TableRow.LayoutParams cellalyout = new TableRow.LayoutParams(30, 30);

			cellalyout.weight = 1;
			cellalyout.setMargins(1, 1, right, bottom);
			sudokuCell.setPadding(0, 0, 0, 0);

			sudokuCell.setLayoutParams(cellalyout);
			tableRow.addView(sudokuCell);
		}
	}

	private void init() {
		cellIds[0][0] = R.id.table_cell_00;
		cellIds[0][1] = R.id.table_cell_01;
		cellIds[0][2] = R.id.table_cell_02;
		cellIds[0][3] = R.id.table_cell_03;
		cellIds[0][4] = R.id.table_cell_04;
		cellIds[0][5] = R.id.table_cell_05;
		cellIds[0][6] = R.id.table_cell_06;
		cellIds[0][7] = R.id.table_cell_07;
		cellIds[0][8] = R.id.table_cell_08;

		cellIds[1][0] = R.id.table_cell_10;
		cellIds[1][1] = R.id.table_cell_11;
		cellIds[1][2] = R.id.table_cell_12;
		cellIds[1][3] = R.id.table_cell_13;
		cellIds[1][4] = R.id.table_cell_14;
		cellIds[1][5] = R.id.table_cell_15;
		cellIds[1][6] = R.id.table_cell_16;
		cellIds[1][7] = R.id.table_cell_17;
		cellIds[1][8] = R.id.table_cell_18;

		cellIds[2][0] = R.id.table_cell_20;
		cellIds[2][1] = R.id.table_cell_21;
		cellIds[2][2] = R.id.table_cell_22;
		cellIds[2][3] = R.id.table_cell_23;
		cellIds[2][4] = R.id.table_cell_24;
		cellIds[2][5] = R.id.table_cell_25;
		cellIds[2][6] = R.id.table_cell_26;
		cellIds[2][7] = R.id.table_cell_27;
		cellIds[2][8] = R.id.table_cell_28;

		cellIds[3][0] = R.id.table_cell_30;
		cellIds[3][1] = R.id.table_cell_31;
		cellIds[3][2] = R.id.table_cell_32;
		cellIds[3][3] = R.id.table_cell_33;
		cellIds[3][4] = R.id.table_cell_34;
		cellIds[3][5] = R.id.table_cell_35;
		cellIds[3][6] = R.id.table_cell_36;
		cellIds[3][7] = R.id.table_cell_37;
		cellIds[3][8] = R.id.table_cell_38;

		cellIds[4][0] = R.id.table_cell_40;
		cellIds[4][1] = R.id.table_cell_41;
		cellIds[4][2] = R.id.table_cell_42;
		cellIds[4][3] = R.id.table_cell_43;
		cellIds[4][4] = R.id.table_cell_44;
		cellIds[4][5] = R.id.table_cell_45;
		cellIds[4][6] = R.id.table_cell_46;
		cellIds[4][7] = R.id.table_cell_47;
		cellIds[4][8] = R.id.table_cell_48;

		cellIds[5][0] = R.id.table_cell_50;
		cellIds[5][1] = R.id.table_cell_51;
		cellIds[5][2] = R.id.table_cell_52;
		cellIds[5][3] = R.id.table_cell_53;
		cellIds[5][4] = R.id.table_cell_54;
		cellIds[5][5] = R.id.table_cell_55;
		cellIds[5][6] = R.id.table_cell_56;
		cellIds[5][7] = R.id.table_cell_57;
		cellIds[5][8] = R.id.table_cell_58;

		cellIds[6][0] = R.id.table_cell_60;
		cellIds[6][1] = R.id.table_cell_61;
		cellIds[6][2] = R.id.table_cell_62;
		cellIds[6][3] = R.id.table_cell_63;
		cellIds[6][4] = R.id.table_cell_64;
		cellIds[6][5] = R.id.table_cell_65;
		cellIds[6][6] = R.id.table_cell_66;
		cellIds[6][7] = R.id.table_cell_67;
		cellIds[6][8] = R.id.table_cell_68;

		cellIds[7][0] = R.id.table_cell_70;
		cellIds[7][1] = R.id.table_cell_71;
		cellIds[7][2] = R.id.table_cell_72;
		cellIds[7][3] = R.id.table_cell_73;
		cellIds[7][4] = R.id.table_cell_74;
		cellIds[7][5] = R.id.table_cell_75;
		cellIds[7][6] = R.id.table_cell_76;
		cellIds[7][7] = R.id.table_cell_77;
		cellIds[7][8] = R.id.table_cell_78;

		cellIds[8][0] = R.id.table_cell_80;
		cellIds[8][1] = R.id.table_cell_81;
		cellIds[8][2] = R.id.table_cell_82;
		cellIds[8][3] = R.id.table_cell_83;
		cellIds[8][4] = R.id.table_cell_84;
		cellIds[8][5] = R.id.table_cell_85;
		cellIds[8][6] = R.id.table_cell_86;
		cellIds[8][7] = R.id.table_cell_87;
		cellIds[8][8] = R.id.table_cell_88;

	}

	private void fillSolvedNumber(Sudoku sudoku) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudoku.getField(i, j).isStartGap()) {
					EditText cell = (EditText) findViewById(cellIds[i][j]);
					cell.setText(String.valueOf(sudoku.getField(i, j)
							.getNumber()));
					cell.setBackgroundColor(lightGreen);
				}
			}
		}
	}

	private void fillSolvedCell(SudokuField sudokuField) {
		if(sudokuField==null){
			return;
		}
		int row = sudokuField.getRow();
		int column = sudokuField.getColumn();
		
		EditText cell = (EditText) findViewById(cellIds[row][column]);
		cell.setText(String.valueOf(sudokuField.getNumber()));
		cell.setBackgroundColor(lightGreen);
	}

	private void fillSudokuTable(int[][] sudoku) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudoku[i][j] != 0) {
					EditText cell = (EditText) findViewById(cellIds[i][j]);
					cell.setText(String.valueOf(sudoku[i][j]));
				}
			}
		}
	}

}
