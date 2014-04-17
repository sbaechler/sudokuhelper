package ch.zahw.students.sudokuhelper.solve;

public class SudokuManager {

	private Sudoku sudoku;

	public Sudoku solveWithNaivlyApproach(Sudoku sudoku) {
		// eine zahl möglich -> sofort ausprobieren (rekursiv)

		boolean solved = false;

		while (!solved) {

		}

		return sudoku;
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
				int index = sudoku.getField(row, i).getAvailableNumbers().indexOf(number);

				if (index != -1) {
					sudoku.getField(row, i).getAvailableNumbers().remove(index);
				}
			}
		}

		// Zahl in der gleichen Spalte entfernen
		for (int i = 0; i < 9; i++) {
			if (!sudoku.getField(i, column).isFounded()) {
				int index = sudoku.getField(i, column).getAvailableNumbers().indexOf(number);

				if (index != -1) {
					sudoku.getField(i, column).getAvailableNumbers().remove(index);
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
					int index = sudoku.getField(i, j).getAvailableNumbers().indexOf(number);

					if (index != -1) {
						sudoku.getField(i, j).getAvailableNumbers().remove(index);
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

							if (checkIfNumberShouldBeRemoved(row, column, number)) {
								int index = sField.getAvailableNumbers().indexOf(number);
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

	private void checkIsFounded(SudokuField sField, int row, int column) {

		if (sField.getSizeOfAvailableNumbers() == 1) {
			int number = sField.getAvailableNumbers().get(0);
			sField.setNumber(sField.getAvailableNumbers().get(0));
			sField.setFounded(true);
			removeNotAvailablenUmber(row, column, number);
		}
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

	public int[][] getSudokuAsArray(Sudoku toArray) {
		int[][] arrSud = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				arrSud[i][j] = toArray.getField(i, j).getNumber();
			}
		}
		return arrSud;
	}

}
