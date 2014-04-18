package ch.zahw.students.sudokuhelper.solve;

import java.util.Vector;

public class SudokuManager {

	private Sudoku sudoku;
	private Vector<Boolean> check;
	private Vector<SudokuField> solveOrder;
	//TODO use command Pattern
	private int indexNextSolveOrder;

	public SudokuManager() {
		init();
	}

	public Sudoku solveWithNaiveApproach(Sudoku sudoku) {
		// eine zahl möglich -> sofort ausprobieren (rekursiv)
		this.sudoku = sudoku;

		init();
		
		while (!checkIfSolved()) {
			naiveApproach(0, 0);
		}

		return sudoku;
	}

	private void init() {
		this.check = new Vector<Boolean>();
		this.solveOrder = new Vector<SudokuField>();
		this.indexNextSolveOrder = -1;
	}

	private boolean naiveApproach(int row, int column) {

		SudokuField sf = sudoku.getField(row, column);
		int number;

		if (!sf.isFounded()) {
			for (int i = 0; i < sf.getAvailableNumbers().size(); i++) {
				number = sf.getAvailableNumbers().get(i);
				if (checkIfNumberShouldBeRemoved(row, column, number)) {
					// int index = sf.getAvailableNumbers().indexOf(number);
					// sf.getAvailableNumbers().remove(index);
				} else {

					sf.setNumber(number);

					if (row == 8 && column == 8) {

						return false;
					} else {

						row = row == 7 ? 0 : row++;
						column = column == 8 ? 0 : column++;
						if (naiveApproach(row, column)) {
							sf.setFounded(true);
							return true;
						}
					}
				}
			}

		}

		return false;
	}

	/**
	 * Hier wird die gefundene Zahl aus der Zeile, Spalte und im Quadrat
	 * entfernt sodass die betreffende Felder diese Zahl nicht mehr zur
	 * Verfügung haben
	 */
	public void removeNotAvailablenUmber(int row, int column, int number) {

		// Zahl in der gleichen Zeile entfernen
		for (int i = 0; i < 9; i++) {
			if (!sudoku.getField(row, i).isFounded()) {
				int index = sudoku.getField(row, i).getAvailableNumbers()
						.indexOf(number);

				if (index != -1) {
					sudoku.getField(row, i).getAvailableNumbers().remove(index);
				}
			}
		}

		// Zahl in der gleichen Spalte entfernen
		for (int i = 0; i < 9; i++) {
			if (!sudoku.getField(i, column).isFounded()) {
				int index = sudoku.getField(i, column).getAvailableNumbers()
						.indexOf(number);

				if (index != -1) {
					sudoku.getField(i, column).getAvailableNumbers()
							.remove(index);
				}
			}
		}

		// Zahl im Quadrat entfernen
		// Quadrat: es gibt 3 * 3 Quadrate
		int qy = (row / 3) * 3;
		int qx = (column / 3) * 3;

		for (int i = qy; i < qy + 3; i++) {
			for (int j = qx; j < qx + 3; j++) {
				if (!sudoku.getField(i, j).isFounded()) {
					int index = sudoku.getField(i, j).getAvailableNumbers()
							.indexOf(number);

					if (index != -1) {
						sudoku.getField(i, j).getAvailableNumbers()
								.remove(index);
					}
				}
			}
		}

	}

	private boolean checkIfNumberShouldBeRemoved(int row, int column, int number) {
		// Wird überprüft ob die Zahl entfernt werden soll falls die Zahl schon
		// in der gleiche Zeile vorkommt
		for (int i = 0; i < 9; i++) {
			if (sudoku.getField(row, i).getNumber() == number) {
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

	public Sudoku solveWithBetterApproach(int[][] sudokuArray) {
		Sudoku sudoku = new Sudoku(sudokuArray);

		return solveWithBetterApproach(sudoku);
	}

	public Sudoku solveWithBetterApproach(Sudoku sudoku) {
		// mögliche Zahlen temporär speichern (falls eine zutrifft-> sofort das
		// Feld auffüllen und die Zahl aus der Zeile, Spalte und Quadrat
		// löschen)
		this.sudoku = sudoku;
		SudokuField sField;
		int number;

		while (!checkIfSolved()) {
			for (int row = 0; row < 9; row++) {
				for (int column = 0; column < 9; column++) {

					sField = sudoku.getField(row, column);

					if (!sField.isFounded()) {
						for (int k = 0; k < sField.getSizeOfAvailableNumbers(); k++) {

							number = sField.getAvailableNumbers().get(k);

							if (checkIfNumberShouldBeRemoved(row, column,
									number)) {
								int index = sField.getAvailableNumbers()
										.indexOf(number);
								sField.getAvailableNumbers().remove(index);
								k--;
							}

							checkIsFounded(sField, row, column);
						}

					}

				}
			}

		}

		return sudoku;
	}

	public void print() {
		int[][] sud = getSudokuAsArray(sudoku);
		for (int i = 0; i < 9; i++) {
			System.out.print("\n-------------------\n|");
			for (int j = 0; j < 9; j++) {
				System.out.print(sud[i][j] + "|");
			}

		}
		System.out.println("\n-------------------");
	}

	private void checkIsFounded(SudokuField sField, int row, int column) {

		if (sField.getSizeOfAvailableNumbers() == 1) {
			int number = sField.getAvailableNumbers().get(0);
			sField.setNumber(sField.getAvailableNumbers().get(0));
			sField.setFounded(true);
			solveOrder.add(sField);
			removeNotAvailablenUmber(row, column, number);
		}
	}

	public void reset() {
		this.check = new Vector<Boolean>();
	}

	public boolean checkIfSolved2() {
		int number = 0;

		for (int row = 0; row < 9; row++) {
			for (int i = 0; i < 9; i++) {

				number = sudoku.getField(row, i).getNumber();

				if (number == 0) {
					return false;
				}

				if (check.get(number) == true) {
					return false;
				} else {
					check.set(number, true);
				}
			}
			reset();
		}

		for (int i = 0; i < 9; i++) {
			for (int column = 0; column < 9; column++) {
				number = sudoku.getField(i, column).getNumber();

				if (number == 0) {
					return false;
				}

				if (check.get(number) == true) {
					return false;
				} else {
					check.set(number, true);
				}
			}
			reset();
		}

		int row = 0;
		int col = 0;
		while (row < 8) {

			for (int i = row; i < row + 3; i++) {
				for (int j = col; j < col + 3; j++) {
					number = sudoku.getField(row, col).getNumber();

					if (number == 0) {
						return false;
					}

					if (check.get(number) == true) {
						return false;
					} else {
						check.set(number, true);
					}
				}
				reset();
			}

			row += 3;
			col = 0;
		}

		return true;
	}

	public boolean checkIfSolved() {
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

	public int[][] getArrSud(int[][] arrSud) {
		return arrSud;
	}

	public Sudoku getSudoku() {
		return sudoku;
	}

	public Vector<SudokuField> getSolveOrder() {
		return solveOrder;
	}

	public SudokuField getNextSolveOrder() {
		indexNextSolveOrder++;

		if (indexNextSolveOrder >= solveOrder.size()) {
			return null;
		}

		return solveOrder.get(indexNextSolveOrder);
	}

	public int[][] getSudokuAsArray(Sudoku toArray) {
		int[][] arrSud = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				arrSud[i][j] = toArray.getField(i, j).getNumber();
			}
		}
		return arrSud;
	}

	public SudokuField getPreviousSolveOrder() {
		
		int prev = indexNextSolveOrder-1;
	
		if(prev<0){
			return null;
		}
		
		return solveOrder.get(prev);
	}

}
