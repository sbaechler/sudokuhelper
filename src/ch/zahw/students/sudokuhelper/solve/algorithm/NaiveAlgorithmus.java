package ch.zahw.students.sudokuhelper.solve.algorithm;

import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuField;

public class NaiveAlgorithmus extends AAlgorithm {

	public NaiveAlgorithmus(int[][] sudoku) {
		super(sudoku);
	}

	@Override
	public Sudoku solve() {
		return solveWithNaiveApproach();
	}

	public Sudoku solveWithNaiveApproach() {
		init();
		SudokuField sField;
		int number;

		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				sField = sudoku.getField(row, column);
				for (int k = 0; k < sField.getSizeOfAvailableNumbers(); k++) {

					number = sField.getAvailableNumbers().get(k);

					if (sField.isFounded() == false
							&& checkIfNumberShouldBeRemoved(row, column, number)) {
						int index = sField.getAvailableNumbers()
								.indexOf(number);
						sField.getAvailableNumbers().remove(index);
					}
				}
			}
		}


		while (!checkIfSolvedAdvaced()) {
			naiveApproach(0, 0);
		}

		return sudoku;
	}

	private boolean naiveApproach(int row, int column) {

		SudokuField sf = sudoku.getField(row, column);
		int number = 0;

		for (int i = 0; i < sf.getAvailableNumbers().size(); i++) {
			if (!sf.isFounded()) {
				number = sf.getAvailableNumbers().get(i);
				sf.setNumber(number);
			}

			if (row == 8 && column == 8) {
				if (checkIfSolvedAdvaced()) {
					return true;
				}
			} else {
				if (column == 8) {
					column = -1;
					row++;
				}

				if (naiveApproach(row, column + 1)) {
					return true;
				}
			}

			// if (column == 8) {
			// if (row == 8) {
			// if (checkIfSolvedAdvaced()) {
			// return true;
			// }
			// } else {
			// if (naiveApproach(row + 1, column - 8)) {
			// return true;
			// }
			// }
			// } else {
			// if (naiveApproach(row, column + 1)) {
			// return true;
			// }
			// }
		}

		return false;
	}

}
