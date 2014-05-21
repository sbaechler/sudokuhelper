package ch.zahw.students.sudokuhelper.solve.algorithm;

import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuField;

// Dieser Zustand wird vom Sudoku selbst erkannt.
@Deprecated
public class HiddenSingleAlgorithm extends AAlgorithm {

	public HiddenSingleAlgorithm(int [][] sudoku) {
		super(sudoku);
	}

	@Override
	public Sudoku solve() {
		SudokuField sf;

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
					for (int number: sf.getAvailableNumbers()) {

						// rowcheck
						if (findHiddenSingle(sudoku.getRowSudokuFields(row),
								number, sf)) {

							sudoku.setValue(row, column, number);
							sf.setFounded(true);

							// von vorne anfangen
							continue start;
						}

						// columncheck
						if (findHiddenSingle(
								sudoku.getColumnSudokuFields(column), number,
								sf)) {
                                                    sudoku.setValue(row, column, number);
                                                    sf.setFounded(true);
                                                    
							// von vorne anfangen
							continue start;
						}

						// quadratcheck
						if (findHiddenSingle(sudoku.getSudokuSquare(row),
								number, sf)) {

                                                        sudoku.setValue(row, column, number);
                                                        sf.setFounded(true);
                                                        
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
			SudokuField field) {
		SudokuField sField;

		for (int i = 0; i < nineFields.length; i++) {
		    sField = nineFields[i];
		    
		    if (sField == field) {
				continue;
			}

			

			// Falls ein anderes Sudokufeld die gleiche Zahl als mögliche Zahl
			// hat, wird false zurückgegeben da es kein hidden single ist
			if (sField.getAvailableNumbers().contains(number)) {
				return false;

			}
		}

		return true;
	}

}
