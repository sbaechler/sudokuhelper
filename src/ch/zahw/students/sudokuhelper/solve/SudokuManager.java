package ch.zahw.students.sudokuhelper.solve;

import java.util.Vector;

import ch.zahw.students.sudokuhelper.solve.algorithm.AAlgorithm;
import ch.zahw.students.sudokuhelper.solve.algorithm.NaiveAlgorithmus;
import ch.zahw.students.sudokuhelper.solve.algorithm.SimpleAlgorithm;


public class SudokuManager {

	private Sudoku sudoku;
	private AAlgorithm aaAlgorithm;
	private Vector<SudokuField> solveOrder;
	private int indexNextSolveOrder;

	// creates a new, empty Sudoku
	public SudokuManager(){
	    this.sudoku = new Sudoku();
	}
	// creates a Sudoku from a given list of candidates
	public SudokuManager(int[][] sudokuArray){
	    this.sudoku = new Sudoku(sudokuArray);
	}
	// Uses an existing Sudoku (i.e. from saved state)
	public SudokuManager(Sudoku sudoku){
	    this.sudoku = sudoku;
	}
	
	/**
        * Sets the values for an existing Sudoku.
        * @param candidates - 2-dimensional array of numbers.
        */
	public void resetSudoku(int[][] candidates){
	    sudoku.setValues(candidates);
	}
	
	public Sudoku solveWithNaiveApproach() {
		// eine zahl möglich -> sofort ausprobieren (rekursiv)
		aaAlgorithm = new NaiveAlgorithmus(sudoku);
		Sudoku solvedSudoku = aaAlgorithm.solve();
		this.solveOrder = aaAlgorithm.getSolveOrder();
		this.indexNextSolveOrder = -1;

		return solvedSudoku;
	}

	public Sudoku solveWithBetterApproach() {
		aaAlgorithm = new SimpleAlgorithm(sudoku);
		Sudoku solvedSudoku = aaAlgorithm.solve();
		this.solveOrder = aaAlgorithm.getSolveOrder();
		this.indexNextSolveOrder = -1;
		
		return solvedSudoku;
	}
	
	public int[][] getArrSud(int[][] arrSud) {
		return arrSud;
	}

	public Sudoku getSudoku() {
		return sudoku;
	}
	
	public SudokuField[] getSudokuFields(){
	    return sudoku.getFields();
	}

	public Vector<SudokuField> getSolveOrder() {
		return aaAlgorithm.getSolveOrder();
	}

	public SudokuField getNextSolveOrder() {
		indexNextSolveOrder++;

		if (indexNextSolveOrder >= solveOrder.size()) {
			return null;
		}

		return solveOrder.get(indexNextSolveOrder);
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
	
	@Deprecated
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

	
	// ***************************************** TODO wieder löschen
	public int[][] createSudokuSimply() {
		int[][] toFillSudoku = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// first: row, second: column
				toFillSudoku[i][j] = 0;
			}

		}

		toFillSudoku[0][0] = 8;
		toFillSudoku[0][1] = 1;
		toFillSudoku[0][2] = 2;
		toFillSudoku[0][7] = 9;

		toFillSudoku[1][0] = 5;
		toFillSudoku[1][2] = 3;
		toFillSudoku[1][4] = 2;
		toFillSudoku[1][6] = 1;

		toFillSudoku[2][3] = 1;
		toFillSudoku[2][4] = 5;
		toFillSudoku[2][8] = 6;

		toFillSudoku[3][3] = 2;
		toFillSudoku[3][4] = 4;
		toFillSudoku[3][7] = 1;
		toFillSudoku[3][8] = 5;

		toFillSudoku[4][2] = 7;
		toFillSudoku[4][3] = 5;
		toFillSudoku[4][7] = 2;

		toFillSudoku[5][1] = 8;
		toFillSudoku[5][3] = 3;
		toFillSudoku[5][7] = 4;

		toFillSudoku[6][0] = 9;
		toFillSudoku[6][5] = 4;

		toFillSudoku[7][2] = 1;
		toFillSudoku[7][6] = 7;
		toFillSudoku[7][8] = 9;

		toFillSudoku[8][0] = 3;
		toFillSudoku[8][1] = 5;
		toFillSudoku[8][2] = 8;

		return toFillSudoku;
	}

}
