package ch.zahw.students.sudokuhelper.solve.algorithm;

import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuField;

public class SimpleAlgorithm extends AAlgorithm {

	public SimpleAlgorithm(int[][]  sudoku) {
		super(sudoku);
	}

	@Override
	public Sudoku solve() {

		// mögliche Zahlen temporär speichern (falls eine zutrifft-> sofort das
		// Feld auffüllen und die Zahl aus der Zeile, Spalte und Quadrat
		// löschen)
		SudokuField sField;
		int number;

		while (!checkSimplySolved()) {
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

}
