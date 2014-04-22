package ch.zahw.students.sudokuhelper.test;

import java.io.IOException;

import android.test.InstrumentationTestCase;
import android.util.Log;
import ch.zahw.students.sudokuhelper.capture.DigitExtractor;
import ch.zahw.students.sudokuhelper.capture.NoSudokuFoundException;
import ch.zahw.students.sudokuhelper.capture.SquareFinder;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.highgui.Highgui;

// org.opencv.test is not in the release version yet as of 2.4.8.

public class SquareFinderTest extends InstrumentationTestCase {
    
    private double[][] vLines;
    private double[][] hLines;
    private double[][] edges;
    
    public void setUp(){
        vLines = new double[6][4];
        hLines = new double[6][4];
        edges = new double[4][4];
    }
    
    /**
     * This class creates a line-grid with the borders 10/10, 10/100, 10/500
     * and 100/500.
     */
    public void testLines() {
        hLines[0] = new double[]{10.0, 10.0,  500.0, 10.0};
        hLines[1] = new double[]{10.0, 100.0, 500.0, 100.0};
        hLines[2] = new double[]{ 0.0, 200.0, 550.0, 200.0};
        hLines[3] = new double[]{10.0, 300.0, 500.0, 300.0};
        hLines[4] = new double[]{10.0, 400.0, 500.0, 400.0};
        hLines[5] = new double[]{10.0, 500.0, 500.0, 500.0};
        
        vLines[0] = new double[]{ 10.0, 10.0,  10.0, 500.0};
        vLines[1] = new double[]{100.0, 10.0, 100.0, 500.0};
        vLines[2] = new double[]{ 0.0, 10.0, 0.0, 400.0};
        vLines[3] = new double[]{300.0, 10.0, 300.0, 500.0};
        vLines[4] = new double[]{400.0, 10.0, 400.0, 500.0};
        vLines[5] = new double[]{500.0, 10.0, 500.0, 500.0};
        
        edges[0] = new double[]{10.0, 10.0, 500.0, 10.0};
        edges[1] = new double[]{500.0, 10.0, 500.0, 500.0};
        edges[2] = new double[]{10.0, 500.0, 500.0, 500.0};
        edges[3] = new double[]{10.0, 10.0, 10.0, 500.0};
        
        SquareFinder squarefinder = null;
        try {
            squarefinder = new SquareFinder(hLines, vLines);
        } catch (NoSudokuFoundException e1) {
            fail(e1.getMessage());
        }
        
        // find the upper and lower edges. Should be the upper and lower
        // horizontal line that does not break the grid
        try {
            squarefinder.findUpperAndLowerEdge();
            squarefinder.findLeftAndRightEdge();
        } catch (NoSudokuFoundException e1) {
            fail(e1.getMessage());
        }
        double[][] calculatedEdges = squarefinder.getEdges();
        assertEquals(calculatedEdges.length, edges.length);
        for(int i = 0; i<4; i++) {
            for (int j = 0; j<4; j++) {
                assertEquals("Compare edge " + i + ", " + j, 
                        edges[i][j], calculatedEdges[i][j], 0.001);
            }
        }
        
        // make sure it raises an exception if no lines can be found
        try {
            squarefinder = new SquareFinder(hLines, new double[0][0]);
            fail("Square Finder did not throw an exception");
        } catch (NoSudokuFoundException e){  }
        
        try {
            squarefinder = new SquareFinder(hLines, hLines);
        } catch (NoSudokuFoundException e) {
            fail(e.getMessage());
        }
        // All lines are horizontal.
        try {
            squarefinder.findUpperAndLowerEdge();
            squarefinder.findLeftAndRightEdge();
            fail("Find Edges did not throw an exception");
        } catch (NoSudokuFoundException e){  }
    }
    
    public void testFindCornerPoints(){
        SquareFinder squarefinder = new SquareFinder();
        double[] v1 = new double[]{0, 0, 0, 100};
        double[] v2 = new double[]{0, 0, 100, 0};
        Point result = squarefinder.findCornerPoint(v1, v2);
        // lines touch at (0,0)
        assertEquals(0.0, result.x, 0.01);
        assertEquals(0.0, result.y, 0.01);
        
        v1 = new double[]{0, 10, 0, 100};
        v2 = new double[]{10, 0, 100, 0};
        result = squarefinder.findCornerPoint(v1, v2);
        // lines cross at (0,0)
        assertEquals(0.0, result.x, 0.01);
        assertEquals(0.0, result.y, 0.01);
        
        v1 = new double[]{50, 0, 50, 100};
        v2 = new double[]{0, 50, 100, 50};
        result = squarefinder.findCornerPoint(v1, v2);
        // lines intersece at (50,50)
        assertEquals(50.0, result.x, 0.01);
        assertEquals(50.0, result.y, 0.01);
        
        v1 = new double[]{0, 0, 100, 100};
        v2 = new double[]{0, 100, 100, 0};
        result = squarefinder.findCornerPoint(v1, v2);
        // lines intersece at (50,50)
        assertEquals(50.0, result.x, 0.01);
        assertEquals(50.0, result.y, 0.01);
        
        // Test previously failing corner:
        v1 = new double[]{143, 619, 143, 231};
        v2 = new double[]{133, 739, 703, 719};
        result = squarefinder.findCornerPoint(v1, v2);
        // lines intersece at (143,739)
        assertEquals(143.0, result.x, 0.01);
        assertEquals(739.0, result.y, 0.01);
        
    }
    
    public void testFindBoundingBoxes(){
        // sudoku1 has 27 digits
        Mat mGray = null;
        try {
            mGray = Utils.loadResource(getInstrumentation().getContext(), R.raw.sudoku1, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        DigitExtractor extractor = new DigitExtractor(mGray);
        try {
            extractor.extractDigits();
        } catch (NoSudokuFoundException e1) {
            fail(e1.getMessage());
        }
        assertEquals(27, extractor.getDigitCount());
        
        // sudoku2 has 24 digits
        try {
            mGray = Utils.loadResource(getInstrumentation().getContext(), R.raw.sudoku2, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        extractor = new DigitExtractor(mGray);
        try {
            extractor.extractDigits();
        } catch (NoSudokuFoundException e1) {
            fail(e1.getMessage());
        }
        assertEquals(24, extractor.getDigitCount());
        
        // sudoku3 is not recognized correctly. This should fail
        try {
            mGray = Utils.loadResource(getInstrumentation().getContext(), R.raw.sudoku3, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        extractor = new DigitExtractor(mGray);
        try {
            extractor.extractDigits();
            fail("Bad Sudoku did not throw exception");
        } catch (NoSudokuFoundException e) {
            assert(e instanceof NoSudokuFoundException);
        }
        
    }
    

}
