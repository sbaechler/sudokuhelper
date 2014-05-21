package ch.zahw.students.sudokuhelper.solve.algorithm;

import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuField;

public class HiddenSingleAlgorithmus extends AAlgorithm {

	public HiddenSingleAlgorithmus(int [][] sudoku) {
		super(sudoku);
	}

	@Override
	public Sudoku solve() {
		SudokuField sf;
		int number;

		start:

		// über zeilen iterieren
		for (int row = 0; row < 9; row++) {

			// über spalten iterieren
			for (int column = 0; column < 9; column++) {
				// sudokuField
				sf = sudoku.getField(row, column);

				// es werden nur sudoku Felder genommen die keine gesetzte,
				// definitive Zahl haben
				if (sf.isFounded() == false) {

					// über alle mögliche Zahlen iterieren
					for (int i = 0; i < sf.getAvailableNumbers().size(); i++) {
						number = sf.getAvailableNumbers().get(i);

						// rowcheck
						if (findHiddenSingle(sudoku.getRowSudokuFields(row),
								number, column)) {

							removeNotAvailablenUmber(row, column, number);

							// von vorne anfangen
							continue start;
						}

						// columncheck
						if (findHiddenSingle(
								sudoku.getColumnSudokuFields(column), number,
								row)) {
							removeNotAvailablenUmber(row, column, number);

							// von vorne anfangen
							continue start;
						}

						// quadratcheck
						if (findHiddenSingle(sudoku.getSudokuSquare(row),
								number, column)) {

							// Sudokufield im quadrat
							int cellRow = (row / 3) * 3;
							int cellColumn = (column) * 3;

							removeNotAvailablenUmber(cellRow, cellColumn,
									number);
							// von vorne anfangen
							continue start;
						}
					}
				}

			}

		}

		return sudoku;
	}

	/**
	 * 
	 * @param nineFields
	 *            die zu untersuchende SudokuFelder
	 * @param number
	 *            Diese Zahl wird überprüft ob es ein hidden single ist
	 * @param index
	 *            Der Index welche die untersuchende Zahl besitzt
	 * @return true -> hidden single, false -> kein hidden single
	 */
	private boolean findHiddenSingle(SudokuField[] nineFields, int number,
			int index) {
		SudokuField sField;

		for (int i = 0; i < nineFields.length; i++) {
			if (i == index) {
				continue;
			}

			sField = nineFields[i];

			// Falls ein anderes Sudokufeld die gleiche Zahl als mögliche Zahl
			// hat, wird false zurückgegeben da es kein hidden single ist
			if (sField.getAvailableNumbers().contains(number)) {
				return false;

			}
		}

		return true;
	}

}
