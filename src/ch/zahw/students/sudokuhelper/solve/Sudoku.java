package ch.zahw.students.sudokuhelper.solve;

import java.util.Vector;

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
				
				fields[i][j] = new SudokuField(i, j);
				
				if (notSolvedSudoku[i][j] != 0) {
					//TODO eventuell mögliche Zahlen aus Sudoku field löschen
					fields[i][j].clearAvailableNumbers();
					fields[i][j].setNumber(notSolvedSudoku[i][j]);
					fields[i][j].setFounded(true);
				}else{
					fields[i][j].setStartGap(true);
				}
			}

		}
	}
	
	public SudokuField[]  getColunSudokuFields(int column){
		SudokuField[] sf = new SudokuField[9];
		
		for (int i = 0; i < 9; i++) {
			sf[i]= 	fields[i][column];
		}
		
		return sf;
	}

	
	public SudokuField[] getRowSudokuFields(int row){
		SudokuField[] sf = new SudokuField[9];
		
		for (int i = 0; i < 9; i++) {
			sf[i]= 	fields[row][i];
		}
		return sf;
	}

	/**
	 * 
	 * @param index geht von 0 bis 8
	 * @return
	 */
	public SudokuField[] getSudokuQadrat(int index){
		SudokuField[] sf = new SudokuField[9];
		int sfIndex=0;

		int startRow = (index / 3) * 3;
		int startColumn= (index % 3) * 3;
		
		
		for (int row = startRow; row < startRow+3; row++) {
			for (int column = startColumn; column < startColumn+3; column++) {
				sf[sfIndex] = fields[row][column];
				sfIndex++;
			}
		}
		
		return sf;
	}
	
	public SudokuField getField(int row, int column){
		return fields[row][column];
	}
}
