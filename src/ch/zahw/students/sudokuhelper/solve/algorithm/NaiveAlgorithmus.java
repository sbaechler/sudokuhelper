package ch.zahw.students.sudokuhelper.solve.algorithm;

import ch.zahw.students.sudokuhelper.solve.Sudoku;
import ch.zahw.students.sudokuhelper.solve.SudokuField;


public class NaiveAlgorithmus extends AAlgorithm{
	
	public NaiveAlgorithmus(int[][]  sudoku) {
		super(sudoku);
	}


	@Override
	public Sudoku solve() {
		init();
		
		while (!checkIfSolved()) {
			naiveApproach(0, 0);
		}

		return sudoku;
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
	
}
