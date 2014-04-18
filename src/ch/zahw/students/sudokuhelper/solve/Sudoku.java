package ch.zahw.students.sudokuhelper.solve;

public class Sudoku {

	private SudokuField[][] fields;

	public Sudoku() {
		fields = new SudokuField[9][9];
	}

	public Sudoku(int[][] notSolvedSudoku) {
		fill(notSolvedSudoku);
	}

	private void fill(int[][] notSolvedSudoku) {
		fields = new SudokuField[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				
				fields[i][j] = new SudokuField();
				
				if (notSolvedSudoku[i][j] != 0) {
					//TODO eventuell mögliche Zahlen aus Sudoku field löschen
					fields[i][j].setNumber(notSolvedSudoku[i][j]);
					fields[i][j].setFounded(true);
				}else{
					fields[i][j].setStartGap(true);
				}
			}

		}
	}
	
	public SudokuField getField(int row, int column){
		return fields[row][column];
	}
}
