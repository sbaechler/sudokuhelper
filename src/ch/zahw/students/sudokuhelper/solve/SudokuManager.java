package ch.zahw.students.sudokuhelper.solve;

import java.util.Vector;

import ch.zahw.students.sudokuhelper.solve.algorithm.AAlgorithm;
import ch.zahw.students.sudokuhelper.solve.algorithm.HiddenSingleAlgorithm;
import ch.zahw.students.sudokuhelper.solve.algorithm.NaiveAlgorithmus;
import ch.zahw.students.sudokuhelper.solve.algorithm.SimpleAlgorithm;

public class SudokuManager {

	private Sudoku sudoku;

	private NaiveAlgorithmus naiveAlgorithm;
	private HiddenSingleAlgorithm hiddenSingle;
	private SimpleAlgorithm nakedSingle;

	private Vector<SudokuField> solveOrder;
	private int indexNextSolveOrder;
	private boolean isSolved = false;

	public Sudoku solve() {
		naiveAlgorithm = new NaiveAlgorithmus(sudoku);
		hiddenSingle = new HiddenSingleAlgorithm(sudoku);
		nakedSingle = new SimpleAlgorithm(sudoku);

		Sudoku solvedSudokuNakedSingle;
		Sudoku solvedSudokuHiddenSingle;

		while (true) {
			// solve with naked singles
			solvedSudokuNakedSingle = nakedSingle.solve();

			if (nakedSingle.isSolved()) {
				// GELÖST !
				isSolved = true;
				break;
			} else {
				// sovle with hidden singles
				solvedSudokuHiddenSingle = hiddenSingle.solve();

				if (!solvedSudokuNakedSingle.equals(solvedSudokuHiddenSingle)) {
					// wieder naked single
					continue;
				} else {
					// ja: naiv
					naiveAlgorithm.solve();
				}
				// nein: abruch
				isSolved = false;
				break;
			}
		}

		this.solveOrder = naiveAlgorithm.getSolveOrder();

		return hiddenSingle.getSudoku();
	}

	// creates a new, empty Sudoku
	public SudokuManager() {
		this.sudoku = new Sudoku();
	}

	// creates a Sudoku from a given list of candidates
	public SudokuManager(int[][] sudokuArray) {
		this.sudoku = new Sudoku(sudokuArray);
	}

	// Uses an existing Sudoku (i.e. from saved state)
	public SudokuManager(Sudoku sudoku) {
		this.sudoku = sudoku;
	}

	/**
	 * Sets the values for an existing Sudoku.
	 * 
	 * @param candidates
	 *            - 2-dimensional array of numbers.
	 */
	public void resetSudoku(int[][] candidates) {
		sudoku.setValues(candidates);
	}

	// TODO: use sudoku.lock() as first argument in solve()

	public Sudoku solveWithNaiveApproach() {
		// eine zahl möglich -> sofort ausprobieren (rekursiv)
		naiveAlgorithm = new NaiveAlgorithmus(sudoku);
		Sudoku solvedSudoku = naiveAlgorithm.solve();
		this.solveOrder = naiveAlgorithm.getSolveOrder();
		this.indexNextSolveOrder = -1;

		return solvedSudoku;
	}

	public Sudoku solveWithBetterApproach() {
		Sudoku solvedSudoku = nakedSingle.solve();
		this.solveOrder = nakedSingle.getSolveOrder();
		this.indexNextSolveOrder = -1;

		return solvedSudoku;
	}

	public int[][] getArrSud(int[][] arrSud) {
		return arrSud;
	}

	public Sudoku getSudoku() {
		return sudoku;
	}

	public SudokuField[] getSudokuFields() {
		return sudoku.getFields();
	}

	public SudokuField getSudokuField(int row, int column) {
		return sudoku.getField(row, column);
	}

	public void setValue(int row, int column, int value) {
		sudoku.setValue(row, column, value);
	}

	public Vector<SudokuField> getSolveOrder() {
		return naiveAlgorithm.getSolveOrder();
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

		int prev = indexNextSolveOrder - 1;

		if (prev < 0) {
			return null;
		}

		return solveOrder.get(prev);
	}

	public boolean isSolved() {
		return isSolved;
	}

}
