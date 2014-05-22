package ch.zahw.students.sudokuhelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Dialog;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuField;
import ch.zahw.students.sudokuhelper.solve.SudokuFieldView;
import ch.zahw.students.sudokuhelper.solve.SudokuManager;

public class MainHelper {

	private static final String TAG = "SudokuHelper::MainHelper";
	private static final int LIGHT_GREEN = Color.rgb(186, 243, 183);
	private static final Pattern CANDIDATE_PATTERN = Pattern
			.compile("^(\\d\\d?),(\\d\\d?):(\\d)-(\\d)$");
	private final Dialog numberDialog;
	
	private int[][] cellIds = new int[9][9];
	private SudokuManager sudokuManager;
	private MainActivity mainActivity;

	public MainHelper(MainActivity mainActivity, Sudoku sudoku) {
		Log.v(TAG, "Creating instance MainHelper");
		this.mainActivity = mainActivity;
		this.sudokuManager = new SudokuManager(sudoku);
		numberDialog = new Dialog(mainActivity);
		init();
	}

	public MainHelper(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
		this.sudokuManager = new SudokuManager();
		numberDialog = new Dialog(mainActivity);
		init();
	}

	public Sudoku getSudoku() {
		return sudokuManager.getSudoku();
	}


	/**
	 * Re-render the whole Sudoku
	 */
	public void updateSudokuGui() {
		for (SudokuField field : sudokuManager.getSudokuFields()) {
			// field.getView().setText(field.getNumberAsString());
		}
	}

	public boolean isSudokuSolved() {
		return getSudoku().isSolved();
	}

	// ****************************** Init ******************************
	public void init() {
		Log.v(TAG, "Init");
		initCellIds();
	}

	private void initCellIds() {
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

	// *********************** Create Table****************************

	/**
	 * Creates the empty Sudoku GUI user interface.
	 * 
	 * @param sudokuTableLayout
	 *            - The view for the table layout
	 */
	public void createTable(View sudokuTableLayout) {

		Log.v(TAG, "Creating table");
		TableLayout tLayout = (TableLayout) sudokuTableLayout;

		tLayout.setClickable(false);
		createRows(tLayout);

		// for (int row = 0; row < 9; row++) {
		// for (int column = 0; column < 9; column++) {
		// TextView cell = (TextView) mainActivity
		// .findViewById(cellIds[row][column]);
		// cell.setMinHeight(cell.getWidth());
		// }
		// }

	}

	private void createRows(TableLayout tableLayout) {
		TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.WRAP_CONTENT,
				TableLayout.LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
				TableRow.LayoutParams.MATCH_PARENT,
				TableRow.LayoutParams.MATCH_PARENT);
		tableLayout.setLayoutParams(tableParams);

		int borderBottom;

		for (int i = 0; i < 9; i++) {

			TableRow row = new TableRow(mainActivity);
			row.setLayoutParams(tableParams);

			borderBottom = i == 2 || i == 5 ? 3 : 1;
			rowParams.weight = 1;
			row.setBackgroundColor(Color.BLACK);
			row.setLayoutParams(rowParams);

			createCell(tableLayout, rowParams, row, i, borderBottom);
			tableLayout.addView(row);
		}
	}

