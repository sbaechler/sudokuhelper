package ch.zahw.students.sudokuhelper.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import ch.zahw.students.sudokuhelper.solve.Sudoku;

public class SudokuParser {

	private Sudoku toSolve;
	private Sudoku solutionSudoku;

	public Sudoku read(File file) {
		System.out.println(file);
		if (!file.exists() || !file.isFile()) {
			return null;
		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			toSolve = createSudoku(reader);

			if (reader.readLine().equals("**********************")) {
				// keine lösung vorhanden
				return null;
			}

			solutionSudoku = createSudoku(reader);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return toSolve;
	}

	private Sudoku createSudoku(BufferedReader reader) throws IOException {
		Sudoku sudoku = new Sudoku();
		String[] canditates;
		String line = reader.readLine();

		for (int row = 0; row < 9; row++) {
			line = reader.readLine();
			canditates = line.split(",");

			for (int column = 0; column < 9; column++) {
				sudoku.setValue(row, column,
						Integer.parseInt(canditates[column]));
			}

		}

		return sudoku;
	}

	public void print(Sudoku solvedSudoku) {
		printSudoku(solvedSudoku);
		printSudoku(solutionSudoku);
	}

	private void printSudoku(Sudoku sudoku) {
		System.out.print("-----------------------------------");
		for (int row = 0; row < 9; row++) {

			for (int column = 0; column < 9; column++) {
				System.out.print(solutionSudoku.getField(row, column));
			}

		}
		System.out.print("-----------------------------------");
	}

	public Sudoku getSolutionSudoku() {
		return solutionSudoku;
	}

	public Sudoku getToSolve() {
		return toSolve;
	}

	public Sudoku parseString(String sudokuString) {

		String[] rows = sudokuString.split("\n");
		String row = null;
		int[][] sudokuint = new int[9][9];

		String value = null;

		for (int i = 0; i < 9; i++) {

			row = rows[i];
			row = row.trim();

			for (int j = 0; j < 9; j++) {
				sudokuint[i][j] = Integer.parseInt(value);
			}

		}

		return new Sudoku(sudokuint);
	}
}
