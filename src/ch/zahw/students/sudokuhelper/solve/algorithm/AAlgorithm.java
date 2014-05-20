package ch.zahw.students.sudokuhelper.solve.algorithm;

import java.util.Vector;

import android.util.Log;

import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuField;

public abstract class AAlgorithm {

	protected Sudoku sudoku;
	private boolean[] rowCheck;
	private boolean[] colCheck;
	private boolean[] squareCheck;
	private Vector<SudokuField> solveOrder;

	public AAlgorithm(Sudoku sudoku) {
		this.sudoku = sudoku;
		init();
	}

	public abstract Sudoku solve();

	protected void init() {
		this.rowCheck = new boolean[9];
		this.colCheck = new boolean[9];
		this.squareCheck = new boolean[9];
		this.solveOrder = new Vector<SudokuField>();
	}


	@Deprecated
	protected boolean checkIfNumberShouldBeRemoved(int row, int column,
			int number) {
		// Wird überprüft ob die Zahl entfernt werden soll falls die Zahl schon
		// in der gleiche Zeile vorkommt
		for (int i = 0; i < 9; i++) {
			if (sudoku.getNumber(row, i) == number) {
				return true;
			}
		}

		// Wird überprüft ob die Zahl entfernt werden soll falls die Zahl schon
		// in der gleiche Spalte vorkommt
		for (int i = 0; i < 9; i++) {
			if (sudoku.getField(i, column).getNumber() == number) {
				return true;
			}
		}

		// Wird überprüft ob die Zahl entfernt werden soll falls die Zahl schon
		// im Quadrat vorkommt
		// Quadrat: es gibt 3 * 3 Quadrate
		int qy = (row / 3) * 3;
		int qx = (column / 3) * 3;

		for (int i = qy; i < qy + 3; i++) {
			for (int j = qx; j < qx + 3; j++) {
				if (sudoku.getField(i, j).getNumber() == number) {
					return true;
				}
			}
		}

		return false;
	}

//	protected void checkIsFounded(SudokuField sField, int row, int column) {
//		if (sField.getSizeOfAvailableNumbers() == 1) {
//			int number = sField.getAvailableNumbers().get(0);
//			sField.setNumber(sField.getAvailableNumbers().get(0));
//			sField.setFounded(true);
//			solveOrder.add(sField);
//			removeNotAvailablenUmber(row, column, number);
//		}
//	}

	protected boolean rowAndColcheck() {
		int numberRow = 0;
		int numberCol = 0;

		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 9; i++) {

				numberRow = sudoku.getField(j, i).getNumber()-1;
				numberCol = sudoku.getField(i, j).getNumber()-1;

				if (numberRow == 0 || numberCol == 0) {
					return false;
				}

				if (rowCheck[numberRow] == true || colCheck[numberCol] == true) {
					return false;
				} else {
					rowCheck[numberRow] = true;
					colCheck[numberCol] = true;
				}
			}
			this.rowCheck = new boolean[9];
			this.colCheck = new boolean[9];
		}

		return true;
	}

	public boolean checkIfSolvedAdvaced() {

		// Überprüft ob die Zahlen 1-9 in jeder Spalte und Zeile richtig gesetzt
		// wurde
		if (!rowAndColcheck()) {
			return false;
		}

		int number = 0;
		int row = 0;
		int col = 0;

		while (row < 8) {

			for (int i = row; i < row + 3; i++) {
				for (int j = col; j < col + 3; j++) {
					number = sudoku.getField(row, col).getNumber();

					if (number == 0) {
						return false;
					}

					if (squareCheck[number] == true) {
						return false;
					} else {
						squareCheck[number] = true;
					}
				}

				this.squareCheck = new boolean[9];
			}

			row += 3;
			col = 0;
		}

		return true;
	}

	protected boolean checkSimplySolved() {
		// Evlt eine bessere Überprüfung schreiben (oder nur für bereits
		// angefangene Sudokus) überprüfen ob wirklich 1-9 einmal vorkommen

		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				if (sudoku.getField(row, column).getNumber() == 0) {
					return false;
				}
			}
		}

		return true;
	}
	
	public Vector<SudokuField> getSolveOrder() {
		return solveOrder;
	}
}
