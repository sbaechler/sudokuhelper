package ch.zahw.students.sudokuhelper.test;

import org.junit.Test;
import static org.junit.Assert.*;
import ch.zahw.students.sudokuhelper.SquareFinder;

// org.opencv.test is not in the release version yet as of 2.4.8.


public class SquareFinderTest {
    
    private double[][] vLines;
    private double[][] hLines;
    private double[][] edges;
    
    public SquareFinderTest(){
        vLines = new double[6][4];
        hLines = new double[6][4];
        edges = new double[4][4];
    }
    
    @Test
    public void testLines()
    {
        hLines[0] = new double[]{10.0, 10.0,  100.0, 10.0};
        hLines[1] = new double[]{10.0, 100.0, 100.0, 100.0};
        hLines[2] = new double[]{ 0.0, 200.0, 150.0, 200.0};
        hLines[3] = new double[]{10.0, 300.0, 100.0, 300.0};
        hLines[4] = new double[]{10.0, 400.0, 100.0, 400.0};
        hLines[5] = new double[]{10.0, 500.0, 100.0, 500.0};
        
        vLines[0] = new double[]{ 10.0, 10.0,  10.0, 500.0};
        vLines[1] = new double[]{100.0, 10.0, 100.0, 500.0};
        vLines[2] = new double[]{ 0.0, 10.0, 0.0, 400.0};
        vLines[3] = new double[]{300.0, 10.0, 300.0, 500.0};
        vLines[4] = new double[]{400.0, 10.0, 400.0, 500.0};
        vLines[5] = new double[]{500.0, 10.0, 500.0, 500.0};
        
        edges[0] = new double[]{10.0, 10.0, 100.0, 10.0};
        edges[1] = new double[]{500.0, 10.0, 500.0, 500.0};
        edges[2] = new double[]{500.0, 10.0, 500.0, 500.0};
        edges[3] = new double[]{10.0, 10.0, 10.0, 500.0};
        
        SquareFinder squarefinder = new SquareFinder(hLines, vLines);
        
        // find the upper and lower edges. Should be the upper and lower
        // horizontal line that does not break the grid
        squarefinder.findUpperAndLowerEdge();
        squarefinder.findLeftAndRightEdge();
        double[][] calculatedEdges = squarefinder.getEdges();
        assertEquals(calculatedEdges.length, edges.length);
        for(int i = 0; i<4; i++) {
            for (int j = 0; j<4; j++) {
                assertEquals(edges[i][j], calculatedEdges[i][j], 0.001);
            }
        }
    }
    

}