	private void createCell(TableLayout tableLayout, LayoutParams rowParams,
			TableRow tableRow, int i, int bottom) {
		int right;

		for (int j = 0; j < 9; j++) {

			right = j == 2 || j == 5 ? 3 : 1;

			SudokuFieldView sudokuCell = new SudokuFieldView(mainActivity, i, j);
			sudokuCell.setGravity(Gravity.CENTER);
			sudokuCell.setBackgroundColor(Color.WHITE);
			sudokuCell.setId(cellIds[i][j]);

			TableRow.LayoutParams celllayout = new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.MATCH_PARENT);

			celllayout.weight = 1;
			celllayout.setMargins(1, 1, right, bottom);
			sudokuCell.setPadding(0, 0, 0, 0);

			sudokuCell.setLayoutParams(celllayout);

			tableRow.addView(sudokuCell);
			sudokuCell.setOnClickListener(mCellListener);
			// bind the listener
			sudokuManager.getSudokuField(i, j).addObserver(sudokuCell);
		}
	}

	// ****************************** Functions ******************************
	public void step(View view) {
		Log.v(TAG, "Solve Step");
		fillSolvedCell();
	}

	public void solve(View view) {
		Log.v(TAG, "Solve");

		// nur einmal das Sudoku zu lösen
		if (!isSudokuSolved()) {
			sudokuManager.solve();
		}
		
		// Das Sudoku kann von der Step Funktion schon gelöst worden sein.
		// Deswegen wird das Sudoku die gelösten Felder angezeigt
		fillSolvedNumber(getSudoku());
	}

	// ****************************** Change table******************************
	private void fillSolvedCell() {
		SudokuField nextField = sudokuManager.getNextSolveOrder();
		SudokuField prevSf = sudokuManager.getPreviousSolveOrder();

		if (nextField != null) {
			changeCell(nextField.getRow(), nextField.getColumn(),
					nextField.getNumber(), Color.GREEN);
		}

		if (prevSf != null) {
			changeCell(prevSf.getRow(), prevSf.getColumn(), -1, LIGHT_GREEN);
		}

	}

	private void fillSolvedNumber(Sudoku sudoku) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudoku.getField(i, j).isStartGap()) {
					changeCell(i, j, sudoku.getField(i, j).getNumber(),
							LIGHT_GREEN);
				}
			}
		}
	}

	// private void fillSudokuTable(int[][] sudoku) {
	// Log.v(TAG, "Filling Sudoku table");
	//
	// for (int i = 0; i < 9; i++) {
	// for (int j = 0; j < 9; j++) {
	// changeCell(i, j, sudoku[i][j], Color.WHITE);
	// }
	// }
	//
	// }

	private void changeCell(int row, int column, int number, int color) {
		SudokuFieldView cell = (SudokuFieldView) mainActivity
				.findViewById(cellIds[row][column]);
		// cell.setMinHeight(cell.getWidth());
		cell.setBackgroundColor(color);
	}

	/**
	 * Extracts the candidates values from the String returned from the capture
	 * activity
	 */
	public void parseResult(String candidateString) {
		int[][] primaryValues = new int[9][9];
		int[][] secondaryValues = new int[9][9];
		String[] fields = candidateString.split(";");
		for (int i = 0; i < fields.length; i++) {
			Matcher matcher = CANDIDATE_PATTERN.matcher(fields[i]);
			if (matcher.matches()) {
				// 1: row, 2: column, 3: primary value, 4: secondary value
				int row = Integer.parseInt(matcher.group(1));
				int column = Integer.parseInt(matcher.group(2));
				int primary = Integer.parseInt(matcher.group(3));
				int secondary = Integer.parseInt(matcher.group(4));
				primaryValues[row][column] = primary;
				if (secondary > 0) {
					secondaryValues[row][column] = secondary;
				}
			} else {
				Log.e(TAG, "No match for: " + fields[i]);
			}
		}
		sudokuManager.resetSudoku(primaryValues);
		updateSudokuGui();
	}

	// ****************************** Edit numbers
	// ******************************

	// Creates an anonymous implementation of OnClickListener
	private OnClickListener mCellListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Log.v(TAG, "Click event received from view " + view);
			showNumberPicker((SudokuFieldView) view,
					((SudokuFieldView) view).getValue());
		}
	};

	// show the number picker to edit the field value.
	private void showNumberPicker(SudokuFieldView fieldView, int value) {
		numberDialog.setTitle(R.string.numberPicker);
		numberDialog.setContentView(R.layout.number_picker);
		Button bOk = (Button) numberDialog.findViewById(R.id.button_ok);
		Button bClear = (Button) numberDialog.findViewById(R.id.button_clear);
		final NumberPicker np = (NumberPicker) numberDialog
				.findViewById(R.id.numberPicker1);
		np.setMaxValue(9);
		np.setMinValue(1);
		np.setWrapSelectorWheel(true);
		// set the number to the current value
		if (value > 0) {
			np.setValue(value);
		}
		// send the selected value to the Sudoku Manager.
		bOk.setOnClickListener(new PickerListener(fieldView) {
			@Override
			public void onClick(View v) {
				sudokuManager.manuallyOverrideValue(getRow(), getColumn(), np.getValue());
				numberDialog.dismiss();
			}
		});
		bClear.setOnClickListener(new PickerListener(fieldView) {
			@Override
			public void onClick(View v) {
				sudokuManager.manuallyOverrideValue(getRow(), getColumn(), 0);
				numberDialog.dismiss();
			}
		});
		numberDialog.show();
	}

	// the base class for the callback. Can store the position of the original
	// field.
	abstract class PickerListener implements OnClickListener {
		private int row;
		private int column;

		public PickerListener(SudokuFieldView fieldView) {
			row = fieldView.getRow();
			column = fieldView.getColumn();
		}

		protected int getRow() {
			return row;
		}

		protected int getColumn() {
			return column;
		}

	}

}